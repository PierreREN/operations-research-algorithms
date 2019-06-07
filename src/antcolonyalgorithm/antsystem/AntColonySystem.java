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

import antcolonyalgorithm.ant.SimpleAnt;
import antcolonyalgorithm.processcontroller.AntsController;
import basics.testcases.Environment;
import basics.tools.NonredundantLinkedList;

import java.util.LinkedList;

public class AntColonySystem {

    private Environment environment;
    private AntsController antsController;

    private LinkedList<SimpleAnt> ants;

    private double minCyclePathLength = Double.POSITIVE_INFINITY;
    private NonredundantLinkedList<LinkedList> optimalPaths;

    public AntColonySystem(Environment environment, AntsController antsController) {
        this.environment = environment;
        this.antsController = antsController;
    }

    public void initialize() {
        double alpha = antsController.getDoubleParameter("alpha");
        double beta = antsController.getDoubleParameter("beta");
        double rho = antsController.getDoubleParameter("rho");
        double threshold = antsController.getDoubleParameter("threshold");
        int colonySize = antsController.getIntegerParameter("colonySize");

        ants = new LinkedList<>();
        optimalPaths = new NonredundantLinkedList<>();
        for (int i = 0; i < colonySize; i++) {
            ants.add(new SimpleAnt(environment, alpha, beta, rho, threshold));
        }
    }

    public void run() {
        for (antsController.initialize(); !antsController.reachStoppingCriterion(); antsController.update()) {
            allAntsInitialize();
            int maxInnerIteration = environment.nodes().size();
            for (int i = 0; i < maxInnerIteration; i++) {
                allAntsStepForward();
                allAntsLeavePheromonesLocally();
            }
            environment.volatilizePheromones();
            for (SimpleAnt simpleAnt : ants) {
                double cyclePathLength = simpleAnt.getCyclePathLength();
                if (antsController.equalToCurrentOptimum(cyclePathLength)
                        && cyclePathLength != Double.POSITIVE_INFINITY) {
                    optimalPaths.add(simpleAnt.getVisitedPath());
                } else if (antsController.betterThanCurrentOptimum(cyclePathLength)) {
                    optimalPaths.clear();
                    optimalPaths.add(simpleAnt.getVisitedPath());
                    antsController.setOptimum(cyclePathLength);
                }
            }
            leavePheromonesGlobally();
//            showLocalResult();
        }
    }

    private void allAntsInitialize() {
        for (SimpleAnt simpleAnt : ants) {
            simpleAnt.initialize();
        }
    }

    private void allAntsStepForward() {
        for (SimpleAnt simpleAnt : ants) {
            simpleAnt.moveToNextNode();
        }
    }

    private void allAntsLeavePheromonesLocally() {
        for (SimpleAnt simpleAnt : ants) {
            simpleAnt.leavePheromoneLocally();
        }
    }

    private void leavePheromonesGlobally() {
        double meanCyclePathLength = 0;
        for (SimpleAnt simpleAnt : ants) {
            meanCyclePathLength += simpleAnt.getCyclePathLength();
        }
        meanCyclePathLength /= ants.size();

        for (int i = 0; i < optimalPaths.size(); i++) {
            LinkedList shortestCyclePath = optimalPaths.get(i);
            for (int j = 0; j < shortestCyclePath.size() - 1; j++) {
                environment.leavePheromone(shortestCyclePath.get(j), shortestCyclePath.get(j + 1), environment.getVolatility() * meanCyclePathLength / minCyclePathLength);
            }
        }
    }

    private void localOptimalAntsLeavePheromonesGlobally() {
        LinkedList<SimpleAnt> antsWithShortestLocalCyclePath = new LinkedList<>();
        double localMinCyclePathLength = Double.POSITIVE_INFINITY;
        for (SimpleAnt simpleAnt : ants) {
            double cyclePathLength = simpleAnt.getCyclePathLength();
            if (cyclePathLength < localMinCyclePathLength) {
                localMinCyclePathLength = cyclePathLength;
                antsWithShortestLocalCyclePath.clear();
                antsWithShortestLocalCyclePath.add(simpleAnt);
            } else if (cyclePathLength == localMinCyclePathLength
                    && cyclePathLength != Double.POSITIVE_INFINITY) {
                antsWithShortestLocalCyclePath.add(simpleAnt);
            }
        }
        for (SimpleAnt simpleAnt : antsWithShortestLocalCyclePath) {
            simpleAnt.leavePheromonesGlobally();
            double cyclePathLength = simpleAnt.getCyclePathLength();
            if (antsController.equalToCurrentOptimum(cyclePathLength)
                    && cyclePathLength != Double.POSITIVE_INFINITY) {
                optimalPaths.add(simpleAnt.getVisitedPath());
            } else if (antsController.betterThanCurrentOptimum(cyclePathLength)) {
                optimalPaths.clear();
                optimalPaths.add(simpleAnt.getVisitedPath());
                antsController.setOptimum(cyclePathLength);
            }
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
