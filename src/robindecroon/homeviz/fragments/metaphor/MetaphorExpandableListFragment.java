/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.metaphor;

import libraries.stackoverflow.ExpandableListFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.house.device.Electric;
import robindecroon.homeviz.house.device.Heating;
import robindecroon.homeviz.house.device.Water;
import robindecroon.homeviz.listeners.MetaphorListChildListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

/**
 * The Class MetaphorExpandableListFragment.
 */
public class MetaphorExpandableListFragment extends ExpandableListFragment {

	/** The listener. */
	private static ExpandableListView.OnChildClickListener listener;

	/* (non-Javadoc)
	 * @see libraries.stackoverflow.ExpandableListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.expandable_list, container, false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		Bundle args = getArguments();
		if (args != null) {
			Class classType = null;
			switch (args.getInt(Constants.METAPHOR_TYPE)) {
			case Constants.METAPHOR_TYPE_CO2:
				classType = Electric.class;
				break;
			case Constants.METAPHOR_TYPE_FUEL:
				classType = Heating.class;
				break;
			case Constants.METAPHOR_TYPE_WATER:
				classType = Water.class;
				break;
			}

			final int type = args.getInt(Constants.METAPHOR_TYPE);
			FragmentActivity context = getActivity();
			final MyExpandableAdapter adapter = new MyExpandableAdapter(
					context, ((HomeVizApplication) context.getApplication()).getRooms(), classType);
			listener = new MetaphorListChildListener(getActivity(), adapter, type);

			ExpandableListView elv = getExpandableListView();
			elv.setAdapter(adapter);
			elv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
			elv.setOnChildClickListener(listener);
		} else {
			throw new IllegalStateException(getClass().getSimpleName()
					+ " cannot be created without arguments!");
		}
	}

	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public static ExpandableListView.OnChildClickListener getListener() {
		return listener;
	}
}
