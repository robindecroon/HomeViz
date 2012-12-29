package robindecroon.homeviz;

import robindecroon.homeviz.usage.UsageActivity;
import robindecroon.homeviz.util.SystemUiHider;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class HomeScreen extends Activity {

	/**
	 * Code om een usage activity aan te geven
	 */
	private static final int USAGE_REQUEST_CODE = 1;

	/**
	 * Code om een you activity aan te geven
	 */
	private static final int YOU_REQUEST_CODE = 2;

	/**
	 * Code om een hint activity aan te geven
	 */
	private static final int HINT_REQUEST_CODE = 3;

	private static final int PICKFILE_RESULT_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen_layout);
		
		// clicklistener aan usage
		final TextView usage = (TextView) findViewById(R.id.keyword_usage);
		usage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeScreen.this, UsageActivity.class);
				startActivityForResult(intent, USAGE_REQUEST_CODE);
			}
		});

		final TextView you = (TextView) findViewById(R.id.keyword_you);
		you.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType("file/");
					startActivityForResult(intent, PICKFILE_RESULT_CODE);
				} catch (ActivityNotFoundException exp) {
					ToastMessages.noFileManager();
					Log.e("HomeScreen", "No filemanger installed!");
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("HomeScreen", "HomeScreen got a result!");
		if (resultCode != RESULT_OK) {
			Log.e("HomeScreen", "Result not oke!");
			return;
		}

		if (requestCode == PICKFILE_RESULT_CODE) {
			((HomeVizApplication) getApplication()).parseXML(data.getData().getPath());
		}
	}
}
