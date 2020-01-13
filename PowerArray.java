/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getlonghighlycompositefast;

/**
 * An array to manipulate 2^a * 3^b * 5^c ... Used to get highly composite
 * numbers
 *
 * @author bridean
 */
public class PowerArray {

    private final int[] min;
    private final int[] max;
    private int[] curr;

    private PowerArray() {
        min = null;
        max = null;
    }

    public PowerArray(int[] lower, int[] upper) {
        min = new int[lower.length];
        max = new int[upper.length];
        curr = new int[lower.length];
        System.arraycopy(lower, 0, min, 0, min.length);
        System.arraycopy(upper, 0, max, 0, max.length);
        System.arraycopy(lower, 0, curr, 0, min.length);
    }

    private boolean isValid() {
        for (int i = 0; i < (curr.length - 1); i++) {
            if (curr[i + 1] > curr[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean getNextArray() {
        int i = curr.length - 1;
        curr[i]++;
        boolean finished = false;
        if (curr[i] > max[i]) {
            finished = false;
        } else {
            if (isValid()) {
                finished = true;
            }
        }
        while (!finished) {
            curr[i] = min[i];
            i--;
            curr[i]++;
            if ((i == 0) && (curr[i] > max[i])) {
                // We have reached the last record
                return false;
            }
            if (curr[i] > max[i]) {
                finished = false;
            } else {
                if (isValid()) {
                    finished = true;
                }
            }
        }
        return true;
    }
    
    public int[] getCurrentArray() {
        int[] retval = new int[curr.length];
        System.arraycopy(curr, 0, retval, 0, curr.length);
        return retval;
    }
}
