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
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.GroundWater;
import robindecroon.homeviz.util.RainWater;
import robindecroon.homeviz.util.SolarPanel;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.Entry;
import robindecroon.homeviz.xml.LoxoneXMLParser;
import robindecroon.homeviz.xml.XMLReturnObject;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class DownloadLoxoneXMLTask extends
		AsyncTask<String, Void, Map<String, List<Entry>>> {

	private List<Room> rooms;
	private HomeVizApplication app;

	private String user;
	private String password;
	private String ip;

	public DownloadLoxoneXMLTask(HomeVizApplication app) {
		this.rooms = app.getRooms();
		this.app = app;
	}

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
				Log.e(getClass().getSimpleName(),
						"XML error: " + e.getMessage());
				return null;
			}
		} catch (Exception e) {
			// lege foutmelding
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(Map<String, List<Entry>> result) {
		if (result != null) {

			for (String name : result.keySet()) {
				for (Room room : rooms) {
					try {
						Consumer cons = room.getConsumerWithName(name);
						cons.putEntries(result.get(name));
					} catch (NoSuchDevicesInRoom e) {
						// Not in this Room
					}
				}
			}
			ToastMessages.dataLoaded();
			Log.i(getClass().getSimpleName(), "Downloading Statistics 100%!");
		}
	}

	private Map<String, List<Entry>> loadXmlFromNetwork() throws IOException,
			XmlPullParserException {

		Map<String, List<Entry>> map = new HashMap<String, List<Entry>>();

		FTPClient client = new FTPClient();
		try {
			Set<String> fileNames = getFileNames(client);

			// Cookies necessary for authentication
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);
			for (String fileName : fileNames) {
				try {
					InputStream in = openInputStream(fileName);
					if (in != null) {
						LoxoneXMLParser loxoneXMLParser = new LoxoneXMLParser();
						Log.i(getClass().getSimpleName(), "Started parsing "
								+ fileName);
						XMLReturnObject XMLResult = loxoneXMLParser.parse(in);
						List<Entry> entries = XMLResult.getEntries();
						if (XMLResult.getNbOutputs() < 2) {
							putEntriesInMap(map, XMLResult, entries);								
						} else {
							processMeasurement(entries, XMLResult.getName());
						}
					} else {
						Log.e(getClass().getSimpleName(), "No inputstream for "
								+ fileName);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (SocketException e) {
			Log.e(getClass().getSimpleName(),
					"Connection error: " + e.getMessage());
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

	private Set<String> getFileNames(FTPClient client) throws SocketException,
			IOException {
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

	private void processMeasurement(List<Entry> entries, String name) {
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
		if(name.contains(Constants.METER_SOLAR)) {
			String unit = app.getResources().getString(R.string.kwh);
			app.setSolarPanel(new SolarPanel(total, current, today, yesterday, twoDays,
					thisWeek, lastWeek, thisMonth, lastMonth, thisYear, lastYear, unit));	
			return;
		} else if(name.contains(Constants.METER_GROUND_WATER)) {
			String unit = app.getResources().getString(R.string.liter);
			app.setGroundWater(new GroundWater(total, current, today, yesterday, twoDays,
					thisWeek, lastWeek, thisMonth, lastMonth, thisYear, lastYear, unit));	
			return;
		} else if(name.contains(Constants.METER_RAIN_WATER)) {
			String unit = app.getResources().getString(R.string.liter);
			app.setRainWater(new RainWater(total, current, today, yesterday, twoDays,
					thisWeek, lastWeek, thisMonth, lastMonth, thisYear, lastYear, unit));	
			return;
		}
		Log.e(getClass().getSimpleName(), "Measurement with name: " + name + " is not processed!");
	}

	private long findLatestTime(List<Entry> entries) {
		long lastDate = 0;
		for (Entry entry : entries) {
			long newDate = entry.getDate();
			if (lastDate < newDate) {
				lastDate = newDate;
			}
		}
		return lastDate;
	}

	private void putEntriesInMap(Map<String, List<Entry>> map,
			XMLReturnObject XMLResult, List<Entry> entries) {
		Collections.sort(entries, new Comparator<Entry>() {

			@Override
			public int compare(Entry lhs, Entry rhs) {
				return Long.valueOf(lhs.getDate()).compareTo(
						Long.valueOf(rhs.getDate()));
			}
		});
		map.put(XMLResult.getName(), entries);
	}

	private InputStream openInputStream(String fileName)
			throws MalformedURLException, IOException {
		// The XML statistics file
		URL url = new URL("http://" + ip + "/stats/" + fileName + ".xml");
		URLConnection httpConn = url.openConnection();
		// Authentication
		byte[] auth = (user + ":" + password).getBytes();
		String basic = Base64.encodeToString(auth, Base64.NO_WRAP);
		httpConn.setRequestProperty("Authorization", "Basic " + basic);

		InputStream in = httpConn.getInputStream();
		return in;
	}

}
