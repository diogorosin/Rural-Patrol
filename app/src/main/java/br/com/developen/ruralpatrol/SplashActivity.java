package br.com.developen.ruralpatrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.developen.ruralpatrol.task.DownloadPlacesAsyncTask;
import br.com.developen.ruralpatrol.util.Messaging;

public class SplashActivity extends AppCompatActivity implements DownloadPlacesAsyncTask.DownloadFarmsListener {

    public static final String MESSAGE = "Message";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new DownloadPlacesAsyncTask<>(this).execute();

    }

    public void onSuccess() {

        Bundle bundle = new Bundle();

        bundle.putString(MESSAGE, "A base de dados foi atualizada.");

        Intent intent = new Intent(SplashActivity.this, MapsActivity.class);

        intent.putExtras(bundle);

        startActivity(intent);

        finish();

    }

    public void onFailure(Messaging messaging) {

        Bundle bundle = new Bundle();

        bundle.putString(MESSAGE, "Não foi possível atualizar a base de dados.");

        Intent intent = new Intent(SplashActivity.this, MapsActivity.class);

        intent.putExtras(bundle);

        startActivity(intent);

        finish();

    }

}