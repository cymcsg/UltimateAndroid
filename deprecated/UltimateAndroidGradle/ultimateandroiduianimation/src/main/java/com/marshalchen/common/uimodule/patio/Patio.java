package com.marshalchen.common.uimodule.patio;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.marshalchen.common.uimodule.animation.R;
import com.marshalchen.common.uimodule.easyandroidanimations.Animation;
import com.marshalchen.common.uimodule.easyandroidanimations.SlideInAnimation;
import com.marshalchen.common.uimodule.easyandroidanimations.SlideOutAnimation;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Patio extends LinearLayout implements View.OnClickListener {

    /**
     * Constants
     */
    public final static String TAG = Patio.class.getSimpleName();
    public final static int DEFAULT_MAX_PICTURES = 3;

    /**
     * Variables
     */
    private final int WIDGET_LAYOUT_RES_ID = R.layout.patio_patio_layout;
    private final int THUMBNAIL_LAYOUT_RES_ID = R.layout.patio_patio_thumbnail;
    private Context mContext;
    private int mMaxPictures;
    private PatioCallbacks mListener;
    private ArrayList<PatioThumbnail> mPatioThumbnails;
    private String mTakePicturePath;

    //Resources
    private float mThumbnailWidth;
    private float mThumbnailHeight;
    private String mThumbnailsMessageString;

    /**
     * Controls
     */
    //Actions
    public Button mTakePicture;
    public Button mAttachPicture;
    public Button mRemovePicture;
    public Button mCancel;
    //Containers
    public HorizontalScrollView mThumbnailsWrapper;
    public LinearLayout mThumbnailsContainer;
    public TextView mThumbnailsCount;
    //Toolbars
    public LinearLayout mToolbarAddActions;
    public LinearLayout mToolbarRemoveActions;

    //TODO: http://ryanharter.com/blog/2014/08/29/building-dynamic-custom-views/

    /**
     * Constructor
     */
    public Patio(Context context) {
        super(context);
        init(context, null);
    }

    public Patio(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Patio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * Lifecycle methods
     * https://speakerdeck.com/cyrilmottier/deep-dive-into-android-state-restoration
     * https://github.com/CharlesHarley/Example-Android-SavingInstanceState/blob/master/src/com/example/android/savinginstancestate/views/LockCombinationPicker.java
     * https://gist.github.com/granoeste/4037468
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        SavedState savedState = new SavedState(superState);
        savedState.setThumbnailsPaths(getThumbnailsPaths());
        savedState.setTakePicturePath(mTakePicturePath);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        ArrayList<String> thumbnailsPaths = savedState.getThumbnailsPaths();
        String takePicturePath = savedState.getTakePicturePath();

        restoreState(thumbnailsPaths, takePicturePath);
    }

    /**
     * Custom methods
     */
    public void init(Context context, AttributeSet attributeSet) {
        //Setup defaults
        mContext = context;
        mMaxPictures = DEFAULT_MAX_PICTURES;
        mThumbnailHeight = mContext.getResources().getDimension(R.dimen.patio_default_thumbnail_height);
        mThumbnailWidth = mContext.getResources().getDimension(R.dimen.patio_default_thumbnail_width);
        mThumbnailsMessageString = "patio_thumbnails_message";
        mPatioThumbnails = new ArrayList<PatioThumbnail>();
        setOrientation(VERTICAL);

        //Local defaults
        float thumbnailsContainerPadding = mContext.getResources().getDimension(R.dimen.patio_default_thumbnails_container_padding);
        int actionsTextColor = mContext.getResources().getColor(R.color.patio_default_action_text_color);
        int thumbnailsWrapperBackground = mContext.getResources().getColor(R.color.patio_default_thumbnails_container_background);


        //Inflate view and setup controls
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(WIDGET_LAYOUT_RES_ID, this, true);
        //Buttons
        mTakePicture = (Button) findViewById(R.id.patio_action_take_picture);
        mAttachPicture = (Button) findViewById(R.id.patio_action_attach_picture);
        mRemovePicture = (Button) findViewById(R.id.patio_action_remove_picture);
        mCancel = (Button) findViewById(R.id.patio_action_cancel);
        //Containers
        mThumbnailsWrapper = (HorizontalScrollView) findViewById(R.id.patio_thumbnails_wrapper);
        mThumbnailsContainer = (LinearLayout) findViewById(R.id.patio_thumbnails_container);
        mThumbnailsCount = (TextView) findViewById(R.id.patio_thumbnails_count_message);
        //Toolbars
        mToolbarAddActions = (LinearLayout) findViewById(R.id.patio_add_actions_toolbar);
        mToolbarRemoveActions = (LinearLayout) findViewById(R.id.patio_remove_actions_toolbar);
        mToolbarRemoveActions.setVisibility(View.GONE);

        //Set actions listeners
        if(!isInEditMode()) {
            mTakePicture.setOnClickListener(this);
            mAttachPicture.setOnClickListener(this);
            mRemovePicture.setOnClickListener(this);
            mCancel.setOnClickListener(this);
        }

        //Get defined attributes
        if(attributeSet != null) {
            TypedArray a = mContext.getTheme().obtainStyledAttributes(attributeSet, R.styleable.Patio, 0, 0);
            try {
                //Runtime
                mMaxPictures = a.getInt(R.styleable.Patio_patio_maxPictures, DEFAULT_MAX_PICTURES);
                mThumbnailHeight = a.getDimension(R.styleable.Patio_patio_thumbnailHeight, mThumbnailHeight);
                mThumbnailWidth = a.getDimension(R.styleable.Patio_patio_thumbnailWidth, mThumbnailWidth);
                mThumbnailsMessageString = a.getString(R.styleable.Patio_patio_thumbnailsMessage);
                //Local
                thumbnailsContainerPadding = a.getDimension(R.styleable.Patio_patio_thumbnailsContainerPadding, thumbnailsContainerPadding);
                actionsTextColor = a.getColor(R.styleable.Patio_patio_actionsTextColor, actionsTextColor);
                thumbnailsWrapperBackground = a.getColor(R.styleable.Patio_patio_thumbnailsContainerBackground, thumbnailsWrapperBackground);
            } finally {
                a.recycle();
            }
        }

        //Check Max Pictures
        if(mMaxPictures <= 0) {
            mMaxPictures = DEFAULT_MAX_PICTURES;
        }

        //Check Thumbnail Message
        if(mThumbnailsMessageString == null) {
            mThumbnailsMessageString = "patio_thumbnails_message";
        }

        //Setup actions text color
        mTakePicture.setTextColor(actionsTextColor);
        mAttachPicture.setTextColor(actionsTextColor);
        mRemovePicture.setTextColor(actionsTextColor);
        mCancel.setTextColor(actionsTextColor);

        //Setup thumbnails container background
        mThumbnailsWrapper.setBackgroundColor(thumbnailsWrapperBackground);

        //Setup dimensions
        ViewGroup.LayoutParams layoutParams;
        int paddingTop, paddingBottom, paddingLeft, paddingRight;
        //Thumbnails Wrapper height
        layoutParams = mThumbnailsWrapper.getLayoutParams();
        layoutParams.height = Float.valueOf(mThumbnailHeight).intValue();
        mThumbnailsWrapper.setLayoutParams(layoutParams);
        //Thumbnails Container padding
        paddingTop = paddingBottom = paddingLeft = paddingRight = Float.valueOf(thumbnailsContainerPadding).intValue();
        mThumbnailsContainer.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        //Init Thumbnails Message
        updateThumbnailsMessage();
    }

    public void restoreState(ArrayList<String> thumbnailsPaths, String takePicturePath) {
        for(String thumbnailPath : thumbnailsPaths) {
            addThumbnail(thumbnailPath);
        }
        mTakePicturePath = takePicturePath;
    }

    public void addThumbnail(String thumbnailPath) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ImageView imageView = (ImageView) inflater.inflate(THUMBNAIL_LAYOUT_RES_ID, mThumbnailsContainer, false);

        int resizeDimension = mThumbnailWidth > mThumbnailHeight ?
                Float.valueOf(mThumbnailWidth).intValue() :
                Float.valueOf(mThumbnailHeight).intValue();
        Picasso.with(mContext)
                .load(new File(thumbnailPath))
                .resize(resizeDimension, resizeDimension)
                .centerCrop()
                .into(imageView);

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = Float.valueOf(mThumbnailWidth).intValue();
        layoutParams.height = Float.valueOf(mThumbnailHeight).intValue();
        mThumbnailsContainer.addView(imageView, 0, layoutParams);
        imageView.setOnClickListener(this);

        mPatioThumbnails.add(new PatioThumbnail(thumbnailPath, imageView));
        updateThumbnailsMessage();
    }

    public void updateThumbnailsMessage() {
        int count = mPatioThumbnails.size();
        Resources res = mContext.getResources();
        String thumbnailsCountMessage = String.format(mThumbnailsMessageString, count, mMaxPictures);
        mThumbnailsCount.setText(thumbnailsCountMessage);

        //Check actions button if max pictures reached
        boolean actionsEnabled = mPatioThumbnails.size() < mMaxPictures;
        mTakePicture.setEnabled(actionsEnabled);
        mAttachPicture.setEnabled(actionsEnabled);
    }

    public void setCallbacksListener(PatioCallbacks listener) {
        mListener = listener;
    }

    public Intent getTakePictureIntent() {
        mTakePicturePath = null;
        File pictureFile = null;
        try {
            pictureFile = PatioUtils.getNewImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if(pictureFile == null)
            return null;

        mTakePicturePath = pictureFile.getAbsolutePath();
        Log.d(TAG, "Path: " + mTakePicturePath);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
        return takePictureIntent;
    }

    public Intent getAttachPictureIntent() {
        Intent attachPictureIntent = new Intent(Intent.ACTION_PICK);
        attachPictureIntent.setType("image/*");
        return attachPictureIntent;
    }

    public void handleTakePictureResult(Intent data) {
        Log.d(TAG, "File Path: " + mTakePicturePath);

        addThumbnail(mTakePicturePath);
        PatioUtils.addNewImageToGallery(mContext, mTakePicturePath);
    }

    public void handleAttachPictureResult(Intent data) {
        Uri uri = data.getData();
        String filePath = PatioUtils.getRealPathFromURI(mContext, uri);
        Log.d(TAG, "File Path: " + filePath);

        addThumbnail(filePath);
    }

    public void showAddToolbar() {
        if(mToolbarAddActions.getVisibility() == View.VISIBLE)
            return;

        new SlideOutAnimation(mToolbarRemoveActions).setDirection(Animation.DIRECTION_UP).animate();
        new SlideInAnimation(mToolbarAddActions).setDirection(Animation.DIRECTION_UP).animate();
    }

    public void showRemoveToolbar() {
        if(mToolbarRemoveActions.getVisibility() == View.VISIBLE)
            return;

        new SlideOutAnimation(mToolbarAddActions).setDirection(Animation.DIRECTION_UP).animate();
        new SlideInAnimation(mToolbarRemoveActions).setDirection(Animation.DIRECTION_UP).animate();
    }

    public void cancelThumbnailSelection() {
        for(PatioThumbnail patioThumbnail : mPatioThumbnails) {
            patioThumbnail.setSelected(false);
        }
        checkToolbarsStatus();
    }

    public void removeSelectedThumbnails() {
        for(int i = mPatioThumbnails.size() - 1; i >= 0; i--) {
            if(mPatioThumbnails.get(i).isSelected()) {
                ImageView thumbnailView = mPatioThumbnails.get(i).getThumbnailView();
                mThumbnailsContainer.removeView(thumbnailView);
                mPatioThumbnails.remove(i);
            }
        }
        updateThumbnailsMessage();
        checkToolbarsStatus();
    }

    public void checkToolbarsStatus() {
        boolean thumbnailsSelected = false;
        for(PatioThumbnail patioThumbnail : mPatioThumbnails) {
            if (patioThumbnail.getThumbnailView().getAlpha() == 0.5f) {
                thumbnailsSelected = true;
                break;
            }
        }

        if(thumbnailsSelected)
            showRemoveToolbar();
        else
            showAddToolbar();
    }

    public ArrayList<String> getThumbnailsPaths() {
        ArrayList<String> thumbnailsPaths = new ArrayList<String>();
        for(PatioThumbnail patioThumbnail : mPatioThumbnails) {
            thumbnailsPaths.add(patioThumbnail.getThumbnailPath());
        }
        return thumbnailsPaths;
    }

    /**
     * OnClick buttons methods
     */
    @Override
    public void onClick(View view) {
        //Buttons
        if(view instanceof Button) {
            if(view.getId() == R.id.patio_action_take_picture) {
                mListener.onTakePictureClick();
            }
            if(view.getId() == R.id.patio_action_attach_picture) {
                mListener.onAddPictureClick();
            }
            if(view.getId() == R.id.patio_action_remove_picture) {
                removeSelectedThumbnails();
            }
            if(view.getId() == R.id.patio_action_cancel) {
                cancelThumbnailSelection();
            }
        }

        //Thumbnails
        if(view instanceof ImageView) {
            //Check for PatioThumbnail
            for(PatioThumbnail patioThumbnail : mPatioThumbnails) {
                //Inverse selection for selected thumbnail
                if(patioThumbnail.getThumbnailView().equals(view)) {
                    patioThumbnail.setSelected(!patioThumbnail.isSelected());
                    break;
                }
            }
            checkToolbarsStatus();
        }
    }

    /**
     * Interface
     */
    public interface PatioCallbacks {
        public void onTakePictureClick();
        public void onAddPictureClick();
    }

    /**
     * SavedState class for restoring view state
     */
    protected static class SavedState extends BaseSavedState {
        public ArrayList<String> mThumbnailsPaths;
        private String mTakePictureFilePath;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel in) {
            super(in);
            mThumbnailsPaths = in.readArrayList(String.class.getClassLoader());
            mTakePictureFilePath = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeList(mThumbnailsPaths);
            out.writeString(mTakePictureFilePath);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public void setThumbnailsPaths(ArrayList<String> thumbnailsPaths) {
            mThumbnailsPaths = thumbnailsPaths;
        }
        public ArrayList<String> getThumbnailsPaths() {
            return mThumbnailsPaths;
        }

        public void setTakePicturePath(String takePictureFilePath) {
            mTakePictureFilePath = takePictureFilePath;
        }

        public String getTakePicturePath() {
            return mTakePictureFilePath;
        }

    }
}
