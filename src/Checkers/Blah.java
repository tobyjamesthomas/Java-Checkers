package Checkers;

import javax.swing.*;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Manz
 * Date: 1/27/2014
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */

class Blah {

    public static void main (String [] args){

        Scanner nameScanner = (new Scanner("Whats your name?"));
        String anotherName = (nameScanner.nextLine());


        System.out.println(product(3, 4));


    }

    public static int product (int a, int b){

        int product = a*b;
        return product;


    }

}
