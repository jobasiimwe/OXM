package org.agric.oxm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "gis_cordinate")
public class GisCordinate extends BaseData {

    private String xLongitude;

    private String yLongitude;

    private String xLatitude;

    private String yLatitude;

    public GisCordinate() {
	super();
    }

    @Column(name = "x_longitude", nullable = false)
    public String getxLongitude() {
	return xLongitude;
    }

    public void setxLongitude(String xLongitude) {
	this.xLongitude = xLongitude;
    }

    @Column(name = "y_longitude", nullable = false)
    public String getyLongitude() {
	return yLongitude;
    }

    public void setyLongitude(String yLongitude) {
	this.yLongitude = yLongitude;
    }

    @Column(name = "x_latitude", nullable = false)
    public String getxLatitude() {
	return xLatitude;
    }

    public void setxLatitude(String xLatitude) {
	this.xLatitude = xLatitude;
    }

    @Column(name = "y_latitude", nullable = false)
    public String getyLatitude() {
	return yLatitude;
    }

    public void setyLatitude(String yLatitude) {
	this.yLatitude = yLatitude;
    }

}
