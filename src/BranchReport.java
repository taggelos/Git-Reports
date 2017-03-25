import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BranchReport {


    public static void create(List <String> paths, String name, int commits ,MainReport obj) {

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
            bw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"../mystyle.css\">");
            bw.write("</head> <body class = \"body\" >");
            bw.write("<br><div class = \"title\">" + name + "</font><font color = \"red\"> Branch Report </div>");
            bw.write("<br><table class = \"table\">");
            
            bw.write("<tr><th class = \"th\">Id</th><th class = \"th\">Message</th><th class = \"th\" >Date</th><th class = \"th\" >Author</th><th class = \"th\">Release</th>");
            
            String command,output,outputauthor,outputdate,outputid;

            //int commits=0;
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            /*command = "cmd /C  git log "+name+" --oneline | wc -l";
            output = obj.executeCommand(command, paths.get(0));
            commits = Integer.valueOf(output.substring(0, output.length()-1));*/
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            System.out.println("Number of total branch -> " + name +  " commits is: \n" +  commits);
           
            command = "cmd /C git log "+name+" --oneline";
            outputid = obj.executeCommand(command, paths.get(0));
            
            
            command = "cmd /C git log "+name +" --date=format:%Y-%m-%d | grep Date:";
            outputdate = obj.executeCommand(command, paths.get(0));
            
            command = "cmd /C git log "+name +" | grep Author:";
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
            	
            	//System.out.println("line:" + line + " id-> " + id.get(i) + " tag ->" + tag.get(i)+" \n");
            	bw.write("<td class = \"td\">" + id.get(i) +" </td>"  );
            	bw.write("<td class = \"td\">" + msg.get(i) +" </td>"  );
            	bw.write("<td class = \"td\">" + date.get(i) +" </td>"  );
            	bw.write("<td class = \"td\">" + author.get(i).split("<")[0] +" </td>"  );
            	bw.write("<td class = \"td\">" + line +" </td>"  );
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
