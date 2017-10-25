/**
 * Created by david on 2017/10/25.
 */
public class LogModel {
    public  String deviceToken;
    public  String url;
    public  String title;
    public  String message;
    public  Integer isProduction;

    public LogModel(String deviceToken, String url, String title, String message, Integer isProduction){
        this.deviceToken = deviceToken;
        this.url = url;
        this.title = title;
        this.message = message;
        this.isProduction = isProduction;
    }
}
