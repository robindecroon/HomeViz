package robindecroon.homeviz.util.webviews;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class MyJavaScriptInterface {
	Context mContext;

	public MyJavaScriptInterface(Context c) {
		mContext = c;
	}

	public void showToast(String toast) {
		Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	}

	public void openAndroidDialog() {
		AlertDialog.Builder myDialog = new AlertDialog.Builder(mContext);
		myDialog.setTitle("DANGER!");
		myDialog.setMessage("You can do what you want!");
		myDialog.setPositiveButton("ON", null);
		myDialog.show();
	}
}
