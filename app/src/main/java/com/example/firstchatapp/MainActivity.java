package com.example.firstchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 777;
    private FirebaseAnalytics mFirebaseAnalytics;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("1061489812534-grcgjbfch8v2hpg48tookrgpv2bj3qo0.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Button signIn = findViewById(R.id.button);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RC_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //Toast.makeText(this,"Welcome " + account.getDisplayName(),Toast.LENGTH_LONG).show();

            // Signed in successfully, show authenticated UI.
            Intent i = new Intent(MainActivity.this,ChatActivity.class);
            i.putExtra("user",account);
            startActivity(i);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }
}