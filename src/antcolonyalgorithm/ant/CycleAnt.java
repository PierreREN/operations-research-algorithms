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

package antcolonyalgorithm.ant;

import basics.testcases.Environment;
import basics.tools.RouletteWheel;

public class CycleAnt<Node> extends BasicAnt<Node> {

    private double alpha;
    private double beta;
    private double Q;

    public CycleAnt(Environment environment, double alpha, double beta, double Q) {
        super(environment);
        this.alpha = alpha;
        this.beta = beta;
        this.Q = Q;
    }

    @Override
    public boolean moveToNextNode() {
        if (getReachableNodes().isEmpty()) {
            if (environment.adjacentNodes(visitedNodes.getLast()).contains(visitedNodes.getFirst())
                    && environment.getDistance(visitedNodes.getLast(), visitedNodes.getFirst()) < Double.POSITIVE_INFINITY) {
                visitedNodes.add(visitedNodes.getFirst());
            }
            return false;
        } else {
            double[] transitionPower = new double[reachableNodes.size()];
            double[] pheromones = getPheromones();
            double[] heuristics = getHeuristics();
            for (int i = 0; i < transitionPower.length; i++) {
                transitionPower[i] = Math.pow(pheromones[i], alpha) * Math.pow(heuristics[i], beta);
            }
            RouletteWheel rouletteWheel = new RouletteWheel(transitionPower);
            int indexOfNextNode = rouletteWheel.randomIndex();
            currentNode = reachableNodes.get(indexOfNextNode);
            visitedNodes.add(currentNode);
            return true;
        }
    }

    @Override
    public void leavePheromoneLocally() {
    }

    private double calculateDeltaPheromone() {
        return Q / getCyclePathLength();
    }

    @Override
    public void leavePheromonesGlobally() {
        double deltaPheromone = calculateDeltaPheromone();
        for (int i = 0; i < visitedNodes.size() - 1; i++) {
            Node nodeU = visitedNodes.get(i);
            Node nodeV = visitedNodes.get(i + 1);
            environment.leavePheromone(nodeU, nodeV, deltaPheromone);
        }
    }

    public boolean findCyclePath() {
        while (moveToNextNode()) ;
        return isCycleFormed() && allNodesVisited();
    }

}
