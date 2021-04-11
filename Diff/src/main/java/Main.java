import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    static Logger logger;
    public static void main(String[] args) {
        List<String> files = new ArrayList<String>();
        files.add("./files/style.css");
        files.add("./files/style — копия.css");
        try {
            FileInputStream ins = new FileInputStream("./logs/log.config");
            LogManager.getLogManager().readConfiguration(ins);
            logger = Logger.getLogger(Main.class.getName());
            logger.log(Level.INFO, "Start of files comparing");
            Comparator my_comparator = new Comparator(logger);
            my_comparator.compare(files);
            logger.log(Level.INFO, "Comparing finished");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Comparing finished with exception:", e);
        }
    }
}
