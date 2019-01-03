package chapter7_network_scalability;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerCallable implements Callable<Float> {

    private static final ConcurrentHashMap<String, Float> map;
    private String partName;

    static {
        map = new ConcurrentHashMap<>();
        map.put("Axle", 11.11f);
        map.put("Gear", 22.22f);
        map.put("Wheel", 33.33f);
        map.put("Rotor", 44.44f);
    }

    public WorkerCallable(String partName) {
        this.partName = partName;
    }

    @Override
    public Float call() throws Exception {
        float price = map.get(this.partName);
        System.out.println("Worker Callable returned " + price);
        return price;
    }
}
