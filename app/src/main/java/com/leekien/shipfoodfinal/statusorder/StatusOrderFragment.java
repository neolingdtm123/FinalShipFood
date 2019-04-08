package com.leekien.shipfoodfinal.statusorder;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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
import com.google.maps.android.PolyUtil;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.CartAdapter;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.adapter.StatusOrderAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.StatusOrder;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.customView.RobBoldText;
import com.leekien.shipfoodfinal.home.DialogPriceFragment;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.leekien.shipfoodfinal.MainActivity.listFood;

public class StatusOrderFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,StatusOrderManager.View {

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLocation;
    private LatLng mLatLngSearchPosition;
    SupportMapFragment m;
    LatLng latlngMain;
    StatusOrderPresenter statusOrderPresenter;
    int idOrder;
    LinearLayout lnShow;
    LinearLayout lnNotShow;
    ImageView imgNotShow;
    RecyclerView rcvStatus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_status_order, container, false);
        statusOrderPresenter= new StatusOrderPresenter(this);
        Bundle bundle = getArguments();
        if(!CommonActivity.isNullOrEmpty(bundle)){
           idOrder =  bundle.getInt("key");
        }
        rcvStatus = view.findViewById(R.id.rcvStatus);
        lnShow = view.findViewById(R.id.lnShow);
        lnNotShow = view.findViewById(R.id.lnNotShow);
        imgNotShow = view.findViewById(R.id.imgNotShow);
        lnNotShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnNotShow.setVisibility(View.GONE);
                lnShow.setVisibility(View.VISIBLE);
            }
        });
        imgNotShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnNotShow.setVisibility(View.VISIBLE);
                lnShow.setVisibility(View.GONE);
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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



    }

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
        final boolean shouldStopLoop = false;
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                statusOrderPresenter.getOrder(idOrder);
                if (!shouldStopLoop) {
                    mHandler.postDelayed(this, 5000);
                }
            }
        };
        mHandler.post(runnable);

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
        latlngMain = new LatLng(address.getLatitude(),address.getLongitude());
        markerOptions.position(latlngMain);
        markerOptions.title("Vị trí cần tìm");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        showCameraToPosition(latlngMain, 15f);

    }
    private void showShopLocation(){
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(Double.valueOf(MainActivity.latShop),Double.valueOf( MainActivity.lonShop));
        markerOptions.position(latLng);
        markerOptions.title("Vị trí của shop");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
    };

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
    public void showStatusOrder(List<StatusOrder> statusOrderList) {
        StatusOrderAdapter statusOrderAdapter = new StatusOrderAdapter(statusOrderList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvStatus.setLayoutManager(layoutManager);
        rcvStatus.setAdapter(statusOrderAdapter);

    }



}
