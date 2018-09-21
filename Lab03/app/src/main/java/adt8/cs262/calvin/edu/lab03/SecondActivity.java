package adt8.cs262.calvin.edu.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "adt8.cs262.calvin.edu.lab03.extra.REPLY";

    private EditText replyET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = (TextView) findViewById(R.id.text_message);
        textView.setText(message);

        replyET = findViewById(R.id.editText_second);
    }

    public void returnReply(View view) {
        String reply = replyET.getText().toString();
        Intent i = new Intent();
        i.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, i);
        finish();
    }
}
