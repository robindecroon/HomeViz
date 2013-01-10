package robindecroon.homeviz;

import robindecroon.homeviz.listeners.ClickListener;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.usage.UsageActivity;
import robindecroon.homeviz.util.ToastMessages;
import robindecroon.homeviz.you.YouActivity;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Startscherm van HomeViz.
 * 
 */
public class HomeScreen extends Activity {

	/**
	 * Request code om een XML bestand te selecteren.
	 */
	private static final int PICKFILE_RESULT_CODE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen_layout);

		// Usage
		final TextView usage = (TextView) findViewById(R.id.keyword_usage);
		usage.setOnClickListener(new ClickListener(this, UsageActivity.class));

		// You
		final TextView you = (TextView) findViewById(R.id.keyword_you);
		you.setOnClickListener(new ClickListener(this, YouActivity.class));
//		you.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				for(Room room : ((HomeVizApplication) getApplicationContext()).getRooms()) {
//					room.printPercentages();
//				}
//			}
//			
//		});


		// HINT
		final TextView hint = (TextView) findViewById(R.id.keyword_hint);
		hint.setOnClickListener(new View.OnClickListener() {

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
			((HomeVizApplication) getApplication()).parseXML(data.getData()
					.getPath());
		}
	}
}
