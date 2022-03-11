package info.androidhive.materialtabs.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import info.androidhive.materialtabs.R;

public class AdapterFood extends RecyclerView.Adapter<AdapterFood.FoodViewHolder> {

    private List<ConstructorFood> list;
    private Context parent;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public AdapterFood(List<ConstructorFood> list, Context parent) {
        this.list = list;
        this.parent = parent;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterFood.FoodViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        ConstructorFood constructorFood = list.get(position);
        holder.nameFood.setText(constructorFood.getNameFood());
        holder.desFood.setText(constructorFood.getDesFood());
        holder.priceFood.setText(constructorFood.getPriceFood());
        Picasso.get().load(constructorFood.getImageId()).into(holder.imageFood);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {

        TextView nameFood, desFood, priceFood;
        ImageView imageFood;
        Button btnAdd;
        Intent intentCart;

        public FoodViewHolder(@NonNull final View itemView) {
            super(itemView);
            nameFood = itemView.findViewById(R.id.txt_name);
            imageFood = itemView.findViewById(R.id.imagePreview);
            desFood = itemView.findViewById(R.id.txtDes);
            priceFood = itemView.findViewById(R.id.txtPrice);
            btnAdd = itemView.findViewById(R.id.btnAdd);

            user = mAuth.getInstance().getCurrentUser();
            //if (user != null) {
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("cart");
            //}


//          buttonLike = itemView.findViewById(R.id.button_save);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConstructorFood constructorFood = list.get(getAdapterPosition());
                    if(user != null){
                        myRef.child(user.getUid()).child(constructorFood.getKey()).setValue(constructorFood);
                        Toast.makeText(itemView.getContext(), "Успешно добавлено", Toast.LENGTH_SHORT).show();
                  }
                    else {
                        Toast.makeText(itemView.getContext(), "Зaрегестрируйтесь", Toast.LENGTH_SHORT).show();
                    }
                }
//                @Override
//                public void onClick(View view) {
//                    BookView bookView = list.get(getAdapterPosition());
//                    if(user != null){
//                        myRef.child(bookView.getKey()+bookView.getAuthor()).setValue(bookView);
//                        Toast.makeText(itemView.getContext(), "Успешно добавлено", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            });
        }
    }
}
