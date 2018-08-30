package br.com.developen.ruralpatrol.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "Place")
public class PlaceVO implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "identifier")
    private Integer identifier;

    @ColumnInfo(name = "denomination", collate = ColumnInfo.NOCASE)
    private String denomination;

    @ColumnInfo(name = "type")
    private Integer type;

    @ColumnInfo(name = "checked")
    private Boolean checked;

    @ColumnInfo(name = "street")
    private String street;

    @ColumnInfo(name = "number")
    private String number;

    @ColumnInfo(name = "district")
    private String district;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "latitude")
    private Double latitude;

    @ColumnInfo(name = "longitude")
    private Double longitude;

    @TypeConverters({TimestampConverter.class})
    @ColumnInfo(name = "since")
    private Date since;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getDenomination() {

        return denomination;

    }

    public void setDenomination(String denomination) {

        this.denomination = denomination;

    }

    public Integer getType() {

        return type;

    }

    public void setType(Integer type) {

        this.type = type;

    }

    public Boolean getChecked() {

        return checked;

    }

    public void setChecked(Boolean checked) {

        this.checked = checked;

    }

    public String getStreet() {

        return street;

    }

    public void setStreet(String street) {

        this.street = street;

    }

    public String getNumber() {

        return number;

    }

    public void setNumber(String number) {

        this.number = number;

    }

    public String getDistrict() {

        return district;

    }

    public void setDistrict(String district) {

        this.district = district;

    }

    public String getCity() {

        return city;

    }

    public void setCity(String city) {

        this.city = city;

    }

    public Double getLatitude() {

        return latitude;

    }

    public void setLatitude(Double latitude) {

        this.latitude = latitude;

    }

    public Double getLongitude() {

        return longitude;

    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;

    }

    public Date getSince() {

        return since;

    }

    public void setSince(Date since) {

        this.since = since;

    }

}