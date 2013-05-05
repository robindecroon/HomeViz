/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.homeviz.activities.MainActivity;
import robindecroon.homeviz.fragments.usage.UsageContainerFragment;
import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * The listener for receiving consumerOnClick events.
 *
 * @see ConsumerOnClickEvent
 */
public class ConsumerOnClickListener implements OnClickListener {

	/** The room index. */
	private int roomIndex;
	
	/** The context. */
	private MainActivity context;
	
	/** The consumer type. */
	private int consumerType;

	/**
	 * Instantiates a new consumer on click listener.
	 *
	 * @param roomIndex the room index
	 * @param context the context
	 * @param consumerType the consumer type
	 */
	public ConsumerOnClickListener(int roomIndex, MainActivity context,
			int consumerType) {
		this.roomIndex = roomIndex;
		this.context = context;
		this.consumerType = consumerType;
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		ActionBar ab = setUpArrowInActionBar();

		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View actionBarView = inflator.inflate(R.layout.action_bar_title, null);

		context.initSpinners(actionBarView);
		context.setUsageIconsSelection(0);

		ab.setCustomView(actionBarView);

		Fragment usageContainerFragment = new UsageContainerFragment();
		Bundle args = new Bundle();
		args.putInt(Constants.USAGE_TYPE, consumerType);
		args.putInt(Constants.USAGE_ROOM, roomIndex);
		usageContainerFragment.setArguments(args);
		FragmentManager fm = context.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(R.anim.up_enter, R.anim.up_leave);
		ft.replace(R.id.container, usageContainerFragment).commit();
	}

	/**
	 * Sets the up arrow in action bar.
	 *
	 * @return the action bar
	 */
	private ActionBar setUpArrowInActionBar() {
		ActionBar ab = context.getActionBar();
		ab.setDisplayShowCustomEnabled(true);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		ab.setIcon(R.drawable.up_arrow);
		return ab;
	}

}
