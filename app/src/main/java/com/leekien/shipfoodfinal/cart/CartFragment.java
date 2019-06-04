package com.leekien.shipfoodfinal.cart;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leekien.shipfoodfinal.AppUtils;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.CartAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.Foodorder;
import com.leekien.shipfoodfinal.bo.IOnBackPressed;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.TypeFood;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.showinfo.ShowInfoManager;
import com.leekien.shipfoodfinal.showinfo.ShowInfoPresenter;
import com.leekien.shipfoodfinal.statusorder.StatusOrderFragment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.leekien.shipfoodfinal.MainActivity.listFood;


public class CartFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, CartManager.View, IOnBackPressed {

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLocation;
    private LatLng mLatLngSearchPosition;
    SupportMapFragment m;
    CartPresenter cartPresenter;
    RecyclerView rcvDonHang;
    TextView tvDistance, tvPriceFood, tvPriceDistance, tvSumPrice, tvSubmit, tvShow, tvRank;
    EditText edtShipAddress;
    int priceDat = 0;
    double priceDistance = 0;
    String distanceMain;
    int dem = 0;
    int priceSum = 0;
    int priceShip = 0;
    int idOrder = 0;
    Order order;
    String address;
    List<User> userList;
    String addShop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cart_fragment, container, false);
        rcvDonHang = view.findViewById(R.id.rcvDonHang);
        cartPresenter = new CartPresenter(this);
//        SharedPreferences myPreferences
//                = PreferenceManager.getDefaultSharedPreferences(getContext());
//        String json = myPreferences.getString("MyObject", "");
//        Type type = new TypeToken<List<Food>>() {
//        }.getType();
//        listFood = new Gson().fromJson(json, type);
        Bundle bundle = getArguments();
        if (!CommonActivity.isNullOrEmpty(bundle)) {
            if (idOrder == 0) {
                idOrder = bundle.getInt("idOrder");
            }
            order = (Order) bundle.getSerializable("order");
            userList = (List<User>) bundle.getSerializable("shop");
        }
        tvDistance = view.findViewById(R.id.tvDistance);
        tvPriceFood = view.findViewById(R.id.tvPriceFood);
        tvPriceDistance = view.findViewById(R.id.tvPriceDistance);
        tvRank = view.findViewById(R.id.tvRank);
        tvSubmit = view.findViewById(R.id.tvSubmit);
        tvSumPrice = view.findViewById(R.id.tvSumPrice);
        tvShow = view.findViewById(R.id.tvShow);
        edtShipAddress = view.findViewById(R.id.edtShipAddress);
        if (MainActivity.checkOrder) {
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvShow.setVisibility(View.VISIBLE);
            edtShipAddress.setKeyListener(null);
        }
        if (!MainActivity.checkOrder && !CommonActivity.isNullOrEmpty(order)) {
            if (CommonActivity.isNullOrEmpty(order.getAddressship())) {
                edtShipAddress.setVisibility(View.GONE);
            } else {
                edtShipAddress.setText(order.getAddressship());
                edtShipAddress.setVisibility(View.VISIBLE);
            }

        } else {
            edtShipAddress.setText(address);
        }
        if (Integer.parseInt(MainActivity.point) < 500) {
            tvRank.setVisibility(View.GONE);
        } else if (Integer.parseInt(MainActivity.point) < 1000) {
            tvRank.setVisibility(View.VISIBLE);
            tvRank.setText("-5%(Thành viên bạc)");
        } else {
            tvRank.setVisibility(View.VISIBLE);
            tvRank.setText("-10%(Thành viên vàng)");
        }
        cartPresenter.showList();
        tvSubmit.setOnClickListener(this);
        tvShow.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        edtShipAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                address = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edtShipAddress.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        // Identifier of the action. This will be either the identifier you supplied,
                        // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                        if (actionId == EditorInfo.IME_ACTION_SEARCH
                                || actionId == EditorInfo.IME_ACTION_DONE
                                || event.getAction() == KeyEvent.ACTION_DOWN
                                || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (CommonActivity.isNullOrEmpty(edtShipAddress.getText().toString())) {
                                mGoogleMap.clear();
                                showShopLocation();
                                showCameraToPosition(mCurrentLocation, 13f);
                                cartPresenter.getDistance(mCurrentLocation.latitude + "", mCurrentLocation.longitude + "",
                                        addShop.split("/")[0], addShop.split("/")[1]);
                            } else {
                                String addUser = getLocationFromAddress(edtShipAddress.getText().toString());
                                if (CommonActivity.isNullOrEmpty(addUser)) {
                                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.error_location), getString(R.string.shipfood)).show();
                                } else {
                                    mGoogleMap.clear();
                                    showShopLocation();
                                    showShipLocation(addUser);
                                    cartPresenter.getDistance(addUser.split("/")[0], addUser.split("/")[1],
                                            addShop.split("/")[0], addShop.split("/")[1]);
                                }
                            }
                            return true;
                        }
                        // Return true if you have consumed the action, else false.
                        return false;
                    }
                });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        m = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map));
        m.getMapAsync(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            final Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (lastLocation == null) {
                return;
            }
            mCurrentLocation = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            if (mLatLngSearchPosition == null) {
                showCameraToPosition(mCurrentLocation, 13f);
            }
        }
        if (!CommonActivity.isNullOrEmpty(MainActivity.userShop)) {
            addShop = getLocationFromAddress(MainActivity.userShop.getLocation());
            cartPresenter.getDistance(mCurrentLocation.latitude + "", mCurrentLocation.longitude + "", addShop.split("/")[0], addShop.split("/")[1]);
        }

        showShopLocation();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
//
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        mGoogleMap.setTrafficEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);

        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            UiSettings uiSettings = mGoogleMap.getUiSettings();
            uiSettings.setMyLocationButtonEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            //            Common.checkAndRequestPermissionsGPS(getActivity());
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                addNewOrder();
                break;
            case R.id.tvShow:
                StatusOrderFragment statusOrderFragment = new StatusOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("key", idOrder);
                statusOrderFragment.setArguments(bundle);
                replaceFragment(statusOrderFragment, "statusOrderFragment");
                break;
        }
    }

    private void addNewOrder() {
        Order order = new Order();
        order.setType("Đặt hàng");
        String pattern = "dd/MM/yyyy";
        String pattern1 = "HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        DateFormat df1 = new SimpleDateFormat(pattern1);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        String todayHour = df1.format(today);
        order.setCreatetime(todayAsString);
        order.setCreatehour(todayHour);
        order.setCurrentlat(String.valueOf(mCurrentLocation.latitude));
        order.setCurrentlon(String.valueOf(mCurrentLocation.longitude));
        order.setFoodList(listFood);
        order.setDistance(distanceMain);
        order.setPrice(priceShip + "");
        order.setPricefood(showPrice() + "");
        order.setAddressship(edtShipAddress.getText().toString());
        List<User> list = new ArrayList<>();
        list.add(MainActivity.user);
        for (User user : userList) {
            if (user.getId() == MainActivity.idshop) {
                list.add(user);
            }
        }
        order.setUserList(list);
        cartPresenter.newOrder(order, listFood);

    }

    public void showCameraToPosition(LatLng position, float zoomLevel) {
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(position)
                .zoom(zoomLevel)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        if (mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), null);
        }
    }


    private void showShopLocation() {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(Double.valueOf(addShop.split("/")[0]), Double.valueOf(addShop.split("/")[1]));
        markerOptions.position(latLng);
        markerOptions.title("Vị trí của shop");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    private void showShipLocation(String addUser) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(Double.valueOf(addUser.split("/")[0]), Double.valueOf(addUser.split("/")[1]));
        markerOptions.position(latLng);
        markerOptions.title("Vị trí cần ship");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
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


    @Override
    public void showDistance(String distance) {
        distanceMain = distance;
        String a = "";
        if (!MainActivity.checkOrder && !CommonActivity.isNullOrEmpty(order)) {
            a = order.getDistance().split("km")[0];
            tvDistance.setText(order.getDistance());
        } else {
            a = distance.split("km")[0];
            tvDistance.setText(distance);
        }
        double gia = 0;
        if (Double.parseDouble(a) <= 4) {
            gia = 20000;
        } else {
            gia = 20000 + 5000 * (Double.parseDouble(a) - 4);
        }
        Double myDouble = Double.valueOf(gia);
        dem = myDouble.intValue();
        priceShip = dem;
        tvPriceDistance.setText(AppUtils.formatMoney(dem + ""));
        if (!MainActivity.checkOrder && !CommonActivity.isNullOrEmpty(order)) {
            tvPriceFood.setText(AppUtils.formatMoney(order.getPricefood()));
            priceSum = 0;
            priceSum = Integer.parseInt(order.getPricefood()) + dem;
            tvSumPrice.setText(AppUtils.formatMoney(priceSum + ""));
        } else {
            tvPriceFood.setText(AppUtils.formatMoney(showPrice() + ""));
            priceSum = 0;
            priceSum = showPrice() + dem;
            tvSumPrice.setText(AppUtils.formatMoney(showPrice() + dem + ""));
        }

    }

    @Override
    public void showRemoveList(CartAdapter.onReturn onReturn, int position) {
        if (!MainActivity.checkOrder) {
            CommonActivity.createAlertDialog(getActivity(), getString(R.string.error_order_change), getString(R.string.shipfood)).show();
        } else {
            listFood.remove(position);
            CartAdapter cartAdapter = new CartAdapter(listFood, onReturn);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvDonHang.setLayoutManager(layoutManager);
            rcvDonHang.setAdapter(cartAdapter);
            tvPriceFood.setText(AppUtils.formatMoney(showPrice() + ""));
            tvSumPrice.setText(AppUtils.formatMoney(showPrice() + dem + ""));
        }

    }

    @Override
    public void showList(CartAdapter.onReturn onReturn) {
        CartAdapter cartAdapter = new CartAdapter(listFood, onReturn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDonHang.setLayoutManager(layoutManager);
        rcvDonHang.setAdapter(cartAdapter);
    }

    @Override
    public void showSuccess(int id) {
        cartPresenter.send(id);
        Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
        MainActivity.checkOrder = false;
        StatusOrderFragment statusOrderFragment = new StatusOrderFragment();
        Bundle bundle = new Bundle();
        idOrder = id;
        bundle.putInt("key", id);
        statusOrderFragment.setArguments(bundle);
        replaceFragment(statusOrderFragment, "statusOrderFragment");
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public int showPrice() {
        priceDat = 0;
        int discount;
        for (Food food : listFood) {
            if ("0".equals(food.getDiscount())) {
                discount = 0;
            } else {
                discount = Integer.parseInt(food.getDiscount());
            }
            if (!CommonActivity.isNullOrEmpty(food.getPriceDat())) {
                priceDat += (Integer.parseInt(food.getPriceDat()) * (100 - discount) / 100);
            }
        }
        int price = 0;
        if (Integer.parseInt(MainActivity.point) < 500) {
            price = priceDat;

        } else if (Integer.parseInt(MainActivity.point) < 1000) {
            price = priceDat * 95 / 100;
        } else {
            price = priceDat * 90 / 100;
        }
        return price;
    }

    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getContext());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (CommonActivity.isNullOrEmpty(address)) {
                return null;
            }
            Address location = address.get(0);
            String lat = String.valueOf(location.getLatitude());
            String lon = String.valueOf(location.getLongitude());
            return lat + "/" + lon;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
