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
package geneticalgorithm.population;

import java.util.ArrayList;
import java.util.LinkedList;
import tool.NonredundantLinkedList;
import tool.RouletteWheel;

/**
 *
 * @author Pierre REN
 */
public abstract class Population<T> {

    protected ArrayList<T> population;
    protected ArrayList<T> geneticPopulation;
    protected double[] fitnessList;
    protected double[] scaledFitnessList;
    protected double[] fitnessListOfGeneticPopulation;
    protected double[] scaledFitnessListOfGeneticPopulation;
    protected double bestFitnessEver;
    protected NonredundantLinkedList<T> bestGenerationIndividual;
    protected NonredundantLinkedList<T> bestIndividualEver;
    protected boolean searchForMax;

    public Population(int NP) {
        population = new ArrayList(NP);
        bestIndividualEver = new NonredundantLinkedList();
    }

    public void searchForMax() {
        searchForMax = true;
        bestFitnessEver = Double.NEGATIVE_INFINITY;
    }

    public void searchForMin() {
        searchForMax = false;
        bestFitnessEver = Double.POSITIVE_INFINITY;
    }

    /**
     * Proportional selection of individuals according to their scaled fitness
     * using the roulete wheel algorithm. Produce a list of index of individuals
     * which are preferable according to a specific criterion.
     *
     * @param N chromosomes size of the selected individuals
     * @return index of individuals selected
     */
    public int[] proportionalSelection(double[] scaledFitnessList, int N) {
        int[] index = new int[N];
        RouletteWheel rouletteWheel = new RouletteWheel(scaledFitnessList);
        for (int i = 0; i < N; i++) {
            index[i] = rouletteWheel.randomIndex();
        }
        return index;
    }

    public double selectBestGenerationIndividual() {
        double bestGenerationFitness = 0;
        LinkedList index = new LinkedList();
        if (searchForMax) {
            bestGenerationFitness = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < fitnessList.length; i++) {
                if (fitnessList[i] > bestGenerationFitness) {
                    bestGenerationFitness = fitnessList[i];
                    index.clear();
                    index.add(i);
                } else if (fitnessList[i] == bestGenerationFitness) {
                    index.add(i);
                }
            }
        } else if (!searchForMax) {
            bestGenerationFitness = Double.POSITIVE_INFINITY;
            for (int i = 0; i < fitnessList.length; i++) {
                if (fitnessList[i] < bestGenerationFitness) {
                    bestGenerationFitness = fitnessList[i];
                    index.clear();
                    index.add(i);
                } else if (fitnessList[i] == bestGenerationFitness) {
                    index.add(i);
                }
            }
        }
        bestGenerationIndividual = new NonredundantLinkedList();
        for (int i = 0; i < index.size(); i++) {
            bestGenerationIndividual.add(population.get((int) index.get(i)));
        }
        return bestGenerationFitness;
    }

    public abstract void updateBestIndividualEver();

    public void removeDuplicateBestIndividualEver() {
        if (bestIndividualEver.checkRedundancy()) {
            System.out.println("The NonredundantLinkedList works badly: Redundancy occurs!!!");
            bestIndividualEver.removeDuplicates();
        }
    }

    public NonredundantLinkedList<T> getBestIndividualEver() {
        return bestIndividualEver;
    }

    public double maxFitness() {
        double maxFitness = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < fitnessList.length; i++) {
            if (maxFitness < fitnessList[i]) {
                maxFitness = fitnessList[i];
            }
        }
        return maxFitness;
    }

    public double minFitness() {
        double minFitness = Double.POSITIVE_INFINITY;
        for (int i = 0; i < fitnessList.length; i++) {
            if (minFitness > fitnessList[i]) {
                minFitness = fitnessList[i];
            }
        }
        return minFitness;
    }

    public double averageFitness() {
        double sum = 0;
        for (int i = 0; i < fitnessList.length; i++) {
            sum = sum + fitnessList[i];
        }
        double average = sum / fitnessList.length;
        return average;
    }

    public String toString() {
        String s = new String();
        for (int i = 0; i < population.size(); i++) {
            s += population.get(i).toString() + "\n";
        }
        return s;
    }

    public abstract void setProblemNature();

    public abstract void calculateAndScaleFitness();

    public abstract void calculateAndScaleFitnessOfGeneticPopulation();

    public abstract void getGeneticPopulation();

    public abstract void executeGeneticOperations();

    public abstract void getNextGeneration();

    public void evolve() {
        getGeneticPopulation();
        executeGeneticOperations();
        getNextGeneration();
        updateBestIndividualEver();
    }
}
