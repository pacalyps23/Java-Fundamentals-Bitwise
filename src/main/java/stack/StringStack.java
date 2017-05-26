package stack;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by luisgarcia on 5/26/17.
 */
public class StringStack
{
    private String [] myString;
    private static int count = 0;

    public StringStack()
    {
        myString = new String[count];
        count = 0;
    }

    public void push(String element)
    {
            myString = Arrays.copyOf(myString, myString.length+1);

        count++;
    }

    public String pop()
    {
        return "h";
    }

    public void peek()
    {

    }

    public int size()
    {
        return count;
    }






}
