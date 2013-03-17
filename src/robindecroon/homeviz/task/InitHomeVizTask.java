package robindecroon.homeviz.task;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.HomeVizApplication;
import robindecroon.homeviz.users.Person;
import robindecroon.homeviz.xml.HomeVizXMLParser;
import robindecroon.stackoverflow.RandomNumberGenerator;
import android.os.AsyncTask;
import android.util.Log;

public class InitHomeVizTask extends AsyncTask<String, Void, Boolean> {
	
	private static boolean done = false;

	private HomeVizApplication context;

	public InitHomeVizTask(HomeVizApplication context) {
		this.context = context;
	}

	@Override
	protected Boolean doInBackground(String... paths) {
		if(!done) {
			try {
				InputStream in = null;
				in = context.getAssets().open(paths[0]);
				HomeVizXMLParser parser = new HomeVizXMLParser(context);
				if (parser.parse(in)) {
					randomizeLocationsOfPersons();
					done = true;
					return true;
				} else {
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			}
		} else {
			return true;
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if(result) {
			Log.i(getClass().getSimpleName(),"HomeViz initialized successfully");			
		}
	}
	
	private void randomizeLocationsOfPersons() {
		for (Person person : context.getPersons()) {
			if (!person.equals(context.getCurrentUser())) {
				int[] percantages = RandomNumberGenerator.genNumbers(
						context.getRooms().size(), 100);
				for (int i = 0; i < percantages.length; i++) {
					context.getRooms().get(i).setPercentageForPerson(person, percantages[i]);
				}
			}
		}
	}

}
