/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.Entry;
import robindecroon.homeviz.xml.LoxoneXMLParser;
import robindecroon.homeviz.xml.XMLReturnObject;
import robindecroon.homeviz.yield.GroundWater;
import robindecroon.homeviz.yield.RainWater;
import robindecroon.homeviz.yield.SolarPanel;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

/**
 * The Class DownloadLoxoneXMLTask.
 */
public class DownloadLoxoneXMLTask extends
		AsyncTask<String, Void, Map<String, List<Entry>>> {

	/** The rooms. */
	private List<Room> rooms;
	
	/** The app. */
	private HomeVizApplication app;

	/** The user. */
	private String user;
	
	/** The password. */
	private String password;
	
	/** The ip. */
	private String ip;

	/**
	 * Instantiates a new download loxone xml task.
	 *
	 * @param app the app
	 */
	public DownloadLoxoneXMLTask(HomeVizApplication app) {
		this.rooms = app.getRooms();
		this.app = app;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Map<String, List<Entry>> doInBackground(String... settings) {
		Log.i(getClass().getSimpleName(), "Synchronization with Loxone started");
		user = settings[0];
		password = settings[1];
		ip = settings[2];
		try {
			try {
				return loadXmlFromNetwork();
			} catch (IOException e) {
				Log.e(getClass().getSimpleName(), "IO error: " + e.getMessage());
				return null;
			} catch (XmlPullParserException e) {
				Log.e(getClass().getSimpleName(), "XML error: " + e.getMessage());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Map<String, List<Entry>> result) {
		if (result != null) {
			for (String name : result.keySet()) {
				for (Room room : rooms) {
					try {
						Consumer cons = room.getConsumerWithName(name);
						cons.putEntries(result.get(name));
					} catch (NoSuchDevicesInRoom e) {}
				}
			}
			ToastMessages.dataLoaded();
			Log.i(getClass().getSimpleName(), "Downloaded Statistics 100%!");
		}
	}

	/**
	 * Load xml from network.
	 *
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	private Map<String, List<Entry>> loadXmlFromNetwork() throws IOException,
			XmlPullParserException {

		Map<String, List<Entry>> map = new HashMap<String, List<Entry>>();
		FTPClient client = new FTPClient();
		try {
			// Cookies necessary for authentication
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);
			
			for (String fileName : getFileNames(client)) {
				try {
					InputStream in = openInputStream(fileName);
					if (in != null) {
						LoxoneXMLParser loxoneXMLParser = new LoxoneXMLParser();
						Log.i(getClass().getSimpleName(), "Started parsing " + fileName);
						XMLReturnObject XMLResult = loxoneXMLParser.parse(in);
						List<Entry> entries = XMLResult.getEntries();
						if (XMLResult.getNbOutputs() < 2) {
							putEntriesInMap(map, XMLResult, entries);
						} else {
							parseMeasurement(entries, XMLResult.getName());
						}
					} else {
						Log.e(getClass().getSimpleName(), "No inputstream for "	+ fileName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (SocketException e) {
			Log.e(getClass().getSimpleName(), "Connection error: " + e.getMessage());
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), "IO error: " + e.getMessage());
		} finally {
			if (client != null) {
				client.logout();
				client.disconnect();
			}
		}
		return map;
	}

	/**
	 * Gets the file names.
	 *
	 * @param client the client
	 * @return the file names
	 * @throws SocketException the socket exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Set<String> getFileNames(FTPClient client) throws SocketException, IOException {
		client.connect(ip);
		client.enterLocalPassiveMode();
		client.login(user, password);
		client.changeWorkingDirectory(Constants.WORKING_DIRECTORY);

		FTPFile[] ftpFiles = client.listFiles();
		Set<String> fileNames = new HashSet<String>();
		for (FTPFile ftpFile : ftpFiles) {
			fileNames.add(ftpFile.getName());
		}
		Log.i(getClass().getSimpleName(), "File names found!");
		return fileNames;
	}

	/**
	 * Parses the measurement.
	 *
	 * @param entries the entries
	 * @param name the name
	 */
	private void parseMeasurement(List<Entry> entries, String name) {
		long lastDate = findLatestTime(entries);

		double total = 0;
		double current = 0;
		double today = 0;
		double yesterday = 0;
		double twoDays = 0;
		double thisWeek = 0;
		double lastWeek = 0;
		double thisMonth = 0;
		double lastMonth = 0;
		double thisYear = 0;
		double lastYear = 0;

		for (Entry entry : entries) {
			if (entry.getDate() == lastDate) {
				double value = Double.valueOf(entry.getValue());
				switch (entry.getType()) {
				case AQ:
					total = value;
					break;
				case AQp:
					current = value;
					break;
				case AQ1:
					today = value;
					break;
				case AQ2:
					yesterday = value;
					break;
				case AQ3:
					twoDays = value;
					break;
				case AQ4:
					thisWeek = value;
					break;
				case AQ5:
					lastWeek = value;
					break;
				case AQ6:
					thisMonth = value;
					break;
				case AQ7:
					lastMonth = value;
					break;
				case AQ8:
					thisYear = value;
					break;
				case AQ9:
					lastYear = value;
					break;
				default:
					break;
				}
			}
		}
		if (name.contains(Constants.METER_SOLAR)) {
			String unit = app.getResources().getString(R.string.kwh);
			app.setSolarPanel(new SolarPanel(total, current, today, yesterday,
					twoDays, thisWeek, lastWeek, thisMonth, lastMonth,
					thisYear, lastYear, unit));
		} else if (name.contains(Constants.METER_GROUND_WATER)) {
			String unit = app.getResources().getString(R.string.liter);
			app.setGroundWater(new GroundWater(total, current, today,
					yesterday, twoDays, thisWeek, lastWeek, thisMonth,
					lastMonth, thisYear, lastYear, unit));
		} else if (name.contains(Constants.METER_RAIN_WATER)) {
			String unit = app.getResources().getString(R.string.liter);
			app.setRainWater(new RainWater(total, current, today, yesterday,
					twoDays, thisWeek, lastWeek, thisMonth, lastMonth,
					thisYear, lastYear, unit));
		} else{
			Log.e(getClass().getSimpleName(), "Measurement with name: " + name + " is not processed!");			
		}
	}

	/**
	 * Find latest time.
	 *
	 * @param entries the entries
	 * @return the long
	 */
	private long findLatestTime(List<Entry> entries) {
		long lastDate = 0;
		for (Entry entry : entries) {
			long newDate = entry.getDate();
			if (lastDate < newDate) lastDate = newDate;
		}
		return lastDate;
	}

	/**
	 * Put entries in map.
	 *
	 * @param map the map
	 * @param XMLResult the xML result
	 * @param entries the entries
	 */
	private void putEntriesInMap(Map<String, List<Entry>> map,
			XMLReturnObject XMLResult, List<Entry> entries) {
		Collections.sort(entries, new Comparator<Entry>() {
			@Override
			public int compare(Entry lhs, Entry rhs) {
				return Long.valueOf(lhs.getDate()).compareTo(Long.valueOf(rhs.getDate()));
			}
		});
		map.put(XMLResult.getName(), entries);
	}

	/**
	 * Open input stream.
	 *
	 * @param fileName the file name
	 * @return the input stream
	 * @throws MalformedURLException the malformed url exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private InputStream openInputStream(String fileName)
			throws MalformedURLException, IOException {
		// Location of the XML statistics file
		URL url = new URL("http://" + ip + "/stats/" + fileName + ".xml");
		URLConnection httpConn = url.openConnection();
		
		// Authentication
		byte[] auth = (user + ":" + password).getBytes();
		String basic = Base64.encodeToString(auth, Base64.NO_WRAP);
		httpConn.setRequestProperty("Authorization", "Basic " + basic);

		// Get the input stream
		return httpConn.getInputStream();
	}

}
