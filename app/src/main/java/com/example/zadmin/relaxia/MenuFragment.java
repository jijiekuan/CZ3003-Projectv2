package com.example.zadmin.relaxia;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.Engine.ScreenController;
import com.example.zadmin.relaxia.events.ConnectTheDotsEvents.StartConnectDotsEvent;
import com.example.zadmin.relaxia.events.ViewDataAnalysisEvent;

/**
 * Created by Shide on 4/4/17.
 */

public class MenuFragment extends Fragment {

    ImageView logo;
    Button mSelectPictureToWords;
    Button mSelectMemoryGame;
    Button mSelectConnectTheDots;
    Button mViewDataAnalysis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment, container, false);
        logo = (ImageView) view.findViewById(R.id.imageButton);
        mSelectMemoryGame = (Button) view.findViewById(R.id.select_memory_game);
        mSelectConnectTheDots = (Button) view.findViewById(R.id.select_connect_the_dots_game);
        mSelectPictureToWords = (Button) view.findViewById(R.id.select_picture_to_words);
        mViewDataAnalysis = (Button) view.findViewById(R.id.view_data_analysis);

        mSelectMemoryGame.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ScreenController.getInstance().openScreen(ScreenController.Screen.MG_MENU);
                    }
                }
        );

        mSelectConnectTheDots.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Shared.eventBus.notify(new StartConnectDotsEvent());
                    }
                }
        );
        mViewDataAnalysis.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Shared.eventBus.notify(new ViewDataAnalysisEvent());

                    }
                }
        );
        return view;

    }
}
