package com.example.ogan.listofdevelopersinlagosgithub.screens.developerviews;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.R;
import com.example.ogan.listofdevelopersinlagosgithub.screens.common.controllers.BaseActivity;
import com.example.ogan.listofdevelopersinlagosgithub.viewmodel.ListOfDevelopersViewModel;
import com.example.ogan.listofdevelopersinlagosgithub.viewmodel.ListOfDevelopersViewModelFactory;

import retrofit2.Response;


public class ListOfDevelopersActivity extends BaseActivity implements ListOfDevelopersViewMvc.Listener {

    private ListOfDevelopersViewMvc mListOfDevelopersViewMvc;

    private ListOfDevelopersViewModel mListOfDevelopersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListOfDevelopersViewMvc = getPresentationComponent().getViewMvcFactory().getListOfDevelopersViewMvc(null);
        mListOfDevelopersViewMvc.registerListener(this);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, getPresentationComponent().getViewMvcFactory());

        ListOfDevelopersViewModelFactory mListOfDevelopersViewModelFactory = new ListOfDevelopersViewModelFactory(this, recyclerAdapter);

        mListOfDevelopersViewModel = ViewModelProviders.of(this, mListOfDevelopersViewModelFactory).get(ListOfDevelopersViewModel.class);

        mListOfDevelopersViewMvc.setAdapter(mListOfDevelopersViewModel.getRecyclerAdapter());

        setContentView(mListOfDevelopersViewMvc.getRootView());

        mListOfDevelopersViewModel.getErrorLiveData().observe(this, new Observer<Throwable>() {
            @Override
            public void onChanged(@Nullable Throwable throwable) {
                if (throwable != null){
                    mListOfDevelopersViewMvc.hideProgressBar();
                    Toast.makeText(getApplicationContext(),
                            "An error occurred while trying to get data. Please check network connection and try again. ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mListOfDevelopersViewModel.getResponseLiveData().observe(this, new Observer<Response<ApiResult>>() {
            @Override
            public void onChanged(@Nullable Response<ApiResult> apiResultResponse) {
                if (apiResultResponse != null){
                    Toast.makeText(getApplicationContext(),
                            "Response message: " + apiResultResponse.message() + " with code: " + apiResultResponse.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mListOfDevelopersViewModel.getApiResultLiveData().observe(this, new Observer<ApiResult>() {
            @Override
            public void onChanged(@Nullable ApiResult result) {
                mListOfDevelopersViewMvc.hideProgressBar();

                if (result != null){
                    mListOfDevelopersViewMvc.bindData(result.getItems());
                } else {
                    Toast.makeText(ListOfDevelopersActivity.this, "No data", Toast.LENGTH_LONG).show();
                }
            }
        });

        mListOfDevelopersViewModel.getDisplayLoadingFooter().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean display) {
                if (display != null){
                    if (display){
                        mListOfDevelopersViewMvc.showLoadingFooter();
                    } else {
                        mListOfDevelopersViewMvc.removeLoadingFooter();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.refresh_menu) {
            refreshItems();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshItems() {
        mListOfDevelopersViewMvc.clearData();
        mListOfDevelopersViewModel.refresh();
    }


    @Override
    public void loadMoreItems() {
        mListOfDevelopersViewModel.loadMoreItems();
    }

    @Override
    public boolean isLoading() {
        return mListOfDevelopersViewModel.getIsLoading();
    }

    @Override
    public void onRefresh() {
        refreshItems();
        mListOfDevelopersViewMvc.stopRefreshing();
    }

    @Override
    public int getTotalPageCount() {
        return mListOfDevelopersViewModel.getTotalPages();
    }

    @Override
    public boolean isLastPage() {
        return mListOfDevelopersViewModel.getIsLastPages();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListOfDevelopersViewMvc.unregisterListener(this);
    }

}
