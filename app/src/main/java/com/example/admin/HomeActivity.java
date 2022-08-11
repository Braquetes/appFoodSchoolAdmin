package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Canal";

    Bundle Data;
    String extra;
    TextView producto0,producto1,producto2,producto3,producto4,producto5,producto6,producto7,producto8,producto9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            extra = bundle.getString("data");
            notificacion("QR Escaneado");
            transformar(extra);
            /*try {
                Toast.makeText(this, extra, Toast.LENGTH_SHORT).show();

            } catch (Throwable t) {
                Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }*/
        }

        //Data = getIntent().getExtras();
        //String datos = Data.getString("data");
        producto0 = findViewById(R.id.producto0);
        producto1 = findViewById(R.id.producto1);
        producto2 = findViewById(R.id.producto2);
        producto3 = findViewById(R.id.producto3);
        producto4 = findViewById(R.id.producto4);
        producto5 = findViewById(R.id.producto5);
        producto6 = findViewById(R.id.producto6);
        producto7 = findViewById(R.id.producto7);
        producto8 = findViewById(R.id.producto8);
        producto9 = findViewById(R.id.producto9);
    }

    public void transformar(String data){
        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "https://dgs801.com/qr.php?dato="+data;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        String dato0 = null,dato1 = null,dato2 = null,dato3 = null,dato4 = null,dato5 = null,dato6 = null,dato7 = null,dato8 = null,dato9 = null;
                        try {
                            dato0 = response.getString("producto0");
                            producto0.setText(dato0);
                            dato1 = response.getString("producto1");
                            producto1.setText(dato1);
                            dato2 = response.getString("producto2");    
                            producto2.setText(dato2);
                            dato3 = response.getString("producto3");
                            producto3.setText(dato3);
                            dato4 = response.getString("producto4");
                            producto4.setText(dato4);
                            dato5 = response.getString("producto5");
                            producto5.setText(dato5);
                            dato6 = response.getString("producto6");
                            producto6.setText(dato6);
                            dato7 = response.getString("producto7");
                            producto7.setText(dato7);
                            dato8 = response.getString("producto8");
                            producto8.setText(dato8);
                            dato9 = response.getString("producto9");
                            producto9.setText(dato9);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(HomeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    public void notificacion(String xd){
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "nofify", importance);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("FoodSchool")
                .setContentText(xd)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManager.notify(1,builder.build());
    }

    public void venta(View v){
        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "https://dgs801.com/foodschool/admin/venta.php?dato="+extra;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                            notificacion("Venta realizada");
                        Intent intent = new Intent(getApplication(), MenuActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(HomeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }
}