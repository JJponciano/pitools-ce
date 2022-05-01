package info.ponciano.lab.pitools.examples.data_science;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class Individual implements Comparator<Individual>, Comparable<Individual> {
    private String referenceID;
    private String id;
    private double value;

    public Individual(String referenceID, String id, double value) {
        this.referenceID = referenceID;
        this.id = id;
        this.value = value;
    }

    public String getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(String referenceID) {
        this.referenceID = referenceID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull Individual o) {
        return Double.compare(this.value, o.value);
    }

    @Override
    public int compare(Individual o1, Individual o2) {
            if(o1.value-o2.value<0)return -1;
            if(o1.value-o2.value>0)return 1;
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individual that = (Individual) o;

        if (!referenceID.equals(that.referenceID)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        int result = referenceID.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "referenceID='" + referenceID + '\'' +
                ", id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}