package basics.codefactory;

import basics.code.Code;
import basics.code.instances.IntegerCode;
import basics.mutationbehaviors.MutationBehavior;
import basics.objectivefunctions.ObjectiveFunction;
import basics.tools.Ranges;

public class IntegerCodeFactory implements CodeFactory {

    private Ranges ranges;
    private MutationBehavior mutator;
    private ObjectiveFunction objectiveFunction;

    public IntegerCodeFactory(Ranges ranges,
                              MutationBehavior mutator,
                              ObjectiveFunction objectiveFunction) {
        this.ranges = ranges;
        this.mutator = mutator;
        this.objectiveFunction = objectiveFunction;
    }

    @Override
    public Code generateCode() {
        return new IntegerCode(ranges, mutator, objectiveFunction);
    }

    @Override
    public boolean equals(Object object) {
        if (object != null) {
            if (object == this) {
                return true;
            } else if (object instanceof IntegerCodeFactory) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}