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

package antcolonyalgorithm.solver;

import antcolonyalgorithm.antsystem.AntColonySystem;
import antcolonyalgorithm.antsystem.AntCycleModel;
import antcolonyalgorithm.processcontroller.AntsController;
import basics.testcases.Environment;
import basics.testcases.EnvironmentFactory;

public class AntsSolver {

    public static void main(String[] args) {
        AntsSolver antsSolver = new AntsSolver();
        antsSolver.solveWithACM();
        System.out.println("-------------------------#######################-----------------------------");
        antsSolver.solveWithACS();
    }

    public void solveWithACS() {

        Environment environment = EnvironmentFactory.getEnvironment();
        AntsController antsController = new AntsController();

        antsController.setIntegerParameters("maxIteration", 100);
        antsController.setIntegerParameters("colonySize", 20);
        antsController.setDoubleParameters("alpha", 1);
        antsController.setDoubleParameters("beta", 5);
        antsController.setDoubleParameters("rho", 0.1);
        antsController.setDoubleParameters("threshold", 0.9);

        AntColonySystem acs = new AntColonySystem(environment, antsController);
        for (int i = 0; i < 100; i++) {
            acs.initialize();
            acs.run();
            acs.showResult();
        }
    }

    public void solveWithACM() {

        Environment environment = EnvironmentFactory.getEnvironment();
        AntsController antsController = new AntsController();

        antsController.setIntegerParameters("maxIteration", 100);
        antsController.setIntegerParameters("colonySize", 20);
        antsController.setDoubleParameters("alpha", 1);
        antsController.setDoubleParameters("beta", 5);
        antsController.setDoubleParameters("Q", 100);

        AntCycleModel acm = new AntCycleModel(environment, antsController);
        for (int i = 0; i < 100; i++) {
            acm.initialize();
            acm.run();
            acm.showResult();
        }
    }
}
