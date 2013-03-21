package robindecroon.homeviz.listeners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.R;
import robindecroon.layout.Main;
import robindecroon.layout.SpinnerEnum;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public abstract class ActionBarSpinnerListener implements
		AdapterView.OnItemSelectedListener {
	
	protected Main context;
	
	protected AdapterView<?> parentView;
	protected View selectedItemView;

	public ActionBarSpinnerListener(Main context) {
		this.context = context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {
		
		this.parentView = parentView;
		this.selectedItemView = selectedItemView;
		
//		LayoutInflater inflator = (LayoutInflater) context
//				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View v = inflator.inflate(R.layout.action_bar_title, null);
//		context.initSpinners(v, this, position);

		TextView t = (TextView) parentView.getChildAt(0);
		t.setTextColor(Constants.SPINNER_TEXT_COLOR);
		
		String mystring= (String) t.getText();
		SpannableString content = new SpannableString(mystring);
		content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		t.setText(content);
	}
	

	protected void startIntent(int position, int category) {
		Intent intent = new Intent(context, Main.class);
		intent.putExtra(Constants.CATEGORY, category);
		intent.putExtra(Constants.SELECTION, position);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		if (Main.lastCatergory != category) {
			context.startActivity(intent);
		} else if (Main.lastPosition != position) {
			context.startActivity(intent);
		}
	}

	/**
	 * @return the parentView
	 */
	public AdapterView<?> getParentView() {
		return parentView;
	}

	/**
	 * @return the selectedItemView
	 */
	public View getSelectedItemView() {
		return selectedItemView;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
	
	public abstract SpinnerEnum getType();

	
}
