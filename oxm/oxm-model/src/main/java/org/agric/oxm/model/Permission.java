package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission extends BaseData implements Comparable<Permission> {

    private String name;

    private String description;

    public Permission() {
	super();
    }

    public Permission(String name, String description) {
	super();
	this.name = name;
	this.description = description;
    }

    @Column(name = "permission_name")
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public int compareTo(Permission o) {

	return getObjectNameFromPermisionName(this).compareToIgnoreCase(
		getObjectNameFromPermisionName(o));
    }

    public static String getObjectNameFromPermisionName(Permission p) {
	String subString = "";
	if (p.getName().indexOf('_') != -1)
	    subString = p.getName().substring(p.getName().indexOf('_') + 1);
	else
	    return p.getName();

	if (subString.indexOf('_') != -1)
	    return subString.substring(subString.indexOf('_') + 1);
	else
	    return subString;
    }
}
