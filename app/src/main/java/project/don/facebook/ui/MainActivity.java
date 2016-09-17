package project.don.facebook.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import project.don.facebook.R;
import project.don.facebook.adapter.DataAdapter;
import project.don.facebook.model.DataModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SERIOUSLY";
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String USER_TOKEN;
    private final List<DataModel> albumList = new ArrayList<DataModel>();
    private RecyclerView recyclerView;
    DataAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        initUi();
        initLogin();
    }

    private void initUi() {
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        recyclerView = (RecyclerView) findViewById(R.id.rv);


    }

    private void initLogin() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        USER_TOKEN = loginResult.getAccessToken().getToken();
                        info.setText("Access Token " + USER_TOKEN);
                        Log.d("TAGGGGGGGGGGGGG", USER_TOKEN);
                        Log.d("wuahh", "------------------------------------------------------");
                        initAlbum();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        info.setText("Login attempt canceled.");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        info.setText("Login attempt failed.");
                    }
                });
    }


    private void initAlbum() {

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        if (response != null) {

                            JSONObject jsonObject = response.getJSONObject();
                            if (jsonObject != null) {

                                try {
                                    JSONObject albumsJson = jsonObject.optJSONObject("albums");
                                    if (albumsJson != null) {
                                        JSONArray dataJson = albumsJson.optJSONArray("data");
                                        for (int i = 0; i < dataJson.length(); i++) {
                                            JSONObject jRoot = dataJson.getJSONObject(i);
                                            final DataModel dataModel = new DataModel();
                                            //add data to the model
                                            dataModel.setAlbumName(jRoot.getString("name"));
                                            dataModel.setPhotoCount(jRoot.getString("photo_count"));

                                            JSONObject pictureRoot = jRoot.getJSONObject("picture");
                                            if (pictureRoot != null) {
                                                JSONObject dataRoot = pictureRoot.getJSONObject("data");
                                                if (dataRoot != null) {
                                                    dataModel.setUrl(dataRoot.getString("url"));
                                                    albumList.add(dataModel);
                                                }
                                            }



                                                /*add data to arraylist*/
                                            mAdapter = new DataAdapter(getApplicationContext(), albumList);
                                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                            recyclerView.setLayoutManager(mLayoutManager);
                                            recyclerView.setAdapter(mAdapter);


                                        }


                                    } else {
                                        System.out.println("///////////////////////////NO JSON DATA////////////////////////////");
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("--------------------------NO JSON ALBUMS---------------------------------");
                            }


                        } else {
                            System.out.println("****************************NO JSON RESPONSE*****************************");
                        }


                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{name,photo_count,picture}");
        request.setParameters(parameters);
        request.executeAsync();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


