package robindecroon.homeviz;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.util.ToastMessages;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadLoxoneXMLTask extends AsyncTask<String, Void, List<Entry>> {

	@Override
	protected List<Entry> doInBackground(String... urls) {
		try {
			return loadXmlFromNetwork(urls[0]);
		} catch (IOException e) {
			ToastMessages.connectionError();
			return null;
		} catch (XmlPullParserException e) {
			ToastMessages.xmlError();
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<Entry> result) {
		for (Entry entry : result)
			System.out.println("Entry: " + entry);
	}

	// Uploads XML from stackoverflow.com, parses it, and combines it with
	// HTML markup. Returns HTML string.
	private List<Entry> loadXmlFromNetwork(String urlString)
			throws XmlPullParserException, IOException {

		// Instantiate the parser
		LoxoneXMLParser loxoneXMLParser = new LoxoneXMLParser();
		List<Entry> entries = null;
		// Instantiate the inputstream
		InputStream stream = null;

		FTPClient client = new FTPClient();
		try {

			client.connect(urlString);
			client.enterLocalPassiveMode();
			client.login(Constants.LOXONE_USER, Constants.LOXONE_PASSWORD);

			client.changeWorkingDirectory("pools/A/A0/HomeViz/temp");

			FTPFile[] ftpFiles = client.listFiles();
			for (FTPFile ftpFile : ftpFiles) {

				// Check if FTPFile is a regular file
				if (ftpFile.getType() == FTPFile.FILE_TYPE) {
					if (ftpFile.getName().equalsIgnoreCase("drukknopl3.xml")) {
						stream = client.retrieveFileStream(ftpFile.getName());
					}
				}
			}
			if (stream != null) {
				entries = loxoneXMLParser.parse(stream);
			} else {
				Log.e(getClass().getSimpleName(), "No inputstream");
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stream != null) {
				stream.close();
			}
			if (client != null) {
				client.logout();
				client.disconnect();
			}
		}
		return entries;
	}
}
