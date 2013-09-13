package org.agric.oxm.excelimport.model;

import java.util.List;

import org.agric.oxm.excelimport.StringUtil;

/**
 * A class that represents a column and particular strings required ion that column
 * @author Job
 *
 */
public class ColumnStrings {

    private int column;
    
    private List<String> strings;
    
    public ColumnStrings(int column, String strings, String separator){
	this.setColumn(column);
	this.setStrings(StringUtil.convertStringToList(strings, separator));
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

}
