package project.don.facebook.ui;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.widget.AdapterView;
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
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import project.don.facebook.R;
        import project.don.facebook.adapter.ImageAdapter;
        import project.don.facebook.model.ModelImages;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SERIOUSLY";
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String USER_TOKEN;
    private RecyclerView recyclerView;
    private ImageAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        initLogin();
    }

    private void initLogin(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        USER_TOKEN = loginResult.getAccessToken().getToken();
                        info.setText("Access Token " + USER_TOKEN);
                        Log.d("TAGGGGGGGGGGGGG",USER_TOKEN);
                        Log.d("wuahh","------------------------------------------------------");
                        initGraph();
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

    private void initGraph() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Insert your code here

                        if (response != null) {
                            JSONObject result = response.getJSONObject();
                            if (result != null) {
                                JSONObject albumsJson = result.optJSONObject("albums");
                                if (albumsJson != null) {
                                    try {
                                        JSONArray dataAr = albumsJson.getJSONArray("data");
                                        for (int i = 0; i < dataAr.length(); i++) {
                                            JSONObject c = dataAr.getJSONObject(i);
                                            if (c != null) {
                                                JSONObject photosJson = c.getJSONObject("photos");
                                                if (photosJson != null) {
                                                    JSONArray dataArray = photosJson.getJSONArray("data");
                                                    for (int j = 0; j < dataArray.length(); j++) {
                                                        JSONObject pictureJson = dataArray.getJSONObject(j);
                                                        ModelImages modelImages = new ModelImages();
//                                                        modelImages.setImageUrl(pictureJson.optString("picture"));
                                                        modelImages.setImageUrl(pictureJson.getString("picture"));
                                                        List<ModelImages> data = new ArrayList<>();
                                                        data.add(modelImages);

                                                        recyclerView = (RecyclerView) findViewById(R.id.rvResult);

                                                        adapter = new ImageAdapter(MainActivity.this, data);
                                                        recyclerView.setAdapter(adapter);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }


                    }
                });



        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums{photos{album,picture}}");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


