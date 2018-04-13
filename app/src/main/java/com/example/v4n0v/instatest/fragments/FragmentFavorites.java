package com.example.v4n0v.instatest.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.v4n0v.instatest.R;

import java.util.ArrayList;
import java.util.List;



public class FragmentFavorites extends Fragment {
    public static FragmentFavorites newInstance(Bundle bundle) {
        FragmentFavorites currentFragment = new FragmentFavorites();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    private RecyclerView recyclerView;
    private RecyclerOrderAdapter adapter;
    View view;
    List<Photo> photoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);


        photoList = new ArrayList<>();
        int imgId = R.drawable.foto;
        Photo photo1 = new Photo("sasasdasd", imgId);
        Photo photo2 = new Photo("sasasdsdasdasd", imgId);
        Photo photo3 = new Photo("hgntydbfg", imgId);
        photoList.add(photo1);
        photoList.add(photo2);
        photoList.add(photo3);

        initViews();
        //   addItem();
        return view;
    }

    private void initViews() {


        initRecyclerView();


    }

    public void  initRecyclerView(){
//        recyclerView=view.findViewById(R.id.recycler_order);
//
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter = new RecyclerOrderAdapter(photoList);
//
//        recyclerView.setAdapter(adapter);

    }
}