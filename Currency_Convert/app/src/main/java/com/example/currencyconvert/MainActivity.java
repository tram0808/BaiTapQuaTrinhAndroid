package com.example.currencyconvert;

public class MainActivity {
}
package com.example.myapplication;


        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;

        import androidx.appcompat.app.AppCompatActivity;

        import com.google.gson.JsonObject;

        import Kamal.kamalBuild;
        import Kamal.kamalInterface;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText currencyToBeConverted;
    EditText currencyConverted;
    Spinner convertToDropdown;
    Spinner convertFromDropdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyConverted = (EditText) findViewById(R.id.currency_converted);
        currencyToBeConverted = (EditText) findViewById(R.id.currency_to_be_converted);
        convertToDropdown = (Spinner) findViewById(R.id.to);
        convertFromDropdown = (Spinner) findViewById(R.id.from);
        button = (Button) findViewById(R.id.button);


        String[] dropDownList = {"USD", "INR","EUR","NZD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDownList);
        convertToDropdown.setAdapter(adapter);
        convertFromDropdown.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kamalInterface retrofitInterface = kamalBuild.getRetrofitInstance().create(kamalInterface.class);

                Call<JsonObject> call = retrofitInterface.getExchangeCurrency(convertFromDropdown.getSelectedItem().toString());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject rates = res.getAsJsonObject("conversion_rates");
                        double currency = Double.valueOf(currencyToBeConverted.getText().toString());
                        double multiplier = Double.valueOf(rates.get(convertToDropdown.getSelectedItem().toString()).toString());
                        double result = currency * multiplier;
                        currencyConverted.setText(String.valueOf(result));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }


                });


            }
        });}}


-----------------------------------------------------------
        create a package and in that package create one interface and one class.
        -----------------------------------------------------------
        import retrofit2.converter.gson.GsonConverterFactory;

public class kamalBuild {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.exchangerate-api.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}

-----------------------------------------------------------

        package Kamal;

        import com.google.gson.JsonObject;

        import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Path;

public interface kamalInterface {
    @GET("v6/1929988978e7cd9733e5e654/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
-----------------------------------------------------------
