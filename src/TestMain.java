import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

            //String domainName = "google.com";

            //in mac oxs
            String command;
            String output;
            
            List<String> commiters = new ArrayList<String>();
            int commiters_count=0;
            

            //in windows
            //String command = "ping -n 3 " + domainName;
            
        	//System.getProperty("os.name").toLowerCase().startsWith("windows");
            command = "cmd /C git ls-files | wc -l";
            output = obj.executeCommand(command, args[0]);
            
            //System.out.println(System.getProperty("user.dir"));

            System.out.println("Number of files is: \n" +  output);

            //command = "git diff --stat 4b825dc642cb6eb9a060e54bf8d69288fbee4904";
            command = "cmd /C git ls-files | xargs wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total lines is: \n" +  output);
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git branch -ar | find /c /v \"\"";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total branches is: \n" +  output);
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git tag | find /c /v \"\"";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total tags is: \n" +  output);
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | uniq | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total committers is: \n" +  output);
            commiters_count = Integer.parseInt(output.split("\n")[0]);
            System.out.println(commiters_count);
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total commits is: \n" +  output);
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            for (int i = 0; i < commiters_count; i++) {
            	command = "cmd /C  git log | grep Author: | sort | uniq | cut -d ' ' -f 2";
                output = obj.executeCommand(command, args[0]);
                commiters.add((output.split("\n")[i]));
                //System.out.println(output.split("\n")[i]);
			}
            
            for (String item:commiters) {
            	System.out.println(item);
            }
            
            //git log --author="Jon" | grep Author: | wc -l
            for (String item:commiters) {
            	command = "git log --author=\""+item+"\" | grep Author: | wc -l";
                output = obj.executeCommand(command, args[0]);
                System.out.println(item+" : "+ output);		//<--- Den trexei ...
            }
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            //System.out.println(countFiles(args[0]));
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nOLA GOOD (oxi)");


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
                System.out.println("line: "+line);
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
