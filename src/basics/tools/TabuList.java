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

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Pierre REN
 */
public class TabuList<T> implements Cloneable {

    int maxLength;
    LinkedList<T> tabuList;

    public TabuList() {
        maxLength = Integer.MAX_VALUE;
        tabuList = new LinkedList();
    }

    public TabuList(int maxLength) {
        this.maxLength = maxLength;
        tabuList = new LinkedList();
    }

    public static void main(String[] args) {
        TabuList tabuList = new TabuList(5);
        for (int i = 1; i <= 10; i++) {
            tabuList.add(i);
            System.out.println(tabuList);
        }
    }

    public void add(T element) {
        if (tabuList.size() < maxLength) {
            tabuList.add(element);
        } else {
            tabuList.removeFirst();
            tabuList.addLast(element);
        }
    }

    public boolean contains(T element) {
        return tabuList.contains(element);
    }

    public T getFirst() {
        return tabuList.getFirst();
    }

    public Iterator<T> iterator() {
        return tabuList.iterator();
    }

    public int size() {
        return tabuList.size();
    }

    public Object clone() throws CloneNotSupportedException {
        TabuList clonedTabuList = (TabuList) super.clone();
        clonedTabuList.tabuList = (LinkedList) tabuList.clone();
        return clonedTabuList;
    }

    public String toString() {
        String s = "TabuList maximum legnth: " + maxLength + "\t" + tabuList.toString();
        return s;
    }

}
