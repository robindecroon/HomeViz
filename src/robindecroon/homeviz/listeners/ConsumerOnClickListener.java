package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.layout.UsageContainerFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

public class ConsumerOnClickListener implements OnClickListener {

	private int roomIndex;
	private FragmentActivity context;
	private int consumerType;

	public ConsumerOnClickListener(int roomIndex, FragmentActivity context,
			int consumerType) {
		this.roomIndex = roomIndex;
		this.context = context;
		this.consumerType = consumerType;
	}

	@Override
	public void onClick(View v) {
		Fragment fragment = new UsageContainerFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_TYPE, consumerType);
		args.putInt("room", roomIndex);
		fragment.setArguments(args);
		FragmentManager fm = context.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(R.anim.up_enter, R.anim.up_leave);
		ft.replace(R.id.container, fragment).commit();
	}

}
