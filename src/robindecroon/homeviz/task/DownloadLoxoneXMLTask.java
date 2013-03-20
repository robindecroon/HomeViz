package robindecroon.homeviz.task;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.xml.Entry;
import robindecroon.homeviz.xml.LoxoneXMLParser;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadLoxoneXMLTask extends
		AsyncTask<String, Void, List<List<Entry>>> {

	@Override
	protected List<List<Entry>> doInBackground(String... urls) {
		try {
			return loadXmlFromNetwork(urls[0]);
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), "IO error: " + e.getMessage());
			return null;
		} catch (XmlPullParserException e) {
			Log.e(getClass().getSimpleName(), "XML error: " + e.getMessage());
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<List<Entry>> result) {
		if (result != null) {
			// TODO + resetten vorige data
			// for (List<Entry> list : result) {
			// System.out.println("New entries:");
			// for (Entry entry : list)
			// System.out.println("Entry: " + entry);
			// }
		}
	}

	private List<List<Entry>> loadXmlFromNetwork(String urlString)
			throws IOException, XmlPullParserException {

		List<List<Entry>> list = new ArrayList<List<Entry>>();

		FTPClient client = new FTPClient();
		try {

			client.connect(urlString);
			client.enterLocalPassiveMode();
			client.login(Constants.LOXONE_USER, Constants.LOXONE_PASSWORD);

			client.changeWorkingDirectory(Constants.WORKING_DIRECTORY);

			FTPFile[] ftpFiles = client.listFiles();
			for (FTPFile ftpFile : ftpFiles) {
				// Check if FTPFile is a regular file
				if (ftpFile.getType() == FTPFile.FILE_TYPE) {
					if (FilenameUtils.isExtension(ftpFile.getName(), "xml")) {
						System.out
								.println("parsing file: " + ftpFile.getName());
						InputStream stream = client.retrieveFileStream(ftpFile
								.getName());
						if (stream != null) {
							System.out.println("Stream is niet null");
							LoxoneXMLParser loxoneXMLParser = new LoxoneXMLParser();
							list.add(loxoneXMLParser.parse(stream));
						}
					}
				}
				if (!client.completePendingCommand()) {
					Log.e(getClass().getSimpleName(),
							"Pending command not completed!");
				}
			}
		} catch (SocketException e) {
			Log.e(getClass().getSimpleName(),
					"Connection error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(getClass().getSimpleName(), "IO error: " + e.getMessage());
		} finally {
			if (client != null) {
				client.logout();
				client.disconnect();
			}
		}
		return list;
	}
}
