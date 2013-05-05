/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.fragments.metaphor.MetaphorContainerFragment;
import robindecroon.homeviz.fragments.metaphor.MetaphorContentFragment;
import robindecroon.homeviz.house.device.Consumer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * The listener for receiving metaphorListChild events.
 *
 * @see MetaphorListChildEvent
 */
public class MetaphorListChildListener implements
		ExpandableListView.OnChildClickListener {

	/** The context. */
	private FragmentActivity context;
	
	/** The adapter. */
	private MyExpandableAdapter adapter;
	
	/** The consumer. */
	private Consumer consumer;
	
	/** The type. */
	private int type;

	/**
	 * Instantiates a new metaphor list child listener.
	 *
	 * @param context the context
	 * @param adapter the adapter
	 * @param type the type
	 */
	public MetaphorListChildListener(FragmentActivity context,
			MyExpandableAdapter adapter, int type) {
		this.context = context;
		this.adapter = adapter;
		this.type = type;
	}

	/**
	 * Click action.
	 *
	 * @return true, if successful
	 */
	public boolean clickAction() {
		try {
			Fragment metaphorContentFragment = new MetaphorContentFragment();

			Bundle newArgs = new Bundle();
			switch (type) {
			case Constants.METAPHOR_TYPE_CO2:
				newArgs.putString(Constants.METAPHOR_VALUE, consumer.getCO2Value().toString());
				newArgs.putInt(Constants.METAPHOR_TYPE,	Constants.METAPHOR_TYPE_CO2);
				break;
			case Constants.METAPHOR_TYPE_FUEL:
				newArgs.putString(Constants.METAPHOR_VALUE, consumer.getFuel().toString(context));
				newArgs.putInt(Constants.METAPHOR_TYPE,	Constants.METAPHOR_TYPE_FUEL);
				break;
			case Constants.METAPHOR_TYPE_WATER:
				newArgs.putString(Constants.METAPHOR_VALUE,
						Math.round(consumer.getLiter() / Constants.BOTTLE_CONTENT)
								+ context.getResources().getString(R.string.metaphor_water_text));
				newArgs.putInt(Constants.METAPHOR_TYPE,	Constants.METAPHOR_TYPE_WATER);
				break;
			}
			newArgs.putString(Constants.METAPHOR_CONSUMER_NAME,	consumer.getName());
			newArgs.putBoolean(Constants.METAPHOR_CONSUMER, true);
			metaphorContentFragment.setArguments(newArgs);
			context.getSupportFragmentManager().beginTransaction()
					.replace(R.id.metaphor_content, metaphorContentFragment).commit();
			return true;
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListView.OnChildClickListener#onChildClick(android.widget.ExpandableListView, android.view.View, int, int, long)
	 */
	@Override
	public boolean onChildClick(ExpandableListView parent, View selectedView,
			int groupPosition, int childPosition, long id) {
		// deselect the other views
		for (int a = 0; a < parent.getChildCount(); a++) {
			parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
		}
		// mark the selected view
		selectedView.setBackgroundColor(context.getResources().getColor(R.color.roboto));
		
		// set the new consumer
		consumer = adapter.getChild(groupPosition, childPosition);

		// set the flag, needed to reselect item when period changes
		MetaphorContainerFragment.itemSelected = true;

		return clickAction();
	}

}
