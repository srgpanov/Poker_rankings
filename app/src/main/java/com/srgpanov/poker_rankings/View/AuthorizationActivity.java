package com.srgpanov.poker_rankings.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.srgpanov.poker_rankings.Presenter.AuthorizationActivityPresenter;
import com.srgpanov.poker_rankings.Presenter.AuthorizationPresenter;
import com.srgpanov.poker_rankings.R;
import com.srgpanov.poker_rankings.View.AuthorizationInterface;

import io.reactivex.Observable;

/**
 * A login screen that offers login via email/password.
 */
public class AuthorizationActivity extends AppCompatActivity  implements AuthorizationInterface {
    private EditText mLoginET;
    private EditText mPasswordET;
    private Button mAuthBtn;
    private TextView mForgotPswdTv;
    private AuthorizationPresenter mPresenter;

    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        Log.d("RX","Oncreate");
        mPresenter = new AuthorizationActivityPresenter(this);
        mPresenter.attachView(this);
        bindViews();
        mPresenter.viewIsReady();


        // Set up the login form.

    }

    @Override
    public void doOnSuccess() {
        Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void doOnError() {
        Toast.makeText(this,"Ошибка при авторизации",Toast.LENGTH_LONG).show();
    }


    private void bindViews() {
        mLoginET = (EditText)findViewById(R.id.login_email_et);
        mPasswordET = (EditText)findViewById(R.id.login_password_et);
        mAuthBtn =(Button)findViewById(R.id.login_btn);
        mAuthBtn.setOnClickListener(v -> {
            Log.d("RX",mLoginET.getText().toString());
            Log.d("RX",mPasswordET.getText().toString());
            mPresenter.Auth(mLoginET.getText().toString(),mPasswordET.getText().toString());

        });

        mForgotPswdTv =(TextView)findViewById(R.id.remember_txt);
        mForgotPswdTv.setOnClickListener(v -> mPresenter.onForgotPassword());


    }
}

