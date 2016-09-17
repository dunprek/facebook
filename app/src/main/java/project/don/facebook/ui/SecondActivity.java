package project.don.facebook.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import project.don.facebook.R;
import project.don.facebook.adapter.DataAdapter;
import project.don.facebook.adapter.GridAdapter;
import project.don.facebook.model.DataModel;

public class SecondActivity extends AppCompatActivity {

    private String mId;
    private GridAdapter mAdapter;
    private RecyclerView recyclerView;
    private final List<DataModel> albumList = new ArrayList<DataModel>();
    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getMyAlbumId();
        initUi();
        initAlbum();
    }



    //Get ALBUM ID FROM MAIN ACTIVITY
    private void getMyAlbumId() {
        mId = getIntent().getExtras().getString("id");
    }

    private void initUi(){
        lLayout = new GridLayoutManager(SecondActivity.this, 4);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);
    }


    private void initAlbum() {

        GraphRequest request = GraphRequest.newGraphPathRequest(

                //Set MY ALBUM ID
                AccessToken.getCurrentAccessToken(),"/"+mId,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        if (response != null) {

                            JSONObject jsonObject = response.getJSONObject();
                            if (jsonObject != null) {

                                try {
                                    JSONObject albumsJson = jsonObject.optJSONObject("photos");
                                    if (albumsJson != null) {
                                        JSONArray dataJson = albumsJson.optJSONArray("data");
                                        for (int i = 0; i < dataJson.length(); i++) {
                                            JSONObject jRoot = dataJson.optJSONObject(i);
                                            final DataModel dataModel = new DataModel();
                                            //add data to the model
                                            dataModel.setUrl(jRoot.getString("picture"));


                                                /*add data to arraylist*/
                                            mAdapter = new GridAdapter(getApplicationContext(), albumList);
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
                                System.out.println("--------------------------NO JSON PHOTOS---------------------------------");
                            }


                        } else {
                            System.out.println("****************************NO JSON RESPONSE*****************************");
                        }


                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "photos{picture}");
        request.setParameters(parameters);
        request.executeAsync();

    }

}
