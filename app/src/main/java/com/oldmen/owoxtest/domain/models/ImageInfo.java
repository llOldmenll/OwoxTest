package com.oldmen.owoxtest.domain.models;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Header;

public class ImageInfo {

    @SerializedName("width")
    private int mWidth;
    @SerializedName("height")
    private int mHeight;
    @SerializedName("user")
    private User mUser;
    @SerializedName("urls")
    private ImageUrls mImgUrls;

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

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public ImageUrls getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(ImageUrls imgUrls) {
        mImgUrls = imgUrls;
    }

    public class User {
        @SerializedName("name")
        private String name;
        @SerializedName("profile_image")
        private Profile mProfile;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Profile getProfile() {
            return mProfile;
        }

        public void setProfile(Profile profile) {
            mProfile = profile;
        }

        class Profile {

            @SerializedName("small")
            private String small;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }
        }
    }

    public class ImageUrls {
        @SerializedName("raw")
        private String raw;
        @SerializedName("small")
        private String small;
        @SerializedName("thumb")
        private String thumbnail;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
