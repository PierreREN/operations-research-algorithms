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

package geneticalgorithm.scalingfunctions;

public class LinearScaling implements ScalingFunction {

    private final double alpha;
    private final double beta;
    private final double gamma;
    private final double xi;

    public LinearScaling(double alpha, double beta, double gamma, double xi) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        this.xi = xi;
    }

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        double maxValue = maxOf(values);
        double minValue = minOf(values);
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = alpha * values[i] + beta * maxValue + gamma * minValue + xi;
        }
        return scaledValues;
    }

    private double maxOf(double[] values) {
        double maxFitness = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < values.length; i++) {
            if (maxFitness < values[i]) {
                maxFitness = values[i];
            }
        }
        return maxFitness;
    }

    private double minOf(double[] values) {
        double minFitness = Double.POSITIVE_INFINITY;
        for (int i = 0; i < values.length; i++) {
            if (minFitness > values[i]) {
                minFitness = values[i];
            }
        }
        return minFitness;
    }

}
