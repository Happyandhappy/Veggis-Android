package com.developer.android.quickveggis.api.model;

import com.developer.android.quickveggis.model.CartItem;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.model.OrderTotal;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by devmac on 23/09/16.
 */

public class OrderCreateData {

    @SerializedName("invoice_prefix")
    private String invoicePrefix;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("store_name")
    private String storeName;
    @SerializedName("store_url")
    private String storeUrl;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("customer_group_id")
    private String customerGroupId;
    @SerializedName("firstname")
    private String firstname;
    @SerializedName("lastname")
    private String lastname;
    @SerializedName("email")
    private String email;
    @SerializedName("telephone")
    private String telephone;
    @SerializedName("fax")
    private String fax;
    @SerializedName("custom_field")
    private Object customField;
    @SerializedName("payment_firstname")
    private String paymentFirstname;
    @SerializedName("payment_lastname")
    private String paymentLastname;
    @SerializedName("payment_company")
    private String paymentCompany;
    @SerializedName("payment_address_1")
    private String paymentAddress1;
    @SerializedName("payment_address_2")
    private String paymentAddress2;
    @SerializedName("payment_city")
    private String paymentCity;
    @SerializedName("payment_postcode")
    private String paymentPostcode;
    @SerializedName("payment_zone")
    private String paymentZone;
    @SerializedName("payment_zone_id")
    private String paymentZoneId;
    @SerializedName("payment_country")
    private String paymentCountry;
    @SerializedName("payment_country_id")
    private String paymentCountryId;
    @SerializedName("payment_address_format")
    private String paymentAddressFormat;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("payment_code")
    private String paymentCode;
    @SerializedName("shipping_firstname")
    private String shippingFirstname;
    @SerializedName("shipping_lastname")
    private String shippingLastname;
    @SerializedName("shipping_company")
    private String shippingCompany;
    @SerializedName("shipping_address_1")
    private String shippingAddress1;
    @SerializedName("shipping_address_2")
    private String shippingAddress2;
    @SerializedName("shipping_city")
    private String shippingCity;
    @SerializedName("shipping_postcode")
    private String shippingPostcode;
    @SerializedName("shipping_zone")
    private String shippingZone;
    @SerializedName("shipping_zone_id")
    private String shippingZoneId;
    @SerializedName("shipping_country")
    private String shippingCountry;
    @SerializedName("shipping_country_id")
    private String shippingCountryId;
    @SerializedName("shipping_address_format")
    private String shippingAddressFormat;
    @SerializedName("shipping_method")
    private String shippingMethod;
    @SerializedName("shipping_code")
    private String shippingCode;
    @SerializedName("comment")
    private String comment;
    @SerializedName("total")
    private int total;
    @SerializedName("affiliate_id")
    private int affiliateId;
    @SerializedName("commission")
    private int commission;
    @SerializedName("marketing_id")
    private int marketingId;
    @SerializedName("tracking")
    private String tracking;
    @SerializedName("language_id")
    private String languageId;
    @SerializedName("currency_id")
    private String currencyId;
    @SerializedName("currency_code")
    private String currencyCode;
    @SerializedName("currency_value")
    private String currencyValue;
    @SerializedName("ip")
    private String ip;
    @SerializedName("forwarded_ip")
    private String forwardedIp;
    @SerializedName("user_agent")
    private String userAgent;
    @SerializedName("accept_language")
    private String acceptLanguage;
    @SerializedName("totals")
    private ArrayList<OrderTotal> totals;
    @SerializedName("payment_custom_field")
    private ArrayList<?> paymentCustomField;
    @SerializedName("shipping_custom_field")
    private ArrayList<?> shippingCustomField;
    @SerializedName("products")
    private ArrayList<CartItem> products;
    @SerializedName("vouchers")
    private ArrayList<?> vouchers;

    public OrderCreateData() {
        totals = new ArrayList<>();
        paymentCustomField = new ArrayList<>();
        shippingCustomField= new ArrayList<>();
        products = new ArrayList<>();
        vouchers = new ArrayList<>();
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerGroupId() {
        return customerGroupId;
    }

    public void setCustomerGroupId(String customerGroupId) {
        this.customerGroupId = customerGroupId;
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

    public Object getCustomField() {
        return customField;
    }

    public void setCustomField(Object customField) {
        this.customField = customField;
    }

    public String getPaymentFirstname() {
        return paymentFirstname;
    }

    public void setPaymentFirstname(String paymentFirstname) {
        this.paymentFirstname = paymentFirstname;
    }

    public String getPaymentLastname() {
        return paymentLastname;
    }

    public void setPaymentLastname(String paymentLastname) {
        this.paymentLastname = paymentLastname;
    }

    public String getPaymentCompany() {
        return paymentCompany;
    }

    public void setPaymentCompany(String paymentCompany) {
        this.paymentCompany = paymentCompany;
    }

    public String getPaymentAddress1() {
        return paymentAddress1;
    }

    public void setPaymentAddress1(String paymentAddress1) {
        this.paymentAddress1 = paymentAddress1;
    }

    public String getPaymentAddress2() {
        return paymentAddress2;
    }

    public void setPaymentAddress2(String paymentAddress2) {
        this.paymentAddress2 = paymentAddress2;
    }

    public String getPaymentCity() {
        return paymentCity;
    }

    public void setPaymentCity(String paymentCity) {
        this.paymentCity = paymentCity;
    }

    public String getPaymentPostcode() {
        return paymentPostcode;
    }

    public void setPaymentPostcode(String paymentPostcode) {
        this.paymentPostcode = paymentPostcode;
    }

    public String getPaymentZone() {
        return paymentZone;
    }

    public void setPaymentZone(String paymentZone) {
        this.paymentZone = paymentZone;
    }

    public String getPaymentZoneId() {
        return paymentZoneId;
    }

    public void setPaymentZoneId(String paymentZoneId) {
        this.paymentZoneId = paymentZoneId;
    }

    public String getPaymentCountry() {
        return paymentCountry;
    }

    public void setPaymentCountry(String paymentCountry) {
        this.paymentCountry = paymentCountry;
    }

    public String getPaymentCountryId() {
        return paymentCountryId;
    }

    public void setPaymentCountryId(String paymentCountryId) {
        this.paymentCountryId = paymentCountryId;
    }

    public String getPaymentAddressFormat() {
        return paymentAddressFormat;
    }

    public void setPaymentAddressFormat(String paymentAddressFormat) {
        this.paymentAddressFormat = paymentAddressFormat;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getShippingFirstname() {
        return shippingFirstname;
    }

    public void setShippingFirstname(String shippingFirstname) {
        this.shippingFirstname = shippingFirstname;
    }

    public String getShippingLastname() {
        return shippingLastname;
    }

    public void setShippingLastname(String shippingLastname) {
        this.shippingLastname = shippingLastname;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(String shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(String shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingPostcode() {
        return shippingPostcode;
    }

    public void setShippingPostcode(String shippingPostcode) {
        this.shippingPostcode = shippingPostcode;
    }

    public String getShippingZone() {
        return shippingZone;
    }

    public void setShippingZone(String shippingZone) {
        this.shippingZone = shippingZone;
    }

    public String getShippingZoneId() {
        return shippingZoneId;
    }

    public void setShippingZoneId(String shippingZoneId) {
        this.shippingZoneId = shippingZoneId;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShippingCountryId() {
        return shippingCountryId;
    }

    public void setShippingCountryId(String shippingCountryId) {
        this.shippingCountryId = shippingCountryId;
    }

    public String getShippingAddressFormat() {
        return shippingAddressFormat;
    }

    public void setShippingAddressFormat(String shippingAddressFormat) {
        this.shippingAddressFormat = shippingAddressFormat;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        this.affiliateId = affiliateId;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public int getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(int marketingId) {
        this.marketingId = marketingId;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(String currencyValue) {
        this.currencyValue = currencyValue;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getForwardedIp() {
        return forwardedIp;
    }

    public void setForwardedIp(String forwardedIp) {
        this.forwardedIp = forwardedIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public ArrayList<OrderTotal> getTotals() {
        return totals;
    }

    public void setTotals(ArrayList<OrderTotal> totals) {
        this.totals = totals;
    }

    public ArrayList<?> getPaymentCustomField() {
        return paymentCustomField;
    }

    public void setPaymentCustomField(ArrayList<?> paymentCustomField) {
        this.paymentCustomField = paymentCustomField;
    }

    public ArrayList<?> getShippingCustomField() {
        return shippingCustomField;
    }

    public void setShippingCustomField(ArrayList<?> shippingCustomField) {
        this.shippingCustomField = shippingCustomField;
    }

    public ArrayList<CartItem> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<CartItem> products) {
        this.products = products;
    }

    public ArrayList<?> getVouchers() {
        return vouchers;
    }

    public void setVouchers(ArrayList<?> vouchers) {
        this.vouchers = vouchers;
    }

    public void createTotalAndProducts(ArrayList<CartItem> cartItems){

        int totalValue = 0;

        //// TODO: 23/09/16 should get sub total value

        for (int i = 0; i < cartItems.size(); i ++){
            CartItem item = cartItems.get(i);
            totalValue += item.getTotal();
        }

        OrderTotal subTotal = new OrderTotal("", "", "sub_total", "Sub-Total", Integer.toString(totalValue), "1");
        totals.add(subTotal);
        OrderTotal realTotal = new OrderTotal("", "", "total", "Total", Integer.toString(totalValue), "9");
        totals.add(realTotal);

        products.addAll(cartItems);
        for (CartItem item:cartItems){
            item.setTax("");
        }

        this.total = totalValue;
    }

    public void createCustomerInfo(Customer customer){
        customerId = customer.getCustomerId();
        customerGroupId = customer.getCustomerGroupId();
        firstname = customer.getFirstname();
        lastname = customer.getLastname();
        storeId = customer.getStoreId();
        email = customer.getEmail();
        telephone = customer.getTelephone();
        fax = customer.getFax();
//        customField = customer.getCustomFields();
        ip = customer.getIp();
    }

    public void createOtherValues(){
        invoicePrefix = "";
        storeName = "";
        storeUrl = "";
        paymentFirstname = "";
        paymentLastname = "";
        paymentCompany = "";
        paymentAddress1 = "";
        paymentAddress2 = "";
        paymentCity = "";
        paymentPostcode = "";
        paymentZone = "";
        paymentZoneId = "";
        paymentCountry = "";
        paymentCountryId = "";
        paymentAddressFormat = "";
        paymentMethod = "";
        paymentCode = "";
        shippingFirstname = "";
        shippingLastname = "";
        shippingCompany = "";
        shippingAddress1 = "";
        shippingAddress2 = "";
        shippingCity = "";
        shippingPostcode = "";
        shippingZone =  "";
        shippingZoneId = "";
        shippingCountry = "";
        shippingCountryId = "";
        shippingAddressFormat = "";
        shippingMethod = "";
        shippingCode = "";
        comment = "";
        tracking = "";
        languageId = "";
        currencyId = "";
        currencyCode = "";
        currencyValue = "";
        forwardedIp = "";


        userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:46.0) Gecko/20100101 Firefox/46.0";
        acceptLanguage = "en-US,en;q=0.5";
    }
}
