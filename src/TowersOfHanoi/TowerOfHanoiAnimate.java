package TowersOfHanoi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;


/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
*   Class           TowerOfHanoiAnimate
*   File            TowerOfHanoiAnimate.java
*   Description     This class shows the animation solution of the tower of 
*                   hanoi is implements Runnable and ActionListener 
*   @author         <i>Ardit Miftaraj</i>
*   Environment     PC, Windows 10, NetBeans IDE 8.2, jdk 1.8.0_221
*   Date            10/10/2020
*   @version        1.0.0
*   @see            javax.swing.JPanel
*</pre>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
class TowerOfHanoiAnimate extends JPanel implements Runnable, ActionListener {

    
    private static Color BACKGROUND_COLOR = Color.PINK;
    private static Color BORDER_COLOR = Color.BLACK;
    private static Color DISK_COLOR = Color.BLUE;
    private static Color MOVE_DISK_COLOR = Color.red;
    private int moves=0;
    private boolean isItOn = false;
    
    private int speed;
    private int nrOfDisks;
    
    private BufferedImage OSC;   // The off-screen canvas.  Frames are drawn here, then copied to the screen.

    private int status;   // Controls the execution of the thread; value is one of the following constants.

    private static final int GO = 0;       // a value for status, meaning thread is to run continuously
    private static final int PAUSE = 1;    // a value for status, meaning thread should not run
    private static final int STEP = 2;     // a value for status, meaning thread should run one step then pause
   
   
    private int[][] tower;
    private int[] towerHeight;
    private int moveDisk;
    private int moveTower;

    private Display display;  // A subpanel where the frames of the animation are shown.

    private JButton runPauseJButton;  // 3 control buttons for controlling the animation
    private JButton nextStepJButton;


/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
*   Class           Display
*   File            TowerOfHanoiAnimate.java
*   Description     This inner class extends JPanel and overides the 
*                   paintComponent method to draw the animation
*   @author         <i>Ardit Miftaraj</i>
*   Environment     PC, Windows 10, NetBeans IDE 8.2, jdk 1.8.0_221
*   Date            03/10/2020
*   @version        1.0.0
*   @see            javax.swing.JPanel
*</pre>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private class Display extends JPanel {
        /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		paintComponent()
    *	Description     overides paintComponent to draw animation for tower of 
    *                   hanoi
    *   @param          g Graphics
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = (getWidth() - OSC.getWidth())/2;
            int y = (getHeight() - OSC.getHeight())/2;
            g.drawImage(OSC, x, y, null);
        }
    }
     /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		TowerOfHanoiAnimate()
    *	Description     Default constructor that creates all butons needed 
    *                   and invokes to start thread
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public TowerOfHanoiAnimate () {
        OSC = new BufferedImage(600,400,BufferedImage.TYPE_INT_RGB);
        display = new Display();
        display.setPreferredSize(new Dimension(600,400));
        display.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        display.setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);
        JPanel buttonBar = new JPanel();
        add(buttonBar, BorderLayout.SOUTH);
        buttonBar.setLayout(new GridLayout(1,0));
        runPauseJButton = new JButton("Run");
        runPauseJButton.addActionListener(this);
        buttonBar.add(runPauseJButton);
        nextStepJButton = new JButton("Next Step");
        nextStepJButton.addActionListener(this);
        buttonBar.add(nextStepJButton);
        new Thread(this).start();
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		setSpeed()
    *	Description     Set methot for the speed of animation
    *   @param          speed int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		setNrOfDisks()
    *	Description     Set methot for the number of disks
    *   @param          nrOfDisks int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setNrOfDisks(int nrOfDisks) {
        this.nrOfDisks = nrOfDisks;
    }

     

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		actionPerformed()
    *	Description     Method overrided that will appropretly make the right 
    *                   action when a specific button is clicked
    *   @param          evt ActionEvent
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    synchronized public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == runPauseJButton) {  // Toggle between running and paused.
            if (status == GO) {  // Animation is running.  Pause it.
                status = PAUSE;
                nextStepJButton.setEnabled(true);
                runPauseJButton.setText("Run");
            }
            else {  // Animation is paused.  Start it running.
                status = GO;
                nextStepJButton.setEnabled(false);  // Disabled when animation is running
                runPauseJButton.setText("Pause");
            }
        }
        else if (source == nextStepJButton) {  // Set status to make animation run one step.
            status = STEP;
        }
        notify();  // Wake up the thread so it can see the new status value!
    }


    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		run()
    *	Description     Method overrided that will that keep the animation 
    *                   running while condition is true
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    @Override
    public void run() {
        while (!isItOn) {
            runPauseJButton.setText("Run");
            nextStepJButton.setEnabled(true);
            //startOverButton.setEnabled(false);
            setUpProblem();  // Sets up the initial state of the puzzle
            status = PAUSE;
            checkStatus(); // Returns only when user has clicked "Run" or "Next"
            //startOverButton.setEnabled(true);
            try {                       //****** the number of disc can be changed from here down to ****
                solve(nrOfDisks,0,2,1);  // Move 10 disks from pile 0 to pile 1.
                isItOn = true;
            }
            catch (IllegalStateException e) {
               // status = PAUSE;
                // Exception was thrown because user clicked "Start Over".
            }
        }
        runPauseJButton.setText("Run");
        runPauseJButton.setEnabled(false);
        nextStepJButton.setEnabled(false);
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		checkStatus()
    *	Description     Method that cheks if the user clicked pause or next step
    *                   button in order to continue with animation
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    synchronized private void checkStatus() {
        while (status == PAUSE) {
            try {
                wait();
            }
            catch (InterruptedException e) {
            }
        }
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		setUpProblem()
    *	Description     Method that sets up the first image of the animation
    *                   with the appropriate disks
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    synchronized private void setUpProblem() {
        moveDisk= 0;
        tower = new int[3][nrOfDisks];
        for (int i = 0; i < nrOfDisks; i++)
            tower[0][i] = nrOfDisks - i;
        towerHeight = new int[nrOfDisks];
        towerHeight[0] = nrOfDisks;     //down to here *****************************
        if (OSC != null) {
            Graphics g = OSC.getGraphics();
            drawCurrentFrame(g);
            g.dispose();
        }
        display.repaint();
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		solve()
    *	Description     Solves recursively the tower of hanoi
    *   @param          nrOfDisks int
    *   @param          fromPeg int
    *   @param          toPeg int
    *   @param          sparePeg int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private void solve(int disks, int fromPeg, int toPeg, int sparePeg) {
        if (disks == 1)
            moveOne(fromPeg,toPeg);
        else {
            solve(disks-1, fromPeg, sparePeg, toPeg);
            moveOne(fromPeg,toPeg);
            solve(disks-1, sparePeg, toPeg, fromPeg);
        }
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		moveOne()
    *	Description     Moves one by one the disks while also checking the status
    *                   of the app if paused or if next step with the appropriate
    *                   delay info that is provided by the user.
    *   @param          fromStack int
    *   @param          toStack int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    synchronized private void moveOne(int fromStack, int toStack) {
        moveDisk = tower[fromStack][towerHeight[fromStack]-1];
        moveTower = fromStack;
        delay(speed);
        towerHeight[fromStack]--;
        putDisk(MOVE_DISK_COLOR,moveDisk,moveTower);
        delay(speed);
        putDisk(BACKGROUND_COLOR,moveDisk,moveTower);
        delay(speed);
        moveTower = toStack;
        putDisk(MOVE_DISK_COLOR,moveDisk,moveTower);
        delay(speed);
        putDisk(DISK_COLOR,moveDisk,moveTower);
        tower[toStack][towerHeight[toStack]] = moveDisk;
        towerHeight[toStack]++;
        moveDisk = 0;
        if (status == STEP)
            status = PAUSE;
        
        checkStatus();
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		delay()
    *	Description     Method that delays the app for it to be fast or slow
    *   @param          milliseconds int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    synchronized private void delay(int milliseconds) {
        try {
            wait(milliseconds);
        }
        catch (InterruptedException e) {
        }
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		putDisk()
    *	Description     Method that invokes repaint to display the moved disk
    *   @param          color Color
    *   @param          disk int
    *   @param          tower int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void putDisk(Color color, int disk, int tower) {
        Graphics g = OSC.getGraphics();
        g.setColor(color);
        g.fillRoundRect(95+210*tower - 5*disk - 5, 300-12*towerHeight[tower], 10*disk+10, 10, 10, 10);
        g.dispose();
        display.repaint();
    }
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		drawCurrentFrame()
    *	Description     Method that draws the frame in which the animation will happen
    *   @param          g Graphics
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    synchronized private void drawCurrentFrame(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0,0,600,400);
        g.setColor(BORDER_COLOR);
        if (tower == null)
            return;
        g.fillRect(20,312,150,5);
        g.fillRect(220,312,150,5);
        g.fillRect(430,312,150,5);
        g.setColor(DISK_COLOR);
        for (int t = 0; t < nrOfDisks; t++) {
            for (int i = 0; i < towerHeight[t]; i++) {
                int disk = tower[t][i];
                g.fillRoundRect(95+210*t - 5*disk - 5, 300-12*i, 10*disk+10, 10, 10, 10);
            }
        }
        if (moveDisk > 0) {
            g.setColor(MOVE_DISK_COLOR);
            g.fillRoundRect(95+210*moveTower - 5*moveDisk - 5, 300-12*towerHeight[moveTower],
                    10*moveDisk+10, 10, 10, 10);
        }
    }
} 