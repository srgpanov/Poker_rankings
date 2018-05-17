package com.srgpanov.poker_rankings.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;
import com.srgpanov.poker_rankings.Model.PlayersAdapter;
import com.srgpanov.poker_rankings.Presenter.MainActivityPresenter;
import com.srgpanov.poker_rankings.Presenter.MainActivityPresenterFactory;
import com.srgpanov.poker_rankings.Presenter.MainActivityPresenterInterface;
import com.srgpanov.poker_rankings.Presenter.PresenterFactory;
import com.srgpanov.poker_rankings.R;

import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

import static com.srgpanov.poker_rankings.Presenter.MainActivityPresenter.PERMISSION_REQUEST_CODE;

public class MainActivity extends BaseActivity<MainActivityPresenter,MainActivityInterface> implements MainActivityInterface {



    private RecyclerView mRecyclerView;
    private PlayersAdapter playersAdapter;
    private FloatingActionButton mFloatingActionButton;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setVisibility(View.INVISIBLE);
        Log.d("scrnPath","create");



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("scrnPath","start");
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFloatingActionButton.setOnClickListener(view -> presenter.searchPlayers());
    }

    @NonNull
    @Override
    protected PresenterFactory<MainActivityPresenter> getPresenterFactory() {
        return new MainActivityPresenterFactory();
    }

    @Override
    protected void onPresenterCreatedOrRestored(@NonNull MainActivityPresenter presenter) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.searchPlayer(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                presenter.auth();
                return true;
            case R.id.choose_img:
                presenter.chooseScrn();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void showAllPlayers(List<PlayerProfile> players) {

        playersAdapter = new PlayersAdapter(players, new PlayersAdapter.onPlayersClickListener() {
            @Override
            public void onPlayersItemClick(int position, View view) {

            }
        });
        mRecyclerView.setAdapter(playersAdapter);
    }

    @Override
    public void showPlayers(PlayerProfile profile) {
        Log.d("Profile",profile.toString());
        if (playersAdapter == null) {
            List<PlayerProfile> players = new ArrayList<>();
            playersAdapter = new PlayersAdapter(players, new PlayersAdapter.onPlayersClickListener() {
                @Override
                public void onPlayersItemClick(int position, View view) {

                }
            });
            mRecyclerView.setAdapter(playersAdapter);

        }
        playersAdapter.notifyItemInserted(0);
        mRecyclerView.scrollToPosition(0);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }

                break;

            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        if (allowed) {
            //user granted all permissions we can perform our task.
            presenter.chooseScrn();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Storage Permissions denied.", Toast.LENGTH_SHORT).show();

                } else {
                    showNoStoragePermissionSnackbar();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                        String scrnPath = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0);
                        Log.d("scrnPath", scrnPath);
                        presenter.searchPlayers(scrnPath);
                }
                break;
        }
    }

    public void showNoStoragePermissionSnackbar() {
        Snackbar.make(MainActivity.this.findViewById(R.id.main_container), "Storage permission isn't granted", Snackbar.LENGTH_LONG)
                .setAction("SETTINGS", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openApplicationSettings();

                        Toast.makeText(getApplicationContext(),
                                "Open Permissions and grant the Storage permission",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    private void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, PERMISSION_REQUEST_CODE);
    }


}
