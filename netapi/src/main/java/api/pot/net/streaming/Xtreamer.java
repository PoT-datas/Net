package api.pot.net.streaming;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Xtreamer {

    private static XtreamerListener listener_;
    private static Xtreamer xtreamer;
    private static boolean isDownloading = false;

    private static Map<String, Boolean> useCahes = new HashMap<String, Boolean>();
    private static Map<String, Float> downloadings = new HashMap<String, Float>();
    private static Map<String, File> files = new HashMap<String, File>();
    private static Map<String, List<View>> views = new HashMap<String, List<View>>();
    public static int index = 0;
    private static DownloadFile downloadFile;
    //

    private static List<View> views_ = new ArrayList<View>();
    private static String url_ = null;
    private static boolean useCache_ = false;
    private static File file_ = null;
    private static View view_ = null;

    private static Context context_ = null;

    private static String downloader_cache_dir_path = Environment.getExternalStorageDirectory() + "/PoT/api/xil/cache/";
    private static String downloader_temp_dir_path = Environment.getExternalStorageDirectory() + "/PoT/api/xil/temp/";

    public Xtreamer(Context context) {
        context_ = context;
    }

    public static Xtreamer with(Context context) {
        if(xtreamer!=null) return xtreamer;
        if(context_==null && context!=null)
            xtreamer = new Xtreamer(context);
        return xtreamer;
    }

    public Xtreamer load(String url) {
        url_ = url;
        return xtreamer;
    }

    public static Xtreamer useCache(Boolean useCache) {
        useCache_ = useCache;
        return xtreamer;
    }

    public Xtreamer into(File file) {
        file_ = file;
        return xtreamer;
    }

    public Xtreamer listener(XtreamerListener listener){
        listener_ = listener;
        return xtreamer;
    }

    public static Xtreamer options(){
        return xtreamer;
    }

    public static void add(){
        ///---init();

        exe();
    }

    private static void exe(){
        if(!isDownloading){
            new DownloadFile(context_, url_, file_, listener_).execute();
        }
    }





    private static class DownloadFile extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;
        private boolean isDownloaded;
        private String file_name;
        private File temp_file, cache_file;
        private static String downloader_cache_dir_path = Environment.getExternalStorageDirectory() + "/PoT/api/xil/cache/";
        private static String downloader_temp_dir_path = Environment.getExternalStorageDirectory() + "/PoT/api/xil/temp/";

        private List<View> _views_ = new ArrayList<View>();
        private boolean _useCache_;
        private String str_url;
        private boolean canProceed;

        private int bestImgeWidth = 0;
        private int bestImgeHeight = 0;


        private Context context_;
        private String url_;
        private File file_;
        private XtreamerListener listener_;

        public DownloadFile(Context context, String url, File file, XtreamerListener listener) {
            isDownloading = true;
            ///
            this.context_ = context;
            this.url_ = url;
            this.file_ = file;
            this.listener_ = listener;
        }

        @Override
        protected void onPreExecute() {
            if(listener_!=null) listener_.onLoadingReady();
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            //checkDir(downloader_temp_dir_path);
            try {

                //just a try
                //if(modelImage.exists()) modelImage.setDownloading(0);

                URL url = new URL(url_);
                URLConnection connection = url.openConnection();
                connection.connect();

                // getting file length
                int lengthOfFile = connection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                //output files
                temp_file = file_;

                //check dir
                checkDir(temp_file.getParent());

                //check files
                //if(temp_file.exists()) temp_file.delete();

                OutputStream temp_output;
                if(temp_file.exists())
                    temp_output = new FileOutputStream(temp_file, true);
                else
                    temp_output = new FileOutputStream(temp_file);

                byte data[] = new byte[1024];

                long total = 0;

                //passer sur les elts deja telecharger
                long skip = temp_file.exists()?temp_file.length():0;
                input.skip(skip);

                //init progression
                downloadings.put(str_url, 0f);

                final long time = System.currentTimeMillis();

                if(listener_!=null) listener_.onLoadingStart();

                while ((count = input.read(data)) != -1) {
                    total += count;

                    temp_output.write(data, 0, count);

                    if(listener_!=null) listener_.onLoadingProceed(Float.valueOf(skip + total) / lengthOfFile);

                    //downloading_notifyer.notifyer(Float.valueOf(skip + total) / lengthOfFile);

                    /***modelImage.setDownloading(skip + total);*/

                    /**if(breaker!=null && breaker.size()!=0){
                        if(breaker.remove(str_url))
                            return "break";
                    }*/
                }

                /**Downloader.log("delta time(s) :"+(Float.valueOf(System.currentTimeMillis()-time)/1000)+"  "+str_url);*/

                /***if(downloadings.get(str_url)!=1){
                    downloadings.put(str_url, 1f);
                    for(DownloadListener downloadListener : _downloadListeners_) {
                        if (downloadListener != null);
                        listenerMng.onDownLoadUpdate(downloadListener, 1f);
                    }

                    ///
                    //listenerMng.onDownLoadUpdate(null, 1f);//contradiction must call downloading_notifyer.notifyer...
                    ///

                }*/

                /***if(_useCache_==true){
                    if(modelImage.local_path.length()==0)
                        modelImage.setLocal_path(temp_file.getAbsolutePath(), cache_file.getAbsolutePath());
                    //redimensionner l'image obtenu selon le conteneur
                    saveRightDimensToCache(temp_file, cache_file, bestImgeWidth, bestImgeHeight, modelImage);
                }*/

                temp_output.flush();

                temp_output.close();
                input.close();

                return "done";

            } catch (Exception e) {}

            return "error";
        }

        @Override
        protected void onPostExecute(String message) {
            if(message.equals("done") && temp_file!=null && temp_file.exists()) {
                /***for(DownloadListener downloadListener : _downloadListeners_)
                    if (downloadListener != null) listenerMng.onDownLoadEnd(downloadListener, temp_file, true);

                ///
                listenerMng.onDownLoadEnd(null, temp_file, true);
                ///

                modelImage.setLastTimeUsed(System.currentTimeMillis());
                if(index<urls.size()) urls.remove(index);*/

                if(listener_!=null) listener_.onLoadingEnd(file_);

            }else if(message.equals("error")){
                /***for(DownloadListener downloadListener : _downloadListeners_)
                    if(downloadListener!=null) listenerMng.onDownLoadError(downloadListener);

                ///
                listenerMng.onDownLoadError(null);
                ///

                ///
                isDownloading = false;
                NetConnectivity.internetChecker(context);*/
                ///

                if(listener_!=null) listener_.onLoadingError(message, file_);

            }else if(message.equals("break")){
                /**for(DownloadListener downloadListener : _downloadListeners_)
                    if(downloadListener!=null) listenerMng.onDownLoadCancel(downloadListener);

                ///
                listenerMng.onDownLoadCancel(null);
                ///

                urls.remove(index);*/

                if(listener_!=null) listener_.onLoadingCancel(file_);
            }

            isDownloading = false;
        }

    }

    public static boolean checkDir(String path) {
        while(path.charAt(path.length()-1)=='/'){
            path = path.substring(0, path.length()-1);
        }
        //
        File mainDir = new File(path);
        if(!mainDir.exists()) mainDir.mkdir();
        if (!mainDir.exists()) {
            int index = -1;
            String c_path = "", allPath = "";
            File c_dir;
            while ( (index=path.indexOf(File.separator))!=-1 ){
                if(index!=path.length()-1) {
                    if(index!=0) {
                        allPath += (allPath.length()==0 || allPath.charAt(allPath.length()-1)=='/' ? "" : "/") + path.substring(0, index);
                        c_dir = new File(allPath);
                        path = path.substring(index + 1);
                        if (!c_dir.exists()) c_dir.mkdir();
                    }else {
                        allPath += "/";
                        path = path.substring(index + 1);
                    }
                }else path = null;
            }
            if(path!=null){
                allPath += "/"+path;
                c_dir = new File(allPath);
                if(!c_dir.exists()) c_dir.mkdir();
            }
            if(mainDir.exists()) return true;
        }else return true;
        return false;
    }

}
