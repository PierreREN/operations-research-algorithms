package geneticalgorithm.scalingfunctions;

public class PowerLawScaling implements ScalingFunction {

    private double alpha;

    public PowerLawScaling(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = Math.pow(values[i], alpha);
        }
        return scaledValues;
    }
}
