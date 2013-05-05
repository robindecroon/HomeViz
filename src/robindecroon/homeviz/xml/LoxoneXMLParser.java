/* Copyright (C) Robin De Croon - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Robin De Croon <robindecroon@msn.com>, May 2013
 */
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

/**
 * The Class LoxoneXMLParser.
 */
public class LoxoneXMLParser extends XMLParser {

	/** The Constant ns. */
	private static final String ns = null;

	/** The Constant STATISTICS. */
	private static final String STATISTICS = "Statistics";
	
	/** The Constant DATE_TAG. */
	private static final String DATE_TAG = "S";
	
	/** The Constant DATE_ATTRIBUTE. */
	private static final String DATE_ATTRIBUTE = "T";
	
	/** The Constant NAME_ATTRIBUTE. */
	private static final String NAME_ATTRIBUTE = "Name";
	
	/** The Constant OUTPUTS. */
	private static final String OUTPUTS = "Outputs";
	
	/** The Constant NB_OF_OUTPUTS. */
	private static final String NB_OF_OUTPUTS = "NumOutputs";

	/** The name. */
	private String name;
	
	/** The output. */
	private String output;
	
	/** The nb outputs. */
	private int nbOutputs;
	
	/** The outputs. */
	private String[] outputs;

	/**
	 * Parses the.
	 *
	 * @param stream the stream
	 * @return the xML return object
	 * @throws XmlPullParserException the xml pull parser exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public XMLReturnObject parse(InputStream stream)
			throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(stream, null);
			parser.nextTag();
			List<Entry> entries = readStatistics(parser);
			return new XMLReturnObject(name, entries, nbOutputs);
		} finally {
			stream.close();
		}
	}

	/**
	 * Read statistics.
	 *
	 * @param parser the parser
	 * @return the list
	 * @throws XmlPullParserException the xml pull parser exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@SuppressLint("DefaultLocale")
	private List<Entry> readStatistics(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		List<Entry> entries = new ArrayList<Entry>();

		parser.require(XmlPullParser.START_TAG, ns, STATISTICS);

		String tempName = parser.getAttributeValue(null, NAME_ATTRIBUTE);
		// Filter invalid names
		name = tempName.toLowerCase().replaceAll(" ", "").replace("-", "").replace(">", "");
		output = parser.getAttributeValue(null, OUTPUTS);

		try {
			nbOutputs = Integer.valueOf(parser.getAttributeValue(null,
					NB_OF_OUTPUTS));
			if (nbOutputs > 1) {
				outputs = output.split(",");
			}
		} catch (NumberFormatException e) {
			Log.e(getClass().getSimpleName(), name + " has no outnumber!");
		}

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals(DATE_TAG)) {
				Entry entry = readS(parser);
				if (entry != null) {
					entries.add(entry);
				}
			} else {
				skip(parser);
			}
		}
		return entries;
	}

	/**
	 * Read s.
	 *
	 * @param parser the parser
	 * @return the entry
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws XmlPullParserException the xml pull parser exception
	 */
	@SuppressLint("SimpleDateFormat")
	private Entry readS(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, DATE_TAG);

		String time = parser.getAttributeValue(null, DATE_ATTRIBUTE);

		String valueString = parser.getAttributeValue(null, output);
		if (outputs != null) {
			for (String out : outputs) {
				String newString = parser.getAttributeValue(null, out);
				if (newString != null) {
					valueString = newString;
					output = out;
				}
			}
		}

		SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT);
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
		return new Entry(date, valueString, output);
	}
}
