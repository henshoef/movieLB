package com.example.android.moviemanagment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;


public class AddMovie extends AppCompatActivity implements DownloadImageTask.CallBack {
    //EditText title=findViewById(R.id.title);
    String state;
    DownloadImageTask downloadImageTask;
    ImageView imageView;
    EditText urlET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        imageView=findViewById(R.id.imageView);
    }
public void onShowClick(View v){
     urlET = findViewById(R.id.url);
     String url=urlET.getText().toString();
    downloadImageTask=new DownloadImageTask(this);
    downloadImageTask.execute(url);
    //progressDialog = new ProgressDialog(getBaseContext());
    //progressDialog.setTitle("Downloading...");
    //progressDialog.setMessage("Please Wait...");
}
    public void onClickOk(View v) {
        Intent intent = getIntent();
        state = intent.getStringExtra("state");
        EditText title = findViewById(R.id.title);
        EditText description = findViewById(R.id.descrition);
        urlET = findViewById(R.id.url);
            String movieName = title.getText().toString();
            String desc = description.getText().toString();
            String imageUrl = urlET.getText().toString();
            intent.putExtra("name", movieName);
            intent.putExtra("description", desc);
            intent.putExtra("url", imageUrl);
            if(movieName.equals("")){
                Toast toast = Toast.makeText(this, "You must enter a movie name", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                setResult(RESULT_OK, intent);

                finish();
            }
    }

    public void onClickCancel(View v) {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void onPreExecute() {

    }


    public void onSuccses(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
