package com.oldmen.owoxtest.domain.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static com.oldmen.owoxtest.utils.Constants.IMAGESUNSPLASH_TABLE_NAME;

@Entity(tableName = IMAGESUNSPLASH_TABLE_NAME)
public class ImageUnsplash implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int mId;
    private String mImgId;
    private int mWidth;
    private int mHeight;
    private String mAuthorName;
    private String mAuthorAvatar;
    private String mThumbImgUrl;
    private String mSmallImgUrl;
    private String mRegularImgUrl;
    private String mFullImgUrl;
    private String mShareLink;

    public ImageUnsplash() {
    }

    protected ImageUnsplash(Parcel in) {
        mId = in.readInt();
        mImgId = in.readString();
        mWidth = in.readInt();
        mHeight = in.readInt();
        mAuthorName = in.readString();
        mAuthorAvatar = in.readString();
        mThumbImgUrl = in.readString();
        mSmallImgUrl = in.readString();
        mRegularImgUrl = in.readString();
        mFullImgUrl = in.readString();
        mShareLink = in.readString();
    }

    public static final Creator<ImageUnsplash> CREATOR = new Creator<ImageUnsplash>() {
        @Override
        public ImageUnsplash createFromParcel(Parcel in) {
            return new ImageUnsplash(in);
        }

        @Override
        public ImageUnsplash[] newArray(int size) {
            return new ImageUnsplash[size];
        }
    };

    private ImageUnsplash(Builder builder) {
        setId(builder.mId);
        setImgId(builder.mImgId);
        setWidth(builder.mWidth);
        setHeight(builder.mHeight);
        setAuthorName(builder.mAuthorName);
        setAuthorAvatar(builder.mAuthorAvatar);
        setThumbImgUrl(builder.mThumbImgUrl);
        setSmallImgUrl(builder.mSmallImgUrl);
        setRegularImgUrl(builder.mRegularImgUrl);
        setFullImgUrl(builder.mFullImgUrl);
        setShareLink(builder.mShareLink);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getImgId() {
        return mImgId;
    }

    public void setImgId(String imgId) {
        mImgId = imgId;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public void setAuthorName(String authorName) {
        mAuthorName = authorName;
    }

    public String getAuthorAvatar() {
        return mAuthorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        mAuthorAvatar = authorAvatar;
    }

    public String getThumbImgUrl() {
        return mThumbImgUrl;
    }

    public void setThumbImgUrl(String thumbImgUrl) {
        mThumbImgUrl = thumbImgUrl;
    }

    public String getSmallImgUrl() {
        return mSmallImgUrl;
    }

    public void setSmallImgUrl(String smallImgUrl) {
        mSmallImgUrl = smallImgUrl;
    }

    public String getRegularImgUrl() {
        return mRegularImgUrl;
    }

    public void setRegularImgUrl(String regularImgUrl) {
        mRegularImgUrl = regularImgUrl;
    }

    public String getFullImgUrl() {
        return mFullImgUrl;
    }

    public void setFullImgUrl(String fullImgUrl) {
        mFullImgUrl = fullImgUrl;
    }

    public String getShareLink() {
        return mShareLink;
    }

    public void setShareLink(String shareLink) {
        mShareLink = shareLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mImgId);
        dest.writeInt(mWidth);
        dest.writeInt(mHeight);
        dest.writeString(mAuthorName);
        dest.writeString(mAuthorAvatar);
        dest.writeString(mThumbImgUrl);
        dest.writeString(mSmallImgUrl);
        dest.writeString(mRegularImgUrl);
        dest.writeString(mFullImgUrl);
        dest.writeString(mShareLink);
    }


    public static final class Builder {
        private int mId;
        private String mImgId;
        private int mWidth;
        private int mHeight;
        private String mAuthorName;
        private String mAuthorAvatar;
        private String mThumbImgUrl;
        private String mSmallImgUrl;
        private String mRegularImgUrl;
        private String mFullImgUrl;
        private String mShareLink;

        public Builder() {
        }

        public Builder mId(int val) {
            mId = val;
            return this;
        }

        public Builder mImgId(String val) {
            mImgId = val;
            return this;
        }

        public Builder mWidth(int val) {
            mWidth = val;
            return this;
        }

        public Builder mHeight(int val) {
            mHeight = val;
            return this;
        }

        public Builder mAuthorName(String val) {
            mAuthorName = val;
            return this;
        }

        public Builder mAuthorAvatar(String val) {
            mAuthorAvatar = val;
            return this;
        }

        public Builder mThumbImgUrl(String val) {
            mThumbImgUrl = val;
            return this;
        }

        public Builder mSmallImgUrl(String val) {
            mSmallImgUrl = val;
            return this;
        }

        public Builder mRegularImgUrl(String val) {
            mRegularImgUrl = val;
            return this;
        }

        public Builder mFullImgUrl(String val) {
            mFullImgUrl = val;
            return this;
        }

        public Builder mShareLink(String val) {
            mShareLink = val;
            return this;
        }

        public ImageUnsplash build() {
            return new ImageUnsplash(this);
        }
    }
}
