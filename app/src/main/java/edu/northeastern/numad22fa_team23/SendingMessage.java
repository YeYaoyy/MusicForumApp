package edu.northeastern.numad22fa_team23;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import edu.northeastern.numad22fa_team23.databinding.ActivityMainBinding;
import edu.northeastern.numad22fa_team23.databinding.LayoutStickItToEmBinding;

public class SendingMessage extends AppCompatActivity {

//    private ImageView image1;
//    private Button sendMessageButton;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private ImageView selectedImage;
    private String selectedUsername;

    LayoutStickItToEmBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutStickItToEmBinding.inflate(getLayoutInflater());
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance().getReference();
        setSpinner();

//        binding.selectImagebtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                chooseImage();
//            }
//        });

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUsername = binding.spinner.getSelectedItem().toString();
                uploadPicture();
            }
        });


        binding.image01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = binding.image01;
                imageUri = Uri.parse("android.resource://edu.northeastern.numad22fa_team23/drawable/image01");
//                chooseImage();
            }
        });

        binding.image02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = binding.image02;
                imageUri = Uri.parse("android.resource://edu.northeastern.numad22fa_team23/drawable/image02");
               // chooseImage();
            }
        });

        binding.image03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage = binding.image03;
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

    private void setSpinner() {
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                HashMap<String, HashMap<String, User>> map = (HashMap<String, HashMap<String, User>>) dataSnapshot.getValue();
//                User user = dataSnapshot.getValue(User.class);
//                System.out.println(user.get("users"));
                HashMap<String, User> user = map.get("users");
                List<String> userNames = new ArrayList<>();

                for (String u : user.keySet()) {
                    userNames.add(u);
                }
                ArrayAdapter<String> adapter
                        = new ArrayAdapter<>(getApplicationContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        userNames);
                binding.spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                databaseError.toException();
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

    public void onSendButtonPressend(View v) {



    }

    public void createNotificationChannel() {
        // This must be called early because it must be called before a notification is sent.
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Team_23_Stick_It_To_Em";
            String description = "Team_23_Stick_It_To_Em";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Team_23_Stick_It_To_Em_Notification", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

//    public void sendNotification(View view){
//        // Prepare intent which is triggered if the
//        // notification is selected
//        Intent intent = new Intent(this, ReceiveNotificationActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        PendingIntent callIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(),
//                new Intent(this, FakeCallActivity.class), 0);
//
//
//        // Build notification
//        // Actions are just fake
//        String channelId = getString(R.string.channel_id);
//
////        Notification noti = new Notification.Builder(this)   DEPRECATED
//        Notification noti = new NotificationCompat.Builder(this,channelId)
//
//                .setContentTitle("New mail from " + "test@gmail.com")
//                .setContentText("Subject").setSmallIcon(R.drawable.foo)
//
//                .addAction(R.drawable.foo, "Call", callIntent).setContentIntent(pIntent).build();
////                .addAction(R.drawable.icon, "More", pIntent)
////              .addAction(R.drawable.icon, "And more", pIntent).build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        // hide the notification after its selected
//        noti.flags |= Notification.FLAG_AUTO_CANCEL ;
//
//        notificationManager.notify(0, noti);
//    }


}
