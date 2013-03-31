package robindecroon.homeviz.xml;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import robindecroon.homeviz.Constants;
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

	public XMLReturnObject parse(InputStream stream)
			throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(stream, null);
			parser.nextTag();
			List<IEntry> entries = readStatistics(parser);
			return new XMLReturnObject(name, entries);

		} finally {
			stream.close();
		}
	}

	@SuppressLint("DefaultLocale")
	private List<IEntry> readStatistics(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		List<IEntry> entries = new ArrayList<IEntry>();

		parser.require(XmlPullParser.START_TAG, ns, STATISTICS);

		String tempName = parser.getAttributeValue(null, NAME_ATTRIBUTE);
		name = tempName.toLowerCase().replaceAll(" ", "").replace("-", "")
				.replace(">", "");
		output = parser.getAttributeValue(null, OUTPUTS);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(DATE_TAG)) {
				IEntry entry = readS(parser);
				if (entry != null) {
					entries.add(entry);
				}
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	@SuppressLint("SimpleDateFormat")
	private IEntry readS(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, DATE_TAG);

		String time = parser.getAttributeValue(null, DATE_ATTRIBUTE);
		String valueString = parser.getAttributeValue(null, output);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long date = 0;
		try {
			date = df.parse(time).getTime();
			if (date < Constants.CURRENT_TIME) {
				parser.nextTag();
				parser.require(XmlPullParser.END_TAG, ns, DATE_TAG);
				return null;
			}
		} catch (ParseException e) {
			Log.e(getClass().getSimpleName(),
					"Parsing date failed: " + e.getMessage());
		}
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, DATE_TAG);
		// IEntry entry = null;
		// if (output.equals("Q")) {
		// entry = new PressureEntry(date, );
		// } else {
		return new Entry(date, valueString, output);
		// }
		// return entry;
	}
}
