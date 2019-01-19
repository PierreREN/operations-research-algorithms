/*
 * Copyright 2018 Pierre REN.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package basics.tools;

import java.util.Random;

public class Random2Index {

    public int index1;
    public int index2;

    public Random2Index(int L) {
        Random r = new Random();
        index1 = r.nextInt(L);
        index2 = index1;
        //c2>c1
        while (index1 == index2) {
            index2 = r.nextInt(L);
            if (index1 > index2) {
                int i0 = index1;
                index1 = index2;
                index2 = i0;
            }
        }
    }
}
