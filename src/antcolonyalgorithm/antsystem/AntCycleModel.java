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
package antcolonyalgorithm.antsystem;

import antcolonyalgorithm.Environment;
import antcolonyalgorithm.EnvironmentFactory;
import antcolonyalgorithm.ant.CycleAnt;
import antcolonyalgorithm.antsystemcontroller.AntsController;
import basics.tools.NonredundantLinkedList;

import java.util.LinkedList;

/**
 * @author Pierre REN
 */
public class AntCycleModel {

    private Environment environment;
    private AntsController antsController;

    private LinkedList<CycleAnt> ants;

    private double minCyclePathLength = Double.POSITIVE_INFINITY;
    private NonredundantLinkedList<LinkedList> shortestCyclePaths;

    public AntCycleModel(Environment environment, AntsController antsController) {
        this.environment = environment;
        this.antsController = antsController;
    }

    public static void main(String[] args) {

        Environment environment = EnvironmentFactory.getEnvironment();
        AntsController antsController = new AntsController();

        antsController.setIntegerParameters("maxIteration", 200);
        antsController.setIntegerParameters("colonySize", 50);
        antsController.setDoubleParameters("alpha", 1);
        antsController.setDoubleParameters("beta", 5);
        antsController.setDoubleParameters("Q", 100);

        AntCycleModel acm = new AntCycleModel(environment, antsController);
        acm.initialize();
        acm.run();
        acm.showResult();
    }

    public void initialize() {
        double alpha = antsController.getDoubleParameter("alpha");
        double beta = antsController.getDoubleParameter("beta");
        double Q = antsController.getDoubleParameter("Q");
        int colonySize = antsController.getIntegerParameter("colonySize");

        ants = new LinkedList<>();
        shortestCyclePaths = new NonredundantLinkedList<>();
        for (int i = 0; i < colonySize; i++) {
            ants.add(new CycleAnt(environment, alpha, beta, Q));
        }
    }

    public void run() {
        for (antsController.initialize(); !antsController.reachStoppingCriterion(); antsController.update()) {
            environment.volatilizePheromones();
            for (CycleAnt cycleAnt : ants) {
                cycleAnt.initialize();
                if (cycleAnt.findCyclePath()) {
                    double cyclePathLength = cycleAnt.getCyclePathLength();
                    if (cyclePathLength < minCyclePathLength) {
                        minCyclePathLength = cyclePathLength;
                        shortestCyclePaths.clear();
                        shortestCyclePaths.add(cycleAnt.getVisitedPath());
                    } else if (cyclePathLength == minCyclePathLength) {
                        shortestCyclePaths.add(cycleAnt.getVisitedPath());
                    }
                    cycleAnt.leavePheromonesGlobally();
                }
            }
            showLocalResult();
        }
    }

    private void showLocalResult() {
        System.out.println("Shortest Cycle Paths in Iteration [" + antsController.getCurrentIteration() + "] with Length " + minCyclePathLength + ":\t" + shortestCyclePaths);
    }

    public void showResult() {
        System.out.println("Globally Shortest Cycle Paths with Length " + minCyclePathLength + ":\t" + shortestCyclePaths);
    }
}
