package com.kampusverse.Logic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kampusverse.Data.Jadwal;
import com.kampusverse.Data.Profile;
import com.kampusverse.Data.Tugas;
import com.kampusverse.Data.Uang;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;

public class ApiBase {

    public interface SimpleCallback {
        public void OnSuccess(String[] strings);

        public void OnFailure(String message);
    }

    public interface ExtendedCallback {
        // harusnya buat interface nya untuk data jadwal tugas uang zzzzz.....
        // kan enak kalo List<Data> doang zzzzz.....
        // udah bodo amat .....
        public void OnSuccess(List<Jadwal> listJadwal, List<Tugas> listTugas, List<Uang> listUang);

        public void OnFailure(String message);
    }

    private static ApiBase SingleBody = null;
    private final static String URLAPI = "https://kampusbanana.herokuapp.com/api/";
    private final static String URLUSERS = "https://kampusbanana.firebaseio.com/users";
    private SharedData sharedData = SharedData.GetInstance();


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
                .load(URLAPI + "refresh")
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
                .load(URLAPI + "newtoken")
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
                .load(URLAPI + "sendverification")
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
                .load(URLAPI + "signup")
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

    public void UpdateUser(final  Context context, String token, final  SimpleCallback callback){
        JsonObject json = new JsonObject();
        json.addProperty("token", token);
        json.addProperty("displayName", sharedData.GetUser().getNama());
        if(sharedData.GetUser().getFotoURL() != null)
            json.addProperty("photoUrl", sharedData.GetUser().getFotoURL().getHost());

        Ion.with(context)
                .load(URLAPI + "updateuser")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            String[] strings = {"", "", "", "", "", "", ""};
                            strings[0] = result.get("localId").getAsString();
                            if(result.get("displayName") != null)
                                strings[1] = result.get("displayName").getAsString();
                            else {
                                if(sharedData.GetUser().getNama().matches(""))
                                    strings[1] = "";
                                else
                                    strings[1] = sharedData.GetUser().getNama();
                            }
                            strings[2] = result.get("email").getAsString();
                            strings[3] = sharedData.GetUser().getIDToken();
                            strings[4] = sharedData.GetUser().getRefreshToken();

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

    public void GetUserData(final Context context, String token, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        json.addProperty("token", token);

        Ion.with(context)
                .load(URLAPI + "getuser")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result.get("error") == null) {
                            String[] strings = {"", "", "", "", "", "", ""};
                            JsonObject obj = result.getAsJsonArray("users").get(0).getAsJsonObject();
                            strings[0] = obj.get("localId").getAsString();

                            if(obj.get("displayName") != null)
                                strings[1] = obj.get("displayName").getAsString();
                            else {
                                if(sharedData.GetUser().getNama().matches(""))
                                    strings[1] = "";
                                else
                                    strings[1] = sharedData.GetUser().getNama();
                            }

                            strings[2] = obj.get("email").getAsString();
                            strings[3] = sharedData.GetUser().getIDToken();
                            strings[4] = sharedData.GetUser().getRefreshToken();
                            strings[5] = obj.get("emailVerified").getAsString();

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

    public void GetAll(final Context context, final SimpleCallback callback) {
        GetJadwal(context, new ExtendedCallback() {
            @Override
            public void OnSuccess(List<Jadwal> listJadwal, List<Tugas> listTugas, List<Uang> listUang) {
                List<Jadwal> current = sharedData.GetKoleksiJadwal();

                if (current.size() < listJadwal.size()) {
                    sharedData.AddArrayJadwal(listJadwal);
                }

                GetTugas(context, new ExtendedCallback() {
                    @Override
                    public void OnSuccess(List<Jadwal> listJadwal, List<Tugas> listTugas, List<Uang> listUang) {
                        List<Tugas> current = sharedData.GetKoleksiTugas();

                        if (current.size() < listTugas.size()) {
                            sharedData.AddArrayTugas(listTugas);
                        }

                        GetUang(context, new ExtendedCallback() {
                            @Override
                            public void OnSuccess(List<Jadwal> listJadwal, List<Tugas> listTugas, List<Uang> listUang) {
                                List<Uang> current = sharedData.GetKoleksiUang();

                                if (current.size() < listUang.size()) {
                                    sharedData.AddArrayUang(listUang);
                                }
                                callback.OnSuccess(null);
                            }

                            @Override
                            public void OnFailure(String message) {callback.OnFailure(message);}
                        });
                    }

                    @Override
                    public void OnFailure(String message) {callback.OnFailure(message);}
                });
            }

            @Override
            public void OnFailure(String message) {callback.OnFailure(message);}
        });

    }

    private void GetJadwal(final Context context, final ExtendedCallback callback) {
        /*curl GET "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/

        Ion.with(context)
                .load(URLUSERS + "/" + sharedData.GetUser().getUID() + "/Jadwal.json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            List<Jadwal> listJadwal = new ArrayList<>();
                            JsonObject arrayMain = result.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entries = arrayMain.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                String keys = entry.getKey();
                                JsonObject json = arrayMain.get(keys).getAsJsonObject();
                                listJadwal.add(new Jadwal(
                                        json.get("nama").getAsString(),
                                        json.get("desc").getAsString(),
                                        json.get("uid").getAsString(),
                                        json.get("reminder").getAsLong()
                                ));
                            }
                            callback.OnSuccess(listJadwal,null,null);
                        } else {
                            String message = "non existance";
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    private void GetTugas(final Context context, final ExtendedCallback callback) {
        /*curl GET "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/

        Ion.with(context)
                .load(URLUSERS + "/" + sharedData.GetUser().getUID() + "/Tugas.json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            List<Tugas> listTugas = new ArrayList<>();
                            JsonObject arrayMain = result.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entries = arrayMain.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                String keys = entry.getKey();
                                JsonObject json = arrayMain.get(keys).getAsJsonObject();
                                listTugas.add(new Tugas(
                                        json.get("nama").getAsString(),
                                        json.get("desc").getAsString(),
                                        json.get("UID").getAsString(),
                                        json.get("JUID").getAsString(),
                                        json.get("reminder").getAsLong()
                                ));
                                callback.OnSuccess(null, listTugas, null);
                            }
                        } else {
                            String message = "non existance";
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    private void GetUang(final Context context, final ExtendedCallback callback) {
        /*curl GET "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/

        Ion.with(context)
                .load(URLUSERS + "/" + sharedData.GetUser().getUID() + "/Uang.json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            List<Uang> listUang = new ArrayList<>();
                            JsonObject arrayMain = result.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entries = arrayMain.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                String keys = entry.getKey();
                                JsonObject json = arrayMain.get(keys).getAsJsonObject();
                                listUang.add(new Uang(
                                        json.get("nama").getAsString(),
                                        json.get("uid").getAsString(),
                                        json.get("perubahan").getAsDouble()
                                ));
                            }
                            callback.OnSuccess(null,null,listUang);
                        } else {
                            String message = "non existance";
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    public void GetLog(final Context context, final ExtendedCallback callback) {
        /*curl GET "https://kampusbanana.firebaseio.com/users/:uid.json?auth=<ID_TOKEN>"*/

        Ion.with(context)
                .load(URLUSERS + "/" + sharedData.GetUser().getUID() + "/LOGUANG.json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            List<Uang> listUang = new ArrayList<>();
                            JsonObject arrayMain = result.getAsJsonObject();
                            Set<Map.Entry<String, JsonElement>> entries = arrayMain.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                String keys = entry.getKey();
                                JsonObject json = arrayMain.get(keys).getAsJsonObject();
                                listUang.add(new Uang(
                                        json.get("nama").getAsString(),
                                        json.get("perubahan").getAsDouble()
                                ));
                            }
                            callback.OnSuccess(null,null,listUang);
                        } else {
                            String message = "non existance";
                            callback.OnFailure(message);
                        }
                    }
                });
    }

    public void SaveJadwal(final Context context, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        if (sharedData.GetSizeOf(SharedData.KOLEKSI_JADWAL) < 1)
            callback.OnFailure("Nothing to Sync");
        for (Jadwal j : sharedData.GetKoleksiJadwal()) {
            json.add(j.getUID(), j.toJSON());
        }

        Ion.with(context)
                .load("PUT", URLUSERS + "/" + sharedData.GetUser().getUID() + "/Jadwal.json?auth=" + sharedData.GetUser().getIDToken())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        callback.OnSuccess(null);
                    }
                });
    }

    public void SaveTugas(final Context context, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        if (sharedData.GetSizeOf(SharedData.KOLEKSI_TUGAS) < 1)
            callback.OnFailure("Nothing to Sync");
        for (Tugas j : sharedData.GetKoleksiTugas()) {
            json.add(j.getUID(), j.toJSON());
        }

        Ion.with(context)
                .load("PUT", URLUSERS + "/" + sharedData.GetUser().getUID() + "/Tugas.json?auth=" + sharedData.GetUser().getIDToken())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        callback.OnSuccess(null);
                    }
                });
    }

    public void SaveUang(final Context context, final SimpleCallback callback) {
        JsonObject json = new JsonObject();
        if (sharedData.GetSizeOf(SharedData.KOLEKSI_UANG) < 1)
            callback.OnFailure("Nothing to Sync");
        for (Uang j : sharedData.GetKoleksiUang()) {
            json.add(j.getUID(), j.toJSON());
        }

        Ion.with(context)
                .load("PUT", URLUSERS + "/" + sharedData.GetUser().getUID() + "/Uang.json?auth=" + sharedData.GetUser().getIDToken())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        callback.OnSuccess(null);
                    }
                });
    }

    public void SaveLog(final Context context, Uang uang, final SimpleCallback callback){
        JsonObject json = new JsonObject();
        json.addProperty("nama", uang.getNama());
        json.addProperty("perubahan", uang.getPerubahan());

        Ion.with(context)
                .load("POST", URLUSERS + "/" + sharedData.GetUser().getUID() +"/LOGUANG.json?auth=" + sharedData.GetUser().getIDToken())
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        callback.OnSuccess(null);
                    }
                });
    }

    public void DeleteJadwal(final Context context, String uid, final SimpleCallback callback) {
        /*Delete data otomatsu
        curl DELETE  "https://kampusbanana.firebaseio.com/users/:uid/:target.json?auth=<ID_TOKEN>"*/
        Ion.with(context)
                .load("DELETE", URLUSERS + "/" + sharedData.GetUser().getUID() + "/Jadwal/" + uid + ".json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            callback.OnSuccess(null);
                        } else {
                            callback.OnFailure("wiuiui");
                        }
                    }
                });
    }

    public void DeleteTugas(final Context context, String uid, final SimpleCallback callback) {
        /*Delete data otomatsu
        curl DELETE  "https://kampusbanana.firebaseio.com/users/:uid/:target.json?auth=<ID_TOKEN>"*/
        Ion.with(context)
                .load("DELETE", URLUSERS + "/" + sharedData.GetUser().getUID() + "/Tugas/" + uid + ".json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            callback.OnSuccess(null);
                        } else {
                            callback.OnFailure("wiuiui");
                        }
                    }
                });
    }

    public void DeleteUang(final Context context, String uid, final SimpleCallback callback) {
        /*Delete data otomatsu
        curl DELETE  "https://kampusbanana.firebaseio.com/users/:uid/:target.json?auth=<ID_TOKEN>"*/
        Ion.with(context)
                .load("DELETE", URLUSERS + "/" + sharedData.GetUser().getUID() + "/Uang/" + uid + ".json?auth=" + sharedData.GetUser().getIDToken())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            callback.OnSuccess(null);
                        } else {
                            callback.OnFailure("wiuiui");
                        }
                    }
                });
    }

    /*Buat file dengan nama yang otomatsu
    curl POST  "https://kampusbanana.firebaseio.com/users.json?auth=<ID_TOKEN>"*/
    /*DATA {"author": "alanisawesome","title": "The Turing Machine"}*/
}