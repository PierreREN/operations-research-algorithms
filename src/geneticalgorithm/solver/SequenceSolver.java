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

import basics.codefactory.CodeFactory;
import basics.codefactory.SequenceCodeFactory;
import basics.mutationbehaviors.TransposeMutator;
import basics.objectivefunctions.TSP_Evaluator;
import basics.testcases.Environment;
import basics.testcases.EnvironmentFactory;
import geneticalgorithm.chromosomefactory.ChromosomeFactory;
import geneticalgorithm.chromosomefactory.ChromosomeFactoryUsingCodeFactory;
import geneticalgorithm.crossoverbehaviors.CycleCrossover;
import geneticalgorithm.population.CommonPopulation;
import geneticalgorithm.processcontroller.GAController;
import geneticalgorithm.scalingfunctions.LinearScaling;

public class SequenceSolver {

    private CommonPopulation population;

    public static void main(String[] args) {

        Environment environment = EnvironmentFactory.getEnvironment();

        GAController controller = new GAController(new CycleCrossover(),
                new TransposeMutator(0.1),
                new LinearScaling(-1, 1, 0, 0),
                new TSP_Evaluator(environment));
        controller.setIntegerParameters("maxIteration", 10000);
        controller.setIntegerParameters("populationSize", 20);
        controller.setIntegerParameters("geneticPopulationSize", 20);

        SequenceSolver sequenceSolver = new SequenceSolver();
        for (int i = 0; i < 100; i++) {
            sequenceSolver.initialize(environment, controller);
            sequenceSolver.run();
            sequenceSolver.showResult();
        }
    }

    public void initialize(Environment environment, GAController controller) {
        CodeFactory codeFactory = new SequenceCodeFactory(environment.nodes().size() - 1,
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