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
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.CartAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.IOnBackPressed;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.showinfo.ShowInfoManager;
import com.leekien.shipfoodfinal.showinfo.ShowInfoPresenter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


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
    Food food;
    List<Food> listFood = new ArrayList<>();
    String lat;
    String lon;
    boolean check;
    int position = 0;

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
        if (!CommonActivity.isNullOrEmpty(bundle)) {
            food = (Food) bundle.getSerializable("food");
            if (!CommonActivity.isNullOrEmpty(listFood)) {
                for (int i = 0; i < listFood.size(); i++) {
                    if ((food.getIdFood() == listFood.get(i).getIdFood()) && (food.getIdTypeFood() == listFood.get(i).getIdTypeFood())) {
                        check = false;
                        position = i;
                    } else {
                        check = true;
                    }
                }
                if (check) {
                    listFood.add(food);
                } else {
                    listFood.set(position, food);
                }

            } else {
                listFood = new ArrayList<>();
                listFood.add(food);
            }
        }
        CartAdapter cartAdapter = new CartAdapter(listFood);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDonHang.setLayoutManager(layoutManager);
        rcvDonHang.setAdapter(cartAdapter);
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
                showCameraToPosition(mCurrentLocation, 15f);
            }
        }
        showShopLocation();
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
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mCurrentLocation);
        markerOptions.title("Vị trí khách hàng");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
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

    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(getContext());
        List<android.location.Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
        }
        if (list.size() > 0) {
            android.location.Address address = list.get(0);
            directShip(address);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    task.execute();
//                }
//            });
        }

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
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(20.997733, 105.841280);
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

    public String makeURL(String sourcelat, String sourcelng, String destlat, String destlng) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(sourcelat);
        urlString.append(",");
        urlString.append(sourcelng);
        urlString.append("&destination=");// to
        urlString.append(destlat);
        urlString.append("&mode=");// to
        urlString.append("driving");
        urlString.append(",");
        urlString.append(destlng);
        urlString.append("&key=" + getResources().getString(R.string.maps_api_key));
//        urlString.append("&sensor=true");
        return urlString.toString();
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
    public void showDistance() {

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
}