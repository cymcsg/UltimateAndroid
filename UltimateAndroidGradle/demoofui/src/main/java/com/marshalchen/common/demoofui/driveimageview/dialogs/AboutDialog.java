package com.marshalchen.common.demoofui.driveimageview.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.marshalchen.common.demoofui.R;


public class AboutDialog extends Dialog {

    private Button closeButton;

    public AboutDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.drive_image_view_aboutdialog);

        closeButton = (Button) findViewById(R.id.about_submit);
        closeButton.setEnabled(true);
        closeButton.setOnClickListener(new View.OnClickListener() {

                                           @Override
                                           public void onClick(View v) {
                                               dismiss();
                                           }
                                       }

        );

    }

}