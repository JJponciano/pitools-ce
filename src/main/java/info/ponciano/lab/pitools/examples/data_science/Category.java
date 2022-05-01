package info.ponciano.lab.pitools.examples.data_science;

import java.util.*;

public class Category {
    private final static boolean DEBUG=false;
    public final String name;
    public final Map<String, List<Individual>> individuals;
    Map<String, Integer> positions;
    Map<Integer, Integer> statistics;

    public Category(String name) {
        this.name = name;
        this.individuals = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, List<Individual>> getIndividuals() {
        return individuals;
    }

    public void add(String refID, String comparedID, double value) {
        Individual ind = new Individual(refID, comparedID, value);
        if (!this.individuals.containsKey(refID)) {
            this.individuals.put(refID, new ArrayList<>());
        }
        List<Individual> individuals = this.individuals.get(refID);
        if(individuals.contains(ind)){
            int index = individuals.indexOf(ind);
            Individual individual = individuals.get(index);
            double min = Math.min(ind.getValue(), individual.getValue());
            if(DEBUG)
                System.out.println("Update of  " + individual + " by "+ ind+ " -> "+min);
            individual.setValue(min);
        }else
        individuals.add(ind);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void sort() {
        this.individuals.values().forEach(Collections::sort);
    }

    public void calculateMetrics(int charComparisonCount, String... ignored) {
        List<String> excepted=List.of(ignored);
        this.positions = new HashMap<>();
        this.individuals.forEach((k, v) -> {
            if(excepted.contains(k)) {
                System.out.println("Excepted: " + k);
            }else{
                //get the first index of
                int ind = -1;
                int i = 0;
                while (ind < 0 && i < v.size()) {
                    Individual next = v.get(i);
                    String sub = k;
                    if (charComparisonCount > 0 && charComparisonCount < k.length()) {
                        sub = k.substring(0, charComparisonCount);
                    }
                    if (next.getId().startsWith(sub)) {
                        ind = i;
                    } else i++;
                }
                positions.put(k, ind);
            }
        });

        this.statistics = new HashMap<>();
        this.positions.values().forEach(p -> {
            if (!this.statistics.containsKey(p))
                this.statistics.put(p, 0);
            this.statistics.put(p, this.statistics.get(p) + 1);
        });
    }

    public static Category merge(String name, List<Category> cats, String... excluding) {
        List<String> exclude = List.of(excluding);
        Category cat = new Category(name);
        cats.forEach(category -> {
            if (!exclude.contains(category.name))
                category.getIndividuals().values().forEach((individuals) -> individuals.forEach(individual -> cat.add(individual.getReferenceID(), individual.getId(), individual.getValue())));
            else {
                System.out.println(category.name+" excluded");
            }
        });
        return cat;
    }

    @Override
    public String toString() {
        final StringBuilder r = new StringBuilder("Name: " + name);
        if (this.positions != null) {
            r.append("\n\nSTATISTICS:\n\n");
            this.statistics.forEach((k, p) -> r.append(k).append(",").append(p).append("\n"));
            r.append("\n\nPosition:\n\n");
            this.positions.forEach((k, p) -> r.append(k).append(",").append(p).append("\n"));
        }
        r.append("\n\nData:\n\n");
        this.individuals.forEach((k, inds) -> {
            r.append(k);
            inds.forEach(i -> r.append(",").append(i.getId()).append(",").append(i.getValue()));
            r.append("\n");
        });
        return r.toString();
    }
}
