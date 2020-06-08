package TowersOfHanoi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
*   Class           Splash
*   File            Splash.java
*   Description     Class that creates a Rectangle Object 
*   @author         <i>Jonida Durbaku</i>
*   Environment     PC, Windows 10, NetBeans IDE 8.2, jdk 1.8.0_131
*   Date            02/20/2020
*   @version        1.0.0
*</pre>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class Splash extends JWindow
{
    private int duration = 3000;
    JProgressBar loading = new JProgressBar();
    private int progress;
    
    public Splash(int dur)
    {
        duration = dur;
    }
    // a simple method to show a title screen in the center of the screen
    // for the amount of time given in the constructor
    public void showSplash()
    {
       JPanel content = (JPanel)getContentPane();
       content.setBackground(Color.lightGray);
       //set the window's bounds, centering the window
       int width = 650;
       int height = 245;
       Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
       int x = (screen.width - width)/2;
       int y = (screen.height- height)/2;
       //center Splash relative to screen window
       this.setBounds(x,y,width,height);
       //build the splashscreen
       JLabel label = new JLabel(new ImageIcon("src/TowersOfHanoi/splash.gif"));
       JLabel copyrt = new JLabel ("Copyright Towers Of Hanoi inc., 2020, Towers Of Hanoi", JLabel.CENTER);
       
       copyrt.setFont(new Font("Sans-Serif", Font.BOLD,12));
       content.add(label,BorderLayout.CENTER);
       content.add(loading, BorderLayout.SOUTH);
       Color border = new Color(50,20,20,55);
       content.setBorder(BorderFactory.createLineBorder(border,10));
       
       //display it
       this.setVisible(true);
       //wait a little while, maybe while loading resources
       try
       {
           incProgress(20);
           Thread.sleep(duration);
       }
       catch(Exception e)
       {
           e.printStackTrace();
       }
       this.dispose(); //setvisiable(false)
       
    }  
     public void incProgress(int amount) throws InterruptedException
     {
         ProgressThread up = new ProgressThread(amount);
         up.thread.start();
         
     }
     class ProgressThread
     {
         private int amount;
         public ProgressThread(int amount)
         {
             this.amount = amount;
         }
     
     private Thread thread = new Thread(new Runnable()
     {
      @Override
      public void run()
      {
          while (progress < 100)
          {
              progress ++;
              try 
              {
                  Thread.sleep(30);
              }
              catch(InterruptedException ex)
                      {
                         ex.printStackTrace(); 
                      }
              loading.setValue(progress);
          }
      }
     });

     
     }  
    
}
