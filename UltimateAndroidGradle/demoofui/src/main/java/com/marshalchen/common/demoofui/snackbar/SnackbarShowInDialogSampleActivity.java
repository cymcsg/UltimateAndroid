package com.marshalchen.common.demoofui.snackbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.marshalchen.common.demoofui.R;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.OnItemClickListener;

public class SnackbarShowInDialogSampleActivity extends ActionBarActivity {

    private static final String TAG = SnackbarShowInDialogSampleActivity.class.getSimpleName();
    private static final String FRAGMENT_TAG_MY_DIALOG = "MyDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack_bar_activity_show_in_dialog_sample);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button button = (Button) findViewById(android.R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(new MyDialogFragment(), FRAGMENT_TAG_MY_DIALOG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class MyDialogFragment extends DialogFragment implements OnItemClickListener {
        private ViewGroup mSnackbarContainer;
        private ListView mListView;

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Context context = getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            View view = LayoutInflater.from(context).inflate(R.layout.snack_bar_fragment_dialog_list, null, false);

            mListView = (ListView) view.findViewById(android.R.id.list);
            mSnackbarContainer = (ViewGroup) view.findViewById(R.id.snackbar_container);

            List<String> data = new ArrayList<String>();

            for (int i = 0; i < 25; i++) {
                data.add(String.format("Item %d", (i + 1)));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    context, android.R.layout.simple_list_item_1, data);

            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(this);

            builder.setView(view);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            return builder.create();
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            super.onDismiss(dialog);

            mListView.setOnItemClickListener(null);
            mListView = null;
            mSnackbarContainer = null;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SnackbarManager.show(
                    Snackbar.with(getActivity())
                            .text(String.format("Item %d pressed", (position + 1)))
                            .actionLabel("Close")
                            .actionColor(Color.parseColor("#FF8A80"))
                            .duration(Snackbar.SnackbarDuration.LENGTH_LONG)
                            .attachToAbsListView(mListView),
                    mSnackbarContainer, true);

        }
    }
}