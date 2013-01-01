package robindecroon.homeviz.usage;

import java.util.List;
import java.util.Locale;

import robindecroon.homeviz.R;
import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.util.SystemUiHider;
import robindecroon.homeviz.util.ToastMessages;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LightUsageActivity extends UsageFullScreenActivity {
	
	private boolean lampsPresent = true;
	
//	private List<View> view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.light_usage_layout);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		refreshElements();
	}

	@Override
	public void onSwypeToUp() {
	}

	@Override
	public void onSwypeToDown() {
		if (lampsPresent) {
			Intent intent = new Intent(this, LightUsageDetailActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.up_enter, R.anim.up_leave);
		} else {
			ToastMessages.noMoreDetail();
		}
	}

	@Override
	public void onSwypeToLeft() {
		super.onSwypeToLeft();
		Intent intent = new Intent(this, LightUsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_enter, R.anim.left_leave);
		finish();
	}

	@Override
	public void onSwypeToRight() {
		super.onSwypeToRight();
		Intent intent = new Intent(this, LightUsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_enter, R.anim.right_leave);
		finish();
	}

	@Override
	protected void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.light_period);
		usagePeriod.setText(currentPeriod.getName(this));
	}

	@Override
	protected void setLocation() {
		final TextView usageLocation = (TextView) findViewById(R.id.light_location);
		usageLocation.setText(currentRoom.getName());
	}

	@Override
	public void refreshElements() {
		super.refreshElements();
		setAmounts();
	}

	private void setAmounts() {
		LinearLayout lightsLayout = (LinearLayout) findViewById(R.id.lights);
		lightsLayout.removeAllViews();
		try {
			List<Light> lights = currentRoom.getLights();

			for (int i = 0; i < lights.size(); i++) {

				Light light = lights.get(i);
				LinearLayout layout = new LinearLayout(this);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				layout.setLayoutParams(lp);
				layout.setOrientation(LinearLayout.VERTICAL);

				ImageView image = new ImageView(this);
				LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				lp2.gravity = Gravity.CENTER;
				image.setAdjustViewBounds(true);
				image.setLayoutParams(lp2);

				String imagename = light.getId().toLowerCase(Locale.US);
				int picId = getResources().getIdentifier(imagename, "drawable",
						getPackageName());
				image.setImageResource(picId);

				layout.addView(image);

				TextView text = new TextView(this);
				text.setTextColor(getResources().getColor(R.color.Black));
				text.setGravity(Gravity.CENTER);
				text.setLayoutParams(new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT));
				text.setTextSize(40);
				text.setText(light.getPrice(currentPeriod).toString());

				layout.addView(text);

				lightsLayout.addView(layout);
			}
		} catch (NoSuchDevicesInRoom e) {
			TextView noLights = new TextView(this);
			noLights.setText(R.string.no_lights);
			noLights.setTextSize(100);
			noLights.setTextColor(getResources().getColor(R.color.Black));
			lightsLayout.addView(noLights);
			
			lampsPresent = false;

		}
	}

	@Override
	protected void setListeners() {
		// TODO Auto-generated method stub
		
	}

}
