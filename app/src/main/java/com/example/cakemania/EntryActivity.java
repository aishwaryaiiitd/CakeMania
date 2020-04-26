package com.example.cakemania;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import com.example.cakemania.models.Cake;
import com.example.cakemania.services.GetCakeList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static android.content.ContentValues.TAG;

public class EntryActivity extends AppCompatActivity {

    public static ArrayList<Cake> cakeArrayList;
    private String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        URL="http://kekizadmin.com/kekiz_api/actions.php?action=get_cakes&category=27";

        new CountDownTimer(1000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                while(cakeArrayList==null) {
                    getListfromAPI(URL);
                }
                Intent intent=new Intent(EntryActivity.this,MainActivity.class);
                startActivity(intent);//set the new Content of your activity

            }
        }.start();


    }

    private void getListfromAPI (String url) {
        com.example.cakemania.HttpGetRequest getRequest = new com.example.cakemania.HttpGetRequest();
        String jsonStr = "";
        try {
            jsonStr = getRequest.execute(url).get();
        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG,"unable to connect");
        }
        try{
            GetCakeList getCakeList = new GetCakeList();
            cakeArrayList = getCakeList.execute(jsonStr).get();
//            Log.d(TAG,""+cakeArrayList.size());
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG,"unable to get data");
        }
    }
}
