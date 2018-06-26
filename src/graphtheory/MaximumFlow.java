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
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Pierre REN
 */
public class MaximumFlow {

    public static <N> void introduction(MutableValueGraph<N, Double> valueGraph, N source, N sink) {
        final int N = valueGraph.nodes().size();
        ArrayList<N> nodes = new ArrayList(valueGraph.nodes());
        double[][] residuals = new double[N][N];
        double[][] initialCapacities = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    residuals[i][j] = 0;
                    initialCapacities[i][j] = residuals[i][j];
                } else {
                    residuals[i][j] = valueGraph.edgeValue(nodes.get(i), nodes.get(j)).orElse(0.0);
                    initialCapacities[i][j] = residuals[i][j];
                }
//                System.out.print(residuals[i][j]+"\t");
            }
//            System.out.println();
        }
        LinkedList<Double> pathFlowRecord = new LinkedList();
        Integer index = nodes.indexOf(source);
        LinkedList<Integer> path = new LinkedList();
        path.add(index);
        Map<Integer, LinkedList<Integer>> augmentingNodes = new HashMap();
        augmentingNodes.put(index, augmentingNodes(residuals, index, path));
        while (!augmentingNodes.get(nodes.indexOf(source)).isEmpty()) {
            while (index != nodes.indexOf(sink)) {
                if (augmentingNodes.get(index).isEmpty()) {
//                    System.out.println(index+" "+augmentingNodesOfCurrentNode+"EMPTY!!!!!!!!!!!!!");
                    if (index == nodes.indexOf(source)) {
                        break;
                    } else {
                        Integer returnedIndex = index;
                        path.remove(returnedIndex);
                        index = path.getLast();
                        augmentingNodes.get(index).remove(returnedIndex);
//                        System.out.println(index+" "+path.getLast());
//                        System.out.println(augmentingNodes);
                    }
                } else {
                    index = indexOfNextNode(residuals, index, augmentingNodes.get(index));
                    path.add(index);
                    augmentingNodes.put(index, augmentingNodes(residuals, index, path));
                }
            }
//            需要更新residuals矩阵！！！！！
            double pathFlow = Double.POSITIVE_INFINITY;
            for (int i = 0; i < path.size() - 1; i++) {
                if (residuals[path.get(i)][path.get(i + 1)] < pathFlow) {
                    pathFlow = residuals[path.get(i)][path.get(i + 1)];
                }
            }
            for (int i = 0; i < path.size() - 1; i++) {
                residuals[path.get(i)][path.get(i + 1)] -= pathFlow;
                residuals[path.get(i + 1)][path.get(i)] += pathFlow;
            }
//            for (int i=0;i<N;i++){
//                for (int j=0;j<N;j++){
//                    System.out.print(residuals[i][j]+"\t");
//                }
//                System.out.println();
//            }
            pathFlowRecord.add(pathFlow);
            index = nodes.indexOf(source);
            path.clear();
            path.add(index);
            augmentingNodes.clear();
            augmentingNodes.put(index, augmentingNodes(residuals, index, path));
        }
        double maximumFlow = 0;
        for (int i = 0; i < pathFlowRecord.size(); i++) {
            maximumFlow += pathFlowRecord.get(i);
        }
        System.out.println("[Maximum Flow] " + maximumFlow);
        MutableValueGraph<N, Double> flowGraph = ValueGraphBuilder.directed().allowsSelfLoops(true).build();
        for (int i = 0; i < residuals.length; i++) {
            for (int j = 0; j < residuals[0].length; j++) {
                double flow = initialCapacities[i][j] - residuals[i][j];
                if (flow > 0) {
                    flowGraph.putEdgeValue(nodes.get(i), nodes.get(j), flow);
                } else if (flow < 0) {
                    flowGraph.putEdgeValue(nodes.get(j), nodes.get(i), -flow);
                }
            }
        }
        System.out.println(flowGraph);
    }

    private static LinkedList<Integer> augmentingNodes(double[][] residuals, int index, LinkedList<Integer> path) {
        LinkedList<Integer> augmentingNodes = new LinkedList();
        for (int j = 0; j < residuals[index].length; j++) {
            if (residuals[index][j] > 0) {
                augmentingNodes.add(j);
            }
        }
        augmentingNodes.removeAll(path);
        return augmentingNodes;
    }

    private static int indexOfNextNode(double[][] residuals, int index, LinkedList<Integer> augmentingNodes) {
        int indexOfNextNode = -1;
        double maxResidual = 0;
        for (int j = 0; j < augmentingNodes.size(); j++) {
            if (residuals[index][augmentingNodes.get(j)] > maxResidual) {
                indexOfNextNode = augmentingNodes.get(j);
                maxResidual = residuals[index][augmentingNodes.get(j)];
            }
        }
//        System.out.println("[indexOfNextNode] "+indexOfNextNode+" "+maxResidual);
        return indexOfNextNode;
    }

    public static void main(String[] args) {
        MutableValueGraph<Integer, Double> valueGraph = ValueGraphBuilder.directed().allowsSelfLoops(true).build();
        valueGraph.putEdgeValue(1, 2, 20.0);
        valueGraph.putEdgeValue(1, 3, 30.0);
        valueGraph.putEdgeValue(1, 4, 10.0);
        valueGraph.putEdgeValue(2, 1, 0.0);
        valueGraph.putEdgeValue(2, 3, 40.0);
        valueGraph.putEdgeValue(2, 5, 30.0);
        valueGraph.putEdgeValue(3, 1, 0.0);
        valueGraph.putEdgeValue(3, 2, 0.0);
        valueGraph.putEdgeValue(3, 4, 10.0);
        valueGraph.putEdgeValue(3, 5, 20.0);
        valueGraph.putEdgeValue(4, 1, 0.0);
        valueGraph.putEdgeValue(4, 3, 5.0);
        valueGraph.putEdgeValue(4, 5, 20.0);
        valueGraph.putEdgeValue(5, 2, 0.0);
        valueGraph.putEdgeValue(5, 3, 0.0);
        valueGraph.putEdgeValue(5, 4, 0.0);

        introduction(valueGraph, 1, 5);
    }

}
