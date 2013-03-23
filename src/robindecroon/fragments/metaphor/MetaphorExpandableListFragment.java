package robindecroon.fragments.metaphor;

import libraries.stackoverflow.ExpandableListFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Electric;
import robindecroon.homeviz.room.Heating;
import robindecroon.homeviz.room.Water;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class MetaphorExpandableListFragment extends ExpandableListFragment {

	MyExpandableAdapter mAdapter;

	private ExpandableListView elv;

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
		elv.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

		elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				try {
					for (int a = 0; a < parent.getChildCount(); a++) {
						parent.getChildAt(a).setBackgroundColor(
								Color.TRANSPARENT);
					}
					v.setBackgroundColor(getResources()
							.getColor(R.color.roboto));
					Consumer object = adapter.getChild(groupPosition,
							childPosition);
					Fragment fragment = new MetaphorContentFragment();

					Bundle newArgs = new Bundle();
					if (getArguments() != null) {
						switch (type) {
						case Constants.METAPHOR_TYPE_CO2:
							newArgs.putString(Constants.METAPHOR_VALUE, object
									.getCO2Value().toString());
							newArgs.putInt(Constants.METAPHOR_TYPE,
									Constants.METAPHOR_TYPE_CO2);
							break;
						case Constants.METAPHOR_TYPE_FUEL:
							newArgs.putString(Constants.METAPHOR_VALUE, object
									.getFuel().toString());
							newArgs.putInt(Constants.METAPHOR_TYPE,
									Constants.METAPHOR_TYPE_FUEL);
							break;
						case Constants.METAPHOR_TYPE_WATER:
							newArgs.putString(
									Constants.METAPHOR_VALUE,
									Math.round(object.getLiter()
											/ Constants.BOTTLE_CONTENT)
											+ Constants.METAPHOR_WATER_TEXT);
							newArgs.putInt(Constants.METAPHOR_TYPE,
									Constants.METAPHOR_TYPE_WATER);
							break;
						}
					}

					newArgs.putString(Constants.METAPHOR_CONSUMER_NAME,
							object.getName());
					newArgs.putBoolean(Constants.METAPHOR_CONSUMER, true);
					fragment.setArguments(newArgs);
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.metaphor_content, fragment).commit();
					return true;
				} catch (Exception e) {
					Log.e(getClass().getSimpleName(), e.getMessage());
					return false;
				}
			}
		});
	}
}
