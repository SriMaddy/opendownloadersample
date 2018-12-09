package com.opendownloader.opendownloadersample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.opendownloader.opendownloadersample.adapter.ImageRecyclerAdapter;
import com.opendownloader.opendownloadersample.entity.CustomImage;
import com.opendownloader.opendownloadersample.entity.ServerObject;
import com.opendownloader.opendownloadersample.entity.ServerResult;
import com.opendownloader.opendownloadersample.entity.User;
import com.opendownloader.opendownloadersample.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Activity mActivity;
    private ImageRecyclerAdapter mAdapter;

    private ServerResult serverResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutFrozen(true);
        try {
            new Thread(() -> {
                try {
                    sendRequest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendRequest() throws Exception {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(AppConstants.url)
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();

        String rspString = response.body().string();
        serverResult = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(rspString, ServerResult.class);
        List<CustomImage> images = new ArrayList<>();

        for(ServerObject serverObject : serverResult) {
            User user = serverObject.getUser();
            images.add(new CustomImage(user.getProfileImage().getLargeImg(), user.getProfileImage().getMediumImg()));
        }

        mAdapter = new ImageRecyclerAdapter(images, mActivity);
        mRecyclerView.setAdapter(mAdapter);
    }
}
