package br.com.developen.ruralpatrol.task;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.developen.ruralpatrol.exception.HttpRequestException;
import br.com.developen.ruralpatrol.jersey.ExceptionBean;
import br.com.developen.ruralpatrol.jersey.PlaceBean;
import br.com.developen.ruralpatrol.room.PlaceVO;
import br.com.developen.ruralpatrol.util.DB;
import br.com.developen.ruralpatrol.util.Messaging;
import br.com.developen.ruralpatrol.util.RequestBuilder;

public class DownloadPlacesAsyncTask<
        A extends Activity & DownloadPlacesAsyncTask.DownloadFarmsListener,
        B extends Void,
        C extends Void,
        D> extends AsyncTask<B, C, D> {


    private WeakReference<A> activity;


    public DownloadPlacesAsyncTask(A activity) {

        this.activity = new WeakReference<>(activity);

    }


    protected Object doInBackground(Void... parameters) {

        try {

            Response response = RequestBuilder.
                    build("place-list-101.php").
                    request(MediaType.APPLICATION_JSON).
                    get();

            switch (response.getStatus()) {

                case HttpURLConnection.HTTP_OK:

                    return response.readEntity(new GenericType<List<PlaceBean>>(){});

                default:

                    return response.readEntity(ExceptionBean.class);

            }

        } catch (Exception e) {

            e.printStackTrace();

            return new HttpRequestException();

        }

    }


    protected void onPostExecute(Object callResult) {

        A activity = this.activity.get();

        if (activity != null) {

            if (callResult != null) {

                if (callResult instanceof List) {

                    DB database = DB.getInstance(activity);

                    List<PlaceBean> placeBeanList = (List<PlaceBean>) callResult;

                    for (PlaceBean placeBean : placeBeanList){

                        PlaceVO placeVO = new PlaceVO();

                        placeVO.setIdentifier(placeBean.getIdentifier());

                        placeVO.setDenomination(placeBean.getDenomination());

                        placeVO.setType(placeBean.getType());

                        placeVO.setChecked(placeBean.getChecked());

                        placeVO.setStreet(placeBean.getStreet());

                        placeVO.setNumber(placeBean.getNumber());

                        placeVO.setDistrict(placeBean.getDistrict());

                        placeVO.setCity(placeBean.getCity());

                        placeVO.setLatitude(placeBean.getLatitude());

                        placeVO.setLongitude(placeBean.getLongitude());

                        placeVO.setSince(placeBean.getSince());

                        if (database.placeDAO().exists(placeVO.getIdentifier()))

                            database.placeDAO().update(placeVO);

                        else

                            database.placeDAO().create(placeVO);

                    }

                    activity.onSuccess();

                } else {

                    if (callResult instanceof Messaging)

                        activity.onFailure((Messaging) callResult);

                }

            }

        }

    }

    public interface DownloadFarmsListener{

        void onSuccess();

        void onFailure(Messaging messaging);

    }

}