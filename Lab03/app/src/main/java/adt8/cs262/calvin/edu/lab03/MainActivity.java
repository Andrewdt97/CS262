package adt8.cs262.calvin.edu.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int TEXT_REQUEST = 1;
    private TextView replyHeadTV, replyTV;

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "adt8.cs262.calvin.edu.lab03.extra.MESSAGE";
    private EditText mMessageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageEditText = findViewById(R.id.editText_main);
        replyHeadTV = findViewById(R.id.text_header_reply);
        replyTV = findViewById(R.id.text_message_reply);
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");

        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessageEditText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == TEXT_REQUEST ) {
            if ( resultCode == RESULT_OK )
            {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                replyHeadTV.setVisibility(View.VISIBLE);
                replyTV.setVisibility(View.VISIBLE);
                replyTV.setText(reply);
            }
        }
    }
}
