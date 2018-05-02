package com.example.v4n0v.instatest.mvp.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.v4n0v.instatest.App;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.data.AppDataMap;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentColor;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFeed;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentListener;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFavorites;
import com.example.v4n0v.instatest.mvp.view.fragments.adapters.CustomFragmentPA;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.bottom_sheet)
//    View bottomView;

//    @BindView(R.id.fab)
//    FloatingActionButton fab;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private BottomSheetBehavior<View> sheetBehavior;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    FragmentColor fragmentColor;
    // FragmentMain fragmentMain;
    private FragmentFavorites fragmentFavorites;
    private FragmentFeed fragmentFeed;

    private SharedPreferences sharedPreferences;
    @BindView(R.id.container)
    ViewPager mViewPager;
    private CustomFragmentPA customFragmentPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        customFragmentPA = new CustomFragmentPA(getSupportFragmentManager());
//
        initFragmentPA();

        mViewPager.setAdapter(customFragmentPA);
//
//        initTabs();
//
//         fragmentColor = new FragmentColor();
//        //fragmentMain = new FragmentMain();
        init();
        // fillFragment(fragmentMain);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFragmentPA() {
        fragmentFavorites = FragmentFavorites.newInstance(null);
        fragmentFeed = FragmentFeed.newInstance(null);
        App.getInstance().getAppComponent().inject(fragmentFeed);
        App.getInstance().getAppComponent().inject(fragmentFavorites);
        customFragmentPA.addFragment(fragmentFeed, "Лента");
        customFragmentPA.addFragment(fragmentFavorites, "Избранное");

    }

    private void initTabs() {

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, "onTabSelected: " + tab.getText());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.w(TAG, "onTabUnselected: " + tab.getText());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.i(TAG, "onTabReselected: " + tab.getText());
            }
        });

    }


    private void init() {

//
//        sheetBehavior = BottomSheetBehavior.from(bottomView);
//        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                // этот код скрывает кнопку сразу же
//// и отображает после того как нижний экран полностью свернется
//                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
//                    fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
//                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
//
//                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                // fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
//            }
//
//        });


        //    showElementsUI();
        applyColors();

    }


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            //fillFragment(fragmentMain);
        } else if (id == R.id.nav_colors) {
            fillFragment(fragmentColor);
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "Поделиться", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this, "Отправить", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void save(View view) {
        Toast.makeText(MainActivity.this, "Сохранение", Toast.LENGTH_SHORT).show();
        // Snackbar.make(view, "Сохранение данных об авто", Snackbar.LENGTH_SHORT).show();
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    void fillFragment(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_frame, fragment);

//
//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        //   SelectAutoFragment fragment = new SelectAutoFragment();
//        fragmentTransaction.replace(R.id.container_frame, fragment);
//        fragmentTransaction.commit();
    }

    void applyColors() {
//        sharedPreferences = getSharedPreferences(AppDataMap.APP_PREFERENCES, Context.MODE_PRIVATE);
//        boolean isDarkTheme = sharedPreferences.getBoolean(AppDataMap.NAV_THEME_DARK, false);
//
//
//        int colorIntText;
//        int colorBG;
//        int colorGroupText;
//        if (isDarkTheme) {
//            colorIntText = getResources().getColor(R.color.colorWhite);
//            colorBG = getResources().getColor(R.color.colorGrayDark);
//            colorGroupText = getResources().getColor(R.color.colorGrayLight);
//        } else {
//            colorIntText = getResources().getColor(R.color.colorGrayDark);
//            colorGroupText = getResources().getColor(R.color.colorGrayDark);
//            colorBG = getResources().getColor(R.color.colorWhite);
//        }
//
//        MenuItem menuItem = navigationView.getMenu().getItem(2);
//        SpannableString s = new SpannableString(menuItem.getTitle());
//        s.setSpan(new ForegroundColorSpan(colorGroupText), 0, s.length(), 0);
//        menuItem.setTitle(s);
//
//        ColorStateList csl = ColorStateList.valueOf(colorIntText);
//        navigationView.setItemTextColor(csl);
//        navigationView.setItemIconTintList(csl);
//        navigationView.setBackgroundColor(colorBG);

    }


//    void hideElementsUI() {
//        sheetBehavior.setHideable(true);
//        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//
//      //  fab.setVisibility(View.INVISIBLE);
////       bottomIco.setVisibility(View.INVISIBLE);
//    }
//
//    void showElementsUI() {
//        sheetBehavior.setHideable(false);
//        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//
//    //    fab.setVisibility(View.VISIBLE);
//        //      bottomIco.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onFragmentElementClick() {
        applyColors();
    }


}
