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

import java.util.LinkedList;
import java.util.Random;

/**
 * @author Pierre REN
 */
public class SequenceCode extends CommonCode implements Code {

    public SequenceCode(int length, MutationBehavior mutator,
                        ObjectiveFunction objectiveFunction) {
        super(length);
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public void initialize() {
        random();
    }

    private void random() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            list.add(i);
        }
        Random r = new Random();
        for (int i = 0; i < array.length; i++) {
            int index = r.nextInt(list.size());
            array[i] = list.get(index);
            list.remove(index);
        }
    }

    public boolean testSequence() {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] == array[j]) {
                    return false;
                }
            }
        }
        return true;
    }
}