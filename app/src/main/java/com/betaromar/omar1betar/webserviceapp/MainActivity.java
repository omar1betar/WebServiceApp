package com.betaromar.omar1betar.webserviceapp;

import android.media.MediaSync;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public class MainActivity extends AppCompatActivity {

    private Button load;
    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView) findViewById(R.id.textView);

        load = (Button) findViewById(R.id.button);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                retrofitQuery();
            }
        });


    }


    public interface newsService {
        @GET("top-headlines")
        Call<String> getNews(@QueryMap Map<String ,String> queryParameters);
//        Call<ResponseBody> getNews(@Query("apiKey") String api, @Query("country") String country, @Query("category") String category);

    }
    //44ad05232bf9424ba8e4049e9baba738

    private void retrofitQuery() {
        String NEWS_API_URL = "https://newsapi.org/v2/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_API_URL)
                .addConverterFactory(ScalarsConverterFactory)
                .build();
        newsService newsService = retrofit.create(MainActivity.newsService.class);
        Map<String,String> parms =new HashMap<>();
        parms.put("apiKey","44ad05232bf9424ba8e4049e9baba738");
        parms.put("country","eg");
        parms.put("category","business");
        newsService.getNews(parms).






//        newsService.getNews("44ad05232bf9424ba8e4049e9baba738","eg","business").enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
//
//                try {
//                    display.setText(response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });

    }































    private void retrofitExample() {
        String link = "https://jsonplaceholder.typicode.com/posts";

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").build();
        //here we implmintation the interface to accses methods in
        jesonPlaceHolderService jesonPlaceHolderService = retrofit.create(MainActivity.jesonPlaceHolderService.class);
        //we get method get posts and we use enqueue to handle the response from server
        jesonPlaceHolderService.getPosts().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                try {
                    display.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, "ERROR: " + errorMessage, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public interface jesonPlaceHolderService {

        @GET("posts")
        Call<ResponseBody> getPosts();

        @POST("posts")
        Call<ResponseBody> newPost(String title, String content);

        @PUT("posts")
        Call<ResponseBody> updatePost(int id, String title, String content);

        @DELETE
        Call<ResponseBody> deletePost(int id);
    }
















    private void volleyConnectionStringRequest() {
        String link = "https://jsonplaceholder.typicode.com/posts";
        //use String Request
        StringRequest request = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                display.setText(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.e("Volley ERROR: ",error.getLocalizedMessage());

            }
        }
        );
        Volley.newRequestQueue(this).add(request);
    }

    private void loadDataHttpURLConnection() {
        //put the code in thread becouse we are in main thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //link of api url
                String link = "https://jsonplaceholder.typicode.com/posts";
                InputStream inputStream = null;
                try {
                    //check if url is working
                    URL url = new URL(link);
                    //use to open http connection
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //get data from api as stream 0101
                    inputStream = httpURLConnection.getInputStream();
                    //use this block of code to convert from stream to string
                    int c = 0;
                    StringBuffer buffer = new StringBuffer();
                    while ((c = inputStream.read()) != -1) {
                        buffer.append((char) c);
                    }
                    final String reslut = buffer.toString();
                    //we use runuithread becouse setText method must run in mainThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            display.setText(reslut);

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}
