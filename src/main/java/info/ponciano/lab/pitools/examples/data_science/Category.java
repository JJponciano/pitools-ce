package info.ponciano.lab.pitools.examples.data_science;

import java.util.*;

public class Category {
    private final static boolean DEBUG = false;
    public final String name;
    public final Map<String, List<Individual>> individuals;
    Map<String, Integer> positions;
    Map<Integer, Integer> statistics;
    private int total;
    private int top10;
    private int top20;

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

    public void add(String refID, String comparedID, double value, boolean mean) {
        if (this.name.equals("size")) value = 1 - value;
        Individual ind = new Individual(refID, comparedID, value);
        if (!this.individuals.containsKey(refID)) {
            this.individuals.put(refID, new ArrayList<>());
        }
        List<Individual> individuals = this.individuals.get(refID);
        if (individuals.contains(ind)) {
            int index = individuals.indexOf(ind);
            Individual individual = individuals.get(index);
            double new_val;
            if (mean) {
                new_val = (individual.getValue() + ind.getValue()) / 2.0;
            } else {
                double min = Math.min(ind.getValue(), individual.getValue());
                if (min >= 0) {
                    if (DEBUG)
                        System.out.println("Update of  " + individual + " by " + ind + " -> " + min);
                    individual.setValue(min);
                }
                new_val = min;
            }
            individual.setValue(new_val);
        } else
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
        List<String> excepted = List.of(ignored);
        this.positions = new HashMap<>();
        this.individuals.forEach((k, v) -> {
            if (excepted.contains(k)) {
                System.out.println("Excepted: " + k);
            } else {
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
        Iterator<Integer> iterator = this.statistics.keySet().iterator();

        this.total = 0;
        this.top10 = 0;
        this.top20 = 0;
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            if (next >= 0) {
                Integer numbers = this.statistics.get(next);
                total += numbers;
                if (next <= 20) top20 += numbers;
                if (next <= 10) top10 += numbers;

            }

        }

    }

    public static List<Category> mergeMeanStd(List<Category> cats) {
        List<Category> cats_mstd = new ArrayList<>();
        cats_mstd.add(new Category("iss"));
        cats_mstd.add(new Category("icp"));
        cats_mstd.add(new Category("ch"));
        Category catsize=null;
        for(Category category:cats){
            if (category.getName().equals("size")) {
                catsize=category;
            }else
            category.getIndividuals().values().forEach((individuals) -> individuals.forEach(individual -> {
                cats_mstd.forEach(c -> {
                    if (category.getName().toLowerCase().contains(c.getName())) {
                        //ad the individual to the categorie
                        // if the individuals does not exists
                        c.add(individual.getReferenceID(), individual.getId(), individual.getValue(), true);
                    }
                });

            }));
        }
        cats_mstd.add(catsize);
        return cats_mstd;
    }

    public static Category merge(String name, List<Category> cats, String... excluding) {
        List<String> exclude = List.of(excluding);
        Category cat = new Category(name);
        cats.forEach(category -> {
            if (!exclude.contains(category.name))
                category.getIndividuals().values().forEach((individuals) -> individuals.forEach(individual -> cat.add(individual.getReferenceID(), individual.getId(), individual.getValue(), false)));
            else {
                System.out.println(category.name + " excluded");
            }
        });
        return cat;
    }

    @Override
    public String toString() {
        final StringBuilder r = new StringBuilder("Name: " + name);
        if (this.positions != null) {
            r.append("\n\nSTATISTICS on " + total + " scans\n");
            r.append("Top 10: " + top10 + " corresponding to " + (top10 * 100) / total + "% \n");
            r.append("Top 20: " + top20 + " corresponding to " + (top20 * 100) / total + "% \n");

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
