package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ogan on 8/15/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private List<Item> itemList;
    private Context context;

    private boolean isLoadingAdded = false;

    public RecyclerAdapter(Context context) {
        this.context = context;
        itemList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.pagination_loading_layout, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.home_page, parent, false);
        viewHolder = new UserViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                final UserViewHolder userViewHolder = (UserViewHolder) holder;
                String username = itemList.get(position).getLogin();
                String uppercaseUsername = username.substring(0, 1).toUpperCase() + username.substring(1);
                userViewHolder.userName.setText(uppercaseUsername);

                Picasso.with(context).
                        load(itemList.get(position).getAvatarUrl()).
                        into(((UserViewHolder) holder).imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Main2Activity.class);
                        String username = itemList.get(position).getLogin();
                        String url = itemList.get(position).getHtmlUrl();
                        String avatar = itemList.get(position).getAvatarUrl();

                        intent.putExtra("username", username);
                        intent.putExtra("url", url);
                        intent.putExtra("avatar", avatar);
                        context.startActivity(intent);

                    }
                });
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == itemList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Item result) {
        itemList.add(result);
        notifyItemInserted(itemList.size() - 1);
    }

    public void addAll(List<Item> results) {
        for (Item result : results) {
            add(result);
        }
    }

    public void remove(Item result) {
        int position = itemList.indexOf(result);
        if (position > -1) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemList.size() - 1;
        Item result = getItem(position);

        if (result != null) {
            itemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Item getItem(int position) {
        return itemList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */


    protected class UserViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        CircleImageView imageView;



        public UserViewHolder(View view){
            super(view);
            context = view.getContext();
            userName = (TextView) view.findViewById(R.id.user_name);
            imageView = (CircleImageView) view.findViewById(R.id.profile_pic);
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}

