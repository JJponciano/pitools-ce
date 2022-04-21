/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ponciano.lab.pitools;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author jean-jacques.poncian
 */
 class DirChooser extends JPanel implements ActionListener {

    private final String choosertitle;
    private final File dir;

    /**
     * Constructor
     *
     * @param choosertitle Windows title
     * @param dir current directory
     */
    public DirChooser(String choosertitle, File dir) {
        this.choosertitle = choosertitle;
        this.dir = dir;
    }


    public void actionPerformed(ActionEvent e) {
        this.run();
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }


    public String run() {
        JFileChooser chooser = new JFileChooser();

        chooser.setCurrentDirectory(this.dir);
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            return chooser.getSelectedFile().getPath();
        } else {
            System.out.println("No Selection ");
            return "";
        }
    }
}
