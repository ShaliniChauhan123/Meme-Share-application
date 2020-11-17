package com.example.memeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
//import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
   String current=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        //ImageView i = (ImageView) findViewById(R.id.image);
        Button b1 = (Button) findViewById(R.id.share);
        Button b2 = (Button) findViewById(R.id.next);
        Loadmeme();

    }
   private void Loadmeme(){
       ProgressBar p=(ProgressBar) findViewById(R.id.progressBar);
       p.setVisibility(View.VISIBLE);
    String url = "https://meme-api.herokuapp.com/gimme";

    // Request a string response from the provided URL.
       JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url,null,
            new Response.Listener<JSONObject>(){

                @Override
                public void onResponse(JSONObject response) {
                    ImageView i = (ImageView) findViewById(R.id.image);

                    try {String current=response.getString("url");

                        Glide.with(MainActivity.this).load(current).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                ProgressBar p=(ProgressBar) findViewById(R.id.progressBar);
                                p.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                ProgressBar p=(ProgressBar) findViewById(R.id.progressBar);
                                p.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

               /// @Override
               // public void onResponse(String response) {

                    // Display the first 500 characters of the response string.
                    //textView.setText("Response is: "+ response.substring(0,500));
                //}
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //textView.setText("That didn't work!");
        }
    });

// Add the request to the RequestQueue.
  MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
}
    public void sharememe(View view) {
        Intent intent= new Intent(Intent.ACTION_SEND);
         intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this meme "+current);
        intent.setType("text/plain");
        Intent chooser = Intent.createChooser(intent, null);
        startActivity(chooser);
    }

    public void nextmeme(View view) {
        Loadmeme();

        //NEW one
    }
}
