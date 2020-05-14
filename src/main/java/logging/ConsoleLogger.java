package logging;


public class ConsoleLogger {


    public void write(Object... param) {
        for(Object each: param)
            System.out.println(each + " " + "\n");
    }

    public void close()
    {
        System.out.println("Closing program\n");
        System.exit(0);
    }

    public void writeTime(long value, String unit) {
        TimeUnit tunit = new TimeUnit(unit);
        System.out.println( tunit.Convert(value) + " " + unit + " " );

    }


    public void writeTime(String string, long value, String unit) {
        TimeUnit tunit = new TimeUnit(unit);
        System.out.println( string + " " + tunit.Convert(value) + " " + unit + " " );

    }
}
