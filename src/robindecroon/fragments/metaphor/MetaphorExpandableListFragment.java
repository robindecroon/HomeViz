package robindecroon.fragments.metaphor;

import libraries.stackoverflow.ExpandableListFragment;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.room.Electric;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

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

		MyExpandableAdapter adapter = new MyExpandableAdapter(
				this.getActivity(), ((HomeVizApplication) getActivity()
						.getApplication()).getRooms(), Electric.class);

		elv.setAdapter(adapter);

		elv.setGroupIndicator(null); // Get rid of the down arrow
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// use groupPosition and childPosition to locate the current item in the
		// adapter
		Toast.makeText(getActivity(),
				"Hello!!: " + groupPosition + "  :  " + childPosition,
				Toast.LENGTH_LONG).show();
		return true;
	}

}
