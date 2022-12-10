package edu.northeastern.numad22fa_team23;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import edu.northeastern.numad22fa_team23.model.ProjectComment;
import edu.northeastern.numad22fa_team23.model.ProjectMoment;

public class ProjectMomentsActivity extends AppCompatActivity {

    ProjectMomentsAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private String groupName;
    private String userName;
    FirebaseAuth auth;
    private List<HashMap<String, Object>> list;
    private boolean isLocationPermissionGranted = false;
    LocationManager locationManager;
    Location location;
    String provider;
    private SensorManager sensorManager;
    private Sensor temperature;
    private String nowTemp;
    private HardwarePropertiesManager mHardwarePropertiesManager;
    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location locationN) {
            location = locationN;
            //System.out.println("changed:" + location.getLatitude() + " " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i("changed: ", "enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i("changed: ", "disabled");
        }
    };

    private static String SERVER_KEY = "key=AAAAYVMPBrg:APA91bFcn3zDzceEIocqvzaKlPRBN1dKIdThGYeYK443c1A96HrITFGU8J3-VIj1u5ymAHbau-AsH3rpEsrUcN6E7FpCpz9XJjPGFuXDBx33-N_o-I2JLgepGt3qfMudTuCKGnWLKVy3";

    public static float cpuTemperature()
    {
        Process process;
        try {
            process = Runtime.getRuntime().exec("cat sys/class/thermal/thermal_zone0/temp");
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if(line!=null) {
                float temp = Float.parseFloat(line);
                return temp / 1000.0f;
            }else{
                return 51.0f;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(mSensorEventListener, temperature, SensorManager.SENSOR_DELAY_NORMAL);
//        Context context = getBaseContext();
//        mHardwarePropertiesManager = (HardwarePropertiesManager) context.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE);
//        float[] temps = mHardwarePropertiesManager.getDeviceTemperatures(
//                HardwarePropertiesManager.DEVICE_TEMPERATURE_SKIN,
//                HardwarePropertiesManager.TEMPERATURE_CURRENT);
//        nowTemp = String.valueOf(temps[1]);

        nowTemp = String.valueOf(cpuTemperature()) + "°c";
        Intent i = getIntent();
        Bundle data = i.getExtras();
        userName = data.getString("username");
        groupName = data.getString("groupname");

//        userName = "testUser1";
//        groupName = "testGroup1";

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getData();
        fab = (FloatingActionButton) findViewById(R.id.fab_addMoment);
        /*
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoment);
        adapter = new MomentsAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MomentsActivity.this));
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLocationPermission();
                showAddMomentDialog();
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        requestPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(mSensorEventListener, temperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManager.unregisterListener(mSensorEventListener);
    }

    private final SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                float nowTemperature = event.values[0];
                nowTemp = nowTemperature + "°c";
            }
            if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
                float nowTemperature = event.values[0];
                nowTemp = nowTemperature + "hPa";
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };

    private void initLocationPermission() {
        if (ActivityCompat.checkSelfPermission(ProjectMomentsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProjectMomentsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            //return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.GPS_PROVIDER;
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            System.out.println(getLocationAddress(location));
        }
        locationManager.requestLocationUpdates(provider, 2000, 0, locationListener);
    }


    private void showAddMomentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectMomentsActivity.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_moment_dialog, null);
        builder.setTitle("Add new moment");
        builder.setView(dialogView);
        Button addMoment = (Button) dialogView.findViewById(R.id.button_addMoment);
        EditText musicName = (EditText) dialogView.findViewById(R.id.musicDialogName);
        EditText thought = (EditText) dialogView.findViewById(R.id.thoughtContent);
        AlertDialog alertDialog = builder.create();
        addMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable test = musicName.getText();
                if ("".equals(musicName.getText().toString()) || "".equals(thought.getText().toString())) {
                    Toast.makeText(ProjectMomentsActivity.this, "Please fill all EditText", Toast.LENGTH_LONG).show();
                    return;
                }
                mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (!task.isSuccessful()) {
                            Log.e("firebase", "moments add connection failed", task.getException());
                        } else {
                            list = (List<HashMap<String, Object>>) task.getResult().getValue();

                            List<ProjectMoment> newList = new ArrayList<>();
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            for (int i = 0; i < list.size(); i++) {
                                ProjectMoment m = new ProjectMoment();
                                m.setGroupId((String) list.get(i).get("groupId"));
                                m.setMusicName((String) list.get(i).get("musicName"));
                                m.setThought((String) list.get(i).get("thought"));
                                m.setUserName((String) list.get(i).get("userName"));
                                m.setMomentId(((Long) list.get(i).get("momentId")).intValue());
                                m.setLocation((String) list.get(i).get("location"));
                                m.setWeather((String) list.get(i).get("weather"));
                                List<HashMap<String, Object>> comm = (List<HashMap<String, Object>>) list.get(i).get("commentList");
                                if (comm == null) {
                                    comm = new ArrayList<>();
                                }
                                List<ProjectComment> newComm = new ArrayList<>();
                                for (int j = 0; j < comm.size(); j++) {
                                    ProjectComment c = new ProjectComment();
                                    c.setMomentId(((Long) comm.get(j).get("momentId")).intValue());
                                    c.setUserName((String) comm.get(j).get("userName"));
                                    c.setContent((String) comm.get(j).get("content"));
                                    newComm.add(c);
                                }
                                m.setCommentList(newComm);
                                newList.add(m);
                            }
                            ProjectMoment newMoment = new ProjectMoment();
                            newMoment.setMomentId(newList.size());
                            newMoment.setGroupId(groupName);
                            newMoment.setMusicName(musicName.getText().toString());
                            newMoment.setThought(thought.getText().toString());
                            newMoment.setUserName(userName);
                            //get location
                            if (ActivityCompat.checkSelfPermission(ProjectMomentsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProjectMomentsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
                                //requestPermission();
                                Toast.makeText(ProjectMomentsActivity.this, "Must permit sensor use!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            //location = locationManager.getLastKnownLocation(provider);
                            if (location == null) {
                                Toast.makeText(ProjectMomentsActivity.this, "Cannot get location!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            String addr = getLocationAddress(location);
                            newMoment.setLocation(addr);
                            newMoment.setWeather(nowTemp);
                            newList.add(newMoment);
                            mDatabase.child("Groups").child(groupName).child("moments").setValue(newList);
                            Toast.makeText(ProjectMomentsActivity.this, "Post New Moment successfully!", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                //getData();
            }
        });
        Button cancelAddMoment = (Button)dialogView.findViewById(R.id.button_cancelAddMoment);
        cancelAddMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setCancelable(true);
        alertDialog.show();
    }

    private String getLocationAddress(Location location) {
        String addr = "";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            Address address = addresses.get(0);
            String newaddr = address.getAddressLine(0);
            String[] newAddrArray = newaddr.split(",");
            addr = newAddrArray[0] + "," + newAddrArray[1];
        } catch (IOException e) {
            addr = "";
            e.printStackTrace();
        }
        return addr;
    }


    private void requestPermission() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                isLocationPermissionGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                isLocationPermissionGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
                            } else {
                                // No location access granted.
                            }
                        }
                );

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you sure you want to dismiss?")
                .setPositiveButton("Yes",
                        (dialog, id) -> {
                            finish();
                        })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .create();
        builder.show();
    }

    private void getData() {
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "moments connection failed", task.getException());
                }
                else {
                    list = (List<HashMap<String, Object>>)task.getResult().getValue();
                    //List<HashMap<String, Object>> comm;
                    List<ProjectMoment> newList = new ArrayList<>();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        ProjectMoment m = new ProjectMoment();
                        m.setGroupId((String)list.get(i).get("groupId"));
                        m.setMusicName((String)list.get(i).get("musicName"));
                        m.setThought((String)list.get(i).get("thought"));
                        m.setUserName((String)list.get(i).get("userName"));
                        m.setMomentId(((Long)list.get(i).get("momentId")).intValue());
                        m.setLocation((String) list.get(i).get("location"));
                        m.setWeather((String) list.get(i).get("weather"));
                        List<HashMap<String, Object>> comm = (List<HashMap<String, Object>>)list.get(i).get("commentList");
                        if (comm == null) {
                            comm = new ArrayList<>();
                        }
                        List<ProjectComment> newComm = new ArrayList<>();
                        for (int j = 0; j < comm.size(); j++) {
                            ProjectComment c = new ProjectComment();
                            c.setMomentId(((Long)comm.get(j).get("momentId")).intValue());
                            c.setUserName((String) comm.get(j).get("userName"));
                            c.setContent((String) comm.get(j).get("content"));
                            newComm.add(c);
                        }
                        m.setCommentList(newComm);
                        newList.add(m);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoment);
                    adapter = new ProjectMomentsAdapter(newList, getApplication());
                    adapter.setOnItemClickListener(new ProjectMomentsAdapter.OnItemClickListener() {
                        @Override
                        public void onButtonClicked(View view, int position, int momentId) {
                            showAddCommentDialog(momentId);
                            Toast.makeText(ProjectMomentsActivity.this, position + ":" + momentId, Toast.LENGTH_LONG).show();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProjectMomentsActivity.this));
                }
            }
        });
    }
    private void showAddCommentDialog(int momentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectMomentsActivity.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        builder.setTitle("Add new comment");
        builder.setView(dialogView);
        Button addComment = (Button)dialogView.findViewById(R.id.button_addComment);
        EditText comment = (EditText) dialogView.findViewById(R.id.commentContent);
        AlertDialog alertDialog = builder.create();
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable test = comment.getText();
                if ("".equals(comment.getText().toString())) {
                    Toast.makeText(ProjectMomentsActivity.this, "Please fill EditText", Toast.LENGTH_LONG).show();
                    return;
                }
                mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (!task.isSuccessful()) {
                            Log.e("firebase", "comments add connection failed", task.getException());
                        }
                        else {
                            list = (List<HashMap<String, Object>>)task.getResult().getValue();
                            List<HashMap<String, Object>> comm;
                            List<ProjectMoment> newList = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                ProjectMoment m = new ProjectMoment();
                                m.setGroupId((String) list.get(i).get("groupId"));
                                m.setMusicName((String) list.get(i).get("musicName"));
                                m.setThought((String) list.get(i).get("thought"));
                                m.setUserName((String) list.get(i).get("userName"));
                                m.setMomentId(((Long)list.get(i).get("momentId")).intValue());
                                m.setLocation((String) list.get(i).get("location"));
                                m.setWeather((String) list.get(i).get("weather"));
                                comm = (List<HashMap<String, Object>>)list.get(i).get("commentList");
                                if (comm == null) {
                                    comm = new ArrayList<>();
                                }
                                List<ProjectComment> newComm = new ArrayList<>();
                                for (int j = 0; j < comm.size(); j++) {
                                    ProjectComment c = new ProjectComment();
                                    c.setMomentId(((Long)comm.get(j).get("momentId")).intValue());
                                    c.setUserName((String) comm.get(j).get("userName"));
                                    c.setContent((String) comm.get(j).get("content"));
                                    newComm.add(c);
                                }
                                if (m.getMomentId() == momentId) {
                                    ProjectComment c = new ProjectComment();
                                    c.setMomentId(momentId);
                                    c.setUserName(userName);
                                    c.setContent(comment.getText().toString());
                                    newComm.add(c);
                                }
                                m.setCommentList(newComm);
                                newList.add(m);
                            }
                            mDatabase.child("Groups").child(groupName).child("moments").setValue(newList);
                            Toast.makeText(ProjectMomentsActivity.this, "Post New Comment successfully!", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                //getData();
            }
        });
        Button cancelAddMoment = (Button)dialogView.findViewById(R.id.button_cancelAddComment);
        cancelAddMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setCancelable(true);
        alertDialog.show();
    }
}