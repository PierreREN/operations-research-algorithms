package geneticalgorithm.scalingfunctions;

public class LinearScaling implements ScalingFunction {

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        double maxValue = maxOf(values);
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = maxValue - values[i];
        }
        return scaledValues;
    }

    private double maxOf(double[] values) {
        double maxFitness = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < values.length; i++) {
            if (maxFitness < values[i]) {
                maxFitness = values[i];
            }
        }
        return maxFitness;
    }

    private double minOf(double[] values) {
        double minFitness = Double.POSITIVE_INFINITY;
        for (int i = 0; i < values.length; i++) {
            if (minFitness > values[i]) {
                minFitness = values[i];
            }
        }
        return minFitness;
    }

}
