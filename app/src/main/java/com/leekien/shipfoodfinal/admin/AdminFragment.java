package com.leekien.shipfoodfinal.admin;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.AdminAdapter;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.history.HistoryManager;
import com.leekien.shipfoodfinal.signup.SignUpFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment implements View.OnClickListener, AdminManager.View {
    AdminAdapter.onReturn onReturnMain;
    ViewFlipper viewFlipper;
    RecyclerView rcvShop, rcvShip;
    AdminPresenter adminPresenter;
    static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2005;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_admin, container, false);
        adminPresenter = new AdminPresenter(this);
        viewFlipper = view.findViewById(R.id.viewFlipper);
        rcvShip = view.findViewById(R.id.rcvShip);
        rcvShop = view.findViewById(R.id.rcvShop);
        initviewFlipper();
        adminPresenter.getShip();
        adminPresenter.getShop();
        return view;
    }

    private void initviewFlipper() {
        List<String> list = new ArrayList<>();
        list.add("https://images.foody.vn/res/g71/704468/prof/s576x330/foody-upload-api-foody-mobile-1995-jpg-181224153214.jpg");
        list.add("https://images.foody.vn/biz_banner/foody-9life-636879105106471465.jpg");
        list.add("https://images.foody.vn/biz_banner/foody-buffet%20ufo%20dac%20biet%20giam-636878968926402410.jpg");
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            Picasso.get().load(list.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getActivity(), R.anim.slide_in_right);
        viewFlipper.setOutAnimation(getActivity(), R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getShip(List<User> list, AdminAdapter.onReturn onReturn) {
        onReturnMain = onReturn;
        User user = new User();
        user.setType("ship");
        user.setUrlimage("https://firebasestorage.googleapis.com/v0/b/finalshipfood.appspot.com/o/plus_in.png?alt=media&token=272d23d9-1270-451d-b494-16d1c535a526");
        list.add(user);
        AdminAdapter adminAdapter = new AdminAdapter(list, onReturnMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvShip.setLayoutManager(layoutManager);
        rcvShip.setAdapter(adminAdapter);
    }

    @Override
    public void getShop(List<User> list) {
        User user = new User();
        user.setType("shop");
        user.setUrlimage("https://firebasestorage.googleapis.com/v0/b/finalshipfood.appspot.com/o/plus_in.png?alt=media&token=272d23d9-1270-451d-b494-16d1c535a526");
        list.add(user);
        AdminAdapter adminAdapter = new AdminAdapter(list, onReturnMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcvShop.setLayoutManager(layoutManager);
        rcvShop.setAdapter(adminAdapter);
    }

    @Override
    public void callback(final User user, String check) {
        if ("1".equals(check)) {
            String number = ("tel:" + user.getPhone());
            Intent mIntent = new Intent(Intent.ACTION_CALL);
            mIntent.setData(Uri.parse(number));
// Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);

                // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            } else {
                //You already have permission
                try {
                    startActivity(mIntent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
        } else if ("0".equals(check)) {
            String number = user.getPhone();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
        } else if("2".equals(check)){
            SignUpFragment signUpFragment = new SignUpFragment();
            Bundle bundle = new Bundle();
            if ("shop".equals(user.getType())) {
                bundle.putString("check", "1");
            } else if ("ship".equals(user.getType())) {
                bundle.putString("check", "2");
            }
            signUpFragment.setArguments(bundle);
            replaceFragment(signUpFragment, "signUpFragment");
        }
        else {
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirm")
                    .setMessage("Bạn có chắc chắn muốn xóa không?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if ("shop".equals(user.getType())) {
                                adminPresenter.deleteUser(user.getId(),"0");
                            } else if ("ship".equals(user.getType())) {
                                adminPresenter.deleteUser(user.getId(),"1");
                            }
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }

    @Override
    public void success(String check) {
        if("0".equals(check)){
            Toast.makeText(getContext(),"Xóa shop thành công",Toast.LENGTH_SHORT).show();
            adminPresenter.getShop();
        }
        else {
            Toast.makeText(getContext(),"Xóa ship thành công",Toast.LENGTH_SHORT).show();
            adminPresenter.getShip();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the phone call

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void replaceFragment(Fragment fragment, String tag) {
        try {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.lnlayout, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commitAllowingStateLoss();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
