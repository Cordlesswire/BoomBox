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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsFragment extends Fragment {

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
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }

        }
    };


    public SongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.words_list, container, false);

        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("Childish Gambino", "3005", R.drawable.childish_gambino_because, R.raw.childish_gambino_3005));
        words.add(new Word("Childish Gambino", "Freestyle", R.drawable.childish_gambino, R.raw.childish_gambino_freestyle));
        words.add(new Word("Gas Lab", "Chemistry", R.drawable.gas_lab, R.raw.gas_lab_chemistry));
        words.add(new Word("Gas Lab (feat. Natayla & Traum Diggs)", "Jazz Hop", R.drawable.gas_lab, R.raw.gas_lab_jazz_hop));
        words.add(new Word("J.Cole", "Losing my Balance", R.drawable.j_cole, R.raw.jcole_losing_my_balance));
        words.add(new Word("JABS (feat. Willow)", "Payíva (Prod. JABS)", R.drawable.willow, R.raw.jabs_payiva));
        words.add(new Word("Téo", "Enlightened Now", R.drawable.teo, R.raw.teo_enlightened_now));
        words.add(new Word("Téo", "How Low", R.drawable.teo, R.raw.teo_how_low));
        words.add(new Word("Téo", "Selfless-ish", R.drawable.teo, R.raw.teo_selflessish));
        words.add(new Word("Tyler The Creator (feat. Frank Ocean) ", "She", R.drawable.tyler_the_creator, R.raw.tyler_the_creator_she));
        words.add(new Word("Willow (feat. SZA) ", "9", R.drawable.jabs_willow, R.raw.willow_9));
        words.add(new Word("Willow ", "Female Energy", R.drawable.jabs_willow, R.raw.willow_female_energy));
        words.add(new Word("Willow ", "Marceline", R.drawable.jabs_willow, R.raw.willow_marceline));


        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

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
