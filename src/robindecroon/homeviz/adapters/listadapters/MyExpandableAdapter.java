/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.adapters.listadapters;

import java.util.List;

import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.house.Room;
import robindecroon.homeviz.house.device.Consumer;
import robindecroon.homeviz.house.device.Electric;
import robindecroon.homeviz.house.device.Heating;
import robindecroon.homeviz.house.device.Water;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * The Class MyExpandableAdapter.
 */
@SuppressWarnings("rawtypes")
public class MyExpandableAdapter extends BaseExpandableListAdapter {

	/** The groups. */
	private List<Room> groups;
	
	/** The context. */
	private FragmentActivity context;
	
	/** The consumer class. */
	private Class consumerClass;

	/**
	 * Instantiates a new my expandable adapter.
	 *
	 * @param context the context
	 * @param rooms the rooms
	 * @param consumerClass the consumer class
	 */
	public MyExpandableAdapter(FragmentActivity context, List<Room> rooms,
			Class consumerClass) {
		this.groups = rooms;
		this.context = context;
		this.consumerClass = consumerClass;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChild(int, int)
	 */
	@Override
	public Consumer getChild(int arg0, int arg1) {
		try {
			if (consumerClass == Water.class) {
				return groups.get(arg0).getWaters().get(arg1);
			} else if (consumerClass == Electric.class) {
				return groups.get(arg0).getElectrics().get(arg1);
			} else if (consumerClass == Heating.class) {
				return groups.get(arg0).getHeatings().get(arg1);
			} else {
				throw new IllegalStateException("No child for class: " + consumerClass.toString());
			}
		} catch (NoSuchDevicesInRoom e) {
			Log.i(getClass().getSimpleName(), "No consumers in this room");
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildId(int, int)
	 */
	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		try {
			if (consumerClass == Water.class) {
				return groups.get(groupPosition).getWaters().size();
			} else if (consumerClass == Electric.class) {
				return groups.get(groupPosition).getElectrics().size();
			} else if (consumerClass == Heating.class) {
				return groups.get(groupPosition).getHeatings().size();
			} else {
				throw new IllegalStateException("No child for class: " + consumerClass.toString());
			}
		} catch (NoSuchDevicesInRoom e) {
			Log.i(getClass().getSimpleName(), "No consumers in this room");
			return 0;
		}
	}

	/**
	 * Gets the generic view.
	 *
	 * @return the generic view
	 */
	public TextView getGenericView() {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, 64);

		TextView textView = new TextView(context);
		textView.setLayoutParams(lp);
		// Center the text vertically
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		textView.setPadding(36, 0, 0, 0);
		return textView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition).toString());
		return textView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroup(int)
	 */
	@Override
	public Room getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupCount()
	 */
	@Override
	public int getGroupCount() {
		return groups.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupId(int)
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition).toString());
		return textView;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	/* (non-Javadoc)
	 * @see android.widget.ExpandableListAdapter#hasStableIds()
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}
}
