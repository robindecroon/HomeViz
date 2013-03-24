package robindecroon.homeviz.fragments.metaphor;

import libraries.stackoverflow.ExpandableListFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.listeners.MetaphorListChildListener;
import robindecroon.homeviz.room.Electric;
import robindecroon.homeviz.room.Heating;
import robindecroon.homeviz.room.Water;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

public class MetaphorExpandableListFragment extends ExpandableListFragment {

	MyExpandableAdapter mAdapter;

	private ExpandableListView elv;

	private static ExpandableListView.OnChildClickListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.expandable_list, container,
				false);

		return mainView;
	}

	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		elv = getExpandableListView();

		Bundle args = getArguments();
		final int type = args.getInt(Constants.METAPHOR_TYPE);
		Class classType = null;
		if (args != null) {
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
		}

		final MyExpandableAdapter adapter = new MyExpandableAdapter(
				this.getActivity(), ((HomeVizApplication) getActivity()
						.getApplication()).getRooms(), classType);

		elv.setAdapter(adapter);
		elv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

		listener = new MetaphorListChildListener(getActivity(), adapter, type);
		elv.setOnChildClickListener(listener);
	}

	public static ExpandableListView.OnChildClickListener getListener() {
		return listener;
	}
}
