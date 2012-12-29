package robindecroon.homeviz.usage;

import robindecroon.homeviz.R;
import robindecroon.homeviz.util.FullScreenActivity;
import robindecroon.homeviz.util.ToastMessages;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class UsageDetailActivity extends FullScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage_detail_layout);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		refreshElements();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.usage_detail_layout, menu);
		return true;
	}

	@Override
	public void onZoomIn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onZoomOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSwypeToLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSwypeToRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSwypeToUp() {
		Intent intent = new Intent(this, UsageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.down_enter, R.anim.down_leave);
	}

	@Override
	public void onSwypeToDown() {
		ToastMessages.noMoreDetail();
	}

//	@Override
//	public void refreshElements() {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	protected void setPeriod() {
		final TextView usagePeriod = (TextView) findViewById(R.id.usage_detail_period);
		usagePeriod.setText(currentPeriod.getName(this));
	}

	@Override
	protected void setLocation() {
		final TextView usageLocation = (TextView) findViewById(R.id.usage_detail_location);
		usageLocation.setText(currentRoom.getName());
	}

}
