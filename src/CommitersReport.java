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
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse; white-space: nowrap;}th, td { padding: 5px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"blue\">" + name + "</font><font color = \"red\"> Report </font> </h2>");
            bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
            
            bw.write("<tr><th>Branch Name</th><th>Total Commits</th><th>Percentage of Commits</th>");
            
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
	            
	            bw.write("<td> "+ brname.get(i) +"</td>");
	            bw.write("<td> "+ usercom +"</td>");
	            bw.write("<td> "+ String.format("%.02f", Float.valueOf(usercom)*100/commits) +"%</td>");	    
	            bw.write("</tr>");
	            usercom=0;
			}
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            bw.write("</table>");
            bw.write("<br>");
            
            bw.write("<h2><font color = \"red\"> Average Commits </font></h2>");
        	bw.write("<br>");
        	bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
        	
        	
            
        	bw.write("<tr><th>per Day</th><td>"+ name + "</td></tr>");
        	bw.write("<tr><th>per Week</th><td>"+ name + "</td></tr>");
        	bw.write("<tr><th>per Month</th><td>"+ name + "</td></tr>");
        	
        	 /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        	
        	bw.write("</table>");
            bw.write("<br>");
            
            bw.write("<h2><font color = \"red\"> Average Lines </font></h2>");
        	bw.write("<br>");
        	bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
        	
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
            
        	bw.write("<tr><th>Added</th><td>"+ total_add + "</td></tr>");
        	bw.write("<tr><th>Removed</th><td>"+ total_rmv + "</td></tr>");
        	bw.write("<tr><th>Updated</th><td>"+ name + "</td></tr>");
            
            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");

            bw.close();
        } catch (IOException ignored) {
        	ignored.printStackTrace();
        }

    }    
}
