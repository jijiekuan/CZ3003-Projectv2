package com.example.zadmin.relaxia.Fragments.MemoryGameFragments.ConnectTheDotsFragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.Engine.ScreenController;
import com.example.zadmin.relaxia.R;
import com.example.zadmin.relaxia.events.ConnectTheDotsEvents.StartConnectDotsEvent;

public class CTDMenuFragment extends Fragment {
    //EditText mSelectNumDots;
    //Button mStartButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ctd_menu_fragment, container, false);
        //mSelectNumDots = (EditText) view.findViewById(R.id.editTextDifficulty);
        //mStartButton = (Button) view.findViewById(R.id.buttonStartGame);

        /*mStartButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }
        );*/

        Shared.eventBus.notify(new StartConnectDotsEvent());
        return view;
    }

}
