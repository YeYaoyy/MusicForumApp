package edu.northeastern.numad22fa_team23;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.IDataHolder;
import edu.northeastern.numad22fa_team23.model.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtYourService extends AppCompatActivity {
    private ProgressBar progressBar;
    private CheckBox citySearch;
    private CheckBox postCodeSearch;

    List<EditText> inputForCity = new ArrayList<>();
    List<EditText> inputForPostCode = new ArrayList<>();

    LinearLayout linearLayout;
    Button confirmed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_at_your_service);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        citySearch = findViewById(R.id.search_by_city);
        postCodeSearch = findViewById(R.id.search_by_post_code);
        linearLayout = findViewById(R.id.linear_layout);
        confirmed = findViewById(R.id.confirmChoice);

        citySearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    inputCity();
                    postCodeSearch.setChecked(false);
                    int n = inputForPostCode.size();
                    if (n != 0) {
                        inputForPostCode.get(0).setVisibility(View.GONE);
                    }
                }
            }
        });

        postCodeSearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    inputPostCode();
                    citySearch.setChecked(false);
                    citySearch.setChecked(false);
                    int n = inputForCity.size();
                    if (n != 0) {
                        inputForCity.get(0).setVisibility(View.GONE);
                        inputForCity.get(1).setVisibility(View.GONE);
                    }
                }
            }
        });

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        if (citySearch.isChecked()) {
                            Bundle b = new Bundle();

                            b.putInt("flag", 0);
                            b.putString("state", inputForCity.get(0).getText().toString());
                            b.putString("city", inputForCity.get(1).getText().toString());

                            Intent intent = new Intent(AtYourService.this, ActivityOuput.class);

                            intent.putExtras(b);
                            startActivity(intent);
                        } else if (postCodeSearch.isChecked()){
                            Bundle b = new Bundle();

                            b.putInt("flag", 1);
                            b.putString("postCode", inputForPostCode.get(0).getText().toString());

                            Intent intent = new Intent(AtYourService.this, ActivityOuput.class);

                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    }
                }, 3000);
            }
        });

    }

    public void inputCity() {
        inputForCity = new ArrayList<>();
        LinearLayout editTextLayout = new LinearLayout(this);
        editTextLayout.setOrientation(LinearLayout.VERTICAL);
        String[] hint = new String[]{"State:", "City:"};
        linearLayout.addView(editTextLayout);

        for (int i = 0; i <= 1; i++) {
            EditText editText = new EditText(this);
            editText.setHint(hint[i]);
            setEditTextAttributes(editText);
            editTextLayout.addView(editText);
            inputForCity.add(editText);
        }

    }

    public void inputPostCode() {
        inputForPostCode = new ArrayList<>();
        LinearLayout editTextLayout = new LinearLayout(this);
        editTextLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.addView(editTextLayout);
        EditText editText = new EditText(this);
        editText.setHint("PostCode:");
        setEditTextAttributes(editText);
        editTextLayout.addView(editText);
        inputForPostCode.add(editText);

    }


    private void setEditTextAttributes(EditText editText) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(convertDpToPixel(16),
                convertDpToPixel(16),
                convertDpToPixel(16),
                0
        );

        editText.setLayoutParams(params);
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
