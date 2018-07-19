package br.com.developen.ruralpatrol.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.com.developen.ruralpatrol.R;
import br.com.developen.ruralpatrol.room.PlaceVO;
import br.com.developen.ruralpatrol.util.IconUtils;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public MarkerInfoWindowAdapter(Context context) {

        this.context = context.getApplicationContext();

    }

    public View getInfoWindow(Marker arg0) {

        return null;

    }

    public View getInfoContents(Marker arg0) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View v =  inflater.inflate(R.layout.activity_maps_marker, null);

        TextView denominationTextView = v.findViewById(R.id.marker_denomination_textview);

        TextView streetTextView = v.findViewById(R.id.marker_street_textview);

        TextView numberTextView = v.findViewById(R.id.marker_number_textview);

        TextView districtTextView = v.findViewById(R.id.marker_district_textview);

        TextView cityTextView = v.findViewById(R.id.marker_city_textview);


        PlaceVO placeVO = (PlaceVO) arg0.getTag();

        denominationTextView.setText(placeVO.getDenomination());


        //Street
        boolean hasStreet = placeVO.getStreet() != null && !placeVO.getStreet().isEmpty();

        if (hasStreet)

            streetTextView.setText(placeVO.getStreet());

        streetTextView.setVisibility(hasStreet ? View.VISIBLE : View.GONE);


        //Number
        boolean hasNumber = placeVO.getNumber() != null && !placeVO.getNumber().isEmpty();

        if (hasNumber)

            numberTextView.setText(placeVO.getNumber());

        numberTextView.setVisibility(hasNumber ? View.VISIBLE : View.GONE);


        //District
        boolean hasDistrict = placeVO.getDistrict() != null && !placeVO.getDistrict().isEmpty();

        if (hasDistrict)

            districtTextView.setText(placeVO.getDistrict());

        districtTextView.setVisibility(hasDistrict ? View.VISIBLE : View.GONE);


        //City
        boolean hasCity = placeVO.getCity() != null && !placeVO.getCity().isEmpty();

        if (hasCity)

            cityTextView.setText(placeVO.getCity());

        cityTextView.setVisibility(hasCity ? View.VISIBLE : View.GONE);


        return v;

    }

}