package edu.calvin.cs262.adt8.homework02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ListAdapter extends ArrayAdapter {
    private final Activity context;


    private final Player[] playerArray;

    /* Sets the list adapter.
     *
     */
    public ListAdapter(Activity context, Player[] playerArray) {
        super(context, R.layout.listview_row, playerArray);
        this.context = context;
        this.playerArray = playerArray;
    }

    /* Gets the View associated with the listView
     *
     */
    public View getView(int idx, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_row, null, true);

        TextView idTv = (TextView) rowView.findViewById(R.id.id);
        TextView emailTv = (TextView) rowView.findViewById(R.id.email);
        TextView nameTv = (TextView) rowView.findViewById(R.id.name);

        if (rowView != null) {
            idTv.setText(String.valueOf(playerArray[idx].id));
            emailTv.setText(playerArray[idx].email);
            nameTv.setText(playerArray[idx].name);
        }

        return rowView;

    }

}
