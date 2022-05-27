package info.ponciano.lab.pitools.examples.data_science;

import info.ponciano.lab.pitools.PiTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class helps in data science analysis based on CSV file.
 */
public class Datanalyse {
    private  List<Category> categories;

    public Datanalyse() {
        this.categories = new ArrayList<>();
    }
    public void save(String dir,String... excepted){
        File directory=new File(dir);
        if(directory.exists()) PiTools.deleteDirectory(directory);
        boolean mkdir = directory.mkdir();
        this.categories.forEach(c->PiTools.writeTextFile(directory+"/"+c.getName()+".csv",c.toString()));
        Category summary = Category.merge("Results",this.categories,"size");
        summary.sort();
        summary.calculateMetrics(7,excepted);
        PiTools.writeTextFile(directory+"/"+summary.getName()+".csv",summary.toString());
    }
    public void loadCSV(String directory, boolean recursively,String... excepted) throws IOException {
        List<File> csvs = PiTools.lsFiles(new File(directory), "csv", recursively);
        //read each csv file
        for (File csv : csvs) {
            String[][] data = PiTools.readCSV(csv.getPath(), ",");
            // fist value is the category, second value the id reference, third the id for the comparison, and fourth the comparison value
            for (String[] row : data) {
                String cname = row[0].toLowerCase().replaceAll("\\s","_");
                Category cat = new Category(cname);
                if (!categories.contains(cat)) categories.add(cat);
                else cat = this.categories.get(this.categories.indexOf(cat));
                String refid = row[1];
                for (int i = 2; i < row.length - 1; i++) {
                    String id = row[i];
                    i++;
                    double value = Double.parseDouble(row[i]);
                    cat.add(refid, id, value,false);
                }
            }
        }
        this.categories=Category.mergeMeanStd(this.categories);
        this.categories.forEach(c->{c.sort();c.calculateMetrics(7,excepted);});
    }

    public static void main(String[] args) {
        String dir="results\\";
        String res=dir+"rf\\";
        String []excepted={"qw-01" };
        Datanalyse csv=new Datanalyse();
        try {
            csv.loadCSV(dir, true,excepted);
            csv.save(res,excepted);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
