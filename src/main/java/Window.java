import javapns.Push;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by m on 2017/9/20.
 */
public class Window extends JFrame{
    public JLabel log;
    private JTextField deviceToken;
    private JTextField url;
    private JTextField title;
    private JTextField message;
    private JButton checkout;
    private  boolean isProduction;
    public static int windowWidth = 500;
    public static int windowHeight = 300;



    Window(){
        //获取布局管理器
        Container contentPane = this.getContentPane();
        //设置frame布局方式
        contentPane.setLayout(null);
        //创建panel 相当于JFrame的背景板
        JPanel bottomPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        }catch (Exception e){
            e.printStackTrace();
        }

        Color backgroundColor = new Color(45,48,50);
        Color buttonColor = new Color(64,68,70);
        Color fontColor = new Color(159,172,172);
        Color inputFontColoc = new Color(156,168,170);

        contentPane.setBackground(backgroundColor);
        bottomPanel.setBackground(backgroundColor);
        leftPanel.setBackground(backgroundColor);
        rightPanel.setBackground(backgroundColor);

        {   //设置left、right、bottom panel的位置和大小
            int horizontalMargin = 20;
            int verticalMargin = 20;
            leftPanel.setLocation(horizontalMargin, verticalMargin);
            leftPanel.setSize(windowWidth / 4, windowHeight * 2 / 3);

            rightPanel.setLocation(windowWidth / 4 , verticalMargin);
            rightPanel.setSize(windowWidth * 3 / 4 - horizontalMargin, windowHeight * 2 / 3);

            bottomPanel.setLocation(0, windowHeight * 2 / 3+verticalMargin);
            bottomPanel.setSize(windowWidth, windowHeight / 3);
        }

        {
            //设置left、right、bottom panel的布局方式
            leftPanel.setLayout(new GridLayout(4, 1, 0, 30));
            rightPanel.setLayout(new GridLayout(4, 1, 0, 30));
            bottomPanel.setLayout(null);
        }

        {
            //设置leftPanel的内容 label
            JLabel deviceTokenLabel = new JLabel("DeviceToken");
            JLabel urlLabel = new JLabel("     URL    ");
            JLabel titleLabel = new JLabel("     Title  ");
            JLabel messageLabel = new JLabel("   Message  ");

            //设置字体与颜色
            deviceTokenLabel.setFont(new java.awt.Font("Dialog",   1,   15));
            deviceTokenLabel.setForeground(fontColor);
            urlLabel.setFont(new java.awt.Font("Dialog",   1,   15));
            urlLabel.setForeground(fontColor);
            titleLabel.setFont(new java.awt.Font("Dialog",   1,   15));
            titleLabel.setForeground(fontColor);
            messageLabel.setFont(new java.awt.Font("Dialog",   1,   15));
            messageLabel.setForeground(fontColor);

            leftPanel.add(deviceTokenLabel);
            leftPanel.add(urlLabel);
            leftPanel.add(titleLabel);
            leftPanel.add(messageLabel);
        }

        {
            //设置rightPanel的内容 输入框
            deviceToken = new JTextField(20);
            url = new JTextField(20);
            title = new JTextField(20);
            message = new JTextField(20);

            deviceToken.setBackground(backgroundColor);
            deviceToken.setFont(new java.awt.Font("Dialog",   1,   13));
            deviceToken.setForeground(inputFontColoc);


            url.setBackground(backgroundColor);
            url.setFont(new java.awt.Font("Dialog",   1,   13));
            url.setForeground(inputFontColoc);

            title.setBackground(backgroundColor);
            title.setFont(new java.awt.Font("Dialog",   1,   13));
            title.setForeground(inputFontColoc);

            message.setBackground(backgroundColor);
            message.setFont(new java.awt.Font("Dialog",   1,   13));
            message.setForeground(inputFontColoc);

            Border border = new LineBorder(fontColor,1);
            deviceToken.setBorder(border);
            url.setBorder(border);
            title.setBorder(border);
            message.setBorder(border);

            rightPanel.add(deviceToken);
            rightPanel.add(url);
            rightPanel.add(title);
            rightPanel.add(message);
        }


        {
            //设置bottomPanel的内容
            final JButton send = new JButton("发送");
            Border buttonBorder = new LineBorder(buttonColor,1);
            send.setFont(new Font("Dialog", 1, 15));
            send.setBorder(buttonBorder);
            send.setBackground(buttonColor);
            send.setForeground(fontColor);

            checkout = new JButton("Production");
            checkout.setFont(new Font("Dialog", 1, 15));
            checkout.setBorder(buttonBorder);
            checkout.setBackground(buttonColor);
            checkout.setForeground(fontColor);

            this.log = new JLabel(" ");
            this.log.setFont(new Font("Dialog",   1,   15));
            this.log.setForeground(fontColor);

            //获取 bottomPanel的大小
            int panelWidth = windowWidth;
            int panelHeight = windowHeight/3;
            int margin = 2;
            //设置log Label和send button的位置和大小
            log.setLocation(20,10);
            log.setSize(panelWidth*4/5-margin,30);
            send.setLocation(windowWidth/2+windowWidth/4+20,10);
            send.setSize(80,40);
            checkout.setLocation(windowWidth/2,10);
            checkout.setSize(120,40);


            bottomPanel.add(log);
            bottomPanel.add(send);
            bottomPanel.add(checkout);

            //添加点击事件回调
            {
                send.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        send.setFocusable(false);
                        String deviceTokenContent = deviceToken.getText();
                        String urlContent = url.getText();
                        String messageContent = message.getText();
                        String titleContent = title.getText();
                        JsonUtil.record(deviceTokenContent,urlContent,messageContent,titleContent,isProduction);
                        try {
                            PushMsg.log = log;
                            PushMsg p = new PushMsg(deviceTokenContent,urlContent,messageContent,titleContent,isProduction);
                            //开启新线程
                            p.start();
                        }catch (Exception e1){
                            log.setText(e1.toString());
                            //记录Exception日志
                            JsonUtil.logException(e1.toString() + "\n");
                        }
                    }
                });

                checkout.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        checkout.setFocusable(false);
                        isProduction = !isProduction;
                        checkout.setText(isProduction?"Production":"Development");
                    }
                });
            }
        }


        contentPane.add(leftPanel);
        contentPane.add(rightPanel);
        contentPane.add(bottomPanel);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.pack();
        this.setResizable(false);
        this.setLocation(screenSize.width/2-windowWidth/2,screenSize.height/2-windowHeight/2);
        this.setSize(windowWidth,windowHeight);
        this.setVisible(true);

        configTextField();
        System.out.println("load finish");
    }
    private void configTextField(){
        String path = JsonUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int lastIndex = path.lastIndexOf(File.separator) + 1;
        path = path.substring(0, lastIndex) + "config.json";

        JsonUtil.path = path;
        this.deviceToken.setText(JsonUtil.read("deviceToken"));
        this.url.setText(JsonUtil.read("url"));
        this.message.setText(JsonUtil.read("message"));
        this.title.setText(JsonUtil.read("title"));
        Object checkoutContent = JsonUtil.read("isProduction");
        if(((String)checkoutContent).equals("") || checkoutContent.equals("1")){
            checkoutContent = "Production";
        }else{
            checkoutContent = "Development";
        }
        this.checkout.setText((String)checkoutContent);
    }
}
