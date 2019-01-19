package simulatedannealing.solver;

import basics.codefactory.CodeFactory;
import basics.codefactory.SequenceCodeFactory;
import basics.mutationbehaviors.TransposeMutation;
import basics.objectivefunctions.ObjectiveFunction;
import geneticalgorithm.solver.SequenceSolver;
import simulatedannealing.annealingsimulator.AnnealingSimulatorUsingTemperatureController;
import simulatedannealing.processcontroller.OneTemperatureController;
import simulatedannealing.processcontroller.TemperatureController;
import simulatedannealing.state.State;
import simulatedannealing.state.StateUsingCodeFactory;

public class TCASSolver {

    public static void calculateOptimalSchedules(CodeFactory codeFactory,
                                                 double initialTemperature,
                                                 double finalTemperature,
                                                 double coolingSchedule,
                                                 int repetitionSchedule) {

        AnnealingSimulatorUsingTemperatureController simulator = new AnnealingSimulatorUsingTemperatureController();

        TemperatureController tc = new OneTemperatureController(initialTemperature, finalTemperature, coolingSchedule, repetitionSchedule);
        simulator.setTemperatureController(tc);

        State s = new StateUsingCodeFactory(codeFactory);
        simulator.setState(s);

        simulator.initialize();
        simulator.anneal();
        simulator.showResult();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new SequenceSolver();
        }
        System.out.println("--------------------分割线--------------------");
        for (int i = 0; i < 100; i++) {
            calculateOptimalSchedules(new SequenceCodeFactory(4,
                            new TransposeMutation(),
                            new ScheduleEvaluator()),
                    100,
                    0,
                    1,
                    5);
        }
    }

}

class ScheduleEvaluator implements ObjectiveFunction {

    @Override
    public double evaluate(int[] code) {
        double value = 0;
        int[] processingTime = {8, 18, 5, 15};
        int n = code.length;
        for (int i = 0; i < code.length; i++) {
            value += processingTime[code[i] - 1] * n--;
        }
        return value;
    }
}