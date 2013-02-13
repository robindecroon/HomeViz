package robindecroon.homeviz.usage;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import android.content.Intent;
import android.os.Bundle;

public class HouseUsageActivity extends UsageActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentRoom = ((HomeVizApplication) getApplication()).getHouse();
		
		refreshElements();
	}
	
	@Override
	public void setListeners(){
		
	};
	
	@Override
	public void onSwypeToDown() {
		Intent intent = new Intent(this, HouseUsageDetailActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.up_enter, R.anim.up_leave);
		finish();
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

}
