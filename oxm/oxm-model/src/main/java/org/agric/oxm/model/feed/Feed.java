package org.agric.oxm.model.feed;

import java.util.ArrayList;
import java.util.List;

public class Feed {

	// <title>Latest Simsim Prices as at Jan 20, 2014</title>
	// <link>http://infotradeuganda.com/feed/oxfam</link>
	// <description>The latest retail and wholesale prices for simsim from
	// uganda markets collected by InfoTrade</description>
	// <language>en-us</language>
	// <copyright>Copyright (C) 2010 infotradeuganda.com</copyright>

	final String title;
	final String link;
	final String description;
	final String language;
	final String copyright;
	// final String pubDate;

	final List<FeedPrice> entries = new ArrayList<FeedPrice>();

	// public Feed(String title, String link, String description, String
	// language,
	// String copyright, String pubDate) {
	// this.title = title;
	// this.link = link;
	// this.description = description;
	// this.language = language;
	// this.copyright = copyright;
	// this.pubDate = pubDate;
	// }

	public Feed(String title, String link, String description, String language,
			String copyright) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
	}

	public List<FeedPrice> getPrices() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public String getCopyright() {
		return copyright;
	}

	// public String getPubDate() {
	// return pubDate;
	// }

	// @Override
	// public String toString() {
	// return "Feed [copyright=" + copyright + ", description=" + description
	// + ", language=" + language + ", link=" + link + ", pubDate="
	// + pubDate + ", title=" + title + "]";
	// }

	@Override
	public String toString() {
		return "Feed [copyright=" + copyright + ", description=" + description
				+ ", language=" + language + ", link=" + link + ", title="
				+ title + "]";
	}
}
