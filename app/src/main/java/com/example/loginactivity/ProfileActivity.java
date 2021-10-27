package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLoginHandler;
import io.kommunicate.callbacks.KmCallback;
import io.kommunicate.users.KMUser;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Kommunicate.init(this, "f52574e79812dd83de49431ac027ccb0");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String email = user.getEmail();

        KMUser kmuser = new KMUser();

        kmuser.setUserId(email);

        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setText("Welcome : " + email);

        Button logoutButton = findViewById(R.id.buttonLogout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    public void btnpOnClick(View view) {

        Kommunicate.init(this, "f52574e79812dd83de49431ac027ccb0");

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        String email = user.getEmail();

        KMUser kmuser = new KMUser();

        kmuser.setUserId(email);

        Kommunicate.login(this, kmuser, new KMLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {

                new KmConversationBuilder(ProfileActivity.this).setKmUser(kmuser)
                        .launchAndCreateIfEmpty(new KmCallback() {
                            @Override
                            public void onSuccess(Object message) {
                                Log.d("Conversation", "Success : " + message);
                            }

                            @Override
                            public void onFailure(Object error) {
                                Log.d("Conversation", "Failure : " + error);
                            }
                        });
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                // You can perform actions such as repeating the login call or throw an error
                // message on failure
            }
        });
    }
}