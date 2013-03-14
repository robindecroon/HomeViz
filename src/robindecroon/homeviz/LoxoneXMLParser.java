package robindecroon.homeviz;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class LoxoneXMLParser {

	// We don't use namespaces
	private static final String ns = null;

	public List<Entry> parse(InputStream in) throws XmlPullParserException,
			IOException {
		Log.i(getClass().getSimpleName(), "Started Parsing");
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

		parser.require(XmlPullParser.START_TAG, ns, "Statistics");
		String nameAtt = parser.getAttributeValue(null, "Name");
		System.out.println(nameAtt);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			// Starts by looking for the entry tag
			if (name.equals("S")) {
				entries.add(readS(parser));
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	// Processes link tags in the feed.
	private Entry readS(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, "S");

		String time = parser.getAttributeValue(null, "T");
		String valueString = parser.getAttributeValue(null, "Q");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
		long date = 0;
		try {
			date = df.parse(time).getTime();
		} catch (ParseException e) {
			Log.e("LoxoneXMLParser", "Parsen datum mislukt");
		}
		boolean value = valueString.equals("1.000");

		
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, ns, "S");
		Entry entry = new Entry(date, value);
		return entry;
	}

	// Skips tags the parser isn't interested in. Uses depth to handle nested
	// tags. i.e.,
	// if the next tag after a START_TAG isn't a matching END_TAG, it keeps
	// going until it
	// finds the matching END_TAG (as indicated by the value of "depth" being
	// 0).
	private void skip(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
