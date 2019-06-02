package antcolonyalgorithm.ant;

import antcolonyalgorithm.Environment;
import basics.tools.RouletteWheel;

import java.util.Iterator;
import java.util.Random;

public class SimpleAnt<Node> extends BasicAnt<Node> {

    private double alpha;
    private double beta;
    private double rho;
    private double threshold;

    public SimpleAnt(Environment environment, double alpha, double beta, double rho, double threshold) {
        super(environment);
        this.alpha = alpha;
        this.beta = beta;
        this.rho = rho;
        this.threshold = threshold;
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

            Random random = new Random();
            int indexOfNextNode = -1;
            if (random.nextDouble() <= threshold) {
                double maxArg = Double.NEGATIVE_INFINITY;
                for (int i = 0; i < transitionPower.length; i++) {
                    if (transitionPower[i] > maxArg) {
                        maxArg = transitionPower[i];
                        indexOfNextNode = i;
                    }
                }
            } else {
                RouletteWheel rouletteWheel = new RouletteWheel(transitionPower);
                indexOfNextNode = rouletteWheel.randomIndex();
            }
            currentNode = reachableNodes.get(indexOfNextNode);
            visitedNodes.add(currentNode);
            return true;
        }
    }

    @Override
    public void leavePheromoneLocally() {
        Iterator<Node> descendingVisitedNodesIterator = visitedNodes.descendingIterator();
        Node currentNode = descendingVisitedNodesIterator.next();
        Node precedentNode = descendingVisitedNodesIterator.next();
        environment.volatilizePheromone(precedentNode, currentNode, rho);
        environment.leavePheromone(precedentNode, currentNode, calculateLocalDeltaPheromone());
    }

    private double calculateLocalDeltaPheromone() {
        return rho * environment.getInitialPheromone();
    }

    private double calculateDeltaPheromone() {
        return environment.getVolatility() / getCyclePathLength();
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
}
