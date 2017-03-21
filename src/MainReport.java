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
        
        System.out.println("Our paths are: " + " \n "+ args[0] + " \n " + args[1]);

        try {
            File f = new File(args[1] + "/MainReport.htm");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head>");
            bw.write("<title> Exercise-1 </title>");
            bw.write("<style>table, th, td {border: 1px solid black; border-collapse: collapse; white-space: nowrap;}th, td { padding: 5px; text-align: left; }</style>");
            bw.write("</head> <body bgcolor=\"#fcf5ef\" >");
            bw.write("<h2><font color = \"red\"> Report for </font> <a target=\"_blank\" href=\""+ args[0] +"\"> Git repository </a></h2>");
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
            bw.write("<tr><th>Number of files</th><td>"+ output+ "</td></tr>");
            
            
            //command = "git diff --stat 4b825dc642cb6eb9a060e54bf8d69288fbee4904";
            command = "cmd /C git ls-files | xargs wc -l | tail -1";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total lines is: \n" +  totalLines(output)+ "\n") ;
            bw.write("<tr><th>Number of total lines</th><td>" + totalLines(output)+ "</td></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git branch | wc -l";
            output = obj.executeCommand(command, args[0]);
            int numBranches = Integer.parseInt(output.replace("\n", ""));
            System.out.println("Number of total branches is: \n" +  numBranches);
            bw.write("<tr><th>Number of total branches</th><td>" + numBranches+ "</td></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C git tag | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total tags is: \n" +  output);
            bw.write("<tr><th>Number of total tags</th><td>" + output+ "</td></tr>");
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | uniq | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total committers is: \n" +  output);
            bw.write("<tr><th>Number of total committers</th><td>" + output+ "</td></tr>");
            commiters_count = Integer.valueOf(output.substring(0, output.length()-1));
          
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            command = "cmd /C  git log | findstr Author: | sort | wc -l";
            output = obj.executeCommand(command, args[0]);
            System.out.println("Number of total commits is: \n" +  output);
            bw.write("<tr><th>Number of total commits</th><td>" + output+ "</td></tr>");
            commits = Integer.valueOf(output.substring(0, output.length()-1));
            
            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            //git shotlog -sn --all | cut -f 1  <---
            //git shotlog -sn --all | cut -f 2

            /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
            
            List<String> commiters = new ArrayList<String>();
            List<String> paths = new ArrayList<String>();
            paths.add(args[0]);
            paths.add(args[1]);
            
            bw.write("</table>");
            List<String> brnames = createBranchTable(numBranches,bw, paths,commits, obj);
            
            commiters =createCommitersTable(bw, paths,brnames, commiters_count, commits, obj);
            
            System.out.println("\n--------------------------------------");
            
            
            bw.write("</body>");
            bw.write("</html>");

            //br.close();
            bw.close();
        } catch (IOException ignored) {

        }

    }

    public String executeCommand(String command, String path) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command, null, new File(path));
            
            //System.out.println(p.exitValue());
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line+'\n');
            }
            p.waitFor();
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
    
    private static List<String> createBranchTable(int brnum, BufferedWriter bw, List<String> paths ,int commits, MainReport obj) throws IOException{
	    bw.write("<br>");
	    bw.write("<h2><font color = \"red\"> Branches </font></h2>");
    	bw.write("<br>");
    	bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
    	bw.write("<tr><th>Name</th><th>Date of Creation</th><th>Last Date of Modification</th><th>Percentage of Commits</th></tr>");
    	
    	String command, output;
    	int brcommits;
    	
    	ArrayList<String> date = new ArrayList<>();
    	ArrayList<String> auth = new ArrayList<>(); //TODO OR NOT TODO
    	ArrayList<String> brnames = new ArrayList<>();
    	for (int i=0; i<brnum;i++){
    		command = "git for-each-ref --sort=-committerdate refs/heads/ --format=%(committerdate:short),%(authorname),%(refname:short)";
    		output = obj.executeCommand(command, paths.get(0));
    		output = output.split("\n")[i];
         	
         	date.add(output.split(",")[0]);
         	auth.add(output.split(",")[1]);
         	brnames.add(output.split(",")[2]);
         	
         	command = "cmd /C  git log "+brnames.get(i)+" --oneline | wc -l";
            output = obj.executeCommand(command, paths.get(0));
            brcommits = Integer.valueOf(output.substring(0, output.length()-1));
            
         	BranchReport.create(paths, brnames.get(i), brcommits ,obj);
         	
    		bw.write("<tr>");
    		bw.write("<td><a target=\"_blank\" href="+paths.get(1)+"/branchReports/"+brnames.get(i)+".htm>" + brnames.get(i)+ "</a></td>");
    	    		
    		if(brnames.get(i).equals("master")) {
    			command = "cmd /C git log master --date=format:%Y-%m-%d | grep Date: | tail -1";
    		}
    		else {
    			command = "cmd /C git log master..."+brnames.get(i)+" --date=format:%Y-%m-%d | grep Date: | tail -1";
    		}
    		
    		output = obj.executeCommand(command, paths.get(0));
    		
    		output = output.replace("Date:", "");
    		output = output.replaceAll(" ", "");
    		
    		bw.write("<td>"+output+"</td>");  
    		
    		bw.write("<td>"+date.get(i)+"</td>");
    		
    		bw.write("<td>"+String.format("%.02f", Float.valueOf(brcommits)*100/commits)+"%</td>");

        	bw.write("</tr>");
    	}
    	bw.write("</table>");
    	return brnames;
    } 
    
    private static List<String> createCommitersTable(BufferedWriter bw, List<String> paths,List<String> brnames, int commiters_count, int commits, MainReport obj) throws IOException {
    	String s,command,out;
       
        bw.write("<br>");
	    bw.write("<h2><font color = \"red\"> Commiters </font></h2>");
    	bw.write("<br>");
    	bw.write("<table bgcolor=\"#FFFFFF\" style=\"width:15%\">");
    	bw.write("<tr><th>Name</th><th>Number of commits</th><th>Percentage of Commits</th></tr>");
    	
    	
        List<String> commiters = new ArrayList<String>();
        for (int i = 0; i < commiters_count; i++) {
         	command = "cmd /C  git shortlog -sne --all";
         	out = obj.executeCommand(command, paths.get(0));
         	
            
         	s = out.split("\n")[i];
         	while(s.startsWith(" ")){
         		s=s.replaceFirst(" ", "");
         	}

         	commiters.add((s.split("\t")[1]).split("<")[0]);
         	
         	String name =commiters.get(i);
         	name = name.substring(0,name.length()-1);
         	
         	CommitersReport.create(paths,brnames,name,obj );
         	
         	bw.write("<tr>");
         	
         	bw.write("<td><a target=\"_blank\" href="+paths.get(1)+"/userReports/"+name.replace(" ","")+".htm>" + s.split("\t")[1] + "</a></td>");
         	bw.write("<td> "+ s.split("\t")[0] +"</td>");
         	bw.write("<td>" + String.format("%.02f", Float.valueOf(s.split("\t")[0])*100/commits) + "%</td>");
         	bw.write("<tr>");	
        }
        bw.write("</table>");
        return commiters;
    }
    
    
}
