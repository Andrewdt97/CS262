package edu.calvin.cs262.adt8.homework02;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private boolean containsJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /*
     * Inflates the options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
     * Selects options and launches linked activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Button handler for fetch button
     */
    public void onFetchBtnPressed(View view) {
        EditText et = findViewById(R.id.idEt);
        String queryString = et.getText().toString();

        if (queryString.isEmpty()) {
            queryString = "players/";
            this.containsJsonArray = true;
        } else if (Integer.parseInt(queryString) >= 0) {
            queryString = "player/" + queryString;
            this.containsJsonArray = false;
        }  //TODO: THROW ERROR


        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }
    }

    /*
     * Loader constructor
     */
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new QueryLoader(this, bundle.getString("queryString"));
    }

    /*
     * Display of loader results
     */
    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Player[] playersToDisplay;
        boolean error = false;

        if (this.containsJsonArray) {
            playersToDisplay = parsePlayersArray(s);
        } else {
            playersToDisplay = parseSinglePlayer(s);
        }
        for (Player p : playersToDisplay) {
            if (p.id == -1) {
                error = true;
            }
        }

        if (!error) {
            ListAdapter adapter = new ListAdapter(this, playersToDisplay);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(this, R.string.errorMessage,
                    Toast.LENGTH_LONG).show();
        }
    }

    /*
     * Handler for loader reset
     */
    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private Player[] parsePlayersArray(String JsonString) {
        Player[] playersArray = new Player[3];

        try {
            JSONObject jObject = new JSONObject(JsonString);
            JSONArray jArray = jObject.getJSONArray("items");
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject player = jArray.getJSONObject(i);
                playersArray[i] = new Player();

                try {
                    if (player.has("id")) {
                        playersArray[i].setId(player.getInt("id"));
                    }


                    if (player.has("emailAddress")) {
                        playersArray[i].setEmail(player.getString("emailAddress"));
                    }


                    if (player.has("name")) {
                        playersArray[i].setName(player.getString("name"));
                    } else {
                        playersArray[i].setName("no name");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playersArray;
    }

    private Player[] parseSinglePlayer(String JsonString) {
        Player[] playersArray = new Player[1];
        playersArray[0] = new Player();

        try {
            JSONObject player = new JSONObject(JsonString);

            if (player.has("id")) {
                playersArray[0].setId(Integer.parseInt(player.getString("id")));
            }


            if (player.has("emailAddress")) {
                playersArray[0].setEmail(player.getString("emailAddress"));
            }


            if (player.has("name")) {
                playersArray[0].setName(player.getString("name"));
            } else {
                playersArray[0].setName("no name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return playersArray;
    }
}
