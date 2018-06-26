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
package geneticalgorithm.tool.node;

/**
 *
 * @author Pierre REN
 */
public class Coordinates {

    private double x;
    private double y;
    private double z;

    public Coordinates(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static double getDistance(Coordinates c1, Coordinates c2) {
        double distance = Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2) + Math.pow(c1.z - c2.z, 2));
        return distance;
    }

    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof Coordinates) {
            Coordinates coordinates = (Coordinates) obj;
            return x == coordinates.x && y == coordinates.y && z == coordinates.z;
        }
        return false;
    }

    public Object clone() {
        return new Coordinates(x, y, z);
    }

    public static void main(String[] args) {
        Coordinates coordinates = new Coordinates(1, 2, 3);
        Coordinates clone = (Coordinates) coordinates.clone();
        System.out.println(clone.equals(coordinates));
    }

}
