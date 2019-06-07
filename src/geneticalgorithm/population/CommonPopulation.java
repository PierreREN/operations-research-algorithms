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

package geneticalgorithm.population;

import basics.tools.NonredundantLinkedList;
import basics.tools.RouletteWheel;
import geneticalgorithm.chromosome.Chromosome;
import geneticalgorithm.chromosomefactory.ChromosomeFactory;
import geneticalgorithm.processcontroller.GAController;

import java.util.ArrayList;

public class CommonPopulation {

    private GAController controller;

    private ArrayList<Chromosome> population;
    private ChromosomeFactory chromosomeFactory;

    private NonredundantLinkedList<Chromosome> optimums;

    public CommonPopulation(GAController controller,
                            ChromosomeFactory chromosomeFactory) {
        this.controller = controller;
        this.chromosomeFactory = chromosomeFactory;
    }

    public void initialize() {
        int geneticPopulationSize = controller.getIntegerParameter("geneticPopulationSize");
        population = new ArrayList<>(geneticPopulationSize);
        for (int i = 0; i < geneticPopulationSize; i++) {
            population.add(chromosomeFactory.generateChromosome());
        }
        optimums = new NonredundantLinkedList<>();
    }

    /**
     * Proportional selection of individuals according to their scaled fitness
     * using the roulete wheel algorithm. Produce a list of index of individuals
     * which are preferable according to a specific criterion.
     *
     * @param N chromosomes size of the selected individuals
     * @return index of individuals selected
     */
    private int[] proportionalSelection(double[] fitnessList, int N) {
        int[] indexes = new int[N];
        RouletteWheel rouletteWheel = new RouletteWheel(fitnessList);
        for (int i = 0; i < N; i++) {
            indexes[i] = rouletteWheel.randomIndex();
        }
        return indexes;
    }

    private void singleGenerationEvolution() throws CloneNotSupportedException {
        double[] objectiveFunctionValues = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            double value = population.get(i).objectiveFunctionValue();
            objectiveFunctionValues[i] = value;
            if (controller.equalToCurrentOptimum(value)) {
                optimums.add((Chromosome) population.get(i).clone());
            } else if (controller.betterThanCurrentOptimum(value)) {
                optimums.clear();
                optimums.add((Chromosome) population.get(i).clone());
                controller.setOptimum(value);
            }
        }
        double[] fitnessList = controller.scale(objectiveFunctionValues);

        int geneticPopulationSize = controller.getIntegerParameter("geneticPopulationSize");
        int[] indexes = proportionalSelection(fitnessList, geneticPopulationSize);
        ArrayList<Chromosome> geneticPopulation = new ArrayList<>(geneticPopulationSize);
        for (int i = 0; i < geneticPopulationSize; i++) {
            geneticPopulation.add((Chromosome) population.get(indexes[i]).clone());
        }

        for (int i = 0; i < geneticPopulationSize; i = i + 2) {
            Chromosome chromosome1 = geneticPopulation.get(i);
            Chromosome chromosome2 = geneticPopulation.get(i + 1);
            controller.crossover(chromosome1, chromosome2);
        }
        for (Chromosome chromosome : geneticPopulation) {
            chromosome.mutate();
        }
        population = new ArrayList<>(geneticPopulation);
    }

    public void evolve() throws CloneNotSupportedException {
        for (controller.initialize(); !controller.reachStoppingCriterion(); controller.update()) {
            singleGenerationEvolution();
//                System.out.println(population + "\n");
            for (Chromosome chromosome : population) {
                double value = chromosome.objectiveFunctionValue();
                if (controller.equalToCurrentOptimum(value)) {
                    optimums.add((Chromosome) chromosome.clone());
                } else if (controller.betterThanCurrentOptimum(value)) {
                    optimums.clear();
                    optimums.add((Chromosome) chromosome.clone());
                    controller.setOptimum(value);
                }
            }
//            System.out.println("[ Iteration " + controller.getCurrentIteration() + " ]\t" + getOptimums().toString());
        }
    }

    public NonredundantLinkedList<Chromosome> getOptimums() {
        return optimums;
    }
}
