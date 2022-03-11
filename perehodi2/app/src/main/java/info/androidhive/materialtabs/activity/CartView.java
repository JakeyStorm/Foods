package info.androidhive.materialtabs.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.androidhive.materialtabs.R;

public class CartView extends AppCompatActivity {
    private RecyclerView rcView;
    private List<ConstructorFood> result = new ArrayList<>();
    private AdapterFood adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private ConstraintLayout Coplata;
    private Intent intentOpl;

    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        init();
        updateList();

        Coplata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentOpl);
            }
        });

    }

    public void init(){
        intentOpl = new Intent(CartView.this, Oplata.class);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
//        ConstructorFood constructorFood = new ConstructorFood();
        reference = database.getReference("cart/"+user.getUid());
        rcView = findViewById(R.id.rcViewCart);
        rcView.setHasFixedSize(true);
        Coplata = findViewById(R.id.COplata);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);

        rcView.setLayoutManager(llm);
        adapter = new AdapterFood(result, this);
        rcView.setAdapter(adapter);

    }
    private void updateList(){
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ConstructorFood constructorFood = snapshot.getValue(ConstructorFood.class);
                constructorFood.setKey(snapshot.getKey());//получение ключа не трогать а то крашитьс апк
                result.add(constructorFood);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ConstructorFood constructorFood = snapshot.getValue(ConstructorFood.class);
                constructorFood.setKey(snapshot.getKey());
                int index = getItemIndex(constructorFood);
                result.set(index, constructorFood);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                ConstructorFood constructorFood = snapshot.getValue(ConstructorFood.class);
                constructorFood.setKey(snapshot.getKey());
                int index = getItemIndex(constructorFood);
                result.remove(index);
                adapter.notifyItemRemoved(index);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private int getItemIndex(ConstructorFood constructorFood){
        int index = -1;
        for (int i = 0; i < result.size(); i++){
            if (result.get(i).getKey() != null && result.get(i).getKey().equals(constructorFood.getKey())){
                index = i;
                break;
            }
        }
        return index;
    }
}