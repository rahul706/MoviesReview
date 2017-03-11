package com.thenewboston.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Rahul on 11-03-2017.
 */

public class MovieArrayAdapter extends ArrayAdapter {

    private List<MoviesDetails> moviesDetailsesList;

    private int resource;

    private Context context;


    public MovieArrayAdapter(Context context, int resource, List<MoviesDetails> objects) {
        super(context, resource, objects);
        this.context = context;
        this.moviesDetailsesList = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MoviesDetails details = moviesDetailsesList.get(position);
        View view = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView movieName = (TextView) view.findViewById(R.id.textView);
        ImageView image = (ImageView) view.findViewById(R.id.imageView);

        movieName.setText(details.getOriginal_title());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+ details.getPoster_path()).into(image);
        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return moviesDetailsesList.get(position);
    }
}
