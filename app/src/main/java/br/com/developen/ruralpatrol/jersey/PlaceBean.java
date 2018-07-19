package br.com.developen.ruralpatrol.jersey;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public class PlaceBean {

    private Integer identifier;

    private String denomination;

    private Integer type;

    @JsonDeserialize(using=NumericBooleanDeserializer.class)
    private Boolean checked;

    private String street;

    private String number;

    private String district;

    private String city;

    private Double latitude;

    private Double longitude;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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