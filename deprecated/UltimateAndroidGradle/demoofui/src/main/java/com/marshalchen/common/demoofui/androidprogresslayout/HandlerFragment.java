package com.marshalchen.common.demoofui.androidprogresslayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.simplemodule.ProgressLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Handler mHandler = new Handler();

    public static HandlerFragment newInstance() {
        return new HandlerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_progress_layout_fragment_handler, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        final ProgressLayout progressLayout = (ProgressLayout) view.findViewById(R.id.progress);

        progressLayout.setProgress(true);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<String> items = new ArrayList<String>();
                for (int i = 0; i < 100; i++) {
                    items.add("Item " + i);
                }
                listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items));
                progressLayout.setProgress(false);
            }
        }, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = (String) parent.getItemAtPosition(position);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.root_container, TextFragment.newInstance(text))
                .addToBackStack(null)
                .commit();
    }
}
