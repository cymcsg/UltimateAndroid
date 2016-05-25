package com.marshalchen.common.demoofui.driveimageview.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.marshalchen.common.demoofui.R;

import ch.haclyon.driveimageview.DriveImageModel;
import ch.haclyon.driveimageview.DriveImageView;

public class DetailFragment extends Fragment {
    private static final String ARRAY_POS = "position";
    private int arrayPosition;

    public static final int FOLDER_SEEKBAR_MAX = 120;
    public static final int FOLDER_SEEKBAR_PROGRESS = 14;

    public static final int WIDTH_SEEKBAR_MAX = 300;
    public static final int WIDTH_SEEKBAR_PROGRESS = 120;

    public static final int HEIGHT_SEEKBAR_MAX = 200;
    public static final int HEIGHT_SEEKBAR_PROGRESS = 60;

    public static final int OPACITY_SEEKBAR_MAX = 100;
    public static final int OPACITY_SEEKBAR_PROGRESS = 70;

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARRAY_POS, position);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {
        // empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            arrayPosition = getArguments().getInt(ARRAY_POS);
        }
//        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drive_image_view_fragment_detail, container, false);

        final DriveImageView imageView = (DriveImageView) view.findViewById(R.id.detail_driveImageView);
        imageView.setDriveImageModel(new DriveImageModel(MainFragment.sampleTexts[arrayPosition], "test", MainFragment.samplePictures[arrayPosition]));
        imageView.setBackgroundColor(MainFragment.sampleColours[arrayPosition]);
        imageView.setCustomHeight(MainFragment.sampleHeights[arrayPosition]);
        imageView.setAlphaValue(OPACITY_SEEKBAR_PROGRESS / 100.0f);

        Button animateButton = (Button) view.findViewById(R.id.detail_animatebutton);
        animateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.animateText();
            }
        });

        createOpacitySeekbar(view, imageView);

        createFolderEditText(view, imageView);

        createMainEditText(view, imageView);

        createHeightSeekBar(view, imageView);

        createWidthSeekBar(view, imageView);

        createFolderSeekBar(view, imageView);

        return view;
    }

    private void createFolderSeekBar(View view, final DriveImageView imageView) {
        SeekBar folderSeekBar = (SeekBar) view.findViewById(R.id.detail_drive_folder);
        folderSeekBar.setMax(FOLDER_SEEKBAR_MAX);
        folderSeekBar.setProgress(FOLDER_SEEKBAR_PROGRESS);
        folderSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                imageView.setFolderCorner(Float.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createWidthSeekBar(View view, final DriveImageView imageView) {
        SeekBar widthSeekBar = (SeekBar) view.findViewById(R.id.detail_drive_width);
        widthSeekBar.setMax(WIDTH_SEEKBAR_MAX);
        widthSeekBar.setProgress(WIDTH_SEEKBAR_PROGRESS);
        widthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                imageView.setCustomFolderSpacing(Float.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createHeightSeekBar(View view, final DriveImageView imageView) {
        SeekBar heightSeekBar = (SeekBar) view.findViewById(R.id.detail_drive_height);
        heightSeekBar.setMax(HEIGHT_SEEKBAR_MAX);
        heightSeekBar.setProgress(HEIGHT_SEEKBAR_PROGRESS);
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                imageView.setCustomHeight(Float.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void createMainEditText(View view, final DriveImageView imageView) {
        EditText mainEditText = (EditText) view.findViewById(R.id.detail_main);
        mainEditText.setText(imageView.getDriveImageModel().getMainTitle());
        mainEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                DriveImageModel model = imageView.getDriveImageModel();
                model.setMainTitle(editable.toString());
                imageView.setDriveImageModel(model);
            }
        });
    }

    private void createFolderEditText(View view, final DriveImageView imageView) {
        EditText folderEditText = (EditText) view.findViewById(R.id.detail_folder);
        folderEditText.setText(imageView.getDriveImageModel().getFolderTitle());
        folderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                DriveImageModel model = imageView.getDriveImageModel();
                model.setFolderTitle(editable.toString());
                imageView.setDriveImageModel(model);
            }
        });
    }

    private void createOpacitySeekbar(View view, final DriveImageView imageView) {
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.detail_seekbar);
        seekBar.setMax(OPACITY_SEEKBAR_MAX);
        seekBar.setProgress(OPACITY_SEEKBAR_PROGRESS);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                imageView.setAlphaValue(Float.valueOf(i / 100.0f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
