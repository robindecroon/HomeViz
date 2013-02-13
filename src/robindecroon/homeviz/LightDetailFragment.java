package robindecroon.homeviz;

import robindecroon.homeviz.room.Light;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Light detail screen. This fragment is either
 * contained in a {@link LightListActivity} in two-pane mode (on tablets) or a
 * {@link LightDetailActivity} on handsets.
 */
public class LightDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private Light light;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public LightDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			String lightName = (String) getArguments().get(ARG_ITEM_ID);
			light = ((HomeVizApplication) getActivity().getApplication())
					.getCurrentRoom().getLight(lightName);
			
			// light = DummyContent.ITEM_MAP.get(getArguments().getString(
			// ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_light_detail,
				container, false);

		HomeVizApplication app = (HomeVizApplication) getActivity()
				.getApplication();

		// Show the dummy content as text in a TextView.
		if (light != null) {
			double power = light.getPowerUsage(app.getCurrentPeriod());
			TextView co2 = ((TextView) rootView.findViewById(R.id.co2));
			co2.setText(String.format("%.5f g", power * app.getCo2Multiplier()));
			TextView hotmeal = ((TextView) rootView.findViewById(R.id.hot_meal));
			hotmeal.setText(String.format("%.2f meals", power / 10));
			TextView car = ((TextView) rootView.findViewById(R.id.car));
			car.setText(String.format("%.3f liter",power + 0.261));
		}
		
		return rootView;
	}
}
