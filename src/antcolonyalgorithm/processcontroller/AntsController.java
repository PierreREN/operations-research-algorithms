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

package antcolonyalgorithm.processcontroller;

import basics.processcontroller.ProcessController;

import java.util.HashMap;

public class AntsController implements ProcessController {

    private HashMap<String, Integer> integerParameters = new HashMap<>();
    private HashMap<String, Double> doubleParameters = new HashMap<>();

    private int currentIteration;
    private int maxIteration;
    private double minCyclePathLength;

    @Override
    public void initialize() {
        currentIteration = 0;
        maxIteration = integerParameters.getOrDefault("maxIteration", 100);
        minCyclePathLength = Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean reachStoppingCriterion() {
        return currentIteration >= maxIteration;
    }

    @Override
    public void update() {
        currentIteration++;
    }

    public void setIntegerParameters(String key, int value) {
        integerParameters.put(key, value);
    }

    public void setDoubleParameters(String key, double value) {
        doubleParameters.put(key, value);
    }

    public int getIntegerParameter(String string) {
        return integerParameters.getOrDefault(string, 0);
    }

    public double getDoubleParameter(String string) {
        return doubleParameters.getOrDefault(string, 0.0);
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    @Override
    public boolean equalToCurrentOptimum(double value) {
        return value == minCyclePathLength;
    }

    @Override
    public boolean betterThanCurrentOptimum(double value) {
        return value < minCyclePathLength;
    }

    @Override
    public double getOptimum() {
        return minCyclePathLength;
    }

    @Override
    public void setOptimum(double objectiveFunctionValue) {
        this.minCyclePathLength = objectiveFunctionValue;
    }
}
