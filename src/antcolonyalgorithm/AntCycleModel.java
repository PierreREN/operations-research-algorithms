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
package antcolonyalgorithm;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.LinkedList;
import geneticalgorithm.tool.node.Coordinates;
import tool.NonredundantLinkedList;

/**
 *
 * @author Pierre REN
 */
public class AntCycleModel {

    private Environment environment;
    private double alpha = 1;
    private double beta = 5;
    private double Q = 100;

    private int nCycle = 0;
    private int maxIteration = 200;
    private int nAnt = 50;

    private double minCyclePathLength = Double.POSITIVE_INFINITY;
    private NonredundantLinkedList<LinkedList> shortestCyclePaths;

    private class Ant<Node> extends BasicAnt<Node> {

        public Ant(Environment environment) {
            super(environment);
            initialize();
        }

        private boolean isCycleFormed() {
            return environment.nodes().containsAll(visitedNodes) && visitedNodes.containsAll(environment.nodes());
        }

        private double getCyclePathLength() {
            return getVisitedPathLength() + environment.getDistance(visitedNodes.getLast(), visitedNodes.getFirst());
        }

        private double calculateDeltaPheromone() {
            return Q / getCyclePathLength();
        }

        public void leavePheromones() {
            double deltaPheromone = calculateDeltaPheromone();
            for (int i = 0; i < visitedNodes.size() - 1; i++) {
                Node nodeU = visitedNodes.get(i);
                Node nodeV = visitedNodes.get(i + 1);
                environment.leavePheromone(nodeU, nodeV, deltaPheromone);
            }
        }

        @SuppressWarnings("empty-statement")
        public boolean findCyclePath() {
            while (moveToNextNode(alpha, beta));
            return isCycleFormed();
        }

    }

    public AntCycleModel() {
        shortestCyclePaths = new NonredundantLinkedList();
        setEnvironment();
        while (nCycle++ < maxIteration) {
            environment.volatilizePheromones();
            for (int i = 0; i < nAnt; i++) {
                Ant ant = new Ant(environment);
                if (ant.findCyclePath()) {
                    double cylclePathLength = ant.getCyclePathLength();
                    if (cylclePathLength < minCyclePathLength) {
                        minCyclePathLength = cylclePathLength;
                        shortestCyclePaths.clear();
                        shortestCyclePaths.add(ant.getVisitedPath());
                        ant.leavePheromones();
                    } else if (cylclePathLength == minCyclePathLength) {
                        shortestCyclePaths.add(ant.getVisitedPath());
                    }
                }
            }
            System.out.println("Shortest Cycle Paths in Iteration [" + nCycle + "] with Length " + minCyclePathLength + ":\t" + shortestCyclePaths);
        }
        System.out.println("Globally Shortest Cycle Paths with Length " + minCyclePathLength + ":\t" + shortestCyclePaths);
    }

    public void setEnvironment() {
        MutableValueGraph<Coordinates, Double> distanceMap = ValueGraphBuilder.undirected().allowsSelfLoops(true).expectedNodeCount(31).build();
        MutableValueGraph<Coordinates, Double> pheromoneMap = ValueGraphBuilder.undirected().allowsSelfLoops(true).expectedNodeCount(31).build();
        double[] x = new double[]{1304, 3639, 4177, 3712, 3488, 3326, 3238, 4196, 4312, 4386, 3007, 2562, 2788, 2381, 1332, 3715, 3918, 4061, 3780, 3676, 4029, 4263, 3429, 3507, 3394, 3439, 2935, 3140, 2545, 2778, 2370};
        double[] y = new double[]{2312, 1315, 2244, 1399, 1535, 1556, 1229, 1044, 790, 570, 1970, 1756, 1491, 1676, 695, 1678, 2179, 2370, 2212, 2578, 2838, 2931, 1908, 2376, 2643, 3201, 3240, 3550, 2357, 2826, 2975};
        LinkedList<Coordinates> cities = new LinkedList();
        for (int i = 0; i < 31; i++) {
            cities.add(new Coordinates(x[i], y[i], 0));
        }
        for (int i = 0; i < cities.size() - 1; i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                distanceMap.putEdgeValue(cities.get(i), cities.get(j), Coordinates.getDistance(cities.get(i), cities.get(j)));
                pheromoneMap.putEdgeValue(cities.get(i), cities.get(j), 1.0);
            }
        }
        environment = new Environment(distanceMap, pheromoneMap);
    }

    public static void main(String[] args) {
        AntCycleModel antCycleModel = new AntCycleModel();
    }

}
