package com.pot.system;

import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import api.pot.system.sms.Discussion;
import api.pot.system.sms.Message;
import api.pot.system.sms.SmsCallback;
import api.pot.system.sms.XSms;

public class DiscussionActivity extends AppCompatActivity {
    private Discussion discussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        load();

        discussion = SmsActivity.selectedDisc;

        loadMessages(10);

        XSms.with(this)
                .listener(new SmsCallback(){
                    @Override
                    public void onReceivedSms(Message message) {
                        Toast.makeText(DiscussionActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private int count = 0;
    private int currentPos = 9;
    private void loadMessages(int count) {
        this.count += count;
        XSms.with(DiscussionActivity.this)
                .into(discussion)
                .getMessages(this.count, new SmsCallback(){
                    @Override
                    public void onMsgListReady(List<Message> messages) {
                        items.clear();
                        Collections.reverse(messages);
                        items.addAll(messages);
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(currentPos);
                    }
                });
    }

    List<Message> items = new ArrayList<>();
    MessageAdapter adapter;
    RecyclerView recyclerView;
    //
    GridLayoutManager layoutManager;
    private void load() {
        initItems(items);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MessageAdapter(DiscussionActivity.this,items);
        layoutManager = new GridLayoutManager(DiscussionActivity.this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(isFirstVisible(recyclerView)){
                    currentPos = 10;
                    Toast.makeText(DiscussionActivity.this, "Top", Toast.LENGTH_SHORT).show();
                    loadMessages(10);
                }else if(isLastVisible(recyclerView)) {
                    currentPos = 9;
                    Toast.makeText(DiscussionActivity.this, "Bottom", Toast.LENGTH_SHORT).show();
                    if(count>10){
                        count = 0;
                        loadMessages(10);
                    }
                }
            }
        });
    }

    private void initItems(List<Message> items) {
        items.clear();
        //currentPos = 9;
    }

    int getFirstVisible(RecyclerView rv) {
        LinearLayoutManager layoutManager =((LinearLayoutManager) rv.getLayoutManager());
        int pos = layoutManager.findFirstCompletelyVisibleItemPosition();
        return pos;
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
