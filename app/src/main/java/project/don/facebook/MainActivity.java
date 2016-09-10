package project.don.facebook;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

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


        import org.json.JSONException;
        import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SERIOUSLY";
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String USER_TOKEN;

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
                        JSONObject jsonObject = response.getJSONObject();

                        try {
                            Log.i(TAG,jsonObject.getString("albums"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "-------------------------");
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "albums.limit(2){photos{picture}}");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}


