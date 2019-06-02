package antcolonyalgorithm.antsystem;

import antcolonyalgorithm.Environment;
import antcolonyalgorithm.EnvironmentFactory;
import antcolonyalgorithm.ant.SimpleAnt;
import antcolonyalgorithm.antsystemcontroller.AntsController;
import basics.tools.NonredundantLinkedList;

import java.util.LinkedList;

public class AntColonySystem {

    private Environment environment;
    private AntsController antsController;

    private LinkedList<SimpleAnt> ants;

    private double minCyclePathLength = Double.POSITIVE_INFINITY;
    private NonredundantLinkedList<LinkedList> shortestCyclePaths;

    public AntColonySystem(Environment environment, AntsController antsController) {
        this.environment = environment;
        this.antsController = antsController;
    }

    public static void main(String[] args) {

        Environment environment = EnvironmentFactory.getEnvironment();
        AntsController antsController = new AntsController();

        antsController.setIntegerParameters("maxIteration", 200);
        antsController.setIntegerParameters("colonySize", 50);
        antsController.setDoubleParameters("alpha", 1);
        antsController.setDoubleParameters("beta", 5);
        antsController.setDoubleParameters("rho", 0.1);
        antsController.setDoubleParameters("threshold", 0.9);

        AntColonySystem acs = new AntColonySystem(environment, antsController);
        acs.initialize();
        acs.run();
        acs.showResult();
    }

    public void initialize() {
        double alpha = antsController.getDoubleParameter("alpha");
        double beta = antsController.getDoubleParameter("beta");
        double rho = antsController.getDoubleParameter("rho");
        double threshold = antsController.getDoubleParameter("threshold");
        int colonySize = antsController.getIntegerParameter("colonySize");

        ants = new LinkedList<>();
        shortestCyclePaths = new NonredundantLinkedList<>();
        for (int i = 0; i < colonySize; i++) {
            ants.add(new SimpleAnt(environment, alpha, beta, rho, threshold));
        }
    }

    public void run() {
        for (antsController.initialize(); !antsController.reachStoppingCriterion(); antsController.update()) {
            allAntsInitialize();
            int maxInnerIteration = environment.nodes().size();
            for (int i = 0; i < maxInnerIteration; i++) {
                allAntsStepForward();
                allAntsLeavePheromonesLocally();
            }
            environment.volatilizePheromones();
            for (SimpleAnt simpleAnt : ants) {
                double cyclePathLength = simpleAnt.getCyclePathLength();
                if (cyclePathLength < minCyclePathLength) {
                    minCyclePathLength = cyclePathLength;
                    shortestCyclePaths.clear();
                    shortestCyclePaths.add(simpleAnt.getVisitedPath());
                } else if (cyclePathLength == minCyclePathLength
                        && cyclePathLength != Double.POSITIVE_INFINITY) {
                    shortestCyclePaths.add(simpleAnt.getVisitedPath());
                }
            }
            leavePheromonesGlobally();
            showLocalResult();
        }
    }

    private void allAntsInitialize() {
        for (SimpleAnt simpleAnt : ants) {
            simpleAnt.initialize();
        }
    }

    private void allAntsStepForward() {
        for (SimpleAnt simpleAnt : ants) {
            simpleAnt.moveToNextNode();
        }
    }

    private void allAntsLeavePheromonesLocally() {
        for (SimpleAnt simpleAnt : ants) {
            simpleAnt.leavePheromoneLocally();
        }
    }

    private void leavePheromonesGlobally() {
        double meanCyclePathLength = 0;
        for (SimpleAnt simpleAnt : ants) {
            meanCyclePathLength += simpleAnt.getCyclePathLength();
        }
        meanCyclePathLength /= ants.size();
        for (int i = 0; i < shortestCyclePaths.size(); i++) {
            LinkedList shortestCyclePath = shortestCyclePaths.get(i);
            for (int j = 0; j < shortestCyclePath.size() - 1; j++) {
                environment.leavePheromone(shortestCyclePath.get(j), shortestCyclePath.get(j + 1), environment.getVolatility() * meanCyclePathLength / minCyclePathLength);
            }
        }
    }

    private void localOptimalAntsLeavePheromonesGlobally() {
        LinkedList<SimpleAnt> antsWithShortestLocalCyclePath = new LinkedList<>();
        double localMinCyclePathLength = Double.POSITIVE_INFINITY;
        for (SimpleAnt simpleAnt : ants) {
            double cyclePathLength = simpleAnt.getCyclePathLength();
            if (cyclePathLength < localMinCyclePathLength) {
                localMinCyclePathLength = cyclePathLength;
                antsWithShortestLocalCyclePath.clear();
                antsWithShortestLocalCyclePath.add(simpleAnt);
            } else if (cyclePathLength == localMinCyclePathLength
                    && cyclePathLength != Double.POSITIVE_INFINITY) {
                antsWithShortestLocalCyclePath.add(simpleAnt);
            }
        }
        for (SimpleAnt simpleAnt : antsWithShortestLocalCyclePath) {
            simpleAnt.leavePheromonesGlobally();
            double cyclePathLength = simpleAnt.getCyclePathLength();
            if (cyclePathLength < minCyclePathLength) {
                minCyclePathLength = cyclePathLength;
                shortestCyclePaths.clear();
                shortestCyclePaths.add(simpleAnt.getVisitedPath());
            } else if (cyclePathLength == minCyclePathLength) {
                shortestCyclePaths.add(simpleAnt.getVisitedPath());
            }
        }
    }

    private void showLocalResult() {
        System.out.println("Shortest Cycle Paths in Iteration [" + antsController.getCurrentIteration() + "] with Length " + minCyclePathLength + ":\t" + shortestCyclePaths);
    }

    public void showResult() {
        System.out.println("Globally Shortest Cycle Paths with Length " + minCyclePathLength + ":\t" + shortestCyclePaths);
    }
}
