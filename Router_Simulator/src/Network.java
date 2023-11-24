import java.util.Scanner;

public class Network {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the number of WI-FI Connections?");
        int maxConnections = scanner.nextInt();

        System.out.println("What is the number of devices Clients want to connect?");
        int totalDevices = scanner.nextInt();

        Router router = new Router(maxConnections);
        System.out.println("Enter details for Devices (Name Type):");
        for (int i = 1; i <= totalDevices; i++) {

            String name = scanner.next();
            String type = scanner.next();

            Device device = new Device(name, type, router);
            device.start();
        }

        scanner.close();
    }
}

//C1 1 C2 2 C3 3 C4 4 C5 5 C6 6 C7 7 c8 8 c9 9 c10 10
//C1 mobile C2 tablet C3 pc C4 pc