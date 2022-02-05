import com.toshop.application.Application;
import com.toshop.plugins.database.SQLiteDatabasePlugin;
import com.toshop.plugins.ui.SwingUIPlugin;

public class Program {
    public static void main(String[] args) {
        var database = new SQLiteDatabasePlugin("toshop.db");
        var ui = new SwingUIPlugin();
        var application = new Application(database, ui);
    }
}
