package com.kampusverse.Logic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.TreeMap;
import java.util.concurrent.Executor;

public class ApiBase {

   public interface ApiBaseCallback {
        void OnComplete(Object o);
        void OnFail();
    }

    private static ApiBase SingleBody = null;
    private FirebaseAuth FireAuth;
    private FirebaseUser FireUser;

    public static ApiBase GetInstance() {
        if(SingleBody != null) {
            return SingleBody;
        } else {
            SingleBody = new ApiBase();
            return SingleBody;
        }
    }

    private ApiBase() {
        Log.d("Alabama", "Well well look at this shit ..");
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FireAuth = FirebaseAuth.getInstance();
        FireUser = FireAuth.getCurrentUser();
    }

    //Sign Up
    public void BaruEmail(final Context context, String Email, String Password, final ApiBaseCallback callback) {
        // Firebase membuat AKUN baru dari
        Log.d("Firebase", "Creating user with email ..");
        FireAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Alabama", "User created ..");
                            callback.OnComplete(null);
                        } else {
                            Log.d("Alabama", "Fail to create user ..");
                            Log.d("Alabama", "TASK ERR: \n" + task.getException());
                            callback.OnFail();
                        }
                    }
                });
    }

    //Sign In
    public void LoginEmail(final Context context, String Email, String Password, final ApiBaseCallback callback) {
        //Sign in with firebase
        Log.d("Alabama", "Sign In to alabama ..");
        FireAuth.signInWithEmailAndPassword("robertusyudo@gmail.com", "kingkong212")
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Alabama", "Sign In complete ..");
                            FireUser = FireAuth.getCurrentUser();
                            callback.OnComplete(FireUser);
                        } else {
                            Log.d("Alabama", "Sign In fail ..");
                            Log.d("Alabama", "TASK ERR: \n" + task.getException());
                            callback.OnFail();
                        }
                    }
                });
    }

    public boolean IsLogin() {
        Log.d("Alabama", "If need to login say so ..");
        if(FireUser != null) return true;
        Log.d("Alabama", "Need to login ..");
        return false;
    }

    //GetUser
    // TODO: balikan jadi Class Profile
    public FirebaseUser GetUser() {
        //Get user data with firebase
        Log.d("Alabama", "Getting your info ..");
        if(FireUser != null) {
            Log.d("Alabama", "Okay here u go ..");
            return FireUser;
        } else {
            Log.d("Alabama", "No user exist ..");
            return null;
        }
    }

    //SignOut
    public void SignOut() {
        FireAuth.signOut();
        Log.d("Alabama", "Goodbye Boy ..");
        FireUser = null;
    }

}
