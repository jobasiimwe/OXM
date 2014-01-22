package org.agric.oxm.model.feed;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

public class RSSFeedParser {

	// title link description;
	// language copyright
	static final String CHANNEL = "channel";
	static final String TITLE = "title";
	static final String LINK = "link";
	static final String DESCRIPTION = "description";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";

	// market product unit;
	// dateStr retailStr wholeSaleStr;
	static final String ITEM = "item";
	static final String MARKET = "market";
	static final String PRODUCT = "product";
	static final String UNIT = "unit";
	static final String DATE = "date";
	static final String RETAIL_PRICE = "retailPrice";
	static final String WHOLESALE_PRICE = "wholesalePrice";

	final URL url;

	public RSSFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public Feed readFeed() {
		Feed feed = null;
		try {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string

			// title link description;
			// language copyright

			// market product unit;
			// dateStr retailStr wholeSaleStr;

			String title = "";
			String link = "";
			String description = "";
			String language = "";
			String copyright = "";

			String market = "";
			String product = "";
			String unit = "";
			String date = "";
			String retail = "";
			String wholesale = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = read();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

			System.out.println(in.toString());

			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName()
							.getLocalPart();

					printString(localPart + " : ", false);

					switch (localPart) {
					case ITEM:
						if (isFeedHeader) {
							isFeedHeader = false;
							feed = new Feed(title, link, description, language,
									copyright);
						}
						//event = eventReader.nextEvent();
						break;

					// title link description;
					// language copyright

					// market product unit;
					// dateStr retailStr wholeSaleStr;

					case TITLE:
						title = getCharacterData(event, eventReader);
						printString(title, true);
						break;
					case LINK:
						link = getCharacterData(event, eventReader);
						printString(link, true);
						break;
					case DESCRIPTION:
						description = getCharacterData(event, eventReader);
						printString(description, true);
						break;
					case LANGUAGE:
						language = getCharacterData(event, eventReader);
						printString(language, true);
						break;
					case COPYRIGHT:
						copyright = getCharacterData(event, eventReader);
						printString(copyright, true);
						break;
					case MARKET:
						market = getCharacterData(event, eventReader);
						printString(market + " -- ", false);
						break;
					case PRODUCT:
						product = getCharacterData(event, eventReader);
						printString(product + " -- ", false);
						break;
					case UNIT:
						unit = getCharacterData(event, eventReader);
						printString(unit + " -- ", false);
						break;
					case DATE:
						date = getCharacterData(event, eventReader);
						printString(date + " -- ", false);
						break;
					case RETAIL_PRICE:
						retail = getCharacterData(event, eventReader);
						printString(retail + " -- ", false);
						break;
					case WHOLESALE_PRICE:
						wholesale = getCharacterData(event, eventReader);
						printString(wholesale + " -- ", false);
						break;

					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
						// market product unit;
						// dateStr retailStr wholeSaleStr;
						FeedPrice feedPrice = new FeedPrice();
						feedPrice.setMarket(market);
						feedPrice.setProduct(product);
						feedPrice.setUnit(unit);
						feedPrice.setDateStr(date);
						feedPrice.setRetailStr(retail);
						feedPrice.setWholeSaleStr(wholesale);

						feed.getPrices().add(feedPrice);

						printString("\n =================== ", true);

						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}

	private void printString(String value, Boolean printNewLine) {
		if (printNewLine)
			System.out.println(value);
		else
			System.out.print(value);

	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}