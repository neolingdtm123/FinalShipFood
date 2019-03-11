package com.leekien.shipfoodfinal.shipper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.graphics.Color;
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
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShipperFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,ShipperManager.View {

    private GoogleMap mGoogleMap;
    private PolylineOptions polyline;
    private ArrayList<LatLng> listStep;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLocation;
    private LatLng mLatLngSearchPosition;
    SupportMapFragment m;
    AsyncTask<Void, Void, Void> task;
    LatLng latlngMain;
    private FragmentActivity myContext;
    ShipperPresenter shipperPresenter;
    RecyclerView rcvDonHang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_don_hang, container, false);
        rcvDonHang = view.findViewById(R.id.rcvDonHang);
        shipperPresenter = new ShipperPresenter(this);
        shipperPresenter.showListDonHang();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        initViews();
        task = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                // 227 Nguyễn Văn Cừ : 10.762643, 106.682079
                // Phố đi bộ Nguyễn Huệ : 10.774467, 106.703274

                String request = makeURL(String.valueOf(mCurrentLocation.latitude),String.valueOf(mCurrentLocation.longitude)
                        ,String.valueOf(latlngMain.latitude),String.valueOf(latlngMain.longitude));
                GetDirectionsTask task1 = new GetDirectionsTask(request);
                ArrayList<LatLng> list = task1.testDirection();
//                if(list == null){
//                    return null;
//                }
                for (LatLng latLng : list) {
                    listStep.add(latLng);
                }
                return null;
            }

//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                mCountDownTimer = new CountDownTimer(40000, 1500) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        Log.v(this.getClass().getSimpleName(), "onFinish ");
//                        onPostExecute(null);
//                    }
//                };
//                mCountDownTimer.start();
//            }

            @Override
            protected void onPostExecute(Void result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                polyline.addAll(listStep);
                Polyline line = mGoogleMap.addPolyline(polyline);
                line.setColor(Color.BLUE);
                line.setWidth(5);
            }
        };

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
        listStep = new ArrayList<LatLng>();
        polyline = new PolylineOptions();

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
    public String makeURL (String sourcelat, String sourcelng, String destlat, String destlng ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(sourcelat);
        urlString.append(",");
        urlString.append(sourcelng);
        urlString.append("&destination=");// to
        urlString.append(destlat);
        urlString.append(",");
        urlString.append(destlng);
        urlString.append("&key="+getResources().getString(R.string.maps_api_key));
//        urlString.append("&sensor=true");
        return urlString.toString();
    }


    @Override
    public void showListDonHang(List<DonHang> list, DonHangAdapter.onReturn onReturn) {
        DonHangAdapter donHangAdapter = new DonHangAdapter(list,onReturn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDonHang.setLayoutManager(layoutManager);
        rcvDonHang.setAdapter(donHangAdapter);
    }

    @Override
    public void showLocation(String location) {
        searchLocation(location);
    }
}