package com.example.android.miwok;



public class Word {

    private String mTitle;
    private String mArtistName;

    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private int mSoundResourceId;

    public Word(String artistName,String songTitle,int soundResourceId)
    {
        mArtistName = artistName;
        mTitle = songTitle;
        mSoundResourceId = soundResourceId;

    }

    public Word(String artistName,String songTitle,int imageResourceId,int soundResourceId)
    {
        mArtistName = artistName;
        mTitle = songTitle;
        mImageResourceId = imageResourceId;
        mSoundResourceId = soundResourceId;

    }

    public String getArtistName()
    {

        return mArtistName;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public int getImageResourceId()
    {
        return mImageResourceId;
    }


    public boolean hasImage()
    {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getSoundResourceId(){
        return mSoundResourceId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mTitle='" + mTitle + '\'' +
                ", mArtistName='" + mArtistName + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mSoundResourceId=" + mSoundResourceId +
                '}';
    }
}


