package com.leekien.shipfoodfinal.addfood;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.leekien.shipfoodfinal.MainActivity;
import com.leekien.shipfoodfinal.R;
import com.leekien.shipfoodfinal.bo.Food;
import com.leekien.shipfoodfinal.bo.onBackDialog;
import com.leekien.shipfoodfinal.common.CommonActivity;
import com.leekien.shipfoodfinal.statusorder.DialogReasonFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class AddFoodFragment extends Fragment implements AddFoodManager.View, View.OnClickListener, onBackDialog {
    int REQUEST_CODE_IMAGE = 1;
    int REQUEST_CODE_IMAGE_GALLY = 2;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference;
    ImageView imgAnh;
    AddFoodPresenter presenter;
    Uri downloadUrl;
    EditText edtName;
    EditText edtPrice;
    Spinner spn_discount;
    Button btnBack,btnSubmit;
    int position;
    String discount="0";
    String check="0";
    Food food;
    String urlAnh;
    LinearLayout ln;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_new_food, container, false);
        Bundle bundle = getArguments();
        if (!CommonActivity.isNullOrEmpty(bundle)) {
            position = bundle.getInt("position");
            food = (Food) bundle.getSerializable("food");
            check = bundle.getString("key");
        }
        MainActivity.checkAddFood = true;
        storageReference = firebaseStorage.getReferenceFromUrl("gs://finalshipfood.appspot.com");
        presenter = new AddFoodPresenter(this);
        edtName = view.findViewById(R.id.edtName);
        edtPrice = view.findViewById(R.id.edtPrice);
        spn_discount = view.findViewById(R.id.spn_discount);
        btnBack = view.findViewById(R.id.btnBack);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ln = view.findViewById(R.id.ln);
        ImageView imgThem = view.findViewById(R.id.imgThem);
        Button btnContinue = view.findViewById(R.id.btnContinue);
        imgAnh = view.findViewById(R.id.imgAnh);
        initSpinner();
        if("1".equals(check)){
            discount= food.getDiscount();
            edtPrice.setText(food.getPrice()+"");
            edtName.setText(food.getName());
            imgAnh.setVisibility(View.VISIBLE);
            urlAnh = food.getUrlfood();
            Picasso.get().load(food.getUrlfood()).into(imgAnh);
            btnContinue.setVisibility(View.GONE);
            ln.setVisibility(View.VISIBLE);
        }
        else {
            spn_discount.setSelection(0);
            edtPrice.setText("");
            edtName.setText("");
            imgAnh.setVisibility(View.GONE);
            btnContinue.setVisibility(View.VISIBLE);
            ln.setVisibility(View.GONE);
        }

        imgThem.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        spn_discount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                discount = spn_discount.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    private void initSpinner() {
        List<String> list = new ArrayList<>();
        list.add("0%");
        list.add("5%");
        list.add("10%");
        list.add("15%");
        list.add("20%");
        list.add("25%");
        list.add("30%");
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_discount.setAdapter(adapter);
        if("1".equals(check)){
            for(int i =0;i<list.size();i++){
                if(list.get(i).equals(food.getDiscount()+"%")){
                    spn_discount.setSelection(i);
                }
            }
        }
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
            case R.id.btnBack:
                food.setPrice(Integer.valueOf(edtPrice.getText().toString()));
                food.setName(edtName.getText().toString());
                food.setUrlfood(urlAnh);
                food.setDiscount(discount.split("%")[0]);
                presenter.updateFood(food);
                break;
            case R.id.btnSubmit:
                presenter.deleteFood(food.getId());
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
                    urlAnh = downloadUrl.toString();
                }
            });
        } else {
            if (requestCode == REQUEST_CODE_IMAGE_GALLY && resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream =getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imgAnh.setVisibility(View.VISIBLE);
                    imgAnh.setImageBitmap(selectedImage);
                    Calendar calendar = Calendar.getInstance();
                    StorageReference mouRef = storageReference.child("image" + calendar.getTimeInMillis() + "png");
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
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
                            urlAnh = downloadUrl.toString();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showImage() {
        DialogChooseAction pd = new DialogChooseAction();
        pd.setListener(this);
        pd.show(getFragmentManager(), "MonthYearPickerDialog");
    }

    private void addFood() {
        if (CommonActivity.isNullOrEmpty(edtName.getText().toString())
                || CommonActivity.isNullOrEmpty(edtPrice.getText().toString())
                || CommonActivity.isNullOrEmpty(downloadUrl)) {
            Dialog dialog = CommonActivity.
                    createAlertDialog(getActivity(), getString(R.string.null_value), getString(R.string.shipfood));
            dialog.show();
        } else {

            Food food = new Food();
            food.setIdshop(MainActivity.user.getId());
            food.setUrlfood(downloadUrl.toString());
            food.setName(edtName.getText().toString());
            food.setPrice(Integer.parseInt(edtPrice.getText().toString()));
            food.setIdTypeFood(position + 1);
            food.setDiscount(discount.split("%")[0]);
            presenter.addFood(food);
        }
    }

    @Override
    public void showSuccess(String check) {
        if("0".equals(check)){
            Toast.makeText(getContext(), "Thêm món ăn thành công", Toast.LENGTH_LONG).show();
        }
        else if("1".equals(check)){
            Toast.makeText(getContext(), "Cập nhật món ăn thành công", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getContext(), "Xóa món ăn thành công", Toast.LENGTH_LONG).show();
        }

        getFragmentManager().popBackStack();
    }

    @Override
    public void back(String s) {
        if ("0".equals(s)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_IMAGE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
//            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//            photoPickerIntent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_IMAGE_GALLY);
        }
    }
}
