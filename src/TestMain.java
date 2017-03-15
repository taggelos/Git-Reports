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
            bw.write("<head>");
            bw.write("<title> Exercise-1 </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse;}th, td { padding: 5px; text-align: left; }</style>");
            bw.write("</head> <body>");
            bw.write("<h2><font color = \"red\"> Report for git repository in : </font> <a href=\""+ args[1] +"\">"+ args[1] +"</a></h2>");
            bw.write("<table style=\"width:20%\">");
            
            
            //String line;
            //while ((line = br.readLine()) != null) {
            //    bw.write(line);
            //    bw.newLine();
            //}

            TestMain obj = new TestMain();

            //String domainName = "google.com";

            //in mac oxs
            String command;
            String output;
            
            command = "cmd /C git ls-files | wc -l";

            //in windows
            //String command = "ping -n 3 " + domainName;
            
        	//System.getProperty("os.name").toLowerCase().startsWith("windows");
            output = obj.executeCommand(command, args[0]);
            
            //System.out.println(System.getProperty("user.dir"));

            System.out.println("Number of files is: \n" +  output);
            bw.write("<tr><th>Number of files</th><th colspan=\"2\">"+ output+ "</th></tr>");
            
            

            //command = "git diff --stat 4b825dc642cb6eb9a060e54bf8d69288fbee4904";
            //command = "cmd /C git ls-files | xargs wc /l";
            //output = obj.executeCommand(command, args[0]);
            //System.out.println("Number of total lines is: " +  output);
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git branch -ar | find /c /v \"\"";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total branches is: \n" +  output);
            bw.write("<tr><th>Number of total branches</th><th colspan=\"2\">"+ output+ "</th></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git tag | find /c /v \"\"";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total tags is: \n" +  output);
            bw.write("<tr><th>Number of total tags</th><th colspan=\"2\">"+ output+ "</th></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | uniq | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total committers is: \n" +  output);
            bw.write("<tr><th>Number of total committers</th><th colspan=\"2\">"+ output+ "</th></tr>");
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total commits is: \n" +  output);
            bw.write("<tr><th>Number of total commits</th><th colspan=\"2\">"+ output+ "</th></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            //System.out.println(countFiles(args[0]));
            System.out.println("\nOLA GOOD (oxi)");

            bw.write("</table>");
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
        	//System.out.println("wqqwwq");
            p = Runtime.getRuntime().exec(command, null, new File(arg));
            //System.out.println(p.exitValue());
            p.waitFor();
            //System.out.println(p.exitValue());
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            //int counter=0;
            
            while ((line = reader.readLine()) != null) {
                output.append(line+'\n');
            	//counter++;
            }
            //System.out.println(counter);
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
