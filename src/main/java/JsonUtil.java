import com.alibaba.fastjson.JSON;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;

/**
 * Created by david on 2017/9/26.
 */
public class JsonUtil {
    public static String path;
    public static void record(String deviceToken, String url, String message, String title,
                              boolean isProduction){
        LogModel logger = new LogModel(deviceToken,url,title,message,isProduction?1:0);
        String jsonStr = JSON.toJSONString(logger);

        try{
            writeFile(path,jsonStr);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void logException(String error){
        String logPath = path;
        int lastIndex = path.lastIndexOf('/') + 1;
        logPath = logPath.substring(0,lastIndex) + "log.txt";
        try {
            writeFile(logPath, error);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String read(String key){
        String jsonStr = readFile(path);
        JSONObject data;
        String result = "";
        if(jsonStr.length() > 0) {
            try {
                data = new JSONObject(jsonStr);
                result = data.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static void writeFile(String path, String content) throws IOException{
        FileWriter fw = new FileWriter(path);
        PrintWriter out = new PrintWriter(fw);
        out.write(content);
        out.println();
        fw.close();
        out.close();
    }

    private static String readFile(String path){
        File file = new File(path);
        BufferedReader reader = null;
        String lastStr = "";
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while((tempString = reader.readLine()) != null){
                lastStr = lastStr + tempString;
            }
        }catch (IOException e){
            if(e instanceof FileNotFoundException){
                return lastStr = "";
            }
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch(IOException e){

                }
            }
        }
        return lastStr;
    }


}
