import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestMain {

    static long countFiles(String mypath) throws IOException {
        return Files.list(Paths.get(mypath)).count();
    }


    public static void main(String[] args) {

        //FileReader fr = new FileReader();
        //getList lists = new getList();
    	
        if (args[0] == null || args[0].trim().isEmpty() || args[1] == null || args[1].trim().isEmpty()) {
            System.out.println("You need to specify a path!");
            return;
        }
        //File CP_file = new File(args[0]);
        //int count = fr.FileSizeInLines(CP_file);
        System.out.println("Total number of lines in the file are: " + " \n "+ args[0] + " \n " + args[1]);

        try {
            // BufferedReader br = new BufferedReader(
            //        new FileReader("ShowGeneratedHtml.java"));

            File f = new File(args[1] + "/axne.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<body>");
            bw.write("<h1>ShowGeneratedHtml source</h1>");
            bw.write("<textarea cols=75 rows=30>");

            //String line;
            //while ((line = br.readLine()) != null) {
            //    bw.write(line);
            //    bw.newLine();
            //}

            TestMain obj = new TestMain();

            String domainName = "google.com";

            //in mac oxs
            String command = "cmd /C git ls-files | find /c /v \"\"";

            //in windows
            //String command = "ping -n 3 " + domainName;
            
        	//System.getProperty("os.name").toLowerCase().startsWith("windows");
            String output = obj.executeCommand(command, args[0]);
            
            //System.out.println(System.getProperty("user.dir"));

            System.out.println("res > " +  output);


            //System.out.println(countFiles(args[0]));


            bw.write("</text" + "area>");
            bw.write("</body>");
            bw.write("</html>");

            //br.close();
            bw.close();
        } catch (IOException ignored) {

        }

        //List<String> lines = fr.strReader(CP_file);

    }

    private String executeCommand(String command, String arg) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
        	System.out.println("wqqwwq");
            p = Runtime.getRuntime().exec(command, null, new File(arg));
            System.out.println("ssadd");
            p.waitFor();
            System.out.println(p.exitValue());
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            int counter=0;
            
            while ((line = reader.readLine()) != null) {
                output.append(line+'\n');
            	counter++;
            }
            System.out.println(counter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
