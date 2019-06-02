package antcolonyalgorithm.ant;

import antcolonyalgorithm.Environment;
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
