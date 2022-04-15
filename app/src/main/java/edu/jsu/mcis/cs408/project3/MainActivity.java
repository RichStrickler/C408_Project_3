package edu.jsu.mcis.cs408.project3;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import edu.jsu.mcis.cs408.project3.databinding.ActivityMainBinding;

// Despite the fact that it says there are issues with the above import
// everything compiles fine.

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private WebServiceDemoViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        model = new ViewModelProvider(this).get(WebServiceDemoViewModel.class);
        model.sendGetRequest();

        // Create Observer (to update the UI with response data)


        final Observer<JSONObject> jsonObserver = new Observer<JSONObject>() {
            @Override
            public void onChanged(@Nullable final JSONObject newJSON) {
                if (newJSON != null) {
                    try {

                        setOutputText(newJSON.get("messages").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Observe the JSON LiveData

        model.getJsonData().observe(this, jsonObserver);

        // Set Button Listeners (to initiate GET/POST requests)

        binding.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.sendClearRequest();
            }
        });

        // Set Button Listener (to initiate POST requests)

        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.setMessage(binding.userMessage.getText().toString());
                model.sendPostRequest();
            }
        });

    }

    // Update Output Text

    private void setOutputText(String s) {
        binding.output.setText(s);
    }

}