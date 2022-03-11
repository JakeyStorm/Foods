package info.androidhive.materialtabs.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialtabs.R;
import info.androidhive.materialtabs.activity.AdapterFood;
import info.androidhive.materialtabs.activity.ConstructorFood;


public class TwoFragment extends Fragment{private RecyclerView rcView;
    private List<ConstructorFood> result = new ArrayList<>();
    private AdapterFood adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    View v;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
        updateList();

    }
    public void init(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("menu/roll");
        rcView = v.findViewById(R.id.rcView);
        rcView.setHasFixedSize(true);
        //rcView.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(RecyclerView.VERTICAL);

        rcView.setLayoutManager(llm);
        adapter = new AdapterFood(result, getContext());
        rcView.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
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
