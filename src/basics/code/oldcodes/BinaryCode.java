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

/**
 * @author Pierre REN
 */
public class BinaryCode implements Cloneable {

    private int[] code;

    public BinaryCode(int length) {
        code = new int[length];
    }

    public BinaryCode(int[] a) {
        code = new int[a.length];
        for (int i = 0; i < code.length; i++) {
            code[i] = a[i];
        }
    }

    public static void singlePointCrossover(BinaryCode bc1, BinaryCode bc2) {
        try {
            if (bc1.code.length != bc2.code.length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = bc1.code.length;
            Random r = new Random();
            int cut = 1 + r.nextInt(L - 1);
            for (int i = cut; i < L; i++) {
                int sub = bc1.code[i];
                bc1.code[i] = bc2.code[i];
                bc2.code[i] = sub;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void doublePointCrossover(BinaryCode bc1, BinaryCode bc2) {
        try {
            if (bc1.code.length != bc2.code.length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = bc1.code.length;
            if (L == 1) {
                int sub = bc1.code[0];
                bc1.code[0] = bc2.code[0];
                bc2.code[0] = sub;
            } else {
                Random2Cutting r2c = new Random2Cutting(L);
                int cut1 = r2c.cutting1;
                int cut2 = r2c.cutting2;
                for (int i = cut1; i < cut2; i++) {
                    int sub = bc1.code[i];
                    bc1.code[i] = bc2.code[i];
                    bc2.code[i] = sub;
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        BinaryCode bc1 = new BinaryCode(10);
        bc1.random();
        BinaryCode bc2 = (BinaryCode) bc1.clone();
        System.out.println(bc1.code + " " + bc2.code);
        System.out.println(bc1 + "\n" + bc2);
        for (int i = 0; i < bc1.length(); i++) {
            bc1.code[i] = bc1.code[i] + 1;
        }
        System.out.println(bc1 + "\n" + bc2);
        System.out.println(bc1.toDecimal() + " " + bc2.toDecimal());

    }

    public void random() {
        for (int i = 0; i < code.length; i++) {
            if (Math.random() > 0.5) {
                code[i] = 1;
            } else {
                code[i] = 0;
            }
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

    public double toDecimal() {
        double decimal = 0;
        for (int i = 0; i < code.length; i++) {
            decimal += code[i] * Math.pow(2, code.length - 1 - i);
        }
        return decimal;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof BinaryCode) {
                BinaryCode bc = (BinaryCode) obj;
                if (bc.code.length != code.length) {
                    return false;
                }
                for (int i = 0; i < code.length; i++) {
                    if (bc.code[i] != code[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        BinaryCode bc = new BinaryCode(code);
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
                code[i] = 1 - code[i];
            }
        }
    }
}
