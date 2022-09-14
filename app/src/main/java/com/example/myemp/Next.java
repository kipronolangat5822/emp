package com.example.myemp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Next extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
private TextView category;
    private EditText nplate,ncounty,nconstituency,nname,npo,nzip,ndist;
    Button nsubmit;
String[] type={"SELECT MODEL","TOYOTA","MAZDA","NISSAN","AUDI","VW","HONDA"};
    DatabaseReference databaseReference;
    String path ="Employees";
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        nplate=findViewById(R.id.xplate);
        ncounty=findViewById(R.id.xcounty);
        nconstituency=findViewById(R.id.xconstituency);
        nname=findViewById(R.id.xname);
        npo=findViewById(R.id.xpo);
        nzip=findViewById(R.id.xzip);
        category=findViewById(R.id.xmodel);
        ndist=findViewById(R.id.xdist);
        nsubmit=findViewById(R.id.xsubmit);
        Spinner spin = (Spinner) findViewById(R.id.simpleSpinner);
        spin.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,type);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrayAdapter);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        nsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          /*      Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                String day=sdf.format(date);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
                String county="kkb 350k";
                reference.child(county).child(day).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull  DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String DB = snapshot.child("name").getValue(String.class);
                            Toast.makeText(Next.this, "name"+DB, Toast.LENGTH_SHORT).show();
                            nsubmit.setText(DB);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull  DatabaseError error) {

                    }
                });

           */

                String name=nname.getText().toString();
                String po=npo.getText().toString();
                String zip=nzip.getText().toString();
                String county=ncounty.getText().toString();
                String constituency=nconstituency.getText().toString();
                String plate=nplate.getText().toString().trim();
                String model=category.getText().toString();
                String distance=ndist.getText().toString();

                if (name.isEmpty()){
                    Toast.makeText(Next.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (po.equals("")){
                    Toast.makeText(Next.this, "PO cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (zip.equals("")){
                    Toast.makeText(Next.this, "Zip Code cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (county.equals("")){
                    Toast.makeText(Next.this, "County cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (constituency.equals("")){
                    Toast.makeText(Next.this, "Constituency cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (plate.equals("")){
                    Toast.makeText(Next.this, "Plate Number cannot be empty", Toast.LENGTH_SHORT).show();
                }
                if (distance.equals("")){
                    Toast.makeText(Next.this, "Distance cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(Next.this, Registration.class);
                    intent.putExtra("name", name);
                    intent.putExtra("po", po);
                    intent.putExtra("zip", zip);
                    intent.putExtra("county", county);
                    intent.putExtra("constituency", constituency);
                    intent.putExtra("plate", plate);
                    intent.putExtra("model", model);
                    intent.putExtra("distance", distance);
                    startActivity(intent);
                }




            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if (parent.getItemAtPosition(position).equals("TOYOTA")){
            category.setText(parent.getSelectedItem().toString());
        }
        else if (parent.getItemAtPosition(position).equals("AUDI")){
            category.setText(parent.getSelectedItem().toString());
        }
        else if (parent.getItemAtPosition(position).equals("NISSAN")){
            category.setText(parent.getSelectedItem().toString());
        }
        else if (parent.getItemAtPosition(position).equals("VW")){
            category.setText(parent.getSelectedItem().toString());
        }else if(parent.getItemAtPosition(position).equals("HONDA")){
            category.setText(parent.getSelectedItem().toString());
        }
        else if(parent.getItemAtPosition(position).equals("MAZDA")){
            category.setText(parent.getSelectedItem().toString());
        }
        else {
            category.setText(null);
        }
        Toast.makeText(getApplicationContext(), type[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}