package com.developer.android.quickveggis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by devmac on 02/09/16.
 */


public class CartItem {

    public class Category {

        @SerializedName("category_id")
        private String categoryid;
        @SerializedName("category_name")
        private String categoryname;


        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }


        public String getCategoryname() {
            return categoryname;
        }

        public void setCategoryname(String categoryname) {
            this.categoryname = categoryname;
        }
    }

    @SerializedName("cart_id")
    private String cartId;
    @SerializedName("product_id")
    private String productId;

    @SerializedName("categorys")
    private List<Category> categoryInfo;

    @SerializedName("name")
    private String name;
    @SerializedName("model")
    private String model;
    @SerializedName("shipping")
    private String shipping;
    @SerializedName("image")
    private String image;
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("minimum")
    private String minimum;
    @SerializedName("subtract")
    private String subtract;
    @SerializedName("stock")
    private boolean stock;
    @SerializedName("price")
    private int price;
    @SerializedName("total")
    private int total;
    @SerializedName("reward")
    private int reward;
    @SerializedName("points")
    private int points;
    @SerializedName("tax_class_id")
    private String taxClassId;
    @SerializedName("tax")
    private String tax;
    @SerializedName("weight")
    private int weight;
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
    @SerializedName("recurring")
    private boolean recurring;
    @SerializedName("option")
    private List<?> option;
    @SerializedName("download")
    private List<?> download;
    @SerializedName("task_info")
    private List<TaskInfo> taskInfo;

    boolean isSelected = false;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public List<Category> getCategoryInfo() {
        return categoryInfo;
    }

    public void setCategoryInfo(List<Category> categoryInfo) {
        this.categoryInfo = categoryInfo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getSubtract() {
        return subtract;
    }

    public void setSubtract(String subtract) {
        this.subtract = subtract;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTaxClassId() {
        return taxClassId;
    }

    public void setTaxClassId(String taxClassId) {
        this.taxClassId = taxClassId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
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

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public List<?> getOption() {
        return option;
    }

    public void setOption(List<?> option) {
        this.option = option;
    }

    public List<?> getDownload() {
        return download;
    }

    public void setDownload(List<?> download) {
        this.download = download;
    }

    public List<TaskInfo> getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(List<TaskInfo> taskInfo) {
        this.taskInfo = taskInfo;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public class TaskInfo {
        @SerializedName("task_name")
        private String taskName;
        @SerializedName("task_amount")
        private String taskAmount;

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskAmount() {
            return taskAmount;
        }

        public void setTaskAmount(String taskAmount) {
            this.taskAmount = taskAmount;
        }
    }
}
