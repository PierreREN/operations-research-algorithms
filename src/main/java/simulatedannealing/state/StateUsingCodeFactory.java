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
package simulatedannealing.state;

import basics.code.Code;
import basics.codefactory.CodeFactory;

/**
 * @author Pierre REN
 */
public class StateUsingCodeFactory implements State {

    private CodeFactory codeFactory;
    private Code code;
    private double energy;

    public StateUsingCodeFactory(CodeFactory codeFactory) {
        this.codeFactory = codeFactory;
    }

    public void initialize() {
        code = codeFactory.generateCode();
        code.initialize();
        energy = code.objectiveFunctionValue();
    }

    public State getNextState(double T) {
        Code nextCode = (Code) code.clone();
        nextCode.mutate();
        double nextEnergy = nextCode.objectiveFunctionValue();
        double delta = nextEnergy - this.energy;
        if (delta <= 0 || (Math.random() < Math.exp(-delta / T))) {
            this.code = nextCode;
            this.energy = nextEnergy;
        }
        return this;
    }

    public double getEnergy() {
        return energy;
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj == this) {
                return true;
            } else if (obj instanceof StateUsingCodeFactory) {
                StateUsingCodeFactory s = (StateUsingCodeFactory) obj;
                return s.code.equals(code) && s.getEnergy() == energy
                        && s.codeFactory.equals(codeFactory);
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        StateUsingCodeFactory s = new StateUsingCodeFactory(codeFactory);
        s.code = (Code) this.code.clone();
        s.energy = this.energy;
        return s;
    }

    public String toString() {
        return code.toString() + "\tEnergy: " + energy;
    }

    public Code getCode() {
        return code;
    }

}
