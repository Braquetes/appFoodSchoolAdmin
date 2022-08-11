package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VentasActivity extends AppCompatActivity {

    ListView lista;
    ArrayList<String> ar = new ArrayList<String>();
    ArrayList<String> arr = new ArrayList<String>();
    JSONArray jsonArray;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        lista = (ListView) findViewById(R.id.list_item);

        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "https://dgs801.com/foodschool/admin/getVentas.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            jsonArray = response;
                            jsonObject = jsonArray.getJSONObject(0);
                            String nombre = null;
                            String id = null;
                            int estado;

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                nombre = object.getString("productos");
                                estado = Integer.parseInt(object.getString("estado"));
                                id = object.getString("idVenta");
                                if(estado!=1){
                                    ar.add(nombre);
                                    arr.add(id);
                                }
                            }



                            ArrayAdapter<String> adapter =new ArrayAdapter<String>(VentasActivity.this,
                                    android.R.layout.simple_list_item_multiple_choice,ar);
                            lista.setAdapter(adapter);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(VentasActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(VentasActivity.this, arr.get(i), Toast.LENGTH_SHORT).show();
                VentasActivity createPackageContext;
                Intent intent = new Intent(createPackageContext = VentasActivity.this, ConfirmarActivity.class);
                intent.putExtra("id", arr.get(i));
                startActivity(intent);
            }
        });
    }
}