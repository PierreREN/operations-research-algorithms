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
package basics.code.instances;

import basics.code.Code;
import basics.code.CommonCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;
import basics.tools.Ranges;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Pierre REN
 */
public class IntegerCode extends CommonCode implements Code {

    private Ranges ranges;

    public IntegerCode(Ranges ranges, MutationBehavior mutator,
                       ObjectiveFunction objectiveFunction) {
        super(ranges.getLength());
        this.ranges = ranges;
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public void initialize() {
        random();
    }

    private void random() {
        try {
            if (ranges.getUpperLimits() == null && ranges.getLowerLimits() == null) {
                Random r = new Random();
                for (int i = 0; i < array.length; i++) {
                    array[i] = r.nextInt();
                }
            } else if (ranges.getUpperLimits() != null && ranges.getLowerLimits() != null) {
                Random r = new Random();
                for (int i = 0; i < array.length; i++) {
                    if (ranges.getUpperLimits()[i] <= ranges.getLowerLimits()[i]) {
                        String s = "Illegal bounds for ranges.getLowerLimits()[" + i + "] and ranges.getUpperLimits()[" + i + "].";
                        throw new Exception(s);
                    }
                    array[i] = ranges.getLowerLimits()[i] + r.nextInt(ranges.getUpperLimits()[i] - ranges.getLowerLimits()[i]);
                }
            } else {
                throw new Exception("Wrong dimensions of AbstractClasses, lowerlimits and upperlimits.");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof IntegerCode) {
                IntegerCode ic = (IntegerCode) obj;
                return Arrays.equals(this.array, ic.array)
                        && this.ranges.equals(ic.ranges);
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        IntegerCode ic = (IntegerCode) super.clone();
        ic.ranges = (Ranges) this.ranges.clone();
        return ic;
    }
}
