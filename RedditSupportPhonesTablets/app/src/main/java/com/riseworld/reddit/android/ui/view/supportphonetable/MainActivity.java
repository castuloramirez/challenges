package com.riseworld.reddit.android.ui.view.supportphonetable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.riseworld.reddit.android.ui.common.Controller;
import com.riseworld.reddit.android.ui.pojo.DataObject;
import com.riseworld.reddit.android.ui.util.PropertiesReader;
import com.riseworld.reddit.android.util.DetectConnection;
import com.riseworld.reddit.android.util.GlobalREDDIT;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_IS_SECONDARY_SHOWN = "EXTRA_IS_SECONDARY_SHOWN";
    private static final String EXTRA_NAVIGATION_SELECTION = "EXTRA_NAVIGATION_SELECTION";
    private static final String EXTRA_SHOULD_USE_UP = "EXTRA_SHOULD_USE_UP";

    protected boolean isTablet = false;
    protected boolean isSecondaryShown = false;
    String[] contentNames = new String[0];

    private Toolbar toolbar, secondaryToolbar;
    private boolean isDrawerOpen = false;
    private boolean shouldUseUp = false;
    private boolean isPortrait;
    private View mProgressView;
    private RedditServiceTask mAuthTask = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPortrait = getResources().getBoolean(R.bool.isPortrait);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        secondaryToolbar = (Toolbar) findViewById(R.id.secondaryToolbar);

        // set toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.logo);
        }

        if (DetectConnection
                .checkInternetConnection(this)) {

            readRedditOnline(savedInstanceState);
        } else {
            Toast.makeText(this,
                    PropertiesReader.getProperty("internet_off"),
                    Toast.LENGTH_LONG).show();

            readRedditOffline(savedInstanceState);
        }
        //android.support.v4.widget.DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer_layout.setVisibility(View.GONE);
    }

    /**
     * @param savedInstanceState
     */
    private void continueUI(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            replaceFragment();
        }

        // show secondary toolbar if needed
        if (isSecondaryShown) {
            setSecondaryToolbarVisibility();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // sync the toggle state after onRestoreInstanceState has occurred.
        //mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isTablet) {
            setSecondaryToolbarVisibility();
        }

        if (shouldUseUp) {
            setUpNavOn();
        } else {
            setUpNavOff();
        }
        // replaceFragment();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // don't display action bar menu items when drawer is open
/*        if (isDrawerOpen()) {
            menu.clear();
        }*/
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void setSecondaryToolbarVisibility() {
        secondaryToolbar.setVisibility(View.VISIBLE);
    }

    /**
     *
     */
    private void replaceFragment() {
        //Bundle bundle = getContentBundle(0);
        Fragment homeFragment = MainFragment.newInstance(new Bundle());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_container, homeFragment);
        ft.commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // only save cache state if the app is being put in the background, not
        // if the device is changing orientation
        int newOrientation = getResources().getConfiguration().orientation;
//        if (orientation == newOrientation && !calledFromActivity) {
        //getApplication().onSaveInstanceState(outState);
//        }
        // outState.putInt(EXTRA_NAVIGATION_SELECTION,
        //        mDrawerList.getSelectedItemPosition());
        outState.putBoolean(EXTRA_IS_SECONDARY_SHOWN, isSecondaryShown);
        outState.putBoolean(EXTRA_SHOULD_USE_UP, shouldUseUp);
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_container);
        if (currentFragment == null) {
            return;
        }
        // fragment to handle on back pressed,
        if (currentFragment instanceof FragmentOnBackPressedListener) {
            if (((FragmentOnBackPressedListener) currentFragment).onBackPressed()) {
            }
        }
    }

    public void setUpNavOn() {
        if (!isTablet || isPortrait) {
            shouldUseUp = true;
            //noinspection deprecation
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toolbar.setNavigationIcon(null);
                    onBackPressed();
                }
            });
        }
    }

    public void setUpNavOff() {
        if (!isTablet || isPortrait) {
            shouldUseUp = false;
            //     drawerToggle.setDrawerIndicatorEnabled(true);
            toolbar.setNavigationIcon(null);
        }

    }

    public void loadService() {
        try {
            Controller ctrl = new Controller();
            this.contentNames = ctrl.getDataSetAsStringArray();

            final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) this.getApplicationContext();
            globalOCTAVE.setCtrl(ctrl);

            ctrl.fillDatabase(this.getApplicationContext(), ctrl.getListData());

            //ArrayList<DataObject> arrayL= ctrl.getDataSetFromDB(this.getApplicationContext());
            //System.out.println("jauajauauajauaja"+arrayL.size());

        } catch (Exception e) {
            Toast.makeText(this,
                    PropertiesReader.getProperty("errorLoadingService") + ":" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    public void loadServiceFromDB() {
        try {
            Controller ctrl = new Controller();
            ctrl.getDataSetFromDB(this.getApplicationContext());
            if ((ctrl.getListData() != null) && (ctrl.getListData().size() > 0)) {
                //this.contentNames = ctrl.getDataSetAsStringArrayFromDB();

                final GlobalREDDIT globalOCTAVE = (GlobalREDDIT) this.getApplicationContext();
                globalOCTAVE.setCtrl(ctrl);
            }
        } catch (Exception e) {
            Toast.makeText(this,
                    PropertiesReader.getProperty("errorLoadingService") + ":" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }


    private void readRedditOffline(Bundle savedInstanceState) {
        try {
            loadServiceFromDB();
            continueUI(savedInstanceState);
        } catch (Exception e) {
            Toast.makeText(this,
                    PropertiesReader.getProperty("error") + ":" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void readRedditOnline(Bundle savedInstanceState) {
        try {
            mProgressView = findViewById(R.id.login_progress);
            // showProgress(true);
            mAuthTask = new RedditServiceTask();
            mAuthTask.setSavedIn(savedInstanceState);
            mAuthTask.execute((Void) null);
        } catch (Exception e) {
            Toast.makeText(this,
                    PropertiesReader.getProperty("error") + ":" + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        this.toolbar.setVisibility(show ? View.GONE : View.VISIBLE);
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public interface FragmentOnBackPressedListener {
        /**
         * Method callback to handle an activity's onBackPressed event
         *
         * @return true if the fragment consumed the back press
         */
        boolean onBackPressed();
    }

    /**
     * Represents an asynchronous RedditService query task used to get the JSON Info from
     * Reddit programming
     * the user.
     */
    private class RedditServiceTask extends AsyncTask<Void, Void, Boolean> {

        private Bundle savedIn;

        public void setSavedIn(Bundle savedIn) {
            this.savedIn = savedIn;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                loadService();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                continueUI(this.savedIn);
            } else {

            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
