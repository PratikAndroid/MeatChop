package com.codewithpratik.meatchop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    LinearLayout otpVerificationLayout;
    LinearLayout pinSetLayout;
       ImageView messageImage;
       TextInputEditText edt_otp;
       TextView otpVerification;
       TextInputLayout enterOtp;
       TextInputLayout enterPin;
       TextInputLayout confirmPin;
       TextView resetPassword;
       LinearLayout textLayout;
       TextView didNotReceiveOtp;
       TextView resendOtp;
       MaterialButton verify;
       MaterialButton setPin;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

         findId();
        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        reference =  rootNode.getReference("User");
        SharedPreferences getSharedData = getSharedPreferences("com.codewithpratik.meatchop", MODE_PRIVATE);
        String number = getSharedData.getString("mobile_number", "0000");
        String phone = "+91" + number;
        String str = mAuth.getCurrentUser().getPhoneNumber();
        sendVerificationCode(str);
        String s =str.substring(3);


        setPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("pin1","button licked");
                String newPin1 = enterPin.getEditText().getText().toString();
                String newPin2 = confirmPin.getEditText().getText().toString();
                Log.d("pin1","newPin1 : "+ newPin1);
                Log.d("pin1","newPin2 : "+ newPin2);
                if(newPin1.equals(newPin2))
                {     Log.d("pin1","equal : true");
                  // reference.child(number).child("pin").setValue(newPin1);
                    reference = rootNode.getReference("User/"+s).child("pin");
                    reference.setValue(newPin1);
                    Log.d("pin1","pinchanged: " + newPin1);
                    Intent intent = new Intent(OTP.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(OTP.this, "Pin Mismatch.., Enter pin again ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode(str);
            }
        });

    }
    public void findId()
   {   otpVerificationLayout=findViewById(R.id.layout_otp_verification);
       pinSetLayout=findViewById(R.id.layout_pin_confirmation);
       edt_otp= findViewById(R.id.edtOTP_forgot);
       messageImage = findViewById(R.id.img_message_image);
       otpVerification = findViewById(R.id.tv_otp_verification);
       enterOtp = findViewById(R.id.enter_otp);
       enterPin = findViewById(R.id.enter_pin);
       resetPassword = findViewById(R.id.tv_reset_password);
       confirmPin = findViewById(R.id.confirm_pin);
       textLayout = findViewById(R.id.text_layout);
       didNotReceiveOtp = findViewById(R.id.tv_did_not_receive_otp);
       resendOtp = findViewById(R.id.tv_resend_otp);
       verify =  findViewById(R.id.btn_verify_confirm_otp);
       setPin = findViewById(R.id.btn_set_forgot_pin);

    }
    public void verifyOTP(View view)
    {   // validating if the OTP text field is empty or not.
        if (TextUtils.isEmpty(enterOtp.getEditText().getText().toString())) {
            // if the OTP text field is empty display
            // a message to user to enter OTP
            Toast.makeText(OTP.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
        } else {
            // if OTP field is not empty calling
            // method to verify the OTP.
            verifyCode(enterOtp.getEditText().getText().toString());
        }
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the code is correct and the task is successful
                            // we are sending our user to new activity.
                             otpVerificationLayout.setVisibility(View.GONE);
                             pinSetLayout.setVisibility(View.VISIBLE);

                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {
        // this method is used for getting
        // OTP on user phone number.
      /*  PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number, // first parameter is user's mobile number
                60, // second parameter is time limit for OTP
                // verification which is 60 seconds in our case.
                TimeUnit.SECONDS, // third parameter is for initializing units
                // for time period which is in seconds in our case.
                TaskExecutors.MAIN_THREAD, // this task will be excuted on Main thread.
                mCallBack // we are calling callback method when we recieve OTP for
                // auto verification of user.


        );*/
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks

            // initializing our callbacks for on
            // verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // below method is used when
        // OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // when we receive the OTP it
            // contains a unique id which
            // we are storing in our string
            // which we have already created.
            verificationId = s;
        }

        // this method is called when user
        // receive OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // below line is used for getting OTP code
            // which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();

            // checking if the code
            // is null or not.
            if (code != null) {
                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                edt_otp.setText(code);

                // after setting this code
                // to OTP edittext field we
                // are calling our verifycode method.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // displaying error message with firebase exception.
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }

}