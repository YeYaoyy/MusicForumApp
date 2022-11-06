package edu.northeastern.numad22fa_team23;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import edu.northeastern.numad22fa_team23.databinding.ActivityMainBinding;
import edu.northeastern.numad22fa_team23.databinding.LayoutStickItToEmBinding;

public class SendingMessage extends AppCompatActivity {

//    private ImageView image1;
//    private Button sendMessageButton;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;

    LayoutStickItToEmBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutStickItToEmBinding.inflate(getLayoutInflater());
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.selectImagebtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPicture();
            }
        });


        binding.image01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = Uri.parse("android.resource://edu.northeastern.numad22fa_team23/drawable/image01");
//                chooseImage();
            }
        });

        binding.image02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = Uri.parse("android.resource://edu.northeastern.numad22fa_team23/drawable/image02");
               // chooseImage();
            }
        });

        binding.image03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = Uri.parse("android.resource://edu.northeastern.numad22fa_team23/drawable/image03");
                //chooseImage();
            }
        });


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            //image1.setImageURI(imageUri);
            //uploadPicture();
        }
    }

    private void uploadPicture() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sending message...");
        progressDialog.show();

        Date now = new Date();
        String filename = now.toString();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images/" + filename);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //binding.image01
                        Toast.makeText(SendingMessage.this, "Successfully send message", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(SendingMessage.this, "Failed to send message", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
