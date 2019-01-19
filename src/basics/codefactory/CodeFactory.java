package basics.codefactory;

import basics.code.Code;

public interface CodeFactory extends Cloneable {

    Code generateCode();

    boolean equals(Object object);

    Object clone() throws CloneNotSupportedException;

}
