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

import java.util.Arrays;

public class DynamicLinearScaling implements ScalingFunction {

    private double[] A;
    private double[] B;

    public DynamicLinearScaling(double[] A, double[] B) {
        this.A = Arrays.copyOf(A, A.length);
        this.B = Arrays.copyOf(B, B.length);
    }

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = A[i] * values[i] + B[i];
        }
        return scaledValues;
    }
}
