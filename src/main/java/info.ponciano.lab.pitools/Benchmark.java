/*
 * Copyright (C) 2015 Jean-Jacques PONCIANO
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.ponciano.lab.pitools;

import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author socrate
 */
public class Benchmark {

    private long startTime;
    private long endTime;

    public Benchmark() {
    }

    /**
     * Start the recording
     */
    public void go() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Stop the recording
     */
    public void stop() {
        this.endTime = System.currentTimeMillis();
    }

    /**
     * Return the time in ms between the {@code go()} and {@code stop()}
     * calling.
     *
     * @return The time in ms.
     */
    public long getValue() {
        return (endTime - startTime);
    }

    /**
     * Show the time in ms between the {@code go()} and {@code stop()} calling.
     *
     * @return a string representig the time elapsed.
     */
    public String showValue() {
        long value = endTime - startTime;        
        return PiTools.timeToString(value);
    }

    /**
     * Today's the day we recovered over a repeat of elapsed time
     *
     * @param rep number of repeat time
     * @return the ending date after a repeat time
     */
    public Date getEndingDate(int rep) {
        long result = (endTime - startTime) * rep + System.currentTimeMillis();
        return new Date(result);
    }

    /**
     * Print Today's the day over a repeat of elapsed time
     *
     * @param rep number of repeat time
     */
    public void printEndingDate(int rep) {
        long result = (endTime - startTime) * rep + System.currentTimeMillis();
        Date date = new Date(result);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM
        );
        System.out.println("Ending at:" + dateFormat.format(date));
    }

    /**
     * gets today's the day over a repeat of elapsed time
     *
     * @param rep number of repeat time
     * @return today's today's the day over a repeat of elapsed time
     */
    public String getStringEndingDate(int rep) {
        long result = (endTime - startTime) * rep + System.currentTimeMillis();
        Date date = new Date(result);
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.MEDIUM,
                DateFormat.MEDIUM
        );
        return (dateFormat.format(date));
    }
}
