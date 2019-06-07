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

import basics.testcases.Environment;

public class TSP_Evaluator implements ObjectiveFunction {

    private Environment environment;

    public TSP_Evaluator(Environment environment) {
        this.environment = environment;
    }

    @Override
    public double evaluate(int[] code) {

        int[] tspCode = new int[code.length + 2];
        tspCode[0] = code.length;
        for (int i = 1; i <= code.length; i++) {
            tspCode[i] = code[i - 1];
        }
        tspCode[code.length + 1] = code.length;
        double value = 0;
        Object[] nodes = environment.nodes().toArray();
        for (int i = 0; i < tspCode.length - 1; i++) {
            int indexNodeU = tspCode[i];
            int indexNodeV = tspCode[i + 1];
            value += environment.getDistance(nodes[indexNodeU], nodes[indexNodeV]);
        }
        return value;
    }
}