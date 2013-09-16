package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.agric.oxm.model.util.MyDateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "season")
public class Season extends BaseData implements Comparable<Season> {

	private String name, weatherDescription;

	private Date startDate;

	private Date endDate;

	private Weather weather;

	private List<Document> documents = Collections.emptyList();

	public Season() {

	}

	@Column(name = "name", nullable = true)
	public String getName() {
		if (StringUtils.isBlank(this.name))
			return MyDateUtil.getDateRangeString(this.getStartDate(),
					this.getEndDate());
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	// ========================================================================

	@Column(name = "weather_description", nullable = true, length = 32768)
	public String getWeatherDescription() {
		return weatherDescription;
	}

	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "weather", nullable = true)
	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	// ========================================================================

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	@OneToMany(mappedBy = "season", cascade = { CascadeType.ALL }, orphanRemoval = false, fetch = FetchType.LAZY)
	@Fetch(FetchMode.SUBSELECT)
	public List<Document> getDocuments() {
		return this.documents;
	}

	public void addDocument(Document doc) {
		if (doc == null) {
			return;
		}

		if (this.getDocuments() == null) {
			this.setDocuments(new ArrayList<Document>());
		}

		this.getDocuments().add(doc);
	}

	public void removeDocument(Document doc) {
		if (doc == null || this.getDocuments() == null) {
			return;
		}

		this.getDocuments().remove(doc);
	}

	public void removeDocumentsByIds(String[] idzToDelete) {
		for (String id : idzToDelete) {
			Document d = new Document(id);

			if (this.getDocuments().contains(d)) {
				getDocuments().remove(d);
			}
		}
	}

	// ================================================

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Season other = (Season) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Season o) {
		return this.getStartDate().compareTo(o.getStartDate());
	}

	// ========================================================================

	public enum Weather {

		RAINY("Rainy"), SUNNY("Sunny"), WINDY("Windy"), SEVERE_WEATHER(
				"Severe-Weather");

		/**
		 * constructor with initial specified value
		 * 
		 * @param name
		 */
		Weather(String name) {
			this.name = name;
		}

		private String name;

		/**
		 * gets the title of the enumerated value
		 * 
		 * @return
		 */
		public String getName() {
			return this.name;
		}

	};

}
