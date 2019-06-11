package com.example.okhttp3test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String URL_BASE = "http://服务器地址:端口号/goods/sstMasterData4External/queryMasterDataByEAN11";
    private ExecutorService mExecutorService = null;
    private String TAG = "MainActivity";
    private static final int RESULT = 0x01;
    private TextView textview;

    private final MyHandler mHandler = new MyHandler(this);

    class MyHandler extends Handler {
        private WeakReference<MainActivity> mActivityRef;

        public MyHandler(MainActivity activity) {
            mActivityRef = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivityRef.get();
            switch (msg.what) {
                case RESULT:
                    activity.textview.setText(msg.obj.toString());
                    break;

            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textview = findViewById(R.id.textview);

        //new Thread(new connectService());
        //SocketUtil.GetFun();
        mExecutorService = Executors.newCachedThreadPool();
        mExecutorService.execute(new GetRunnble());  //在一个新的线程中请求 Socket 连接
    }

    private class GetRunnble implements Runnable {

        @Override
        public void run() {
            getResult();
        }
    }

    private void getResult() {
        SocketUtil.GetFun(new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "onSuccess" + result);
                Message msg = Message.obtain();
                msg.what = RESULT;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }
}
