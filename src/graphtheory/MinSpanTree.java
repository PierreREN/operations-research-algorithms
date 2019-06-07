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
package graphtheory;

import com.google.common.graph.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author Pierre REN
 */
public class MinSpanTree {

    public static <N> void Prim(MutableValueGraph<N, Double> valueGraph, MutableGraph<N> predefinedGraph) {
        MutableValueGraph<N, Double> minSpanTree = ValueGraphBuilder.undirected().build();
        Set<N> connectedNodes;
        Set<N> unconnectedNodes;
        //Initialization from given graphs
        if (predefinedGraph == null) {
            connectedNodes = new HashSet();
        } else {
            connectedNodes = new HashSet(predefinedGraph.nodes());
        }
        unconnectedNodes = new HashSet(valueGraph.nodes());
        if (connectedNodes.isEmpty()) {//When no edges are predefined by the user, pick one node from the set to add in the set of connected nodes
            Iterator itr = unconnectedNodes.iterator();
            connectedNodes.add((N) itr.next());
            itr.remove();
        } else {//Put the nodes adjacent to predefined edges into the set of connected nodes and delete relevant nodes from the set of unconnected nodes
            for (EndpointPair<N> edge : predefinedGraph.edges()) {
                minSpanTree.putEdgeValue(edge.nodeU(), edge.nodeV(), valueGraph.edgeValue(edge.nodeU(), edge.nodeV()).get());
            }
            unconnectedNodes.removeAll(connectedNodes);
        }
        //Mainbody
        while (!unconnectedNodes.isEmpty()) {
            double minValue = Double.POSITIVE_INFINITY;
            N nodeU = null;
            N nodeV = null;
            for (N connectedNode : connectedNodes) {
                for (N unconnectedNode : unconnectedNodes) {
                    if (valueGraph.hasEdgeConnecting(connectedNode, unconnectedNode)) {
                        double edgeValue = valueGraph.edgeValueOrDefault(connectedNode, unconnectedNode, null);
                        if (edgeValue < minValue) {
                            minValue = edgeValue;
                            nodeU = connectedNode;
                            nodeV = unconnectedNode;
//                            System.out.println("[Line 51]   nodeU: "+nodeU+"    nodeV: "+nodeV);
                        }
                    }
                }
            }
            minSpanTree.putEdgeValue(nodeU, nodeV, minValue);
            connectedNodes.add(nodeV);
            unconnectedNodes.remove(nodeV);
//            System.out.println("[Line 63]   nodeU: "+nodeU+"    nodeV: "+nodeV);
        }
        double totalValue = 0;
        for (EndpointPair<N> edge : minSpanTree.edges()) {
            totalValue += minSpanTree.edgeValue(edge.nodeU(), edge.nodeV()).get();
        }
        System.out.println("Given value graph: \n" + valueGraph);
        System.out.println("Minimum spanning tree of given value graph: \n" + minSpanTree);
        System.out.println("Total value of minimum spanning tree: \n" + totalValue);
    }

    public static <N> void Kruskal(MutableValueGraph<N, Double> valueGraph, MutableGraph<N> predefinedGraph) {
        LinkedList<Set<N>> forest = new LinkedList();
        for (N node : valueGraph.nodes()) {
            Set treeNodes = new HashSet();
            treeNodes.add(node);
            forest.add(treeNodes);
        }
        LinkedList<EndpointPair<N>> edges = new LinkedList(valueGraph.edges());
        if (predefinedGraph != null) {
            edges.removeAll(predefinedGraph.edges());//Predefined edges do not have to be sorted; they are ranked at the top of the list by default
        }
        //Sort the edges in the ascending order according to their respective weights
        LinkedList<Double> values = new LinkedList();
        LinkedList<EndpointPair<N>> sortedEdges = new LinkedList();
        sortedEdges.add(edges.get(0));
        values.add(valueGraph.edgeValue(edges.get(0).nodeU(), edges.get(0).nodeV()).get());
        edges.remove(0);
        //Insert the edges one-by-one
        for (int i = 0; i < edges.size(); i++) {
            double value = valueGraph.edgeValue(edges.get(i).nodeU(), edges.get(i).nodeV()).get();
            for (int j = 0; j < values.size(); j++) {
                if (value < values.get(j)) {
                    values.add(j, value);
                    sortedEdges.add(j, edges.get(i));
                    break;
                }//Insert the value to the proper position in the list in the ascending order
                else if (j == (values.size() - 1)) {
                    values.addLast(value);
                    sortedEdges.addLast(edges.get(i));
                    break;
                }//When the value is superior to all the other values in the list, add the value to the end of the list
            }
        }
        if (predefinedGraph != null) {
            sortedEdges.addAll(0, predefinedGraph.edges());//Insert the predefined edges in the front of the entire list
        }
        //Mainbody of Kruskal's Algorithm
        for (int index = 0; index < sortedEdges.size(); index++) {
            EndpointPair<N> edge = sortedEdges.get(index);
            for (int i = 0; i < forest.size(); i++) {
                if (forest.get(i).contains(edge.nodeU()) || forest.get(i).contains(edge.nodeV())) {
                    if (forest.get(i).contains(edge.nodeU()) && forest.get(i).contains(edge.nodeV())) {
                        sortedEdges.remove(edge);
                        index--;
                        break;
                    } else {
                        for (int j = i + 1; j < forest.size(); j++) {
                            if (forest.get(j).contains(edge.nodeU()) || forest.get(j).contains(edge.nodeV())) {
                                forest.get(i).addAll(forest.get(j));
                                forest.remove(j);
                                break;
                            }
                        }
                    }
                }
            }
        }
        MutableValueGraph<N, Double> minSpanTree = ValueGraphBuilder.undirected().build();
        for (EndpointPair<N> edge : sortedEdges) {
            minSpanTree.putEdgeValue(edge.nodeU(), edge.nodeV(), valueGraph.edgeValue(edge.nodeU(), edge.nodeV()).get());
        }
        double totalValue = 0;
        for (EndpointPair<N> edge : minSpanTree.edges()) {
            totalValue += minSpanTree.edgeValue(edge.nodeU(), edge.nodeV()).get();
        }
        System.out.println("Given value graph: \n" + valueGraph);
        System.out.println("Minimum spanning tree of given value graph: \n" + minSpanTree);
        System.out.println("Total value of minimum spanning tree: \n" + totalValue);
    }

    public static void main(String[] args) {
        MutableValueGraph<String, Double> valueGraph = ValueGraphBuilder.undirected().build();
        valueGraph.putEdgeValue("SE", "LA", 1100.0);
        valueGraph.putEdgeValue("SE", "DE", 1300.0);
        valueGraph.putEdgeValue("SE", "CH", 2000.0);
        valueGraph.putEdgeValue("LA", "CH", 2000.0);
        valueGraph.putEdgeValue("LA", "DC", 2600.0);
        valueGraph.putEdgeValue("LA", "DA", 1400.0);
        valueGraph.putEdgeValue("DE", "CH", 1000.0);
        valueGraph.putEdgeValue("DE", "DA", 780.0);
        valueGraph.putEdgeValue("DA", "CH", 900.0);
        valueGraph.putEdgeValue("DA", "DC", 1300.0);
        valueGraph.putEdgeValue("CH", "NY", 800.0);
        valueGraph.putEdgeValue("DC", "NY", 200.0);

        MutableGraph<String> predefinedGraph = GraphBuilder.undirected().build();
        predefinedGraph.putEdge("LA", "CH");

//        MinSpanTree.Prim(valueGraph,predefinedGraph);
//        MinSpanTree.Kruskal(valueGraph, predefinedGraph);
//    }
//    public static void main(String[] args) {
//        MutableValueGraph<Integer,Double> valueGraph=ValueGraphBuilder.undirected().build();
//        valueGraph.putEdgeValue(1,2,1);
//        valueGraph.putEdgeValue(1,3,5);
//        valueGraph.putEdgeValue(1,4,7);
//        valueGraph.putEdgeValue(1,5,9);
//        valueGraph.putEdgeValue(2,3,6);
//        valueGraph.putEdgeValue(2,4,4);
//        valueGraph.putEdgeValue(2,5,3);
//        valueGraph.putEdgeValue(3,4,5);
//        valueGraph.putEdgeValue(3,6,10);
//        valueGraph.putEdgeValue(4,5,8);
//        valueGraph.putEdgeValue(4,6,3);
        MinSpanTree.Kruskal(valueGraph, null);
        MinSpanTree.Prim(valueGraph, null);
    }

}
