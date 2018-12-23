package com.example.ogan.listofdevelopersinlagosgithub;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ogan.listofdevelopersinlagosgithub.APIgson.UserGson.UserApi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Belema Ogan on 12/21/2018.
 */

public class DeveloperDetailViewMvcImpl implements View.OnClickListener, DeveloperDetailViewMvc {

    private final View mRootView;
    private TextView mTxtUrlTextView;
    private TextView mTxtFullNameTextView;
    private TextView mRepoTextView;
    private FloatingActionButton mButton;
    private CardView mCardView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private final CircleImageView mAvatarView;
    private final List<Listener> mListeners = new ArrayList<>();

    public DeveloperDetailViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        mRootView = inflater.inflate(R.layout.developer_details_activity, parent, false);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_detail);
        mTxtUrlTextView = (TextView) findViewById(R.id.user_url);
        mAvatarView = (CircleImageView) findViewById(R.id.user_pic);
        mButton = (FloatingActionButton) findViewById(R.id.fabBtn);
        mTxtFullNameTextView = (TextView) findViewById(R.id.user_name);
        mRepoTextView = (TextView) findViewById(R.id.repo_number);
        mCardView = (CardView) findViewById(R.id.course_card);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mButton.setOnClickListener(this);
    }

    @Override
    public void registerListener(Listener listener){
        mListeners.add(listener);
    }

    @Override
    public void unregisterListener(Listener listener){
        mListeners.remove(listener);
    }

    private Context getContext(){
        return getRootView().getContext();
    }

    private View findViewById(int id){
        return getRootView().findViewById(id);
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    @Override
    public void displayData(UserApi userResult, String userUrl) {
        String fullName = userResult.getName();
        if (fullName != null) {
            mTxtFullNameTextView.setText(fullName);
        }
        int repoNumber = userResult.getPublicRepos();
        String repositories = getContext().getResources().getString(R.string.repositories, repoNumber);
        mRepoTextView.setText(repositories);
        mTxtUrlTextView.setText(userUrl);
    }

    @Override
    public void showCardView() {
        mCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {
        mToolbar.setTitle(toolbarTitle);
    }

    @Override
    public Toolbar getToolBar() {
        return mToolbar;
    }

    @Override
    public void setProfileImage(String imageUrl) {
        Picasso.with(getContext()).load(imageUrl).into(mAvatarView);
    }

    @Override
    public void customiseView(String imageUrl, final Window window) {
        Picasso.with(getContext()).load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Palette.from(bitmap)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
                                        Palette.Swatch swatch = palette.getVibrantSwatch();
                                        Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                                        Palette.Swatch muted = palette.getMutedSwatch();
                                        if (swatch != null) {
                                            //setting color of toolbar and title text
                                            customiseToolbar(swatch.getRgb(),swatch.getTitleTextColor());
                                        }
                                        if (muted != null) {
                                            //setting color of fab
                                            customiseFloatingActionBar(ColorStateList.valueOf(muted.getRgb()));
                                        }
                                        if (darkVibrant != null) {
                                            //changing the color of status bar from lollipop and above from palette
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                                window.setStatusBarColor(darkVibrant.getRgb());
                                            }
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
    }

    @Override
    public void customiseToolbar(int toolbarBackgroundColor, int toolbarTitleTextColor) {
        mToolbar.setBackgroundColor(toolbarBackgroundColor);
        mToolbar.setTitleTextColor(toolbarTitleTextColor);
    }

    @Override
    public void customiseFloatingActionBar(ColorStateList colorStateList) {
       mButton.setBackgroundTintList(colorStateList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fabBtn:
                for (Listener listener : mListeners){
                    listener.floatingActionButtonClicked();
                }
                break;
        }
    }
}
