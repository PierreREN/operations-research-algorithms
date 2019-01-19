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
package basics.code.instances;

import basics.code.CommonCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.mutationbehaviors.ShiftMutation;
import basics.mutationbehaviors.TransposeMutation;

import java.util.Random;

/**
 * @author Pierre REN
 */
public class OneIntegerCode extends CommonCode {

    private MutationBehavior shift = new ShiftMutation();
    private MutationBehavior transpose = new TransposeMutation();

    private int[] lowerLimits;
    private int[] upperLimits;

    public OneIntegerCode(int length) {
        super(length);
    }

    public OneIntegerCode(int length, int value) {
        super(length, value);
    }

    public OneIntegerCode(int[] a) {
        super(a);
    }

    public void setBound(int Nmax) {
        lowerLimits = new int[array.length];
        upperLimits = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            upperLimits[i] = Nmax;
        }
    }

    public void setBound(int Nmin, int Nmax) {
        lowerLimits = new int[array.length];
        upperLimits = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            lowerLimits[i] = Nmin;
            upperLimits[i] = Nmax + 1;
        }
    }

    public void setBound(int[] Nmax) {
        try {
            if (array.length != Nmax.length) {
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
            if ((array.length != Nmax.length) || (array.length != Nmin.length)) {
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
                for (int i = 0; i < array.length; i++) {
                    array[i] = r.nextInt();
                }
            } else if (upperLimits != null && lowerLimits != null) {
                Random r = new Random();
                for (int i = 0; i < array.length; i++) {
                    if (upperLimits[i] <= lowerLimits[i]) {
                        String s = "Illegal bounds for lowerLimits[" + i + "] and upperLimits[" + i + "].";
                        throw new Exception(s);
                    }
                    array[i] = lowerLimits[i] + r.nextInt(upperLimits[i] - lowerLimits[i]);
                }
            } else {
                throw new Exception("Wrong dimensions of AbstractClasses, lowerlimits and upperlimits.");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof OneIntegerCode) {
                OneIntegerCode ic = (OneIntegerCode) obj;
                if (ic.array.length != array.length) {
                    return false;
                }
                for (int i = 0; i < array.length; i++) {
                    if (ic.array[i] != array[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        OneIntegerCode bc = (OneIntegerCode) super.clone();
        bc.lowerLimits = new int[array.length];
        bc.upperLimits = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            bc.lowerLimits[i] = lowerLimits[i];
            bc.upperLimits[i] = upperLimits[i];
        }
        return bc;
    }

    public void mutation(double Pm) {
        for (int i = 0; i < array.length; i++) {
            if (Math.random() < Pm) {
                Random r = new Random();
                array[i] = lowerLimits[i] + r.nextInt(upperLimits[i] - lowerLimits[i]);
            }
        }
    }

    @Override
    public void initialize() {
        random();
    }

    @Override
    public void mutate() {
        Random r = new Random();
        switch (r.nextInt(3)) {
            case 0:
                mutation(1);
                break;
            case 1:
                shift.mutate(array);
                break;
            case 2:
                transpose.mutate(array);
                break;
        }
    }

    @Override
    public double objectiveFunctionValue() {
        return 0;
    }
}
