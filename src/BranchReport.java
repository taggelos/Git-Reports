import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BranchReport {

    public static void create(List <String> paths, String name, int commits ,MainReport obj) {

        try {
        	File myDir = new File(paths.get(1) , "branchReports");
        	// if the directory does not exist, create it
        	if (!myDir.exists()) {
        	    System.out.println("Creating directory: " + myDir.getName());
        	    boolean result = false;
        	    myDir.mkdir();
        	    result = true;
        	         
        	    if(result) {    
        	        System.out.println(myDir.getName()+" created");  
        	    }
        	    else{
        	    	System.out.println("Error creating directory "+ myDir.getName());
        	    }
        	}
        	System.out.println("---Writing in "+ myDir.getName()+"---");
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
            
            String command; 
            List <String> output,outputid,outputdate,outputauthor;
            System.out.println("Branch: " + name +  " , total commits: " +  commits);
           
            command = "cmd /C git log "+name+" --oneline";
            outputid = obj.executeCommand(command, paths.get(0));
                
            command = "cmd /C git log "+name +" --date=format:%Y-%m-%d";
            outputdate = obj.executeCommand(command, paths.get(0));
            
            
            command = "cmd /C git log "+name;
            outputauthor = obj.executeCommand(command, paths.get(0));
            
            String line="";
            List<String> id = new ArrayList<String>();
            List<String> msg = new ArrayList<String>();
            List<String> date = new ArrayList<String>();
            List<String> author = new ArrayList<String>();
            List<String> tag = new ArrayList<String>();
            String tagtemp="";
            
            for (int i=0; i<commits;i++){
            	bw.write("<tr>");
            	line = outputid.get(i);
            	
            	// id + msg
            	id.add(line.split(" ")[0]);
            	msg.add(line.split(" ", 2)[1]);           	
            	
            	// date
            	for (int j = 0; j < outputdate.size(); j++) {
					if(outputdate.get(j).contains("Date: ")){
						line = outputdate.get(j).replace(" ", "");
						date.add(line.split(":")[1]);
					}
				}
            	
            	for (int j = 0; j < outputauthor.size(); j++) {
					if(outputauthor.get(j).contains("Author: ")){
						line = outputauthor.get(j).replaceFirst(" ", "");
						author.add(line.split(":")[1]);
					}
				}
                
                // release - tag 
                command = "cmd /C  git tag --contains "+id.get(i);
                output = obj.executeCommand(command, paths.get(0));
                if(output.isEmpty()){	
                    tag.add(" - ");
                }
                else{
                	for(int j=0; j<output.size()-1; j++){
                		tagtemp+=output.get(j)+" / ";
                	}
                	tagtemp+=(output.get(output.size()-1));
                	tag.add(tagtemp);
                	tagtemp="";
                }
                            	
            	bw.write("<td class = \"td\">" + id.get(i) +" </td>"  );
            	bw.write("<td class = \"td\">" + msg.get(i) +" </td>"  );

            	bw.write("<td class = \"td\">" + date.get(i) +" </td>"  );

            	bw.write("<td class = \"td\">" + author.get(i).split("<")[0] +" </td>"  );
            	bw.write("<td class = \"td\">" + tag.get(i) +" </td>"  );
            	bw.write("</tr>");
            }
            
            bw.write("</table>");
            bw.write("</body>");
            bw.write("</html>");

            bw.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }

    }
    
}
