/*
 * Copyright 2019 Pierre REN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package basics.tools;

/**
 * @author Pierre REN
 */
public class RouletteWheel {

    double[] wheel;

    public RouletteWheel(double[] valueList) {
        wheel = new double[valueList.length];
        wheel[0] = valueList[0];
        for (int i = 1; i < valueList.length; i++) {
            wheel[i] = wheel[i - 1] + valueList[i];
        }
        if (wheel[wheel.length - 1] == 0) {
            for (int j = 0; j < wheel.length; j++) {
                wheel[j] = (j + 1) / wheel.length;
            }
        } else {
            for (int j = 0; j < wheel.length; j++) {
                wheel[j] = wheel[j] / wheel[wheel.length - 1];
            }
        }
    }

    public static void main(String[] args) {
        RouletteWheel rouletteWheel = new RouletteWheel(new double[]{1, 2, 3, 4});
        int i = 0;
        while (i++ < 10) {
            System.out.println(rouletteWheel.randomIndex());
        }
    }

    public int randomIndex() {
        int index = 0;
        double rand = Math.random();
        for (int j = 0; j < wheel.length; j++) {
            if (rand < wheel[j]) {
                index = j;
                break;
            }
        }
        return index;
    }

}
