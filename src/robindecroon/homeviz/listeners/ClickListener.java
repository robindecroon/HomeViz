package robindecroon.homeviz.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressWarnings("rawtypes")
public class ClickListener implements OnClickListener {

	private Context context;
	private Class toClass;

	public ClickListener(Context context, Class toClass) {
		this.context = context;
		this.toClass = toClass;
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(context, toClass);
		context.startActivity(intent);
	}

}
