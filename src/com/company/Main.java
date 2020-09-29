package com.company;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class Main {

    public static void main(String[] arg) {
        System.out.println("Inga fel!");
    }

}

/**
 * Creates Rational number
 */

class RatNum {

    private int numerator;
    private int denominator; // n/d

    /**
     * Sets numerator = 0, and denominator = 1
     */
    public RatNum() {
        this.numerator = 0;
        this.denominator = 1;
    }


    /**
     * sets denominator to 1, parameter to numerator
     *
     * @param n numerator (int)
     */
    public RatNum(int n) {
        this.numerator = n;
        this.denominator = 1;
    }

    /**
     * Sets the rational number to the String argument, allowed formats are "a/b", "-a/b", "a/-b" or "a".
     *
     * @param s (String)
     */
    public RatNum(String s) {


        this(parse(s));
    }


    /**
     * Sets the rational number to the given int arguments. In the format (numerator) / (denominator)
     *
     * @param numerator
     * @param denominator
     */

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


    /**
     * Copy constructor, creates a new ratnum with the same numerator and denominator as the given argument.
     *
     * @param ratnum
     */
    public RatNum(RatNum ratnum) {
        this.numerator = ratnum.getNumerator();
        this.denominator = ratnum.getDenominator();
    }

    /**
     * Takes String . Returns RatNum with the given parameters.
     * @param frac string parameter written in one of the following forms:  "a/b", "-a/b", "a/-b" or "a"
     */

    public static RatNum parse(String frac) {


        int pLen = frac.length();
        RatNum ratNum;

        if (pLen == 1) {

            ratNum = new RatNum(Integer.parseInt(frac));

        } else {
            int dashPos = frac.indexOf('/');
            String sNumerator = frac.substring(0, dashPos);
            String sDenominator = frac.substring(dashPos + 1, pLen);
            ratNum = new RatNum(Integer.parseInt(sNumerator), Integer.parseInt(sDenominator));
        }

        return ratNum;
    }

    /**
     * Getter method for numerator.
     * @return numerator
     */

    public int getNumerator() {
        return this.numerator;
    }

    /**
     * Getter method for denominator.
     * @return denominator int
     */

    public int getDenominator() {
        return this.denominator;
    }

    /**
     * Setter method for numerator.
     * @param numerator int
     */

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }


    /**
     * Setter method for denominator.
     * @param denominator int
     */

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    /**
     * @param m First integer to compare
     * @param n Second integer to compare
     * @return the greatest common divisor commonly refered as the GCD(m,n)
     */

    static int gcd(int m, int n) {

        int gcd = 1;

        // 0 / 0 is undefined
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

    /**
     * @return The fraction in a String-format
     */
    @Override
    public String toString() {
        return (Integer.toString(this.numerator) + "/" + Integer.toString(this.denominator));
    }


    /**
     * @param r - object which you wanna compare to RatNum
     * @return true if the r is identical to the RatNum
     */
    @Override
    public boolean equals(Object r) {

        // If the object is null it cant be a ratNum
        if (r == null) {
            return false;
        }

        // is r a RatNum?
        if (!(r instanceof RatNum)) {
            return false;
        }

        // Since we know its a ratnum we can typecast r into a ratnum in order to
        // get acess to its methods, without risking errors/exceptions.
        RatNum r2 = (RatNum) r;
        return (this.numerator == r2.getNumerator() && this.denominator == r2.getDenominator());
    }


    /**
     * @param r RatNum you wanna compare
     * @return true if the parameter is smaller than the RatNum which it's being compared to.
     */
    public boolean lessThan(RatNum r) {

        int rExtnum, extNum;
        // extend fractions to the same denominators

        rExtnum = r.getNumerator() * this.getDenominator();
        extNum = this.numerator * r.getDenominator();

        // compare numerators when they share denominators
        if (rExtnum < extNum) {
            return true;
        }
        return false;
    }

    /**
     * @param r
     * @return RatNum sum
     */
    public RatNum add(RatNum r) {

        int sumNumerator, sumDenominator;

        // a/b + c/d = (a*d + c*b) / b*d
        sumDenominator = (this.denominator * r.getDenominator());
        sumNumerator = (this.numerator * r.getDenominator() + r.getNumerator() * this.getDenominator());


        return new RatNum(sumNumerator, sumDenominator);
    }


    /**
     * @param r (RatNum)
     * @return the difference between the RatNum and r as a new RatNum.
     */
    public RatNum sub(RatNum r) {

        int newNum, newDen;

        newNum = ((this.getNumerator() * r.getDenominator()) - (r.getNumerator() * this.getDenominator()));
        newDen = this.getDenominator() * r.getDenominator();

        return new RatNum(newNum, newDen);
    }

    /**
     * Returns the product of The ratnum and the parameter r
     * @param r (RatNum)
     * @return resulting RatNum.
     */
    public RatNum mul(RatNum r) {
        return new RatNum((r.getNumerator() * this.getNumerator()), (r.getDenominator() * this.getDenominator()));
    }

    /**
     * Returns the resulting quota between the RatNum and its parameter
     * @param r (RatNum)
     * @return (RatNum)
     */
    public RatNum div(RatNum r) {
        return new RatNum((this.numerator * r.getDenominator()), (this.denominator * r.getNumerator()));
    }


    /**
     * Returns the rational number in decimal format rounded down
     * with the number of decimals provided as parameter
     * @param decimalCount (Int): Number of decimals
     * @return (String)
     */
    public String toDotString(int decimalCount) {

        String s = "a";
        int temp;

          // removes the least weighted digit
        temp = this.numerator / this.denominator;

        // the denominator will never be negative here since it's already converted,
        // meaning we only have to check the numerator
        if (this.numerator < 0 ) {
            if (temp == 0) { // have to manually concat - since the program may round to zero
                s = "-" + String.valueOf(temp);
            } else {
                s = String.valueOf(temp);
            }
        } else {
            s = String.valueOf(temp);
        }

        if (decimalCount == 0)
            return s;

        // for decimalcount > 0 we need a decimalpoint
        s += ".";

        /*
            algoritm:
            decimalCount - 1 times :

                temp = | numerator / denominator |
                s += toString(temp)
                temp = numerator % denominator
                s += toString(temp)
                temp*=10
         */
        temp = this.numerator % this.denominator;
        temp = Math.abs(temp);
        for (int i = 0; i < decimalCount; i++) {

            temp *= 10;
            s += String.valueOf(temp / this.denominator);
            temp %= this.denominator;

        }
        System.out.println(s);
        return s;
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
            s = s + fraction + "/" + denom;

        return s;


    }

    private static void divTester() {
        // Testar equals och clone
        RatNum x = new RatNum(6, 2);
        RatNum y = new RatNum(0);
        RatNum z = new RatNum(0, 1);
        RatNum w = new RatNum(75, 25);
        Object v = new RatNum(75, 25);
        String str = new String("TEST");

        System.out.println();
        System.out.println(">>>> Test av equals: Vi har inte gått igenom equals ännu ");
        System.out.println("så du behöver inte klara dessa tester än");
        System.out.println("equals test 1 ");
        if (x.equals(y) || !y.equals(z) || !x.equals(w)) {
            System.out.println("RatNumTest3: FEL 1 i equals!!");
        }
        System.out.println("equals test 2 ");
        if (!w.equals(v)) { //  w skall vara lika med v
            // med equals(RatNum r) så väljs dock objects equals
            // eftersom parameterprofilen stämmer där och då blir dom olika
            System.out.println("RatNumTest3: FEL 2 i equals!!");
        }
        System.out.println("equals test 3 ");
        if (!v.equals(w)) { // dyn. bindningen ger RatNums equals
            // men med equals(RatNum r) så blir det som ovan
            System.out.println("RatNumTest3: FEL 3 i equals!!");
        }
        System.out.println("equals test 4 ");
        try {
            if (w.equals(null)) { //skall inte vara lika
                System.out.println("RatNumTest3: FEL 4.1 i equals!!");
            }
        } catch (NullPointerException e) { // men skall klara null
            System.out.println("RatNumTest3: FEL 4.2 i equals!!");
        }
        //System.out.println("equals test 5 ");
        if (w.equals(str)) { // skall ge false
            // med equals(RatNum r) får man återigen Objects equals
            // och den ger rätt svar här
            System.out.println("RatNumTest3: FEL 5 i equals!!");
        }
        System.out.println("<<<< Slut på equals tester");
        RatNum r = new RatNum(1);
        String expected = "";
        int k = 0;
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                expected = "0.500";
                k = 3;
                r = new RatNum(1, 2);
            } else if (i == 1) {
                expected = "-20.0";
                k = 1;
                r = new RatNum(-20, 1);
            } else if (i == 2) {
                expected = "0.666";
                k = 3;
                r = new RatNum(2, 3);
            } else if (i == 3) {
                expected = "0.0001";
                k = 4;
                r = new RatNum(1, 10000);
            } else if (i == 4) {
                expected = "-0.001";
                k = 3;
                r = new RatNum(-11, 10000);
            } else if (i == 5) {
                expected = "104.477";
                k = 3;
                r = new RatNum(7000, 67);
            } else if (i == 6) {
                expected = "0.89";
                k = 2;
                r = new RatNum(89, 99);
            }
            String output = r.toDotString(k);
            if (!output.equals(expected)) {
                System.out.println("RatNumTest3 FEL: toDotString(" + k + ") visar fel för " + r.toString() +
                        "; gav: " + output + "; skulle vara: " + expected);
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
        for (i = 0; i < 3 && sc.hasNext(); i++) {
            a[i] = sc.next();
        }
        if (i != 3 || sc.hasNext())
            return ("Felaktigt uttryck!");
        else {
            try {
                RatNum r1 = RatNum.parse(a[0]);
                String op = a[1];
                char c = op.charAt(0);
                RatNum r2 = new RatNum(a[2]);
                if (op.length() != 1 || "+-*/=<".indexOf(c) < 0)
                    return ("Felaktig operator!");
                else {
                    RatNum res = null;
                    if (c == '+')
                        res = r1.add(r2);
                    else if (c == '-')
                        res = r1.sub(r2);
                    else if (c == '*')
                        res = r1.mul(r2);
                    else if (c == '/')
                        res = r1.div(r2);
                    else if (c == '=')
                        return (Boolean.toString(r1.equals(r2)));
                    else if (c == '<')
                        return (Boolean.toString(r1.lessThan(r2)));
                    if ("+-*/".indexOf(c) >= 0)
                        if (res == null)
                            return ("Fel i add, sub, mul eller div");
                        else
                            return (res.toString());
                }
            } catch (NumberFormatException e) {
                return ("NumberFormatException");
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
            if (!machine) {
                System.out.print("> ");
                System.out.flush();
            }
            if (!in.hasNext()) {
                System.out.println();
                System.exit(0);
            } // no input left
            String s = in.nextLine();
            if (s == null || s.length() == 0) {
                break;
            }
            correctAnswer = "";
            stringToPrint = new StringBuilder();
            if (machine) {
                // split input in question - correct answer
                int i = s.indexOf("-->");
                if (i < 1) {
                    System.out.println("##### Error - No answers found in file - cannot correct");
                    System.exit(0);
                }
                correctAnswer = s.substring(i + 4);
                s = s.substring(0, i);
                //System.out.println("\t##s= " + s + "* correct answer=" + correctAnswer + " debug"); // debug
            }
            String givenAnswer = testExpr(s);
            stringToPrint.append(s + "\t--> " + givenAnswer);
            if (machine && !correctAnswer.equals(givenAnswer)) {
                //System.out.println( "====================================================" );
                //System.out.println("\t##*"+givenAnswer+"* " + "*"+correctAnswer+ "*" + ratNumToString(new RatNum(givenAnswer)) + "* debug"); // debug
                // test if answer given as a/B +
                // strängen till NumberFormatException kan vara olika
                if (correctAnswer.equals(ratNumToString(new RatNum(givenAnswer)))
                        || (correctAnswer.indexOf("NumberFormatException") != -1
                        && givenAnswer.indexOf("NumberFormatException") != -1)
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


