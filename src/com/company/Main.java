package com.company;


import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public static void main(String[] arg) {

        System.out.println("Inga fel!");
    }

}

class RatNum {

    private int numerator;
    private int denominator; // n/d


    public RatNum() {
        this.numerator = 0;
        this.denominator = 1;
    }

    public RatNum(int n) {
        this.numerator = n;
        this.denominator = 1;
    }

    public RatNum(String s) {

        RatNum temp = parse(s);

        this.numerator = temp.getNumerator();
        this.denominator = temp.getDenominator();

    }

    public RatNum(int numerator, int denominator) {

        if (denominator == 0)
            throw new NumberFormatException("Denominator = 0");

        if (gcd(numerator, denominator) != 1) {
            this.numerator = (numerator / gcd(numerator, denominator));
            this.denominator = (denominator / gcd(numerator, denominator));

        } else {

            this.numerator = numerator;
            this.denominator = denominator;

        }

        if (denominator < 0) {
            this.numerator = 0 - this.numerator;
            this.denominator = Math.abs(this.denominator);
        }
    }

    public RatNum(RatNum ratnum) {
        this.numerator = ratnum.getNumerator();
        this.denominator = ratnum.getDenominator();
    }

    /**
     * @param frac Takes String parameter written in one of the following forms:  "a/b", "-a/b", "a/-b" or "a"
     */


    public static RatNum parse(String frac) {
        int tnum = 1;
        int tden = 1;
        RatNum ratNum;


        // if any of the parsing creates an exception we want to handle it
        try {

            switch (frac.length()) {

                case (1):

                    // Maby extend the integer class to make this look better
                    tnum = strIndexToInt(frac, 0);
                    tden = 1;

                    break;

                case (3):
                    tnum = strIndexToInt(frac, 0);
                    tden = strIndexToInt(frac, 3);
                    break;

                case (4):

                    if (frac.charAt(0) == '-') {

                        tnum = -strIndexToInt(frac, 1);
                        tden = strIndexToInt(frac, 3);

                    } else if (frac.charAt(2) == '-') {

                        tnum = -(strIndexToInt(frac, 1));
                        tden = strIndexToInt(frac, 3);

                    }
                    break;

                default:

                    throw new NumberFormatException("Only input in the formats: \"a/b\", \"-a/b\", \"a/-b\" or \"a\" allowed");

            }
        } catch (Exception e) {
            throw new NumberFormatException("Only input in the formats: \"a/b\", \"-a/b\", \"a/-b\" or \"a\" allowed");
        }
        ratNum = new RatNum(tnum, tden);
        return ratNum;
    }





    /*
    Returns the greatest common denominator
     */

    public int getNumerator() {
        return this.numerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }
    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    static int gcd(int m, int n) {

        int gcd = 1;

        if (n == 0 && m == 0) {
            throw new IllegalArgumentException();
        }

        /* if anyone of the 2 integers is zero then return the absolute value of the other number
        if they're the same value, return  the absolute value of any of them*/
        if (m == 0) {
            return Math.abs(n);
        } else if (n == 0) {
            return Math.abs(m);
        } else if (n == m) {
            return Math.abs(n);
        }

        // The absolute value of the number closest to zero is the biggest possible cd for any 2 numbers
        if (Math.abs(m) > Math.abs(n)) {

            for (int i = Math.abs(m); i > 0; i--) {

                if (m % i == 0 && n % i == 0) {
                    return i;
                }
            }

        } else if (Math.abs(m) < Math.abs(n)) {
            for (int i = Math.abs(n); i > 0; i--) {
                if (m % i == 0 && n % i == 0) {
                    return i;
                }
            }
        }
        return gcd;
    }

    // Converts the fraction to a String
    public String toString() {
        return (Integer.toString(numerator) + "/" + Integer.toString(denominator));
    }


    @Override
    public boolean equals(Object r) {
        if (r == null) {
            return false;
        }
        if (!(r instanceof RatNum)) {
            return false;
        }

        RatNum r2 = (RatNum) r;
        return (this.numerator == r2.getNumerator() && this.denominator == r2.getDenominator());
    }

    public boolean lessThan(RatNum r){

        int rExtnum, extNum;
        // extend fractions to the same denominators

        rExtnum = r.getNumerator() * this.getDenominator();
        extNum = this.numerator * r.getDenominator();

        if(rExtnum < extNum){
            return true;
        }
        return false;
    }

    public RatNum add(RatNum r){

        int sumNumerator,sumDenominator;


        // a/b + c/d = (a*d + c*b) / b*d
        sumDenominator = (this.denominator*r.getDenominator());
        sumNumerator = (this.numerator*r.getDenominator() + r.getNumerator()*this.getDenominator());


        return new RatNum(sumNumerator,sumDenominator);
    }

    public RatNum sub(RatNum r){

        int newNum,newDen;

        newNum = ((this.getNumerator()*r.getDenominator()) - (r.getNumerator()*this.getDenominator()));
        newDen = this.getDenominator()*r.getDenominator();

        return new RatNum(newNum,newDen);
    }

    public RatNum mul(RatNum r){
        return new RatNum((r.getNumerator()*this.getNumerator()), (r.getDenominator()*this.getDenominator()));
    }

    public RatNum div(RatNum r){
        return new RatNum((this.numerator*r.getDenominator()),(this.denominator*r.getDenominator()));
    }
    public String toDotString(int decimalCount){

        String s = "a";
        int temp;

        temp = this.numerator / this.denominator;

        if (this.numerator < 0){
            if(temp == 0){
                s = "-" + String.valueOf(temp);
            }else {
                s = String.valueOf(temp);
            }
        }else {
            s = String.valueOf(temp);
        }



        if(decimalCount == 0)
            return s;

        // for decimalcount > 0 we need a decimalpoint
        s += ".";

        temp = this.numerator % this.denominator;
        temp = Math.abs(temp);
        for (int i = 0; i < decimalCount ; i++) {

            temp *= 10;
            s+=String.valueOf(temp/this.denominator);
            temp %= this.denominator;

        }
        System.out.println(s);
        return s;
    }

    /**
     * Converts integer at a specific index of String
     * @param str
     * @param index
     * @return
     */
    private static int strIndexToInt(String str, int index) {
        return Integer.parseInt(String.valueOf(str.charAt(index)));
    }
}


class RatNumTest3 {

    public static String ratNumToString(RatNum r) {
        int num = r.getNumerator();
        int denom = r.getDenominator();
        if (num == 0) {
            return "0";
        }
        String s = "";
        int whole = num / denom;
        int fraction = num % denom;
        if (whole != 0) {
            s = whole + " ";
            fraction = Math.abs(fraction);
        }
        if (fraction != 0)
            s =  s + fraction + "/" + denom;
        return s;
    }

    private static void divTester() {
        // Testar equals och clone
        RatNum x = new RatNum(6,2);
        RatNum y = new RatNum(0);
        RatNum z = new RatNum(0,1);
        RatNum w = new RatNum(75,25);
        Object v = new RatNum(75,25);
        String str = new String("TEST");

        System.out.println();
        System.out.println(">>>> Test av equals: Vi har inte gått igenom equals ännu ");
        System.out.println("så du behöver inte klara dessa tester än");
        //System.out.println("equals test 1 ");
        if (x.equals(y) || !y.equals(z) || !x.equals(w)) {
            System.out.println("RatNumTest3: FEL 1 i equals!!");
        }
        //System.out.println("equals test 2 ");
        if ( !w.equals(v)  ) { //  w skall vara lika med v
            // med equals(RatNum r) så väljs dock objects equals
            // eftersom parameterprofilen stämmer där och då blir dom olika
            System.out.println("RatNumTest3: FEL 2 i equals!!");
        }
        //.out.println("equals test 3 ");
        if ( !v.equals(w) ) { // dyn. bindningen ger RatNums equals
            // men med equals(RatNum r) så blir det som ovan
            System.out.println("RatNumTest3: FEL 3 i equals!!");
        }
        //.out.println("equals test 4 ");
        try {
            if ( w.equals(null)  ) { //skall inte vara lika
                System.out.println("RatNumTest3: FEL 4.1 i equals!!");
            }
        } catch (NullPointerException e) { // men skall klara null
            System.out.println("RatNumTest3: FEL 4.2 i equals!!");
        }
        //System.out.println("equals test 5 ");
        if ( w.equals(str) ) { // skall ge false
            // med equals(RatNum r) får man återigen Objects equals
            // och den ger rätt svar här
            System.out.println("RatNumTest3: FEL 5 i equals!!");
        }
        System.out.println("<<<< Slut på equals tester");
        RatNum r = new RatNum(1);
        String expected = "";
        int k = 0;
        for (int i=0; i<7; i++) {
            if (i == 0) {
                expected = "0.500"; k = 3; r = new RatNum(1,2);
            } else if (i == 1) {
                expected = "-20.0"; k = 1; r = new RatNum(-20,1);
            } else if (i == 2) {
                expected = "0.666"; k = 3; r = new RatNum(2,3);
            } else if (i == 3) {
                expected = "0.0001"; k = 4; r = new RatNum(1,10000);
            } else if (i == 4) {
                expected = "-0.001"; k = 3; r = new RatNum(-11,10000);
            } else if (i == 5) {
                expected = "104.477"; k = 3; r = new RatNum(7000,67);
            } else if (i == 6) {
                expected = "0.89"; k = 2; r = new RatNum(89,99);
            }
            String output = r.toDotString(k);
            if (!output.equals(expected)) {
                System.out.println("RatNumTest3 FEL: toDotString(" + k + ") visar fel för " + r.toString() +
                        "; gav: "+output+"; skulle vara: " + expected);
            }
        }
        /*
          try {
          y = (RatNum) x.clone();
          }
          catch (Exception ce) {}
          if (!y.equals(x) || y==x)
          System.out.println("RatNumTest3: FEL I clone!!");
        */
    } // end divTester

    public static String testExpr(String s) {
        // @require s.length > 0
        //String result = "";
        Scanner sc = new Scanner(s);
        String[] a = new String[3];
        int i;
        for(i=0; i<3 && sc.hasNext(); i++) {
            a[i] = sc.next();
        }
        if (i != 3 || sc.hasNext())
            return("Felaktigt uttryck!");
        else {
            try {
                RatNum r1 = RatNum.parse(a[0]);
                String op = a[1];
                char c = op.charAt(0);
                RatNum r2 = new RatNum(a[2]);
                if (op.length() != 1 || "+-*/=<".indexOf(c) < 0)
                    return("Felaktig operator!");
                else {
                    RatNum res = null;
                    if (c == '+')
                        res = r1.add(r2);
                    else if (c == '-')
                        res = r1.sub(r2);
                    else if (c == '*')
                        res = r1.mul(r2);
                    else  if (c == '/')
                        res = r1.div(r2);
                    else if (c == '=')
                        return( Boolean.toString(r1.equals(r2)) );
                    else if (c == '<')
                        return( Boolean.toString(r1.lessThan(r2)) );
                    if ("+-*/".indexOf(c) >= 0)
                        if (res == null)
                            return("Fel i add, sub, mul eller div");
                        else
                            return(res.toString());
                }
            }
            catch (NumberFormatException e) {
                return("NumberFormatException");
            }
        }
        return ("Okänt fel");
    } // end testExpr

    public static void main(String[] arg) throws IOException {

        divTester();
        StringBuilder stringToPrint;
        String correctAnswer;
        // om det finns argumet så är det testfilen => auto test
        boolean machine; // keep track of if testing is human or machine
        String filename = "";
        Scanner in;
        //System.exit(0); // debug
        if (arg.length > 0) {
            machine = true;
            filename = arg[0];
            in = new Scanner(new File(filename));
            System.out.println("automatic testing - reading " + filename);
            System.out.println("given expression\t given result");
        } else {
            machine = false;
            in = new Scanner(System.in);
            System.out.println("Skriv uttryck på formen a/b ? c/d, där ? är något av tecknen + - * / = <");
        }
        // read input
        while (true) {
            if (!machine) {System.out.print("> ");  System.out.flush();}
            if (!in.hasNext()) {System.out.println(); System.exit(0);} // no input left
            String s = in.nextLine();
            if ( s == null || s.length()==0 ) {
                break;
            }
            correctAnswer = "";
            stringToPrint = new StringBuilder();
            if (machine) {
                // split input in question - correct answer
                int i = s.indexOf("-->");
                if (i<1) {
                    System.out.println("##### Error - No answers found in file - cannot correct");
                    System.exit(0);
                }
                correctAnswer = s.substring(i+4);
                s = s.substring(0,i);
                //System.out.println("\t##s= " + s + "* correct answer=" + correctAnswer + " debug"); // debug
            }
            String givenAnswer = testExpr(s);
            stringToPrint.append(s + "\t--> " + givenAnswer );
            if (machine && !correctAnswer.equals(givenAnswer)) {
                //System.out.println( "====================================================" );
                //System.out.println("\t##*"+givenAnswer+"* " + "*"+correctAnswer+ "*" + ratNumToString(new RatNum(givenAnswer)) + "* debug"); // debug
                // test if answer given as a/B +
                // strängen till NumberFormatException kan vara olika
                if ( correctAnswer.equals(ratNumToString(new RatNum(givenAnswer)))
                        || ( correctAnswer.indexOf("NumberFormatException") != -1
                        && givenAnswer.indexOf("NumberFormatException") != -1 )
                ) {
                    ; // do nothing
                } else {
                    stringToPrint.append(" ###### Error, correct answer is= " + correctAnswer);
                }
            }
            System.out.println(stringToPrint.toString());
        }
    }

}


