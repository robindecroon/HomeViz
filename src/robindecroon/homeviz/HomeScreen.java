//package robindecroon.homeviz;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//import robindecroon.coverflow.CoverFlow;
//import robindecroon.coverflow.ReflectingImageAdapter;
//import robindecroon.coverflow.ResourceImageAdapter;
//import robindecroon.homeviz.listeners.ClickListener;
//import robindecroon.homeviz.task.DownloadLoxoneXMLTask;
//import robindecroon.homeviz.task.InitHomeVizTask;
//import robindecroon.homeviz.usage.UsageActivity;
//import robindecroon.homeviz.util.ToastMessages;
//import robindecroon.homeviz.you.YouActivity;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.ActivityNotFoundException;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.Address;
//import android.location.Criteria;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.facebook.FacebookAuthorizationException;
//import com.facebook.FacebookOperationCanceledException;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.LoginButton;
//import com.facebook.widget.ProfilePictureView;
//
///**
// * Startscherm van HomeViz.
// * 
// */
//public class HomeScreen extends Activity implements LocationListener {
//
//	/**
//	 * Request code om een XML bestand te selecteren.
//	 */
//	private static final int PICKFILE_RESULT_CODE = 1;
//	private static final int LOCATION_SETTINGS = 2;
//
//	private LoginButton loginButton;
//	private ProfilePictureView profilePictureView;
//	private PendingAction pendingAction = PendingAction.NONE;
//	private GraphUser user;
//
//	private enum PendingAction {
//		NONE, POST_PHOTO, POST_STATUS_UPDATE
//	}
//
//	private UiLifecycleHelper uiHelper;
//
//	private Session.StatusCallback callback = new Session.StatusCallback() {
//		@Override
//		public void call(Session session, SessionState state,
//				Exception exception) {
//			onSessionStateChange(session, state, exception);
//		}
//	};
//
//	private void onSessionStateChange(Session session, SessionState state,
//			Exception exception) {
//		if (pendingAction != PendingAction.NONE
//				&& (exception instanceof FacebookOperationCanceledException || exception instanceof FacebookAuthorizationException)) {
//			new AlertDialog.Builder(HomeScreen.this)
//					// TODO
//					.setTitle("cancelled").setMessage("permission_not_granted")
//					.setPositiveButton("ok", null).show();
//			pendingAction = PendingAction.NONE;
//		} else if (state == SessionState.OPENED_TOKEN_UPDATED) {
//			handlePendingAction();
//		}
//		updateUI();
//	}
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.home_screen_layout);
//
//		// Parse alle xml bestanden
//		startTasks();
//
//		// Usage
//		final ImageView usage = (ImageView) findViewById(R.id.keyword_usage);
//		usage.setOnClickListener(new ClickListener(this, UsageActivity.class));
//
//		// You
//		final ImageView you = (ImageView) findViewById(R.id.keyword_you);
//		you.setOnClickListener(new ClickListener(this, YouActivity.class));
//
//		// HINT
//		final TextView hint = (TextView) findViewById(R.id.keyword_hint);
//		hint.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				try {
//					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//					intent.setType("file/");
//					startActivityForResult(intent, PICKFILE_RESULT_CODE);
//				} catch (ActivityNotFoundException exp) {
//					ToastMessages.noFileManager();
//					Log.e("HomeScreen", "No filemanger installed!");
//				}
//			}
//		});
//
//		initCurrentLocation();
//
//		uiHelper = new UiLifecycleHelper(this, callback);
//		uiHelper.onCreate(savedInstanceState);
//
//		loginButton = (LoginButton) findViewById(R.id.loginButton1);
//		loginButton
//				.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
//					@Override
//					public void onUserInfoFetched(GraphUser user) {
//						HomeScreen.this.user = user;
//						((HomeVizApplication) getApplication())
//								.setFacebookUser(user);
//						updateUI();
//						// It's possible that we were waiting for this.user to
//						// be populated in order to post a
//						// status update.
//						handlePendingAction();
//					}
//				});
//
//		profilePictureView = (ProfilePictureView) findViewById(R.id.profilePictureView1);
//
//		final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(R.id.coverflowReflect);
//		setupCoverFlow(reflectingCoverFlow, false);
//	}
//
//	private void startTasks() {
//		new InitHomeVizTask((HomeVizApplication) getApplication())
//				.execute("HomeViz.xml");
//		new DownloadLoxoneXMLTask().execute(Constants.LOXONE_IP);
//	}
//
//	/**
//	 * Setup cover flow.
//	 * 
//	 * @param mCoverFlow
//	 *            the m cover flow
//	 * @param reflect
//	 *            the reflect
//	 */
//	private void setupCoverFlow(final CoverFlow mCoverFlow,
//			final boolean reflect) {
//		BaseAdapter coverImageAdapter;
//		if (reflect) {
//			coverImageAdapter = new ReflectingImageAdapter(
//					new ResourceImageAdapter(this));
//		} else {
//			coverImageAdapter = new ResourceImageAdapter(this);
//		}
//		mCoverFlow.setAdapter(coverImageAdapter);
//		mCoverFlow.setSelection(2, true);
//		setupListeners(mCoverFlow);
//	}
//
//	/**
//	 * Sets the up listeners.
//	 * 
//	 * @param mCoverFlow
//	 *            the new up listeners
//	 */
//	private void setupListeners(final CoverFlow mCoverFlow) {
//		mCoverFlow.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(final AdapterView<?> parent,
//					final View view, final int position, final long id) {
//				// textView.setText("Item clicked! : " + id);
//			}
//
//		});
//		mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(final AdapterView<?> parent,
//					final View view, final int position, final long id) {
//				// textView.setText("Item selected! : " + id);
//			}
//
//			@Override
//			public void onNothingSelected(final AdapterView<?> parent) {
//				// textView.setText("Nothing clicked!");
//			}
//		});
//	}
//
//	private void updateUI() {
//		Session session = Session.getActiveSession();
//		boolean enableButtons = (session != null && session.isOpened());
//
//		if (enableButtons && user != null) {
//			profilePictureView.setProfileId(user.getId());
//			System.out.println("Facebookname: " + user.getFirstName());
//		} else {
//			profilePictureView.setProfileId(null);
//		}
//	}
//
//	@SuppressWarnings("incomplete-switch")
//	private void handlePendingAction() {
//		PendingAction previouslyPendingAction = pendingAction;
//		// These actions may re-set pendingAction if they are still pending, but
//		// we assume they
//		// will succeed.
//		pendingAction = PendingAction.NONE;
//
//		switch (previouslyPendingAction) {
//		case POST_PHOTO:
//			// postPhoto();
//			break;
//		case POST_STATUS_UPDATE:
//			// postStatusUpdate();
//			break;
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		Log.i("HomeScreen", "HomeScreen got a result!");
//		if (resultCode != RESULT_OK) {
//			Log.e("HomeScreen", "Result not oke!");
//			return;
//		}
//
//		if (requestCode == PICKFILE_RESULT_CODE) {
//			((HomeVizApplication) getApplication()).parseXML(data.getData()
//					.getPath());
//		}
//		uiHelper.onActivityResult(requestCode, resultCode, data);
//	}
//
//	// ////////////////////////////////////////////////////
//	// / Locatie ///
//	// ////////////////////////////////////////////////////
//
//	/**
//	 * Deze methode initialiseert het opzoeken van de locatie.
//	 * 
//	 * De primaire methode is het bepalen van de locatie via Netwerkgegevens.
//	 * Als dit niet lukt, wordt er gebruikgemaakt van de GPS (als deze
//	 * aanstaat). Is er geen internetverbinding en staat de GPS af, dan wordt de
//	 * locatie niet opgezocht.
//	 */
//	private void initCurrentLocation() {
//		LocationManager locationManager;
//		String provider;
//		try {
//			String svcName = Context.LOCATION_SERVICE;
//			locationManager = (LocationManager) getSystemService(svcName);
//
//			provider = null;
//			boolean gpsEnabled = locationManager
//					.isProviderEnabled(LocationManager.GPS_PROVIDER);
//			boolean internetEnabled = isNetworkConnected();
//
//			if (internetEnabled) {
//				Log.i("Homescreen", "Tracking location through internet");
//				Criteria criteria = new Criteria();
//				criteria.setAccuracy(Criteria.ACCURACY_FINE);
//				criteria.setPowerRequirement(Criteria.POWER_LOW);
//				criteria.setAltitudeRequired(false);
//				criteria.setBearingRequired(false);
//				criteria.setSpeedRequired(false);
//				criteria.setCostAllowed(true);
//				provider = locationManager.getBestProvider(criteria, true);
//				locationManager.requestSingleUpdate(provider, this, null);
//			} else if (gpsEnabled) {
//				Log.i("Homescreen", "Tracking location through GPS");
//				provider = LocationManager.GPS_PROVIDER;
//				locationManager.requestSingleUpdate(provider, this, null);
//			} else {
//				ToastMessages.noLocationResource();
//			}
//		} catch (Exception e) {
//			Log.i("Homescreen", "locationServices are disabled");
//			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					switch (which) {
//					case DialogInterface.BUTTON_POSITIVE:
//						ToastMessages.enableLocation();
//						Intent myIntent = new Intent(
//								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//						HomeScreen.this.startActivityForResult(myIntent,
//								LOCATION_SETTINGS);
//						break;
//
//					case DialogInterface.BUTTON_NEGATIVE:
//						// No button clicked
//						break;
//					}
//				}
//			};
//
//			AlertDialog.Builder builder = new AlertDialog.Builder(
//					HomeScreen.this);
//			builder.setMessage(
//					getResources().getString(R.string.question_enable_location))
//					.setPositiveButton(getResources().getString(R.string.Yes),
//							dialogClickListener)
//					.setNegativeButton(getResources().getString(R.string.No),
//							dialogClickListener).show();
//		}
//	}
//
//	/**
//	 * Methode die nagaat of de gebruiker met internet verbonden is.
//	 * 
//	 * @return True als de gebruiker met internet verbonden is.
//	 */
//	private boolean isNetworkConnected() {
//		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//		if (networkInfo != null) {
//			// Geen actieve netwerken
//			return true;
//		}
//
//		return false;
//	}
//
//	/**
//	 * Methode die de currentCity en currentCountry variable invult, gebaseerd
//	 * op het meegegeven Location object.
//	 * 
//	 * @param currentLocation
//	 *            De huidige locatie.
//	 */
//	private void setLocation(Location currentLocation) {
//		double lat = currentLocation.getLatitude();
//		double lng = currentLocation.getLongitude();
//
//		String currentCity = null;
//		String currentCountry = null;
//
//		Geocoder geocoder = new Geocoder(getApplicationContext(),
//				Locale.getDefault());
//		List<Address> addresses;
//		try {
//			if (isNetworkConnected()) {
//				addresses = geocoder.getFromLocation(lat, lng, 1);
//				Address first = addresses.get(0);
//				currentCity = first.getLocality();
//				currentCountry = first.getCountryName();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			currentCity = getResources().getString(R.string.no_location);
//			currentCountry = getResources().getString(R.string.no_location);
//		}
//
//		// ((HomeVizApplication) getApplication()).setCurrentCity(currentCity);
//		((HomeVizApplication) getApplication())
//				.setCurrentCountry(currentCountry);
//		Log.i("Homescreen", "Updated location, we are in: " + currentCity
//				+ ", " + currentCountry);
//	}
//
//	@Override
//	public void onLocationChanged(Location location) {
//		setLocation(location);
//	}
//
//	@Override
//	public void onProviderDisabled(String provider) {
//	}
//
//	@Override
//	public void onProviderEnabled(String provider) {
//		Log.i("HomeScreen", "Tracking location through: " + provider);
//		initCurrentLocation();
//	}
//
//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//	}
//}
