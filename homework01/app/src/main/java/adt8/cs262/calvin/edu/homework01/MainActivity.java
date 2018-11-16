package adt8.cs262.calvin.edu.homework01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int value1;
    int value2;

    /*
     *  Effectively the constructor for the activity. Initializes everything, including the spinner.
     *
     *   @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.operatorSpinner);
        spinner.setAdapter(
                ArrayAdapter.createFromResource(this,
                        R.array.operators, R.layout.support_simple_spinner_dropdown_item)
        );
    }

    /*
     * Button handler for the calculate button
     *
     * @param view : button that makes the call
     */
    public void onCalculateButtonPressed(View view) {
        TextView resultsTV = findViewById(R.id.resultsTextView);
        value1 = Integer.valueOf(
                ((EditText) findViewById(R.id.value1ET)).getText().toString());
        value2 = Integer.valueOf(
                ((EditText) findViewById(R.id.value2ET)).getText().toString());

        char operatorChar = ((Spinner) findViewById(R.id.operatorSpinner))
                .getSelectedItem().toString().charAt(0);

        switch (operatorChar) {
            case '+':
                resultsTV.setText(String.valueOf(value1 + value2));
                break;
            case '-':
                resultsTV.setText(String.valueOf(value1 - value2));
                break;
            case '*':
                resultsTV.setText(String.valueOf(value1 * value2));
                break;
            case '/':
                resultsTV.setText(String.valueOf(value1 / value2));
                break;
        }
    }
}
