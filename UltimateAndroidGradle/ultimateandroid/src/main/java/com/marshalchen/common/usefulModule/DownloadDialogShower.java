package com.marshalchen.common.usefulModule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marshalchen.common.R;

/**
 * A custom DownloadDialogShower to show download progress dialog easily
 */
public class DownloadDialogShower {
    ProgressBar mProgress;
    AlertDialog.Builder builder;
    AlertDialog progressDialog;
    TextView updatePercentTextView;
    TextView updateCurrentTextView;
    TextView updateTotalTextView;
    boolean interceptFlag = false;
    public static final int DOWN_UPDATE = 1;
    public static final int DOWN_FINISH = 2;
    public static final int DOWN_ERROR = 3;
    Context mContext;

    private DownloadDialogShower(Context mContext) {
        this.mContext = mContext;
        initVariables();
    }


    private void showDownloadDialog() {
        builder = new AlertDialog.Builder(mContext);
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress_update, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        updatePercentTextView = (TextView) v.findViewById(R.id.updatePercentTextView);
        updateCurrentTextView = (TextView) v.findViewById(R.id.updateCurrentTextView);
        updateCurrentTextView.setText(prepareDownloadingTextViewString);
        updateTotalTextView = (TextView) v.findViewById(R.id.updateTotalTextView);
        // builder.setCustomTitle(inflater.inflate(R.layout.progress_update_title, null));
        builder.setTitle(downloadTitle);
        builder.setView(v);
        builder.setNegativeButton(downloadCancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;

            }
        });
        progressDialog = builder.create();

        progressDialog.show();
    }

    private String noticeTitle = "";
    private String positiveButtonString = "";
    private String negativeButtonString = "";
    private String updateCurrentTextViewString = "";
    private String prepareDownloadingTextViewString = "";
    private String downloadTitle = "";
    private String downloadFinshed = "";
    private String downloadCancel = "";
    private String errorTitle = "";
    private String errorMessage = "";
    private String errorButton = "";

    private String currentDownload = "1/";
    private String totalDownload = "0";


    public int progress = 0;

    private void initVariables() {
        noticeTitle = mContext.getResources().getString(R.string.dialog_shower_notice_title);
        positiveButtonString = mContext.getResources().getString(R.string.dialog_shower_positive_button);
        negativeButtonString = mContext.getResources().getString(R.string.dialog_shower_negative_button);
        updateCurrentTextViewString = mContext.getResources().getString(R.string.dialog_shower_notice_current_textview);
        prepareDownloadingTextViewString = mContext.getResources().getString(R.string.dialog_shower_notice_prepare_downloading);
        downloadTitle = mContext.getResources().getString(R.string.dialog_shower_download_title);
        downloadFinshed = mContext.getResources().getString(R.string.dialog_shower_download_finish);
        downloadCancel = mContext.getResources().getString(R.string.dialog_shower_download_cancel);
        errorTitle = mContext.getResources().getString(R.string.dialog_shower_download_error_title);
        errorMessage = mContext.getResources().getString(R.string.dialog_shower_download_error_message);
        errorButton = mContext.getResources().getString(R.string.dialog_shower_download_error_button);

    }

    public void showNoticeDialog(String alertInfo, final Thread thread) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(noticeTitle);
        builder.setMessage(alertInfo);
        builder.setPositiveButton(positiveButtonString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
                thread.start();

            }
        });
        builder.setNegativeButton(negativeButtonString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog noticeDialog = builder.create();
        // noticeDialog.setContentView(v);
        WindowManager.LayoutParams lp = noticeDialog.getWindow().getAttributes();
        lp.height = 200;
        noticeDialog.getWindow().setAttributes(lp);
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }


    public Handler processHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);    //To change body of overridden methods use File | Settings | File Templates.
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    updatePercentTextView.setText(progress + "" + "%");
                    try {
                        updateCurrentTextView.setText(updateCurrentTextViewString
                                + currentDownload);
                        updateTotalTextView.setText(totalDownload);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case DOWN_FINISH:
                    progressDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setText(downloadFinshed);
                    break;
                case DOWN_ERROR:
                    progressDialog.dismiss();
                    AlertDialog.Builder builderError = new AlertDialog.Builder(mContext);
                    builderError.setTitle(errorTitle);
                    builderError.setMessage(errorMessage);
                    builderError.setNegativeButton(errorButton, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderError.create().show();
                    break;
            }
        }
    };

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getPositiveButtonString() {
        return positiveButtonString;
    }

    public void setPositiveButtonString(String positiveButtonString) {
        this.positiveButtonString = positiveButtonString;
    }

    public String getNegativeButtonString() {
        return negativeButtonString;
    }

    public void setNegativeButtonString(String negativeButtonString) {
        this.negativeButtonString = negativeButtonString;
    }

    public String getUpdateCurrentTextViewString() {
        return updateCurrentTextViewString;
    }

    public void setUpdateCurrentTextViewString(String updateCurrentTextViewString) {
        this.updateCurrentTextViewString = updateCurrentTextViewString;
    }

    public String getPrepareDownloadingTextViewString() {
        return prepareDownloadingTextViewString;
    }

    public void setPrepareDownloadingTextViewString(String prepareDownloadingTextViewString) {
        this.prepareDownloadingTextViewString = prepareDownloadingTextViewString;
    }

    public String getDownloadTitle() {
        return downloadTitle;
    }

    public void setDownloadTitle(String downloadTitle) {
        this.downloadTitle = downloadTitle;
    }

    public String getDownloadFinshed() {
        return downloadFinshed;
    }

    public void setDownloadFinshed(String downloadFinshed) {
        this.downloadFinshed = downloadFinshed;
    }

    public String getDownloadCancel() {
        return downloadCancel;
    }

    public void setDownloadCancel(String downloadCancel) {
        this.downloadCancel = downloadCancel;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorButton() {
        return errorButton;
    }

    public void setErrorButton(String errorButton) {
        this.errorButton = errorButton;
    }

    public String getCurrentDownload() {
        return currentDownload;
    }

    public void setCurrentDownload(String currentDownload) {
        this.currentDownload = currentDownload;
    }

    public String getTotalDownload() {
        return totalDownload;
    }

    public void setTotalDownload(String totalDownload) {
        this.totalDownload = totalDownload;
    }


    public static class Builder {
        Context builderContext;
        private DownloadDialogShower downloadDialogShower;

        public Builder(Context context) {
            initDefaults(context);
            downloadDialogShower = new DownloadDialogShower(builderContext);
        }

        private void initDefaults(Context context) {
            builderContext = context;

        }

        public Builder setNoticeTitle(String title) {
            downloadDialogShower.setNoticeTitle(title);
            return this;
        }

        public Builder setDownloadTitle(String title) {
            downloadDialogShower.setDownloadTitle(title);
            return this;
        }

        public Builder setErrorTitle(String title) {
            downloadDialogShower.setErrorTitle(title);
            return this;
        }

        public Builder setCurrentDownload(String title) {
            downloadDialogShower.setCurrentDownload(title);
            return this;
        }

        public Builder setDownloadCancel(String title) {
            downloadDialogShower.setDownloadCancel(title);
            return this;
        }

        public Builder setDownloadFinshed(String title) {
            downloadDialogShower.setDownloadFinshed(title);
            return this;
        }

        public Builder setErrorButton(String title) {
            downloadDialogShower.setErrorButton(title);
            return this;
        }

        public Builder setErrorMessage(String title) {
            downloadDialogShower.setErrorMessage(title);
            return this;
        }

        public Builder setNegativeButtonString(String title) {
            downloadDialogShower.setNegativeButtonString(title);
            return this;
        }

        public Builder setPositiveButtonString(String title) {
            downloadDialogShower.setPositiveButtonString(title);
            return this;
        }

        public Builder setPrepareDownloadingTextViewString(String title) {
            downloadDialogShower.setPrepareDownloadingTextViewString(title);
            return this;
        }

        public Builder setTotalDownload(String title) {
            downloadDialogShower.setTotalDownload(title);
            return this;
        }

        public Builder setUpdateCurrentTextViewString(String title) {
            downloadDialogShower.setUpdateCurrentTextViewString(title);
            return this;
        }

        public DownloadDialogShower build() {
            return downloadDialogShower;
        }
    }
}

