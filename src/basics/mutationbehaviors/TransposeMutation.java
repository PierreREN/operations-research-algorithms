package basics.mutationbehaviors;

import basics.tools.Random2Index;

public class TransposeMutation implements MutationBehavior {
    @Override
    public void mutate(int[] array) {
        if (array.length > 1) {
            Random2Index r = new Random2Index(array.length);
            int code_2 = array[r.index2];
            array[r.index2] = array[r.index1];
            array[r.index1] = code_2;
        }
    }
}
