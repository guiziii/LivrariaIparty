package com.f22labs.instalikefragmenttransaction.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.f22labs.instalikefragmenttransaction.R;
import com.f22labs.instalikefragmenttransaction.activities.MainActivity;
import com.f22labs.instalikefragmenttransaction.adapters.ConfigRetrieve;
import com.f22labs.instalikefragmenttransaction.adapters.ConfigRetrieveCliente;
import com.f22labs.instalikefragmenttransaction.adapters.ServerImageParseAdapter;
import com.f22labs.instalikefragmenttransaction.utils.Static;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;


public class evento extends BaseFragment
{


    TextView NomeEvento,DataEvento,Horaevento,DescEvento,PrecoEvento,DistanciaE,EnderecoE,CriadorE;
    String idcli;

    int fragCount;

    private Toolbar mToolbar;
    ImageLoader imageLoader1;
    public NetworkImageView networkImageView2 ;

    private ProgressDialog loading;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public static evento newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        evento fragment = new evento();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
         View view = inflater.inflate(R.layout.fragment_evento, container, false);

        NomeEvento = (TextView)view.findViewById(R.id.txtNomeEvento);
        DataEvento = (TextView)view.findViewById(R.id.txtData);
        Horaevento = (TextView)view.findViewById(R.id.txtHora);
        DescEvento = (TextView)view.findViewById(R.id.txtDescEvento);
        PrecoEvento =(TextView)view.findViewById(R.id.txtpreco);
        DistanciaE = (TextView)view.findViewById(R.id.txtDistanciaE);
        EnderecoE = (TextView)view.findViewById(R.id.txtEnderecoE);
        CriadorE = (TextView)view.findViewById(R.id.txtCriadorE);
        networkImageView2=(NetworkImageView)view.findViewById(R.id.VollyEspecifico);



        getData();





        ButterKnife.bind(this, view);



        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);






    }

    private void getData()
    {


        loading = ProgressDialog.show(getActivity(), "Carregando informações do evento!", "Por favor, aguarde", false, false);

        String url = ConfigRetrieve.DATA_URL+String.valueOf(Static.getIdevento()).trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            loading.setCancelable(true);
                            e.printStackTrace();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private void getData1()
    {




        String url = ConfigRetrieveCliente.DATA_URL+idcli;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON2(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                           // loading.setCancelable(true);
                            e.printStackTrace();
                        }
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response)
    {
        String name="";
        String address="";
        String vc = "";
        String desc = "";
        String hora = "";
        String preco = "";
        String endereco = "";
        String latitude = "";
        String longitude = "";
        String id = "";

        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(ConfigRetrieve.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            name = collegeData.getString(ConfigRetrieve.KEY_NAME);
            address = collegeData.getString(ConfigRetrieve.KEY_ADDRESS);
            vc = collegeData.getString(ConfigRetrieve.KEY_VC);
            desc = collegeData.getString(ConfigRetrieve.KEY_DESC);
            hora = collegeData.getString(ConfigRetrieve.KEY_HORA);
            preco = collegeData.getString(ConfigRetrieve.KEY_PRECO);
            endereco = collegeData.getString(ConfigRetrieve.KEY_END);
            latitude = collegeData.getString(ConfigRetrieve.KEY_LATITUDE);
            longitude = collegeData.getString(ConfigRetrieve.KEY_LONGITUDE);
            id = collegeData.getString(ConfigRetrieve.KEY_IDCLI);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        imageLoader1 = ServerImageParseAdapter.getInstance(getContext()).getImageLoader();

        imageLoader1.get(vc,
                ImageLoader.getImageListener(
                        networkImageView2,//Server Image
                        R.mipmap.ic_launcher,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );
        networkImageView2.setImageUrl(vc, imageLoader1);

       // NomeEvento.setText(name);
        DataEvento.setText(address);
        DescEvento.setText(desc);
        Horaevento.setText(hora);
        PrecoEvento.setText(preco);
        EnderecoE.setText(" "+endereco);
        ( (MainActivity)getActivity()).updateToolbarTitle((fragCount == 0) ? name: "Sub News "+fragCount);
                idcli =id;
        getData1();
       // new JsonTask().execute("https://maps.googleapis.com/maps/api/directions/json?origin="+latitude.toString().replace(" ", "")+','+longitude.toString().replace(" ", "")+"&destination=-23.2349128,-45.8990308");
        new JsonTask().execute("https://maps.googleapis.com/maps/api/directions/json?origin=" + longitude.toString().replace(" ", "") + ',' + latitude.toString().replace(" ", "") + "&destination=" + Static.getLatitude() + "," + Static.getLongitude() + "");


        // mToolbar.setTitle(name);
        // mToolbar.setSubtitle("R$" + preco);
        //region Click da Toolbar + Ícone da Toolbar
     /*  mToolbar.setNavigationIcon(R.mipmap.back); //Icone de Voltar

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setTitle("");
                mToolbar.setSubtitle("");
                mToolbar.setNavigationIcon(null);
                Tab1 frag = new Tab1();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.realtabcontent, frag, "mainFrag");
                ft.commit();

            }
        });
        //endregion */

    }

    private void showJSON2(String response)
    {
        String name="";
        String email="";
        String login = "";
        String senha = "";


        try
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result2 = jsonObject.getJSONArray(ConfigRetrieveCliente.JSON_ARRAY);
            JSONObject collegeData = result2.getJSONObject(0);
            name = collegeData.getString(ConfigRetrieveCliente.KEY_NAME);
            email = collegeData.getString(ConfigRetrieveCliente.KEY_EMAIL);
            login = collegeData.getString(ConfigRetrieveCliente.KEY_LOGIN);
            senha = collegeData.getString(ConfigRetrieveCliente.KEY_SENHA);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
      CriadorE.setText(" "+name);


    }

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

                JSONObject parentArray3 = finalObject2.getJSONObject("distance");




                String distance = parentArray3.getString("text");

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
                DistanciaE.setText(" "+result);
                //Viewholder.bar.setProgress(32);

            }
            else
            {
                DistanciaE.setText("Erro inesperado no calculo da distância");
            }


        }
    }

}



