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

import geneticalgorithm.chromosome.IntegerChromosome;
import geneticalgorithm.chromosome.Crossover;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pierre REN
 */
public abstract class TemplateIntegerPopulation extends Population<IntegerChromosome> {

    public TemplateIntegerPopulation(int NP) {
        super(NP);
    }

    @Override
    public void calculateAndScaleFitness() {
        fitnessList = new double[population.size()];
        for (int i = 0; i < population.size(); i++) {
            fitnessList[i] = population.get(i).calculateFitness();
        }
        scaleFitness();
    }

    @Override
    public void calculateAndScaleFitnessOfGeneticPopulation() {
        fitnessListOfGeneticPopulation = new double[geneticPopulation.size()];
        for (int i = 0; i < geneticPopulation.size(); i++) {
            fitnessListOfGeneticPopulation[i] = geneticPopulation.get(i).calculateFitness();
        }
        scaleFitnessOfGeneticPopulation();
    }

    public void getGeneticPopulationWithProportionalSelection() {
        int[] index = proportionalSelection(scaledFitnessList, population.size());
        geneticPopulation = new ArrayList(index.length);
        for (int i = 0; i < index.length; i++) {
            try {
                geneticPopulation.add((IntegerChromosome) population.get(index[i]).clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void getGeneticPopulationWithProportionalSelection(int geneticNP) {
        int[] index = proportionalSelection(scaledFitnessList, geneticNP);
        geneticPopulation = new ArrayList(geneticNP);
        for (int i = 0; i < geneticNP; i++) {
            try {
                geneticPopulation.add((IntegerChromosome) population.get(index[i]).clone());
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateBestIndividualEver() {
        double bestGenerationFitness = selectBestGenerationIndividual();
        if (searchForMax) {
            if (bestGenerationFitness > bestFitnessEver) {
                bestFitnessEver = bestGenerationFitness;
                bestIndividualEver.clear();
                for (int i = 0; i < bestGenerationIndividual.size(); i++) {
                    try {
                        bestIndividualEver.add((IntegerChromosome) bestGenerationIndividual.get(i).clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (bestGenerationFitness == bestFitnessEver) {
                for (int i = 0; i < bestGenerationIndividual.size(); i++) {
                    try {
                        bestIndividualEver.add((IntegerChromosome) bestGenerationIndividual.get(i).clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } else if (!searchForMax) {
            if (bestGenerationFitness < bestFitnessEver) {
                bestFitnessEver = bestGenerationFitness;
                bestIndividualEver.clear();
                for (int i = 0; i < bestGenerationIndividual.size(); i++) {
                    try {
                        bestIndividualEver.add((IntegerChromosome) bestGenerationIndividual.get(i).clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (bestGenerationFitness == bestFitnessEver) {
                for (int i = 0; i < bestGenerationIndividual.size(); i++) {
                    try {
                        bestIndividualEver.add((IntegerChromosome) bestGenerationIndividual.get(i).clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        bestGenerationIndividual.clear();
        removeDuplicateBestIndividualEver();
    }

    public void singlePointCrossover(double Pc) {
        if (Math.random() < Pc) {
            for (int i = 0; i < geneticPopulation.size(); i += 2) {
                Crossover.singlePoint(geneticPopulation.get(i), geneticPopulation.get(i + 1));
            }
        }
    }

    public void doublePointCrossover(double Pc) {
        if (Math.random() < Pc) {
            for (int i = 0; i < geneticPopulation.size(); i += 2) {
                Crossover.doublePoint(geneticPopulation.get(i), geneticPopulation.get(i + 1));
            }
        }
    }

    public void mutation(double Pm) {
        for (int i = 0; i < geneticPopulation.size(); i++) {
            geneticPopulation.get(i).mutation(Pm);
        }
    }

    public void getNextGenerationWithoutSelection() {
        try {
            if (population.size() != geneticPopulation.size()) {
                throw new UnsupportedOperationException("Inconsistent population size.");
            }
            population = (ArrayList<IntegerChromosome>) geneticPopulation.clone();
            calculateAndScaleFitness();
            geneticPopulation.clear();
        } catch (UnsupportedOperationException uoe) {
            uoe.printStackTrace(System.out);
        }
    }

    public void getNextGenerationWithProportionalSelection() {
        calculateAndScaleFitnessOfGeneticPopulation();
        int[] index = proportionalSelection(scaledFitnessListOfGeneticPopulation, population.size());
        try {
            for (int i = 0; i < population.size(); i++) {
                population.set(i, (IntegerChromosome) geneticPopulation.get(index[i]).clone());
                fitnessList[i] = geneticPopulation.get(index[i]).getFitness();
            }
            scaleFitness();
            geneticPopulation.clear();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(TemplateIntegerPopulation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public abstract void scaleFitness();

    public abstract void scaleFitnessOfGeneticPopulation();
}
