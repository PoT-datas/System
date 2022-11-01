package com.pot.system;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import api.pot.system.sms.Discussion;
import api.pot.system.sms.Message;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.ViewHolder> {


    Context mContext;
    List<Discussion> items ;
    private Discussion item;


    public DiscussionAdapter(Context mContext, List<Discussion> mData) {
        this.mContext = mContext;
        this.items = mData;
    }

    public boolean isFullVersion() {
        return fullVersion;
    }

    public void setFullVersion(boolean fullVersion) {
        this.fullVersion = fullVersion;
    }

    private boolean fullVersion = false;
    @NonNull
    @Override
    public DiscussionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_discussion,viewGroup,false);
        return new DiscussionAdapter.ViewHolder(layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull DiscussionAdapter.ViewHolder holder, int position) {
        item = items.get(position);

        holder.content.setText(item.getLastSms());
        holder.user.setText(item.getKey());
        holder.meta.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        RelativeLayout container2;
        TextView user, content, meta;

        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            user = itemView.findViewById(R.id.user);
            content = itemView.findViewById(R.id.content);
            meta = itemView.findViewById(R.id.meta);
        }
    }
}

