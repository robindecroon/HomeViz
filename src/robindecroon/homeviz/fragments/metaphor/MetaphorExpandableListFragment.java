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

	private static ExpandableListView.OnChildClickListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.expandable_list, container, false);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onActivityCreated(Bundle savedState) {
		super.onActivityCreated(savedState);

		Bundle args = getArguments();
		if (args != null) {
			Class classType = null;
			final int type = args.getInt(Constants.METAPHOR_TYPE);
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

			final MyExpandableAdapter adapter = new MyExpandableAdapter(
					this.getActivity(), ((HomeVizApplication) getActivity()
							.getApplication()).getRooms(), classType);

			ExpandableListView elv = getExpandableListView();
			elv.setAdapter(adapter);
			elv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

			listener = new MetaphorListChildListener(getActivity(), adapter,
					type);
			elv.setOnChildClickListener(listener);
		} else {
			throw new IllegalStateException(getClass().getSimpleName()
					+ " cannot be created without arguments!");
		}

	}

	public static ExpandableListView.OnChildClickListener getListener() {
		return listener;
	}
}
