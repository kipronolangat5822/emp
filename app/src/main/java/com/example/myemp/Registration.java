package com.example.myemp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity{
private Button rnext,rage1,rage2,rage3,rage4,rage5,rage6;
private CircleImageView rmale,rfemale,rcamera;
private TextView rages,rgender;
private ImageView ba;
EditText idn;
    String Storage_Path = "images";
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String Database_Path ="Employees";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loading=new ProgressDialog(this);
        idn=findViewById(R.id.xxid);
        ba=findViewById(R.id.back);
        rnext=findViewById(R.id.xnext);
        rage1=findViewById(R.id.button1);
        rage2=findViewById(R.id.button2);
        rage3=findViewById(R.id.button3);
        rage4=findViewById(R.id.button4);
        rage5=findViewById(R.id.button5);
        rage6=findViewById(R.id.button6);
        rmale=findViewById(R.id.xmale);
        rfemale=findViewById(R.id.xfemale);
        rcamera=findViewById(R.id.xcamera);
        rages=findViewById(R.id.xages);
        rgender=findViewById(R.id.xgender);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        rcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), REQUEST_IMAGE_CAPTURE);


            }
        });
        ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registration.this,Next.class);
                startActivity(intent);
                finish();
            }
        });

        rnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
        rmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgender.setText("male");
            }
        });
        rfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgender.setText("female");
            }
        });
        rage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("15-30");
            }
        });
        rage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("31-40");
            }
        });
        rage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("41-50");
            }
        });
        rage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("51-60");
            }
        });
        rage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("61-73");
            }
        });
        rage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("74-");
            }
        });
        rage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rages.setText("15-30");
            }
        });


    }

    private void next() {
        String age = rages.getText().toString();
        String gender = rgender.getText().toString();
        String id=idn.getText().toString();
        String name=getIntent().getStringExtra("name");
        String po=getIntent().getStringExtra("po");
        String zip=getIntent().getStringExtra("zip");
        String county=getIntent().getStringExtra("county");
        String constituency=getIntent().getStringExtra("constituency");
        String plate=getIntent().getStringExtra("plate");
        String model=getIntent().getStringExtra("model");
        String distance=getIntent().getStringExtra("distance");

        if (age.isEmpty()){
            Toast.makeText(this, "Please Choose Your Age", Toast.LENGTH_SHORT).show();
        }
        if (gender.isEmpty()){
            Toast.makeText(this, "Please Choose Your Gender", Toast.LENGTH_SHORT).show();
        }
        if (id.isEmpty()){
            Toast.makeText(this, "Please Enter Your Id", Toast.LENGTH_SHORT).show();
        }
        else {
            loading.setTitle("Upload");
            loading.setMessage("Please wait....");
            loading.setCanceledOnTouchOutside(true);
            loading.show();
            if (FilePathUri != null) {
                Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
                String day=sdf.format(date);
                StorageReference storageReference2nd = storageReference.child(Storage_Path +
                        System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
                String time= String.valueOf(System.currentTimeMillis());

                storageReference2nd.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        loading.dismiss();

                        Toast.makeText(getApplicationContext(), "Uploaded Successfully ", Toast.LENGTH_LONG).show();
                       Intent intent=new Intent(Registration.this,Next.class);
                       startActivity(intent);
                       finish();
                        User upload = new User(id, gender, age, downloadUrl.toString(),name,po,zip,county,constituency,plate,model,distance);
                        databaseReference.child(plate).setValue(upload);



                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loading.dismiss();
                                Toast.makeText(Registration.this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                loading.setTitle("Saving");

                            }
                        });


            } else {

                Toast.makeText(Registration.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {


            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                rcamera.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

}