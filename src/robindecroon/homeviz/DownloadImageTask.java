package robindecroon.homeviz;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import robindecroon.coverflow.ResourceImageAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

//	Bitmap mImageView;

	
	ResourceImageAdapter adapter;
	
	public DownloadImageTask(ResourceImageAdapter resourceImageAdapter) {
		this.adapter = resourceImageAdapter;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
//		mImageView = result;
		Log.e("Tast", "New image!");
		adapter.updateBitmap(result);
	}

	@Override
	protected Bitmap doInBackground(String... src) {
		try {
			URL url = new URL(src[0]);
			InputStream input = url.openStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}