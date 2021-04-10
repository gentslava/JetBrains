import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        boolean debug = true;
        List<String> files = new ArrayList<String>();
        files.add("./files/style.css");
        files.add("./files/style — копия.css");
        try {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            if (debug) System.out.println(formatter.format(date) + " - Начало сравнения файлов");
            Comparator my_comparator = new Comparator(debug);
            my_comparator.compare(files);
            date = new Date();
            if (debug) System.out.println(formatter.format(date) + " - Сравнение завершено");
        } catch (Exception e) {
            if (debug) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                System.out.println(formatter.format(date) + " - Сравнение завершено с ошибкой:");
                e.printStackTrace();
            }
        }
    }
}
