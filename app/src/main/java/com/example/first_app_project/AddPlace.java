package com.example.first_app_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AddPlace extends AppCompatActivity {

    private TextView txtPlaceName, txtPlaceYear, txtPlaceDescriotion;
    private Button btnAddPlace, btnSelectImage, btnAddAudioNote;
    private ImageView imgPlace;
    private Uri selectedImageUri;
    private MediaRecorder mediaRecorder;
    private String audioSavePath = null;
    private File tempAudioFile;

    private boolean isRecording = false;

    private int SELECT_PICTURE = 200;

    SQLiteManager sqLiteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        txtPlaceName = findViewById(R.id.txtPlaceName);
        txtPlaceYear = findViewById(R.id.txtPlaceYear);
        txtPlaceDescriotion = findViewById(R.id.txtPlaceDescription);
        btnAddPlace = findViewById(R.id.btnAddPlace);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        imgPlace = findViewById(R.id.imgPlace);
        btnAddAudioNote = findViewById(R.id.btnAddAudioNote);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("New_Audio_Note", "New Audio Note", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnAddAudioNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission() == true)
                {
                    if(mediaRecorder == null) {

                        audioSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/" + "tempAudioNote.mp3";
                        mediaRecorder = new MediaRecorder();
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        mediaRecorder.setOutputFile(audioSavePath);

                        tempAudioFile = new File(audioSavePath);
                    }

                    if(!isRecording) {

                        if(tempAudioFile.exists())
                        {
                            tempAudioFile.delete();
                        }

                        try {
                            Toast.makeText(AddPlace.this, "Start Recording", Toast.LENGTH_SHORT).show();

                            mediaRecorder.prepare();
                            mediaRecorder.start();

                            btnAddAudioNote.setText("STOP RECORDING");
                            isRecording = true;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        mediaRecorder.stop();
                        mediaRecorder.release();

                        mediaRecorder = null;
                        btnAddAudioNote.setText("ADD NEW AUDIO NOTE");

                        Toast.makeText(AddPlace.this, "Stop Recording", Toast.LENGTH_SHORT).show();

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(AddPlace.this, "New_Audio_Note");
                        builder.setContentTitle("New Audio Note!");
                        builder.setContentText("New audio note has been added...");
                        builder.setSmallIcon(R.drawable.ic_up_arrow);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AddPlace.this);
                        managerCompat.notify(1, builder.build());

                        isRecording = false;
                    }
                }
                else
                {
                    Toast.makeText(AddPlace.this, "Permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(AddPlace.this, new String[] {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
            }
        });

        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPlaceName.getText().toString().matches(""))
                {
                    Toast.makeText(AddPlace.this, "Enter Place Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtPlaceYear.getText().toString().matches("") && IsInteger(txtPlaceYear.getText().toString()))
                {
                    Toast.makeText(AddPlace.this, "Enter Correct Place Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtPlaceDescriotion.toString().matches(""))
                {
                    Toast.makeText(AddPlace.this, "Enter Place Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedImageUri == null)
                {
                    Toast.makeText(AddPlace.this, "Choose Place Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(isRecording)
                {
                    Toast.makeText(AddPlace.this, "Stop the recording first", Toast.LENGTH_SHORT).show();
                    return;
                }

                File placeAudioFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music/" + txtPlaceName.getText().toString() + "AudioNote.mp3");
                tempAudioFile.renameTo(placeAudioFile);
                Place tempPlace = new Place(Place.placeArrayList.size() + 1, Integer.parseInt(txtPlaceYear.getText().toString()),
                        txtPlaceName.getText().toString(), selectedImageUri, txtPlaceDescriotion.getText().toString(),
                        txtPlaceDescriotion.getText().toString());
                sqLiteManager.addPlaceRatingToDatabase(tempPlace);
                Place.placeArrayList.add(tempPlace);
                Intent intent = new Intent(AddPlace.this, AllPlacesActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPermission()
    {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int third = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        return first == PackageManager.PERMISSION_GRANTED && second == PackageManager.PERMISSION_GRANTED && third == PackageManager.PERMISSION_GRANTED;
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_OPEN_DOCUMENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ContentResolver cr;
        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    cr = this.getContentResolver();
                    cr.takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    imgPlace.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public static boolean IsInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}