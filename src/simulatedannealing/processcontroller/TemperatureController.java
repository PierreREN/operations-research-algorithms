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

package simulatedannealing.processcontroller;

import basics.processcontroller.ProcessController;

public class TemperatureController implements ProcessController {

    private double initialTemperature;
    private double finalTemperature;
    private double coolingSchedule;
    private int repetitionSchedule;

    private double currentTemperature;
    private double minEnergy;

    public TemperatureController(double initialTemperature,
                                 double finalTemperature,
                                 double coolingSchedule,
                                 int repetitionSchedule) {
        this.initialTemperature = initialTemperature;
        this.finalTemperature = finalTemperature;
        this.coolingSchedule = coolingSchedule;
        this.repetitionSchedule = repetitionSchedule;
    }

    @Override
    public void initialize() {
        currentTemperature = this.initialTemperature;
        minEnergy = Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean reachStoppingCriterion() {
        return currentTemperature <= finalTemperature;
    }

    @Override
    public void update() {
        currentTemperature -= coolingSchedule;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public int getRepetitionSchedule() {
        return repetitionSchedule;
    }

    @Override
    public boolean equalToCurrentOptimum(double value) {
        return value == minEnergy;
    }

    @Override
    public boolean betterThanCurrentOptimum(double value) {
        return value < minEnergy;
    }

    @Override
    public double getOptimum() {
        return minEnergy;
    }

    @Override
    public void setOptimum(double objectiveFunctionValue) {
        this.minEnergy = objectiveFunctionValue;
    }
}
