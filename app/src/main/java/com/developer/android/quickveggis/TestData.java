package com.developer.android.quickveggis;

import android.os.Bundle;

import com.developer.android.quickveggis.model.Card;
import com.developer.android.quickveggis.model.Category;
import com.developer.android.quickveggis.model.History;
import com.developer.android.quickveggis.model.NotificationModel;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.Support;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static List<Product> getProducts() {
        List<Product> products = new ArrayList();
//        Product product = new Product();
//        product.setName("Apple");
//        product.setImage("http://weknowyourdreamz.com/images/apple/apple-01.jpg");
//        product.setPrice(34.0d);
//        products.add(product);
//        Product product2 = new Product();
//        product2.setName("Lemon");
//        product2.setImage("https://cuidasdeti.files.wordpress.com/2014/04/limones-amarillos-de-cuidasdeti.jpg");
//        product2.setPrice(12.0d);
//        products.add(product2);
//        Product product3 = new Product();
//        product3.setName("Apple 2");
//        product3.setImage("http://weknowyourdreamz.com/images/apple/apple-01.jpg");
//        product3.setPrice(4.0d);
//        products.add(product3);
        return products;
    }

    public static List<Product> getNewProducts() {
        List<Product> products = new ArrayList();
//        Product product = new Product();
//        product.setName("Lay's Maxx, \nSizzling Barbeque");
//        product.setImageId(R.drawable.product_barbeque);
//        product.setPrice(34.0d);
//        products.add(product);
//        Product product2 = new Product();
//        product2.setName("Kissan Mixed Fruit \nJam 500gms");
//        product2.setImageId(R.drawable.product_jam);
//        product2.setPrice(12.0d);
//        products.add(product2);
//        Product product3 = new Product();
//        product3.setName("Parachute Coconnut \nOil 80Ml");
//        product3.setImageId(R.drawable.product_oil);
//        product3.setPrice(4.0d);
//        products.add(product3);
//        Product product4 = new Product();
//        product4.setName("Nivea Men Creme \n150ml");
//        product4.setImageId(R.drawable.product_nivea);
//        product4.setPrice(4.0d);
//        products.add(product4);
//        Product product5 = new Product();
//        product5.setName("MAGGI 2 Minutes \nNoodles Biryani");
//        product5.setImageId(R.drawable.product_biryani);
//        product5.setPrice(4.0d);
//        products.add(product5);
//        Product product6 = new Product();
//        product6.setName("Sunfeast Mom's Magic \nCashew & Almond");
//        product6.setImageId(R.drawable.product_almond);
//        product6.setPrice(4.0d);
//        products.add(product6);
        return products;
    }

    public static List<Product> getCartProducts() {
        List<Product> products = new ArrayList();
//        Product product = new Product();
//        product.setName("NIVEA MEN Powe Gel");
//        product.setDesc("Brand: NIVEA \nQuantity: 1");
//        product.setImageId(R.drawable.product_nivea_cart);
//        product.setPrice(34.0d);
//        products.add(product);
//        Product product2 = new Product();
//        product2.setName("LAY'S Barbecue \nFlavoured Potato Chips");
//        product2.setImageId(R.drawable.product_barbeque_cart);
//        product2.setDesc("Brand: Frito Lays \nQuantity: 1");
//        product2.setPrice(12.0d);
//        products.add(product2);
        return products;
    }

    public static List<Card> getCards() {
        List<Card> cards = new ArrayList();
        cards.add(new Card("https://d39l2hkdp2esp1.cloudfront.net/img/site/giftcard/gift-card.jpg"));
        cards.add(new Card("https://squareselect.com/images1/products/27amazon-egiftcard.jpg"));
        cards.add(new Card("http://www.inprincipo.com/files/content-images/AppleRose-Light/index.jpg"));
        return cards;
    }

    public static List<Bundle> getSearchResult(){
        List<Bundle> results = new ArrayList();

        Bundle result = new Bundle();
        result.putString("name", "Lays Maxx");
        result.putString("variety", "Barbeque");
        result.putString("size", "174g");
        results.add(result);

        return results;
    }

    public static List<History> getHistory() {
        List<History> data = new ArrayList();
        data.add(new History("Apple", History.STATUS_PENDING));
        data.add(new History("Orange", History.STATUS_PENDING));
        data.add(new History("Juice", History.STATUS_COMPLETED));
        data.add(new History("Banana", History.STATUS_COMPLETED));
        return data;
    }

    public static List<NotificationModel> getNotification() {
        List<NotificationModel> data = new ArrayList();
        data.add(new NotificationModel("9", "title", "Boost your earnings by inviting friends. \nTheres no limit to how much you can earn!", "04/04/16  3.05am"));
        data.add(new NotificationModel("4545", "title", "Wallmart receit pending.", "04/04/16  3.05am"));
        data.add(new NotificationModel("23", "title", "Remember, you have 3 rebated on your shopping list!", "04/04/16   3.05am"));
        return data;
    }

    public static List<Support> getSupports() {
        List<Support> data = new ArrayList();
        data.add(new Support("bg_grey_task_battle", "Rebate Status", "My transaction wasn't approved"));
        data.add(new Support("ic_beaty", "Technical issues", "Are there errors in the app?"));
        data.add(new Support("product_barbeque_cart", "General Queries", "Ask us about anything else"));
        return data;
    }

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList();
//        categories.add(new Category(R.drawable.ic_hot_selling, R.string.hot_selling));
//        categories.add(new Category(R.drawable.ic_grocery, R.string.grocery));
//        categories.add(new Category(R.drawable.ic_pearl, R.string.fruits));
//        categories.add(new Category(R.drawable.ic_household, R.string.household));
//        categories.add(new Category(R.drawable.ic_bear, R.string.bear));
//        categories.add(new Category(R.drawable.ic_baby, R.string.baby));
//        categories.add(new Category(R.drawable.ic_beaty, R.string.beaty));
//        categories.add(new Category(R.drawable.ic_hygiene, R.string.hygiene));
//        categories.add(new Category(R.drawable.ic_woman, R.string.female));
//        categories.add(new Category(R.drawable.ic_creates, R.string.creates));
        return categories;
    }
}
