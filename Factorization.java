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
public class Factorization {
    private final int[] factors;

    private Factorization() {
        factors = null;
    }

    public static Factorization getFactorization(long num) {
        long tmpnum = num;
        int i = 0;
        int[] tmpfacs = new int[Primes.getNumPrimes()];
        while ((i < Primes.getNumPrimes()) && (tmpnum > 1)) {
            int pow = 0;
            int pr = Primes.getPrime(i);
            while ((tmpnum % pr) == 0) {
                tmpnum = tmpnum/pr;
                pow++;
            }
            tmpfacs[i] = pow;
            i++;
        }
        int last = Primes.getNumPrimes() - 1;
        while (tmpfacs[last] == 0) {
            last--;
        }
        int[] retval = new int[last+1];
        for (i=0; i<(last+1); i++) {
            retval[i] = tmpfacs[i];
        }
        return new Factorization(retval);
    }

    public Factorization(int[] facs) {
        factors = new int[facs.length];
        System.arraycopy(facs, 0, factors, 0, facs.length);
    }

    public double getLogNum() {
        double retval = 0.0;

        for (int i=0; i<factors.length; i++) {
            int pr = Primes.getPrime(i);
            retval += Math.log((double) pr) * factors[i];
        }
        return retval;
    }

    public double getNumDivisors() {
        double retval = 1.0;

        for (int i=0; i<factors.length; i++) {
            retval = retval * (factors[i] + 1);
        }
        return retval;
    }

    @Override
    public String toString() {
        String retstr = "";
        for (int i = 0; i < factors.length; i++) {
            if (factors[i] > 0) {
                retstr += factors[i] + " ";
            }
        }
        return retstr;
    }
}
