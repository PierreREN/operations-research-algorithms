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

package geneticalgorithm.solver;

import basics.codefactory.BinaryCodeFactory;
import basics.codefactory.CodeFactory;
import basics.mutationbehaviors.TransposeMutator;
import basics.objectivefunctions.BinaryEvaluator;
import geneticalgorithm.chromosomefactory.ChromosomeFactory;
import geneticalgorithm.chromosomefactory.ChromosomeFactoryUsingCodeFactory;
import geneticalgorithm.crossoverbehaviors.DoublePointCrossover;
import geneticalgorithm.population.CommonPopulation;
import geneticalgorithm.processcontroller.GAController;
import geneticalgorithm.scalingfunctions.LinearScaling;

public class BinarySolver {

    private CommonPopulation population;

    public static void main(String[] args) {

        GAController controller = new GAController(new DoublePointCrossover(),
                new TransposeMutator(0.1),
                new LinearScaling(-1, 1, 0, 0),
                new BinaryEvaluator());
        controller.setIntegerParameters("maxIteration", 100);
        controller.setIntegerParameters("populationSize", 20);
        controller.setIntegerParameters("geneticPopulationSize", 20);

        BinarySolver binarySolver = new BinarySolver();
        binarySolver.initialize(controller, 4);

        binarySolver.run();
        binarySolver.showResult();
    }

    public void initialize(GAController controller, int codeLength) {
        CodeFactory codeFactory = new BinaryCodeFactory(codeLength,
                controller.getMutator(),
                controller.getObjectiveFunction());
        ChromosomeFactory chromosomeFactory =
                new ChromosomeFactoryUsingCodeFactory(codeFactory);

        population = new CommonPopulation(controller, chromosomeFactory);
        population.initialize();
    }

    public void run() {
        try {
            population.evolve();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void showResult() {
        System.out.println(population.getOptimums());
    }
}