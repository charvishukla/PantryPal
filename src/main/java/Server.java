public class Server {
    private Model model;
    private RestController restController;

    public static void main(String[] args) {
        Model model = new Model();
        RestController restController = new RestController(model);
    }
}
