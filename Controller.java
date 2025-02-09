package MVC.src;

public class Controller {
    static String validateInput(String id) {
        return Model.validateInput(id);
    }

    static void fix(String id) {
        Model.fix(id);
    }

    static void connectDB() {
        Model.connectDB();
    }

    static String getInfo(String id) {
        return Model.getInfo(id);
    }
}