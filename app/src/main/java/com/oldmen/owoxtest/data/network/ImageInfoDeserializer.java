package com.oldmen.owoxtest.data.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.oldmen.owoxtest.domain.models.ImageUnsplash;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImageInfoDeserializer implements JsonDeserializer<List<ImageUnsplash>> {

    @Override
    public List<ImageUnsplash> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<ImageUnsplash> imgList = new ArrayList<>();
        JsonArray jsonArray;
        if (json.isJsonObject())
            jsonArray = json.getAsJsonObject().getAsJsonArray("results");
        else
            jsonArray = json.getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObjMain = jsonArray.get(i).getAsJsonObject();
            JsonObject jsonObjAuthor = jsonObjMain.getAsJsonObject("user");
            JsonObject jsonObjUrls = jsonObjMain.getAsJsonObject("urls");
            imgList.add(new ImageUnsplash.Builder()
                    .mImgId(jsonObjMain.get("id").getAsString())
                    .mWidth(jsonObjMain.get("width").getAsInt())
                    .mHeight(jsonObjMain.get("height").getAsInt())
                    .mAuthorName(jsonObjAuthor.get("name").getAsString())
                    .mAuthorAvatar(jsonObjAuthor.getAsJsonObject("profile_image").get("medium").getAsString())
                    .mThumbImgUrl(jsonObjUrls.get("thumb").getAsString())
                    .mSmallImgUrl(jsonObjUrls.get("small").getAsString())
                    .mRegularImgUrl(jsonObjUrls.get("regular").getAsString())
                    .mFullImgUrl(jsonObjUrls.get("full").getAsString())
                    .mShareLink(jsonObjMain.getAsJsonObject("links").get("html").getAsString())
                    .build());
        }

        return imgList;
    }

}
