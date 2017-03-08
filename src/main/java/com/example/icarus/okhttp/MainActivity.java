package com.example.icarus.okhttp;

import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnGet,btnPost;
    private TextView tvResutl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGet = (Button) findViewById(R.id.get);
        btnPost = (Button) findViewById(R.id.post);
        tvResutl = (TextView) findViewById(R.id.tv_result);
        //Http Get请求
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        //创建okHttpClient对象
                        OkHttpClient mOkHttpClient = new OkHttpClient();
                        //创建一个Request
                        final Request request = new Request.Builder().get().url("https://www.baidu.com/").build();

                        //new call
                        Call call = mOkHttpClient.newCall(request);
                        //请求加入调度
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Request request, IOException e) {
                                Log.i("MainActivity","Get请求失败");
                            }

                            @Override
                            public void onResponse(Response response) throws IOException {
                                final String res = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvResutl.setText(res);
                                    }
                                });
                            }
                        });
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Http Post请求
                OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("zxh","123");
                final Request request = new Request.Builder().url("https://www.baidu.com/").post(builder.build()).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvResutl.setText(res);
                            }
                        });

                    }
                });
            }
        });




    }
}
