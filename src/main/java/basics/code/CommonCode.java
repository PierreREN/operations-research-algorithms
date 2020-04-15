/*
 * Copyright 2019 Pierre REN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package basics.code;

import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;

public abstract class CommonCode implements Code {

    protected int[] array;

    protected MutationBehavior mutator;

    protected ObjectiveFunction objectiveFunction;

    public CommonCode(int length) {
        array = new int[length];
    }

    public CommonCode(int length, int value) {
        array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = value;
        }
    }

    public CommonCode(int[] a) {
        array = new int[a.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = a[i];
        }
    }

    @Override
    public int[] getArray() {
        return array;
    }

    @Override
    public void mutate() {
        mutator.mutate(array);
    }

    @Override
    public double objectiveFunctionValue() {
        return objectiveFunction.evaluate(array);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof CommonCode) {
                CommonCode cc = (CommonCode) obj;
                if (cc.array.length != array.length) {
                    return false;
                }
                for (int i = 0; i < array.length; i++) {
                    if (cc.array[i] != array[i]) {
                        return false;
                    }
                }
                return cc.mutator == mutator &&
                        cc.objectiveFunction == objectiveFunction;
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        CommonCode bc = null;
        try {
            bc = (CommonCode) super.clone();
            bc.array = new int[array.length];
            System.arraycopy(array, 0, bc.array, 0, array.length);
            bc.mutator = mutator;
            bc.objectiveFunction = objectiveFunction;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bc;
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < array.length - 1; i++) {
            s = s + array[i] + ",";
        }
        s = s + array[array.length - 1] + "]";
        return s;
    }
}
