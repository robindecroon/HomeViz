/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
package robindecroon.homeviz.fragments.metaphor;

import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.MetaphorListChildListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * The Class MetaphorContainerFragment.
 */
public class MetaphorContainerFragment extends Fragment {

	/** The last arguments. */
	private static Bundle lastArguments;
	
	/** The context. */
	private static FragmentActivity context;
	
	/** The item selected, needed to reopen the last selected device. */
	public static boolean itemSelected = false;

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View metaphorContainerView = inflater.inflate(R.layout.metaphor_container_layout,container, false);

		lastArguments = getArguments();
		context = getActivity();
		itemSelected = false;

		Fragment listFragment = new MetaphorExpandableListFragment();
		listFragment.setArguments(getArguments());
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_list, listFragment).commit();

		Fragment contentFragment = new MetaphorContentFragment();
		contentFragment.setArguments(getArguments());
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_content, contentFragment).commit();

		return metaphorContainerView;
	}

	/**
	 * Reset views.
	 */
	public static void resetViews() {
		Fragment metaphorContentFragment = new MetaphorContentFragment();
		metaphorContentFragment.setArguments(lastArguments);
		context.getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_content, metaphorContentFragment).commit();
		try {
			if (itemSelected) {
				((MetaphorListChildListener) MetaphorExpandableListFragment
						.getListener()).clickAction();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
