package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.oganbelema.listofdevelopersinlagosgithub.R;
import com.oganbelema.listofdevelopersinlagosgithub.model.items.Item;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.BaseObservableViewMvc;

import java.util.ArrayList;

import io.reactivex.annotations.Nullable;

/**
 * Created by Belema Ogan on 12/21/2018.
 */

public class ListOfDevelopersViewMvcImpl extends BaseObservableViewMvc<ListOfDevelopersViewMvc.Listener>
        implements SwipeRefreshLayout.OnRefreshListener, ListOfDevelopersViewMvc {

    private final RecyclerView mRecyclerView;
    private final ProgressBar mProgressBar;
    private final SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    private RecyclerAdapter mRecyclerAdapter;

    public ListOfDevelopersViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {

        setRootView(inflater.inflate(R.layout.list_of_developers_activity, parent, false));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        PaginationScrollListener paginationScrollListener = new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                for (Listener listener : getListeners()) {
                    listener.loadMoreItems();
                }
            }

            @Override
            public int getTotalPageCount() {
                int totalPageCount = 0;

                for (Listener listener : getListeners()) {
                    totalPageCount = listener.getTotalPageCount();
                }

                return totalPageCount;
            }

            @Override
            public boolean isLastPage() {
                boolean isLastPage = false;

                for (Listener listener : getListeners()) {
                    isLastPage = listener.isLastPage();
                }

                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                boolean isLoading = false;

                for (Listener listener : getListeners()) {
                    isLoading = listener.isLoading();
                }

                return isLoading;
            }
        };

        mRecyclerView.addOnScrollListener(paginationScrollListener);

    }

    @Override
    public void setAdapter(RecyclerAdapter recyclerAdapter) {
        mRecyclerAdapter = recyclerAdapter;
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onRefresh() {
        for (Listener listener: getListeners()){
            listener.onRefresh();
        }
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void bindData(ArrayList<Item> data) {
        if (mRecyclerAdapter != null){
            mRecyclerAdapter.addAll(data);
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getRootView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void clearData() {
        if (mRecyclerAdapter != null){
            mRecyclerAdapter.clear();
        }
    }

    @Override
    public void showLoadingFooter() {
        if (mRecyclerAdapter != null){
            mRecyclerAdapter.addLoadingFooter();
        }
    }

    @Override
    public void removeLoadingFooter() {
        if (mRecyclerAdapter != null){
            mRecyclerAdapter.removeLoadingFooter();
        }
    }
}
