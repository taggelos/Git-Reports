import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BranchReport {


    public static void create(List <String> paths, String name, MainReport obj) {

        try {
        	File myDir = new File(paths.get(1) , "branchReports");
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
        	File f = new File(myDir, name+".htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> "+name+" </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse; white-space: nowrap;}th, td { padding: 10px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"blue\">" + name + "</font><font color = \"red\"> Branch Report </font> </h2>");
            bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
            
            bw.write("<tr><th>Id</th><th>Message</th><th>Date</th><th>Author</th><th>Release</th>");
            
            String command,output,outputauthor,outputdate,outputid;

            int commits=0;
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            command = "cmd /C  git log "+name+" --oneline | wc -l";
            output = obj.executeCommand(command, paths.get(0));
            commits = Integer.valueOf(output.substring(0, output.length()-1));
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            System.out.println("Number of total branch -> " + name +  " commits is: \n" +  output);
           
            command = "cmd /C git log "+name+" --oneline | cat";
            outputid = obj.executeCommand(command, paths.get(0));
            
            
            command = "cmd /C git log "+name +" --date=format:%Y-%m-%d | grep Date: | cat ";
            outputdate = obj.executeCommand(command, paths.get(0));
            
            command = "cmd /C git log "+name +" | grep Author: | cat";
            outputauthor = obj.executeCommand(command, paths.get(0));
            
            
            //String[] parts = output.split("\n");
            String line;
            List<String> id = new ArrayList<String>();
            List<String> msg = new ArrayList<String>();
            List<String> date = new ArrayList<String>();
            List<String> author = new ArrayList<String>();
            List<String> tag = new ArrayList<String>();
            
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
            	
            	// author
             
                outputauthor= outputauthor.replaceAll("Author:","");
                line = outputauthor.split("\n")[i];
                while(line.startsWith(" ")){
             		line=line.replaceFirst(" ", "");
             	}
                author.add(line);
                
                // release - tag
                
                command = "cmd /C  git tag --contains "+id.get(i);
                output = obj.executeCommand(command, paths.get(0));
                tag.add(output);
                
                line = tag.get(i).replace("\n", " / ");
                if(tag.get(i).length() != 0)
                	line = line.substring(0, line.length()-2);
            	
            	System.out.println("line:" + line + " id-> " + id.get(i) + " tag ->" + tag.get(i)+" \n");
            	bw.write("<td>" + id.get(i) +" </td>"  );
            	bw.write("<td>" + msg.get(i) +" </td>"  );
            	bw.write("<td>" + date.get(i) +" </td>"  );
            	bw.write("<td>" + author.get(i) +" </td>"  );
            	bw.write("<td>" + line +" </td>"  );
            	bw.write("</tr>");
            }
            
            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");

            bw.close();
        } catch (IOException ignored) {

        }

    }
    
}
