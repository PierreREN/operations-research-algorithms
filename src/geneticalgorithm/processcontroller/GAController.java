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

package geneticalgorithm.processcontroller;

import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;
import basics.processcontroller.ProcessController;
import geneticalgorithm.chromosome.Chromosome;
import geneticalgorithm.crossoverbehaviors.CrossoverBehavior;
import geneticalgorithm.scalingfunctions.ScalingFunction;

import java.util.HashMap;

public class GAController implements ProcessController {

    private HashMap<String, Integer> integerParameters = new HashMap<>();
    private HashMap<String, Double> doubleParameters = new HashMap<>();
    private HashMap<String, Boolean> booleanParameters = new HashMap<>();

    private CrossoverBehavior crossoverBehavior;
    private MutationBehavior mutator;
    private ScalingFunction scalingFunction;
    private ObjectiveFunction objectiveFunction;

    private int currentIteration;
    private int maxIteration;
    private double optimalFitness;

    public GAController(CrossoverBehavior crossoverBehavior,
                        MutationBehavior mutator,
                        ScalingFunction scalingFunction,
                        ObjectiveFunction objectiveFunction) {
        this.crossoverBehavior = crossoverBehavior;
        this.mutator = mutator;
        this.scalingFunction = scalingFunction;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public void initialize() {
        currentIteration = 0;
        maxIteration = integerParameters.getOrDefault("maxIteration", 100);
        optimalFitness = Double.POSITIVE_INFINITY;
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

    public void setBooleanParameters(String key, boolean value) {
        booleanParameters.put(key, value);
    }

    public int getIntegerParameter(String string) {
        return integerParameters.getOrDefault(string, 0);
    }

    public double getDoubleParameter(String string) {
        return doubleParameters.getOrDefault(string, 0.0);
    }

    public boolean getBooleanParameters(String string) {
        return booleanParameters.getOrDefault(string, false);
    }

    public int getCurrentIteration() {
        return currentIteration;
    }

    @Override
    public boolean equalToCurrentOptimum(double value) {
        return value == optimalFitness;
    }

    @Override
    public boolean betterThanCurrentOptimum(double value) {
        return value < optimalFitness;
    }

    @Override
    public double getOptimum() {
        return optimalFitness;
    }

    @Override
    public void setOptimum(double objectiveFunctionValue) {
        this.optimalFitness = objectiveFunctionValue;
    }

    public MutationBehavior getMutator() {
        return mutator;
    }

    public ObjectiveFunction getObjectiveFunction() {
        return objectiveFunction;
    }

    public double[] scale(double[] values) {
        return scalingFunction.scale(values);
    }

    public void crossover(Chromosome chromosome1, Chromosome chromosome2) {
        crossoverBehavior.crossover(chromosome1, chromosome2);
    }
}
