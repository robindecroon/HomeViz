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

@SuppressWarnings("rawtypes")
public class MyExpandableAdapter extends BaseExpandableListAdapter {

	private List<Room> groups;
	private FragmentActivity context;
	private Class consumerClass;

	public MyExpandableAdapter(FragmentActivity context, List<Room> rooms,
			Class consumerClass) {
		this.groups = rooms;
		this.context = context;
		this.consumerClass = consumerClass;
	}

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
				throw new IllegalStateException("No child for class: "
						+ consumerClass.toString());
			}
		} catch (NoSuchDevicesInRoom e) {
			Log.i(getClass().getSimpleName(), "No consumers in this room");
			return null;
		}
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

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
				throw new IllegalStateException("No child for class: "
						+ consumerClass.toString());
			}
		} catch (NoSuchDevicesInRoom e) {
			Log.i(getClass().getSimpleName(), "No consumers in this room");
			return 0;
		}
	}

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

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition).toString());
		return textView;
	}

	@Override
	public Room getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition).toString());
		return textView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}
}
