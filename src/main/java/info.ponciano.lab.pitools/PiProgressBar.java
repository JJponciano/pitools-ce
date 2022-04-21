package info.ponciano.lab.pitools;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;

public class PiProgressBar {

    private final ProgressBar pb;

    public PiProgressBar(String title, long max) {
        pb = new ProgressBar(title,max, ProgressBarStyle.ASCII); // name, initial max
    }
    public void start(){
        pb.start();
    }
    public void stop(){
        pb.stop();
    }
    public synchronized void step(){
        pb.step();
    }
    public void stepTo(long n){
        pb.stepTo(n);
    }
}