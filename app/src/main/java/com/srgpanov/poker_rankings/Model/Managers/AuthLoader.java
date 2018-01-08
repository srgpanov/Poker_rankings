package com.srgpanov.poker_rankings.Model.Managers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

/**
 * Created by srgpanov on 26.08.2017.
 */

public class AuthLoader extends AsyncTaskLoader<Boolean> {
    private OPRManager mManager;
    private String mLogin;
    private String mPassword;
    private Context mContext;

    public AuthLoader(Context context, OPRManager manager, Bundle bundle) {
        super(context);
        mContext = context;
        mManager = manager;
        mLogin = bundle.getString("Login");
        mPassword = bundle.getString("Password");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public Boolean loadInBackground() {
        try {
            OPRManager.AuthResponse authResponse =mManager.authOnOPR(mLogin,mPassword);

            if(authResponse.isAuth()){
                mManager.saveCookie(authResponse.getCoockie(),mContext);
                return true;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
