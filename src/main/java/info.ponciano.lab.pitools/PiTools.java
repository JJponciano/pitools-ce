package info.ponciano.lab.pitools;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PiTools {
    /**
     * Converts the time value in ms to understandable String (h,m,s,ms)
     *
     * @param time time in millisecond to be converted
     * @return string in format : (h,m,s,ms)
     */
    public static String timeToString(long time) {
        String res = "";
        int sec = 1000;
        int min = 60 * sec;
        int hour = 60 * min;
        //get the number of hours
        int resHour = (int) (time / hour);
        //if the time is in hours
        if (resHour > 0) {
            res += resHour + "h, ";
            //remove hours of the time value
            time = time % hour;
        }

        //get the number of min
        int resMin = (int) (time / min);
        //if the time is in hours
        if (resMin > 0) {
            res += resMin + "m, ";
            //remove hours of the time value
            time = time % min;
        }
        //get the number of min
        int resSec = (int) (time / sec);
        //if the time is in hours
        if (resSec > 0) {
            res += resSec + "s, ";
            //remove hours of the time value
            time = time % sec;
        }
        res += time + "ms";
        return res;
    }

    /**
     * Selects with GUI a file
     *
     * @param parent the parent component of the dialog, can be null but never
     * <code>new Frame()</code>.
     * @param filter filter to apply such as (txt,csv).
     * @param parent_path path of the directory where the gui should display first.
     * @return The pathname of the file selected or a empty string if no file is
     * selected.
     */
    public static String dialogOpenFile(final Component parent, final FileNameExtensionFilter filter, final String parent_path) {
        final File file = new File(parent_path);
        final JFileChooser fc = new JFileChooser(file);
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileFilter(filter);

        final int returnVal = fc.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        }
        fc.setEnabled(false);
        return "";
    }
    /**
     * Selects with GUI a directory
     *
     * @param parent the parent component of the dialog, can be null.
     * @param title windows title
     * @return The pathname of the file selected or a empty string if no file is
     * selected.
     */
    public static String dialogOpenDir(final Component parent, final String title, final String parent_path) {

        final DirChooser dirC = new DirChooser(title, new File(parent_path));
        return dirC.run();
    }
    /**
     * Selects with GUI a file
     *
     * @param parent the parent component of the dialog, can be null.
     * @return The pathname of the file selected or a empty string if no file is
     * selected.
     */
    public static String  dialogOpenFile(final Component parent, final String parent_path) {

        final File fileio = new File(parent_path);
        final JFileChooser fc = new JFileChooser(fileio);
        final int returnVal = fc.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }
    /**
     * Selects with GUI a file path and name to save a file
     *
     * @param parent the parent component of the dialog, can be null.
     * @param filter filter to be apply.
     * @return the path of the file if a file isselected. Empty string
     * otherwise.
     */
    public static String dialogSaveFile(final Component parent, final FileNameExtensionFilter filter, String path) {

        final File fileio = new File(path);
        final JFileChooser fc = new JFileChooser(fileio);
        if(filter!=null) {
            fc.addChoosableFileFilter(filter);
            fc.setAcceptAllFileFilterUsed(false);
            fc.setFileFilter(filter);
        }
        final int returnVal = fc.showSaveDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile().getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * Reads a CSV table at the path of the instance.
     *
     * Example of usage: <code>
     * String arrays="c1,c2,c3,c4,c5\nv1,v2,v3,v4,v5\nv6,v7,v8,v9,v10";
     * //write file
     * PiFile file =new PiFile("temp.csv");
     * file.writeTextFile(arrays);
     *
     * //read the file
     * String [][]csv=file.readCSV(",");
     * //results:
     * "c1"== csv[0][0];
     * "c2"== csv[0][1];
     * "c3"== csv[0][2];
     * "c4"== csv[0][3];
     * "c5"== csv[0][4];
     *
     * "v1"== csv[1][0];
     * "v2"== csv[1][1];
     * "v3"== csv[1][2];
     * "v4"== csv[1][3];
     * "v5"== csv[1][4];
     * "v6"== csv[2][0];
     * "v7"== csv[2][1];
     * "v8"== csv[2][2];
     * "v9"==csv[2][3];
     * "v10"== csv[2][4];
     *
     * </code>
     *
     * @param path Path of the file to read.
     * @param separator separator character as ','.
     * @return String array with the first row ([0][..]) represents the columns
     * titles.
     * @throws IOException if the file cannot be read
     */
    public static String[][] readCSV(final String path,final String separator) throws IOException {
        final List<String> lines = readAllLines(path);
        return extractCSV(separator, lines);
    }
    /**
     * Remove column that has a header equals to an element of the
     * {@code exeptedColumne}
     *
     * @param exeptedColumne
     * @return array representing the CSV with the specified columns removed
     */
    public static String[][] removeColumn(String[][] csv, List<String> exeptedColumne) {

        if (exeptedColumne == null || exeptedColumne.isEmpty()) {
            return csv;
        }

        // extract allowed header
        List<String> header = new ArrayList<>();
        for (String h : csv[0]) {
            boolean isOK = !exeptedColumne.contains(h) && !header.contains(h);
            for (String e : exeptedColumne) {
                if (h.contains(e)) {
                    isOK = false;
                    break;
                }
            }
            if (isOK) {
                header.add(h);
            }
        }
        // create the new CSV
        String[][] csvCleaned = new String[csv.length][header.size()];
        for (int i = 0; i < csv.length; i++) {// read csv line
            int k = 0;
            for (int j = 0; j < csv[i].length; j++) {// read csv column
                // get the header corresponding to the actual value
                String h = csv[0][j];
                // if the header is not forbidden
                if (header.contains(h)) {
                    // get the value
                    String v = csv[i][j];
                    // add the value to the new csv
                    csvCleaned[i][k] = v;
                    k++;
                }

            }

        }
        return csvCleaned;

    }

    /**
     * Reads file with use buffer
     * @param path Path of the file to read
     * @return the text contained in the file
     * @throws IOException if something wrong.
     * to readTextBuffer
     */
    public static List<String> readAllLines(String path) throws IOException {
        final List<String> buff = new ArrayList<>();
        final File fileio = new File(path);

        try (BufferedReader reader = Files.newBufferedReader(fileio.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                buff.add(line);
            }
        }
        return buff;
    }
    /**
     *
     * Writes an array in a CSV file
     *
     * @param path path of the file.
     * @param array array to be written.
     * @param separator character used to separated value
     * @param header column headers of the array.
     */
    public static void writesCSV(final String path, final String[][] array, final String separator,
                                 final String... header) {
        String write = "";

        for (int i = 0; i < header.length; i++) {
            write += header[i];
            if (i + 1 < header.length) {
                write += separator;
            }
        }
        write += "\n";
        for (String[] array1 : array) {
            for (int j = 0; j < array1.length; j++) {
                write += array1[j];
                if (j + 1 < array1.length) {
                    write += separator;
                }
            }
            write += "\n";
        }
        PiTools.writeTextFile(path,write);
    }

    /**
     *  Writes a Map in a CSV file
     * @param output path of the file.
     * @param separator character used to separated value
     * @param header  column headers of the array.
     * @param data data to saved
     */
    public void writeCSV(String output, final char separator, List<String> header, Map<String, Map<String, String>> data) {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(output);
            String h = "id";
            for (int i = 0; i < header.size(); i++) {
                String p = header.get(i);
                h += separator + p;
            }
            myWriter.write(h + System.lineSeparator());


            Iterator<String> iterator = data.keySet().iterator();
            while (iterator.hasNext()) {
                String id = iterator.next();
                String row = id;
                for (int i = 0; i < header.size(); i++) {
                    String p = header.get(i);
                    row += separator + data.get(id).get(p);
                }
                myWriter.write(row + System.lineSeparator());
            }
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(PiTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                myWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(PiTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public  static String[][] extractCSV(final String separator, final List<String> lines) {
        if (lines.isEmpty()) {
           return null;
        }
        // remove empty line
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            if (l == null || l.isBlank()||l.startsWith("#")||l.startsWith("//")) {
                lines.remove(i);
                i--;
            }
        }
        // get the number of lines
        final int nbRow = lines.size();
        // split the first line
        String[] values = lines.get(0).split(separator);

        // get the number of columns
        final int nbCol = values.length;
        final String[][] results = new String[nbRow][nbCol];
        results[0] = values;
        // build the arrays
        for (int i = 1; i < lines.size(); i++) {
            String[] split = lines.get(i).split(separator);
            if (split.length>0) {
                results[i] = split;
            }
        }
        return results;
    }
    /**
     * Reads all test in a file
     *
     * @return a string contained each line of the file
     * @throws FileNotFoundException If the file does not exist, is a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading.
     */
    public static String readTextFile(String path) throws FileNotFoundException {
        // test if the file exists
        if (!new File(path).exists())// if it does not exist throws an exception.
        {
            throw new java.io.FileNotFoundException("the file with the path " + path + " is not found");
        } else {
            try {
                return readTextBuffer(path);
            } catch (final IOException ex) {
                Logger.getLogger(PiTools.class.getName()).log(Level.SEVERE, null, ex);
                return "";
            }
        }
    }

    /**
     * Reads file with use buffer
     *
     * @return the text contained in the file
     * @throws FileNotFoundException If the file does not exist, is a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading.
     * @throws IOException if something wrong.
     */
    private static String readTextBuffer(String path) throws FileNotFoundException, IOException {
        final StringBuilder buff = new StringBuilder();
        final File fileio = new File(path);

        try (BufferedReader reader = Files.newBufferedReader(fileio.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line).append("\n");
            }
        }
        String buffS = buff.toString();
        buffS = buffS.replaceFirst("[\\s]*" +"$", "");

        return buffS;
    }
    /**
     * Writes a text in the file with use a buffer
     * @param path Path of the file
     * @param txt text to be written
     */
    public static void writeTextFile(final String path,final String txt) {
        FileWriter myWriter = null;
        try {

            myWriter = new FileWriter(path);
            myWriter.write(txt);
            myWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(PiTools.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                assert myWriter != null;
                myWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(PiTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    /**
     * Returns a list of strings naming (NOT PATH) the files and directories in the
     * directory denoted by this abstract pathname that satisfy the specified
     * filter. Names are sorted in alphabetical order
     *
     * @param ext extension of the file without ".", "*" for no filtering.
     * @param recursive True for recursive listing, false otherwise.
     * @return A list of strings naming the files and directories in the
     * directory denoted by this abstract pathname that were accepted by the
     * given {@code filter}. The array will be empty if the directory is empty
     * or if no names were accepted by the filter. Returns {@code null} if this
     * abstract pathname does not denote a directory, or if an I/O error occurs.
     */
    public static  List<String> lsName(final File directory, final String ext, boolean recursive) {
        final List<String> result = new ArrayList<>();
        final String[] listFile = directory.list();
        if (listFile!=null)
        for (final String listFile1 : listFile) {
            final File file = new File(directory.getPath() + "/" + listFile1);
            if (recursive&&file.isDirectory()) {
                result.addAll(lsName(file, ext, true));
            } else {
                if (ext.equals("*") || file.getName().endsWith(ext)) {
                    result.add(file.getName());
                }
            }
        }
        return result.stream().sorted().toList();
    }
    /**
     * Returns a list of strings path of the files and directories in the
     * directory denoted by this abstract pathname that satisfy the specified
     * filter. Names are sorted in alphabetical order
     *
     * @param ext extension of the file without ".", "*" for no filtering.
     * @param recursive True for recursive listing, false otherwise.
     * @return A list of strings naming the files and directories in the
     * directory denoted by this abstract pathname that were accepted by the
     * given {@code filter}. The array will be empty if the directory is empty
     * or if no names were accepted by the filter. Returns {@code null} if this
     * abstract pathname does not denote a directory, or if an I/O error occurs.
     */
    public static  List<String> lsPath(final File directory, final String ext, boolean recursive) {
        final List<String> result = new ArrayList<>();
        final String[] listFile = directory.list();
        if (listFile!=null)
        for (final String listFile1 : listFile) {
            final File file = new File(directory.getPath() + "/" + listFile1);
            if (recursive&&file.isDirectory()) {
                result.addAll(lsPath(file, ext, true));
            } else {
                if (ext.equals("*") || file.getName().endsWith(ext)) {
                    result.add(file.getPath());
                }
            }
        }
        return result.stream().sorted().toList();
    } /**
     * Returns a list of files and directories in the
     * directory denoted by this abstract pathname that satisfy the specified
     * filter. Names are sorted in alphabetical order
     *
     * @param ext extension of the file without ".", "*" for no filtering.
     * @param recursive True for recursive listing, false otherwise.
     * @return A list of strings naming the files and directories in the
     * directory denoted by this abstract pathname that were accepted by the
     * given {@code filter}. The array will be empty if the directory is empty
     * or if no names were accepted by the filter. Returns {@code null} if this
     * abstract pathname does not denote a directory, or if an I/O error occurs.
     */
    public static  List<File> lsFiles(final File directory, final String ext, boolean recursive) {
        final List<File> result = new ArrayList<>();
        final String[] listFile = directory.list();
        if (listFile!=null)
        for (final String listFile1 : listFile) {
            final File file = new File(directory.getPath() + "/" + listFile1);
            if (recursive&&file.isDirectory()) {
                result.addAll(lsFiles(file, ext, true));
            } else {
                if (ext.equals("*") || file.getName().endsWith(ext)) {
                    result.add(file);
                }
            }
        }
        return result.stream().sorted().toList();
    }

    public static void deleteDirectory(File directory) {

        // if the file is directory or not
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();

            // if the directory contains any file
            if(files != null) {
                for(File file : files) {

                    // recursive call if the subdirectory is non-empty
                    deleteDirectory(file);
                }
            }
        }

        if(directory.delete()) {
            System.out.println(directory + " is deleted");
        }
        else {
            System.out.println("Directory not deleted");
        }
    }
    /**
     * Serializes object in a file.
     *
     * @param <T> Type of object to be serialized
     * @param object object to be saved.
     */
    public static<T> void save(String path,final T object) {
        // initialization of the stream.
        ObjectOutputStream oos = null;
        try {
            // get the final file
            final FileOutputStream fichier = new FileOutputStream(path);
            // open the stream from the file
            oos = new ObjectOutputStream(fichier);

            // write the object
            oos.writeObject(object);
        } catch (final java.io.IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (oos != null) {
                    // empty the buffer
                    oos.flush();
                    // close the file
                    oos.close();
                }
            } catch (final IOException ex) {
                System.err.println(ex);
            }
        }
    }

    /**
     * Gets a object serialized in the file
     *
     * @param <T> Type of object to be loaded.
     * @return the object serialized or null if something wrong.
     * @throws FileNotFoundException If the file does not exist, is a directory
     * rather than a regular file, or for some other reason cannot be opened for
     * reading .
     */
    public static <T> T load(String path) throws FileNotFoundException {

        // test if the file exists
        if (!new File(path).exists()) // if it does not exists throws a exception.
        {
            throw new java.io.FileNotFoundException("the file with the path " + path + "does not found");
        }
        ObjectInputStream ois = null;
        try {
            final FileInputStream fichier = new FileInputStream(path);
            ois = new ObjectInputStream(fichier);
            @SuppressWarnings("unchecked")
            final T object = (T) ois.readObject();
            ois.close();
            return object;
        } catch (final java.io.IOException | ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (final IOException ex) {
                System.err.println(ex);
            }
        }
        return null;
    }
}
