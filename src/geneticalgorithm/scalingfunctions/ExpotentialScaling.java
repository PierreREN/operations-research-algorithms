package geneticalgorithm.scalingfunctions;

public class ExpotentialScaling implements ScalingFunction {

    private double a;
    private double b;
    private double c;

    public ExpotentialScaling(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = a * Math.exp(b * values[i]) + c;
        }
        return scaledValues;
    }
}
