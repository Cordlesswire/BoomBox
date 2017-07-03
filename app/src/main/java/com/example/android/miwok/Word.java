package com.example.android.miwok;

/**
 * Created by Ashmita on 13-01-2017.
 */

public class Word {

    private String mMiwokTranslation;
    private String mDefaultTransaltion;

    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private int mSoundResourceId;

    public Word(String defaultTransaltion,String miwokTranslation,int soundResourceId)
    {
        mDefaultTransaltion = defaultTransaltion;
        mMiwokTranslation = miwokTranslation;
        mSoundResourceId = soundResourceId;

    }

    public Word(String defaultTransaltion,String miwokTranslation,int imageResourceId,int soundResourceId)
    {
        mDefaultTransaltion = defaultTransaltion;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mSoundResourceId = soundResourceId;

    }

    public String getDefaultTranslation()
    {

        return mDefaultTransaltion;
    }

    public String getMiwokTranslation()
    {
        return mMiwokTranslation;
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
                "mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mDefaultTransaltion='" + mDefaultTransaltion + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mSoundResourceId=" + mSoundResourceId +
                '}';
    }
}


