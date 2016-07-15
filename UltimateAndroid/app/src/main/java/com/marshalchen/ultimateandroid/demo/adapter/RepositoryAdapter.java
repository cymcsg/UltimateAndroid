package com.marshalchen.ultimateandroid.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.marshalchen.ultimateandroid.demo.R;
import com.marshalchen.ultimateandroid.demo.model.Repository;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by Marshal Chen on 1/7/16.
 */
public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.ViewHolder> {
    List<Repository> repositoryList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder vh = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, null, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ownerName.setText(repositoryList.get(position).getOwner().getLogin());
//        Glide.with(holder.itemView.getContext()).load(repositoryList.get(position).getOwner().
//                getAvatar_url()).transform(new CircleTransform(holder.itemView.getContext())).placeholder(R.mipmap.ic_launcher).into(holder.ownerAvatar);

//        Glide.with(holder.itemView.getContext()).load(repositoryList.get(position).getOwner().
//                getAvatar_url()).dontAnimate().placeholder(R.mipmap.ic_launcher).into(holder.ownerAvatar);
        Glide.with(holder.itemView.getContext()).load(repositoryList.get(position).getOwner().
                getAvatar_url()).bitmapTransform(getTransform(position, holder.itemView.getContext())).placeholder(R.mipmap.ic_launcher).into(holder.ownerAvatar);

        holder.repositoryName.setText(repositoryList.get(position).getName());
        holder.repositoryForks.setText("forks:" + repositoryList.get(position).getForks_count());
        holder.repositoryStars.setText("stars:" + repositoryList.get(position).getWatchers());
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.owner_avatar)
        ImageView ownerAvatar;
        @BindView(R.id.owner_name)
        TextView ownerName;
        @BindView(R.id.repository_name)
        TextView repositoryName;
        @BindView(R.id.repository_stars)
        TextView repositoryStars;
        @BindView(R.id.repository_forks)
        TextView repositoryForks;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public RepositoryAdapter(List<Repository> repositoryList)

    {
        this.repositoryList = repositoryList;
    }


    Transformation<Bitmap> getTransform(int position, Context mContext) {
        if (position % 19 == 0) {
            return new CropCircleTransformation(mContext);
        } else if (position % 19 == 1) {
            return new RoundedCornersTransformation(mContext, 30, 0,
                    RoundedCornersTransformation.CornerType.BOTTOM);

        } else if (position % 19 == 2) {
            return new CropTransformation(mContext, 300, 100, CropTransformation.CropType.BOTTOM);

        } else if (position % 19 == 3) {
            return new CropSquareTransformation(mContext);

        } else if (position % 19 == 4) {
            return new CropTransformation(mContext, 300, 100, CropTransformation.CropType.CENTER);

        } else if (position % 19 == 5) {
            return new ColorFilterTransformation(mContext, Color.argb(80, 255, 0, 0));

        } else if (position % 19 == 6) {
            return new GrayscaleTransformation(mContext);

        } else if (position % 19 == 7) {
            return new CropTransformation(mContext, 300, 100);

        } else if (position % 19 == 8) {
            return new BlurTransformation(mContext, 25);
        } else if (position % 19 == 9) {
            return new ToonFilterTransformation(mContext);

        } else if (position % 19 == 10) {
            return new SepiaFilterTransformation(mContext);

        } else if (position % 19 == 11) {
            return new ContrastFilterTransformation(mContext, 2.0f);
        } else if (position % 19 == 12) {
            return new InvertFilterTransformation(mContext);
        } else if (position % 19 == 13) {
            return new PixelationFilterTransformation(mContext, 20);
        } else if (position % 19 == 14) {
            return new SketchFilterTransformation(mContext);
        } else if (position % 19 == 15) {
            return new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f));
        } else if (position % 19 == 16) {
            return new BrightnessFilterTransformation(mContext, 0.5f);
        } else if (position % 19 == 17) {
            return new KuwaharaFilterTransformation(mContext, 25);
        } else if (position % 19 == 18) {
            return new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                    new float[]{0.0f, 0.0f, 0.0f}, 0f, 0.75f);
        }
        return null;
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

}
