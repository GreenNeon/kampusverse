package com.kampusverse.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kampusverse.BuildConfig;
import com.kampusverse.Logic.ApiBase;
import com.kampusverse.Logic.SharedData;
import com.kampusverse.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditProfile extends AppCompatActivity {

    TextView changeProfile, fromGallery;
    ImageView mImageView;
    String mCurrentPhotoPath;
    Button btnFinish;
    EditText txtNama, txtEmail, txtPass;
    String nama, email, pass;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int PICK_IMAGE_REQUEST = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        changeProfile = findViewById(R.id.changePicture);
        mImageView = findViewById(R.id.profilephoto);
        btnFinish = findViewById(R.id.btnFinishEdit);
        txtNama = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPass);

        txtNama.setText(SharedData.GetInstance().GetUser().getNama());
        fromGallery = findViewById(R.id.fromGallery);

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiData();
            }
        });

    }

    public void validasiData() {
        nama = txtNama.getText().toString();
        email = txtEmail.getText().toString();
        pass = txtPass.getText().toString();
        //kalo mau diarahin activity lain atau nyimpen ke db masukinnya disini yud!!!!!!!!!!!!
        ApiBase api = ApiBase.GetInstance();
        SharedData.GetInstance().GetUser().setNama(nama);
        if (pass.matches("")) {

        }
        api.UpdateUser(getApplicationContext(), SharedData.GetInstance().GetUser().getIDToken(), new ApiBase.SimpleCallback() {
            @Override
            public void OnSuccess(String[] strings) {
                Intent intent = new Intent(EditProfile.this, Beranda.class);
                startActivity(intent);
            }

            @Override
            public void OnFailure(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO) {
            Bitmap bmp = null;
            Bitmap rotatedBitmap = null;
            try {
                bmp = BitmapFactory.decodeFile(mCurrentPhotoPath);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                mImageView.setImageBitmap(rotatedBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_REQUEST) {

            Uri uri = data.getData();
            InputStream in = null;
            try {
                in = getContentResolver().openInputStream(uri);
                ExifInterface exifInterface;
                exifInterface = new ExifInterface(in);
                int rotation = 0;
                int orientation = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotation = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotation = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotation = 270;
                        break;
                }
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotation);
                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    mImageView.setImageBitmap(rotatedBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                // Handle any errors
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "Error while saving picture.", Toast.LENGTH_LONG).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            } else {
                Toast.makeText(getApplicationContext(), "photo file null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        Canvas canvas = new Canvas(rotatedBitmap);
        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

}
