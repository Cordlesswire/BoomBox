package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistsFragment extends Fragment {


    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener afListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }

        }
    };


    public PlaylistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.words_list,container,false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> words = new ArrayList<>();

        //We don't need to make the ListView play sound when its clicked...edit the parameters so that we don't have to add a raw file
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));
        words.add(new Word("Name of The Playlist", "Small description e.g: mood sad", R.raw.childish_gambino_3005));



        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        //ImageView hidePlayIcon = (ImageView) rootView.findViewById(R.id.imagePlay);
        //hidePlayIcon.setVisibility(View.GONE);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Word word = words.get(position);
                releaseMediaPlayer();


                int requestResult = mAudioManager.requestAudioFocus(afListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getSoundResourceId());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }

            }
        });

    return rootView;
}

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(afListener);
        }
    }



}
