package robindecroon.fragments.metaphor;

import libraries.stackoverflow.ExpandableListFragment;
import robindecroon.homeviz.Constants;
import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.room.Consumer;
import robindecroon.homeviz.room.Electric;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

		final MyExpandableAdapter adapter = new MyExpandableAdapter(
				this.getActivity(), ((HomeVizApplication) getActivity()
						.getApplication()).getRooms(), Electric.class);

		elv.setAdapter(adapter);
		elv.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

		elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				try {
					for(int a = 0; a < parent.getChildCount(); a++)
		            {
		                parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
		            }
					v.setBackgroundColor(getResources().getColor(R.color.roboto));
					Consumer object = adapter.getChild(groupPosition,
							childPosition);
					Fragment fragment2 = new MetaphorContentFragment();
					Bundle args = new Bundle();
					args.putString(Constants.CO2_KEY, object.getCO2Value()
							.toString());
					fragment2.setArguments(args);
					getActivity().getSupportFragmentManager()
							.beginTransaction()
							.replace(R.id.metaphor_content, fragment2).commit();
					return true;
				} catch (Exception e) {
					System.out.println("something wrong here    ");
					return false;
				}
			}
		});

//		elv.setGroupIndicator(null); // Get rid of the down arrow
	}
}
