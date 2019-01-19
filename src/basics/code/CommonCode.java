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
                if (cc.mutator != mutator ||
                        cc.objectiveFunction != objectiveFunction) {
                    System.out.println("MUTATOR OR OBJECTIVEFUNCTION");
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        CommonCode bc = (CommonCode) super.clone();
        bc.array = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            bc.array[i] = array[i];
        }
        bc.mutator = mutator;
        bc.objectiveFunction = objectiveFunction;
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
