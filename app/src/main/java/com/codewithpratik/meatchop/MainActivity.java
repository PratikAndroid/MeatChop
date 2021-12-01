package com.codewithpratik.meatchop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    Button lets_start;
    Button verify;
    Button start;
    TextInputLayout mobileBox;
    TextInputLayout pinBox;
    TextInputLayout emailBox;
    TextInputLayout otpBox;
    TextInputEditText edtOTP;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String verificationId;
    TextView forgotPassword;

    public static final String MOBILE_NUMBER = "user_mobile_number_123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // below line is for getting instance  of our FirebaseAuth.

        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        lets_start=findViewById(R.id.lets_start);
        pinBox=findViewById(R.id.pin_text_input);
        emailBox=findViewById(R.id.email_text_input);
        mobileBox=findViewById(R.id.mobile_text_input);
        otpBox=findViewById(R.id.otp_text_input);
        edtOTP = findViewById(R.id.otp_edit_text);
        start=findViewById(R.id.pin_verify);
        verify=findViewById(R.id.verify);
        forgotPassword=findViewById(R.id.tv_forgot_pin);




            FirebaseApp.initializeApp(this);



    }

    public void forgotPin(View view)
    {
        Intent intent = new Intent(MainActivity.this,OTP.class);
        startActivity(intent);

    }

    public void letStart(View view)
    {
        reference = rootNode.getReference("User");
        String mb_number = mobileBox.getEditText().getText().toString();
           // Read difference between child and orderByChild
        reference.orderByChild("mobileNumber").equalTo(mb_number)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (TextUtils.isEmpty(mobileBox.getEditText().getText().toString())) {
                            // when mobile number text field is empty
                            // displaying a toast message.
                            Toast.makeText(MainActivity.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                        } else {
                            // if the text field is not empty we are calling our
                            // send OTP method for getting OTP from Firebase.
                               if(snapshot.exists())
                               {
                                   pinBox.setVisibility(View.VISIBLE);
                                    forgotPassword.setVisibility(View.VISIBLE);
                               //  verify.setVisibility(View.VISIBLE);
                                   start.setVisibility(View.VISIBLE);
                                   lets_start.setVisibility(View.GONE);

                               }
                               else
                               {
                                   Toast.makeText(MainActivity.this, "New User Please fill all details", Toast.LENGTH_SHORT).show();
                                   emailBox.setVisibility(View.VISIBLE);
                                   pinBox.setVisibility(View.VISIBLE);
                                   otpBox.setVisibility(View.VISIBLE);
                                   verify.setVisibility(View.VISIBLE);
                                   lets_start.setVisibility(View.GONE);
                                   String phone = "+91" + mobileBox.getEditText().getText().toString();
                                   sendVerificationCode(phone);

                               }
                        } }
                        @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void verifyPin(View view)
      {
          reference = rootNode.getReference(Constant.userKey);
          String entered_pin = pinBox.getEditText().getText().toString();

          reference.orderByChild("pin").equalTo(entered_pin).addValueEventListener(new ValueEventListener() {

              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if(snapshot.exists())
                  {     String mobile = mobileBox.getEditText().getText().toString();

                      SharedPreferences sharedPreferences = getSharedPreferences("com.codewithpratik.meatchop",MODE_PRIVATE);
                      SharedPreferences.Editor editor = sharedPreferences.edit();
                      editor.putString("mobile_number",mobile);
                      editor.commit();
                      Intent intent = new Intent(MainActivity.this,Home.class);
                      startActivity(intent);
                      finish();
                  }
                  else {
                      Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {
              }
          });

      }

    public void verifyOTP(View view)
    {   // validating if the OTP text field is empty or not.
        if (TextUtils.isEmpty(otpBox.getEditText().getText().toString())) {
            // if the OTP text field is empty display
            // a message to user to enter OTP
            Toast.makeText(MainActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
        } else {
            // if OTP field is not empty calling
            // method to verify the OTP.
            verifyCode(otpBox.getEditText().getText().toString());
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
                            String mobile = mobileBox.getEditText().getText().toString();
                            String email = emailBox.getEditText().getText().toString();
                            String pin = pinBox.getEditText().getText().toString();

                            SharedPreferences sharedPreferences = getSharedPreferences("com.codewithpratik.meatchop",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("mobile_number",mobile);
                            editor.commit();
                            LogInContent logInContent = new LogInContent(mobile,email,pin);
                            reference.child(mobile).setValue(logInContent);
                            Intent i = new Intent(MainActivity.this, Home.class);
                            startActivity(i);
                            finish();
                        } else {
                            // if the code is not correct then we are
                            // displaying an error message to the user.
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
                edtOTP.setText(code);

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
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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



