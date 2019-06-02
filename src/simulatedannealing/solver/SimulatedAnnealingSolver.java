package simulatedannealing.solver;

import basics.codefactory.CodeFactory;
import basics.codefactory.SequenceCodeFactory;
import basics.mutationbehaviors.MutationBehavior;
import basics.mutationbehaviors.TransposeMutation;
import basics.objectivefunctions.ObjectiveFunction;
import simulatedannealing.annealingsimulator.AnnealingSimulator;
import simulatedannealing.annealingsimulator.TemperatureController;
import simulatedannealing.state.State;
import simulatedannealing.state.StateUsingCodeFactory;

public class SimulatedAnnealingSolver {

    public static void main(String[] args) {

        TemperatureController tc = new TemperatureController(100, 0, 1, 5);

        int codeLength = 4;
        MutationBehavior mutator = new TransposeMutation();
        ScheduleEvaluator scheduleEvaluator = new ScheduleEvaluator();
        CodeFactory codeFactory = new SequenceCodeFactory(codeLength, mutator, scheduleEvaluator);

        State s = new StateUsingCodeFactory(codeFactory);

        AnnealingSimulator simulator = new AnnealingSimulator();
        simulator.setTemperatureController(tc);
        simulator.setState(s);

        simulator.initialize();
        simulator.anneal();
        simulator.showResult();
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