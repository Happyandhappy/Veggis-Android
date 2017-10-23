package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Product {
    @SerializedName("id")
    private String id;
    @SerializedName("seo_h1")
    private String seoH1;
    @SerializedName("name")
    private String name;
    @SerializedName("manufacturer")
    private String manufacturer;
    @SerializedName("sku")
    private String sku;
    @SerializedName("model")
    private String model;
    @SerializedName("image")
    private String image;
    @SerializedName("banner")
    private String banner;
    @SerializedName("price")
    private String price;
    @SerializedName("rating")
    private int rating;
    @SerializedName("description")
    private String description;
    @SerializedName("terms_condition")
    private String termsCondition;
    @SerializedName("special")
    private String special;
    @SerializedName("minimum")
    private String minimum;
    @SerializedName("meta_title")
    private String metaTitle;
    @SerializedName("meta_description")
    private String metaDescription;
    @SerializedName("meta_keyword")
    private String metaKeyword;
    @SerializedName("tag")
    private String tag;
    @SerializedName("upc")
    private String upc;
    @SerializedName("ean")
    private String ean;
    @SerializedName("jan")
    private String jan;
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("mpn")
    private String mpn;
    @SerializedName("location")
    private String location;
    @SerializedName("stock_status")
    private String stockStatus;
    @SerializedName("manufacturer_id")
    private String manufacturerId;
    @SerializedName("tax_class_id")
    private String taxClassId;
    @SerializedName("date_available")
    private String dateAvailable;
    @SerializedName("weight")
    private String weight;
    @SerializedName("weight_class_id")
    private String weightClassId;
    @SerializedName("length")
    private String length;
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;
    @SerializedName("length_class_id")
    private String lengthClassId;
    @SerializedName("subtract")
    private String subtract;
    @SerializedName("sort_order")
    private String sortOrder;
    @SerializedName("status")
    private String status;
    @SerializedName("date_added")
    private String dateAdded;
    @SerializedName("date_modified")
    private String dateModified;
    @SerializedName("viewed")
    private String viewed;
    @SerializedName("weight_class")
    private String weightClass;
    @SerializedName("length_class")
    private String lengthClass;
    @SerializedName("reward")
    private Object reward;
    @SerializedName("points")
    private String points;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("reviews")
    private Review reviews;

    @SerializedName("TotalTaskAmount")
    private String TotalTaskAmount;
    @SerializedName("variety")
    private String variety;
    @SerializedName("size")
    private String size;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("note")
    private String note;
    @SerializedName("validStores")
    private String validStores;

    @SerializedName("validon")
    private String validon;

    @SerializedName("images")
    private ArrayList<?> images;
    @SerializedName("attribute_groups")
    private ArrayList<?> attributeGroups;
    @SerializedName("discounts")
    private ArrayList<?> discounts;
    @SerializedName("options")
    private ArrayList<?> options;
    @SerializedName("category")
    private ArrayList<Category> category;

    private Date availabeDate;
    private Date added;
    private Date modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeoH1() {
        return seoH1;
    }

    public void setSeoH1(String seoH1) {
        this.seoH1 = seoH1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        this.termsCondition = termsCondition;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getMetaKeyword() {
        return metaKeyword;
    }

    public void setMetaKeyword(String metaKeyword) {
        this.metaKeyword = metaKeyword;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getJan() {
        return jan;
    }

    public void setJan(String jan) {
        this.jan = jan;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getMpn() {
        return mpn;
    }

    public void setMpn(String mpn) {
        this.mpn = mpn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(String taxClassId) {
        this.taxClassId = taxClassId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightClassId() {
        return weightClassId;
    }

    public void setWeightClassId(String weightClassId) {
        this.weightClassId = weightClassId;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLengthClassId() {
        return lengthClassId;
    }

    public void setLengthClassId(String lengthClassId) {
        this.lengthClassId = lengthClassId;
    }

    public String getSubtract() {
        return subtract;
    }

    public void setSubtract(String subtract) {
        this.subtract = subtract;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(String dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed;
    }

    public String getWeightClass() {
        return weightClass;
    }

    public void setWeightClass(String weightClass) {
        this.weightClass = weightClass;
    }

    public String getLengthClass() {
        return lengthClass;
    }

    public void setLengthClass(String lengthClass) {
        this.lengthClass = lengthClass;
    }

    public Object getReward() {
        return reward;
    }

    public void setReward(Object reward) {
        this.reward = reward;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTotalTaskAmount() {
        return TotalTaskAmount;
    }

    public void setTotalTaskAmount(String totalTaskAmount) {
        this.TotalTaskAmount = totalTaskAmount;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getValidStores() {
        return validStores;
    }

    public void setValidStores(String validStores) {
        this.validStores = validStores;
    }

    public String getValidon() {
        return validon;
    }

    public void setValidon(String validon) {
        this.validon = validon;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Review getReviews() {
        return reviews;
    }

    public void setReviews(Review reviews) {
        this.reviews = reviews;
    }

    public ArrayList<?> getImages() {
        return images;
    }

    public void setImages(ArrayList<?> images) {
        this.images = images;
    }

    public ArrayList<?> getAttributeGroups() {
        return attributeGroups;
    }

    public void setAttributeGroups(ArrayList<?> attributeGroups) {
        this.attributeGroups = attributeGroups;
    }

    public ArrayList<?> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(ArrayList<?> discounts) {
        this.discounts = discounts;
    }

    public ArrayList<?> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<?> options) {
        this.options = options;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public Date getAvailableDate()
    {
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(dateAvailable);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
