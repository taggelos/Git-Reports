import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommitersReport {


    public static void create(String path, String name) {

        try {
        	File myDir = new File("userReports");
        	// if the directory does not exist, create it
        	if (!myDir.exists()) {
        	    System.out.println("creating directory: " + myDir.getName());
        	    boolean result = false;
        	    myDir.mkdir();
        	    result = true;	         
        	    if(result) {    
        	        System.out.println("DIR created");  
        	    }
        	}
            File f = new File(path + "/userReports/"+name+".htm");
            System.out.println("--->"+f.getAbsolutePath()+"<---");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> "+name+" </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse; white-space: nowrap;}th, td { padding: 5px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"blue\">" + name + "</font><font color = \"red\"> Report </font> </h2>");
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
        	ignored.printStackTrace();
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
