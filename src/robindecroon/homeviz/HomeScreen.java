package robindecroon.homeviz;

import robindecroon.homeviz.usage.UsageActivity;
import robindecroon.homeviz.util.SystemUiHider;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class HomeScreen extends FullScreenActivity {
	
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen_layout);

//		// verberg de actionbar
//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
//		controlsView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		
		// clicklistener aan usage
		final TextView usage = (TextView) findViewById(R.id.keyword_usage);
		usage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
		usage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeScreen.this, UsageActivity.class);
				startActivityForResult(intent, USAGE_REQUEST_CODE);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, Menu.NONE, "Settings");
		return true;
	}
}
