package com.example.android.boombox;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {
    //Declare Variables
    private Handler songHandler = new Handler();

    private ImageView previousButton, rewindButton, pauseButton, playButton, forwardButton, nextButton;
    private SeekBar seekBar;
    //private TextView startTimeView, songTitle, endTimeView;

    private double startTime = 0;
    private double finalTime = 0;

    private int forwardTime = 5000;
    private int backwardTime = 5000;

    //Keeps track of Current Song
      private int currentIndex = 0;

    //Variable to Store song information
    private TextView songTitle;
    private  String artistName;
    private String songName;



    private MediaPlayer mMediaPlayer ;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afListener = new AudioManager.OnAudioFocusChangeListener(){
        public void onAudioFocusChange(int focusChange){
            if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }

        }
    };

    public ArtistsFragment() {
        // Required empty public constructor
    }

    public static int oneTimeOnly = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.words_list,container,false);

        //Initialize Views and Buttons
        previousButton = (ImageView) rootView.findViewById(R.id.previousButton);
        rewindButton = (ImageView) rootView.findViewById(R.id.rewindButton);
        pauseButton = (ImageView) rootView.findViewById(R.id.pauseButton);
        playButton = (ImageView) rootView.findViewById(R.id.playButton);
        forwardButton = (ImageView) rootView.findViewById(R.id.forwardButton);
        nextButton = (ImageView) rootView.findViewById(R.id.nextButton);


        //Disable buttons because we can't skip a song if it has not started playing yet
        previousButton.setEnabled(false);
        forwardButton.setEnabled(false);
        nextButton.setEnabled(false);
        rewindButton.setEnabled(false);
        pauseButton.setEnabled(false);
        playButton.setEnabled(false);

        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        seekBar.setClickable(false);

        songTitle = (TextView) rootView.findViewById(R.id.songInformation);

        //startTimeView = (TextView) rootView.findViewById(R.id.startTime);
        // endTimeView = (TextView) rootView.findViewById(R.id.endTime);


        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> songs = new ArrayList<>();

        //Don't need to play raw files but redirect the user to the specified artists songs list when the user clicks on whichever artist
        //Can leave it as a ListView information will be easier for the user to read this way
        songs.add(new Word("Childish Gambino","number of songs in memory",R.drawable.childish_gambino,R.raw.childish_gambino_3005));
        songs.add(new Word("Gas Lab","number of songs in memory",R.drawable.gas_lab,R.raw.gas_lab_chemistry));
        songs.add(new Word("J.Cole","number of songs in memory",R.drawable.j_cole,R.raw.jcole_losing_my_balance));
        songs.add(new Word("JABS","number of songs in memory",R.drawable.jabs_willow,R.raw.jabs_payiva));
        songs.add(new Word("Téo","number of songs in memory",R.drawable.teo,R.raw.teo_how_low));
        songs.add(new Word("The Muffinz","number of songs in memory",R.drawable.the_muffinz,R.raw.teo_enlightened_now));
        songs.add(new Word("Tupac Shakur","number of songs in memory",R.drawable.tupac,R.raw.gas_lab_jazz_hop));
        songs.add(new Word("Willow","number of songs in memory",R.drawable.willow,R.raw.willow_9));

        WordAdapter itemsAdapter = new WordAdapter(getActivity(), songs, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        //final LinearLayout musicControls = (LinearLayout) rootView.findViewById(R.id.musicControls);



        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Hide the music controls until a user clicks on a song for better User Experience
                // musicControls.setVisibility(View.VISIBLE);
                final Word word = songs.get(position);
                releaseMediaPlayer();

                int requestResult = mAudioManager.requestAudioFocus(afListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (requestResult == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getSoundResourceId());
                    artistName= word.getArtistName();
                    songName= word.getTitle();
                    mMediaPlayer.start();
                    songTitle.setText(artistName + " - "+ songName + ".mp3");
                    mMediaPlayer.start();

                    //Enable buttons so that they are clickable
                    //previousButton.setEnabled(true); Still need to fix Previous track method
                    forwardButton.setEnabled(true);
                    nextButton.setEnabled(true);
                    rewindButton.setEnabled(true);
                    pauseButton.setEnabled(true);

                    //Method to Play Next Song
                    nextButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Pause any sound that's currently playing to be able to load the next song
                            mMediaPlayer.pause();
                            //Increment our current song Index
                            currentIndex ++;
                            if (currentIndex < (songs.size() - 1)) {
                                mMediaPlayer = MediaPlayer.create(getActivity(), word.getSoundResourceId() + currentIndex);
                                mMediaPlayer.start();
                                songTitle.setText(word.getArtistName() + " - " + word.getTitle() + ".mp3");
                            }
                        }
                    });

                    //Method to Play Previous Song
                    previousButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Pause any sound that's currently playing to be able to go to the previous song
                            mMediaPlayer.pause();
                            if (currentIndex > 1) {
                                mMediaPlayer = MediaPlayer.create(getActivity(),word.getSoundResourceId() - 1);
                                mMediaPlayer.start();
                            }
                        }
                    });


                    mMediaPlayer.setOnCompletionListener(mCompletionListener);


                    finalTime = mMediaPlayer.getDuration();
                    startTime = mMediaPlayer.getCurrentPosition();

                    if (oneTimeOnly == 0) {
                        seekBar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

                    //Methods to Display Song Duration
                    //endTimeView.setText(String.format("%d min, %d sec",
                    //TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    //TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                    // TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                    //  finalTime)))
                    //);

                    seekBar.setProgress((int) startTime);
                    seekBar.setMax(mMediaPlayer.getDuration());
                    songHandler.postDelayed(UpdateSongTime, 100);


                }

            }
        });


        //Method to fast forward the track
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;
                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mMediaPlayer.seekTo((int) startTime);
                }
            }
        });


        //Method to rewind the track
        rewindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;
                if ((temp-backwardTime)> 0) {
                    startTime = startTime - backwardTime;
                    mMediaPlayer.seekTo((int) startTime);
                }
            }
        });



        //Method to Pause song
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    //"Pause" is not in display so the user can see the "Play" Button to Resume music
                    pauseButton.setVisibility(View.GONE);
                    //Displays "Play" button and replaces the "Pause" button on the controls layout
                    playButton.setVisibility(View.VISIBLE);
                    playButton.setEnabled(true);

                    //Disable Control buttons since we don't need them....when song is not Playing
                    previousButton.setEnabled(false);
                    forwardButton.setEnabled(false);
                    nextButton.setEnabled(false);
                    rewindButton.setEnabled(false);
                }
            }
        });


        //Method to Play song if it was Paused
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();
                //"Play" is not in display so the user can see the "Pause" Button to Pause the music if needed
                playButton.setVisibility(View.GONE);
                //Displays "Pause" button and replaces the "Play" button on the controls layout
                pauseButton.setVisibility(View.VISIBLE);

                //Enable buttons so that the user can user them to manage songs
                previousButton.setEnabled(true);
                forwardButton.setEnabled(true);
                nextButton.setEnabled(true);
                rewindButton.setEnabled(true);
            }
        });


        return rootView;
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mMediaPlayer.getCurrentPosition();
            seekBar.setProgress((int) startTime);
            songHandler.postDelayed(this, 100);
        }
    };

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