package basics.mutationbehaviors;

import java.util.Random;

public class ShiftMutation implements MutationBehavior {

    @Override
    public void mutate(int[] array) {
        Random r = new Random();
        int index = 1 + r.nextInt(array.length - 1);
        int code_0 = array[index];
        for (int i = index; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = code_0;
    }
}
