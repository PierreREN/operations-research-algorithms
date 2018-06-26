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
package simulatedannealing;

import java.util.logging.Level;
import java.util.logging.Logger;
import tool.NonredundantLinkedList;

/**
 *
 * @author Pierre REN
 */
public class SimulatedAnnealing {

    private final int initialTemperature = 100;
    private final int finalTemperature = 0;
    private final int coolingSchedule = 10;
    private final int repetitionSchedule = 5;

    private double T;
    private double currentMinEnergy = Double.POSITIVE_INFINITY;
    private NonredundantLinkedList<State> globalMinimum;

    State state;

    public SimulatedAnnealing(State initialState) {
        state = initialState;
        globalMinimum = new NonredundantLinkedList();
    }

    public void anneal() {
        NonredundantLinkedList<State> localMinimum = new NonredundantLinkedList();
        for (T = initialTemperature; !stoppingCriterion(); temperatureChange()) {
            for (int k = 0; k < repetitionSchedule; k++) {
                state = state.getNext(T);
                if (state.getEnergy() < currentMinEnergy) {
                    currentMinEnergy = state.getEnergy();
                    localMinimum.clear();
                    try {
                        localMinimum.add((State) state.clone());//需要采用clone方法以保证保存的不是s的引用
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(SimulatedAnnealing.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (state.getEnergy() == currentMinEnergy) {
                    try {
                        localMinimum.add((State) state.clone());
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(SimulatedAnnealing.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        globalMinimum = localMinimum;
    }

    public void getResult() {
        System.out.println(globalMinimum);
    }

    private boolean stoppingCriterion() {
        return T < finalTemperature;
    }

    private void temperatureChange() {
        T -= coolingSchedule;
    }

    public static void main(String[] args) {
        State s = new State();
        s.initialize();
        SimulatedAnnealing simulator = new SimulatedAnnealing(s);
        simulator.anneal();
        simulator.getResult();
    }

}
