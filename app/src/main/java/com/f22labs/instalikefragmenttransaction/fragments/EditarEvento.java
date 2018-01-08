package com.f22labs.instalikefragmenttransaction.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.f22labs.instalikefragmenttransaction.adapters.ConfigRetrieve;
import com.f22labs.instalikefragmenttransaction.adapters.ServerImageParseAdapter;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class EditarEvento extends BaseFragment
{


    private ProgressDialog loading;
    ImageLoader imageLoader1;
    public NetworkImageView networkImageView2 ;
    // TextView NomeEvento,DataEvento,Horaevento,DescEvento,PrecoEvento,DistanciaE,EnderecoE,CriadorE;
    String idcli;
    TextView txtCidadeEditarEvento,txtEstadoEditarEvento,txtLatitudeEditarEvento,txtLongitudeEditarEvento,editarImagemEvento;

    EditText editarNomeEvento, editarEndEvento , editarDataEvento, editarHoraEvento, editarFaceEvento, editarDescEvento, editarPrecoEvento;
    Button btnCidadeEstado, btnEditarEvento;
    String a, b, c, d;

    public void UpdateEvento()
    {
        String nome_evento = editarNomeEvento.getText().toString();
        String estado_evento = txtEstadoEditarEvento.getText().toString();
        String cidade_evento = txtCidadeEditarEvento.getText().toString();
        String latitude_evento = txtLatitudeEditarEvento.getText().toString();
        String longitude_evento = txtLongitudeEditarEvento.getText().toString();
        String data_evento = editarDataEvento.getText().toString();
        String hora_evento = editarHoraEvento.getText().toString();
        String face_evento = editarFaceEvento.getText().toString();
        String desc_evento = editarDescEvento.getText().toString();
        String preco_evento = editarPrecoEvento.getText().toString();
        String img_evento = editarImagemEvento.getText().toString();
        String id_evento = String.valueOf(Static.getIdevento()).trim();
        UpdateToDatabase(nome_evento, estado_evento, cidade_evento, latitude_evento, longitude_evento, data_evento, hora_evento, face_evento, desc_evento, preco_evento, img_evento,id_evento);

    }

    private void UpdateToDatabase(String nome_evento, String estado_evento, String cidade_evento, String latitude_evento, String longitude_evento, String data_evento, String hora_evento, String face_evento, String desc_evento, String preco_evento, String img_evento, String id_evento) {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {
            @Override
            protected String doInBackground(String... params)
            {
                String paramNome = params[0];
                String paramEstado = params[1];
                String paramCidade = params[2];
                String paramLatitude = params[3];
                String paramLongitude = params[4];
                String paramData = params[5];
                String paramHora = params[6];
                String paramFace = params[7];
                String paramDesc = params[8];
                String paramPreco = params[9];
                String paramImagem = params[10];
                String paramId = params[11];


                String nome_evento = editarNomeEvento.getText().toString();
                String estado_evento = txtEstadoEditarEvento.getText().toString();
                String cidade_evento = txtCidadeEditarEvento.getText().toString();
                String latitude_evento = txtLatitudeEditarEvento.getText().toString();
                String longitude_evento = txtLongitudeEditarEvento.getText().toString();
                String data_evento = editarDataEvento.getText().toString();
                String hora_evento = editarHoraEvento.getText().toString();
                String face_evento = editarFaceEvento.getText().toString();
                String desc_evento = editarDescEvento.getText().toString();
                String preco_evento = editarPrecoEvento.getText().toString();
                String img_evento = editarImagemEvento.getText().toString();
                String id_evento = String.valueOf(Static.getIdevento()).trim();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nome_evento", nome_evento));
                nameValuePairs.add(new BasicNameValuePair("estado_evento", estado_evento));
                nameValuePairs.add(new BasicNameValuePair("cidade_evento", cidade_evento));
                nameValuePairs.add(new BasicNameValuePair("latitude_evento", latitude_evento));
                nameValuePairs.add(new BasicNameValuePair("longitude_evento", longitude_evento));
                nameValuePairs.add(new BasicNameValuePair("data_evento", data_evento));
                nameValuePairs.add(new BasicNameValuePair("hora_evento", hora_evento));
                nameValuePairs.add(new BasicNameValuePair("face_evento", face_evento));
                nameValuePairs.add(new BasicNameValuePair("desc_evento", desc_evento));
                nameValuePairs.add(new BasicNameValuePair("preco_evento", preco_evento));
                nameValuePairs.add(new BasicNameValuePair("img_evento", img_evento));
                nameValuePairs.add(new BasicNameValuePair("id_evento", id_evento));


                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://guiziii.esy.es/UpdateEvento.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
            }


        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(nome_evento, estado_evento, cidade_evento, latitude_evento, longitude_evento, data_evento, hora_evento, face_evento, desc_evento, preco_evento, img_evento, id_evento);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_editar_evento, container, false);



        editarNomeEvento = (EditText) view.findViewById(R.id.editarNomeEvento);
        editarDataEvento = (EditText) view.findViewById(R.id.editarDataEvento);
        editarHoraEvento = (EditText) view.findViewById(R.id.editarHoraEvento);
        editarFaceEvento = (EditText) view.findViewById(R.id.editarFaceEvento);
        editarDescEvento = (EditText) view.findViewById(R.id.editarDescEvento);
        editarPrecoEvento = (EditText) view.findViewById(R.id.editarPrecoEvento);
        editarEndEvento = (EditText) view.findViewById(R.id.editarEndEvento);
        networkImageView2=(NetworkImageView)view.findViewById(R.id.VollyUpdate);
        txtCidadeEditarEvento = (TextView) view.findViewById(R.id.txtCidadeEditarEvento);
        txtEstadoEditarEvento= (TextView) view.findViewById(R.id.txtEstadoEditarEvento);
        txtLatitudeEditarEvento = (TextView) view.findViewById(R.id.txtLatitudeEditarEvento);
        txtLongitudeEditarEvento = (TextView) view.findViewById(R.id.txtLongitudeEditarEvento);
        editarImagemEvento = (TextView) view.findViewById(R.id.editarImagemEvento);

        btnEditarEvento = (Button) view.findViewById(R.id.btnEditarEvento);
        btnEditarEvento.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                UpdateEvento();
            }
        });

        getData();

        return view;
    }


    public void onButtonPressed(Uri uri) {

    }


    public void onAttach(Context context) {
        super.onAttach((Activity) context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void getData()
    {

        loading = ProgressDialog.show(getActivity(), "Carregando informações do evento!", "Por favor, aguarde", false, false);

        String url = ConfigRetrieve.DATA_URL+ String.valueOf(Static.getIdevento()).trim();

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
                //DistanciaE.setText(" "+result);
                //Viewholder.bar.setProgress(32);

            }
            else
            {
                // DistanciaE.setText("Erro inesperado no calculo da distância");
            }


        }
    }

    private void showJSON(String response) {
        String name = "";
        String address = "";
        String vc = "";
        String desc = "";
        String hora = "";
        String preco = "";
        String endereco = "";
        String latitude = "";
        String longitude = "";
        String cidade = "";
        String estado = "";
        String id = "";

        try {
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
            cidade = collegeData.getString(ConfigRetrieve.KEY_CIDADE);
            estado = collegeData.getString(ConfigRetrieve.KEY_ESTADO);
            id = collegeData.getString(ConfigRetrieve.KEY_IDCLI);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageLoader1 = ServerImageParseAdapter.getInstance(getContext()).getImageLoader();

        imageLoader1.get(vc,
                ImageLoader.getImageListener(
                        networkImageView2,//Server Image
                        R.drawable.add3,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );
        networkImageView2.setImageUrl(vc, imageLoader1);



        editarNomeEvento.setText(name);
        editarDataEvento.setText(address);
        editarDescEvento.setText(desc);
        editarHoraEvento.setText(hora);
        editarPrecoEvento.setText(preco);
        editarEndEvento.setText(" " + endereco);
        txtCidadeEditarEvento.setText(cidade);
        txtEstadoEditarEvento.setText(estado);
        txtLongitudeEditarEvento.setText(longitude);
        txtLatitudeEditarEvento.setText(latitude);
        editarImagemEvento.setText(vc);
        idcli = id;
        // getData();
        // new JsonTask().execute("https://maps.googleapis.com/maps/api/directions/json?origin="+latitude.toString().replace(" ", "")+','+longitude.toString().replace(" ", "")+"&destination=-23.2349128,-45.8990308");
        // new JsonTask().execute("https://maps.googleapis.com/maps/api/directions/json?origin=" + longitude.toString().replace(" ", "") + ',' + latitude.toString().replace(" ", "") + "&destination=" + Static.getLatitude() + "," + Static.getLongitude() + "");

        /*
        Tab1 frag = new Tab1();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_left, 0);
        ft.replace(R.id.realtabcontent, frag, "mainFrag");
        ft.commit();
        */

    }



    public interface OnFragmentInteractionListener{

        void onFragmentInteraction(Uri uri);
    }
}
