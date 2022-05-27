/* Copyright (C) Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info), 2015-2020, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
package info.ponciano.lab.pitools.crypto;

import info.ponciano.lab.pitools.PiTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 *
 * @author Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
 */
public class AllCombination implements Serializable {

    ArrayList<IntegerCombination> combinations = new ArrayList<>();
    /**
     *
     */
    private static final long serialVersionUID = 6425256172062015153L;

    public static AllCombination load(String path) throws FileNotFoundException {
        return PiTools.load(path);
    }

    public void save(String path) {
        PiTools.save(path,this);
    }

    /**
     * Calculate all combinations of the numbers between those given in the
     * parameters.
     *
     * @param startNumber starting number included
     * @param endnumber ending number included
     * @return List of combination.
     */
    public AllCombination(int startNumber, int endnumber) {
        String defaultDir = "allcombination/";
        new File(defaultDir).mkdir();
        String defaultPath = defaultDir + startNumber + "_" + endnumber + ".alc";

        // test first if the file exists
        try {
            this.combinations = load(defaultPath).getCombinations();
        } catch (Exception e) {

            if (startNumber > endnumber) {
                throw new IllegalArgumentException(
                        "start number(" + startNumber + ") upper than endnumber(" + endnumber + ")");
            }

            IntegerCombination integerCombination = new IntegerCombination(startNumber, endnumber);
            List<Integer> combination = integerCombination.getCombination();
//        for (int i = 0; i < combination.size(); i++) {
//            List<Integer> l = new ArrayList<>();
//            l.add(combination.get(i));
//            combinations.add(new IntegerCombination(l));
//            for (int j = i + 1; j < combination.size(); j++) {
//                List<Integer> copyOfL = new ArrayList<>();
//                copyOfL.addAll(l);
//                copyOfL.add(combination.get(j));
//                combinations.add(new IntegerCombination(copyOfL));
//            }
//        }
            combinations.addAll(recCombination(integerCombination));
//        for (int i = startNumber; i < endnumber; i++) {
//            //add combination of number from 
//            combinations.add(integerCombination);
//            combinations.add(new IntegerCombination(i, i));
//            for (int j = i + 1; j < endnumber + 1; j++) {
//                //add combination of number from i to endnumber excluded j;
//                combinations.add(new IntegerCombination(i, endnumber, j));
//            }
//        }
//        combinations.add(new IntegerCombination(endnumber, endnumber));
            this.save(defaultPath);
        }
    }

    /**
     * Recursive adding of all combination based on a combination
     *
     * @param integerCombination
     * @return
     */
    private static Collection<? extends IntegerCombination> recCombination(IntegerCombination integerCombination) {
        Set<IntegerCombination> all = new HashSet<>();
        List<Integer> combination = integerCombination.getCombination();
        all.add(integerCombination);
        if (combination.size() > 1) {
            for (int i = 0; i < combination.size(); i++) {
                List<Integer> c = new ArrayList<>();
                c.addAll(combination);
                c.remove(i);
                IntegerCombination ic = new IntegerCombination(c);
                all.addAll(recCombination(ic));
            }
        }
        return all;
    }

    public ArrayList<IntegerCombination> getCombinations() {
        return combinations;
    }

    public void setCombinations(ArrayList<IntegerCombination> combinations) {
        this.combinations = combinations;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((combinations == null) ? 0 : combinations.hashCode());
        return result;
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
        AllCombination other = (AllCombination) obj;
        if (combinations == null) {
            if (other.combinations != null) {
                return false;
            }
        } else if (!combinations.equals(other.combinations)) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            AllCombination all = new AllCombination(0, i);
            ArrayList<IntegerCombination> combinations = all.getCombinations();
//            combinations.forEach(integerCombination -> {
//                System.out.println(integerCombination);
//            });
            System.out.println(i+": "+combinations.size());
        }

    }

}
