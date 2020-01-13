package getlonghighlycompositefast;

import java.math.BigInteger;

/**
 * A class to store primes and other related information.
 *
 * @author bridean
 */
public class Primes {

    private static int nprimes;
    private static int[] primes;
    private static double[] logprimes;
    // These are all of the primordials that will fit in long
    private static final long[] primordials = {2, 6, 30, 210, 2310, 30030,
        510510, 9699690, 223092870, 6469693230l, 200560490130l, 7420738134810l,
        304250263527210l, 13082761331670030l, 614889782588491410l};

    private Primes() {
        // Don't use the default constructor
        nprimes = 10;
        int[] tmpprs = getInternalPrimes(10);
        double[] tmplogs = getLogPrimes(tmpprs);
        primes = tmpprs;
        logprimes = tmplogs;
    }

    /**
     * Generate and store the first num prime numbers. Also, store the logarithm
     * of those numbers.
     *
     * @param num
     */
    public Primes(int num) {
        // The minimum number of prime numbers stored will be 10. Change if needed
        int min = 10;
        if (num < min) {
            nprimes = min;
        } else {
            nprimes = num;
        }
        int[] tmpprs = getInternalPrimes(nprimes);
        double[] tmplogs = getLogPrimes(tmpprs);
        primes = tmpprs;
        logprimes = tmplogs;
    }

    private static int[] getInternalPrimes(int var) {
        int[] retPrimes = new int[var];
        int testnum, mod3int, loc;

        retPrimes[0] = 2;
        retPrimes[1] = 3;
        retPrimes[2] = 5;
        retPrimes[3] = 7;
        loc = 4;
        testnum = 7;
        mod3int = testnum % 3;
        while (loc < var) {
            if (mod3int == 1) {
                testnum += 4;
                mod3int = 2;
            } else {
                testnum += 2;
                mod3int = 1;
            }
            boolean isprime = true;
            int tmploc = 0;
            int testdiv = retPrimes[tmploc];
            while ((testdiv * testdiv) < (testnum + 1)) {
                if ((testnum % testdiv) == 0) {
                    isprime = false;
                }
                tmploc++;
                testdiv = retPrimes[tmploc];
            }
            if (isprime) {
                retPrimes[loc] = testnum;
                loc++;
            }
        }
        return retPrimes;
    }

    private static double[] getLogPrimes(int[] pr) {
        double retval[] = new double[pr.length];
        for (int i = 0; i < pr.length; i++) {
            retval[i] = Math.log((double) pr[i]);
        }
        return retval;
    }

    public static int[] getPrimes(int var) {
        int[] retval;
        if (var < primes.length) {
            retval = new int[var];
            for (int i = 0; i < var; i++) {
                retval[i] = primes[i];
            }
        } else {
            retval = getInternalPrimes(var);
        }
        return retval;
    }

    public static int getNumPrimes() {
        return primes.length;
    }

    public static int getPrime(int loc) {
        return primes[loc];
    }

    public static double getLogPrime(int loc) {
        return logprimes[loc];
    }

    public static long[] getPrimordials() {
        long[] retval = new long[primordials.length];
        for (int i = 0; i < primordials.length; i++) {
            retval[i] = primordials[i];
        }
        return retval;
    }

    public static BigInteger getPrimordial(int num) {
        BigInteger retval = BigInteger.ONE;
        if (num < primordials.length) {
            return BigInteger.valueOf(primordials[num]);
        } else {
            for (int i = 0; i <= num; i++) {
                retval = retval.multiply(BigInteger.valueOf(primes[i]));
            }
            return retval;
        }
    }

    /**
     * Get the prime factorization of var in terms of an array. For example, if
     * the returned array is {2, 1, 4} this means that the prime factorization
     * of var is 2^2 * 3^1 * 5^4. This algorithm will not find prime factors
     * that are not in the prime list.
     *
     * @param var
     * @return
     */
    private int[] getfactorization(long var) {
        long tmpvar = var;
        int[] facs = new int[primes.length];
        // A prime factorization where not all of the primes listed are used
        // should never happen
        int loc = 0;
        while (tmpvar > 1) {
            int cpr = primes[loc];
            while ((tmpvar % cpr) == 0) {
                tmpvar = tmpvar / cpr;
                facs[loc]++;
            }
            loc++;
        }
        return facs;
    }

    public static boolean isPrime(long num) {
        for (int i = 0; i < 10; i++) {
            if (num == primes[i]) {
                return true;
            }
        }
        int sqrtnum = (int) (Math.sqrt((double) num) + 1.0);
        int i = 0;
        int currpr = primes[i];
        while (currpr < sqrtnum) {
            if ((num % currpr) == 0) {
                return false;
            }
            i++;
            if (i > (primes.length - 1)) {
                break;
            }
            currpr = primes[i];
        }
        if (i < primes.length) {
            return true;
        }
        // num is too large for trial division
        BigInteger bint = BigInteger.valueOf(num);
        boolean retval = bint.isProbablePrime(10);
        return retval;
    }
}
