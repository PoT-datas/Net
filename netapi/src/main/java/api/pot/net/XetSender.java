package api.pot.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SuppressLint("NewApi")
public class XetSender {

    /**
     * *******************IMG UPLOAD***************************************/
    /*public static void imageUpload(final Context context, String key, final String imagePath) {
        final String key_ = key.trim();
        String url = "https://ibstest.btaorg.com/api/v1/users/user/profile";
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String message = jObj.getString("message");
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", key_);
                return params;
            }
        };
        smr.addFile("file", imagePath);
        ///
        Volley.newRequestQueue(context).add(smr);
    }*/
    /**
     * *******************IMG UPLOAD***************************************/

    public static byte[] getFileData(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        byte[] tmpBuff = new byte[size];

        try (FileInputStream inputStream = new FileInputStream(file)) {
            int read = inputStream.read(bytes, 0, size);
            if (read < size) {
                int remain = size - read;
                while (remain > 0) {
                    read = inputStream.read(tmpBuff, 0, remain);
                    System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                    remain -= read;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

}
