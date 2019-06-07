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

import java.util.Random;

public class ShiftMutator implements MutationBehavior {

    private double mutationRate;

    public ShiftMutator(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    @Override
    public void mutate(int[] array) {
        Random r = new Random();
        if (r.nextDouble() < mutationRate) {
            int index = 1 + r.nextInt(array.length - 1);
            int code_0 = array[index];
            for (int i = index; i > 0; i--) {
                array[i] = array[i - 1];
            }
            array[0] = code_0;
        }
    }
}
