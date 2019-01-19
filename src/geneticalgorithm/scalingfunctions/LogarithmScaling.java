package geneticalgorithm.scalingfunctions;

public class LogarithmScaling implements ScalingFunction {

    private double a;
    private double b;

    public LogarithmScaling(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = a * Math.log(values[i]) + b;
        }
        return scaledValues;
    }
}
