package robindecroon.homeviz.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
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
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.xml.IEntry;
import robindecroon.homeviz.xml.LoxoneXMLParser;
import robindecroon.homeviz.xml.XMLReturnObject;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class DownloadLoxoneXMLTask extends
		AsyncTask<String, Void, Map<String, List<IEntry>>> {

	private List<Room> rooms;
	
	private String user;
	private String password;
	private String ip;

	public DownloadLoxoneXMLTask(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	protected Map<String, List<IEntry>> doInBackground(String... settings) {
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
	protected void onPostExecute(Map<String, List<IEntry>> result) {
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

	private Map<String, List<IEntry>> loadXmlFromNetwork()
			throws IOException, XmlPullParserException {

		Map<String, List<IEntry>> map = new HashMap<String, List<IEntry>>();

		FTPClient client = new FTPClient();
		try {
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

			// Cookies necessary for authentication
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);
			for (String fileName : fileNames) {
				try {
					// The XML statistics file
					URL url = new URL("http://" + ip
							+ "/stats/" + fileName + ".xml");
					URLConnection httpConn = url.openConnection();
					// Authentication
					byte[] auth = (user + ":" + password).getBytes();
					String basic = Base64.encodeToString(auth, Base64.NO_WRAP);
					httpConn.setRequestProperty("Authorization", "Basic "
							+ basic);

					InputStream in = httpConn.getInputStream();
					if (in != null) {
						LoxoneXMLParser loxoneXMLParser = new LoxoneXMLParser();
						Log.i(getClass().getSimpleName(), "Started parsing "
								+ fileName);
						XMLReturnObject XMLResult = loxoneXMLParser.parse(in);
						List<IEntry> entries = XMLResult.getEntries();
						Collections.sort(entries, new Comparator<IEntry>() {

							@Override
							public int compare(IEntry lhs, IEntry rhs) {
								return Long.valueOf(lhs.getDate()).compareTo(
										Long.valueOf(rhs.getDate()));
							}
						});
						map.put(XMLResult.getName(), entries);
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

}
