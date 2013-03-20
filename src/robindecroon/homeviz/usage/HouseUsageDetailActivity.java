package robindecroon.homeviz.usage;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import android.content.Intent;
import android.os.Bundle;

public class HouseUsageDetailActivity extends UsageDetailActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentRoom = ((HomeVizApplication) getApplication()).getHouse();

		refreshElements();
	}

	@Override
	public void onSwypeToLeft() {

	}

	@Override
	public void onSwypeToRight() {

	}

	@Override
	public void initActionBar() {

	}

	@Override
	public void onSwypeToUp() {
		Intent intent = new Intent(this, HouseUsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
		finish();
	}

}
