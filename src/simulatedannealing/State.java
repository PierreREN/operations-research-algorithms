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

import simulatedannealing.code.Code;
import simulatedannealing.code.SequenceCode;

/**
 *
 * @author Pierre REN
 */
public class State implements Cloneable {

    private double energy;
    private Code solution;

    public void initialize() {
        //Should be updated according to user's demand
        solution = new SequenceCode(4);
        solution.initialize();
        energy = solution.objectiveFunctionValue();
    }

    public State getNext(double T) {
        State next = new State();
        next.solution = solution.getNextSolution();
        next.energy = next.solution.objectiveFunctionValue();
        double delta = next.energy - this.energy;
        if (delta <= 0) {
            return next;
        } else if (Math.exp(-delta / T) > Math.random()) {
            return next;
        } else {
            return this;
        }
    }

    public double getEnergy() {
        return energy;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof State) {
                State s = (State) obj;
                if (s.solution.equals(solution) && s.energy == energy) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        State s = (State) super.clone();
        s.solution = (Code) this.solution.clone();
        s.energy = this.energy;
        return s;
    }

    public String toString() {
        String s = solution.toString() + "\tEnergy: " + energy;
        return s;
    }

}
