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

package simulatedannealing.solver;

import basics.codefactory.CodeFactory;
import basics.codefactory.SequenceCodeFactory;
import basics.mutationbehaviors.MutationBehavior;
import basics.mutationbehaviors.TransposeMutator;
import basics.objectivefunctions.TSP_Evaluator;
import basics.testcases.Environment;
import basics.testcases.EnvironmentFactory;
import simulatedannealing.annealingsimulator.AnnealingSimulator;
import simulatedannealing.processcontroller.TemperatureController;
import simulatedannealing.state.State;
import simulatedannealing.state.StateUsingCodeFactory;

public class SimulatedAnnealingSolver {

    public static void main(String[] args) {

        Environment environment = EnvironmentFactory.getEnvironment();

        TemperatureController tc = new TemperatureController(1000, 0, 1, 100);

        int codeLength = environment.nodes().size() - 1;
        MutationBehavior mutator = new TransposeMutator(1);
        TSP_Evaluator scheduleEvaluator = new TSP_Evaluator(environment);
        CodeFactory codeFactory = new SequenceCodeFactory(codeLength, mutator, scheduleEvaluator);

        State s = new StateUsingCodeFactory(codeFactory);

        AnnealingSimulator simulator = new AnnealingSimulator();
        simulator.setTemperatureController(tc);
        simulator.setState(s);

        for (int i = 0; i < 100; i++) {
            simulator.initialize();
            simulator.anneal();
            simulator.showResult();
        }
    }
}