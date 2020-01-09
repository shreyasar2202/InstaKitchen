package com.example.kitchen.instakitchen;

import
        android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


import java.util.List;
import java.util.Set;

public class ResetPassword extends AppCompatActivity implements View.OnClickListener{
Button reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String prefix = " a ";
        setContentView(R.layout.activity_reset_password);
        Intent mainIntent = getIntent();
        if (mainIntent != null && mainIntent.getData() != null && (mainIntent.getData().getScheme().equals("https"))) {
            Uri data = mainIntent.getData();
            String email = data.getQueryParameter("email");
            TextView textView = (TextView) findViewById(R.id.uri);
            textView.setText(email);


        }
        EditText editText=(EditText)findViewById(R.id.NewPassword);
        String newPassword=editText.getText().toString().trim();
        reset=(Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==reset)
        {
//            UserRecord.UpdateRequest request = new UserRecord.UpdateRequest(uid)
//                    .setEmail("user@example.com")
//                    .setPhoneNumber("+11234567890")
//                    .setEmailVerified(true)
//                    .setPassword("newPassword")
//                    .setDisplayName("Jane Doe")
//                    .setPhotoUrl("http://www.example.com/12345678/photo.png")
//                    .setDisabled(true);
//
//            Task<UserRecord> task = FirebaseAuth.getInstance().updateUser(request)
//                    .addOnSuccessListener(userRecord -> {
//                        // See the UserRecord reference doc for the contents of userRecord.
//                        System.out.println("Successfully updated user: " + userRecord.getUid());
//                    })
//                    .addOnFailureListener(e -> {
//                        System.err.println("Error updating user: " + e.getMessage());
//                    });
        }
    }
}