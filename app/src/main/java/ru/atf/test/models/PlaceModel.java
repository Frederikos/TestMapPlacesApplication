package ru.atf.test.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlaceModel {

    static final String TAG = PlaceModel.class.getSimpleName();

    public static ArrayList<PlaceModel> getPlacesFromString(String inputString) {
        ArrayList<PlaceModel> result = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(inputString);
            JSONArray items = jsonObject.getJSONObject("block").getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                result.add(new PlaceModel(items.getJSONObject(i)));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }

    public final String title;
    public final String subText;
    public final String imageUrl;
    public final RaitingModel raiting;
    public final LocationModel location;

    private PlaceModel(JSONObject jsonObject) {
        title = jsonObject.optString("title");
        subText = jsonObject.optString("subtext");
        imageUrl = jsonObject.optString("image");
        raiting = new RaitingModel(jsonObject.optJSONObject("rating"));
        location = new LocationModel(jsonObject.optJSONObject("location"));
    }

    public class RaitingModel {
        public final String count;
        public final String fsValue;
        public final String type;
        public final String text;

        public RaitingModel(JSONObject jsonObject) {
            count = jsonObject.optString("count");
            fsValue = jsonObject.optString("fsValue");
            type = jsonObject.optString("type");
            text = jsonObject.optString("text");
        }
    }

    public class LocationModel {
        public final Double longitude;
        public final Double latitude;
        public final String location;
        public final Double distance;

        public LocationModel(JSONObject jsonObject) {
            longitude = jsonObject.optDouble("longitude");
            latitude = jsonObject.optDouble("latitude");
            location = jsonObject.optString("location");
            distance = jsonObject.optDouble("distance");
        }
    }

}
