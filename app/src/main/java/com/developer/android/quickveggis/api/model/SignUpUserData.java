package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 02/09/16.
 */
public class SignUpUserData {

    @SerializedName("address_1")
    private String address1;
    @SerializedName("address_2")
    private String address2;
    @SerializedName("city")
    private String city;
    @SerializedName("company_id")
    private String companyId;
    @SerializedName("company")
    private String company;
    @SerializedName("country_id")
    private String countryId;
    @SerializedName("email")
    private String email;
    @SerializedName("fax")
    private String fax;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("postcode")
    private String postcode;
    @SerializedName("tax_id")
    private String taxId;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("zone_id")
    private String zoneId;
    @SerializedName("password")
    private String password;
    @SerializedName("confirm")
    private String confirm;
    @SerializedName("agree")
    private String agree;

    public SignUpUserData(String address1, String address2, String city, String companyId, String company, String countryId, String email, String fax, String firstname, String lastname, String postcode, String taxId, String telephone, String zoneId, String password, String confirm, String agree) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.companyId = companyId;
        this.company = company;
        this.countryId = countryId;
        this.email = email;
        this.fax = fax;
        this.firstname = firstname;
        this.lastname = lastname;
        this.postcode = postcode;
        this.taxId = taxId;
        this.telephone = telephone;
        this.zoneId = zoneId;
        this.password = password;
        this.confirm = confirm;
        this.agree = agree;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }
}
