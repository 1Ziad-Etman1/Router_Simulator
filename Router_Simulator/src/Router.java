import java.util.ArrayList;
import java.util.List;

public class Router {
    private List<Device> connections;
    private Semaphore semaphore;

    public Router(int maxConnections) {
        connections = new ArrayList<>();
        semaphore = new Semaphore(maxConnections);
    }

    public void occupyConnection(Device device) {
        try {
            if (semaphore.permits > 0) {
                semaphore.acquire();
                System.out.println("- " + device + " arrived");
                System.out.println("- Connection " + device.getName() + ": " + device.getType() + " Occupied");
                connections.add(device);
            } else {
                waiting(device);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseConnection(Device device) {
        try {
            if (semaphore.permits > 0){
                Thread.sleep(3000); // Simulate online activity delay
                System.out.println("- Connection " + device.getName() + ": " + device.getType() + " Logged out");
                connections.remove(device); // Remove the device from the list of connections
                semaphore.release(); // Release the permit in the semaphore
            } else {
                waiting(device);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waiting(Device device) {
        System.out.println("- " + device + " arrived and waiting");
    }
}