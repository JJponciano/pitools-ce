package info.ponciano.lab.pitools;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PiToolsTest {

    @Test
    void timeToString() {
    }

    @Test
    void dialogOpenFile() {
    }

    @Test
    void dialogOpenDir() {
    }

    @Test
    void testDialogOpenFile() {
    }

    @Test
    void dialogSaveFile() {
    }

    @Test
    void readCSV() {
    }

    @Test
    void readAllLines() {
    }

    @Test
    void readTextFile() {
    }

    @Test
    void writeTextFile() {
    }

    @Test
    void ls() {
        File dirTest=new File("test_pitools");
        File dirTest2=new File(dirTest.getPath()+"/sub_test_pitools");
        dirTest.mkdir();
        dirTest2.mkdir();
        PiTools.writeTextFile(dirTest.getPath()+"/t1.txt","file 1\n for testing");
        PiTools.writeTextFile(dirTest.getPath()+"/t2.txt","file 2\n for testing");
        PiTools.writeTextFile(dirTest.getPath()+"/t2.xyz","file 3\n for testing");
        PiTools.writeTextFile(dirTest2.getPath()+"/t3.txt","file 4\n for testing");
        PiTools.writeTextFile(dirTest2.getPath()+"/t3.xyz","file 5\n for testing");

        List<String> results = PiTools.ls(dirTest, "txt", true);
        List<String> resultsNR = PiTools.ls(dirTest, "txt", false);
        assertEquals(3, results.size());
        assertEquals(2, resultsNR.size());

        assertEquals("t1.txt", results.get(0));
        assertEquals("t2.txt", results.get(1));
        assertEquals("t3.txt", results.get(2));

    }

    @Test
    void testLs() {
    }
}