

import generated.RunBookEntry;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class RunBookUtility {
	
	static String inputFilePath=null;
	private static String inputFileName=null;
	private static String outputFilePath=null;
	private static String generatedPriceFileName=null;
	
	private static LinkedHashMap<String, RunBookEntry> overnightJobs = new LinkedHashMap<String, RunBookEntry>();
	private static Map<String, String> jobsMap = new HashMap<String, String>();


	static {
		try{
			Properties prop = new Properties();
			//prop.load(new FileInputStream("configuration.properties"));
			prop.load(ClassLoader.getSystemResourceAsStream("RunbookConfiguration.properties"));
			inputFilePath = prop.getProperty("inputFilePath");
			inputFileName = prop.getProperty("originalPriceFileName");
			generatedPriceFileName = prop.getProperty("generatedPriceFileName");
			outputFilePath = prop.getProperty("outputFilePath");
			
			
			jobsMap.put("Catalog Dataload","PIM Catalog Load");
			jobsMap.put("Extracting the stockdb data ","Stock Article Extract");
			jobsMap.put("SEO URL Keyword generator","SEO KeyWord Gen");
			jobsMap.put("Price Dataload","Price Data Load");
			jobsMap.put("ShowOrHideWasPricesCmd","ShowOrHideWasPrices");
			jobsMap.put("Solr Indexing for Staging","SOLR Index");
			jobsMap.put("DB Consolidation","DB Consolidation");
			jobsMap.put("DB Propagation","Data Propagation");
			jobsMap.put("Web Assets sync","Web Assets Propagation");
			jobsMap.put("Webserver RewriteMap","301 Redirects");
			jobsMap.put("Solr propagation to Production","SOLR Propagation");
			jobsMap.put("Stock Dataload on production","Stock Load (PROD)");
			jobsMap.put("Extracting catalog data","Catalog extract");
			jobsMap.put("Extracting price data","Price Extract");
			jobsMap.put("DB Registries Refresh","DB Registry Refresh");
			jobsMap.put("Cache","DynaCache Clear");
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError(e.getMessage());
		}
	} 	

	public static void main(String[] args) throws Exception {
		try 
		(
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath+generatedPriceFileName));
				 BufferedReader reader = new BufferedReader(new FileReader (inputFilePath+inputFileName));
			)
		{
			
			   
			    String         line = null;
			    //StringBuilder  stringBuilder = new StringBuilder();
			    //String         ls = System.getProperty("line.separator");
			    
			    System.out.println("outputting file::");
			    
			    while((line = reader.readLine()) != null) {
		            /*stringBuilder.append(line);
		            stringBuilder.append(ls);*/
			    	System.out.println("-------------processing new line------------");
			    	System.out.println(line);
			    	processLine(line);
			    	
			    }
			    
			System.out.println("=====================Printing output======================");
			for(String jobName: overnightJobs.keySet()){
				RunBookEntry entry = overnightJobs.get(jobName);
				writer.write(String.format("%s\t%s\t%s\t%s\t%s\t%s", entry.getJobName(),entry.getJobType(),entry.getStatus(), displayTime(entry.getJobStartTime()), displayTime(entry.getJobEndTime()), entry.getTimeTaken()));
				writer.newLine();
				System.out.println(String.format("%s\t%s\t%s\t%s\t%s\t%s", entry.getJobName(),entry.getJobType(),entry.getStatus(), displayTime(entry.getJobStartTime()), displayTime(entry.getJobEndTime()), entry.getTimeTaken()));
				
			}
			    
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}
		

	}

	private static void processLine(String line) {
		
		if(!line.contains("--")){
			return;
		}else{
			String[] lineArr = line.split("--");
			String tempJobName= new String();
			Date tempDate= null;
			
			boolean startEntry=false;
			boolean endEntry=false;
			for(String element: lineArr){
				//tempJobName= null; tempDate=null; startEntry = false; endEntry = false;
				if(element.contains("dun-stg-app01")){
					continue;
				}else if(element.contains("-") && element.length()==14){
					// date time element
					tempDate = resolveDate(element);
					System.out.println("resolved date returned: "+ displayTime(tempDate));
				}else if(element.contains("Started") || element.contains("started") || element.contains("Starting")){
					startEntry = true;
				}else if(element.contains("Successful") || element.contains("Completed") || element.contains("completed") || element.contains("Finished") || element.contains("No Source files found")){
					endEntry = true;
				}
				
				if(endEntry || startEntry){
					tempJobName = resolveJobName(element);
				}
			}
			
			if(null != tempJobName && !tempJobName.isEmpty() && null!=tempDate){
				RunBookEntry entry = overnightJobs.get(tempJobName);
				if(null == entry){
					entry = new RunBookEntry();
					entry.setJobName(tempJobName);
					overnightJobs.put(tempJobName, entry);
				}
				if(startEntry){
					System.out.println("start entry for job: "+tempJobName+" at: "+displayTime(tempDate));
					entry.setJobStartTime(tempDate);
				}else if(endEntry){
					System.out.println("End entry for job: "+tempJobName+" at: "+displayTime(tempDate));
					entry.setJobEndTime(tempDate);
					evaluateJobTimeTaken(entry);
				}
				System.out.println("Updated entry: \n"+ entry);
				overnightJobs.put(tempJobName, entry);
			}
		}
		
	}

	private static void evaluateJobTimeTaken(RunBookEntry entry) {
		if(null!=entry.getJobStartTime() && null!=entry.getJobEndTime()){
			Date startTime, endTime;
			startTime = entry.getJobStartTime();
			endTime = entry.getJobEndTime();
			
			long difference = endTime.getTime() - startTime.getTime();
			int diffInSec = (int)difference/1000;
			int min = diffInSec/60;
			
			String displayHour = diffInSec/3600 >0 ? String.valueOf(diffInSec/3600) : "00";
			String displayMin = min % 60 >0 ? String.valueOf(min % 60) : "00";
			String displaySec = diffInSec % 60 > 0 ? String.valueOf(diffInSec % 60) : "00";
			
			entry.setTimeTaken(String.format("%s:%s:%s", displayHour, displayMin, displaySec));
			System.out.println("time difference evaluated is: "+ entry.getTimeTaken());
			
		}
	}

	private static String resolveJobName(String element) {
		for(String jobString : jobsMap.keySet()){
			if(element.contains(jobString)){
				return jobsMap.get(jobString);
			}
		}
		return null;
	}

	private static Date resolveDate(String element) {
		String tempDate = null;
		Date date = null;
		SimpleDateFormat dt = new SimpleDateFormat("yymmdd-HHmmss"); 
		SimpleDateFormat dt2 = new SimpleDateFormat("HH:mm:ss");
		try{
			date = dt.parse(element);
			tempDate = dt2.format(date).toString();
			System.out.println("Date resolved is:"+ tempDate);
			
		} catch (ParseException e) {
			System.out.println("Exception while evaluatng start/end time");
			e.printStackTrace();
		}
		return date ;
	}
	
	private static String displayTime(Date jobStartTime2) {
		SimpleDateFormat dt2 = new SimpleDateFormat("HH:mm:ss");
		return dt2.format(jobStartTime2).toString();
	}
	/*private static void copyFile(String  sourceFilePath, String destFilePath)throws IOException {
		File sourceFile = new File(sourceFilePath);
		File destFile = new File(destFilePath);
		if (!sourceFile.exists()) {
			return;
		}
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}

	}*/
}
