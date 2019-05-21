package com.leekien.shipfoodfinal.changepass;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassPresenter implements ChangePassManager.Presenter {
    ChangePassManager.View view;

    public ChangePassPresenter(ChangePassManager.View view) {
        this.view = view;
        this.interactor = new ChangePassInteractor();
    }

    ChangePassManager.Interactor interactor;

    @Override
    public void updateInfo(int id, String pass) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    view.updateInfo();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        };
        interactor.updateInfo(id,pass,callback);
    }
}
