package api.pot.net.streaming;

import java.io.File;

public interface XtreamerListener {

    public void onLoadingReady();
    public void onLoadingStart(int length);
    public void onLoadingProceed(float evolution);
    public void onLoadingEnd(String destination);
    public void onLoadingEnd(File file);
    public void onLoadingError(String msg, String destination);
    public void onLoadingError(String msg, File file);
    public void onLoadingCancel(String destination);
    public void onLoadingCancel(File file);
}
