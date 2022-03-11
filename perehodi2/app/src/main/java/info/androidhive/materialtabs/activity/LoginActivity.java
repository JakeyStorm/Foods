package info.androidhive.materialtabs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.androidhive.materialtabs.R;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {
    EditText etMail, etPass;
    Button btnReg;
    Intent menuIntent;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etMail = findViewById(R.id.etMail);
        etPass = findViewById(R.id.etPass);
        btnReg = findViewById(R.id.button2);
        menuIntent = new Intent(LoginActivity.this, MenuActivity.class);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(etMail.getText().toString(), etPass.getText().toString());
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        getUserData();
    }

    private void getUserData(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(menuIntent);
            btnReg.setVisibility(View.VISIBLE);
        }
        else {
            btnReg.setVisibility(View.VISIBLE);
        }
    }

    private void signUp(String email, String password){
        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                getUserData();

                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();

                                Toast.makeText(LoginActivity.this, "Успешно." + user.getEmail(), Toast.LENGTH_SHORT).show();
                                UserConst userConst = new UserConst(user.getUid(), user.getEmail(), "Пока ничего");
                                startActivity(menuIntent);
                                myRef.child(user.getUid()).setValue(userConst);

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
        else {
            Toast.makeText(LoginActivity.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }
}