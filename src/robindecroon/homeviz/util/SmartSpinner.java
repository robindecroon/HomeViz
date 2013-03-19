package robindecroon.homeviz.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

public class SmartSpinner extends Spinner {

	
	


//	void setSelectionInt(int position, boolean animate) {
//	    mOldSelectedPosition = INVALID_POSITION;
//	    super.setSelectionInt(position, animate);
//	}

	public SmartSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsSpinner#setSelection(int)
	 */
	@Override
	public void setSelection(int position) {
		// TODO Auto-generated method stub
		super.setSelection(position);
	}

}
