package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.homeviz.adapters.listadapters.MyExpandableAdapter;
import robindecroon.homeviz.fragments.metaphor.MetaphorContainerFragment;
import robindecroon.homeviz.fragments.metaphor.MetaphorContentFragment;
import robindecroon.homeviz.room.Consumer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

public class MetaphorListChildListener implements
		ExpandableListView.OnChildClickListener {

	private static FragmentActivity context;
	private MyExpandableAdapter adapter;
	private static Consumer consumer;
	private static int type;

	public MetaphorListChildListener(FragmentActivity context,
			MyExpandableAdapter adapter, int type) {
		MetaphorListChildListener.context = context;
		this.adapter = adapter;
		MetaphorListChildListener.type = type;
	}

	public boolean clickAction() {
		try {

			Fragment fragment = new MetaphorContentFragment();

			Bundle newArgs = new Bundle();
			switch (type) {
			case Constants.METAPHOR_TYPE_CO2:
				newArgs.putString(Constants.METAPHOR_VALUE, consumer
						.getCO2Value().toString());

				newArgs.putInt(Constants.METAPHOR_TYPE,
						Constants.METAPHOR_TYPE_CO2);
				break;
			case Constants.METAPHOR_TYPE_FUEL:
				newArgs.putString(Constants.METAPHOR_VALUE, consumer.getFuel()
						.toString());
				newArgs.putInt(Constants.METAPHOR_TYPE,
						Constants.METAPHOR_TYPE_FUEL);
				break;
			case Constants.METAPHOR_TYPE_WATER:
				newArgs.putString(
						Constants.METAPHOR_VALUE,
						Math.round(consumer.getLiter()
								/ Constants.BOTTLE_CONTENT)
								+ Constants.METAPHOR_WATER_TEXT);
				newArgs.putInt(Constants.METAPHOR_TYPE,
						Constants.METAPHOR_TYPE_WATER);
				break;
			}

			newArgs.putString(Constants.METAPHOR_CONSUMER_NAME,
					consumer.getName());
			newArgs.putBoolean(Constants.METAPHOR_CONSUMER, true);
			fragment.setArguments(newArgs);
			context.getSupportFragmentManager().beginTransaction()
					.replace(R.id.metaphor_content, fragment).commit();
			return true;
		} catch (Exception e) {
			Log.e(getClass().getSimpleName(), e.getMessage());
			return false;
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		for (int a = 0; a < parent.getChildCount(); a++) {
			parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
		}
		v.setBackgroundColor(context.getResources().getColor(R.color.roboto));
		consumer = adapter.getChild(groupPosition, childPosition);

		MetaphorContainerFragment.itemSelected = true;

		return clickAction();
	}

}
