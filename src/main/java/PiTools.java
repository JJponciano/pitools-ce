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

}
