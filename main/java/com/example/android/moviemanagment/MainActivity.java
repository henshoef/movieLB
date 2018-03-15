package com.example.android.moviemanagment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadImageTask.CallBack {


    private String movieName = "";
    private String movieDesc = "";
    private String movieUrl = "";
    private int counter = 1;
    private float sizeW = 0;
    private float sizeH = 0;
    private MenuItem addMovieByMenual;
    private MenuItem addMovieByIntenet;
    private MenuItem deleteAll;
    private MenuItem exit;
    private final int requsetCode = 1;
    private LinearLayout mainLayout;
    private ArrayList<MovieSample> movies;
    private LinearLayout.LayoutParams imageViewDetails;
    private LinearLayout.LayoutParams TextViewDetails;
    private MovieSample movieSample;
    private ImageView iv;
    private String state = "add";
    DownloadImageTask downloadImageTask;
    private int resultCode;
    DataBaseHandler db;
    int i;
    String tempTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHandler(this);
        movies =db.getAllMovieList(this);
        i=0;



        //creating a Layout and setting it's background color
        mainLayout = findViewById(R.id.mainLayout);
        //creating a Button Array
//        movies = new ArrayList<MovieSample>();
        //here we set the height and width of the button and EditText to match their content


        //setting the EditText sizeW by dp casted to px ("setWidth" takes only px)
        Resources r = getResources();
        sizeW = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                r.getDisplayMetrics());
        sizeH = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300,
                r.getDisplayMetrics());
        TextViewDetails =new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT

        );
        imageViewDetails = new LinearLayout.LayoutParams(
                Math.round(sizeW),
                Math.round(sizeH)

        );
        while(i<movies.size()){
            setDisplayMovie();
            i++;
        }
    }

    public void setDisplayMovie(){
        final TextView movieSampleName=new TextView(this);
    movieSampleName.setText(movies.get(i).getName());
    movieSampleName.setTextColor(getResources().getColor(R.color.white));
        InputStream imageStream = this.getResources().openRawResource(R.raw.image);
        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
         iv = new ImageView(this);
   //     iv.setLayoutParams(imageViewDetails);

    if (!movies.get(i).getImageUrl().equals("")) {
        downloadImageTask = new DownloadImageTask(this);
        downloadImageTask.execute(movies.get(i).getImageUrl());

    }else{
        
        iv.setImageBitmap(bitmap);
    }
    mainLayout.addView(movieSampleName,TextViewDetails);
    mainLayout.addView(iv,imageViewDetails);
    final CustomDialog cdd=new CustomDialog(MainActivity.this,movies.get(i).getName(),counter);
        iv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        mainLayout.removeView(movieSample);
        mainLayout.removeView(movieSampleName);


        }
    });
//    iv.setOnLongClickListener(new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View view) {
//            cdd.show();
//            return false;
//        }
//    });
//    movies.add(movieSample);
//    mainLayout.addView(movieSampleName, TextViewDetails);
//    mainLayout.addView(movieSample, imageViewDetails);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        // Need to find menu items only here because they belong to the menu and not to the main layout.
        // Also, they are not created yet on the onCreate event:

        addMovieByMenual = menu.findItem(R.id.addMovieByManual);
        addMovieByIntenet = menu.findItem(R.id.addMovieByInternet);
        deleteAll = menu.findItem(R.id.deleteAll);
        exit = menu.findItem(R.id.exit);
        return true;
    }
public void addMovie(Intent data){
    state = "add";
    movieName = data.getStringExtra("name");
    movieDesc = data.getStringExtra("description");
    movieUrl = data.getStringExtra("url");
    movieSample = new MovieSample(this, counter, movieName, movieDesc, movieUrl);
    db.addMovie(movieSample);
//    final TextView movieSampleName=new TextView(this);
//    movieSampleName.setText(movieName);
//    movieSampleName.setTextColor(getResources().getColor(R.color.white));
//    InputStream imageStream = this.getResources().openRawResource(R.raw.image);
//    Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//    movieSample.setImageBitmap(bitmap);
//    if (!movieUrl.equals("")) {
//        downloadImageTask = new DownloadImageTask(this);
//        downloadImageTask.execute(movieUrl);
//    }
//    final CustomDialog cdd=new CustomDialog(MainActivity.this,movieName,counter);
//    movieSample.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//        mainLayout.removeView(movieSample);
//        mainLayout.removeView(movieSampleName);
//
//
//        }
//    });
//    movieSample.setOnLongClickListener(new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View view) {
//            cdd.show();
//            return false;
//        }
//    });
//    movies.add(movieSample);
//    mainLayout.addView(movieSampleName, TextViewDetails);
//    mainLayout.addView(movieSample, imageViewDetails);
}
    // Return true to state that the menu event has been handled.
    // Return false to state that the menu event has not been handled.
    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requsetCode == 1 && resultCode == RESULT_OK) {
           addMovie(data);

        }
        this.recreate();
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menuItemSettings:
                return true;
            case R.id.addMovieByManual: {
                Intent intent = new Intent(this, AddMovie.class);
                intent.putExtra("state", state);
                startActivityForResult(intent, requsetCode);
                onActivityResult(requsetCode, resultCode, intent);
                state = "add";
                counter++;
                return true;
            }
            case R.id.addMovieByInternet: {
                Intent intent = new Intent(this, InternetActivity.class);
                startActivityForResult(intent, requsetCode);
                return true;
            }
            case R.id.deleteAll: {
                db.clear();
                restart();
               // mainLayout.removeAllViews();
                return true;
            }
            case R.id.exit: {
                finish();
                return true;
            }
        }

        return false;
    }
    public void loadMovie( ){

        //movies =db.getAllMovieList();

        i =0;

        if (movies.size() == i) {

           // TextView empty = (TextView) findViewById(R.id.showAll);

           // empty.setVisibility(View.VISIBLE);

        } else {

            while(i<movies.size()) {

                String s = movies.get(i).getName();

                tempTitle =s;

                String u = movies.get(i).getImageUrl();

                String d = movies.get(i).getDecription();

                int id =movies.get(i).getId();

                //addMovie();



                i++;

            }

        }

    }
    public void restart(){

        Intent intent = new Intent(this, MainActivity.class);

        this.startActivity(intent);

        this.finishAffinity();

    }


    @Override
    public void onPreExecute() {

    }

    @Override
    public void onSuccses(Bitmap result) {
        iv.setImageBitmap(result);
    }
}