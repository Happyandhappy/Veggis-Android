package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.SocialLoginData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.SessionController;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.developer.android.quickveggis.ui.activity.SignInActivity;
import com.developer.android.quickveggis.ui.activity.SignupActivity;
import com.developer.android.quickveggis.ui.activity.TutorialActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener{
    @Bind(R.id.btnFacebook)
    View btnFacebook;
    @Bind(R.id.btnGoogle)
    View btnGoogle;
    @Bind(R.id.btnSignIn)
    View btnSignIn;
    @Bind(R.id.btnSignUp)
    View btnSignUp;
    @Bind(R.id.imgBg)
    ImageView imgBg;
    @Bind(R.id.imgLogo)
    ImageView imgLogo;
    @Bind(R.id.fbLoginButton)
    LoginButton fbLoginButton;

    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 9001;

    private String loginUserProflileImg;
    private String loginUserProvider;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmen_login, container, false);
        ButterKnife.bind(this, view);

        loginUserProflileImg = "";
        loginUserProvider = "";

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getContext()).load(R.drawable.logo).fit().centerInside().into(this.imgLogo);
        Picasso.with(getContext()).load(R.drawable.splash).fit().centerCrop().into(this.imgBg);

        initFBLogin();
        initGoogle();
//        this.btnFacebook.setOnClickListener(mOnClickListener);
//        this.btnGoogle.setOnClickListener(mOnClickListener);
        this.btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSocialData("", "");
                startActivity(SignInActivity.getStartIntent(getContext()));
                getActivity().finish();
            }
        });
        this.btnSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SignupActivity.getStartIntent(getContext()));
                getActivity().finish();
            }
        });
    }

    private void login() {
        startActivity(TutorialActivity.getStartIntent(getContext()));
        getActivity().finish();
    }

    OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (SessionController.getInstance().isExistShownSecondTutorial()) {
                if (CustomerController.getInstance().isCustomerLoggedIn()) {
                    startMainActivity();
                }else {
                    // TODO: 01/09/16 add login function
                    login();
                }
            }else {
                login();
            }
        }
    };

    private void startMainActivity()
    {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @OnClick(R.id.btnFacebook)
    public void onClickFaceBookLogin(){
        fbLoginButton.performClick();
    }

    private void initFBLogin() {
        if (null != LoginManager.getInstance())
            LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                final String accessToken = loginResult.getAccessToken().getToken();
                if (loginResult.getRecentlyGrantedPermissions().contains("email")) {
                    final ProgressDialog pDialog = new ProgressDialog(getActivity());
                    pDialog.setMessage("Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    GraphRequest request = GraphRequest.newMeRequest(
                            loginResult.getAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    pDialog.dismiss();
                                    try {
                                        String id = object.getString("id");
                                        String email = object.getString("email");
                                        String name = object.getString("name");
                                        String avatar = "https://graph.facebook.com/" + id + "/picture?type=large";

//                                        id; //
                                        loginUserProflileImg = avatar;
                                        loginUserProvider = "facebook";

                                        socialLogin(new SocialLoginData(email, accessToken, "facebook", avatar));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id, name, email");
                    request.setParameters(parameters);
                    request.executeAsync();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Login failed, Please try again.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getContext(), "Some error occurred, Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.btnGoogle)
    public void onClickGoogleLogin(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestServerAuthCode(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            googleLogin(new SocialLoginData(acct.getEmail(), acct.getIdToken(), "google", ""));

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void saveSocialData(String profileImg, String provider) {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profileImg", profileImg);
        editor.putString("provider", provider);

        editor.commit();
    }

    private void socialLogin(SocialLoginData loginData){

        CustomerController.getInstance().saveLoginSocialCustomer(loginData);
        this.saveSocialData(loginData.getProfileimg(), loginData.getProvider());

        ServiceAPI.newInstance().socialLogin(loginData, new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {

                data.setProfileimg(loginUserProflileImg);
                data.setProvider(loginUserProvider);

                CustomerController.getInstance().saveLoginCustomer(data);
                if (SessionController.getInstance().isExistShownSecondTutorial()) {
                    startMainActivity();
                }else {
                    login();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Login failed from Server, Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void googleLogin(SocialLoginData data){
        ServiceAPI.newInstance().googleLogin(data, new ResponseCallback<Customer>() {
            @Override
            public void onSuccess(Customer data) {
                CustomerController.getInstance().saveLoginCustomer(data);

                SocialLoginData socialLoginData = new SocialLoginData(data.getEmail(), "", "google", "");
                saveSocialData("", "google");
//                (String email, String accessToken, String provider, String profileimg)

                CustomerController.getInstance().saveLoginSocialCustomer(socialLoginData);
                if (SessionController.getInstance().isExistShownSecondTutorial()) {
                    startMainActivity();
                }else {
                    login();
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(getContext(), "Login failed, Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
