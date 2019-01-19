/*
 * Copyright 2018 Pierre REN.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package basics.code.oldcodes;

import basics.tools.Random2Cutting;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pierre REN
 */
public class IntegerCode implements Cloneable {

    private int[] code;
    private int[] lowerLimits;
    private int[] upperLimits;

    public IntegerCode(int length) {
        code = new int[length];
    }

    public IntegerCode(int[] a) {
        code = new int[a.length];
        for (int i = 0; i < code.length; i++) {
            code[i] = a[i];
        }
    }

    public static void singlePointCrossover(IntegerCode ic1, IntegerCode ic2) {
        try {
            if (ic1.code.length != ic2.code.length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = ic1.code.length;
            Random r = new Random();
            int cut = 1 + r.nextInt(L - 1);
            for (int i = cut; i < L; i++) {
                int sub = ic1.code[i];
                ic1.code[i] = ic2.code[i];
                ic2.code[i] = sub;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void doublePointCrossover(IntegerCode ic1, IntegerCode ic2) {
        try {
            if (ic1.code.length != ic2.code.length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = ic1.code.length;
            if (L == 1) {
                int sub = ic1.code[0];
                ic1.code[0] = ic2.code[0];
                ic2.code[0] = sub;
            } else {
                Random2Cutting r2c = new Random2Cutting(L);
                int cut1 = r2c.cutting1;
                int cut2 = r2c.cutting2;
                for (int i = cut1; i < cut2; i++) {
                    int sub = ic1.code[i];
                    ic1.code[i] = ic2.code[i];
                    ic2.code[i] = sub;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        int[] a = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] b = new int[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        IntegerCode c1 = new IntegerCode(a.length);
        c1.setBound(1, 9);
        IntegerCode c2 = new IntegerCode(a.length);
        c2.setBound(1, 9);
        c1.random();
        c2.random();
        System.out.println(c1 + "\n" + c2);
        c2 = (IntegerCode) c1.clone();
        System.out.println(c1 + "\n" + c2);
        for (int i = 0; i < c1.length(); i++) {
            c1.code[i] += 1;
        }
        System.out.println(c1 + "\n" + c2);
        System.out.println(c1.code + " " + c2.code);
    }

    public void setBound(int Nmax) {
        lowerLimits = new int[code.length];
        upperLimits = new int[code.length];
        for (int i = 0; i < code.length; i++) {
            upperLimits[i] = Nmax;
        }
    }

    public void setBound(int Nmin, int Nmax) {
        lowerLimits = new int[code.length];
        upperLimits = new int[code.length];
        for (int i = 0; i < code.length; i++) {
            lowerLimits[i] = Nmin;
            upperLimits[i] = Nmax + 1;
        }
    }

    public void setBound(int[] Nmax) {
        try {
            if (code.length != Nmax.length) {
                throw new Exception("Inequal lengths of AbstractClasses and bound");
            }
            lowerLimits = new int[Nmax.length];
            upperLimits = new int[Nmax.length];
            for (int i = 0; i < Nmax.length; i++) {
                upperLimits[i] = Nmax[i];
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void setBound(int[] Nmin, int[] Nmax) {
        try {
            if ((code.length != Nmax.length) || (code.length != Nmin.length)) {
                throw new Exception("Inequal lengths of AbstractClasses and bound");
            }
            lowerLimits = new int[Nmax.length];
            upperLimits = new int[Nmax.length];
            for (int i = 0; i < Nmax.length; i++) {
                lowerLimits[i] = Nmin[i];
                upperLimits[i] = Nmax[i] + 1;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void random() {
        try {
            if (upperLimits == null && lowerLimits == null) {
                Random r = new Random();
                for (int i = 0; i < code.length; i++) {
                    code[i] = r.nextInt();
                }
            } else if (upperLimits != null && lowerLimits != null) {
                Random r = new Random();
                for (int i = 0; i < code.length; i++) {
                    if (upperLimits[i] <= lowerLimits[i]) {
                        String s = "Illegal bounds for lowerLimits[" + i + "] and upperLimits[" + i + "].";
                        throw new Exception(s);
                    }
                    code[i] = lowerLimits[i] + r.nextInt(upperLimits[i] - lowerLimits[i]);
                }
            } else {
                throw new Exception("Wrong dimensions of AbstractClasses, lowerlimits and upperlimits.");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public int length() {
        return code.length;
    }

    public int[] getCode() {
        return code;
    }

    public int sum() {
        int sum = 0;
        for (int i = 0; i < code.length; i++) {
            sum += code[i];
        }
        return sum;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof IntegerCode) {
                IntegerCode ic = (IntegerCode) obj;
                if (ic.code.length != code.length) {
                    return false;
                }
                for (int i = 0; i < code.length; i++) {
                    if (ic.code[i] != code[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        IntegerCode bc = null;
        try {
            bc = (IntegerCode) super.clone();
            bc.code = new int[code.length];
            bc.lowerLimits = new int[code.length];
            bc.upperLimits = new int[code.length];
            for (int i = 0; i < code.length; i++) {
                bc.code[i] = code[i];
                bc.lowerLimits[i] = lowerLimits[i];
                bc.upperLimits[i] = upperLimits[i];
            }

        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(IntegerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bc;
    }

    public String toString() {
        String s = "[";
        for (int i = 0; i < code.length - 1; i++) {
            s = s + code[i] + ",";
        }
        s = s + code[code.length - 1] + "]";
        return s;
    }

    public void mutation(double Pm) {
        for (int i = 0; i < code.length; i++) {
            if (Math.random() < Pm) {
                Random r = new Random();
                code[i] = lowerLimits[i] + r.nextInt(upperLimits[i] - lowerLimits[i]);
            }
        }
    }
}
