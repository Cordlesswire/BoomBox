package com.example.android.boombox;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;



public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;
    MediaPlayer mediaPlayer = new MediaPlayer();

    public WordAdapter(Activity context,ArrayList<Word> words,int colorResourceId)
    {
        super(context,0,words);
        mColorResourceId = colorResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

        //This returns the artist name and displays it on the ListView
        TextView artistName = (TextView) listItemView.findViewById(R.id.artistname_text_view);
        artistName.setText(currentWord.getArtistName());

        //This returns the Song Title and displays it on the ListView
        TextView titleWord = (TextView) listItemView.findViewById(R.id.title_text_view);
        titleWord.setText(currentWord.getTitle());

        //This returns the Album art of each song or album and displays it on the ListView
        ImageView image = (ImageView) listItemView.findViewById(R.id.image);


        if(currentWord.hasImage()) {
            image.setImageResource(currentWord.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        }
        else {
            image.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);

           //int audio = currentWord.getSoundResourceId();
           //mediaPlayer =  mediaPlayer.create(WordAdapter.this,audio);
          return listItemView;
    }
}
