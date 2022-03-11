package info.androidhive.materialtabs.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.fragments.OneFragment;
import info.androidhive.materialtabs.fragments.ThreeFragment;
import info.androidhive.materialtabs.fragments.TwoFragment;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnPizza, btnFish, btnSoup, btnCart;
    ConstraintLayout СPizza, CFhish, CSoup;
    View view;
    ImageView btnScanner, btnPerson;
    Intent intentCart;
    public static TextView txtMesto;
    public static String numTable = "0";


    private List<ConstructorFood> result = new ArrayList<>();
    private AdapterFood adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    private DatabaseReference myRef;



    private ImageView btnMic;
    private SoundPool sounds;
    private int sound_sirena;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new OneFragment()).commit();
        init();
        btnPizza.setOnClickListener(this);
        btnFish.setOnClickListener(this);
        btnSoup.setOnClickListener(this);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentCart);
            }
        });
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScannerActivity.class));
            }
        });


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status !=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        createSoundPool();

        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PersonActivity.class));
            }
        });
    }

    public void onCLickMic(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ConstructorFood constructorFood = new ConstructorFood();
        if(resultCode == RESULT_OK && data !=null){
            if(requestCode==10){
                ArrayList<String> text= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if(text.get(0).equals("Пеперони")||text.get(0).equals("пеперони")){
                    textToSpeech.speak("успешно добавленно!",TextToSpeech.QUEUE_FLUSH,null);
                        reference.setValue(constructorFood.getKey());
                }
                Toast.makeText(MenuActivity.this, text.get(0), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createNewSoundPool(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sounds = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .build();
    }

    @SuppressWarnings("depracation")
    protected void createOldSoundPool(){
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
    }
    protected void createSoundPool(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            createNewSoundPool();
        }else {
            createOldSoundPool();
        }
    }


    public void init() {
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference = database.getReference("сart/"+mAuth.getCurrentUser().getUid());
        btnPerson = findViewById(R.id.btnPerson);
        btnMic = findViewById(R.id.btnAssestent);
        txtMesto = findViewById(R.id.txtMesto);
        btnScanner = findViewById(R.id.btnScanner);
        btnPizza = findViewById(R.id.btnPIzza);
        btnFish = findViewById(R.id.btnFish);
        btnSoup = findViewById(R.id.btnSoup);
        CFhish = findViewById(R.id.CPhish);
        CSoup = findViewById(R.id.CSoup);
        СPizza = findViewById(R.id.Cpizza);
        btnCart = findViewById(R.id.btnCart);
        intentCart = new Intent(MenuActivity.this, CartView.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
            Fragment selectedFragment = null;
            switch (v.getId()) {
                case R.id.btnPIzza:
                    СPizza.setBackground(getDrawable(R.drawable.btn_menu_back_red));
                    CFhish.setBackground(getDrawable(R.drawable.btn_menu_back));
                    CSoup.setBackground(getDrawable(R.drawable.btn_menu_back));
                    selectedFragment = new OneFragment();
                    break;
                case R.id.btnFish:
                    СPizza.setBackground(getDrawable(R.drawable.btn_menu_back));
                    CFhish.setBackground(getDrawable(R.drawable.btn_menu_back_red));
                    CSoup.setBackground(getDrawable(R.drawable.btn_menu_back));
                    selectedFragment = new TwoFragment();
                    break;
                case R.id.btnSoup:
                    СPizza.setBackground(getDrawable(R.drawable.btn_menu_back));
                    CFhish.setBackground(getDrawable(R.drawable.btn_menu_back));
                    CSoup.setBackground(getDrawable(R.drawable.btn_menu_back_red));
                    selectedFragment = new ThreeFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, selectedFragment).commit();
    }


    /*public void choisFragment(){
        Fragment selectedFragment = null;
        switch (view.) {
            case R.id.btnPIzza:
                selectedFragment = new OneFragment();
                break;
            case R.id.btnFish:
                selectedFragment = new TwoFragment();
                break;
            case R.id.btnSoup:
                selectedFragment = new ThreeFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, selectedFragment).commit();*/


//        final Fragment[] selectedFragment = {null};
//        btnPIzza.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFragment[0] = new OneFragment();
//            }
//        });
//
//        btnSoup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFragment[2] = new ThreeFragment();
//            }
//        });
//
//        btnFish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedFragment[1] = new TwoFragment();
//            }
//        });

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, selectedFragment[0]).commit();
    }