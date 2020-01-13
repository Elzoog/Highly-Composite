/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getlonghighlycompositefast;

/**
 *
 * @author bridean
 */
public class CompositeNumber {

    private final double lognum;
    private final double numdivs;
    private final Factorization fac;

    private CompositeNumber() {
        lognum = 0.0;
        numdivs = 0;
        fac = null;
    }

    public CompositeNumber(double lnum, double divs, Factorization facs) {
        lognum = lnum;
        numdivs = divs;
        fac = facs;
    }

    public double getLogNum() {
        return lognum;
    }

    public double getNumDivisors() {
        return numdivs;
    }

    /*
    * This allows for the possiblity of making a CompositeNumber invalid
    * by setting the number of divisors to zero or one.
     */
    public boolean isValid() {
        return (numdivs > 1);
    }

    public Factorization getFactorization() {
        return fac;
    }

    @Override
    public String toString() {
        String retstr = "";
        if (isValid()) {
            double num = Math.exp(lognum);
            if (num < (double) Long.MAX_VALUE) {
                long lnum = (long) (num + 0.5);
                retstr = lnum + " ";
            } else {
                retstr = Math.exp(lognum) + " ";
            }
            if (numdivs < (double) Long.MAX_VALUE) {
                long lnum = (long) (numdivs + 0.5);
                retstr += lnum + " ";
            } else {
                retstr += numdivs + " ";
            }
            retstr += fac.toString();
        } else {
            retstr = "invalid";
        }
        return retstr;
    }
}
