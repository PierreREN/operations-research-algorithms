package simulatedannealing.annealingsimulator;

import basics.processcontroller.ProcessController;

public class TemperatureController implements ProcessController {

    private double initialTemperature;
    private double finalTemperature;
    private double coolingSchedule;
    private int repetitionSchedule;

    private double currentTemperature;

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
}
