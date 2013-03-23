package robindecroon.homeviz.listeners;

import robindecroon.fragments.usage.UsageContainerFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.Main;
import robindecroon.homeviz.R;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
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

		ActionBar ab = context.getActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setIcon(R.drawable.up_arrow);

		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View abv = inflator.inflate(R.layout.action_bar_title, null);

		((Main) context).initSpinners(abv);
		((Main) context).setUsageIconsSelection(0);

		ab.setCustomView(abv);

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
