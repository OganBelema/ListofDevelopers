package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ogan on 8/15/17.
 */

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DeveloperListItemViewMvc.Listener {

    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private final List<Item> mItemList;
    private Context mContext;

    private boolean mIsLoadingAdded = false;

    public RecyclerAdapter(Context context) {
        mContext = context;
        mItemList = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (viewType) {
            case ITEM:
                DeveloperListItemViewMvc developerListItemViewMvc =
                        new DeveloperListItemViewMvcImpl(inflater, parent);
                viewHolder = new UserViewHolder(developerListItemViewMvc);
                developerListItemViewMvc.registerListener(this);
                break;
            case LOADING:
                LoaderViewMvc loaderViewMvc = new LoaderViewMvcImpl(inflater, parent);
                viewHolder = new LoadingVH(loaderViewMvc);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                final UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.mDeveloperListItemViewMvc.bindDeveloper(getItem(position));
                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mItemList.size() - 1 && mIsLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    private void add(Item result) {
        mItemList.add(result);
        notifyItemInserted(mItemList.size() - 1);
    }

    public void addAll(List<Item> results) {
        for (Item result : results) {
            add(result);
        }
    }

    private void remove(Item result) {
        int position = mItemList.indexOf(result);
        if (position > -1) {
            mItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        mIsLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        mIsLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        mIsLoadingAdded = false;

        int position = mItemList.size() - 1;
        Item result = getItem(position);

        if (result != null) {
            mItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private Item getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public void onDevelopClicked(Item item) {
        Intent intent = new Intent(mContext, DeveloperDetailsActivity.class);
        String username = item.getLogin();
        String url = item.getHtmlUrl();
        String avatar = item.getAvatarUrl();

        intent.putExtra(DeveloperDetailsActivity.USERNAME_KEY, username);
        intent.putExtra(DeveloperDetailsActivity.URL_KEY, url);
        intent.putExtra(DeveloperDetailsActivity.AVATAR_KEY, avatar);
        mContext.startActivity(intent);
    }

   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */


    class UserViewHolder extends RecyclerView.ViewHolder{

        private final DeveloperListItemViewMvc mDeveloperListItemViewMvc;

        public UserViewHolder(DeveloperListItemViewMvc developerListItemViewMvc){
            super(developerListItemViewMvc.getUserView());
            mDeveloperListItemViewMvc = developerListItemViewMvc;
        }
    }

    class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(LoaderViewMvc loaderViewMvc) {
            super(loaderViewMvc.getLoaderView());
        }
    }


}

