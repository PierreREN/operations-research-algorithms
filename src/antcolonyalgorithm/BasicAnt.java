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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import tool.RouletteWheel;

/**
 *
 * @author Pierre REN
 */
public class BasicAnt<Node> {

    protected final Environment<Node> environment;
    protected Node currentNode;
    protected LinkedList<Node> visitedNodes;
    protected LinkedList<Node> reachableNodes;

    public BasicAnt(Environment environment) {
        this.environment = environment;
        visitedNodes = new LinkedList();
    }

    public void initialize() {
        Random r = new Random();
        int index = r.nextInt(environment.nodes().size()) + 1;
        Iterator itr = environment.nodes().iterator();
        for (int i = 0; i < index; i++) {
            currentNode = (Node) itr.next();
        }
//        System.out.println(currentNode);
        visitedNodes.add(currentNode);
    }

    private LinkedList<Node> getReachableNodes() {
        reachableNodes = new LinkedList(environment.adjacentNodes(currentNode));
        reachableNodes.removeAll(visitedNodes);
        return reachableNodes;
    }

    private double[] getDistances() {
        double[] distances = new double[reachableNodes.size()];
        for (int i = 0; i < distances.length; i++) {
            distances[i] = environment.getDistance(currentNode, reachableNodes.get(i));
        }
        return distances;
    }

    private double[] getHeuristics() {
        double[] distances = getDistances();
        double[] heuristics = new double[distances.length];
        for (int i = 0; i < heuristics.length; i++) {
            heuristics[i] = 1 / distances[i];
        }
        return heuristics;
    }

    private double[] getPheromones() {
        double[] pheromones = new double[reachableNodes.size()];
        for (int i = 0; i < pheromones.length; i++) {
            pheromones[i] = environment.getPheromone(currentNode, reachableNodes.get(i));
        }
        return pheromones;
    }

    public boolean moveToNextNode(double alpha, double beta) {
        if (getReachableNodes().isEmpty()) {
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

    public LinkedList<Node> getVisitedPath() {
        return visitedNodes;
    }

    public double getVisitedPathLength() {
        double length = 0;
        for (int i = 0; i < visitedNodes.size() - 1; i++) {
            Node nodeU = visitedNodes.get(i);
            Node nodeV = visitedNodes.get(i + 1);
//            System.out.println(visitedNodes.size()+"\t"+i+"\t"+nodeU+"\t"+nodeV);
            length += environment.getDistance(nodeU, nodeV);
        }
        return length;
    }

}
