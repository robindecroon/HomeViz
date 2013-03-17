package robindecroon.homeviz.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.util.Log;
import android.util.Xml;

public class LoxoneXMLParser extends XMLParser {

	// We don't use namespaces
	private static final String ns = null;

	private static final String STATISTICS = "Statistics";
	private static final String DATE_TAG = "S";
	private static final String DATE_ATTRIBUTE = "T";
	private static final String NAME_ATTRIBUTE = "Name";
	private static final String OUTPUTS = "Outputs";
	
	private String name;
	private String output;

	public List<Entry> parse(InputStream in) throws XmlPullParserException,
			IOException {
		Log.i(getClass().getSimpleName(), "Parsing Loxone XML");
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readStatistics(parser);

		} finally {
			in.close();
		}
	}

	private List<Entry> readStatistics(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		List<Entry> entries = new ArrayList<Entry>();

		parser.require(XmlPullParser.START_TAG, ns, STATISTICS);
		
		name = parser.getAttributeValue(null,NAME_ATTRIBUTE);
		output = parser.getAttributeValue(null, OUTPUTS);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals(DATE_TAG)) {
				entries.add(readS(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	@SuppressLint("SimpleDateFormat")
	private Entry readS(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, DATE_TAG);

		String time = parser.getAttributeValue(null, DATE_ATTRIBUTE);
		String valueString = parser.getAttributeValue(null, output);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		long date = 0;
		try {
			date = df.parse(time).getTime();
		} catch (ParseException e) {
			Log.e(getClass().getSimpleName(), "Parsen datum mislukt");
		}
		boolean value = valueString.equals("1.000");

		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, DATE_TAG);
		Entry entry = new Entry(date, value, name, output);
		return entry;
	}
}
