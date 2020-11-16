package com.slnikah.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.slnikah.R;

import java.util.HashMap;


public class Register extends Fragment {
    AppCompatButton register;
    AppCompatEditText name,email,mobile,pw,repw;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        register= v.findViewById(R.id.register);
        name= v.findViewById(R.id.full_name);
        email= v.findViewById(R.id.email);
        mobile= v.findViewById(R.id.mobile);
        pw= v.findViewById(R.id.password);
        repw= v.findViewById(R.id.re_password);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name = name.getText().toString();
                String str_email = email.getText().toString();
                String str_mobile = mobile.getText().toString();
                String str_pw = pw.getText().toString();
                String str_confirmpw = repw.getText().toString();

                if (TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_mobile) ||TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_pw) || TextUtils.isEmpty(str_confirmpw)){
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                }else if(str_pw.length()<6){
                    Toast.makeText(getActivity(), "Password must have at least 6 characters", Toast.LENGTH_SHORT).show();
                }else if(!(str_pw.equals(str_confirmpw))){
                    Toast.makeText(getActivity(), "Confirm password didn't match", Toast.LENGTH_SHORT).show();
                }else{
                    pd = new ProgressDialog(getActivity());
                    pd.setMessage("Please Wait....");
                    pd.show();
                    pd.setCanceledOnTouchOutside(false);

                    register(str_name, str_mobile, str_email, str_pw);
                }


            }
        });
        return v;
    }

    private void register(String str_name, String str_mobile, String str_email, String str_pw) {
        auth.createUserWithEmailAndPassword(str_email, str_pw).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = null;
                    if (firebaseUser != null) {
                        userid = firebaseUser.getUid();
                    }

                    if (userid != null) {
                        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
                    }

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("name", str_name);
                    hashMap.put("mobile", str_mobile);
                    hashMap.put("access", "customer");
                    hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/sl-nikah-2810f.appspot.com/o/Users%2Ficons8-account-480.png?alt=media&token=ec6feaa1-2733-4c83-9067-e1cd902ef04a");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                String body="<!DOCTYPE html><html><body>Your email is registered for Sl Nikah account. Please use the the login details below. "  +
                                        "                                        <br><br>username= " +str_email+""+
                                        "                                        <br>password= " +str_pw+"" +
                                        "                                        <br><br>If you didn't request this. Please ignore this email."  +
                                        "                                        <br><br>Thank You." +
                                        "                                        <br>Sl Nikah Team.</body></html>";
                                String to=str_email;
                                String subject="Sl Nikah account registration";
                                SpannableString message= SpannableString.valueOf("Your email is registered for Sl Nikah account. Please use the the login details below. " +
                                        "<br>username= " +str_email+
                                        "<br>password= " +str_pw+
                                        "<br><br>If you didn't request this. Please ignore this email." +
                                        "<br>Thank You." +
                                        "<br>Sl Nikah Team.");
                                String htmlEncodedString = Html.toHtml(message);

                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("text/html");
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                                email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));

                                //need this to prompts email client only
//                                email.setType("message/rfc822");

//                                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                                startActivity(Intent.createChooser(email, "Email:"));
                            }
                        }
                    });


                } else {
                    pd.dismiss();
                    Toast.makeText(getActivity(), "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}