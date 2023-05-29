package com.example.firstchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatActivity extends AppCompatActivity {

    GoogleSignInAccount account;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            account = (GoogleSignInAccount) extras.get("user");
        }

        TextView welcome = findViewById(R.id.welcomeTV);
        welcome.setText("Welcome, " + account.getDisplayName());

        ImageView userImage = findViewById(R.id.mainUserImage);
        Glide.with(this).load(account.getPhotoUrl()).into(userImage);

        adapter = new MessageAdapter();
        RecyclerView recycler = findViewById(R.id.chatRV);
        recycler.setHasFixedSize(false);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(), 1);
        recycler.setLayoutManager(manager);

        recycler.setAdapter(adapter);

        FloatingActionButton btn = findViewById(R.id.addMessageBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = findViewById(R.id.messageET);
                chatMessage m = new chatMessage(account.getPhotoUrl().toString(), account.getDisplayName(), account.getId(), text.getText().toString());
                adapter.addMessage(m);
                text.setText("");
            }
        });

    }
}