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
import basics.code.instances.SequenceCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;

public class SequenceCodeFactory implements CodeFactory {

    private int codeLength;
    private MutationBehavior mutator;
    private ObjectiveFunction objectiveFunction;

    public SequenceCodeFactory(int codeLength,
                               MutationBehavior mutator,
                               ObjectiveFunction objectiveFunction) {
        this.codeLength = codeLength;
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public Code generateCode() {
        return new SequenceCode(codeLength, mutator, objectiveFunction);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null) {
            if (object == this) {
                return true;
            } else return object instanceof SequenceCodeFactory;
        }
        return false;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}