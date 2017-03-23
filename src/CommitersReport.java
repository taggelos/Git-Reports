import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CommitersReport {


    public static void create(List <String> paths, List<String> brname ,String name ,MainReport obj) {

        try {
        	File myDir = new File(paths.get(1) , "userReports");
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
            File f = new File(myDir, name.replace(" ","") +".htm");

            System.out.println("--->"+f.getAbsolutePath()+"<---");
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> "+name+" </title>");
            bw.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"" +paths.get(0) +"/mystyle.css\">");
            bw.write("</head> <body class = \"body\" >");
            bw.write("<br><div class = \"title\">" + name + "</font><font color = \"red\"> Report </div>");
            bw.write("<br><table class = \"table\">");
            
            bw.write("<tr><th class = \"th\">Branch Name</th><th class = \"th\">Total Commits</th><th class = \"th\">Percentage of Commits</th>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            String command,output;
            String[] line;
			int usercom=0;
            
            for (int i = 0; i < brname.size(); i++) {
				bw.write("<tr>");
				
				command = "cmd /C  git log "+ brname.get(i) +" --oneline | wc -l";
	            output = obj.executeCommand(command, paths.get(0));
	            System.out.println("Number of total branch commits is: "+name +" \n" +  output);
	            int commits = Integer.valueOf(output.substring(0, output.length()-1));
	            
	            
	            command = "cmd /C git log "+brname.get(i) +" | grep Author:";
	            output = obj.executeCommand(command, paths.get(0));
	            line = output.split("\n");
	            for (int j = 0; j < line.length; j++) {
	            	if(line[j].split("<")[0].contains(name))
	                	usercom++;
				}
                
                System.out.println("USERCOM: "+ usercom + "name "+ name +" AXNEEE \n" +  output);
	            
	            bw.write("<td class = \"td\"> "+ brname.get(i) +"</td>");
	            bw.write("<td class = \"td\"> "+ usercom +"</td>");
	            bw.write("<td class = \"td\"> "+ String.format("%.02f", Float.valueOf(usercom)*100/commits) +"%</td>");	    
	            bw.write("</tr>");
	            usercom=0;
			}
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            bw.write("</table>");
            bw.write("<br><br><br>");            
            bw.write("<div class = \"title\"> Average Commits </div>");

        	bw.write("<br><br><table class = \"table\">");
        	
        	
            
        	bw.write("<tr><th class = \"th\">per Day</th><td class = \"td\">"+ name + "</td></tr>");
        	bw.write("<tr><th class = \"th\">per Week</th><td class = \"td\">"+ name + "</td></tr>");
        	bw.write("<tr><th class = \"th\">per Month</th><td class = \"td\">"+ name + "</td></tr>");
        	
        	 /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        	
        	bw.write("</table>");
            bw.write("<br><br><br>");
            
            bw.write("<div class = \"title\"> Average Lines </div>");
        	bw.write("<br><br><table class = \"table\">");
        	
        	command = "cmd /C git log --author=\""+name +"\" --pretty=tformat: --numstat | cut -f 1";
        	output = obj.executeCommand(command, paths.get(0));
        	
        	if(output.isEmpty()) {
        		output="0";
        	}
        	//System.out.println("OUT: "+ output);
        	output = output.replaceAll("-", "0");
        	int total_add=0;
        	line = output.split("\n");
        	for (int j = 0; j < line.length; j++) {
        		//System.out.println( "--->"+ line[j]);
        		total_add += Integer.valueOf(line[j]);
        	}
        	
        	command = "cmd /C git log --author=\""+name +"\" --pretty=tformat: --numstat | cut -f 2";
        	output = obj.executeCommand(command, paths.get(0));
        	
        	if(output.isEmpty()) {
        		output="0";
        	}
        	System.out.println("OUT: "+ output);
        	output = output.replaceAll("-", "0");
        	int total_rmv=0;
        	line = output.split("\n");
        	for (int j = 0; j < line.length; j++) {
        		System.out.println( "--->"+ line[j]);
        		total_rmv += Integer.valueOf(line[j]);
        	}
            
        	bw.write("<tr><th class = \"th\">Added</th><td class = \"td\">"+ total_add + "</td></tr>");
        	bw.write("<tr><th class = \"th\">Removed</th><td class = \"td\">"+ total_rmv + "</td></tr>");
        	bw.write("<tr><th class = \"th\">Updated</th><td class = \"td\">"+ name + "</td></tr>");
            
            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");

            bw.close();
        } catch (IOException ignored) {
        	ignored.printStackTrace();
        }

    }    
}
