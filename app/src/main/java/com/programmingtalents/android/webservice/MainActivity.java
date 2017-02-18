package com.programmingtalents.android.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.programmingtalents.android.webservice.adapter.DataAdapter;
import com.programmingtalents.android.webservice.models.DataHandler;
import com.programmingtalents.android.webservice.network.CallAddr;
import com.programmingtalents.android.webservice.network.NetworkStatus;
import com.programmingtalents.android.webservice.network.OnWebServiceResult;
import com.programmingtalents.android.webservice.utils.CommonUtilities;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnWebServiceResult{

    String url= "http://api.themoviedb.org/3/movie/tt0816692/credits?api_key=8496be0b2149805afa458ab8ec27560c";
    List<DataHandler> model= new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView)findViewById(R.id.recycler);
        hitRequest();
    }

    private void hitRequest(){
        FormEncodingBuilder parameters= new FormEncodingBuilder();
        parameters.add("page" , "1");
        if(NetworkStatus.getInstance(this).isConnectedToInternet()) {
            CallAddr call = new CallAddr(this, url,parameters,CommonUtilities.SERVICE_TYPE.GET_DATA, this);
            call.execute();
        }else {
            Toast.makeText(this, "No Network!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type) {
        Log.e("res", "type= "+ type+ " res= "+ result);
        try {
            JSONObject obj= new JSONObject(result);
            JSONArray arr= obj.getJSONArray("cast");
            for(int i=0; i<arr.length(); i++) {
                JSONObject jobj= arr.getJSONObject(i);
                DataHandler handler = new DataHandler();
                handler.setCast_id(jobj.getInt("cast_id"));
                handler.setOrder(jobj.getInt("order"));
                handler.setName(jobj.getString("name"));
                handler.setCharacter(jobj.getString("character"));
                model.add(handler);
            }
            DataAdapter adapter= new DataAdapter(this, model);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }catch (Exception e){
            e.printStackTrace();
        }
    }}
