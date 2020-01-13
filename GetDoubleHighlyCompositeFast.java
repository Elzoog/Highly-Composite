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
public class GetDoubleHighlyCompositeFast {

    private static Primes primes = new Primes(1000);
    // To handle the case where the next hc is exactly 2 times the previous one
    // make log2 slightly greater than the actual value
    private static final double log2 = 0.693147181;
    
    private static int[] getMinArray(double lognum) {
        // Try to get the minimum and maximum power arrays
        int i = -1;
        double maxlog = 0.0;
        // Get the maximum possible prime number assuming all powers of one
        double testlog = lognum;
        while (maxlog < testlog) {
            i++;
            maxlog += primes.getLogPrime(i);
        }
        int maxprloc = i - 1;
        int maxpr = primes.getPrime(maxprloc);
        // Get a better estimate of maxpr given Ramanujan's formula
        i = -1;
        maxlog = 0.0;
        // if maxlong == testlog I want it to loop one more time
        while (!(maxlog > testlog)) {
            i++;
            int lowpow = (int) (primes.getLogPrime(maxprloc) / primes.getLogPrime(i));
            if (lowpow == 0) {
                // This can occur if the values are small
                maxlog = testlog + 1.0;
            }
            maxlog += (primes.getLogPrime(i) * lowpow);
        }
        maxprloc = i - 1;
        maxpr = primes.getPrime(maxprloc);
        // Get the first prime that could have a power of zero
        maxlog = 0.0;
        i = -1;
        while (maxlog < testlog) {
            i++;
            int lowpow = (int) (primes.getLogPrime(maxprloc) / primes.getLogPrime(i));
            if (lowpow == 1) {
                lowpow = 2;
            }
            if (lowpow == 0) {
                maxlog = testlog;
                continue;
            }
            maxlog += (primes.getLogPrime(i) * lowpow);
        }
        // For 2 * 10^50 this would be p(18) = 67
        int firstzero = i;
        // Generate the minimum array
        int[] minarr = new int[maxprloc+1];
        for (i = 0; i <= maxprloc; i++) {
            int lowpow = (int) (primes.getLogPrime(maxprloc) / primes.getLogPrime(i));
            if (i < firstzero) {
                minarr[i] = lowpow;
            } else {
                minarr[i] = 0;
            }
        }
        return minarr;        
    }
    
    private static CompositeNumber getNextHC(CompositeNumber hc) {
        double logtwonum = hc.getLogNum() + log2;
        double lognum = hc.getLogNum();
        double numdivs = hc.getNumDivisors();
        int[] minarr = getMinArray(logtwonum);
        int[] maxarr = new int[minarr.length];
        double logm = Primes.getLogPrime(minarr.length);
        for (int i=0; i<minarr.length; i++) {
            if (minarr[i] > 0) {
                double lnum = logm/Primes.getLogPrime(i);
                int highpow = ((int) lnum) * 2;
                maxarr[i] = highpow;
            } else {
                maxarr[i] = 1;
            }
        }
        PowerArray parr = new PowerArray(minarr, maxarr);
        double bestlog = Double.MAX_VALUE;
        int[] bestarr = new int[minarr.length];
        Factorization fac = null;
        Factorization bestfac = null;
        double bestnumdivs = 0;
        while (parr.getNextArray()) {
            int[] carray = parr.getCurrentArray();
            fac = new Factorization(carray);
            double clog = fac.getLogNum();
            double cdivs = fac.getNumDivisors();
            if (cdivs == 0) {
                return new CompositeNumber(0.0, 0, null);
            }
            if ((clog > lognum) && (clog < logtwonum)) {
                if (cdivs > numdivs) {
                    if (clog < bestlog) {
                        bestlog = clog;
                        bestnumdivs = cdivs;
                        bestfac = fac;
                    }
                }
            }
        }
        return new CompositeNumber(bestlog, bestnumdivs, bestfac);
    }

    public static void main(String[] args) {
        // Get the first few highly composites using the slow method
        long test = 1;
        double numdivs = 1;
        Factorization fac = null;
        double numsecs = Double.parseDouble(args[0]);

        long starttime = System.currentTimeMillis();
        for (int i = 2; i < 1000; i++) {
            fac = Factorization.getFactorization(i);
            double tmpdivs = fac.getNumDivisors();
            if (tmpdivs > numdivs) {
                System.out.print(i + " " + tmpdivs + " ");
                System.out.println(fac.toString());
                System.out.flush();
                test = i;
                numdivs = tmpdivs;
            }
        }
        // Now use the fast method
        CompositeNumber hc = new CompositeNumber(Math.log(test), numdivs, fac);
        boolean finished = false;
        while (!finished) {
            CompositeNumber nexthc = getNextHC(hc);
            if (nexthc.isValid()) {
                System.out.println(nexthc.toString());
                hc = nexthc;
            } else {
                finished = true;
            }
            // Run for 1 hour
            long endtime = System.currentTimeMillis();
            double tmpnumsecs = (double) (endtime - starttime);
            tmpnumsecs = tmpnumsecs/1000.0;
            if (tmpnumsecs > numsecs) {
                finished = true;
            }
        }
        long endtime = System.currentTimeMillis();
        double tmpnumsecs = (double) (endtime - starttime);
        tmpnumsecs = tmpnumsecs/1000.0;
        System.out.println("That took " + tmpnumsecs + " seconds");
    }
}
