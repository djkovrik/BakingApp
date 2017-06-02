package com.sedsoftware.bakingapp.data.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sedsoftware.bakingapp.utils.MyGsonAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {

  public static <T> T createFrom(Class<T> serviceClass, String endpoint) {

    OkHttpClient client = new OkHttpClient.Builder()
        .addNetworkInterceptor(new StethoInterceptor())
        .build();

    Gson gson = new GsonBuilder()
        .registerTypeAdapterFactory(MyGsonAdapterFactory.create())
        .create();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(endpoint)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();

    return retrofit.create(serviceClass);
  }
}
