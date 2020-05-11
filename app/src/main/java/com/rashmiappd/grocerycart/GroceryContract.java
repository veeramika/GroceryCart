package com.rashmiappd.grocerycart;

import android.provider.BaseColumns;

public class GroceryContract {

    private GroceryContract(){} //A private constructor is created to ensure that we never
    //create any instance of it, so its empty constructor
    //One of the use of private constructor is to serve singleton classes.
    //A singleton class is one which limits the number of objects creation to one.



    //Static classes are basically a way of grouping classes together in Java.
    // Java doesn't allow you to create top-level static classes; only nested (inner) static classes.

    public static final class GroceryEntry implements BaseColumns{

        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESSTAMP = "timestamp";
    }
}

/*The main difference between a static and final keyword is that static is keyword is used to define the class member
that can be used independently of any object of that class.
 Final keyword is used to declare, a constant variable, a method which can not be overridden and a class that can not be inherited*/

/*A static method belongs to the class itself while a non-static method belongs to each instance of a class.
Therefore, a static method can be called directly without creating any instance of the class and an object
is needed to call a non-static method.
A static variable is common to all the instances (or objects) of the class because it is a class level variable.
In other words you can say that only a single copy of static variable is created and shared among all the instances
 of the class. Memory allocation for such variables only happens once when the class is loaded in the memory.
*/