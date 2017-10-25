import javax.swing.*;

/**
 * Created by m on 2017/9/20.
 */
public class Application {

    public static void main(String []args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Window mainWindow = new Window();

            }
        });
    }


}

/*
  deviceToken : 6b371d18009ad7a85e75c04f4cb5d6a7eeabb7421acf785fa532de1e346743a3
  deviceToken bp : e24f84e8a17fb3939954f89d3086d2a41f8cc49b6821829d15a0c60bebd5bdf6
  url         : http://m.i360mall.com/item.html?itemId=5123404
  activity page url : http://m.i360mall.com/1925c43107eff051.html
 */