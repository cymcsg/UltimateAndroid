package com.marshalchen.common.demoofui.blurdialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;
import com.marshalchen.common.uimodule.blurdialogfragment.SupportBlurDialogFragment;


/**
 * Simple fragment with blur effect behind.
 */
public class SampleSupportDialogFragment extends SupportBlurDialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View customView = getActivity().getLayoutInflater().inflate(R.layout.blur_dialog_dialog_fragment, null);
        TextView label = ((TextView) customView.findViewById(R.id.textView));
        label.setMovementMethod(LinkMovementMethod.getInstance());
        Linkify.addLinks(label, Linkify.WEB_URLS);
        builder.setView(customView);
        return builder.create();
    }
}
