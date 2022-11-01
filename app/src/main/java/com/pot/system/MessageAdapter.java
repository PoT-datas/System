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

import api.pot.system.phone.XPhoning;
import api.pot.system.sms.Message;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {


    Context mContext;
    List<Message> items ;
    private Message item;


    public MessageAdapter(Context mContext, List<Message> mData) {
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
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_message,viewGroup,false);
        return new MessageAdapter.ViewHolder(layout);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        item = items.get(position);

        holder.container.setVisibility(item.isInbox()?View.VISIBLE:View.GONE);
        holder.container2.setVisibility(item.isInbox()?View.GONE:View.VISIBLE);

        if(item.isInbox()){
            holder.content.setText(item.getBody());
            holder.user.setText(item.getKey()+"("+item.getDisplayName()+")");
            holder.meta.setText(item.getDate());
        }else {
            holder.content2.setText(item.getBody());
            holder.user2.setText("Me"/*XPhoning.getPhoneNumber(mContext) +"("+item.getDisplayName()+")"*/);
            holder.meta2.setText(item.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        RelativeLayout container2;

        TextView user, content, meta;
        TextView user2, content2, meta2;

        public ViewHolder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            container2 = itemView.findViewById(R.id.container2);
            //
            user = itemView.findViewById(R.id.user);
            content = itemView.findViewById(R.id.content);
            meta = itemView.findViewById(R.id.meta);
            //
            user2 = itemView.findViewById(R.id.user2);
            content2 = itemView.findViewById(R.id.content2);
            meta2 = itemView.findViewById(R.id.meta2);
        }
    }
}

