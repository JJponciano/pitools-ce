/* Copyright (C) Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info), 2015-2020, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package info.ponciano.lab.pitools.crypto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 *
 * @author Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
 */
public class IntegerCombination implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7690821735678606322L;
	protected List<Integer> combination;



    public IntegerCombination(List<Integer> combination) {
        this.combination = combination;
    }

    /**
     * Add linear combination from {@code startNumber} to {@code ndnumber}
     *
     * @param startNumber starting number
     * @param endnumber ending number
     */
    IntegerCombination(int startNumber, int endnumber) {
        this.combination = new ArrayList<>();
        for (int i = startNumber; i < endnumber+1; i++) {
            this.combination.add(i);
        }
    }

    /**
     * Add linear combination from {@code startNumber} to {@code ndnumber} excluded numbers {@code excluded}
     *
     * @param startNumber starting number
     * @param endnumber ending number
     * @param excluded excluded number which are remove from the sequence.
     */
    IntegerCombination(int startNumber, int endnumber, int... excluded) {
        this.combination = new ArrayList<>();
        for (int i = startNumber; i < endnumber+1; i++) {
            if (this.notExcluded(i, excluded)) {
                this.combination.add(i);
            }
        }
    }

    public List<Integer> getCombination() {
        return combination;
    }

    private boolean notExcluded(int i, int[] excluded) {
        for (int j : excluded) {
            if (i == j) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.combination);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IntegerCombination other = (IntegerCombination) obj;
        if (!Objects.equals(this.combination, other.combination)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return "{" +  combination + '}';
    }

}
       
