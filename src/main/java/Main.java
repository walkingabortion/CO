import bench.TestCPU;
import logging.ConsoleLogger;
import timer.Timer;

public class Main {
    public static void main(String[] args)
    {
        Timer timer = new Timer();
        ConsoleLogger log = new ConsoleLogger();
        TestCPU bench = new TestCPU(12000);  //12000 is some sort of java heap limit
        long elapsed;


        timer.start();      //warmUp
        bench.initialize();
        bench.run();
        elapsed = timer.stop();
        //log.writeTime("Benched in ", elapsed, "mili");

        for(int i = 0; i < 10; i++)
        {
            bench.initialize();

            timer.start();
            bench.run();
            elapsed = timer.stop();

            //score based on elapsed time, workload

            log.writeTime("Benched in ", elapsed, "mili");
        }

        log.close();
    }
}
