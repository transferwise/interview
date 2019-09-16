import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallCenter {

    private final ExecutorService executorService;
    private final Queue<Call> calls;
    private final int numOfAgents;
    private boolean open = false;

    public CallCenter(int numberOfAgents) {
        calls = new ArrayBlockingQueue<>(numberOfAgents);
        executorService = Executors.newFixedThreadPool(numberOfAgents);
        this.numOfAgents = numberOfAgents;
    }

    public void open() {
        for (int i = 0; i < numOfAgents; ++i) {
            Thread agent = new Thread(new Agent(i, calls));
            executorService.submit(agent);
            agent.start();
        }
        open = true;
    }

    public void accept(Call call) {
        if (isOpen()) {
            calls.add(call);
        }
    }

    public void close() {

        for(int i = 0; i < numOfAgents; ++i) {
            accept(Agent.GO_HOME);
        }

        executorService.shutdown();
        open = false;
    }

    private boolean isOpen() {
        return open;
    }
}
class Call {
    private final int id;
    private final int duration;
    private final long startTime;

    public Call(int id, int duration) {
        this.id = id;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    public void answer() {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
        }
    }

}

class Agent implements Runnable {

    public static final Call GO_HOME = new Call(-1,0);
    private final int id;
    private final Queue<Call> calls;

    public Agent(int id, Queue calls) {
        this.id = id;
        this.calls = calls;
    }

    @Override
    public void run() {
        while(true) {
            Call call = calls.poll();

            if (call == GO_HOME) {
                break;
            }

            call.answer();
        }
    }
}
