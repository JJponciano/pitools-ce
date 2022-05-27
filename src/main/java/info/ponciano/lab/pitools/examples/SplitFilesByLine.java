package info.ponciano.lab.pitools.examples;

import info.ponciano.lab.pitools.PiTools;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SplitFilesByLine {

    public static void main(String[] args) {
        String source="data/";
        String cname="size";
        String path ="data/f.csv";

        try {
            String[][] lines = PiTools.readCSV(path,",");
            for (String[]line:lines) {
                //creates a file corresponding to  the line id
                File f=new File(source+line[0]+".csv");
                StringBuilder info=new StringBuilder();
                if(f.exists())
                    info.append(PiTools.readTextFile(f.getPath())).append("\n");
                info.append(cname);
                for(String s:line)
                    info.append(",").append(s);
                PiTools.writeTextFile(f.getPath(),info.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
