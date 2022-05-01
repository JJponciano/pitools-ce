package info.ponciano.lab.pitools.examples;

import info.ponciano.lab.pitools.PiTools;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparatorFilesTest {

    @Test
    void compareLines() throws IOException {
        //write 2 files
        PiTools.writeTextFile("test1.txt","1234567-000-02\n1434777-000-02\n1634777-000-02\n1234587-010-02\n");
        PiTools.writeTextFile("test2.txt","1834567-000-02\n1434777-001-02\n1674777-000-02\n1234587-010-02\n");
        List<String> common = ComparatorFiles.compareLines("test1.txt", "test2.txt", 11,true);
        assertEquals(1, common.size());
        assertEquals(common.get(0),("1234587-010"));
        List<String> diff = ComparatorFiles.compareLines("test1.txt", "test2.txt", 11,false);
        assertEquals(3, diff.size());
    }

}