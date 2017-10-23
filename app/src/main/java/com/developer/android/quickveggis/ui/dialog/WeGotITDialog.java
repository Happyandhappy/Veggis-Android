package com.developer.android.quickveggis.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.developer.android.quickveggis.R;
import com.developer.android.quickveggis.ui.activity.MainActivity;
import com.squareup.picasso.Picasso;

public class WeGotITDialog extends DialogFragment {
    @Bind(R.id.btnNext)
    View btnNext;
    int id;
    @Bind(R.id.img)
    ImageView image;
    ActionListener listener;

    /* renamed from: com.quickveggies.quickveggies.ui.dialog.WeGotITDialog.1 */
    class C02771 implements OnClickListener {
        C02771() {
        }

        public void onClick(View v) {
            if (WeGotITDialog.this.listener != null) {
                WeGotITDialog.this.listener.onContinueClicked(WeGotITDialog.this.id);
            }

            MainActivity.getInstance().where = MainActivity.FROM_CART;
            WeGotITDialog.this.dismiss();
            getActivity().finish();  //Finish OfflineRedeemActivity to show category fragment of MainActivity
        }
    }

    public static WeGotITDialog newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        WeGotITDialog fragment = new WeGotITDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_we_gotit, container, false);
        ButterKnife.bind((Object) this, view);
        Picasso.with(getContext()).load((int) R.drawable.ic_we_got_it).into(this.image);
        this.id = getArguments().getInt("id");
        this.btnNext.setOnClickListener(new C02771());
        return view;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        setCancelable(false);
        dialog.getWindow().requestFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.getInstance().where = MainActivity.FROM_CART;
        getActivity().finish();  //Finish OfflineRedeemActivity to show category fragment of MainActivity
    }
}
