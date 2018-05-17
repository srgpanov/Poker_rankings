package com.srgpanov.poker_rankings.Presenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;
import com.srgpanov.poker_rankings.Model.Managers.OPRManager;
import com.srgpanov.poker_rankings.Model.Managers.PlayersLoader;
import com.srgpanov.poker_rankings.Model.Managers.SkanManager;
import com.srgpanov.poker_rankings.R;
import com.srgpanov.poker_rankings.View.AuthorizationActivity;
import com.srgpanov.poker_rankings.View.MainActivity;
import com.srgpanov.poker_rankings.View.MainActivityInterface;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;

/**
 * Created by srgpa on 24.05.2017.
 */

public class MainActivityPresenter implements MainActivityPresenterInterface {
    private OPRManager mOPRManager;
    private SkanManager mSkanManager;
    private LoaderManager mLoaderManager;
    private MainActivity mActivity;
    private List<PlayerProfile> mProfileList;
    public static final int PERMISSION_REQUEST_CODE = 755;


    public MainActivityPresenter() {
        mProfileList = new ArrayList<>();
    }


    @Override
    public void onViewAttached(MainActivityInterface view) {
        Log.d("scrnPath","onViewAttached");
        mActivity = (MainActivity) view;
        if (mOPRManager == null) {
            mOPRManager = new OPRManager(mActivity);
        }
        if (mSkanManager == null) {
            mSkanManager = new SkanManager(mActivity);
        }
        if (mLoaderManager == null) {
            mLoaderManager = mActivity.getSupportLoaderManager();
        }
        view.showAllPlayers(mProfileList);
    }

    @Override
    public void onViewDetached() {
        Log.d("scrnPath","onViewDetached");
    }

    @Override
    public void onViewDestroyed() {
        Log.d("scrnPath","onViewDestroyed");
        mActivity = null;
    }

    @Override
    public void onDestroyed() {

    }

    @Override
    public void searchPlayer(String nickname) {
        PlayerProfile profile = new PlayerProfile();
        profile.setNickName(nickname);
        loadPlayers(true, profile, 200);

    }

    @Override
    public void searchPlayers() {

    }

    @Override
    public void searchPlayers(String scrnPath) {
        List<PlayerProfile> profileList = mSkanManager.getPlayersProfile(scrnPath);
        load(profileList, true);
    }

    private void load(List<PlayerProfile> profiles, boolean restart) {
        for (int i = 0; i < profiles.size(); i++) {
            loadPlayers(restart, profiles.get(i), profiles.get(i).getNickName().hashCode());
        }
    }

    private void loadPlayers(boolean restart, PlayerProfile profile, Integer id) {
        LoadPlayersCallbacks callbacks = new LoadPlayersCallbacks();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Player", profile);

        if (restart) {
            mLoaderManager.restartLoader(id, bundle, callbacks);
        } else {
            mLoaderManager.initLoader(id, bundle, callbacks);

        }
    }

    @Override
    public void auth() {
        Intent intent = new Intent(mActivity, AuthorizationActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void chooseScrn() {
        if (hasPermissions()) {
            FilePickerBuilder.getInstance()
                    .setMaxCount(1)
                    .setActivityTheme(R.style.Theme_AppCompat)
                    .enableCameraSupport(false)
                    .showFolderView(false)
                    .pickPhoto(mActivity);


        } else {
            requestPermissionWithRationale();
        }
    }

    //region Permissions
    private boolean hasPermissions() {
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            res = mActivity.checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPermissionWithRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            final String message = "Storage permission is needed to show files count";
            Snackbar.make(mActivity.findViewById(R.id.main_container), message, Snackbar.LENGTH_LONG)
                    .setAction("GRANT", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestPerms();
                        }
                    })
                    .show();
        } else {
            requestPerms();
        }
    }

    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mActivity.requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    //endregion
    private class LoadPlayersCallbacks implements LoaderCallbacks<PlayerProfile> {

        @Override
        public Loader<PlayerProfile> onCreateLoader(int id, Bundle args) {
            return new PlayersLoader(mActivity, mOPRManager, args);
        }

        @Override
        public void onLoadFinished(Loader<PlayerProfile> loader, PlayerProfile profile) {
            Log.d("data", profile.toString());
            mProfileList.add(0,profile);
            mActivity.showPlayers(profile);
        }

        @Override
        public void onLoaderReset(Loader<PlayerProfile> loader) {

        }
    }

}

