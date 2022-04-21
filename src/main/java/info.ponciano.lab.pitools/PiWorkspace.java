/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.pitools;

import java.io.File;
import java.io.FileNotFoundException;



/**
 *
 * @author jean-jacques.poncian
 */
public class PiWorkspace {

    protected String dir;
    private final String WORKSPACE = ".workspace";

    /**
     * Creates new instance of <code>PiWorkspace</code> and set or create the
     * directory
     *
     * @param dir workspace directory .
     */
    public PiWorkspace(String dir) {
        this.dir = dir;
        boolean mkdir = new File(dir).mkdir();
        this.save();
    }

    /**
     * Loads the directory of the workspace automaticaly and creates new
     * instance of <code>PiWorkspace</code>
     *
     */
    public PiWorkspace() {
        this.dir = "workspace";
        try {
            this.load();
        } catch (PiWException ex) {
            boolean mkdir = new File(dir).mkdir();
            this.save();
        }
    }

    /**
     * Load configuration from the path
     */
    private void load() throws PiWException {
        try {
            this.dir =PiTools.readTextFile(WORKSPACE);
        } catch (FileNotFoundException ex) {
            throw new PiWException("No workspace is defined");
        }
    }

    /**
     * save configuration in workspace file
     */
    private void save() {
        PiTools.writeTextFile(WORKSPACE,dir);
    }

    public String getDir() {
        return dir;
    }

    /**
     * Set the new directory
     *
     * @param dir new directory to be set
     *
     * <p>
     * Examples:
     * <blockquote><pre>
     * setDir("src/test/file.txt") -> "src/test/"
     * </pre></blockquote>
     * </p>
     */
    public void setDir(String dir) {
        int lastIndexOf = dir.lastIndexOf('/');
        //if the workspace is in windows.
        if (lastIndexOf < 0) {
            lastIndexOf = dir.lastIndexOf('\\');
        }
        if (lastIndexOf > 0) {
            this.dir = dir.substring(0, lastIndexOf);
        } else {
            this.dir = dir;
        }
        this.save();
    }

}
