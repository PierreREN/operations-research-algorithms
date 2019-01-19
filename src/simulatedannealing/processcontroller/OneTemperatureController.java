package simulatedannealing.processcontroller;

public class OneTemperatureController implements TemperatureController {

    private double initialTemperature;
    private double finalTemperature;
    private double coolingSchedule;
    private int repetitionSchedule;

    private double currentTemperature;

    public OneTemperatureController(double initialTemperature,
                                    double finalTemperature,
                                    double coolingSchedule,
                                    int repetitionSchedule) {
        this.initialTemperature = initialTemperature;
        currentTemperature = this.initialTemperature;
        setFinalTemperature(finalTemperature);
        setCoolingSchedule(coolingSchedule);
        setRepetitionSchedule(repetitionSchedule);
    }

    public void setFinalTemperature(double finalTemperature) {
        this.finalTemperature = finalTemperature;
    }

    public void setCoolingSchedule(double coolingSchedule) {
        this.coolingSchedule = coolingSchedule;
    }

    @Override
    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(double temperature) {
        this.currentTemperature = temperature;
    }

    @Override
    public int getRepetitionSchedule() {
        return repetitionSchedule;
    }

    public void setRepetitionSchedule(int repetitionSchedule) {
        this.repetitionSchedule = repetitionSchedule;
    }

    @Override
    public boolean reachStoppingCriterion() {
        return currentTemperature <= finalTemperature;
    }

    @Override
    public double getChangedTemperature() {
        currentTemperature -= coolingSchedule;
        return currentTemperature;
    }
}
