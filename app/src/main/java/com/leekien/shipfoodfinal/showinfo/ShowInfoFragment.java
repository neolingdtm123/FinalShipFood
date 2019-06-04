package com.leekien.shipfoodfinal.showinfo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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
import com.google.maps.android.PolyUtil;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.customView.RobBoldText;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShowInfoFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, ShowInfoManager.View {

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLocation;
    private LatLng mLatLngSearchPosition;
    SupportMapFragment m;
    LatLng latlngMain;
    ShowInfoPresenter showInfoPresenter;
    RecyclerView rcvDonHang;
    RobBoldText btnSubmit, btnSubmit1;
    Order order;
    List<LatLng> latLngList = new ArrayList<>();
    PolylineOptions polylineOptions = new PolylineOptions();
    LinearLayout linearLayout;
    TextView tvId, tvDistance, tvNameCus, tvLocation, tvPhone, tvFood, tvPriceFood, tvPrice;
    String todayAsString = "";
    String todayHour = "";
    String add1 = "";
    String add = "";
    boolean check = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_show_info_fragment, container, false);
        rcvDonHang = view.findViewById(R.id.rcvDonHang);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit1 = view.findViewById(R.id.btnSubmit1);
        tvDistance = view.findViewById(R.id.tvDistance);
        tvNameCus = view.findViewById(R.id.tvNameCus);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvPhone = view.findViewById(R.id.tvPhone);
        tvFood = view.findViewById(R.id.tvFood);
        tvPriceFood = view.findViewById(R.id.tvPriceFood);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvId = view.findViewById(R.id.tvId);
        linearLayout = view.findViewById(R.id.linear);
        showInfoPresenter = new ShowInfoPresenter(this);
        Bundle bundle = getArguments();
        if (!CommonActivity.isNullOrEmpty(bundle)) {
            order = (Order) bundle.getSerializable("key");
        }
        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        initViews();
        tvDistance.setText(order.getDistance());
        for (User user : order.getUserList()) {
            if ("user".equals(user.getType())) {
                tvNameCus.setText(user.getName());
            }
        }

        tvLocation.setText(order.getAddress());
        tvPhone.setText(order.getUserList().get(0).getPhone());
        tvId.setText("Đơn hàng" + " " + "#" + order.getId());
        String text = "";
        for (int i =0;i<order.getFoodList().size();i++) {
            Food food = order.getFoodList().get(i);
            String text1 ="";
            if(i!= order.getFoodList().size()-1){
                text1= food.getName()+"("+food.getNumberDat()+" phần)+";
            }
            else {
                text1= food.getName()+"("+food.getNumberDat()+" phần)";
            }

            text += text1;
        }
        tvPriceFood.setText("Giá sản phẩm:" + " " + order.getPricefood());
        tvPrice.setText("Giá ship:" + " " + " " + order.getPrice());
        tvFood.setText(text);
        String pattern = "dd/MM/yyyy";
        String pattern1 = "HH:mm";
        final DateFormat df = new SimpleDateFormat(pattern);
        final DateFormat df1 = new SimpleDateFormat(pattern1);
        Date today = Calendar.getInstance().getTime();
        todayAsString = df.format(today);
        todayHour = df1.format(today);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleMap.clear();
//                check = false;
                order.setShiptime(todayAsString);
                order.setShiphour(todayHour);
                order.setType("Đã nhận hàng");
                showInfoPresenter.accept(order);
            }
        });
        btnSubmit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date today = Calendar.getInstance().getTime();
                todayAsString = df.format(today);
                todayHour = df1.format(today);
                order.setEndtime(todayAsString);
                order.setEndhour(todayHour);
                order.setType("Ship hoàn thành");
                showInfoPresenter.updatEnd(order);
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

    private void initViews() {
        latLngList = new ArrayList<LatLng>();
        polylineOptions = new PolylineOptions();

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
                showCameraToPosition(mCurrentLocation, 15f);
            }
        }
        showShopLocation();
        String a = String.valueOf(mCurrentLocation.latitude);
        String b = String.valueOf(mCurrentLocation.longitude);
        add = getLocationFromAddress(order.getShopAdress());
//        if (check) {
        if ("Đặt hàng".equals(order.getType())) {
            showInfoPresenter.getInfo(a, b, add.split("/")[0], add.split("/")[1]);
            btnSubmit1.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        } else {
//            MarkerOptions markerOptions = new MarkerOptions();
//            if ("".equals(order.getAddressship())) {
//                latlngMain = new LatLng(Double.valueOf(order.getCurrentlat()), Double.valueOf(order.getCurrentlon()));
//            } else {
//                latlngMain = new LatLng(Double.valueOf(add1.split("/")[0]), Double.valueOf(add1.split("/")[1]));
//            }
//            markerOptions.position(latlngMain);
//            markerOptions.title("Vị trí cần ship");
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//            markerOptions.alpha(0.8f);
//            markerOptions.rotation(0);
//            Marker marker = mGoogleMap.addMarker(markerOptions);
//            marker.showInfoWindow();
//            showCameraToPosition(latlngMain, 13f);
            if ("".equals(order.getAddressship())) {
                showInfoPresenter.getInfo(add.split("/")[0], add.split("/")[1],
                        order.getCurrentlat(), order.getCurrentlon());
                latlngMain = new LatLng(Double.valueOf(order.getCurrentlat()), Double.valueOf(order.getCurrentlon()));

            } else {
                add1 = getLocationFromAddress(order.getAddressship());
                showInfoPresenter.getInfo(add.split("/")[0], add.split("/")[1],
                        add1.split("/")[0], add1.split("/")[1]);
                latlngMain = new LatLng(Double.valueOf(add1.split("/")[0]), Double.valueOf(add1.split("/")[1]));
            }
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latlngMain);
            markerOptions.title("Vị trí cần ship");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            markerOptions.alpha(0.8f);
            markerOptions.rotation(0);
            Marker marker = mGoogleMap.addMarker(markerOptions);
            marker.showInfoWindow();
            showCameraToPosition(latlngMain, 13f);

            btnSubmit1.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        }
    }


//    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
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
//                        Common.checkAndRequestPermissionsGPS(getActivity());
        }
        init();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//
//            case R.id.imgPosition:
//                break;
//                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 17));
        }
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(mCurrentLocation);
//        markerOptions.title("Vị trí khách hàng");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//        markerOptions.alpha(0.8f);
//        markerOptions.rotation(0);
//        Marker marker = mGoogleMap.addMarker(markerOptions);
//        marker.showInfoWindow();
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
//        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
////            @Override
////            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
////                if (actionId == EditorInfo.IME_ACTION_DONE
////                        || actionId == EditorInfo.IME_ACTION_SEARCH
////                        || event.getAction() == KeyEvent.ACTION_DOWN
////                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {
////                    if(edtSearch.getText().toString().isEmpty()){
////                        button.setVisibility(View.GONE);
////                    }
////                    else {
////                        searchLocation();
////                    }
////
////                }
////                return false;
////            }
////        });
    }


    private void directShip(android.location.Address address) {
        MarkerOptions markerOptions = new MarkerOptions();
        latlngMain = new LatLng(address.getLatitude(), address.getLongitude());
        markerOptions.position(latlngMain);
        markerOptions.title("Vị trí cần tìm");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        showCameraToPosition(latlngMain, 15f);

    }

    private void showShopLocation() {
        MarkerOptions markerOptions1 = new MarkerOptions();
        String add1 = getLocationFromAddress(order.getShopAdress());
        LatLng latLng = new LatLng(Double.valueOf(add1.split("/")[0]), Double.valueOf(add1.split("/")[1]));
        markerOptions1.position(latLng);
        markerOptions1.title("Vị trí cuả shop");
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions1.alpha(0.8f);
        markerOptions1.rotation(0);
        Marker marker1 = mGoogleMap.addMarker(markerOptions1);
        marker1.showInfoWindow();
        showCameraToPosition(latLng, 12f);
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
    public void directShop(List<String> points) {
        polylineOptions = new PolylineOptions();
        for (String a : points) {
            polylineOptions.addAll(PolyUtil.decode(a));
        }
        Polyline line = mGoogleMap.addPolyline(polylineOptions);
        line.setColor(Color.BLUE);
        line.setWidth(10);
    }

    @Override
    public void replace(Order order) {
        showInfoPresenter.send(order.getId());
        MarkerOptions markerOptions = new MarkerOptions();
        add1 = getLocationFromAddress(order.getAddressship());
        if("".equals(order.getAddressship())){
            latlngMain = new LatLng(Double.valueOf(order.getCurrentlat()), Double.valueOf(order.getCurrentlon()));
        }else {
            latlngMain= new LatLng(Double.valueOf(add1.split("/")[0]),Double.valueOf(add1.split("/")[1]));
        }
        markerOptions.position(latlngMain);
        markerOptions.title("Vị trí cần ship");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        showCameraToPosition(latlngMain, 15f);
        if(CommonActivity.isNullOrEmpty(order.getAddressship())){
            showInfoPresenter.getInfo(add.split("/")[0],add.split("/")[1],
                    order.getCurrentlat(), order.getCurrentlon());
        }
        else {
            add1 = getLocationFromAddress(order.getAddressship());
            showInfoPresenter.getInfo(add.split("/")[0],add.split("/")[1],
                    add1.split("/")[0], add1.split("/")[1]);
        }
        btnSubmit1.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.GONE);
    }

    @Override
    public void end() {
        getFragmentManager().popBackStack();
    }
    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getContext());
        List<android.location.Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
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
