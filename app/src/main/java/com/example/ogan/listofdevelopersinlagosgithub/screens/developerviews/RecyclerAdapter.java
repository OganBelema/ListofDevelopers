package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.ogan.listofdevelopersinlagosgithub.model.items.Item;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ViewMvc;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ogan on 8/15/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DeveloperListItemViewMvc.Listener {

    private static final int ITEM = 0;
    private static final int LOADING = 1;


    private final List<Item> mItemList = new ArrayList<>();
    private Context mContext;
    private ViewMvcFactory mViewMvcFactory;

    private boolean mIsLoadingAdded = false;

    public RecyclerAdapter(Context context, ViewMvcFactory viewMvcFactory) {
        mContext = context;
        mViewMvcFactory = viewMvcFactory;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ITEM:
                DeveloperListItemViewMvc developerListItemViewMvc =
                        mViewMvcFactory.getDeveloperListItemViewMvc(parent);
                viewHolder = new UserViewHolder(developerListItemViewMvc);
                developerListItemViewMvc.registerListener(this);
                break;
            case LOADING:
                ViewMvc viewMvc = mViewMvcFactory.getViewMvc(parent);
                viewHolder = new LoadingVH(viewMvc);
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
            super(developerListItemViewMvc.getRootView());
            mDeveloperListItemViewMvc = developerListItemViewMvc;
        }
    }

    class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(ViewMvc viewMvc) {
            super(viewMvc.getRootView());
        }
    }


}

