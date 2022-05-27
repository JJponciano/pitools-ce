package info.ponciano.lab.pitools.examples;

import info.ponciano.lab.pitools.PiTools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class allow to compare the content of two txt file.
 */
public class ComparatorFiles {

    /** Compare the content of two files (one line to all others) and return common or different content
     * @param path1 path of the first file to compare
     * @param path2 path of the second file to compare
     * @param getCommon true to get only common content, false to get only the different content.
     * @param characters number of first character use to create the prefix for the comparison for each line (0 for complet matching)
     * @return list of the prefixes that are common or different to the second file
     */
    public static List<String> compareLines(String path1, String path2, int characters,boolean getCommon) throws IOException {
        List<String> common=new ArrayList<>();
        // read files
        List<String> l1 = PiTools.readAllLines(path1);
        List<String> l2 = PiTools.readAllLines(path2);

        for (String l:l1) {
            String prefix;
            //get the prefix
            if(characters>0&&characters<l.length())
                prefix=l.substring(0,characters);
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
            //if it is different, inverse the matching
            if(!getCommon)match=!match;
            if(match&&!common.contains(prefix))
                common.add(prefix);
        }
        return  common;
    }


    public static void main(String[] args) {
        String f1 = PiTools.dialogOpenFile(null, new FileNameExtensionFilter("txt","txt"), "");
        String f2 = PiTools.dialogOpenFile(null, new FileNameExtensionFilter("txt","txt"), f1);
        try {
            // compare results and get common point if true, different if false
            int selection = JOptionPane.showConfirmDialog(null, "Do you want to extract common lines? ", "Selection", JOptionPane.YES_NO_OPTION);
            List<String> common = ComparatorFiles.compareLines(f1, f2, 11,selection==0);
            String s = PiTools.dialogSaveFile(null, new FileNameExtensionFilter("txt","txt"),f2);
            StringBuilder results= new StringBuilder(common.size() + "\n");
            for (String l : common) {
                results.append(l).append("\n");
            }
            PiTools.writeTextFile(s, results.toString());
            JOptionPane.showMessageDialog(null,"File saved:"+s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void copyFilesStartWith(String startwithfile, String dir, String dest) throws IOException {
        List<String> startw = PiTools.readAllLines(startwithfile);
        List<String> files = PiTools.lsName(new File(dir), "*", true);

        for (String f : files) {
            boolean startwith=false;
            int i=0;
            while (!startwith&&i<startw.size()){
                if(f.startsWith(startw.get(i)))startwith=true;
                else i++;
            }
            String input = dir + f;
            if(startwith){
                String df = dest + f;
                Files.copy(new File(input).toPath(),new File(df).toPath());
                System.out.println(input+"->"+df);
            }else
            System.out.println(input+"-> X");

        }
    }
}
