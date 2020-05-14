package bench;

import java.util.Random;

public class TestCPU {
    private boolean running;
    private int workload;
    private int[][] A, B, C;

    public TestCPU(int workload)
    {
        this.workload = workload;
        A = new int[workload][ workload];
        B = new int[workload][ workload];
        C = new int[workload][ workload];
    }

    private void print()
    {
        for(int i = 0; i < workload; i++)
        {
            for(int j = 0; j < workload; j++)
            {
                System.out.print(A[i][j] + " ");
            }
            System.out.println();
        }

        for(int i = 0; i < workload; i++)
        {
            for(int j = 0; j < workload; j++)
            {
                System.out.print(B[i][j] + " ");
            }
            System.out.println();
        }
    }
    public void initialize(Object ... param)
    {
        //this.workload = (Integer) param[0];

        Random rand = new Random();

        for(int i = 0; i < workload; i++)
        {
            for(int j = 0; j < workload; j++)
            {
                A[i][j] = rand.nextInt();
                B[i][j] = rand.nextInt();
            }
        }

        //print();
    }

    public void run()
    {
        for(int i = 0; i < workload && running; i++)
        {
            for(int j = 0; j < workload && running; j++)
            {
                C[i][j] = 0;
                for(int k = 0; k < workload && running; k++)
                {
                    C[i][j] += A[i][k]*B[k][j];
                }
            }
        }
    }

    public void cancel()
    {
        this.running = false;
    }

    public void warmUp()
    {
        for(int i = 0; i < workload && running; i++)
        {
            for(int j = 0; j < workload && running; j++)
            {
                C[i][j] = 0;
                for(int k = 0; k < workload && running; k++)
                {
                    C[i][j] += A[i][k]*B[k][j];
                }
            }
        }
    }
}
