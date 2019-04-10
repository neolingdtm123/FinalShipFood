package com.leekien.shipfoodfinal.cart;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    AsyncTask<Void, Void, Void> task;
    LatLng latlngMain;
    private FragmentActivity myContext;
    CartPresenter cartPresenter;
    RecyclerView rcvDonHang;
    int position = 0;
    TextView tvDistance, tvPriceFood, tvPriceDistance, tvSumPrice,tvSubmit;
    int priceDat = 0;
    double priceDistance = 0;
    String distanceMain;
    int dem = 0;
    int priceSum = 0;
    List<TypeFood> typeFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cart_fragment, container, false);
        rcvDonHang = view.findViewById(R.id.rcvDonHang);
        cartPresenter = new CartPresenter(this);
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(getContext());
        String json = myPreferences.getString("MyObject", "");
        Type type = new TypeToken<List<Food>>() {
        }.getType();
        listFood = new Gson().fromJson(json, type);
        Bundle bundle = getArguments();
//        if (!CommonActivity.isNullOrEmpty(bundle)) {
//            if (!CommonActivity.isNullOrEmpty(listFood)) {
//                for (int i = 0; i < listFood.size(); i++) {
//                    if ((food.getId() == listFood.get(i).getId()) && (food.getIdTypeFood() == listFood.get(i).getIdTypeFood())) {
//                        check = false;
//                        position = i;
//                    } else {
//                        check = true;
//                    }
//                }
//                if (check) {
//                    listFood.add(food);
//                } else {
//                    listFood.set(position, food);
//                }
//
//            } else {
//                listFood = new ArrayList<>();
//                listFood.add(food);
//            }
//        }
        tvDistance = view.findViewById(R.id.tvDistance);
        tvPriceFood = view.findViewById(R.id.tvPriceFood);
        tvPriceDistance = view.findViewById(R.id.tvPriceDistance);
        tvSubmit = view.findViewById(R.id.tvSubmit);
        tvSumPrice = view.findViewById(R.id.tvSumPrice);
        cartPresenter.showList();
        tvSubmit.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    SharedPreferences myPreferences
                            = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor myEditor = myPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(listFood);
                    myEditor.putString("MyObject", json);
                    myEditor.commit();
                    return true;
                }
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
        cartPresenter.getDistance(mCurrentLocation);


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
//        mLatLngSearchPosition = latLng;
//        showMarkerToGoogleMap(mLatLngSearchPosition);
//
//        if (mRadiusSearch.get() <= Constant.RADIUS_DEFAULT
//                || mRadiusSearch.get() >= Constant.RADIUS_ALL) {
//            showCameraToPosition(mLatLngSearchPosition, Constant.LEVEL_ZOOM_DEFAULT);
//        } else {
//            final LatLngBounds circleBounds = new LatLngBounds(
//                    locationMinMax(false, mLatLngSearchPosition, mRadiusSearch.get()),
//                    locationMinMax(true, mLatLngSearchPosition, mRadiusSearch.get()));
//            showCameraToPosition(circleBounds, 200);
//        }
//
//        showCircleToGoogleMap(mLatLngSearchPosition, mRadiusSearch.get());
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
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            //            Common.checkAndRequestPermissionsGPS(getActivity());
        }
        showShopLocation();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSubmit:
                addNewOrder();
                break;
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mCurrentLocation);
        markerOptions.title("Vị trí khách hàng");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    private void addNewOrder() {
        Order order = new Order();
        order.setType("Đặt hàng");
        String pattern = "MM/dd/yyyy HH:mm:ss";
        String pattern1 = "HH:mm";
        DateFormat df = new SimpleDateFormat(pattern);
        DateFormat df1 = new SimpleDateFormat(pattern1);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);
        String todayHour =df1.format(today);
        order.setCreatetime(todayAsString);
        order.setCreatehour(todayHour);
        order.setCurrentlat(String.valueOf(mCurrentLocation.latitude));
        order.setCurrentlon(String.valueOf(mCurrentLocation.longitude));
        order.setFoodList(listFood);
        order.setDistance(distanceMain);
        order.setPrice(priceSum +"");
        order.setPricefood(priceDat+"");
        List<User> list = new ArrayList<>();
        list.add(MainActivity.user);
        order.setUserList(list);
        cartPresenter.newOrder(order,listFood);

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

    public void init() {
//
    }




    private void showShopLocation() {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(Double.valueOf(MainActivity.latShop),Double.valueOf( MainActivity.lonShop));
        markerOptions.position(latLng);
        markerOptions.title("Vị trí của shop");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }

    ;

    public void showCameraToPosition(LatLngBounds bounds, int padding) {
        if (mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        }
    }

    public void showCircleToGoogleMap(LatLng position, float radius) {
        if (position == null) {
            return;
        }
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(position);
        //Radius in meters
        circleOptions.radius(radius * 1000);
        circleOptions.fillColor(getResources().getColor(R.color.circle_on_map));
        circleOptions.strokeColor(getResources().getColor(R.color.circle_on_map));
        circleOptions.strokeWidth(0);
        if (mGoogleMap != null) {
            mGoogleMap.addCircle(circleOptions);
        }
    }

    public void showMarkerToGoogleMap(LatLng position) {
        mGoogleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions().position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_active));
        mGoogleMap.addMarker(markerOptions);
    }

    private LatLng locationMinMax(boolean positive, LatLng position, float radius) {
        double sign = positive ? 1 : -1;
        double dx = (sign * radius * 1000) / 6378000 * (180 / Math.PI);
        double lat = position.latitude + dx;
        double lon = position.longitude + dx / Math.cos(position.latitude * Math.PI / 180);
        return new LatLng(lat, lon);
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
        tvDistance.setText(distance);
        String[] a = distance.split("km");
        priceDistance = 10000 * Double.parseDouble(a[0]);
        Double myDouble = Double.valueOf(priceDistance);
        dem = myDouble.intValue();
        tvPriceDistance.setText(dem + " " + "đ");
        tvPriceFood.setText(String.valueOf(showPrice() + " " + "đ"));
        priceSum=0;
        priceSum= showPrice() + dem;
        tvSumPrice.setText(showPrice() + dem + " " + "đ");
    }

    @Override
    public void showRemoveList(CartAdapter.onReturn onReturn, int position) {
        listFood.remove(position);
        CartAdapter cartAdapter = new CartAdapter(listFood, onReturn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDonHang.setLayoutManager(layoutManager);
        rcvDonHang.setAdapter(cartAdapter);
        tvPriceFood.setText(showPrice() + " " + "đ");
        tvSumPrice.setText(showPrice() + dem + " " + "đ");
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
        Toast.makeText(getContext(),"Đặt hàng thành công",Toast.LENGTH_SHORT).show();
        StatusOrderFragment statusOrderFragment = new StatusOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key",id);
        statusOrderFragment.setArguments(bundle);
        replaceFragment(statusOrderFragment,"statusOrderFragment");
    }

    @Override
    public boolean onBackPressed() {
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor myEditor = myPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(listFood);
        myEditor.putString("MyObject", json);
        myEditor.commit();
        return false;
    }

    public int showPrice() {
        priceDat =0;
        for (Food food : listFood) {
            priceDat += Integer.parseInt(food.getPriceDat());
        }
        return priceDat;
    }
}
