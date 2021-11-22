package yh.evanz.quizapplication;

import android.app.Application;

public class myApp extends Application {
    private StorageManager storageManager = new StorageManager();
    private ExternalStorageManager externalStorageManager = new ExternalStorageManager();

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public ExternalStorageManager getExternalStorageManager() {
        return externalStorageManager;
    }
}
