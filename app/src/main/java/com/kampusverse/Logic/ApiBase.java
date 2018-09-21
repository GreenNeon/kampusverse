package com.kampusverse.Logic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Profile;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Executor;

public class ApiBase {

    public interface SimpleCallback {
        public void OnSuccess(String[] strings);

        public void OnFailure(String message);
    }

    private static ApiBase SingleBody = null;
//    private FirebaseAuth FireAuth;
//    private FirebaseUser FireUser;

    public static ApiBase GetInstance() {
        if (SingleBody != null) {
            return SingleBody;
        } else {
            SingleBody = new ApiBase();
            return SingleBody;
        }
    }

    private ApiBase() {
    }

    public void RefreshToken(final Context context, final String[] data, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        json.addProperty("token", data[4]);

        Ion.with(context)
                .load("https://kampusbanana.herokuapp.com/api/refresh")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            String[] strings = data.clone();
                            strings[3] = result.get("id_token").getAsString();
                            strings[4] = result.get("refresh_token").getAsString();

                            SharedData sdata = SharedData.GetInstance();
                            sdata.ReplaceUser(new Profile(strings[0], strings[1], strings[2], strings[3], strings[4]));

                            String message;
                            if (sdata.GetUser().getNama().trim().equalsIgnoreCase(""))
                                message = "Hai, " + sdata.GetUser().getEmail();
                            else
                                message = "Hai, " + sdata.GetUser().getNama();

                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            callback.OnSuccess(strings);
                        } else {
                            String message = result.get("error").getAsJsonObject().get("message").getAsString();
                            Log.e("APIBASE", message);
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    public void Login(final Context context, String email, String password, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);

        Ion.with(context)
                .load("https://kampusbanana.herokuapp.com/api/newtoken")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            String[] strings = {"", "", "", "", "", "", ""};
                            strings[0] = result.get("localId").getAsString();
                            strings[1] = result.get("displayName").getAsString();
                            strings[2] = result.get("email").getAsString();
                            strings[3] = result.get("idToken").getAsString();
                            strings[4] = result.get("refreshToken").getAsString();
                            strings[5] = result.get("registered").getAsString();

                            RefreshToken(context, strings, new SimpleCallback() {
                                @Override
                                public void OnSuccess(String[] strings) {
                                    callback.OnSuccess(null);
                                }

                                @Override
                                public void OnFailure(String message) {
                                    callback.OnFailure(message);
                                }
                            });
                        } else {
                            String message = result.get("error").getAsJsonObject().get("message").getAsString();
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    private void SendEmail(final Context context, String[] data, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        json.addProperty("token", data[2]);

        Ion.with(context)
                .load("https://kampusbanana.herokuapp.com/api/sendverification")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            callback.OnSuccess(null);
                        } else {
                            String message = result.get("error").getAsJsonObject().get("message").getAsString();
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    public void Register(final Context context, String email, String password, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);

        Ion.with(context)
                .load("https://kampusbanana.herokuapp.com/api/signup")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            String[] strings = {"", "", "", "", "", "", ""};
                            strings[0] = result.get("localId").getAsString();
                            strings[1] = result.get("email").getAsString();
                            strings[2] = result.get("idToken").getAsString();
                            strings[3] = result.get("refreshToken").getAsString();

                            SendEmail(context, strings, new SimpleCallback() {
                                @Override
                                public void OnSuccess(String[] strings) {
                                    Toast.makeText(context, "Email send!", Toast.LENGTH_SHORT).show();
                                    callback.OnSuccess(null);
                                }

                                @Override
                                public void OnFailure(String message) {
                                    callback.OnFailure(message);
                                }
                            });
                        } else {
                            String message = result.get("error").getAsJsonObject().get("message").getAsString();
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    public void GetUserData(final Context context, String token,final SimpleCallback callback){
        JsonObject json = new JsonObject();
        json.addProperty("email", token);

        Ion.with(context)
                .load("https://kampusbanana.herokuapp.com/api/newtoken")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            String[] strings = {"", "", "", "", "", "", ""};
                            strings[0] = result.get("localId").getAsString();
                            strings[1] = result.get("displayName").getAsString();
                            strings[2] = result.get("email").getAsString();
                            strings[3] = result.get("idToken").getAsString();
                            strings[4] = result.get("refreshToken").getAsString();
                            strings[5] = result.get("registered").getAsString();

                            RefreshToken(context, strings, new SimpleCallback() {
                                @Override
                                public void OnSuccess(String[] strings) {
                                    callback.OnSuccess(null);
                                }

                                @Override
                                public void OnFailure(String message) {
                                    callback.OnFailure(message);
                                }
                            });
                        } else {
                            String message = result.get("error").getAsJsonObject().get("message").getAsString();
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    public void GetJadwal(final Context context, String uid, String idToken, final SimpleCallback callback) {
        /*curl GET "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/
        String url = "https://kampusbanana.firebaseio.com/users/" + uid +"/Jadwal.json?auth=" + idToken;

        Ion.with(context)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            List<Jadwal> list = new ArrayList<>();
                            JsonArray arrayMain = result.getAsJsonArray();
                            for (JsonElement element : arrayMain) {
                                JsonObject obj = element.getAsJsonObject();
                                list.add(new Jadwal(
                                        obj.get("nama").getAsString(),
                                        obj.get("desc").getAsString(),
                                        obj.get("uid").getAsString(),
                                        obj.get("reminder").getAsLong()
                                ));
                            }
                        } else {

                        }
                    }
                });
    }

    public void PutToFirebase(final Context context, String uid, String snippet, String idToken, final SimpleCallback callback) {
        /*Buat file dengan nama yang ditentukan sendiri
        curl PUT "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/
        /*DATA "{alanisawesome": {"name": "Alan Turing","birthday": "June 23, 1912"}}*/
        JsonObject json = new JsonObject();
        String url = "https://kampusbanana.firebaseio.com/users/" + uid + snippet + "?auth=" + idToken;

        Ion.with(context)
                .load("PUT", url)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            callback.OnSuccess(null);
                        } else {
                            callback.OnFailure(null);
                        }
                    }
                });
    }

    public void PatchToFirebase(final Context context, String uid, String idToken) {
        /*Update data otomatsu
        curl PATCH  "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/
        /*DATA {"nickname": "Alan The Machine"}*/
        JsonObject json = new JsonObject();
        Ion.with(context)
                .load("PATCH", "https://kampusbanana.firebaseio.com/users/" + uid + ".json?auth=" + idToken)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {

                        } else {

                        }
                    }
                });
    }

    public void DeleteAtFirebase(final Context context, String uid, String idToken, String targetuid) {
        /*Delete data otomatsu
        curl DELETE  "https://kampusbanana.firebaseio.com/users/:uid/:target.json?auth=<ID_TOKEN>"*/
        JsonObject json = new JsonObject();
        Ion.with(context)
                .load("DELETE", "https://kampusbanana.firebaseio.com/users/" + uid + "/" + targetuid + ".json?auth=" + idToken)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {

                        } else {

                        }
                    }
                });
    }

    /*Buat file dengan nama yang otomatsu
    curl POST  "https://kampusbanana.firebaseio.com/users.json?auth=<ID_TOKEN>"*/
    /*DATA {"author": "alanisawesome","title": "The Turing Machine"}*/
}