import bench.TestCPU;
import logging.ConsoleLogger;
import timer.Timer;

public class Main {
    public static void main(String[] args)
    {
        Timer timer = new Timer();
        ConsoleLogger log = new ConsoleLogger();
        TestCPU bench = new TestCPU();  //12000 is some sort of java heap limit

        long elapsed;
        int workload = 10000;
        double totalTime = 0;
        int iterations = 10;

        timer.start();      //warmUp
        bench.initialize(workload);
        bench.run();
        elapsed = timer.stop();

        for(int i = 0; i < iterations; i++)
        {
            bench.initialize(workload);

            timer.start();
            bench.run();

            totalTime += timer.stop();
        }
        double medie =  (double)totalTime / iterations;
        double score = workload /  (Math.log(medie) + 1);
        System.out.println("Score is " + Math.log(score) );

        log.close();
    }
}
