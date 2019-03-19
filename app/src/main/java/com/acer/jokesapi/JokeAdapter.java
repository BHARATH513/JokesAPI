package com.acer.jokesapi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.MyViewHolder> {
    Context context;
    ArrayList<DataModel> jokes;

    public JokeAdapter(Context context, ArrayList<DataModel> jokes) {
        this.context = context;
        this.jokes = jokes;
    }
    @NonNull
    @Override
    public JokeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.rowdesign,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeAdapter.MyViewHolder myViewHolder, int i) {
        DataModel model=jokes.get(i);
        //Toast.makeText(context, "joke:"+model.getJoke(), Toast.LENGTH_SHORT).show();
        myViewHolder.joke.setText(model.getJoke());

    }

    @Override
    public int getItemCount() {
        return jokes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView joke;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            joke=itemView.findViewById(R.id.asdfg);
        }
    }
}
