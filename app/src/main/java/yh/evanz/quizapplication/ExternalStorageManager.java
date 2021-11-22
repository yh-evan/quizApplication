package yh.evanz.quizapplication;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExternalStorageManager {
    String filename = "result.txt";
    public void saveNewResultPrivateExternal(Context context, ArrayList<ResultModel> resultList) {
        OutputStream os = null;
        int attempts = resultList.size(), score = 0;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        try{
            File folder = context.getExternalFilesDir("resultExternalData");
            File myFile = new File(folder, filename);
            OutputStream outStream = new FileOutputStream(myFile);
            for (ResultModel r: resultList
                 ) {
                score =+ r.score;
            }
            outStream.write((formatter + "Your correct answer are" + score + " in " + attempts +" attempts").getBytes());
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }


}
