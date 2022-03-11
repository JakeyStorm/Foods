package info.androidhive.materialtabs.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import info.androidhive.materialtabs.R;

public class PersonActivity extends AppCompatActivity {
    TextView txtTableNam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        txtTableNam = findViewById(R.id.txtNamber);

        txtTableNam.setText("Столик № " + MenuActivity.txtMesto.getText().toString());
    }
}