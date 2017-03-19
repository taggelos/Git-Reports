import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BranchReport {


    public static void create(String path, String name) {

        try {
        	File myDir = new File("branchReports");
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
            File f = new File(path + "/branchReports/"+name+".htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> "+name+" </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse; white-space: nowrap;}th, td { padding: 10px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"blue\">" + name + "</font><font color = \"red\"> Branch Report </font> </h2>");
            bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
            
            bw.write("<tr><th>Id</th><th>Message</th><th>Date</th><th>Author</th><th>Release</th>");
            
            BranchReport obj = new BranchReport();
            String command,output,outputauthor,outputdate,outputid;

            int commits=0;
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            command = "cmd /C  git log "+name+" --oneline | wc -l";
            output = obj.executeCommand(command, path);
            System.out.println("Number of total branch commits is: "+name +" \n" +  output);
            commits = Integer.valueOf(output.substring(0, output.length()-1));
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            System.out.println("Number of total branch commits is: \n" +  output);
           
            command = "cmd /C  git log "+name+" --oneline";
            outputid = obj.executeCommand(command, path);
            
            command = "cmd /C git log "+name +" --date=format:%Y-%m-%d | grep Date: ";
            outputdate = obj.executeCommand(command, path);
            
            command = "cmd /C git log "+name +" | grep Author: ";
            outputauthor = obj.executeCommand(command, path);

            //String[] parts = output.split("\n");
            String line;
            List<String> id = new ArrayList<String>();
            List<String> msg = new ArrayList<String>();
            List<String> date = new ArrayList<String>();
            List<String> author = new ArrayList<String>();
            List<String> tag = new ArrayList<String>();
            
            System.out.println("aaaaaaaa: " +name +" aaaaaaaaaaaaa \n");
            for (int i=0; i<commits;i++){
            	bw.write("<tr>");
            	line = outputid.split("\n")[i];
            	// id + msg
            	
            	id.add(line.split(" ")[0]);
            	msg.add(line.split(" ", 2)[1]);
            	
            	
            	// date

            	outputdate= outputdate.replaceAll("Date:","");
                line = outputdate.split("\n")[i];
                while(line.startsWith(" ")){
             		line=line.replaceFirst(" ", "");
             	}
                date.add(line);
                System.out.println("Number of total branch commits is LALLALALA: "+name +" \n" +  date.get(i));
            	
            	// author
             
                outputauthor= outputauthor.replaceAll("Author:","");
                line = outputauthor.split("\n")[i];
                while(line.startsWith(" ")){
             		line=line.replaceFirst(" ", "");
             	}
                author.add(line);
                
                // release - tag
                
                command = "cmd /C  git tag --contains "+id.get(i);
                output = obj.executeCommand(command, path);
                //output.split("/n");
                tag.add(output);
            	
            	System.out.println("line:" + line + " id-> " + id.get(i) + " msg ->" + msg.get(i)+" \n");
            	bw.write("<td>" + id.get(i) +" </td>"  );
            	bw.write("<td>" + msg.get(i) +" </td>"  );
            	bw.write("<td>" + date.get(i) +" </td>"  );
            	bw.write("<td>" + author.get(i) +" </td>"  );
            	bw.write("<td>" + tag.get(i) +" </td>"  );
            	bw.write("</tr>");
            }
            
           
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
