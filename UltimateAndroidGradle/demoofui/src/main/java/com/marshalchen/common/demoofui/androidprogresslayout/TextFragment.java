package com.marshalchen.common.demoofui.androidprogresslayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marshalchen.common.demoofui.R;

public class TextFragment extends Fragment {

    private static final String PARAM_TEXT = "TextFragment.PARAM_TEXT";

    public static TextFragment newInstance(String text) {
        Bundle args = new Bundle();
        args.putString(PARAM_TEXT, text);

        TextFragment textFragment = new TextFragment();
        textFragment.setArguments(args);
        return textFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.android_progress_layout_fragment_text, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String paramText = getArguments().getString(PARAM_TEXT);

        ((TextView) view.findViewById(R.id.text)).setText(paramText);
    }
}
