package com.marshalchen.common.demoofui.recyclerviewstickyheaders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.demoofui.recyclerviewstickyheaders.adapters.BigramHeaderAdapter;
import com.marshalchen.common.demoofui.recyclerviewstickyheaders.adapters.InitialHeaderAdapter;
import com.marshalchen.common.demoofui.recyclerviewstickyheaders.adapters.PersonAdapter;
import com.marshalchen.common.demoofui.recyclerviewstickyheaders.data.PersonDataProvider;
import com.marshalchen.common.uimodule.recyclerviewstickyheaders.StickyHeadersBuilder;
import com.marshalchen.common.uimodule.recyclerviewstickyheaders.StickyHeadersItemDecoration;


/**
 * Created by aurel on 22/09/14.
 */
public class RecyclerViewStickyHeadersActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private RecyclerView list;
    private StickyHeadersItemDecoration top;
    private StickyHeadersItemDecoration overlay;
    private PersonDataProvider personDataProvider;
    private PersonAdapter personAdapter;
    private Spinner samplesSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_sticky_head_activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        list = (RecyclerView)findViewById(R.id.list);
        samplesSpinner = (Spinner)findViewById(R.id.samples_spinner);

        list.setLayoutManager(new LinearLayoutManager(RecyclerViewStickyHeadersActivity.this, LinearLayoutManager.VERTICAL, false));

        personDataProvider = new PersonDataProvider();
        personAdapter = new PersonAdapter(personDataProvider);

        top = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(new BigramHeaderAdapter(personDataProvider.getItems()))
                .build();

        overlay = new StickyHeadersBuilder()
                .setAdapter(personAdapter)
                .setRecyclerView(list)
                .setStickyHeadersAdapter(new InitialHeaderAdapter(personDataProvider.getItems()), true)
                .build();


        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.recycler_view_stick_header);

        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.add_item) {
                            int addedPosition = personDataProvider.insertAfter(list.getChildPosition(list.getChildAt(0)));
                            personAdapter.notifyItemInserted(addedPosition);
                            return true;
                        }
                        else if (item.getItemId() == R.id.github) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/eowise/recyclerview-stickyheaders/"));
                            startActivity(browserIntent);
                            return true;
                        }

                        return false;
                    }
                }
        );

        samplesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    list.setAdapter(personAdapter);
                    list.removeItemDecoration(overlay);
                    list.addItemDecoration(top);
                }
                else {
                    list.setAdapter(personAdapter);
                    list.removeItemDecoration(top);
                    list.addItemDecoration(overlay);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
