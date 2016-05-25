package com.marshalchen.common.demoofui.BlurNavigationDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.marshalchen.common.demoofui.R;

import java.util.ArrayList;

/**
 * Created by charbgr on 8/30/14.
 */
public class MovieAdapter extends BaseAdapter {

    private ArrayList<Movie> movies;
    private Context context;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);
        ImageView imageView;
        TextView textView;


        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.blur_navigation_drawer_movie_item, parent,
                    false);
        }

        imageView = (ImageView)convertView.findViewById(R.id.picture);
        textView = (TextView)convertView.findViewById(R.id.text);

        imageView.setImageDrawable(movie.getDrawable());
        textView.setText(movie.getTitle());

        return convertView;
    }
}
