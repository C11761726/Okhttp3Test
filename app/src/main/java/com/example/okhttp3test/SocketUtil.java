package com.example.okhttp3test;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SocketUtil {

    private static String TAG = "SocketUtil";

    public static void postForTradeInfo(String stationCode, String barCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

        String url_base = MainActivity.URL_BASE;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("stationCode", stationCode);
        builder.add("barCode", barCode);
        //builder.add("description",description);
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(url_base)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res_str = response.body().string();
                Log.d("Util", "=postMsg result=>>" + res_str);
            }
        });

    }

    public static void GetFun(final VolleyCallback callback){
        String url = "http://v.juhe.cn/weather/index?format=2&cityname=%E8%8B%8F%E5%B7%9E&key=这里填写key";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onSuccess(response.body().string());
                //Log.d(TAG, "onResponse: " + response.body().string());
            }
        });
    }
}
