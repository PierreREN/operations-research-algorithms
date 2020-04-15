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

import java.util.Arrays;

public class Ranges implements Cloneable {

    private int length;
    private int[] lowerLimits;
    private int[] upperLimits;

    public Ranges(int length) {
        this.length = length;
    }

    public Ranges(int length, int Nmax) {
        this.length = length;
        lowerLimits = new int[length];
        upperLimits = new int[length];
        for (int i = 0; i < length; i++) {
            upperLimits[i] = Nmax;
        }
    }

    public Ranges(int length, int Nmin, int Nmax) {
        this.length = length;
        lowerLimits = new int[length];
        upperLimits = new int[length];
        for (int i = 0; i < length; i++) {
            lowerLimits[i] = Nmin;
            upperLimits[i] = Nmax;
        }
    }

    public Ranges(int[] Nmax) {
        this.length = Nmax.length;
        lowerLimits = new int[Nmax.length];
        upperLimits = new int[Nmax.length];
        for (int i = 0; i < Nmax.length; i++) {
            upperLimits[i] = Nmax[i];
        }
    }

    public Ranges(int[] Nmin, int[] Nmax) {
        try {
            if (Nmin.length != Nmax.length) {
                throw new Exception("Inequal lengths of AbstractClasses and bound");
            }
            this.length = Nmax.length;
            lowerLimits = new int[Nmax.length];
            upperLimits = new int[Nmax.length];
            for (int i = 0; i < Nmax.length; i++) {
                lowerLimits[i] = Nmin[i];
                upperLimits[i] = Nmax[i];
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[] getLowerLimits() {
        return lowerLimits;
    }

    public void setLowerLimits(int[] lowerLimits) {
        this.lowerLimits = lowerLimits;
    }

    public int[] getUpperLimits() {
        return upperLimits;
    }

    public void setUpperLimits(int[] upperLimits) {
        this.upperLimits = upperLimits;
    }

    public boolean equals(Object object) {
        if (object != null) {
            if (object == this) {
                return true;
            } else if (object instanceof Ranges) {
                Ranges r = (Ranges) object;
                return r.length == this.length
                        && Arrays.equals(r.lowerLimits, this.lowerLimits)
                        && Arrays.equals(r.upperLimits, this.upperLimits);
            }
        }
        return false;
    }

    public Object clone() {
        Ranges ranges = null;
        try {
            ranges = (Ranges) super.clone();
            ranges.length = this.length;
            ranges.lowerLimits = Arrays.copyOf(this.lowerLimits, length);
            ranges.upperLimits = Arrays.copyOf(this.upperLimits, length);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ranges;
    }

}
