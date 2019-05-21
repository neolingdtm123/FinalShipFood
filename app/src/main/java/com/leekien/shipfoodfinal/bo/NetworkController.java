package com.leekien.shipfoodfinal.bo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.leekien.shipfoodfinal.MainActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkController {
    private static volatile AppAPI appAPI;
    public static final String IP_ADDRESS = "192.168.1.19";
//    public static final String IP_ADDRESS = "192.168.43.220";
//    public static final String IP_ADDRESS = "192.168.1.3";
    public static final String BASE_URL = "http://"+IP_ADDRESS+":8080/";
    public static final String BASE_URL_IMAGE = "http://"+IP_ADDRESS+":8080/lazada";


    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    /**
     * Thư viện nhận api
     * @return
     */
    public static AppAPI getInfoService() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", MainActivity.auth).build();
                return chain.proceed(request);
            }
        });
        if (appAPI == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Long.class,new LongTypeAdapter())
                    .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(Double.class,new DoubleTypeAdapter())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(getUnsafeOkHttpClient(60,60))
                     .client(httpClient.build())
                    .build();
            appAPI = retrofit.create(AppAPI.class);
        }
        return appAPI;
    }
    public static AppAPI getInfoServices() {


        if (appAPI == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Long.class,new LongTypeAdapter())
                    .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(Double.class,new DoubleTypeAdapter())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(getUnsafeOkHttpClient(60,60))
                    .build();
            appAPI = retrofit.create(AppAPI.class);
        }
        return appAPI;
    }


    public static AppAPI signInAuth() {
        if (appAPI == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Long.class,new LongTypeAdapter())
                    .registerTypeAdapter(Integer.class, new IntegerTypeAdapter())
                    .registerTypeAdapter(Double.class,new DoubleTypeAdapter())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(getUnsafeOkHttpClient(60,60))
                    // .client(okHttpClient())
                    .build();
            appAPI = retrofit.create(AppAPI.class);
        }
        return appAPI;
    }

    public static class LongTypeAdapter extends TypeAdapter<Long> {
        @Override
        public Long read(com.google.gson.stream.JsonReader reader) throws IOException {
            if(reader.peek() == com.google.gson.stream.JsonToken.NULL){
                reader.nextNull();
                return null;
            }
            String stringValue = reader.nextString();
            try{
                Long value = Long.valueOf(stringValue);
                return value;
            }catch(NumberFormatException e){
                return null;
            }
        }
        @Override
        public void write(JsonWriter writer, Long value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
    public static class IntegerTypeAdapter extends TypeAdapter<Integer> {
        @Override
        public Integer read(com.google.gson.stream.JsonReader reader) throws IOException {
            if(reader.peek() == com.google.gson.stream.JsonToken.NULL){
                reader.nextNull();
                return null;
            }
            String stringValue = reader.nextString();
            try{
                Integer value = Integer.valueOf(stringValue);
                return value;
            }catch(NumberFormatException e){
                return null;
            }
        }
        @Override
        public void write(JsonWriter writer, Integer value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static class DoubleTypeAdapter extends TypeAdapter<Double> {
        @Override
        public Double read(JsonReader reader) throws IOException {
            if(reader.peek() == JsonToken.NULL){
                reader.nextNull();
                return null;
            }
            String stringValue = reader.nextString();
            try{
                Double value = Double.valueOf(stringValue);
                return value;
            }catch(NumberFormatException e){
                return null;
            }
        }
        @Override
        public void write(JsonWriter writer, Double value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }
}
