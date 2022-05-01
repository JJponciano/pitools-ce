package info.ponciano.lab.pitools;

import org.junit.jupiter.api.Test;

import java.io.File;

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
        dirTest.mkdir();
        PiTools.writeTextFile(dirTest.getPath()+"/t1.txt","file 1\n for testing");
    }

    @Test
    void testLs() {
    }
}