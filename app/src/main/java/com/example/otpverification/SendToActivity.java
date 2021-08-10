package com.example.otpverification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SendToActivity extends AppCompatActivity {

    private EditText editText;
    private Button getButton;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to);
        
        
        editText=findViewById(R.id.inputNumber);
        getButton=findViewById(R.id.getButtonId);
        progressBar=findViewById(R.id.sendProgresbar_id);

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                
                if (editText.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendToActivity.this, "Enter mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    progressBar.setVisibility(View.VISIBLE);
                    getButton.setVisibility(View.INVISIBLE);


                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+880"+editText.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            SendToActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                    progressBar.setVisibility(View.GONE);
                                    getButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendToActivity.this, "ALhamdlillah code sent", Toast.LENGTH_SHORT).show();


                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    getButton.setVisibility(View.VISIBLE);
                                    Toast.makeText(SendToActivity.this, "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                                    progressBar.setVisibility(View.GONE);
                                    getButton.setVisibility(View.VISIBLE);

                                    Intent intent=new Intent(getApplicationContext(),VerifyToActivity.class);
                                    intent.putExtra("mobile",editText.getText().toString());
                                    intent.putExtra("verificationId",verificationId);
                                    startActivity(intent);

                                }
                            }
                    );
                }
                
            }
        });
        
    }
}