package com.example.first_app_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddPlace extends AppCompatActivity {

    private TextView txtPlaceName, txtPlaceYear, txtPlaceDescriotion;
    private Button btnAddPlace, btnSelectImage;
    private ImageView imgPlace;
    private Uri selectedImageUri;

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

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPlaceName.getText() == null)
                {
                    Toast.makeText(AddPlace.this, "Enter Place Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtPlaceYear.getText() == null && IsInteger(txtPlaceYear.getText().toString()))
                {
                    Toast.makeText(AddPlace.this, "Enter Correct Place Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(txtPlaceDescriotion.getText() == null)
                {
                    Toast.makeText(AddPlace.this, "Enter Place Description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedImageUri == null)
                {
                    Toast.makeText(AddPlace.this, "Choose Place Image", Toast.LENGTH_SHORT).show();
                    return;
                }
                Place tempPlace = new Place(Place.placeArrayList.size() + 1, Integer.valueOf(txtPlaceYear.getText().toString()),
                        txtPlaceName.getText().toString(), selectedImageUri, txtPlaceDescriotion.getText().toString(),
                        txtPlaceDescriotion.getText().toString());
                sqLiteManager.addPlaceRatingToDatabase(tempPlace);
                Place.placeArrayList.add(tempPlace);
                Intent intent = new Intent(AddPlace.this, AllPlacesActivity.class);
                startActivity(intent);
            }
        });
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