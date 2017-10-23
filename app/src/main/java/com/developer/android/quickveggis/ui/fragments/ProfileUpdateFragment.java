package com.developer.android.quickveggis.ui.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.UpdateAccountData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Customer;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileUpdateFragment extends Fragment {
    @Bind(R.id.header)
    ImageView header;

    @Bind(R.id.edZipCode)
    EditText edtZipCode;

    @Bind(R.id.edReferalCode)
    EditText edtReferalCode;

    @Bind(R.id.radioMale)
    RadioButton radioMale;

    @Bind(R.id.radioFemale)
    RadioButton radioFemale;

    @Bind(R.id.edBirthday)
    EditText edtBirthday;

    @Bind(R.id.rg_family)
    RadioGroup rgFamily;

    @Bind(R.id.cb_apply_cook_home)
    CheckBox cbApplyCookHome;

    @Bind(R.id.cb_apply_baby_product)
    CheckBox cbApplyBabyProduct;

    @Bind(R.id.cb_apply_kid_product)
    CheckBox cbApplyKidProduct;

    @Bind(R.id.cb_apply_pet_owner)
    CheckBox cbApplyPetOwner;

    @Bind(R.id.cb_apply_none)
    CheckBox cbApplyNone;

    @Bind(R.id.cb_style_premium_fan)
    CheckBox cbStylePremiumFan;

    @Bind(R.id.cb_style_health_item)
    CheckBox cbStyleHealthItem;

    @Bind(R.id.cb_style_biggest_saving)
    CheckBox cbStyleBiggestSaving;

    @Bind(R.id.cb_style_new_products)
    CheckBox cbStyleNewProudcts;

    @Bind(R.id.cb_preference_vegetarian)
    CheckBox cbPreferenceVegetarian;

    @Bind(R.id.cb_preference_vagan)
    CheckBox cbPreferenceVagan;

    @Bind(R.id.cb_preference_gluten_free)
    CheckBox cbPreferenceGlutenFree;

    @Bind(R.id.cb_preference_milk)
    CheckBox cbPreferenceMilk;

    @Bind(R.id.cb_preference_peanut)
    CheckBox cbPreferencePeanut;

    @Bind(R.id.cb_preference_none)
    CheckBox cbPreferenceNone;

    @Bind(R.id.rg_eth)
    RadioGroup rgEth;

    @Bind(R.id.btnSave)
    Button btnSave;

    int year;
    int month;
    int day;

    ProgressDialog pDialog;

    Customer customer;

    public static ProfileUpdateFragment newInstance() {
        Bundle args = new Bundle();
        ProfileUpdateFragment fragment = new ProfileUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        ButterKnife.bind(this, view);
        initWidget();
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.profile_update);
        Picasso.with(getContext()).load(R.drawable.ic_profile_update_header).fit().centerCrop().into(this.header);
    }

    void initWidget(){
        customer = CustomerController.getInstance().getLoggedInCustomer();
        if (customer == null)       return;

        edtZipCode.setText(customer.getZipCode());
        edtReferalCode.setText(customer.getReferalCode());

        if(customer.getGender().equals("Male"))
            radioMale.setChecked(true);
        else if(customer.getGender().equals("Female"))
            radioFemale.setChecked(true);

        edtBirthday.setText(customer.getBirthday());

        if (customer.getShoppingForFamily() != null && !customer.getShoppingForFamily().equals("")){
            switch (Integer.parseInt(customer.getShoppingForFamily())) {
                case 1:
                    rgFamily.check(R.id.rb_family_1);
                    break;

                case 2:
                    rgFamily.check(R.id.rb_family_2);
                    break;

                case 3:
                    rgFamily.check(R.id.rb_family_3);
                    break;

                default:
                    rgFamily.check(R.id.rb_family_4);
                    break;
            }
        }

        if (customer.getPleaseCheckAllTheApplay() != null && !customer.getPleaseCheckAllTheApplay().equals("")){

            String[] checkAllThatApplies = customer.getPleaseCheckAllTheApplay().split(",");
            for (int i = 0; i < checkAllThatApplies.length; i ++) {
                switch (Integer.parseInt(checkAllThatApplies[i])) {
                    case 1:
                        cbApplyCookHome.setChecked(true);
                        break;
                    case 2:
                        cbApplyBabyProduct.setChecked(true);
                        break;
                    case 3:
                        cbApplyKidProduct.setChecked(true);
                        break;
                    case 4:
                        cbApplyPetOwner.setChecked(true);
                        break;
                    case 5:
                        cbApplyNone.setChecked(true);
                        break;
                }
            }
        }

        if (customer.getWahysYourShoppingStyle() != null && !customer.getWahysYourShoppingStyle().equals("")){
            String[] shoppingStyles = customer.getWahysYourShoppingStyle().split(",");
            for (int i = 0; i < shoppingStyles.length; i ++) {
                switch (Integer.parseInt(shoppingStyles[i])) {
                    case 1:
                        cbStylePremiumFan.setChecked(true);
                        break;
                    case 2:
                        cbStyleHealthItem.setChecked(true);
                        break;
                    case 3:
                        cbStyleBiggestSaving.setChecked(true);
                        break;
                    case 4:
                        cbStyleNewProudcts.setChecked(true);
                        break;
                }
            }
        }
        if (customer.getFoodPreferancesAndAllergies() != null && !customer.getFoodPreferancesAndAllergies().equals("")){

            String[] foodPreferences = customer.getFoodPreferancesAndAllergies().split(",");
            for (int i = 0; i < foodPreferences.length; i ++) {
                switch (Integer.parseInt(foodPreferences[i])) {
                    case 1:
                        cbPreferenceVegetarian.setChecked(true);
                        break;
                    case 2:
                        cbPreferenceVagan.setChecked(true);
                        break;
                    case 3:
                        cbPreferenceGlutenFree.setChecked(true);
                        break;
                    case 4:
                        cbPreferenceMilk.setChecked(true);
                        break;
                    case 5:
                        cbPreferencePeanut.setChecked(true);
                        break;
                    case 6:
                        cbPreferenceNone.setChecked(true);
                        break;
                }
            }
        }
        if (customer.getWahtsYourEthinicity() != null && !customer.getWahtsYourEthinicity().equals("")){
            switch (Integer.parseInt(customer.getWahtsYourEthinicity())) {
                case 1:
                    rgEth.check(R.id.rb_eth_hindu);
                    break;
                case 2:
                    rgEth.check(R.id.rb_eth_sikh);
                    break;
                case 3:
                    rgEth.check(R.id.rb_eth_christian);
                    break;
                case 4:
                    rgEth.check(R.id.rb_eth_muslim);
                    break;
                case 5:
                    rgEth.check(R.id.rb_eth_other);
                    break;
            }
        }
    }

    @OnClick(R.id.edBirthday)
    public void onClickBithday(){

        getCurrentBirthday();

        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                edtBirthday.setText(
                        new StringBuilder()
                                .append(year).append("-")
                                .append(monthOfYear + 1).append("-")
                                .append(dayOfMonth).append(""));
            }
        }, year, month, day).show();
    }

    void getCurrentBirthday(){
        if (edtBirthday.getText().toString().isEmpty()){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }else {

            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                 date = dateFormat.parse(edtBirthday.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                year = date.getYear();
                month = date.getMonth();
                day = date.getDate();
            }
        }
    }

    public String addIntegerStr(String mainString, int value){
        if (!mainString.isEmpty())    mainString += ",";
        mainString = mainString + Integer.toString(value);
        return mainString;
    }

    @OnClick(R.id.btnSave)
    public void onClickSave(){
        String zipCode = edtZipCode.getText().toString();
        String referralCode = edtReferalCode.getText().toString();
        String birthDay = edtBirthday.getText().toString();
        String gender = radioMale.isChecked()?"Male":"Female";
        int shippingForFamily = rgFamily.getCheckedRadioButtonId();

        switch (shippingForFamily){
            case R.id.rb_family_1:
                shippingForFamily = 1;
                break;

            case R.id.rb_family_2:
                shippingForFamily = 2;
                break;

            case R.id.rb_family_3:
                shippingForFamily = 3;
                break;

            case R.id.rb_family_4:
                shippingForFamily = 4;
                break;
        }

        String checkAllThatApply = "";
        if (cbApplyCookHome.isChecked())        checkAllThatApply = addIntegerStr(checkAllThatApply, 1);
        if (cbApplyBabyProduct.isChecked())     checkAllThatApply = addIntegerStr(checkAllThatApply, 2);
        if (cbApplyKidProduct.isChecked())      checkAllThatApply = addIntegerStr(checkAllThatApply, 3);
        if (cbApplyPetOwner.isChecked())        checkAllThatApply = addIntegerStr(checkAllThatApply, 4);
        if (cbApplyNone.isChecked())            checkAllThatApply = addIntegerStr(checkAllThatApply, 5);

        String whatShippingStyle = "";
        if (cbStylePremiumFan.isChecked())      whatShippingStyle = addIntegerStr(whatShippingStyle, 1);
        if (cbStyleHealthItem.isChecked())      whatShippingStyle = addIntegerStr(whatShippingStyle, 2);
        if (cbStyleBiggestSaving.isChecked())   whatShippingStyle = addIntegerStr(whatShippingStyle, 3);
        if (cbStyleNewProudcts.isChecked())     whatShippingStyle = addIntegerStr(whatShippingStyle, 4);

        String foodPreferences = "";
        if (cbPreferenceVegetarian.isChecked())     foodPreferences = addIntegerStr(foodPreferences, 1);
        if (cbPreferenceVagan.isChecked())          foodPreferences = addIntegerStr(foodPreferences, 2);
        if (cbPreferenceGlutenFree.isChecked())     foodPreferences = addIntegerStr(foodPreferences, 3);
        if (cbPreferenceMilk.isChecked())           foodPreferences = addIntegerStr(foodPreferences, 4);
        if (cbPreferencePeanut.isChecked())         foodPreferences = addIntegerStr(foodPreferences, 5);
        if (cbPreferenceNone.isChecked())           foodPreferences = addIntegerStr(foodPreferences, 6);

        int ethnicity = rgEth.getCheckedRadioButtonId();
        switch (ethnicity){
            case R.id.rb_eth_hindu:
                ethnicity = 1;
                break;

            case R.id.rb_eth_sikh:
                ethnicity = 2;
                break;

            case R.id.rb_eth_christian:
                ethnicity = 3;
                break;

            case R.id.rb_eth_muslim:
                ethnicity = 4;
                break;

            case R.id.rb_eth_other:
                ethnicity = 5;
                break;
        }

        if (zipCode.isEmpty()){
            Toast.makeText(getContext(), "Please input zip code.", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (referralCode.isEmpty()){
//            Toast.makeText(getContext(), "Please input referral code.", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (birthDay.isEmpty() && false){
            Toast.makeText(getContext(), "Please select bithday.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (shippingForFamily == -1){
            Toast.makeText(getContext(), "Please select shipping for a family", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkAllThatApply.isEmpty()){
            Toast.makeText(getContext(), "Please select ALL THAT APPLY options", Toast.LENGTH_SHORT).show();
            return;
        }

        if (whatShippingStyle.isEmpty()){
            Toast.makeText(getContext(), "Please select shipping style", Toast.LENGTH_SHORT).show();
            return;
        }

        if (foodPreferences.isEmpty()){
            Toast.makeText(getContext(), "Please select food preferences and allergies", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ethnicity == -1){
            Toast.makeText(getContext(), "Please select ethnicity", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateAccountData data = new UpdateAccountData(zipCode, referralCode, gender, birthDay, Integer.toString(shippingForFamily),
                checkAllThatApply, whatShippingStyle, foodPreferences, Integer.toString(ethnicity));

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        ServiceAPI.newInstance().updateAccount(data, new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getAccountDetails();
                Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getAccountDetails(){

        ServiceAPI.newInstance().getAccountDetails(new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {
                pDialog.dismiss();
                CustomerController.getInstance().saveLoginCustomer(data);
            }

            @Override
            public void onFailure(String error) {
                pDialog.dismiss();
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
