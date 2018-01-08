package com.srgpanov.poker_rankings.Presenter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.srgpanov.poker_rankings.Model.Managers.AuthLoader;
import com.srgpanov.poker_rankings.Model.Managers.OPRManager;
import com.srgpanov.poker_rankings.View.AuthorizationInterface;

import java.util.Map;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by srgpanov on 24.08.2017.
 */

public class AuthorizationActivityPresenter implements AuthorizationPresenter, LoaderManager.LoaderCallbacks<Boolean> {
    private AppCompatActivity mActivity;
    private AuthorizationInterface mAuthorizationInterface;
    private OPRManager mManager;

    private final Pattern emailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


    public AuthorizationActivityPresenter(AuthorizationInterface authorizationInterface) {
        mAuthorizationInterface = authorizationInterface;
    }

    @Override
    public void attachView(AuthorizationInterface view) {
        mActivity = (AppCompatActivity) view;
        mManager = new OPRManager();
    }

    @Override
    public void detachView() {
        mActivity = null;


    }

    @Override
    public void viewIsReady() {


    }

    @Override
    public void Auth(String login, String password) {
        Bundle bundle= new Bundle();
        bundle.putString("Login",login);
        bundle.putString("Password",password);

        mActivity.getSupportLoaderManager().restartLoader(101,bundle,this);

    }


    @Override
    public void onForgotPassword() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.officialpokerrankings.com/redir.asp?register=50"));
        mActivity.startActivity(intent);

    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new AuthLoader(mActivity, mManager, args);
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean data) {
        Log.d("RX",data.toString());
        if (data){

            mAuthorizationInterface.doOnSuccess();
        } else mAuthorizationInterface.doOnError();
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {

    }




}
