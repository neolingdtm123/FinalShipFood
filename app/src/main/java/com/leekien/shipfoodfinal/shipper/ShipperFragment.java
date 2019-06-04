package com.leekien.shipfoodfinal.shipper;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
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
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.DonHangAdapter;
import com.leekien.shipfoodfinal.bo.DonHang;
import com.leekien.shipfoodfinal.bo.GetDirectionsTask;
import com.leekien.shipfoodfinal.bo.Order;
//import com.leekien.shipfoodfinal.service.PushLocationService;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.logout.TabFragment;
import com.leekien.shipfoodfinal.service.PushLocationService;
import com.leekien.shipfoodfinal.showinfo.ShowInfoFragment;
import com.leekien.shipfoodfinal.statis.StatisFragment;
import com.leekien.shipfoodfinal.successorder.SuccessOrderFragment;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.leekien.shipfoodfinal.MainActivity.mServiceIntent;

public class ShipperFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, ShipperManager.View {

    private FusedLocationProviderClient client;
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
    ImageView imgLocation;
    TextView tvHistory, tvInfo;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_don_hang, container, false);
        rcvDonHang = view.findViewById(R.id.rcvDonHang);
        imgLocation = view.findViewById(R.id.imgLocation);
        tvHistory = view.findViewById(R.id.tvHistory);
        tvInfo = view.findViewById(R.id.tvInfo);
        imgLocation.setOnClickListener(this);
        shipperPresenter = new ShipperPresenter(this);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        initViews();
        Intent intent = new Intent(getContext(), PushLocationService.class);
        context.startService(intent);
        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatisFragment statisFragment = new StatisFragment();
                Bundle bundle = new Bundle();
                bundle.putString("check","0");
                statisFragment.setArguments(bundle);
                replaceFragment(statisFragment, "statisFragment");
            }
        });
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabFragment tabFragment = new TabFragment();
                replaceFragment(tabFragment, "tabFragment");
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
                showCameraToPosition(mCurrentLocation, 13f);
            }
        }

        shipperPresenter.getLocation(MainActivity.user.getId(), mCurrentLocation.latitude, mCurrentLocation.longitude);

//        showShopLocation();
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
        init();
        final boolean shouldStopLoop = false;
        final Handler mHandler = new Handler();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shipperPresenter.showListDonHang();
                if (!shouldStopLoop) {
                    mHandler.postDelayed(this, 5000);
                }
            }
        };
        mHandler.post(runnable);
//        Intent intent = new Intent(getContext(), PushLocationService.class);
//        context.startService(intent);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgLocation:
                showCurrentPosition();
                break;

        }
    }

    private void showCurrentPosition() {
        showCameraToPosition(mCurrentLocation, 13f);

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
//
//



    public void showCameraToPosition(LatLngBounds bounds, int padding) {
        if (mGoogleMap != null) {
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        }
    }


    @Override
    public void showListDonHang(List<Order> list, DonHangAdapter.onReturn onReturn) {
        for (Order order : list) {
            List<User> users = order.getUserList();
            for (User user : users) {
                if ("shop".equals(user.getType())) {
                    order.setShopAdress(user.getLocation());
                }
            }
            if (CommonActivity.isNullOrEmpty(order.getAddressship())) {
                order.setAddress(getAddress(Double.valueOf(order.getCurrentlat()), Double.valueOf(order.getCurrentlon())));
            } else {
                order.setAddress(order.getAddressship());
            }
        }
        DonHangAdapter donHangAdapter = new DonHangAdapter(list, onReturn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvDonHang.setLayoutManager(layoutManager);
        rcvDonHang.setAdapter(donHangAdapter);
    }

    @Override
    public void directShip(Order order, String shipAddress, String shopAddress) {
        mGoogleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        if(CommonActivity.isNullOrEmpty(order.getAddressship())){
            latlngMain= new LatLng(Double.valueOf(order.getCurrentlat()),Double.valueOf(order.getCurrentlon()));
        }
        else {
            String add = getLocationFromAddress(shipAddress);
            latlngMain = new LatLng(Double.valueOf(add.split("/")[0]), Double.valueOf(add.split("/")[1]));
        }

        markerOptions.position(latlngMain);
        markerOptions.title("Vị trí cần ship");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
        showCameraToPosition(latlngMain, 12f);


        MarkerOptions markerOptions1 = new MarkerOptions();
        String add1 = getLocationFromAddress(shopAddress);
        latlngMain = new LatLng(Double.valueOf(add1.split("/")[0]), Double.valueOf(add1.split("/")[1]));
        markerOptions1.position(latlngMain);
        markerOptions1.title("Vị trí cuả shop");
        markerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        markerOptions1.alpha(0.8f);
        markerOptions1.rotation(0);
        Marker marker1 = mGoogleMap.addMarker(markerOptions1);
        marker1.showInfoWindow();
        showCameraToPosition(latlngMain, 12f);
    }


    @Override
    public void replace(Order order) {
        ShowInfoFragment showInfoFragment = new ShowInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", order);
        showInfoFragment.setArguments(bundle);
        replaceFragment(showInfoFragment, "ss");
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

    public String getAddress(double lat, double lng) {
        String add = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            return add;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        return add;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    public void getLocation() {
        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(context);
        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    MainActivity.lat = location.getLatitude();
                    MainActivity.lon = location.getLongitude();
                }
            }
        });
    }

    public void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, 1);
    }

    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;

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
