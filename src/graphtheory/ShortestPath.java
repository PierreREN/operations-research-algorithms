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
package graphtheory;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Pierre REN
 */
public class ShortestPath {

    public static <N> void Dijkstra(MutableValueGraph<N, Double> valueGraph, N startNode) {
        if (!valueGraph.nodes().contains(startNode)) {
            System.err.println("Start node is not included in the given value graph.");
        }
        Set<N> unvisitedNodes = new HashSet(valueGraph.nodes());
        Map<N, N> predecessors = new HashMap();
        Map<N, Double> distances = new HashMap();
        for (N node : valueGraph.nodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.replace(startNode, 0.0);
        while (!unvisitedNodes.isEmpty()) {
            double minDistance = Double.POSITIVE_INFINITY;
            N currentNode = null;
            for (N node : unvisitedNodes) {
                if (distances.get(node) < minDistance) {
                    minDistance = distances.get(node);
                    currentNode = node;
                }
            }
            unvisitedNodes.remove(currentNode);
            for (N successor : valueGraph.successors(currentNode)) {
                double alt = distances.get(currentNode) + valueGraph.edgeValue(currentNode, successor).get();
                if (alt < distances.get(successor)) {
                    distances.put(successor, alt);
                    predecessors.put(successor, currentNode);
                }
            }
        }
        Set<N> destinations = new HashSet(valueGraph.nodes());
        destinations.remove(startNode);
        ArrayList<MutableValueGraph> shortestPaths = new ArrayList(valueGraph.nodes().size() - 1);
        for (N destination : destinations) {
            MutableValueGraph<N, Double> shortestPath = ValueGraphBuilder.directed().build();
            LinkedList<N> path = new LinkedList();
            path.add(destination);
            N predecessor = predecessors.get(destination);
            while (predecessor != null) {
                path.addFirst(predecessor);
                predecessor = predecessors.get(predecessor);
            }
            for (int i = 0; i < path.size() - 1; i++) {
                N nodeU = path.get(i);
                N nodeV = path.get(i + 1);
                shortestPath.putEdgeValue(nodeU, nodeV, valueGraph.edgeValue(nodeU, nodeV).get());
            }
            shortestPaths.add(shortestPath);
        }
        System.out.println(distances);
        for (MutableValueGraph shortestPath : shortestPaths) {
            System.out.println(shortestPath);
        }
    }

    public static <N> void Floyd(MutableValueGraph<N, Double> valueGraph, N startNode, N destination) {
        ArrayList<N> nodes = new ArrayList(valueGraph.nodes());
        int n = nodes.size();
        double[][] distance = new double[n][n];
        int[][] next = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distance[i][j] = 0;
                    next[i][j] = 0;
                } else {
                    distance[i][j] = valueGraph.edgeValue(nodes.get(i), nodes.get(j)).orElse(Double.POSITIVE_INFINITY);
//                    System.out.println(i+" "+j+" "+valueGraph.edgeValueOrDefault(nodes.get(i),nodes.get(j),Double.POSITIVE_INFINITY));
                    // Bug of Guava in Release 23.0
                    // Has been fixed in recent releases since Oct. 2017
                    // Need to update the jar
                    next[i][j] = j;
                }
            }
        }
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j && i != k && j != k && distance[i][k] + distance[k][j] < distance[i][j]) {
                        distance[i][j] = distance[i][k] + distance[k][j];
                        next[i][j] = k;
                    }
                }
            }
        }
        LinkedList<Integer> indexSerie = new LinkedList();
        indexSerie.add(nodes.indexOf(startNode));
        indexSerie.add(nodes.indexOf(destination));
        while (next[indexSerie.get(0)][indexSerie.get(1)] != indexSerie.get(1)) {
            indexSerie.add(1, next[indexSerie.get(0)][indexSerie.get(1)]);
        }
        System.out.println(indexSerie);
        MutableValueGraph<N, Double> shortestPath = ValueGraphBuilder.directed().build();
        for (int i = 0; i < indexSerie.size() - 1; i++) {
            int indexU = indexSerie.get(i);
            int indexV = indexSerie.get(i + 1);
            N nodeU = nodes.get(indexU);
            N nodeV = nodes.get(indexV);
            shortestPath.putEdgeValue(nodeU, nodeV, valueGraph.edgeValue(nodeU, nodeV).get());
        }
        System.out.println(shortestPath);
    }

    public static void main(String[] args) {
        MutableValueGraph<Integer, Double> valueGraph = ValueGraphBuilder.directed().allowsSelfLoops(true).build();
        valueGraph.putEdgeValue(1, 2, 100.0);
        valueGraph.putEdgeValue(1, 3, 30.0);
        valueGraph.putEdgeValue(2, 3, 20.0);
        valueGraph.putEdgeValue(3, 4, 10.0);
        valueGraph.putEdgeValue(3, 5, 60.0);
        valueGraph.putEdgeValue(4, 2, 15.0);
        valueGraph.putEdgeValue(4, 5, 50.0);

        Dijkstra(valueGraph, 1);
        Floyd(valueGraph, 1, 2);
    }
}
