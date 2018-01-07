

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Properties;

public class RunPriceUtility {
	
	static String inputFilePath=null;
	private static String inputFileName=null;
	private static  String outputFileName = null;
	private static String outputFilePath = null;
	private static  String partNubmersInDbList = null;
	private static String searcListFileName = null;
	private static  String lookupResultFileName = null;
	private static String generatedUniquePartNubmersFile = null;

	static {
		try{
			Properties prop = new Properties();
			//prop.load(new FileInputStream("configuration.properties"));
			prop.load(ClassLoader.getSystemResourceAsStream("configuration.properties"));
			inputFilePath = prop.getProperty("inputFilePath");
			inputFileName = prop.getProperty("originalPriceFileName");
			searcListFileName = prop.getProperty("partnumbersList");
			outputFilePath = prop.getProperty("outputFilePath");
			generatedUniquePartNubmersFile = prop.getProperty("generatedUniquePartNubmersFile");
			outputFileName = prop.getProperty("generatedPriceFileName");
			partNubmersInDbList = prop.getProperty("partNubmersInDbList");
			lookupResultFileName = prop.getProperty("lookupResultFileName");
			
			//System.out.println(inputFilePath);
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError(e.getMessage());
		}
	} 	

	public static void main(String[] args) {
		try {
			GenerateUniquePartNumbersFromFile.generateUniquePartNumbersFromFile();
			// copy generated file from output folde in input folder so that it will be available as input file for next step
			copyFile(outputFilePath+generatedUniquePartNubmersFile,inputFilePath+generatedUniquePartNubmersFile);
			DBReader.readPartnumbersFromDB();
			copyFile(outputFilePath+partNubmersInDbList,inputFilePath+partNubmersInDbList);
			Lookup.lookupPartnumbers();
			copyFile(outputFilePath+lookupResultFileName,inputFilePath+lookupResultFileName);
			PriceFileGenerator.generatePriceFile();
						
		}catch (Exception e){
			
		}

	}
	
	private static void copyFile(String  sourceFilePath, String destFilePath)throws IOException {
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
		try{
		
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		
		}finally{
			
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}

	}
}
