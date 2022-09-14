package com.example.myemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private EditText memail,mpassword;
private TextView mforgot,msignup;
private Button mlogin,mgoogle;
private FirebaseAuth mAuth;
ProgressDialog loading;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=mAuth.getCurrentUser();
        if (currentuser !=null){
            Intent intent=new Intent(MainActivity.this,Next.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading=new ProgressDialog(this);
        memail=findViewById(R.id.xemail);
        mpassword=findViewById(R.id.xpassword);
        mforgot=findViewById(R.id.xforgot);
        mlogin=findViewById(R.id.xlogin);
        mAuth=FirebaseAuth.getInstance();


        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        mforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgot();
            }
        });
        mgoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                google();
            }
        });
        
    }

    private void google() {
    }

    private void forgot() {
    }

    private void login() {
        loading.setMessage("Please wait...");
        loading.setTitle("Signing In");
        loading.show();
        final String email=memail.getText().toString();
        final String password=mpassword.getText().toString();
mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()){
            Intent intent=new Intent(MainActivity.this,Next.class);
            startActivity(intent);
            finish();
            loading.dismiss();

        }
        else{
            String message=task.getException().toString();
            Toast.makeText(MainActivity.this, "Failed to login"+message, Toast.LENGTH_SHORT).show();
            loading.dismiss();
        }

    }
});
    }
}