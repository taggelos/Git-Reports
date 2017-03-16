import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BranchReport {


    public static void create(String path) {

        try {
            File f = new File(path + "/axne2.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> Exercise-1 </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse;}th, td { padding: 5px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"red\"> Branch Report </font> </h2>");
            bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
            
            bw.write("<tr><th>Id</th><th>Message</th><th>Date</th><th>Author</th><th>Release</th>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            System.out.println("\nOLA GOOD (oxi)");

            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");

            //br.close();
            bw.close();
        } catch (IOException ignored) {

        }

    }

    private String executeCommand(String command, String arg) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command, null, new File(arg));
            p.waitFor();
            //System.out.println(p.exitValue());
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line+'\n');
            }
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return output.toString();
    }

    
}
