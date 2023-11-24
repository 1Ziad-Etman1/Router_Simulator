public class Device extends Thread {
    private String name;
    private String type;
    private Router router;

    public Device(String name, String type, Router router) {
        super(name);
        this.name = name;
        this.type = type;
        this.router = router;
    }

    public String get_Name() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public void run() {
        router.occupyConnection(this); // Connect to the router
        System.out.println("- Connection " + name + ": " + type + " login");
        System.out.println("- Connection " + name + ": " + type + " performs online activity");
        try{
            sleep(3000);
        } catch (Exception e){
            return;
        }
        router.releaseConnection(this); // Disconnect from the router
    }

    @Override
    public String toString() {
        return "(" + name + ")(" + type + ")";
    }
}
