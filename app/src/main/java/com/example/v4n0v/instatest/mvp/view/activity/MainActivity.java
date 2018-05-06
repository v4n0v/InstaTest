package com.example.v4n0v.instatest.mvp.view.activity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.v4n0v.instatest.App;
import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.event_bus.TabSelectBus;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentColor;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFavorites;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentFeed;
import com.example.v4n0v.instatest.mvp.view.fragments.FragmentListener;
import com.example.v4n0v.instatest.mvp.view.fragments.adapters.CustomFragmentPA;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import timber.log.Timber;

import static android.widget.Toast.LENGTH_SHORT;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentListener, View.OnClickListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_sheet)
    View bottomView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

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

        bottomView.setOnClickListener(this);
        initFragmentPA();

        mViewPager.setAdapter(customFragmentPA);

        init();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initFragmentPA() {
        fragmentFeed = FragmentFeed.newInstance(null);


        fragmentFavorites = FragmentFavorites.newInstance(null);


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
                if (tab.getText().equals(getResources().getString(R.string.favorites))) {
                    TabSelectBus.getBus().post(0);
                    Timber.d("TabSelectBus posted 0 after tab '"+getResources().getString(R.string.favorites)+"' selected");
                }
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

    public void showOptions() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater li = LayoutInflater.from(this);
        builder.setTitle(R.string.action_settings);
        final View additionView = li.inflate(R.layout.options_layout, null);
        builder.setView(additionView);
        builder.setCancelable(true);
        // получаю настройки, узнаю, какя тема выбрана
        // переключаю в выбранное состояние
        boolean isSwitched = Paper.book("theme").read("theme_switch", false);
        if (isSwitched) {
            Switch themeSwitch = additionView.findViewById(R.id.switch_theme);
            themeSwitch.setChecked(true);
        }

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Switch themeSwitch = additionView.findViewById(R.id.switch_theme);
                if (themeSwitch.isChecked()) {
                    Toast.makeText(MainActivity.this, "Теменая тема", Toast.LENGTH_SHORT).show();
                    Paper.book("theme").write("theme_switch", true);
                } else {
                    Toast.makeText(MainActivity.this, "Светлая тема", Toast.LENGTH_SHORT).show();
                    Paper.book("theme").write("theme_switch", false);

                }

                Intent reloadIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(reloadIntent);
//
            }
        });
        builder.show();
    }


    private void init() {

        sheetBehavior = BottomSheetBehavior.from(bottomView);
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // этот код скрывает кнопку сразу же
                // и отображает после того как нижний экран полностью свернется
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {

                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // fab.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
            }

        });
        fab.setOnClickListener(new View.OnClickListener() {
                        @Override
            public void onClick(View view) {
                         toast("FAB click");
            }
        });
        initTabs();
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
//            toast("Регистрация невозможна");
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
            showOptions();
            Toast.makeText(MainActivity.this, "Выбор темы", LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "Поделиться", LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this, "Отправить", LENGTH_SHORT).show();
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void toast(String msg){
        Toast.makeText(MainActivity.this, msg, LENGTH_SHORT).show();
    }

    public void save(View view) {
        Toast.makeText(MainActivity.this, "Сохранение", LENGTH_SHORT).show();
        // Snackbar.make(view, "Сохранение данных об авто", Snackbar.LENGTH_SHORT).show();
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    void applyColors() {

        boolean isDarkTheme = Paper.book("theme").read("theme_switch", false);

        int colorIntText;
        int colorBG;
        int colorGroupText;
        if (isDarkTheme) {
            colorIntText = getResources().getColor(R.color.colorWhite);
            colorBG = getResources().getColor(R.color.colorGrayDark);
            colorGroupText = getResources().getColor(R.color.colorGrayLight);
        } else {
            colorIntText = getResources().getColor(R.color.colorGrayDark);
            colorGroupText = getResources().getColor(R.color.colorGrayDark);
            colorBG = getResources().getColor(R.color.colorWhite);
        }

        MenuItem menuItem = navigationView.getMenu().getItem(2);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(colorGroupText), 0, s.length(), 0);
        menuItem.setTitle(s);

        ColorStateList csl = ColorStateList.valueOf(colorIntText);
        navigationView.setItemTextColor(csl);
        navigationView.setItemIconTintList(csl);
        navigationView.setBackgroundColor(colorBG);

    }


    @Override
    public void onFragmentElementClick() {
        applyColors();
    }


    @Override
    public void onClick(View v) {
        if (sheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED)
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        else sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

    }


}
