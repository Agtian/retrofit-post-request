package com.example.retrofitpostrequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    // Initialize variable
    EditText etName, etTrip;
    Button btSubmit;
    RecyclerView recyclerView;

    String sBaseUrl = "https://api.instantwebtools.net/v1/";
    String sName, sTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign variable
        etName = findViewById(R.id.et_name);
        etTrip = findViewById(R.id.et_trip);
        btSubmit = findViewById(R.id.bt_submit);
        recyclerView = findViewById(R.id.recycler_view);

        // Call method
        getPassenger();
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values from edit text
                sName = etName.getText().toString().trim();
                sTrip = etTrip.getText().toString().trim();
                // Check condition
                if (!sName.isEmpty() && !sTrip.isEmpty()) {
                    // When both values are not empty
                    // Call method
                    addPassenger();
                }
            }
        });
    }

    // Api Interface
    private interface getInter{
        // Get request
        @GET("passenger")

        // Initialize string
        Call<String> STRING_CALL(
            @Query("page") String page,
            @Query("size") String size
        );
    }

    // Api method
    private void getPassenger() {
        // Initialize progress dialog
        ProgressDialog dialog = ProgressDialog.show(
                this, "", "Please wait...", true
        );

        // Initialize retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        // Initialize interface
        getInter inter = retrofit.create(getInter.class);

        // Pass input value
        Call<String> call = inter.STRING_CALL("756", "25");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // When response is successsful and body is not empty
                    // Dismiss dialog
                    dialog.dismiss();
                    try {
                        // Initialize json object
                        JSONObject object = new JSONObject(response.body());
                        // Get json array
                        JSONArray jsonArray = object.getJSONArray("data");
                        // Initialize layout manager
                        GridLayoutManager layoutManager = new GridLayoutManager(
                                MainActivity.this, 2
                        );
                        // Set layout manager
                        recyclerView.setLayoutManager(layoutManager);
                        // Set adapter
                        recyclerView.setAdapter(new MainAdapter(jsonArray));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    // Api interface
    private interface postInter{
        // Post request
        @FormUrlEncoded
        @POST("passenger")
        // Initialize string call
        Call<String> STRING_CALL (
                @Field("name") String name,
                @Field("trips") String trips,
                @Field("airline") String airline
        );
    }

    private void addPassenger() {
        // Initialize retrofit
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        // Initialize interface
        postInter inter = retrofit.create(postInter.class);
        // Pass input values
        Call<String> call = inter.STRING_CALL(sName, sTrip, "1");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Check condition
                if (response.isSuccessful() && response.body() != null) {
                    // When response is successful & body is not empty
                    // Clear edit text values
                    etName.getText().clear();
                    etTrip.getText().clear();
                    // Call get method
                    getPassenger();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}