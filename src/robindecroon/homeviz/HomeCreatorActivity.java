package robindecroon.homeviz;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.text.WordUtils;

import robindecroon.homeviz.room.Appliance;
import robindecroon.homeviz.room.HomeCinema;
import robindecroon.homeviz.room.Light;
import robindecroon.homeviz.room.Room;
import robindecroon.homeviz.room.Water;
import robindecroon.homeviz.util.ToastMessages;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class HomeCreatorActivity extends Activity {

	private int lightCounter = 1;
	private int appliancesCounter = 0;
	private int multimediaCounter = 0;
	private int waterCounter = 1;

	private NumberPicker lightsPicker;
	private NumberPicker appliancesPicker;
	private NumberPicker multimediaPicker;
	private NumberPicker waterPicker;

	private HomeVizApplication app;

	private List<Room> backup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_creator_layout);
		// Show the Up button in the action bar.
		setupActionBar();

		app = (HomeVizApplication) getApplication();
		backup = app.getRooms();

		refreshRooms();

		lightsPicker = (NumberPicker) findViewById(R.id.home_creator_light_picker);
		lightsPicker.setMaxValue(Constants.MAX_NB_CONSUMERS);
		lightsPicker.setMinValue(0);

		appliancesPicker = (NumberPicker) findViewById(R.id.home_creator_appliances_picker);
		appliancesPicker.setMaxValue(Constants.MAX_NB_CONSUMERS);
		appliancesPicker.setMinValue(0);

		multimediaPicker = (NumberPicker) findViewById(R.id.home_creator_multimedia_picker);
		multimediaPicker.setMaxValue(Constants.MAX_NB_CONSUMERS);
		multimediaPicker.setMinValue(0);

		waterPicker = (NumberPicker) findViewById(R.id.home_creator_water_picker);
		waterPicker.setMaxValue(Constants.MAX_NB_CONSUMERS);
		waterPicker.setMinValue(0);

		Button clearButton = (Button) findViewById(R.id.home_creator_clear_button);
		clearButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							app.reset();
							refreshRooms();
							break;
						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(
						HomeCreatorActivity.this);
				String message = getResources().getString(
						R.string.clear_rooms_message);
				String yes = getResources().getString(R.string.Yes);
				String no = getResources().getString(R.string.No);
				builder.setMessage(message)
						.setPositiveButton(yes, dialogClickListener)
						.setNegativeButton(no, dialogClickListener).show();
			}
		});

		Button doneButton = (Button) findViewById(R.id.home_creator_done_button);
		doneButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (app.getRooms().size() == 0) {
					for (Room room : backup) {
						app.addRoom(room);
					}
					onBackPressed();
				} else {
					StringBuilder xmlFileContent = new StringBuilder(
							"<?xml version=\"1.0\" encoding=\"utf-8\"?><HomeViz>");
					for (Room room : app.getRooms()) {
						xmlFileContent.append(room.toXML());
					}
					xmlFileContent.append("</HomeViz>");

					String FILENAME = "PersonalXML.xml";

					try {
						FileOutputStream fos = openFileOutput(FILENAME,
								Context.MODE_PRIVATE);
						fos.write(xmlFileContent.toString().getBytes());
						fos.close();

						SharedPreferences settings = PreferenceManager
								.getDefaultSharedPreferences(HomeCreatorActivity.this);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString(Constants.XML_FILE, FILENAME);
						editor.commit();

						Intent intent = new Intent(HomeCreatorActivity.this,
								Main.class);
						intent.putExtra(Constants.CATEGORY, Main.lastCatergory);
						intent.putExtra(Constants.SELECTION, Main.lastPosition);
						startActivity(intent);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// onBackPressed();
			}
		});

		Button roomButton = (Button) findViewById(R.id.home_creator_add_room_button);
		roomButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				EditText edit = (EditText) findViewById(R.id.home_creator_new_room_name);
				String roomName = WordUtils.capitalizeFully(edit.getText()
						.toString());

				if (roomName.equals("")) {
					ToastMessages.enterRoomName();
				} else {
					Room room = new Room(roomName);

					int nbLights = lightsPicker.getValue();
					for (int l = 0; l < nbLights; l++) {
						String name = getNextLight();
						room.addLight(new Light(name, 50,
								getApplicationContext()));
					}

					int nbAppliances = appliancesPicker.getValue();
					for (int l = 0; l < nbAppliances; l++) {
						String name = getNextApplianceName();
						room.addAppliance(new Appliance(name, 50,
								getApplicationContext()));
					}

					int nbMultimedias = multimediaPicker.getValue();
					for (int l = 0; l < nbMultimedias; l++) {
						String name = getNextMultimedia();
						room.addHomeCinema(new HomeCinema(name, 50,
								getApplicationContext()));
					}

					int nbWaters = waterPicker.getValue();
					for (int l = 0; l < nbWaters; l++) {
						String name = getNextWater();
						room.addWater(new Water(name, getApplicationContext()));
					}

					app.addRoom(room);

					clearEdit(edit);

					refreshRooms();
				}
			}
		});
	}

	protected void refreshRooms() {
		TextView roomsText = (TextView) findViewById(R.id.home_creator_rooms);
		StringBuilder text = new StringBuilder();
		for (Room room : app.getRooms()) {
			text.append("- " + room.getName());
			text.append("\n");
		}
		roomsText.setText(text);
	}

	private String getNextLight() {
		if (lightCounter > 19) {
			lightCounter = 1;
		}
		String light = "l" + lightCounter;
		lightCounter++;
		return light;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		safetyCheck();
		super.onBackPressed();
	}

	private void safetyCheck() {
		if (app.getRooms().size() == 0) {
			for (Room room : backup) {
				app.addRoom(room);
			}
		}
	}

	protected String getNextWater() {
		if (waterCounter > 7) {
			waterCounter = 1;
		}
		String crane = "kraan" + waterCounter;
		waterCounter++;
		return crane;
	}

	protected String getNextMultimedia() {
		String[] mumes = Constants.MULTIMEDIAS;
		if (multimediaCounter >= mumes.length) {
			multimediaCounter = 0;
		}
		String mumeName = mumes[multimediaCounter];
		multimediaCounter++;
		return mumeName;
	}

	protected String getNextApplianceName() {
		String[] appls = Constants.APPLIANCES;
		if (appliancesCounter >= appls.length) {
			appliancesCounter = 0;
		}
		String applName = appls[appliancesCounter];
		multimediaCounter++;
		return applName;
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void clearEdit(EditText edit) {
		lightsPicker.setValue(0);
		appliancesPicker.setValue(0);
		multimediaPicker.setValue(0);
		waterPicker.setValue(0);

		edit.setText("");
	}
}
