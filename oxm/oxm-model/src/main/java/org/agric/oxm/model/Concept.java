package org.agric.oxm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "concepts")
public class Concept extends BaseData implements Comparable<Concept> {

	private String name;

	private List<ConceptCategory> categories;

	private String description;

	public Concept() {
		super();
	}

	public Concept(String conceptName, List<ConceptCategory> conceptCategories,
			String conceptDescription) {
		super();
		this.name = conceptName;
		this.categories = conceptCategories;
		this.description = conceptDescription;
	}

	@Column(name = "concept_name", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(uniqueConstraints = @UniqueConstraint(columnNames = {
			"concept_id", "concept_category_id" }), name = "concept_category_link", joinColumns = @JoinColumn(name = "concept_id", unique = false), inverseJoinColumns = @JoinColumn(name = "concept_category_id", unique = false))
	public List<ConceptCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ConceptCategory> categories) {
		this.categories = categories;
	}

	public void addCategory(ConceptCategory category) {
		if (category == null) {
			return;
		}

		if (getCategories() == null) {
			setCategories(new ArrayList<ConceptCategory>());
		}

		getCategories().add(category);
	}

	public void removeCategory(ConceptCategory category) {
		if (category == null || getCategories() == null) {
			return;
		}

		getCategories().remove(category);
	}

	@Column(name = "concept_description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean belongsTo(ConceptCategory conceptCategory) {
		if (this.getCategories() != null) {
			for (ConceptCategory category : getCategories()) {
				if (category.getId().equalsIgnoreCase(conceptCategory.getId()))
					return true;
			}
		}
		return false;
	}

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
		Concept other = (Concept) obj;
		if (super.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!super.getId().equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public int compareTo(Concept o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
}
