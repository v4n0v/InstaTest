package com.example.v4n0v.instatest.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.v4n0v.instatest.R;
import com.example.v4n0v.instatest.data.Preferences;

/**
 * Created by v4n0v on 15.03.18.
 */

public class FragmentColor extends Fragment implements View.OnClickListener {
    Switch themeSwitch;
    FragmentListener mainActivity;

    @Override
    public void onAttach(Context context) {
        mainActivity = (FragmentListener) context;
        super.onAttach(context);
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor ed;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color, container, false);
        initViews();
        themeSwitch = view.findViewById(R.id.switch_theme);
        themeSwitch.setOnClickListener(this);

        sharedPreferences = getActivity().getSharedPreferences(Preferences.APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean isSwiched = sharedPreferences.getBoolean(Preferences.NAV_THEME_DARK, false);
        if (isSwiched)
            themeSwitch.setChecked(true);
            else
                themeSwitch.setChecked(false);

        //   addItem();
        return view;
    }

    private void initViews() {


        //  initRecyclerView();


    }


    @Override
    public void onClick(View view) {
        ed = sharedPreferences.edit();
        if (themeSwitch.isChecked()) {
            Toast.makeText(getActivity(), "Теменая тема", Toast.LENGTH_SHORT).show();
            ed.putBoolean(Preferences.NAV_THEME_DARK, true);

        } else {
            Toast.makeText(getActivity(), "Светлая тема", Toast.LENGTH_SHORT).show();
            int colorIntText = getResources().getColor(R.color.colorWhite);
            int colorIntIco = getResources().getColor(R.color.colorWhite);
            ed.putBoolean(Preferences.NAV_THEME_DARK, false);
        }

        ed.apply();
        mainActivity.onFragmentElementClick();
        //   applyColors();
    }


}
