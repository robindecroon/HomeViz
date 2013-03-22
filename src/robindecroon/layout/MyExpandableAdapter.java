package robindecroon.layout;

import java.util.List;

import robindecroon.homeviz.exceptions.NoSuchDevicesInRoom;
import robindecroon.homeviz.room.Room;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

	private List<Room> groups;
//	private List<Consumer> children;
	private FragmentActivity context;
	
	public MyExpandableAdapter(FragmentActivity context, List<Room> rooms) {
		this.groups = rooms;
		this.context = context;
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		try {
			return groups.get(arg0).getWaters().get(arg1);
		} catch (NoSuchDevicesInRoom e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	public int getChildrenCount(int groupPosition) {
		try {
			return groups.get(groupPosition).getWaters().size();
		} catch (NoSuchDevicesInRoom e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getChild(groupPosition, childPosition).toString());
		return textView;
	}

	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TextView textView = getGenericView();
		textView.setText(getGroup(groupPosition).toString());
		return textView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}
}
