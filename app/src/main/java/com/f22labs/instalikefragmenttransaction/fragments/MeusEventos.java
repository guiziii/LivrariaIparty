package com.f22labs.instalikefragmenttransaction.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.f22labs.instalikefragmenttransaction.R;
import com.f22labs.instalikefragmenttransaction.adapters.GetDataAdapter;
import com.f22labs.instalikefragmenttransaction.adapters.RecyclerViewAdapter;
import com.f22labs.instalikefragmenttransaction.adapters.RecyclerViewAdapterMeusEvento;
import com.f22labs.instalikefragmenttransaction.interfaces.RecyclerViewOnClickListenerHack;
import com.f22labs.instalikefragmenttransaction.utils.Static;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MeusEventos extends BaseFragment implements RecyclerViewOnClickListenerHack
{
    private FragmentTabHost mTabHost;
    private Tab1.OnItemClickListener mListener;

    public interface OnItemClickListener
    {
        public void onItemClick(View view, int position);
    }
    @Override
    public void onClickListener(View view, int position) {

    }

    @Override
    public void onLongPressClickListener(View view, int position) {

    }

    RecyclerViewAdapter mAdapter ;

    GestureDetector mGestureDetector;

    SwipeRefreshLayout mSwipeRefreshLayout;
    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://guiziii.esy.es/SelectMEvent.php";
    String GET_JSON_DATA_HTTP_URL2 = "http://guiziii.esy.es/SelectMEvent.php?id_cli="+ String.valueOf(Static.getId())+"";
    //  ----------------------------------------------------------------------------------------------------
    //  |EXEMPLO DA NOVA FORMA DE PESQUISA COM CIDADE E ESTADO                                               |
    //  |http://guiziii.esy.es/ListCitybyState.php?cidade_evento=Sao%20Jose%20dos%20Campos&estado_evento=SP  |
    //  ----------------------------------------------------------------------------------------------------
    String JSON_IMAGE_URL = "img_evento";
    String JSON_DATA = "data_evento";
    String JSON_HORA = "hora_evento";
    String JSON_IDEVENTO = "id_evento";
    String JSON_IMAGE_TITLE_NAME = "nome_evento";
    String JSON_PRECO = "preco_evento";
    String JSON_LATITUDE = "latitude_evento";
    String JSON_LONGITUDE = "longitude_evento";
    private final String KEY_RECYCLER_STATE = "recycler_state";

    private static Bundle mBundleRecyclerViewState;
    private static int dyb;

    private Toolbar mToolbar;
    TextView txtTitle, txtSubtitle;
    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;
    String myJSON, resposta;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_meus_eventos, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swifeRefresh5);
        //mListener = listener;

        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview5);

         recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)
                {
                    dyb = dy;
                    // Scrolling down
                    //Toast.makeText(getActivity(), "Scrolling down", Toast.LENGTH_SHORT).show();
                    //mToolbar.hideOverflowMenu();
                    mToolbar.animate().translationY(-mToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                    ((ActionBarActivity)getActivity()).getSupportActionBar().hide();

                } else
                {


                    // Scrolling up
                    //  Toast.makeText(getActivity(), "Scrolling up", Toast.LENGTH_SHORT).show();
                    mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    ((ActionBarActivity)getActivity()).getSupportActionBar().show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING)
                {
                    mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    ((ActionBarActivity)getActivity()).getSupportActionBar().show();
                }
                else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    ((ActionBarActivity)getActivity()).getSupportActionBar().show();
                }
                else
                {
                    mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    ((ActionBarActivity)getActivity()).getSupportActionBar().show();
                }
            }
        });

        try
        {

            Field f = mSwipeRefreshLayout.getClass().getDeclaredField("mCircleView");
            f.setAccessible(true);
            ImageView img = (ImageView)f.get(mSwipeRefreshLayout);
            img.setBackgroundResource(R.color.black_de);
            img.setImageResource(R.drawable.add3);
        }
        catch (NoSuchFieldException e) {e.printStackTrace();}
        catch (IllegalAccessException e) {e.printStackTrace();}

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {


                GetDataAdapter1 = new ArrayList<>();

                recyclerView.setHasFixedSize(true);

                recyclerViewlayoutManager = new LinearLayoutManager(getActivity());

                recyclerView.setLayoutManager(recyclerViewlayoutManager);

                JSON_DATA_WEB_CALL();

            }
        });





        GET_JSON_DATA_HTTP_URL2 = "http://guiziii.esy.es/SelectMEvent.php?id_cli="+ String.valueOf(Static.getId())+"";

        JSON_DATA_WEB_CALL();

        return view;
    }



    //region Metodo pra manter o estado da lista
    @Override
    public void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null)
        {
            //if(dyb>0){((ActionBarActivity)getActivity()).getSupportActionBar().hide();}
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);

        }
    }
    //endregion

    //region Chamada no Host
    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL2,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);
        mSwipeRefreshLayout.setRefreshing(false);

        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
        ((ActionBarActivity)getActivity()).getSupportActionBar().show();


    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            final GetDataAdapter GetDataAdapter2 = new GetDataAdapter();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);
                class JsonTask extends AsyncTask<String,String,String> {


                    @Override
                    protected String doInBackground(String... params) {
                        HttpURLConnection connection = null;
                        BufferedReader reader = null;
                        try {
                            URL url = new URL(params[0]);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.connect();
                            InputStream stream = connection.getInputStream();
                            reader = new BufferedReader(new InputStreamReader(stream));
                            StringBuffer buffer = new StringBuffer();
                            String line = "";
                            while ((line = reader.readLine()) != null) {
                                buffer.append(line);
                            }
                            String finalJson = buffer.toString();
                            JSONObject parentObject = new JSONObject(finalJson);

                            JSONArray parentArray = parentObject.getJSONArray("routes");



                            JSONObject finalObject = parentArray.getJSONObject(0);

                            JSONArray parentArray2 = finalObject.getJSONArray("legs");

                            JSONObject finalObject2 = parentArray2.getJSONObject(0);

                            JSONObject parentArray3 = finalObject2.getJSONObject("start_location");




                            String distance = parentArray3.getString("lng");

                            return distance;

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                            try {
                                if (reader != null) {
                                    reader.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                    @Override
                    protected void  onPostExecute(String result)
                    {super.onPostExecute(result);
                        if(result !=null)
                        {
                            GetDataAdapter2.setDistancia(result);

                        }
                        else
                        {
                            GetDataAdapter2.setDistancia("Nothing");
                        }


                    }
                }
                GetDataAdapter2.setImageTitleNamee(json.getString(JSON_IMAGE_TITLE_NAME));
                GetDataAdapter2.setDataImage(json.getString(JSON_DATA));
                GetDataAdapter2.setHoraImage(json.getString(JSON_HORA));
                GetDataAdapter2.setId_evento(json.getString(JSON_IDEVENTO));
                GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL));
                GetDataAdapter2.setPreco(json.getString(JSON_PRECO));
                GetDataAdapter2.setLatitudeEvento(json.getString(JSON_LATITUDE));
                GetDataAdapter2.setLongitudeEvento(json.getString(JSON_LONGITUDE));
                // new JsonTask().execute("https://maps.googleapis.com/maps/api/directions/json?origin="+json.getString(JSON_LONGITUDE)+","+json.getString(JSON_LATITUDE)+"&destination=-22.91199364,-43.23021412");

            } catch (JSONException e) {

                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapterMeusEvento(GetDataAdapter1, getActivity());

        recyclerView.setAdapter(recyclerViewadapter);



    }

//endregion

/*
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++)
            {
                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);

                HashMap<String,String> persons = new HashMap<String,String>();

                persons.put(TAG_ID,id);
                persons.put(TAG_NAME,name);
                persons.put(TAG_ADD,address);

                personList.add(persons);
            }

            final ListAdapter adapter = new SimpleAdapter
                    (
                    getActivity(), personList, R.layout.list_item,
                    new String[]{TAG_ID,TAG_NAME,TAG_ADD},
                    new int[]{R.id.id, R.id.name, R.id.address}


                    );



            list.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }*/
    private void PesqToDatabase(String name){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String paramUsername = params[0];


                //InputStream is = null;

                String name = String.valueOf(Static.getId());


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("name", name));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://guiziii.esy.es/SelectMEvent.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    resposta = sb.toString();
                    return sb.toString();

                    //is = entity.getContent();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";


            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                // TextView textViewResult = (TextView) findViewById(R.id.textViewResult);
                //textViewResult.setText("Inserted");

                myJSON=result;
                //showList();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(name);

    }




}
