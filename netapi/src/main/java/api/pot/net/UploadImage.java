package api.pot.net;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UploadImage extends AsyncTask<Void, Void, Void> {
    Bitmap image;
    String name;
    String server;
    Activity context;

    public UploadImage(Bitmap image, String name, String server){
        this.image = image;
        this.name = name;
        this.server = server;
    }

    @Override
    protected Void doInBackground(Void... params) {
        ArrayList<NameValuePair> dataToSend = new ArrayList<NameValuePair>();//1-
        if(image!=null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            //1+
            dataToSend.add(new BasicNameValuePair("image", encodedImage));
            dataToSend.add(new BasicNameValuePair("name", name));
            dataToSend.add(new BasicNameValuePair("directory", server));
        }

        HttpParams httpRequestParams = getHttpRequestParams();

        HttpClient client = new DefaultHttpClient(httpRequestParams);
        HttpPost post = new HttpPost(server);

        try {
            post.setEntity(new UrlEncodedFormEntity(dataToSend));
            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
    }

    private HttpParams getHttpRequestParams(){
        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000*10);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000*10);
        return httpRequestParams;
    }
}