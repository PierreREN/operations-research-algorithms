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
package antcolonyalgorithm.antsystem;

import antcolonyalgorithm.ant.CycleAnt;
import antcolonyalgorithm.processcontroller.AntsController;
import basics.testcases.Environment;
import basics.tools.NonredundantLinkedList;

import java.util.LinkedList;

/**
 * @author Pierre REN
 */
public class AntCycleModel {

    private Environment environment;
    private AntsController antsController;

    private LinkedList<CycleAnt> ants;

    private NonredundantLinkedList<LinkedList> optimalPaths;

    public AntCycleModel(Environment environment, AntsController antsController) {
        this.environment = environment;
        this.antsController = antsController;
    }

    public void initialize() {
        double alpha = antsController.getDoubleParameter("alpha");
        double beta = antsController.getDoubleParameter("beta");
        double Q = antsController.getDoubleParameter("Q");
        int colonySize = antsController.getIntegerParameter("colonySize");

        ants = new LinkedList<>();
        optimalPaths = new NonredundantLinkedList<>();
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
                    if (antsController.equalToCurrentOptimum(cyclePathLength)) {
                        optimalPaths.add(cycleAnt.getVisitedPath());
                    } else if (antsController.betterThanCurrentOptimum(cyclePathLength)) {
                        optimalPaths.clear();
                        optimalPaths.add(cycleAnt.getVisitedPath());
                        antsController.setOptimum(cyclePathLength);
                    }
                    cycleAnt.leavePheromonesGlobally();
                }
            }
//            showLocalResult();
        }
    }

    private void showLocalResult() {
        System.out.println("Shortest Cycle Paths in Iteration ["
                + antsController.getCurrentIteration()
                + "] with Length "
                + antsController.getOptimum()
                + ":\t" + optimalPaths);
    }

    public void showResult() {
        System.out.println("Globally Shortest Cycle Paths with Length "
                + antsController.getOptimum()
                + ":\t"
                + optimalPaths);
    }
}
