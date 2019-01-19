package geneticalgorithm.scalingfunctions;

import java.util.Arrays;

public class DynamicLinearScaling implements ScalingFunction {

    private double[] A;
    private double[] B;

    public DynamicLinearScaling(double[] A, double[] B) {
        this.A = Arrays.copyOf(A, A.length);
        this.B = Arrays.copyOf(B, B.length);
    }

    @Override
    public double[] scale(double[] values) {
        double[] scaledValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            scaledValues[i] = A[i] * values[i] + B[i];
        }
        return scaledValues;
    }
}
