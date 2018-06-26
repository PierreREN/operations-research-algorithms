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
package simulatedannealing.code;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import tool.Random2Index;

/**
 *
 * @author Pierre REN
 */
public class SequenceCode implements Code, Cloneable {

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

    @Override
    public int getValue(int index) {
        return code[index];
    }

    @Override
    public void setValue(int index, int value) {
        code[index] = value;
    }

    @Override
    public void initialize() {
        random();
    }

    protected void random() {
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

    @Override
    public Code getNextSolution() {
        try {
            Code nextSolution = (Code) this.clone();
            Random2Index r2i = new Random2Index(code.length);
            int index1 = r2i.index1;
            int index2 = r2i.index2;
            int sub = nextSolution.getValue(index1);
            nextSolution.setValue(index1, nextSolution.getValue(index2));
            nextSolution.setValue(index2, sub);
            return nextSolution;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SequenceCode.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public double objectiveFunctionValue() {
        double value = 0;
        int[] processingTime = {8, 18, 5, 15};
        int n = code.length;
        for (int i = 0; i < code.length; i++) {
            value += processingTime[code[i] - 1] * n--;
        }
        return value;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        SequenceCode sc = (SequenceCode) super.clone();
        sc.code = code.clone();
        return sc;
    }

    public String toString() {
        String s = "[";
        for (int i = 0; i < code.length - 1; i++) {
            s = s + code[i] + ",";
        }
        s = s + code[code.length - 1] + "]";
        return s;
    }
}
