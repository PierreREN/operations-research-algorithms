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

package basics.codefactory;

import basics.code.Code;
import basics.code.instances.IntegerCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;
import basics.tools.Ranges;

public class IntegerCodeFactory implements CodeFactory {

    private Ranges ranges;
    private MutationBehavior mutator;
    private ObjectiveFunction objectiveFunction;

    public IntegerCodeFactory(Ranges ranges,
                              MutationBehavior mutator,
                              ObjectiveFunction objectiveFunction) {
        this.ranges = ranges;
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public Code generateCode() {
        return new IntegerCode(ranges, mutator, objectiveFunction);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null) {
            if (object == this) {
                return true;
            } else if (object instanceof IntegerCodeFactory) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        IntegerCodeFactory integerCodeFactory = null;
        try {
            integerCodeFactory = (IntegerCodeFactory) super.clone();
            integerCodeFactory.ranges = ranges;
            integerCodeFactory.mutator = mutator;
            integerCodeFactory.objectiveFunction = objectiveFunction;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return integerCodeFactory;
    }
}