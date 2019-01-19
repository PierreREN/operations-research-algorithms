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
package geneticalgorithm.scalingfunctions;

/**
 * @author Pierre REN
 */
public class ScalingFunctions {

    public static double[] regularization(boolean searchForMax, double[] values, double maxFitness, double minFitness, double r) {
        double[] scaledValues = new double[values.length];
        if (searchForMax) {
            for (int i = 0; i < values.length; i++) {
                scaledValues[i] = (values[i] - minFitness + r) / (maxFitness - minFitness + r);
            }
        } else {
            for (int i = 0; i < values.length; i++) {
                scaledValues[i] = (maxFitness - values[i] + r) / (maxFitness - minFitness + r);
            }
        }
        return scaledValues;
    }

    public void window(double a, int w) {

    }
}
