package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.oganbelema.listofdevelopersinlagosgithub.common.Constants;
import com.oganbelema.listofdevelopersinlagosgithub.model.items.Item;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ViewMvc;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.ViewMvcFactory;

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
    private DeveloperListItemViewMvc mDeveloperListItemViewMvc;

    public RecyclerAdapter(Context context, ViewMvcFactory viewMvcFactory) {
        mContext = context;
        mViewMvcFactory = viewMvcFactory;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case ITEM:
                mDeveloperListItemViewMvc = mViewMvcFactory.getDeveloperListItemViewMvc(parent);
                viewHolder = new UserViewHolder(mDeveloperListItemViewMvc);
                mDeveloperListItemViewMvc.registerListener(this);
                break;
            case LOADING:
                ViewMvc viewMvc = mViewMvcFactory.getViewMvc(parent);
                viewHolder = new LoadingVH(viewMvc);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,  int position) {

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

    void addAll(List<Item> results) {
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

    void clear() {
        mIsLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    void addLoadingFooter() {
        mIsLoadingAdded = true;
        add(new Item());
    }

    void removeLoadingFooter() {
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
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mDeveloperListItemViewMvc.unregisterListener(this);
    }

    @Override
    public void onDevelopClicked(Item item) {
        Intent intent = new Intent(mContext, DeveloperDetailsActivity.class);
        String username = item.getLogin();
        String url = item.getHtmlUrl();
        String avatar = item.getAvatarUrl();

        intent.putExtra(Constants.USERNAME_KEY, username);
        intent.putExtra(Constants.URL_KEY, url);
        intent.putExtra(Constants.AVATAR_KEY, avatar);
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

        UserViewHolder(DeveloperListItemViewMvc developerListItemViewMvc){
            super(developerListItemViewMvc.getRootView());
            mDeveloperListItemViewMvc = developerListItemViewMvc;
        }
    }

    class LoadingVH extends RecyclerView.ViewHolder {

        LoadingVH(ViewMvc viewMvc) {
            super(viewMvc.getRootView());
        }
    }


}

