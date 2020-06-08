package TowersOfHanoi;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
*<pre>
*   Class           SolutionMethod
*   File            SolutionMethod.java
*   Description     This class shows the solution with non recursive tower of
*                   Hanoi
*   @author         <i>Ardit Miftaraj</i>
*   Environment     PC, Windows 10, NetBeans IDE 8.2, jdk 1.8.0_221
*   Date            10/10/2020
*   @version        1.0.0
*   @see            javax.swing.JPanel
*</pre>
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
public class SolutionMethod 
{
   int nrOfDisks;
    private  ArrayList<Stack<Integer>> stacks = new ArrayList<>(3);
    int moves=0;
    long timer;
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		setNrOfDisks()
    *	Description     Set the number of disks
    *   @param          nrOfDisks   int
    *   @author         <i>Jonida Durbaku</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void setNrOfDisks(int nrOfDisks) {
        this.nrOfDisks = nrOfDisks;
    }
    
    
    
    public void displayStacks() {
        System.out.println("Stacks:");
        for (Stack stack : stacks) {
            System.out.println(stack);
        }
        System.out.println("");
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		nonResursiveSolution()
    *	Description     solves the tower of hanoi non recursively
    *   @param          nrOfDisks   int
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public void nonResursiveSolution(int nrOfDisks)
    {
        long startTimer = System.currentTimeMillis();
        for (int i = 0; i < 3; i++)
            stacks.add(new Stack<>());

        Stack center = stacks.get(1); // center pillar

        for (int i = nrOfDisks; i > 0; i--) // push 3 disks
            stacks.get(0).push(i);


        boolean isSmallestMove = true;
        while (center.size() != nrOfDisks) {
            if (isSmallestMove)
                moveSmallest();
             else
                makeLegalMove();
            isSmallestMove = !isSmallestMove;
             displayStacks();
             
    }
        long stopTimer = System.currentTimeMillis();
        timer=stopTimer-startTimer;
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		getTimer()
    *	Description     gets the time it needs to solve the tower of hanoi
    *   @return         long
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    public long getTimer() {
        return timer;
    }
    
    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		makeLegalMove()
    *	Description     checks if the movement is legal if the one before is
    *                   smallesr or empty
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private  void makeLegalMove() {

        ArrayList<Stack<Integer>> excludeSmallest = new ArrayList<>(stacks);
        excludeSmallest.remove(getSmallest());

        Stack<Integer> stk1 = excludeSmallest.get(0);
        Stack<Integer> stk2 = excludeSmallest.get(1);

        if (stk1.empty() && stk2.isEmpty()) return;
        if (stk1.empty())
            stk1.push(stk2.pop());
        else if (stk2.isEmpty())
            stk2.push(stk1.pop());
        else if (stk1.peek() < stk2.peek())
            stk2.push(stk1.pop());
        else
            stk1.push(stk2.pop());
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		moveSmallest()
    *	Description     gets the smallest and moves it 
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private  void moveSmallest() {
        Stack<Integer> smallest = getSmallest();

        int size = stacks.size();
        int index = stacks.indexOf(smallest);
        int delta = ((size & 1) == 1) ? 1 : -1;
        index += delta;

        if (index < 0)
            index = size - 1;
        if (index == size)
            index = 0;

        // Move smallest
        stacks.get(index).push(smallest.pop());
    }

    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *<pre>
    *	Method		getSmallest()
    *	Description     gets the smallest object to move 
    *   @return         Stack<Integer>
    *   @author         <i>Ardit Miftaraj</i>
    *	Date            03/10/2020 
    *</pre>
    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    private  Stack<Integer> getSmallest() {
        Stack<Integer> smallest = null;
        for (Stack<Integer> stack : stacks) {
            if (smallest == null) smallest = stack;
            if (stack.isEmpty()) continue;
            try {
                if (smallest.isEmpty())
                    smallest = stack;
                else if (stack.peek() < smallest.peek())
                    smallest = stack;
            } catch (EmptyStackException ignore) {
            }
        }
        return smallest;
    }
}

