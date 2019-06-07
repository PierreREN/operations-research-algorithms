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

package basics.mutationbehaviors;

import basics.tools.Random2Index;

public class TransposeMutator implements MutationBehavior {

    private double mutationRate;

    public TransposeMutator(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(int[] array) {
        if (Math.random() < mutationRate) {
            if (array.length > 1) {
                Random2Index r = new Random2Index(array.length);
                int code_2 = array[r.index2];
                array[r.index2] = array[r.index1];
                array[r.index1] = code_2;
            }
        }
    }
}
