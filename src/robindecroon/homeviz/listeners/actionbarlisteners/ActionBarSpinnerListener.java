package robindecroon.homeviz.listeners.actionbarlisteners;

import robindecroon.homeviz.Constants;
import robindecroon.homeviz.activities.MainActivity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public abstract class ActionBarSpinnerListener implements
		AdapterView.OnItemSelectedListener {

	protected MainActivity context;

	public ActionBarSpinnerListener(MainActivity context) {
		this.context = context;
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView,
			View selectedItemView, int position, long id) {

		TextView t = (TextView) parentView.getChildAt(0);
		t.setTextColor(Constants.SPINNER_TEXT_COLOR);

		String mystring = (String) t.getText();
		SpannableString content = new SpannableString(mystring);
		content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		t.setText(content);
	}

	protected void startIntent(int position, int category) {
		MainActivity.clickCounter++;
		MainActivity.categoryStack.push(category);
		MainActivity.selectionStack.push(position);
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(Constants.CATEGORY, category);
		intent.putExtra(Constants.SELECTION, position);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		if (MainActivity.lastCatergory != category) {
			context.startActivity(intent);
		} else if (MainActivity.lastPosition != position) {
			context.startActivity(intent);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
