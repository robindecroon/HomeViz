package robindecroon.homeviz.util;

import robindecroon.homeviz.fragments.metaphor.MetaphorContainerFragment;
import robindecroon.homeviz.fragments.total.TotalTreeMapFragment;
import robindecroon.homeviz.fragments.usage.UsageContainerFragment;
import android.app.Activity;
import android.util.Log;

public class FragmentResetter {

	public static void reset(Activity c) {
		try {
			c.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					try {
						UsageContainerFragment.resetViews();
					} catch (Exception e) {
					}
					try {
						MetaphorContainerFragment.resetViews();
					} catch (Exception e) {
					}
					try {
						TotalTreeMapFragment.resetViews();
					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
			Log.e("FragmentResetter",
					"User scrolled the optionspinner too fast");
		}
	}

}
