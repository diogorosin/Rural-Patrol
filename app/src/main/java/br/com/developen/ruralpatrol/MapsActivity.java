package br.com.developen.ruralpatrol;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.developen.ruralpatrol.room.PlaceVO;
import br.com.developen.ruralpatrol.task.BindPlacesOnGoogleMapAsyncTask;
import br.com.developen.ruralpatrol.task.FindPlacesByDenominationAsyncTask;
import br.com.developen.ruralpatrol.util.IconUtils;
import br.com.developen.ruralpatrol.util.Messaging;
import br.com.developen.ruralpatrol.widget.MarkerInfoWindowAdapter;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback,
        FindPlacesByDenominationAsyncTask.Listener{


    private static final int FINE_LOCATION_PERMISSION_REQUEST = 1;

    private static final LatLng MARAVILHA_SC = new LatLng(-26.766813, -53.176872);


    private HashMap<Integer, Marker> markers;

    private GoogleMap mMap;

    private FloatingSearchView mSearchView;

    private LocationManager mLocationManager;


    private LocationListener mLocationListener = new LocationListener() {

        public void onLocationChanged(Location location) {}

        public void onStatusChanged(String s, int i, Bundle bundle) {}

        public void onProviderEnabled(String s) {}

        public void onProviderDisabled(String s) {}

    };


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString(SplashActivity.MESSAGE) != null)

            Toast.makeText(
                    getApplicationContext(),
                    bundle.getString(SplashActivity.MESSAGE),
                    Toast.LENGTH_LONG).show();

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mSearchView = findViewById(R.id.floating_search_view);

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {

            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

                PlaceSuggestions placeSuggestion = (PlaceSuggestions) item;

                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), IconUtils.getListIconByType(placeSuggestion.mPlace.getType()), null));

                leftIcon.setColorFilter(Color.parseColor("#000000"));

                leftIcon.setAlpha(.36f);

            }

        });

        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals(""))

                    mSearchView.clearSuggestions();

                else

                    new FindPlacesByDenominationAsyncTask<>(MapsActivity.this).execute(newQuery);

            }

        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {

            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {

                PlaceSuggestions suggestion = (PlaceSuggestions) searchSuggestion;

                if (getMarkers().get(suggestion.mPlace.getIdentifier()) != null) {

                    final Marker marker = getMarkers().get(suggestion.mPlace.getIdentifier());

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(marker.getPosition())
                            .zoom(20)
                            .bearing(0)
                            .build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {

                        public void onFinish() {

                            marker.showInfoWindow();

                        }

                        public void onCancel() {}

                    });

                }

                mSearchView.clearSearchFocus();

                mSearchView.setSearchText(((PlaceSuggestions) searchSuggestion).mPlace.getDenomination());

            }

            public void onSearchAction(String query) {}

        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {

            public void onActionMenuItemSelected(MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.action_place:

                        goToMyLocation();

                        break;

                }

            }

        });

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case FINE_LOCATION_PERMISSION_REQUEST: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    goToMyLocation();

                }

            }

        }

    }

    public void goToMyLocation() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    FINE_LOCATION_PERMISSION_REQUEST);

        } else {

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    10, 5000, mLocationListener);

            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(location == null ? MARAVILHA_SC : new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(13)
                    .bearing(0)
                    .build();

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            if (!mMap.isMyLocationEnabled())

                mMap.setMyLocationEnabled(true);

        }

    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getApplicationContext());

        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        mMap.getUiSettings().setCompassEnabled(false);

        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            public void onInfoWindowClick(Marker marker) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                mapIntent.setPackage("com.google.android.apps.maps");

                startActivity(mapIntent);

            }

        });

        new BindPlacesOnGoogleMapAsyncTask<>(this, mMap).execute();

    }

    public void onSuccess(List<PlaceVO> list) {

        List<SearchSuggestion> result = new ArrayList<>();

        for (PlaceVO placeVO : list)

            result.add(new PlaceSuggestions(placeVO));

        mSearchView.swapSuggestions(result);

    }

    public void onFailure(Messaging messaging) {}

    public HashMap<Integer, Marker> getMarkers() {

        if (markers == null)

            markers = new HashMap<>();

        return markers;

    }

    private static class PlaceSuggestions implements SearchSuggestion {

        private final PlaceVO mPlace;

        public static final Parcelable.Creator<PlaceSuggestions> CREATOR = new Parcelable.Creator<PlaceSuggestions>() {

            public PlaceSuggestions createFromParcel(Parcel in) {

                return new PlaceSuggestions(in);

            }

            public PlaceSuggestions[] newArray(int size) {

                return new PlaceSuggestions[size];

            }

        };

        public PlaceSuggestions(PlaceVO placeVO) {

            mPlace = placeVO;

        }

        public String getBody() {

            return mPlace.getDenomination();

        }

        public int describeContents() {

            return 0;

        }

        public void writeToParcel(Parcel dest, int flags) {

            dest.writeString(mPlace.getDenomination());

        }

        private PlaceSuggestions(Parcel in) {

            mPlace = (PlaceVO) in.readSerializable();

        }

    }

}