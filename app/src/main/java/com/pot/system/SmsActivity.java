package com.pot.system;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Telephony;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import api.pot.system.permissions.XPermission;
import api.pot.system.sms.Discussion;
import api.pot.system.sms.Message;
import api.pot.system.sms.SmsCallback;
import api.pot.system.sms.XSms;
import api.pot.view.xlistview.XItemViewTouch;
import api.pot.view.xlistview.XItemViewTouchCallback;

/*import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;



//package androidx.activity.result;

import android.annotation.SuppressLint;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;*/
import static api.pot.system.permissions.XPermission.isAccessExternalStorage;
import static api.pot.system.permissions.XPermission.isAccessSms;
import static api.pot.system.permissions.XPermission.isLocationEnabled;

public class SmsActivity extends AppCompatActivity {
    public static Discussion selectedDisc;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        load();

        if(isAccessSms(this)) loadDiscussions(10);
        else {
            Toast.makeText(this, "nd p", Toast.LENGTH_SHORT).show();
            XPermission.isAccessSms(this, PERMISSION_SMS);
        }

        findViewById(R.id.addMsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SmsActivity.this, NewSmsActivity.class));
            }
        });

        XSms.with(this)
                .listener(new SmsCallback(){
                    @Override
                    public void onReceivedSms(Message message) {
                        Toast.makeText(SmsActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private final int PERMISSION_SMS = 100;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_SMS: {
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    if (!isAccessSms(this)) return;
                    loadDiscussions(10);
                } else {
                    Toast.makeText(this, "Need PERMISSION", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private int count = 0;
    private void loadDiscussions(int count) {
        this.count += count;
        XSms.with(SmsActivity.this)
                .getDiscutions(this.count, new SmsCallback(){
                    @Override
                    public void onDiscussionFound(final Discussion discussion) {
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                items.add(discussion);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
    }



    List<Discussion> items = new ArrayList<>();
    DiscussionAdapter adapter;
    RecyclerView recyclerView;
    //
    GridLayoutManager layoutManager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void load() {
        initItems(items);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new DiscussionAdapter(SmsActivity.this,items);
        layoutManager = new GridLayoutManager(SmsActivity.this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new XItemViewTouch(SmsActivity.this, recyclerView, new XItemViewTouchCallback(){
            @Override
            public void onClick(View view, int position) {
                selectedDisc = items.get(position);
                startActivity(new Intent(SmsActivity.this, DiscussionActivity.class));
            }
        }));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(isFirstVisible(recyclerView)){
                    Toast.makeText(SmsActivity.this, "top", Toast.LENGTH_SHORT).show();
                    if(count>10){
                        count = 0;
                        loadDiscussions(10);
                    }
                }else if(isLastVisible(recyclerView)) {
                    Toast.makeText(SmsActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                    loadDiscussions(10);
                }
            }
        });
    }

    private void initItems(List<Discussion> items) {
        items.clear();
    }

    boolean isFirstVisible(RecyclerView rv) {
        LinearLayoutManager layoutManager =((LinearLayoutManager) rv.getLayoutManager());
        int pos = layoutManager.findFirstCompletelyVisibleItemPosition();
        int numItems = rv.getAdapter().getItemCount();
        return (pos <= 0);
    }

    boolean isLastVisible(RecyclerView rv) {
        LinearLayoutManager layoutManager =((LinearLayoutManager) rv.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = rv.getAdapter().getItemCount();
        return (pos >= numItems - 1);
    }

}
