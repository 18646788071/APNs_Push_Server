/**
 * Created by david on 2017/9/21.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.codemodel.internal.fmt.JTextFile;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import javax.swing.*;

public class PushMsg extends Thread{
    public static JLabel log;
    public String deviceToken;
    public String url;
    public String message;
    public String title;
    private boolean isProduction;

    private static String disP12 = "com.qihu.malljd_push_dis.p12";
    private static String devP12 = "com.qihu.malljd_push_dev.p12";


    public PushMsg(String deviceToken, String url, String message, String title, boolean isProduction){
        this.deviceToken = deviceToken;
        this.url = url;
        this.message = message;
        this.title = title;
        this.isProduction = isProduction;
    }
    public static void push(String deviceToken, String url, String message, String title, boolean isProduction) throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                log.setText("正在推送消息...");
            }
        });


        List<String> tokens = new ArrayList<String>();
        tokens.add(deviceToken);


        //java必须要用导出p12文件  php的话是pem文件
        String certificatePath = PushMsg.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int lastIndex = certificatePath.lastIndexOf('/') + 1;
        certificatePath = certificatePath.substring(0,lastIndex) + (isProduction?disP12:devP12);
        if(certificatePath.length() <= 0){
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    log.setText("找不到证书！");
                }
            });
            return ;
        }
        //导出证书时设置的密码
        String msgCertificatePassword = "360buy";
        boolean sendCount = true;

        //设置payload
        PushNotificationPayload payload = new PushNotificationPayload();
        payload.addCustomDictionary("url", url);
        payload.addSound("default");//设置为系统默认声音
        payload.addCustomAlertBody(message);
        payload.addCustomAlertTitle(title);

//        payload.setContentAvailable(true);
//        payload.addCustomDictionary("acme1", "bar");
//        payload.addCustomDictionary("acme2", 42);

        //初始化pushManager
        PushNotificationManager pushManager = new PushNotificationManager();

        //isProduction 决定线上环境还是测试环境
        pushManager.initializeConnection(new AppleNotificationServerBasicImpl(
                certificatePath, msgCertificatePassword, isProduction));


        List<PushedNotification> notifications = new ArrayList<PushedNotification>();

        //开始推送消息
        if (sendCount) {
            Device device = new BasicDevice();
            device.setToken(deviceToken);
            PushedNotification notification = pushManager.sendNotification(
                    device, payload, true);
            notifications.add(notification);
        } else {
            List<Device> devices = new ArrayList<Device>();
            for (String token : tokens) {
                devices.add(new BasicDevice(token));
            }
            notifications = pushManager.sendNotifications(payload, devices);
        }

        List<PushedNotification> failedNotification = PushedNotification
                .findFailedNotifications(notifications);
        List<PushedNotification> successfulNotification = PushedNotification
                .findSuccessfulNotifications(notifications);
        int failed = failedNotification.size();
        int successful = successfulNotification.size();
        if(successful>0){
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    log.setText("推送成功!");
                }
            });
        }else{
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    log.setText("推送失败!");
                }
            });

        }
        pushManager.stopConnection();
    }

    public void run(){
        try {
            this.push(this.deviceToken, this.url, this.message, this.title, this.isProduction);
        }catch (Exception e){
            if(e.toString().indexOf("File does not exist")!=-1 && e.toString().indexOf(".p12") != -1){
                try {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            log.setText("找不到证书");
                        }
                    });
                }catch (Exception e1){
                    e1.printStackTrace();
                }

            }
            JsonUtil.logException(e.toString());
        }
    }

}