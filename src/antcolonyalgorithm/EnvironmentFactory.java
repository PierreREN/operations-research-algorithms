package antcolonyalgorithm;

import antcolonyalgorithm.node.Coordinates;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.util.LinkedList;

public class EnvironmentFactory {

    public static Environment getEnvironment() {
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
        return new Environment(distanceMap, pheromoneMap, 1);
    }

}
