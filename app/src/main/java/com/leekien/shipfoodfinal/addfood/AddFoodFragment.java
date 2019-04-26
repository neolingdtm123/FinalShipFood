package com.leekien.shipfoodfinal.addfood;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.common.CommonActivity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class AddFoodFragment extends Fragment implements AddFoodManager.View, View.OnClickListener {
    int REQUEST_CODE_IMAGE = 1;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    ImageView imgAnh;
    AddFoodPresenter presenter;
    Uri downloadUrl;
    EditText edtName;
    EditText edtPrice;
    int position;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_food, container, false);
        Bundle bundle = getArguments();
        if(!CommonActivity.isNullOrEmpty(bundle)){
            position=bundle.getInt("position");
        }
        storageReference = firebaseStorage.getReferenceFromUrl("gs://finalshipfood.appspot.com");
        presenter = new AddFoodPresenter(this);
        edtName = view.findViewById(R.id.edtName);
        edtPrice = view.findViewById(R.id.edtPrice);
        ImageView imgThem = view.findViewById(R.id.imgThem);
        Button btnContinue = view.findViewById(R.id.btnContinue);
        imgThem.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        imgAnh = view.findViewById(R.id.imgAnh);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnContinue:
                addFood();
                break;
            case R.id.imgThem:
                showImage();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAnh.setVisibility(View.VISIBLE);
            imgAnh.setImageBitmap(bitmap);
            Calendar calendar = Calendar.getInstance();
            StorageReference mouRef = storageReference.child("image" + calendar.getTimeInMillis() + "png");
//            imgAnh.setDrawingCacheEnabled(true);
//            imgAnh.buildDrawingCache();
//            Bitmap bitmap1 = imgAnh.getDrawingCache();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] datas = byteArrayOutputStream.toByteArray();
            UploadTask uploadTask = mouRef.putBytes(datas);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    downloadUrl = taskSnapshot.getDownloadUrl();
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    private void addFood() {
        if (CommonActivity.isNullOrEmpty(edtName.getText().toString())
                || CommonActivity.isNullOrEmpty(edtPrice.getText().toString())
                || CommonActivity.isNullOrEmpty(downloadUrl)) {
          Dialog dialog = CommonActivity.
                  createAlertDialog(getActivity(), getString(R.string.null_value), getString(R.string.shipfood));
          dialog.show();
        }
        else {

            Food food = new Food();
            food.setUrlfood(downloadUrl.toString());
            food.setName(edtName.getText().toString());
            food.setPrice(Integer.parseInt(edtPrice.getText().toString()));
            food.setIdTypeFood(position+1);
            presenter.addFood(food);
        }
    }

    @Override
    public void showSuccess() {
        Toast.makeText(getContext(),"Thêm món ăn thành công",Toast.LENGTH_LONG).show();
        MainActivity.checkAddFood= true;
        getFragmentManager().popBackStack();
    }

}
