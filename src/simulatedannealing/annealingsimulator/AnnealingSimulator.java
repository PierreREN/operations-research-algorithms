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
package simulatedannealing.annealingsimulator;

import basics.tools.NonredundantLinkedList;
import simulatedannealing.state.State;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Pierre REN
 */
public class AnnealingSimulator {

    private TemperatureController tc;

    private State state;

    private double currentMinEnergy;
    private NonredundantLinkedList<State> globalMinimum;

    public void setTemperatureController(TemperatureController temperatureController) {
        tc = temperatureController;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void initialize() {
        state.initialize();
        currentMinEnergy = Double.POSITIVE_INFINITY;
        globalMinimum = new NonredundantLinkedList<>();
    }

    public void anneal() {
        NonredundantLinkedList<State> localMinimum = new NonredundantLinkedList<>();
        for (tc.initialize(); !tc.reachStoppingCriterion(); tc.update()) {
            double T = tc.getCurrentTemperature();
            for (int k = 0; k < tc.getRepetitionSchedule(); k++) {
                state = state.getNextState(T);
                if (state.getEnergy() < currentMinEnergy) {
                    currentMinEnergy = state.getEnergy();
                    localMinimum.clear();
                    try {
                        localMinimum.add((State) state.clone());//需要采用clone方法以保证保存的不是s的引用
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(AnnealingSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (state.getEnergy() == currentMinEnergy) {
                    try {
                        localMinimum.add((State) state.clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(AnnealingSimulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        globalMinimum = localMinimum;
    }

    public NonredundantLinkedList<State> getGlobalMinimum() {
        return globalMinimum;
    }

    public void showResult() {
        System.out.println(getGlobalMinimum());
    }

}
