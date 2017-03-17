import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MainReport {


    public static void main(String[] args) {

        if (args[0] == null || args[0].trim().isEmpty() || args[1] == null || args[1].trim().isEmpty()) {
            System.out.println("You need to specify a path!");
            return;
        }
        
        System.out.println("Total number of lines in the file are: " + " \n "+ args[0] + " \n " + args[1]);

        try {
            File f = new File(args[1] + "/axne.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> Exercise-1 </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse;}th, td { padding: 5px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"red\"> Report for </font> <a target=\"_blank\" href=\""+ args[1] +"\"> Git repository </a></h2>");
            bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
            
            MainReport obj = new MainReport();

            String command;
            String output;
            
            
            int commiters_count=0;
            int commits=0;
            
            command = "cmd /C git ls-files | wc -l";

            //in windows
        	//System.getProperty("os.name").toLowerCase().startsWith("windows");
            
            output = obj.executeCommand(command, args[0]);
            
            //System.out.println(System.getProperty("user.dir"));

            System.out.println("Number of files is: \n" +  output);
            bw.write("<tr><th>Number of files</th><th>"+ output+ "</th></tr>");
            
            
            //command = "git diff --stat 4b825dc642cb6eb9a060e54bf8d69288fbee4904";
            command = "cmd /C git ls-files | xargs wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total lines is: \n" +  totalLines(output)+ "\n") ;
            bw.write("<tr><th>Number of total lines</th><th>" + totalLines(output)+ "</th></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git branch -ar | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total branches is: \n" +  output);
            bw.write("<tr><th>Number of total branches</th><th>" + output+ "</th></tr>");
            int numBranches = Integer.parseInt(output.replace("\n", ""));
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git tag | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total tags is: \n" +  output);
            bw.write("<tr><th>Number of total tags</th><th>" + output+ "</th></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | uniq | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total committers is: \n" +  output);
            bw.write("<tr><th>Number of total committers</th><th>" + output+ "</th></tr>");
            commiters_count = Integer.valueOf(output.substring(0, output.length()-1));
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total commits is: \n" +  output);
            bw.write("<tr><th>Number of total commits</th><th>" + output+ "</th></tr>");
            commits = Integer.valueOf(output.substring(0, output.length()-1));
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            /*
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
            */
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  //Tha xreiastei gia pososto commits ana suggrafea
            
            command = "cmd /C  git shortlog -sne --all";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of commits per User: \n" +  output);
            //bw.write("<tr><th>Number of commits per User</th><th>" + output+ "</th></tr>");
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/ //
            
            //git shotlog -sn --all | cut -f 1  <---
            //git shotlog -sn --all | cut -f 2
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/  //Tha xreiastei gia onomasia branches
            
            command = "cmd /C git for-each-ref --sort=-committerdate refs/heads/";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of commits per User: \n" +  output);
            //bw.write("<tr><th>Number of commits per User</th><th>" + output+ "</th></tr>");
            
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            
            System.out.println("\nOLA GOOD (oxi)");

            bw.write("</table>");
            
            bw.write("<br>");
    	    bw.write("<h2><font color = \"red\"> Commiters </font></h2>");
        	bw.write("<br>");
        	bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
        	bw.write("<tr><th>Name</th><th>Number of commits</th><th>Percentage</th></tr>");

            for (int i = 0; i < commiters_count; i++) {
             	command = "cmd /C  git shortlog -sne --all";
             	output = obj.executeCommand(command, args[0]);
             	computePercentages(output, bw, args[0], i, commits);	
            }
            bw.write("</table>");
            
            createBranchTable(numBranches,bw,args[0]);
            
            bw.write("</body>");
            bw.write("</html>");

            //br.close();
            bw.close();
        } catch (IOException ignored) {

        }

    }

    private String executeCommand(String command, String path) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command, null, new File(path));
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
        //.substring(0,output.length()-2)
        return output.toString();
    }
    
    //git for-each-ref --sort=committerdate --format=%(committerdate:iso8601)%09%(refname) refs/heads
    
    private static String totalLines(String out){
    	String[] parts = out.split("\n");
    	//remove the word total
    	out = parts[parts.length-1].replace("total" , ""); 
    	//remove whitespaces from beginning
    	parts = out.split("\\s+");   
		return parts[1];
    }
    
     private static void createBranchTable(int num, BufferedWriter bw, String path) throws IOException{
	    bw.write("<br>");
	    bw.write("<h2><font color = \"red\"> Branches </font></h2>");
    	bw.write("<br>");
    	bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
    	bw.write("<tr><th>Name</th><th>Date of Creation</th><th>Last Date of Modification</th></tr>");
    	for (int i=0; i<num;i++){
    		bw.write("<tr>");
    		bw.write("<td> <a target=\"_blank\" href= \"axne2.htm\" >55577854 </a></td>");
    		bw.write("<td> 55577854 </td>");
    		bw.write("<td> 55577854 </td>");  

        	bw.write("</tr>");
    	}
    	bw.write("</table>");
    	String name = "TestBranch";  //TODO
    	BranchReport.create(path, name);
    }
    
     private static List<String> computePercentages(String out, BufferedWriter bw, String path, int i, int commits) throws IOException {
		String s;
		int[] c;
		List<String> commiters = new ArrayList<String>();
     
     	s = out.split("\n")[i];
     	while(s.startsWith(" ")){
     		s=s.replaceFirst(" ", "");
     	}

     	commiters.add((s.split("\t")[0]));
     	//c[0] = Integer.valueOf(s.split("\t")[0]);
     	System.out.println("--> "+ s.split("\t")[0]);
     	System.out.println("--> "+ s.split("\t")[1]);
     	System.out.println("--> "+ (Float.valueOf(s.split("\t")[0])/commits)*100 + "%");
     	
     	bw.write("<tr>");
     	bw.write("<td>"+ s.split("\t")[1] +"</td>");
     	bw.write("<td> "+ s.split("\t")[0] +"</td>");
     	bw.write("<td>" + (Float.valueOf(s.split("\t")[0])/commits)*100 + "%</td>");
     	bw.write("<tr>");
     	
        return commiters; 
     }
}
