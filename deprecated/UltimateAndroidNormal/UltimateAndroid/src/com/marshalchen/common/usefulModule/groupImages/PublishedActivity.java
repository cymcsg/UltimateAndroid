package com.marshalchen.common.usefulModule.groupImages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.marshalchen.common.R;
import com.marshalchen.common.commonUtils.logUtils.Logs;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.io.File;
import java.util.Calendar;
import java.util.Locale;

public class PublishedActivity extends Activity {

    private GridView noScrollgridview;
    private GridAdapter adapter;
    private TextView activity_selectimg_send;
    private static final int TAKE_PICTURE = 1;
    private static final int CHANGE_PICTURE = 1;
    String PATH = Environment
            .getExternalStorageDirectory() + "/DCIM";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_activity_selectimg);

        Init();
    }

    public void Init() {
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.drr.size()) {
                    new PopupWindows(PublishedActivity.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(PublishedActivity.this,
                            PhotoActivity.class);
                    Logs.d("ID---" + arg2);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
//		activity_selectimg_send = (TextView) findViewById(R.id.activity_selectimg_send);
//		activity_selectimg_send.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				List<String> list = new ArrayList<String>();
//				for (int i = 0; i < Bimp.drr.size(); i++) {
//					String Str = Bimp.drr.get(i).substring(
//							Bimp.drr.get(i).lastIndexOf("/") + 1,
//							Bimp.drr.get(i).lastIndexOf("."));
//					list.add(FileUtils.SDPATH+Str+".JPEG");
//				}
//				// 高清的压缩图片全部就在  list 路径里面了
//				// 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
//				// 完成上传服务器后 .........
//				FileUtils.deleteDir();
//			}
//		});
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            //loading();
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }

        public int getCount() {
            return (Bimp.drr.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.group_item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            if (position == Bimp.drr.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                // holder.image.setImageBitmap(Bimp.bmp.get(position));
                ImageLoader.getInstance().displayImage("file://" + Bimp.drr.get(position), holder.image);
                Logs.d("drr---" + Bimp.drr.get(position) + "   " + position + "  " + Bimp.drr.size());
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

//        public void loading() {
//            new Thread(new Runnable() {
//                public void run() {
//                    while (true) {
//                        Logs.d("size----" + Bimp.max + "   " + Bimp.drr.size());
//                        if (Bimp.max > Bimp.drr.size())
//                            Bimp.max--;
//                        if (Bimp.max == Bimp.drr.size()) {
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                            break;
//                        } else {
//                            try {
//                                String path = Bimp.drr.get(Bimp.max);
//                                System.out.println(path);
//                                Bitmap bm = ImageUtils.revitionImageSize(path);
//                                Bimp.bmp.add(bm);
//                                String newStr = path.substring(
//                                        path.lastIndexOf("/") + 1,
//                                        path.lastIndexOf("."));
//                                //FileUtils.saveBitmap(bm, "" + newStr);
//                                Bimp.max += 1;
//                                Message message = new Message();
//                                message.what = 1;
//                                handler.sendMessage(message);
//                            } catch (IOException e) {
//
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }).start();
//        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

//    protected void onRestart() {
//        super.onRestart();
//        adapter.update();
//
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (adapter != null)
//            adapter.update();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.update();
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.group_item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.abc_fade_in));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.slide_up_short));

            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(null);
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    // photo();
                    takePhoto();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(PublishedActivity.this,
                            ImageBucketActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }


    private static String path = "";

//    public void photo() {
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(Environment.getExternalStorageDirectory()
//                + "/myimage/", String.valueOf(System.currentTimeMillis())
//                + ".jpg");
//        path = file.getPath();
//        Uri imageUri = Uri.fromFile(file);
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(openCameraIntent, TAKE_PICTURE);
//    }

    /**
     * 调用系统相机
     */
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用系统相机
        new DateFormat();

        String name = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        Uri imageUri = Uri.fromFile(new File(PATH, name));
        path = PATH + "/" + name;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (Bimp.drr.size() < 9 && resultCode == RESULT_OK) {
                        Bimp.drr.add(path);
                        Logs.d("path--" + path);
                    }
//                    if (adapter != null)
//                        adapter.update();
                    break;
            }
        }

    }
}
