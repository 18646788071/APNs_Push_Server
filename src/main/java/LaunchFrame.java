import javax.swing.*;
import java.awt.*;

/**
 * Created by david on 2017/9/27.
 */
public class LaunchFrame extends JFrame {
    public LaunchFrame(){

        int windowHeight = 300;
        int windowWidth = 500;

        //设置启动图
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path + "/launch.png";
        ImageIcon launchImage = new ImageIcon(path);
        launchImage.setImage(launchImage.getImage().getScaledInstance(windowWidth,windowHeight,Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(launchImage);
        this.add(imageLabel,BorderLayout.CENTER);

        //设置位置及大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(windowWidth,windowHeight);
        this.setLocation(screenSize.width/2-windowWidth/2,screenSize.height/2-windowHeight/2);

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        System.out.println("launch load finish");
    }

    public void finishLaunch(){
        this.setVisible(false);
    }
}
