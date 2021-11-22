package yh.evanz.quizapplication;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class StorageManager {
    String filename = "result.txt";

    public void resetTheStorage(Activity activity) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveNewResultInInternalPrivateFile (Activity activity, ResultModel result) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = activity.openFileOutput(filename, Context.MODE_APPEND);
            fileOutputStream.write((result.toString()+"+").getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public ArrayList<ResultModel> getResultsFromInternalPrivateFile(Activity activity) {
        FileInputStream fileInputStream = null;
        int read;
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<ResultModel> list = new ArrayList<>(0);
        try {
            fileInputStream = activity.openFileInput(filename);
            while ((read = fileInputStream.read()) != -1) {
                 stringBuffer.append((char)read);
            }
            list = fromStringToList(stringBuffer.toString()) ;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }

        return list;
    }

    private ArrayList<ResultModel> fromStringToList(String str){
        int index = 0;
        ArrayList<ResultModel> list = new ArrayList<>(0);
        for (int i = 0; i < str.toCharArray().length; i++){
            if (str.toCharArray()[i] == '+'){
                String result = str.substring(index, i);
                list.add(convertStringToResult(result));
                index=i+1;
            }
        }
        return list;
    }

    private ResultModel convertStringToResult(String result){
        String attempt = "", score = "";
        for (int i = 0; i < result.toCharArray().length; i++) {
            if (result.toCharArray()[i] == '-') {
                attempt = result.substring(0, i);
                score = result.substring(i+1, result.toCharArray().length);
            }
        }
        return new ResultModel(Integer.parseInt(attempt), Integer.parseInt(score));
    }
}
