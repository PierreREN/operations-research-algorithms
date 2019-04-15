package basics.mutationbehaviors;

import basics.tools.Ranges;

import java.util.Random;

public class RangesMutation implements MutationBehavior {

    private Ranges ranges;
    private double Pm;

    public RangesMutation(Ranges ranges, double Pm) {
        this.ranges = ranges;
        this.Pm = Pm;
    }

    @Override
    public void mutate(int[] array) {
        for (int i = 0; i < ranges.getLength(); i++) {
            if (Math.random() < Pm) {
                Random r = new Random();
                array[i] = ranges.getLowerLimits()[i] +
                        r.nextInt(ranges.getUpperLimits()[i] - ranges.getLowerLimits()[i]);
            }
        }
    }
}
