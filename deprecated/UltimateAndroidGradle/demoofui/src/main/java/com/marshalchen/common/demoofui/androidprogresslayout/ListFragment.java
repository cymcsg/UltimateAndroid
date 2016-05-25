package com.marshalchen.common.demoofui.androidprogresslayout;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.marshalchen.common.demoofui.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends android.support.v4.app.ListFragment {

    public static final String HANDLER = "Handler";
    public static final String WEB_VIEW = "WebView";

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<String> items = new ArrayList<String>();
        items.add(HANDLER);
        items.add(WEB_VIEW);
        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = ((ProgressLayoutActivity) getActivity()).getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) l.getAdapter().getItem(position);

        if (item.equals(HANDLER)) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.root_container, HandlerFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        } else if (item.equals(WEB_VIEW)) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.root_container, WebViewFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }

        ((ActionBarActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
