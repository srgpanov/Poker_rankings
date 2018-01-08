package com.srgpanov.poker_rankings.Model.Managers;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by srgpanov on 29.07.2017.
 */

 public class PlayersLoader extends AsyncTaskLoader<PlayerProfile> {
    private OPRManager mManager;
    private PlayerProfile mProfile;


    public PlayersLoader(Context context, OPRManager manager, Bundle bundle) {
        super(context);
        mManager = manager;
        mProfile = bundle.getParcelable("Player");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }



    @Override
    public PlayerProfile loadInBackground() {
        try {
            return mManager.searchProfile(mProfile);
        } catch (IOException e) {
            mProfile.setFounded(false);
            e.printStackTrace();
            return mProfile;
        }


    }
}
