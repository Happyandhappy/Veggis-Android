package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 31/08/16.
 */
public class Country {

    @SerializedName("country_id")
    private String countryId;
    @SerializedName("name")
    private String name;
    @SerializedName("iso_code_2")
    private String isoCode2;
    @SerializedName("iso_code_3")
    private String isoCode3;
    @SerializedName("address_format")
    private String addressFormat;
    @SerializedName("postcode_required")
    private String postcodeRequired;
    @SerializedName("status")
    private String status;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode2() {
        return isoCode2;
    }

    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    public String getIsoCode3() {
        return isoCode3;
    }

    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    public String getAddressFormat() {
        return addressFormat;
    }

    public void setAddressFormat(String addressFormat) {
        this.addressFormat = addressFormat;
    }

    public String getPostcodeRequired() {
        return postcodeRequired;
    }

    public void setPostcodeRequired(String postcodeRequired) {
        this.postcodeRequired = postcodeRequired;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
