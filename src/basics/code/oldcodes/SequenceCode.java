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
import basics.tools.Random2Index;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pierre REN
 */
public class SequenceCode implements Cloneable {

    private int[] code;

    public SequenceCode(int length) {
        code = new int[length];
    }

    public SequenceCode(int length, int value) {
        code = new int[length];
        for (int i = 0; i < length; i++) {
            code[i] = value;
        }
    }

    public SequenceCode(int[] a) {
        code = new int[a.length];
        for (int i = 0; i < code.length; i++) {
            code[i] = a[i];
        }
    }

    /**
     * @param sc1 the value of sc1
     * @param sc2 the value of sc2
     */
    public static void orderCrossover(SequenceCode sc1, SequenceCode sc2) {
        try {
            if (sc1.code.length != sc2.code.length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = sc1.code.length;
            Random2Cutting r = new Random2Cutting(L);
            int cut1 = r.cutting1;
            int cut2 = r.cutting2;
            //cut2-cut1的新染色体中
            SequenceCode cutPiece1 = new SequenceCode(cut2 - cut1);
            SequenceCode cutPiece2 = new SequenceCode(cut2 - cut1);
            for (int i = 0; i < cut2 - cut1; i++) {
                cutPiece1.code[i] = sc1.code[i + cut1];
                cutPiece2.code[i] = sc2.code[i + cut1];
            }
            //从第二个切点后按原顺序去掉已有基因，将未重复的基因按顺序插入
            int j = cut2 == L ? 0 : cut2;
            int k = j;
            //cut2后至尾部
            for (int i = cut2; i < L; i++) {
                while (cutPiece2.contains(sc1.code[j])) {
                    j = (j + 1 == L) ? 0 : j + 1;
                }
                sc1.code[i] = sc1.code[j];
                j = (j + 1 == L) ? 0 : j + 1;
                while (cutPiece1.contains(sc2.code[k])) {
                    k = (k + 1 == L) ? 0 : k + 1;
                }
                sc2.code[i] = sc2.code[k];
                k = (k + 1 == L) ? 0 : k + 1;
            }
            //头部至cut1
            for (int i = 0; i <= cut1; i++) {
                while (cutPiece2.contains(sc1.code[j])) {
                    j = (j + 1 == L) ? 0 : j + 1;
                }
                sc1.code[i] = sc1.code[j];
                j = (j + 1 == L) ? 0 : j + 1;
                while (cutPiece1.contains(sc2.code[k])) {
                    k = (k + 1 == L) ? 0 : k + 1;
                }
                sc2.code[i] = sc2.code[k];
                k = (k + 1 == L) ? 0 : k + 1;
            }
            //补全交叉位置上的基因
            for (int i = 0; i < cut2 - cut1; i++) {
                sc1.code[i + cut1] = cutPiece2.code[i];
                sc2.code[i + cut1] = cutPiece1.code[i];
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * @param sc1 the value of sc1
     * @param sc2 the value of sc2
     */
    public static void partiallyMappedCrossover(SequenceCode sc1, SequenceCode sc2) {
        try {
            if (sc1.code.length != sc2.code.length) {
                throw new Exception("The lengths of codes are not equal.");
            }
            int L = sc1.code.length;
            Random2Cutting r2c = new Random2Cutting(L);
            int cut1 = r2c.cutting1;
            int cut2 = r2c.cutting2;
            //System.out.println(cutting1+" "+cutting2);
            SequenceCode cutPiece1 = new SequenceCode(cut2 - cut1);
            SequenceCode cutPiece2 = new SequenceCode(cut2 - cut1);
            for (int i = 0; i < cut2 - cut1; i++) {
                cutPiece1.code[i] = sc1.code[i + cut1];
                cutPiece2.code[i] = sc2.code[i + cut1];
                sc1.code[i + cut1] = cutPiece2.code[i];
                sc2.code[i + cut1] = cutPiece1.code[i];
            }
            //按照交叉产生的映射关系将重复的位置换掉，而交换的子串部分保持不变。
            for (int i = 0; i < cut1; i++) {
                int index1 = cutPiece2.index(sc1.code[i]);
                while (index1 >= 0) {
                    sc1.code[i] = cutPiece1.code[index1];
                    index1 = cutPiece2.index(sc1.code[i]);
                }
                int index2 = cutPiece1.index(sc2.code[i]);
                while (index2 >= 0) {
                    sc2.code[i] = cutPiece2.code[index2];
                    index2 = cutPiece1.index(sc2.code[i]);
                }
            }
            for (int i = cut2; i < L; i++) {
                int index1 = cutPiece2.index(sc1.code[i]);
                while (index1 >= 0) {
                    sc1.code[i] = cutPiece1.code[index1];
                    index1 = cutPiece2.index(sc1.code[i]);
                }
                int index2 = cutPiece1.index(sc2.code[i]);
                while (index2 >= 0) {
                    sc2.code[i] = cutPiece2.code[index2];
                    index2 = cutPiece1.index(sc2.code[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    /**
     * @param sc1 the value of sc1
     * @param sc2 the value of sc2
     */
    public static void cycleCrossover(SequenceCode sc1, SequenceCode sc2) {
        try {
            if (sc1.code.length != sc2.code.length || sc1.length() == 0 || sc2.length() == 0) {
                System.out.println(sc1.toString() + " " + sc2.toString());
                throw new Exception("The lengths of codes are not equal.");
            }
            int length = sc1.code.length;
            SequenceCode newPiece1 = new SequenceCode(length);
            SequenceCode newPiece2 = new SequenceCode(length);
            int i = 0;
            int order = 0;
            while (i >= 0) {
                int j = 0;
                switch (order) {
                    case 0:
                        newPiece1.code[i] = sc1.code[i];
                        newPiece2.code[i] = sc2.code[i];
                        j = sc1.index(sc2.code[i]);
                        while (j != i) {
                            newPiece1.code[j] = sc1.code[j];
                            newPiece2.code[j] = sc2.code[j];
                            j = sc1.index(sc2.code[j]);
                        }
                        i = newPiece2.firstLeftZero();
                        order = 1 - order;
                        break;
                    case 1:
                        newPiece1.code[i] = sc2.code[i];
                        newPiece2.code[i] = sc1.code[i];
                        j = sc2.index(sc1.code[i]);
                        while (j != i) {
                            newPiece1.code[j] = sc2.code[j];
                            newPiece2.code[j] = sc1.code[j];
                            j = sc2.index(sc1.code[j]);
                        }
                        i = newPiece1.firstLeftZero();
                        order = 1 - order;
                        break;
                }
            }
            sc1.code = newPiece1.code;
            sc2.code = newPiece2.code;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        for (int i = 0; i < 100; i++) {
            SequenceCode sc1 = new SequenceCode(2);
            sc1.random();
            SequenceCode sc2 = (SequenceCode) sc1.clone();
//            System.out.println(sc1.toString()+sc1.testSequence()+"\n"+sc2.toString()+sc2.testSequence());
//            partiallyMappedCrossover(sc1,sc2);
//            System.out.println(sc1.toString()+sc1.testSequence()+"\n"+sc2.toString()+sc2.testSequence());
//            orderCrossover(sc1,sc2);
//            System.out.println(sc1.toString()+sc1.testSequence()+"\n"+sc2.toString()+sc2.testSequence());
            cycleCrossover(sc1, sc2);
            System.out.println(sc1.toString() + sc1.testSequence() + "\n" + sc2.toString() + sc2.testSequence());
        }
//        SequenceCode sc1=new SequenceCode(10);
//        sc1.random();
//        SequenceCode sc2=(SequenceCode)sc1.clone();
//        System.out.println(sc1.AbstractClasses+" "+sc2.AbstractClasses);
//        System.out.println(sc1+"\n"+sc2);
//        for (int i=0;i<sc1.length();i++){
//            sc1.AbstractClasses[i]=sc1.AbstractClasses[i]+1;
//        }
//        System.out.println(sc1+"\n"+sc2);
    }

    public void random() {
        LinkedList<Integer> list = new LinkedList();
        for (int i = 0; i < code.length; i++) {
            list.add(i + 1);
        }
        Random r = new Random();
        for (int i = 0; i < code.length; i++) {
            int index = r.nextInt(list.size());
            code[i] = list.get(index);
            list.remove(index);
        }
    }

    //    public void random() {
//        for (int i = 0; i < AbstractClasses.length; i++) {
//            AbstractClasses[i] = randomSequenceValue(i);
//        }
//    }
//
//    private int randomSequenceValue(int i) {
//        Random r = new Random();
//        int R = 1 + r.nextInt(AbstractClasses.length);
//        for (int j = 0; j < i; j++) {
//            if (AbstractClasses[j] == R) {
//                return randomSequenceValue(i);
//            }
//        }
//        return R;
//    }
    public boolean testSequence() {
        for (int i = 0; i < code.length - 1; i++) {
            for (int j = i + 1; j < code.length; j++) {
                if (code[i] == code[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean contains(int x) {
        for (int value : code) {
            if (value == x) {
                return true;
            }
        }
        return false;
    }

    public int index(int x) {
        for (int i = 0; i < code.length; i++) {
            if (code[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public int firstLeftZero() {
        for (int i = 0; i < code.length; i++) {
            if (code[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public int length() {
        return code.length;
    }

    public int[] getCode() {
        return code;
    }

    public int sum() {
        int sum = 0;
        int m = 1;
        for (int i = code.length - 1; i >= 0; i--) {
            sum += code[i] * m;
            m = m * 10;
        }
        return sum;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof SequenceCode) {
                SequenceCode sc = (SequenceCode) obj;
                if (sc.code.length != code.length) {
                    return false;
                }
                for (int i = 0; i < code.length; i++) {
                    if (sc.code[i] != code[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        SequenceCode bc = null;
        try {
            bc = (SequenceCode) super.clone();
            bc = new SequenceCode(code);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SequenceCode.class.getName()).log(Level.SEVERE, null, ex);
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

    public void shiftMutation(double Pm) {
        if (Math.random() < Pm) {
            Random r = new Random();
            int index = 1 + r.nextInt(code.length - 1);
            int code_0 = code[index];
            for (int i = index; i > 0; i--) {
                code[i] = code[i - 1];
            }
            code[0] = code_0;
        }
    }

    public void transpositionMutation(double Pm) {
        if (Math.random() < Pm && code.length > 1) {
            Random2Index r = new Random2Index(code.length);
            int code_2 = code[r.index2];
            code[r.index2] = code[r.index1];
            code[r.index1] = code_2;
        }
    }
}
