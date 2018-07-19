package br.com.developen.ruralpatrol.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import br.com.developen.ruralpatrol.exception.CannotLoadPlacesException;
import br.com.developen.ruralpatrol.room.PlaceVO;
import br.com.developen.ruralpatrol.util.DB;
import br.com.developen.ruralpatrol.util.Messaging;

public class FindPlacesByDenominationAsyncTask<
        A extends Activity & FindPlacesByDenominationAsyncTask.Listener,
        B extends String,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public FindPlacesByDenominationAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(String... parameters) {

        try {

            DB database = DB.getInstance(activity.get());

            return database.placeDAO().listByDenomination( '%' + parameters[0].toUpperCase() + '%');

        } catch (Exception e) {

            return new CannotLoadPlacesException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A activity = this.activity.get();

        if (activity != null) {

            if (callResult != null) {

                if (callResult instanceof List) {

                    activity.onSuccess((List<PlaceVO>) callResult);

                } else {

                    if (callResult instanceof Messaging){

                        activity.onFailure((Messaging) callResult);

                    }

                }

            }

        }

    }

    public interface Listener {

        void onSuccess(List<PlaceVO> list);

        void onFailure(Messaging messaging);

    }

}