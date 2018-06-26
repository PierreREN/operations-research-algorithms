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
package tool;

import java.util.Collection;
import java.util.LinkedList;

/**
 *
 * @author Pierre REN
 */
public class NonredundantLinkedList<T> {

    LinkedList<T> nonredundantList;

    public NonredundantLinkedList() {
        nonredundantList = new LinkedList();
    }

    public NonredundantLinkedList(Collection<T> c) {
        nonredundantList = new LinkedList();
        for (T element : c) {
            add(element);
        }
    }

    public int size() {
        return nonredundantList.size();
    }

    public boolean add(T element) {
        for (int i = 0; i < nonredundantList.size(); i++) {
            if (nonredundantList.get(i).equals(element)) {
                return false;
            }
        }
        nonredundantList.add(element);
        return true;
    }

    public void clear() {
        nonredundantList.clear();
    }

    public T get(int index) {
        return nonredundantList.get(index);
    }

    public void removeDuplicates() {
        for (int i = 0; i < nonredundantList.size(); i++) {
            for (int j = i + 1; j < nonredundantList.size(); j++) {
                if (nonredundantList.get(i).equals(nonredundantList.get(j))) {
                    nonredundantList.remove(j--);
                }
            }
        }
    }

    public boolean checkRedundancy() {
        for (int i = 0; i < nonredundantList.size(); i++) {
            for (int j = i + 1; j < nonredundantList.size(); j++) {
                if (nonredundantList.get(i).equals(nonredundantList.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        return nonredundantList.toString();
    }

}
