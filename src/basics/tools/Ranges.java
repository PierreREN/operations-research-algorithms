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
                if (r.length == this.length
                        && Arrays.equals(r.lowerLimits, this.lowerLimits)
                        && Arrays.equals(r.upperLimits, this.upperLimits)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        Ranges ranges = (Ranges) super.clone();
        ranges.length = this.length;
        ranges.lowerLimits = Arrays.copyOf(this.lowerLimits, length);
        ranges.upperLimits = Arrays.copyOf(this.upperLimits, length);
        return ranges;
    }

}
