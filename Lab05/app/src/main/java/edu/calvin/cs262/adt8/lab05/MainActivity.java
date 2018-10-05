package edu.calvin.cs262.adt8.lab05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>{


    EditText editText;
    TextView authorTv, titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.bookInput);
        authorTv = findViewById(R.id.authorText);
        titleTv = findViewById(R.id.titleText);

        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    public void searchBooks(View view) {
        String queryString = editText.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            //new FetchBook(titleTv, authorTv).execute(queryString);
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle,this);

            titleTv.setText(R.string.loading_string);
            authorTv.setText("");
        }
        else {
            if (queryString.length() == 0) {
                authorTv.setText("");
                titleTv.setText("Please enter a search term");
            } else {
                authorTv.setText("");
                titleTv.setText("Please check your network connection and try again.");
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new BookLoader(this, bundle.getString("queryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        try {
            JSONObject jObject = new JSONObject(s);
            JSONArray jArray = jObject.getJSONArray("items");
            //Iterate through the results
            for(int i = 0; i<jArray.length(); i++){
                JSONObject book = jArray.getJSONObject(i); //Get the current item
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }

                //If both a title and author exist, update the TextViews and return
                if (title != null && authors != null){
                    titleTv.setText(title);
                    authorTv.setText(authors);
                    return;
                }
            }
            titleTv.setText(R.string.noResults);
            authorTv.setText("");
        } catch (Exception e){
            e.printStackTrace();
            titleTv.setText(R.string.noResults);
            authorTv.setText("");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
