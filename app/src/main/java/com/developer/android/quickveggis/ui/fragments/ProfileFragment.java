package com.developer.android.quickveggis.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.App;
import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.api.ServiceAPI;
import com.developer.android.quickveggis.api.model.SocialLoginData;
import com.developer.android.quickveggis.api.response.ResponseCallback;
import com.developer.android.quickveggis.controller.CustomerController;
import com.developer.android.quickveggis.model.Customer;
import com.developer.android.quickveggis.model.EarningValue;
import com.developer.android.quickveggis.model.Product;
import com.developer.android.quickveggis.model.ProfileMenu;
import com.developer.android.quickveggis.model.event.Logout;
import com.developer.android.quickveggis.ui.activity.LoginActivity;
import com.developer.android.quickveggis.ui.activity.ProfileActivity;
import com.developer.android.quickveggis.ui.utils.FragmentUtils;
import com.developer.android.quickveggis.ui.utils.RecyclerItemClickListener;
import com.facebook.login.widget.ProfilePictureView;
import com.squareup.picasso.Picasso;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration.Builder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

//import java.net.HttpURLConnection;
//import java.net.ssl.HttpsURLConnection;

public class ProfileFragment extends Fragment {
    private static final int MENU_ABOUT = 3;
    private static final int MENU_SETTINGS = 2;
    private static final int MENU_UPDATE_PROFILE = 1;
    private static final int MENU_LOGOUT = 4;
    TextView txtFirstCharacterOfName;
    MenuAdapter adapter;
    List<ProfileMenu> data;
    @Bind(R.id.rv)
    RecyclerView rv;

    /* renamed from: com.quickveggies.quickveggies.ui.fragment.ProfileFragment.1 */
    class C05691 implements RecyclerItemClickListener.OnItemClickListener {
        C05691() {
        }

        public void onItemClick(View view, int position) {
            ProfileFragment.this.onMenuClicked(ProfileFragment.this.data.get(position));
        }
    }

    public class MenuAdapter extends Adapter<MenuAdapter.Holder> {

        public class Holder extends ViewHolder {
            @Bind(R.id.txtTitle)
            TextView txtTitle;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind((Object) this, itemView);
            }
        }

        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(ProfileFragment.this.getContext()).inflate(R.layout.item_settings, parent, false));
        }

        public void onBindViewHolder(Holder holder, int position) {
            holder.txtTitle.setText(((ProfileMenu) ProfileFragment.this.data.get(position)).getTitle());
        }

        public int getItemCount() {
            return ProfileFragment.this.data.size();
        }
    }

    public ProfileFragment() {
        this.data = new ArrayList();
    }

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private SocialLoginData readSocialData() {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.login.user.social", Context.MODE_PRIVATE);

        String profileImg = preferences.getString("profileImg","");
        String provider = preferences.getString("provider","");

        SocialLoginData data = new SocialLoginData("", "", provider, profileImg);

        return data;
    }

    ImageView profileImageView;
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Customer customer = CustomerController.getInstance().getLoggedInCustomer();
        SocialLoginData socialCustomer ; // = CustomerController.getInstance().getLoggedInSocialCustomer();
        socialCustomer = this.readSocialData();

        if ( !socialCustomer.getProvider().contentEquals("") ) {  // socialCustomer != null

            profileImageView = (ImageView)view.findViewById(R.id.ic_profile);

            ImageView imgSocial = (ImageView)view.findViewById(R.id.ic_social);
            if (socialCustomer.getProvider().contentEquals("facebook")) {

                new DownloadImageTask().execute(socialCustomer.getProfileimg());
                imgSocial.setVisibility(View.VISIBLE);
            }
        }

        TextView txtName = (TextView)view.findViewById(R.id.text_name);
        txtFirstCharacterOfName = (TextView)view.findViewById(R.id.txtFirstCharacterNameProfile);
        txtName.setText(customer.getFirstname() + customer.getLastname());
        String upperFirstname = customer.getFirstname().toUpperCase();
        txtFirstCharacterOfName.setText(String.valueOf(upperFirstname.charAt(0)));

        final TextView txtLifetime = (TextView)view.findViewById(R.id.text_lifetime);
        txtLifetime.setText("0र Lifetime Earnings");

        ServiceAPI.newInstance().getCustomerEarningValues(customer.getCustomerId(), new ResponseCallback<EarningValue>() { //
            @Override
            public void onSuccess(EarningValue data) {

                if (data.getLifetime() != null) {
                    txtLifetime.setText(data.getLifetime() + "र Lifetime Earnings");
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });

        ButterKnife.bind(this, view);
        return view;
    }

    private class DownloadImageTask extends AsyncTask {
        protected Bitmap doInBackground(String... urls) {
            return loadImageFromNetwork(urls[0]);
        }

        protected void onPostExecute(Bitmap result) {
            profileImageView.setImageBitmap(result);
            txtFirstCharacterOfName.setVisibility(View.GONE);

        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            return loadImageFromNetwork((String) params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {

            if (result != null) {
                Bitmap bitmap = (Bitmap)result;

                Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);

                if (circleBitmap != null ) {
                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);

                    profileImageView.setImageBitmap(circleBitmap);
                    txtFirstCharacterOfName.setVisibility(View.GONE);
                }
            }


        }
    }

    private Bitmap loadImageFromNetwork(String strUrl) {

        Bitmap fb_img = null;
        try {
            URL fb_url = new URL(strUrl);//small | noraml | large
            HttpsURLConnection conn1 = (HttpsURLConnection) fb_url.openConnection();
            HttpsURLConnection.setFollowRedirects(true);
            conn1.setInstanceFollowRedirects(true);
            fb_img = BitmapFactory.decodeStream(conn1.getInputStream());

        }catch (Exception ex) {
            ex.printStackTrace();

            Toast.makeText(getActivity(), ex.toString(), Toast.LENGTH_LONG).show();
        }

        return fb_img;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.profile);
        this.rv.setLayoutManager(new LinearLayoutManager(getContext()));
        fillMenu();
        this.adapter = new MenuAdapter();
        this.rv.setAdapter(this.adapter);
        this.rv.addItemDecoration(((new Builder(getActivity()).color(ContextCompat.getColor(getContext(), R.color.divider))).sizeResId(R.dimen.dp1)).build());
        this.rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new C05691()));
    }

    private void fillMenu() {
        data.clear();
        data.add(new ProfileMenu(R.string.update_profile, MENU_UPDATE_PROFILE));
        data.add(new ProfileMenu(R.string.settings, MENU_SETTINGS));
        data.add(new ProfileMenu(R.string.about, MENU_ABOUT));
        data.add(new ProfileMenu(R.string.log_out, MENU_LOGOUT));
    }

    private void onMenuClicked(ProfileMenu menu) {
        switch (menu.getId()) {
            case MENU_UPDATE_PROFILE /*1*/:
                FragmentUtils.changeFragment(getActivity(),R.id.content, ProfileUpdateFragment.newInstance(), true);
                break;
            case MENU_SETTINGS /*2*/:
                FragmentUtils.changeFragment(getActivity(),R.id.content, SettingsFragment.newInstance(), true);
                break;
            case MENU_ABOUT /*3*/:
                FragmentUtils.changeFragment(getActivity(), R.id.content, AboutFragment.newInstance(), true);
                break;
            case MENU_LOGOUT:
                logout();
                break;
        }
    }

    private void logout(){
        final ProgressDialog logoutDialog = new ProgressDialog(ProfileFragment.this.getActivity());
        logoutDialog.setMessage("Logging out...");
        logoutDialog.setCancelable(false);
        logoutDialog.show();

        ServiceAPI.newInstance().logout(new ResponseCallback<String>() {
            @Override
            public void onSuccess(String data) {
//                SessionController.getInstance().deleteSession();
                CustomerController.getInstance().logoutUser();
                App.getInstance().getEventBus().post(new Logout());
                startActivity(LoginActivity.getStartIntent(ProfileFragment.this.getActivity()));
                logoutDialog.dismiss();
                ProfileFragment.this.getActivity().finish();
            }

            @Override
            public void onFailure(String error) {
                logoutDialog.dismiss();
                Toast.makeText(getActivity(), "Sign Out Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
            }
        });

//        OkHttpTask logoutTask = new OkHttpTask(new NetworkTaskEvent() {
//            @Override
//            public void success(final JSONObject jsonRes) {
//                ProfileFragment.this.getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() { //
//                        //loginDialog.cancel();
//                        if (logoutDialog.isShowing())
//                            logoutDialog.dismiss();
//                        try {
//                            if (jsonRes.getBoolean("success")) {
//                                Toast.makeText(getActivity(), "Successfully logged out!", Toast.LENGTH_LONG).show();
//                                startActivity(LoginActivity.getStartIntent(ProfileFragment.this.getActivity()));
//                                ProfileFragment.this.getActivity().finish();
//                            } else {
//                                Toast.makeText(getActivity(), "Sign In Failed.\nPlease try again later", Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void errorConnect() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (logoutDialog.isShowing())
//                            logoutDialog.dismiss();
//                        Toast.makeText(ProfileFragment.this.getActivity(), ProfileFragment.this.getActivity().getResources().getString(R.string.connect_error), Toast.LENGTH_SHORT).show();
//                        //DialogUtils.showAlertDialog(ProfileFragment.this.getActivity(), getActivity().getResources().getString(R.string.connect_error));
//                    }
//                });
//            }
//
//            @Override
//            public void error() {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (logoutDialog.isShowing())
//                            logoutDialog.dismiss();
//                        Toast.makeText(ProfileFragment.this.getActivity(), "Error while trying to logout", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        }, OkHttpTask.POST, null);
//
//        logoutTask.execute(Config.API_BASE_URL + Config.API_LOGOUT);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProfileActivity)getActivity()).btnSave.setVisibility(View.GONE);
    }
}
