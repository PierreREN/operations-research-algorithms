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
package geneticalgorithm.solver;

import geneticalgorithm.chromosome.BinaryChromosome;
import geneticalgorithm.population.TemplateBinaryPopulation;
import geneticalgorithm.tool.ScalingFunction;

/**
 *
 * @author Pierre REN
 */
public class BinarySolver extends TemplateBinaryPopulation {

    static final int NP = 10;
    static final int maxIteration = 20;
    static final double Pc = 0.8;
    static final double Pm = 0.1;

    static final int geneLength = 10;

    public BinarySolver() {
        super(NP);
        for (int k = 0; k < NP; k++) {
            population.add(new BinaryChromosome(geneLength));
        }
        setProblemNature();
        calculateAndScaleFitness();
        updateBestIndividualEver();
        for (int i = 0; i < maxIteration; i++) {
            evolve();
//            System.out.println("Average Fitness for Iteration "+i+" :"+averageFitness());
//            System.out.println("Maximum Fitness for Iteration "+i+" :"+maxFitness());
            System.out.print(maxFitness() + " ");
        }
        for (int i = 0; i < bestIndividualEver.size(); i++) {
            for (int j = i + 1; j < bestIndividualEver.size(); j++) {
                if (bestIndividualEver.get(j).equals(bestIndividualEver.get(i))) {
                    System.out.println("There is a bug with BINARYSOLVER remove duplicate best individuals ever");
                }
            }
        }
    }

    @Override
    public void setProblemNature() {
        searchForMax();
//        searchForMin();
    }

    @Override
    public void scaleFitness() {
        scaledFitnessList = ScalingFunction.linear(fitnessList, 1, -minFitness());
//        scaledFitnessList=ScalingFunction.linear(fitnessList, -1, maxFitness());
    }

    @Override
    public void scaleFitnessOfGeneticPopulation() {
        scaledFitnessListOfGeneticPopulation = ScalingFunction.linear(fitnessListOfGeneticPopulation, 1, -minFitness());
//        scaledFitnessListOfGeneticPopulation=ScalingFunction.linear(fitnessListOfGeneticPopulation, -1, maxFitness());
    }

    @Override
    public void getGeneticPopulation() {
        getGeneticPopulationWithProportionalSelection();
    }

    @Override
    public void getNextGeneration() {
        getNextGenerationWithProportionalSelection();
    }

    @Override
    public void executeGeneticOperations() {
        doublePointCrossover(Pc);
        mutation(Pm);
    }

    public double bestFitnessEver() {
        return bestFitnessEver;
    }

    public static void main(String[] args) {
        new BinarySolver();
    }

}
