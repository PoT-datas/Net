package api.pot.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MultipartRequest extends Request<String> {

    ///private MultipartEntity entity = new MultipartEntity();
    private static final String FILE_PART_NAME = "file";
    private static final String STRING_PART_NAME = "text";
    private static final String SELFIE_IMAGE = "selfieImage";
    private static final String SELFIE_CAPTION = "cap";

    private final Response.Listener<String> mListener;
    private final File mFilePart;
    private final String mStringPart;

    public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, File file, String stringPart)
    {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        mStringPart = stringPart;
        buildMultipartEntity();
    }

    private void buildMultipartEntity()
    {
        /*System.out.println("buildMultipartEntity");

        entity.addPart(SELFIE_IMAGE, new FileBody(mFilePart));
        try
        {
            entity.addPart(SELFIE_CAPTION, new StringBody(mStringPart));
        }
        catch (UnsupportedEncodingException e)
        {
            VolleyLog.e("UnsupportedEncodingException");
        }*/
    }

    /*@Override
    public String getBodyContentType()

    {  System.out.println("getBodyContentType");
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        System.out.println("getBody");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            entity.writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response)
    {
        mListener.onResponse(response);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();
        System.out.println("getHeaders");

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        AppController.getInstance().addSessionCookie(headers);

        return headers;

    }*/



    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(String response) {

    }
}