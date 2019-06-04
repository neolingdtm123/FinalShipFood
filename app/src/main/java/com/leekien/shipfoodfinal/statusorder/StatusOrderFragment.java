package com.leekien.shipfoodfinal.statusorder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Subscription;
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
import com.google.gson.Gson;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.adapter.StatusOrderAdapter;
import com.leekien.shipfoodfinal.bo.Order;
import com.leekien.shipfoodfinal.bo.StatusOrder;
import com.leekien.shipfoodfinal.bo.User;
import com.leekien.shipfoodfinal.bo.onBackDialog;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.home.DialogFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StatusOrderFragment extends Fragment
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, StatusOrderManager.View, onBackDialog {
    static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 2005;

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mCurrentLocation;
    private LatLng mLatLngSearchPosition;
    SupportMapFragment m;
    StatusOrderPresenter statusOrderPresenter;
    int idOrder;
    LinearLayout lnShow;
    LinearLayout lnNotShow;
    ImageView imgNotShow;
    RecyclerView rcvStatus;
    Button btnContinue;
    Button btnCancel;
    Order orderMain;
    Context context;
    String addShop;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_status_order, container, false);
        statusOrderPresenter = new StatusOrderPresenter(this);
        Bundle bundle = getArguments();
        if (!CommonActivity.isNullOrEmpty(bundle)) {
            idOrder = bundle.getInt("key");
        }
        rcvStatus = view.findViewById(R.id.rcvStatus);
        btnContinue = view.findViewById(R.id.btnContinue);
        btnCancel = view.findViewById(R.id.btnCancel);
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
        btnContinue.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
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
            addShop = getLocationFromAddress(MainActivity.userShop.getLocation());
            showShopLocation();
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
            UiSettings uiSettings = mGoogleMap.getUiSettings();
            uiSettings.setMyLocationButtonEnabled(true);
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            //            Common.checkAndRequestPermissionsGPS(getActivity());
        }
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
            case R.id.btnContinue:
                orderMain.setType("Đã hoàn thành");
                statusOrderPresenter.deleteOrder(orderMain, "1");
                break;
            case R.id.btnCancel:
                DialogReasonFragment pd = new DialogReasonFragment();
                pd.setListener(this);
                pd.show(getFragmentManager(), "MonthYearPickerDialog");
                break;

        }

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

    private void showShipLocation(double lat, double lon) {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(lat, lon);
        markerOptions.position(latLng);
        markerOptions.title("Vị trí của ship");
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(R.drawable.ic_scooter)));
        markerOptions.alpha(0.8f);
        markerOptions.rotation(0);
        Marker marker = mGoogleMap.addMarker(markerOptions);
        marker.showInfoWindow();
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
    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getContext());
        List<android.location.Address> address;

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

    ;


    @Override
    public void call(String phone) {
        String number = ("tel:" + phone);
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
    }

    @Override
    public void showStatusOrder(List<StatusOrder> statusOrderList, Order order,StatusOrderAdapter.onReturn onReturn) {
        mGoogleMap.clear();
        showShopLocation();
        for (User user : order.getUserList()) {
            if ("ship".equals(user.getType())) {
                showShipLocation(Double.valueOf(user.getCurrentlat()), Double.valueOf(user.getCurrentlon()));
            }
        }

        orderMain = order;
        if (statusOrderList.get(2).isCheck()) {
            btnContinue.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
        } else {
            btnCancel.setVisibility(View.VISIBLE);
            btnContinue.setVisibility(View.GONE);
        }
        StatusOrderAdapter statusOrderAdapter = new StatusOrderAdapter(statusOrderList,onReturn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvStatus.setLayoutManager(layoutManager);
        rcvStatus.setAdapter(statusOrderAdapter);

    }

    @Override
    public void cancelSuccess(String checkType) {
        if ("2".equals(checkType)) {
            Toast.makeText(getActivity(), getString(R.string.success_cancel), Toast.LENGTH_SHORT).show();
        }
        else {
            int point = Integer.parseInt(MainActivity.point);
            point += ((Integer.parseInt(orderMain.getPrice())+Integer.parseInt(orderMain.getPricefood()))/1000);
            MainActivity.point= String.valueOf(point);
            statusOrderPresenter.updateUser();
            Toast.makeText(getActivity(), "Bạn được cộng thêm "+String.valueOf((Integer.parseInt(orderMain.getPrice())+Integer.parseInt(orderMain.getPricefood()))/1000)+" điểm vào điểm rank của mình", Toast.LENGTH_SHORT).show();
        }
        MainActivity.listFood = new ArrayList<>();
        getFragmentManager().popBackStack("homeframent", 0);
    }


    @Override
    public void back(String s) {
        orderMain.setType("Đã hủy");
        orderMain.setContentcancel(s);
        statusOrderPresenter.deleteOrder(orderMain, "2");
    }

    private Bitmap getMarkerBitmapFromView(@DrawableRes int resId) {
        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_custom_shipper, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image);
        markerImageView.setImageResource(resId);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }
}
