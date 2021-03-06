package com.oganbelema.listofdevelopersinlagosgithub.screens.developerviews;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.palette.graphics.Palette;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.oganbelema.listofdevelopersinlagosgithub.R;
import com.oganbelema.listofdevelopersinlagosgithub.model.users.UserApi;
import com.oganbelema.listofdevelopersinlagosgithub.screens.common.views.BaseObservableViewMvc;


import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Belema Ogan on 12/21/2018.
 */

public class DeveloperDetailViewMvcImpl extends BaseObservableViewMvc<DeveloperDetailViewMvc.Listener>
        implements View.OnClickListener, DeveloperDetailViewMvc {

    private TextView mTxtUrlTextView;
    private TextView mTxtFullNameTextView;
    private TextView mRepoTextView;
    private FloatingActionButton mButton;
    private CardView mCardView;
    private Toolbar mToolbar;
    private ProgressBar mProgressBar;
    private final CircleImageView mAvatarView;

    public DeveloperDetailViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.developer_details_activity, parent, false));
        mProgressBar = (ProgressBar) findViewById(R.id.pb_detail);
        mTxtUrlTextView = (TextView) findViewById(R.id.user_url);
        mAvatarView = (CircleImageView) findViewById(R.id.user_pic);
        mButton = (FloatingActionButton) findViewById(R.id.fabBtn);
        mTxtFullNameTextView = (TextView) findViewById(R.id.user_name);
        mRepoTextView = (TextView) findViewById(R.id.repo_number);
        mCardView = (CardView) findViewById(R.id.info_card);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mButton.setOnClickListener(this);
    }

    @Override
    public void displayData(UserApi userResult) {
        String fullName = userResult.getName();
        if (fullName != null) {
            mTxtFullNameTextView.setText(fullName);
        }
        int repoNumber = userResult.getPublicRepos();
        String repositories = getContext().getResources().getString(R.string.repositories, repoNumber);
        mRepoTextView.setText(repositories);
        mTxtUrlTextView.setText(userResult.getHtmlUrl());
    }

    @Override
    public void showCardView() {
        mCardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCardView(){
        mAvatarView.setVisibility(View.GONE);
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
    public void setProfileImage(Bitmap bitmap) {
        mAvatarView.setImageBitmap(bitmap);
    }

    @Override
    public void setProfileImage(Drawable drawable) {
        mAvatarView.setImageDrawable(drawable);
    }

    @Override
    public void customiseView(Bitmap bitmap, final Window window) {
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
    public void customiseToolbar(int toolbarBackgroundColor, int toolbarTitleTextColor) {
        mToolbar.setBackgroundColor(toolbarBackgroundColor);
        mToolbar.setTitleTextColor(toolbarTitleTextColor);
    }

    @Override
    public void customiseFloatingActionBar(ColorStateList colorStateList) {
       mButton.setBackgroundTintList(colorStateList);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(getRootView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabBtn) {
            for (Listener listener : getListeners()) {
                listener.floatingActionButtonClicked();
            }
        }
    }
}
