package org.agric.oxm.excelimport.model;

import java.util.List;

import org.agric.oxm.excelimport.StringUtil;




/**
 * represents an excel column
 * 
 * @author Job
 * 
 */
public class Column {

    private int index;

    private String name;

    private String description;

    private List<Value> alloudValues;

    /**
     * is always a ApachePoi cell content type
     */
    private Integer contentType;

    private boolean isNull;

    private boolean isBlank;

    private boolean isUnique;

    public Column(int index, String name, String description, List<Value> values, int contentType,
	    boolean isNull,
	    boolean isBlank, boolean isUnique) {
	this.setIndex(index);
	this.setName(name);
	this.setDescription(description);
	this.setAlloudValues(values);
	this.setContentType(contentType);
	this.setNull(isNull);
	this.setBlank(isBlank);
	this.setUnique(isUnique);
    }

    public Column(String name, List<Value> values, int contentType,
	    boolean isNull,
	    boolean isBlank, boolean isUnique) {
	this.setIndex(index);
	this.setName(name);
	this.setAlloudValues(values);
	this.setContentType(contentType);
	this.setNull(isNull);
	this.setBlank(isBlank);
	this.setUnique(isUnique);
    }

    public Column(int index, String longStringofValues, String separator) {
	this.setIndex(index);
	this
		.setAlloudValues(StringUtil.convertStringToValues(index, longStringofValues,
			separator));
    }

    public Column(){
	
    }
    
    public int getIndex() {
	return index;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Value> getAlloudValues() {
	return alloudValues;
    }

    public void setAlloudValues(List<Value> alloudValues) {
	this.alloudValues = alloudValues;
    }

    public Integer getContentType() {
	return contentType;
    }

    public void setContentType(Integer contentType) {
	this.contentType = contentType;
    }

    public boolean isNull() {
	return isNull;
    }

    public void setNull(boolean isNull) {
	this.isNull = isNull;
    }

    public boolean isBlank() {
	return isBlank;
    }

    public void setBlank(boolean isBlank) {
	this.isBlank = isBlank;
    }

    public boolean isUnique() {
	return isUnique;
    }

    public void setUnique(boolean isUnique) {
	this.isUnique = isUnique;
    }

}
