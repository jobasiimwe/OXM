package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "concept_categories")
public class ConceptCategory extends BaseData implements Comparable<ConceptCategory> {

    private String name;

    private String description;

    private String code;

    public ConceptCategory() {
	super();
    }

    public ConceptCategory(String conceptCategoryName,
	    String conceptCategoryDescription, String conceptCategoryCode) {
	super();
	this.name = conceptCategoryName;
	this.description = conceptCategoryDescription;
	this.code = conceptCategoryCode;
    }

    @Column(name = "category_name", nullable = false)
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Column(name = "category_description", nullable = false)
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Column(name = "concept_category_code", nullable = true, unique = true)
    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

    @Override
    public int compareTo(ConceptCategory o) {
	return this.getName().compareToIgnoreCase(o.getName());
    }

}
