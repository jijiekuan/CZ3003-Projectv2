package com.example.zadmin.relaxia.DataAnalysis;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zadmin.relaxia.Common.Shared;
import com.example.zadmin.relaxia.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.zadmin.relaxia.R.id.graphView;

/**
 * Created by Shide on 4/4/17.
 */

public class ViewDataFragment extends Fragment {

    private GraphView mGraphView;
    private DataPoint[] values;
    private ScoreDataBaseHandler mScoreDataBaseHandler = ScoreDataBaseHandler.getInstance(Shared.context);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_data_fragment, container, false);

        values = mScoreDataBaseHandler.dbToCoords();

        mGraphView = (GraphView) view.findViewById(graphView);
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(values);
        mGraphView.addSeries(lineGraphSeries);

        mGraphView.getViewport().setXAxisBoundsManual(true);
        mGraphView.getViewport().setMinX(0);
        mGraphView.getViewport().setMaxX(6);


        mGraphView.getViewport().setScrollable(true);

        mGraphView.setTitle("Child's Progress Report");
        mGraphView.setTitleTextSize(48);

        mGraphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

        mGraphView.getGridLabelRenderer().setHorizontalAxisTitle("Attempts");
        mGraphView.getGridLabelRenderer().setHorizontalAxisTitleTextSize(48);


        mGraphView.getGridLabelRenderer().setVerticalAxisTitle("Stars Earned");
        mGraphView.getGridLabelRenderer().setVerticalAxisTitleTextSize(48);

        mGraphView.getViewport().setScrollable(true);






        return view;

    }

}
