package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 19/09/16.
 */
public class UpdateAccountData {

    public UpdateAccountData(String zipCode, String referalCode, String gender, String birthday, String shoppingForFamily,
                             String pleaseCheckAllTheApplay, String wahysYourShoppingStyle, String foodPreferancesAndAllergies, String wahtsYourEthinicity) {
        this.zipCode = zipCode;
        this.referalCode = referalCode;
        this.gender = gender;
        this.birthday = birthday;
        this.shoppingForFamily = shoppingForFamily;
        this.pleaseCheckAllTheApplay = pleaseCheckAllTheApplay;
        this.wahysYourShoppingStyle = wahysYourShoppingStyle;
        this.foodPreferancesAndAllergies = foodPreferancesAndAllergies;
        this.wahtsYourEthinicity = wahtsYourEthinicity;
    }

    @SerializedName("zip_code")
    private String zipCode;
    @SerializedName("referal_code")
    private String referalCode;
    @SerializedName("gender")
    private String gender;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("shopping_for_family")
    private String shoppingForFamily;
    @SerializedName("please_check_all_the_applay")
    private String pleaseCheckAllTheApplay;
    @SerializedName("wahys_your_shopping_style")
    private String wahysYourShoppingStyle;
    @SerializedName("food_preferances_and_allergies")
    private String foodPreferancesAndAllergies;
    @SerializedName("wahts_your_ethinicity")
    private String wahtsYourEthinicity;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getReferalCode() {
        return referalCode;
    }

    public void setReferalCode(String referalCode) {
        this.referalCode = referalCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getShoppingForFamily() {
        return shoppingForFamily;
    }

    public void setShoppingForFamily(String shoppingForFamily) {
        this.shoppingForFamily = shoppingForFamily;
    }

    public String getPleaseCheckAllTheApplay() {
        return pleaseCheckAllTheApplay;
    }

    public void setPleaseCheckAllTheApplay(String pleaseCheckAllTheApplay) {
        this.pleaseCheckAllTheApplay = pleaseCheckAllTheApplay;
    }

    public String getWahysYourShoppingStyle() {
        return wahysYourShoppingStyle;
    }

    public void setWahysYourShoppingStyle(String wahysYourShoppingStyle) {
        this.wahysYourShoppingStyle = wahysYourShoppingStyle;
    }

    public String getFoodPreferancesAndAllergies() {
        return foodPreferancesAndAllergies;
    }

    public void setFoodPreferancesAndAllergies(String foodPreferancesAndAllergies) {
        this.foodPreferancesAndAllergies = foodPreferancesAndAllergies;
    }

    public String getWahtsYourEthinicity() {
        return wahtsYourEthinicity;
    }

    public void setWahtsYourEthinicity(String wahtsYourEthinicity) {
        this.wahtsYourEthinicity = wahtsYourEthinicity;
    }
}
