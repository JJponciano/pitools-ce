package info.ponciano.lab.pitools;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allow to compare the content of two txt file.
 */
public class ComparatorFiles {

    /** Compare the content of two files (one line to all others) and return common content
     * @param path1 path of the first file to compare
     * @param path2 path of the second file to compare
     * @param nbcharacters number of first character use to create the prefix for the comparison for each line (0 for complet matching)
     * @return list of the prefixes that are common to the second file
     */
    public static List<String> containsLine(String path1, String path2, int nbcharacters) throws IOException {
        List<String> common=new ArrayList<>();
        // read files
        List<String> l1 = PiTools.readAllLines(path1);
        List<String> l2 = PiTools.readAllLines(path2);

        for (String l:l1) {
            String prefix;
            //get the prefix
            if(nbcharacters>0)
                prefix=l.substring(0,nbcharacters);
            else
                prefix=l;
            //get the matching
            boolean match=false;
            int i=0;
            while (i<l2.size()&&!match){
                if (l2.get(i).startsWith(prefix))
                    match=true;
                else i++;
            }
            if(match&&!common.contains(prefix))
                common.add(prefix);
        }
        return  common;
    }

    public static void main(String[] args) {
        String f1 = PiTools.dialogOpenFile(null, new FileNameExtensionFilter("txt","txt"), "");
        String f2 = PiTools.dialogOpenFile(null, new FileNameExtensionFilter("txt","txt"), f1);
        try {
            List<String> common = ComparatorFiles.containsLine(f1, f2, 14);
            String s = PiTools.dialogSaveFile(null, new FileNameExtensionFilter("txt","txt"),f2);
            StringBuilder results= new StringBuilder(common.size() + "\n");
            for (String l : common) {
                results.append(l).append("\n");
            }
            PiTools.writeTextFile(s, results.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
