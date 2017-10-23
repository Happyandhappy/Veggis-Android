package com.developer.android.quickveggis.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by devmac on 10/09/16.
 */
public class SocialLoginData {

    @SerializedName("email")
    private String email;
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("provider")
    private String provider;
    @SerializedName("profileimg")
    private String profileimg;


    public SocialLoginData(String email, String accessToken, String provider, String profileimg) {
        this.email = email;
        this.accessToken = accessToken;
        this.provider = provider;
        this.profileimg = profileimg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }
}
