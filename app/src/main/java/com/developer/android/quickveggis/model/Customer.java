package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by devmac on 01/09/16.
 */
public class Customer {

    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("provider")
    private String provider;
    @SerializedName("customer_group_id")
    private String customerGroupId;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("profile_img")
    private String profileimg;
    @SerializedName("email")
    private String email;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("fax")
    private String fax;
    @SerializedName("salt")
    private String salt;
    @SerializedName("cart")
    private Object cart;
    @SerializedName("wishlist")
    private Object wishlist;
    @SerializedName("newsletter")
    private String newsletter;
    @SerializedName("address_id")
    private String addressId;
    @SerializedName("ip")
    private String ip;
    @SerializedName("status")
    private String status;
    @SerializedName("approved")
    private String approved;
    @SerializedName("safe")
    private String safe;
    @SerializedName("token")
    private String token;
    @SerializedName("code")
    private String code;
    @SerializedName("date_added")
    private String dateAdded;
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
    @SerializedName("session")
    private String session;
    @SerializedName("account_custom_field")
    private boolean accountCustomField;
    @SerializedName("custom_fields")
    private List<?> customFields;

    private Wallet wallet;

    public Customer() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Object getCart() {
        return cart;
    }

    public void setCart(Object cart) {
        this.cart = cart;
    }

    public Object getWishlist() {
        return wishlist;
    }

    public void setWishlist(Object wishlist) {
        this.wishlist = wishlist;
    }

    public String getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(String newsletter) {
        this.newsletter = newsletter;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getSafe() {
        return safe;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isAccountCustomField() {
        return accountCustomField;
    }

    public void setAccountCustomField(boolean accountCustomField) {
        this.accountCustomField = accountCustomField;
    }

    public List<?> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<?> customFields) {
        this.customFields = customFields;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
