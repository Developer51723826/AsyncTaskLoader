package com.example.asynctaskloadera;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>
{
    public static final String DATA_KEY = "data_key";
    public static final String TAG = "my_Tag";
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tex1);
        runCode();
    }

    public void runCode(){
        Bundle bundle=new Bundle();
        bundle.putString(DATA_KEY,"some url that returns some data");
        getSupportLoaderManager().restartLoader(100,null, (androidx.loader.app.LoaderManager.LoaderCallbacks<Object>) this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle args) {

        List<String> songsList= Arrays.asList(Palylist.songs);

        return new MyTaskLoader(this,args,songsList)
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
          log(data);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {


    }


    public static class MyTaskLoader extends AsyncTaskLoader<String> {
        private Bundle mArgs;
        private List<String> mSongsList;

        public MyTaskLoader(@NonNull Context context, Bundle mArgs, List<String> mmSongsList) {
            super(context);
            this.mArgs = mArgs;
            mSongsList = mmSongsList;

        }

        @Nullable
        @Override
        public String loadInBackground() {
            String data = mArgs.getString(DATA_KEY);

            Log.d(TAG, "loadInBackgrud: URL: " + data);

            Log.d(TAG, "loadInBackgrud: Thread Name: " + Thread.currentThread().getName());

            for (String songs : mSongsList) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "LoadInBackground: Song Name: " + songs);
            }

            Log.d(TAG, "LoadInBackground: Thread Terminated: ");
            return "result from loader";
        }

        @Override
        public void deliverResult(@Nullable String data) {

            data+= ":modified" ;
            super.deliverResult(data);
        }
    }

    private void log(String message) {
        Log.i(TAG, message);
        //  textView.append(message + "\n");
        textView.setText(message);
    }
}