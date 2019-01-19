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

/**
 * @author Pierre REN
 */
public class Random2Cutting {

    public int cutting1;
    public int cutting2;

    public Random2Cutting(int L) {
        Random r = new Random();
        cutting1 = r.nextInt(L + 1);
        cutting2 = cutting1;
        //cut2>cut1且cut1、cut2不同时为0和L
        while (cutting1 == cutting2 || cutting2 - cutting1 == L) {
            cutting2 = r.nextInt(L + 1);
            if (cutting1 > cutting2) {
                int c0 = cutting1;
                cutting1 = cutting2;
                cutting2 = c0;
            }
        }
    }
}
