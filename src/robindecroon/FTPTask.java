package robindecroon;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import android.os.AsyncTask;

public class FTPTask extends AsyncTask<String, Void, File> {

	@Override
	protected File doInBackground(String... params) {
		try {
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect("192.168.1.102");
//		ftpClient.login(user, password);
//		ftpClient.changeWorkingDirectory(serverRoad);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//		BufferedInputStream buffIn=null;
//		buffIn=new BufferedInputStream(new FileInputStream(file));
			ftpClient.enterLocalPassiveMode();
//		ftpClient.storeFile("test.txt", buffIn);
//		buffIn.close();
			
			for(String string: ftpClient.listNames())
				System.out.println(string);
			ftpClient.logout();
			ftpClient.disconnect();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(File file) {
		
	}


}
