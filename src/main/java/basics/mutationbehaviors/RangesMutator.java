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

import basics.tools.Ranges;

import java.util.Random;

public class RangesMutator implements MutationBehavior {

    private Ranges ranges;
    private double mutationRate;
    private double singleDigitMutationRate;

    public RangesMutator(Ranges ranges, double mutationRate, double singleDigitMutationRate) {
        this.ranges = ranges;
        this.mutationRate = mutationRate;
        this.singleDigitMutationRate = singleDigitMutationRate;
    }

    @Override
    public void mutate(int[] array) {
        Random r = new Random();
        if (r.nextDouble() < mutationRate) {
            for (int i = 0; i < ranges.getLength(); i++) {
                if (r.nextDouble() < singleDigitMutationRate) {
                    array[i] = ranges.getLowerLimits()[i] + r.nextInt(ranges.getUpperLimits()[i] - ranges.getLowerLimits()[i]);
                }
            }
        }
    }
}