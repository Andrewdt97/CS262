package edu.calvin.cs262.adt8.lab05;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchBook extends AsyncTask<String, Void, String> {

    private TextView titleText, authorText;

    public FetchBook(TextView titleText, TextView authorText) {
        this.titleText = titleText;
        this.authorText = authorText;
    }

    @Override
    protected String doInBackground(String... params) {
        return NetworkUtils.getBookInfo(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
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
                    titleText.setText(title);
                    authorText.setText(authors);
                    return;
                }
            }
            titleText.setText(R.string.noResults);
            authorText.setText("");
        } catch (Exception e){
            e.printStackTrace();
            titleText.setText(R.string.noResults);
            authorText.setText("");
        }
    }
}
