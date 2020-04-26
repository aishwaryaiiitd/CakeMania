package com.example.cakemania.services;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cakemania.models.Cake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GetCakeList extends AsyncTask<String, Void, ArrayList<Cake>> {
    private ArrayList<Cake> cakeList;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("HomeFragment","Json Data is downloading");

    }

    @Override
    protected ArrayList<Cake> doInBackground(String... params) {
        String jsonStr = params[0];
        cakeList = new ArrayList<Cake>();
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray data = jsonObj.getJSONArray("data");

                // looping through All cakes
                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);
                    String name = c.getString("cake_name");
                    JSONArray w_l_p = c.getJSONArray("w_l_p");
                    JSONObject first = w_l_p.getJSONObject(0);
                    String weight = first.getString("weight");
                    String price = first.getString("price");
                    String image = first.getString("pictures");
                    JSONObject im = new JSONObject(image);
                    String image_id = im.getString("file_name");

                    Cake cake=new Cake(name,weight,price,image_id);
                    Log.d(TAG,name+" "+weight+" "+price+" "+image_id);
                    // adding cake to mArrayList
                    cakeList.add(cake);
                }

                return cakeList;
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Cake> arrayList) {
        super.onPostExecute(arrayList);
    }
}
