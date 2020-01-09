package com.example.kitchen.instakitchen;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity  implements View.OnClickListener{
    EditText kitchen_id;
    private Button submit;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        kitchen_id = (EditText) findViewById(R.id.kitchen_id);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }
    void getResetEmail()
    {

        database = FirebaseDatabase.getInstance();

        String kid = kitchen_id.getText().toString().trim();
        Log.e("kitchenid",kid+" abc");

        myRef=FirebaseDatabase.getInstance().getReference("Backups");
        myRef=myRef.child(QueryUtils.emailEncode(kid)).child("EmailID");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                email = dataSnapshot.getValue(String.class);
                sendEmail();
                Log.e("Sad",email);
            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void sendEmail() {
        auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("abc", "Email sent.");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==submit)
        {getResetEmail();
    }
}
}