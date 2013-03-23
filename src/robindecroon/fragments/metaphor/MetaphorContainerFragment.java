package robindecroon.fragments.metaphor;

import robindecroon.homeviz.R;
import robindecroon.homeviz.listeners.MetaphorListChildListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MetaphorContainerFragment extends Fragment {

	private static Bundle lastArguments;
	private static FragmentActivity context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.metaphor_container_layout,
				container, false);

		lastArguments = getArguments();
		context = getActivity();

		itemSelected = false;

		Fragment fragment = new MetaphorExpandableListFragment();
		fragment.setArguments(getArguments());
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_list, fragment).commit();

		Fragment fragment2 = new MetaphorContentFragment();
		fragment2.setArguments(getArguments());
		getActivity().getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_content, fragment2).commit();

		return rootView;
	}

	public static boolean itemSelected = false;

	public static void resetViews() {
		Fragment fragment2 = new MetaphorContentFragment();
		fragment2.setArguments(lastArguments);
		context.getSupportFragmentManager().beginTransaction()
				.replace(R.id.metaphor_content, fragment2).commit();
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
