import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Comparator {
    static Logger logger;
    String HTML;

    protected Comparator(Logger logger) throws IOException {
        this.logger = logger;
        this.logger.log(Level.INFO, "Constructor");
        HTML = "<html>\n\t<head>\n\t\t<meta charset=\"utf-8\">\n\t\t<link rel=\"stylesheet\" href=\"./style.css\">\n\t\t<title>File Comparator</title>\n\t</head>\n";
    }

    private int[][] fill_dyn_matrix(List<String> str1, List<String> str2) {
        int lines = str1.size()+1, columns = str2.size()+1;

        int[][] matrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) matrix[i][0] = 0;
        for (int i = 0; i < columns; i++) matrix[0][i] = 0;

        for (int i = 1; i < lines; i++) {
            for (int j = 1; j < columns; j++) {
                if (str1.get(i-1).equals(str2.get(j-1))) matrix[i][j] = matrix[i-1][j-1]+1;
                else matrix[i][j] = Math.max(matrix[i-1][j], matrix[i][j-1]);
            }
        }

        logger.log(Level.INFO, "Matrix:");
        String matrix_str = "";
        for (int i = 0; i < lines; i++) {
            matrix_str += "\n";
            for (int j = 0; j < columns; j++) {
                matrix_str += (matrix[i][j]);
                if (j < columns - 1) matrix_str += "\t";
            }
        }
        logger.log(Level.INFO, matrix_str);

        return matrix;
    }

    private void LCS (String file1, String file2) throws IOException {
        BufferedReader reader1 = new BufferedReader(new FileReader(file1));
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));

        List<String> text1 = new ArrayList<String>();
        String currStr = reader1.readLine();
        while (currStr != null) {
            text1.add(currStr);
            currStr = reader1.readLine();
        }

        List<String> text2 = new ArrayList<String>();
        currStr = reader2.readLine();
        while (currStr != null) {
            text2.add(currStr);
            currStr = reader2.readLine();
        }

        int[][] matrix = fill_dyn_matrix(text1, text2);

        List<String> res1 = new ArrayList<String>();
        List<String> res2 = new ArrayList<String>();
        int i = text1.size(), j = text2.size();
        int count = 0;
        boolean change = false;
        String str1 = "", str2 = "";
        while (i > 0 || j > 0) {
            if (i > 0) str1 = text1.get(i-1); if (j > 0) str2 = text2.get(j-1);
            if (i > 0 && j > 0 && str1.equals(str2)) {
                res1.add("<p>"+str1+"</p>");  res2.add("<p>"+str2+"</p>");
                i--; j--; count = 0; continue;
            }
            if (i > 0 && matrix[i-1][j] == matrix[i][j]) {
                i--; count++; change = true;
            }
            if (j > 0 && matrix[i][j-1] == matrix[i][j]) {
                j--; count--; change = true;
            }
            if (change) {
                if (count == 0) {
                    res1.add("<p style=\"background-color:#03a9f4;\">"+str1+"</p>");
                    res2.add("<p style=\"background-color:#03a9f4;\">"+str2+"</p>");
                    logger.log(Level.INFO, "Changed string: \"" + str1 + "\" on \"" + str2 + "\"");
                }
                else if (count > 0) {
                    res1.add("<p style=\"background-color:#b0b0b0;\">"+str1+"</p>");
                    res2.add("<p></p>");
                    logger.log(Level.INFO, "Removed \"" + str1 + "\" string from 1 file");
                }
                else {
                    res1.add("<p></p>");
                    res2.add("<p style=\"background-color:#03f403;\">"+str2+"</p>");
                    logger.log(Level.INFO, "Added \"" + str2 + "\" string to 2 file");
                }
                change = false;
            }
        }

        Collections.reverse(res1);
        Collections.reverse(res2);
        logger.log(Level.INFO, "Exit file parts:");
        HTML += "\t\t\t<div class=\"file\">";
        for (i = 0; i < res1.size(); i++) {
            HTML += res1.get(i);
            logger.log(Level.INFO, "\t\t\t" + res1.get(i));
        }
        HTML += "\t\t\t</div>";
        HTML += "\t\t\t<div class=\"file\">";
        for (j = 0; j < res2.size(); j++) {
            HTML += res2.get(j);
            logger.log(Level.INFO, "\t\t\t" + res2.get(j));
        }
        HTML += "\t\t\t</div>";
    }

    protected void compare(List<String> files) throws Exception {
        HTML += "\t<body>\n";
        for (int i = 0; i < files.size() - 1; i++) {
            HTML += "\t\t<div class=\"compare\">";
            LCS(files.get(i), files.get(i+1));
            HTML += "\t\t</div>";
        }
        HTML += "\t</body>\n</html>";
        String folder = "Result";
        File dir = new File(folder);
        dir.mkdir();
        PrintStream out = new PrintStream(new FileOutputStream(folder + "\\" + "diff.html"));
        out.print(HTML);

        logger.log(Level.INFO, "All files were compared");
    }
}