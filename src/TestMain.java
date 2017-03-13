import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


/**
 * Created by aggel on 13/3/2017.
 */
public class TestMain {

    static long countFiles(String mypath) throws IOException {
        return  Files.list(Paths.get(mypath)).count();
    };

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

    public static void main(String[] args) {

        //FileReader fr = new FileReader();
        //getList lists = new getList();

        if (args[0] == null || args[0].trim().isEmpty() || args[1]==null || args[1].trim().isEmpty()) {
            System.out.println("You need to specify a path!");
            return;
        } else {
            File CP_file = new File(args[0]);
            //int count = fr.FileSizeInLines(CP_file);
            System.out.println("Total number of lines in the file are: "+ args[0] + " /n " + args[1]);

            try{
           // BufferedReader br = new BufferedReader(
            //        new FileReader("ShowGeneratedHtml.java"));

            File f = new File(args[1]+"/axne.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<body>");
            bw.write("<h1>ShowGeneratedHtml source</h1>");
            bw.write("<textarea cols=75 rows=30>");

            String line;
            //while ((line = br.readLine()) != null) {
            //    bw.write(line);
            //    bw.newLine();
            //}

                TestMain obj = new TestMain();

                String domainName = "google.com";

                //in mac oxs
                String command = "ping -c 3 " + domainName;

                //in windows
                //String command = "ping -n 3 " + domainName;

                String output = obj.executeCommand(command);

                System.out.println(output);



            //System.out.println(countFiles(args[0]));


            bw.write("</text" + "area>");
            bw.write("</body>");
            bw.write("</html>");

            //br.close();
            bw.close();}
            catch (IOException e){

            }

            //List<String> lines = fr.strReader(CP_file);

        }
    }
}
