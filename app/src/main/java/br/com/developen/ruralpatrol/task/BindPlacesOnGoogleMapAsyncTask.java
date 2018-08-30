package br.com.developen.ruralpatrol.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.developen.ruralpatrol.MapsActivity;
import br.com.developen.ruralpatrol.exception.CannotLoadPlacesException;
import br.com.developen.ruralpatrol.room.PlaceVO;
import br.com.developen.ruralpatrol.util.DB;
import br.com.developen.ruralpatrol.util.IconUtils;
import br.com.developen.ruralpatrol.util.Messaging;

public class BindPlacesOnGoogleMapAsyncTask<
        A extends MapsActivity,
        B extends GoogleMap,
        C extends Void,
        D extends Void,
        E> extends AsyncTask<C, D, E> {


    private static final int ICON_WIDTH = 72;

    private static final int ICON_HEIGHT = 100;

    private Map<Integer, Bitmap> bitmapList;

    private WeakReference<A> activity;

    private WeakReference<B> googleMap;


    public BindPlacesOnGoogleMapAsyncTask(A activity, B googleMap) {

        this.activity = new WeakReference<>(activity);

        this.googleMap = new WeakReference<>(googleMap);

    }


    protected Object doInBackground(Void... parameters) {

        try {

            DB database = DB.getInstance(activity.get());

            return database.placeDAO().list();

        } catch (Exception e) {

            return new CannotLoadPlacesException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A activity = this.activity.get();

        B googleMap = this.googleMap.get();

        if (activity != null && googleMap != null) {

            if (callResult != null) {

                if (callResult instanceof List) {

                    List<PlaceVO> placeVOList = (List<PlaceVO>) callResult;

                    for (PlaceVO placeVO : placeVOList) {

                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(placeVO.getLatitude(), placeVO.getLongitude())));

                        marker.setTag(placeVO);

                        marker.setIcon(
                                BitmapDescriptorFactory.
                                        fromBitmap(getBitmapList(activity).get(placeVO.getType())));

                        activity.getMarkers().put(placeVO.getIdentifier(), marker);

                    }

                    activity.goToMyLocation();

                } else {

                    if (callResult instanceof Messaging){}

                }

            }

        }

    }

    private Map<Integer, Bitmap> getBitmapList(Context context){

        if (bitmapList == null){

            bitmapList = new HashMap<>();

            bitmapList.put(1, getBitmap(context, IconUtils.getPinIconByType(1)));

            bitmapList.put(2, getBitmap(context, IconUtils.getPinIconByType(2)));

            bitmapList.put(3, getBitmap(context, IconUtils.getPinIconByType(3)));

            bitmapList.put(4, getBitmap(context, IconUtils.getPinIconByType(4)));

            bitmapList.put(5, getBitmap(context, IconUtils.getPinIconByType(5)));

            bitmapList.put(6, getBitmap(context, IconUtils.getPinIconByType(6)));

            bitmapList.put(7, getBitmap(context, IconUtils.getPinIconByType(7)));

            bitmapList.put(8, getBitmap(context, IconUtils.getPinIconByType(8)));

            bitmapList.put(9, getBitmap(context, IconUtils.getPinIconByType(9)));

        }

        return bitmapList;

    }

    private Bitmap getBitmap(Context context, Integer icon){

        BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(icon);

        Bitmap bitmap = bitmapDrawable.getBitmap();

        return Bitmap.createScaledBitmap(bitmap, ICON_WIDTH, ICON_HEIGHT, false);

    }

}