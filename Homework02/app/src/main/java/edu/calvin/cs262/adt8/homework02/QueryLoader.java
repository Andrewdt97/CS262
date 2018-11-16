package edu.calvin.cs262.adt8.homework02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

class QueryLoader extends AsyncTaskLoader<String> {
    private final String queryString;

    /*
     * Constructor for the query loader
     */
    public QueryLoader(@NonNull Context context, String str) {
        super(context);
        queryString = str;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /*
     * Call to getInfo
     */
    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getInfo(queryString);
    }
}
