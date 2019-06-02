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

import com.google.common.graph.EndpointPair;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;

import java.util.Set;

/**
 * @author Pierre REN
 */
public class Environment<Node> {

    private ValueGraph<Node, Double> distanceMap;
    private MutableValueGraph<Node, Double> pheromoneMap;
    private double initialPheromone;
    private double volatility = 0.1;

    public Environment(ValueGraph<Node, Double> distanceMap, MutableValueGraph<Node, Double> pheromoneMap, double initialPheromone) {
        this.distanceMap = distanceMap;
        this.pheromoneMap = pheromoneMap;
        this.initialPheromone = initialPheromone;
    }

    public Set nodes() {
        return distanceMap.nodes();
    }

    public Set adjacentNodes(Node node) {
        return distanceMap.adjacentNodes(node);
    }

    public double getDistance(Node nodeU, Node nodeV) {
        return distanceMap.edgeValue(nodeU, nodeV).get();
    }

    public double getPheromone(Node nodeU, Node nodeV) {
        return pheromoneMap.edgeValue(nodeU, nodeV).get();
    }

    public double getInitialPheromone() {
        return initialPheromone;
    }

    public double getVolatility() {
        return volatility;
    }

    public void volatilizePheromones() {
        Set<EndpointPair<Node>> edges = pheromoneMap.edges();
        for (EndpointPair<Node> edge : edges) {
            Node nodeU = edge.nodeU();
            Node nodeV = edge.nodeV();
            volatilizePheromone(nodeU, nodeV, volatility);
        }
    }

    public void volatilizePheromone(Node nodeU, Node nodeV, double volatility) {
        double currentPheromone = pheromoneMap.edgeValue(nodeU, nodeV).get();
        double legacyPheromone = (1 - volatility) * currentPheromone;
        pheromoneMap.putEdgeValue(nodeU, nodeV, legacyPheromone);
    }

    public void leavePheromone(Node nodeU, Node nodeV, double deltaPheromone) {
        double legacyPheromone = pheromoneMap.edgeValue(nodeU, nodeV).get();
        pheromoneMap.putEdgeValue(nodeU, nodeV, legacyPheromone + deltaPheromone);
    }

    public void showPheromones() {
        System.out.println(pheromoneMap);
    }

}
