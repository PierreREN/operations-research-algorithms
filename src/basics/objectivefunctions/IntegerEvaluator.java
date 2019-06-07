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

package basics.objectivefunctions;

public class IntegerEvaluator implements ObjectiveFunction {

    @Override
    public double evaluate(int[] code) {
        double value = 0;
        int[] processingTime = {8, 18, 5, 15};
        int n = code.length;
        for (int i = 0; i < code.length; i++) {
            value += processingTime[code[i] - 1] * n--;
        }
        return value;
    }
}