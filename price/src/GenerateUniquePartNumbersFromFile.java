import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import generated.CatalogObjects;

public class GenerateUniquePartNumbersFromFile {
	
	static String inputFilePath=null;
	private static String inputFileName=null;
	private static  String generatedUniquePartNubmersFile = null;
	private static String outputFilePath = null;
	private static  String uniquePartNumbersCommaSeparatedList = null;
	static boolean foundMatch ; // true if want to write matching records else false.

	static {
		try{
			Properties prop = new Properties();
			//prop.load(new FileInputStream("configuration.properties"));
			prop.load(ClassLoader.getSystemResourceAsStream("configuration.properties"));
			inputFilePath = prop.getProperty("inputFilePath");
			inputFileName = prop.getProperty("originalPriceFileName");
			generatedUniquePartNubmersFile = prop.getProperty("generatedUniquePartNubmersFile");
			outputFilePath = prop.getProperty("outputFilePath");
			uniquePartNumbersCommaSeparatedList = prop.getProperty("uniquePartNumbersCommaSeparatedList");
			
			//System.out.println(inputFilePath);
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError(e.getMessage());
		}
	} 	
	
	public static void generateUniquePartNumbersFromFile() throws Exception{
		try(
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath+generatedUniquePartNubmersFile));
				BufferedWriter commaSapratedListwriter = new BufferedWriter(new FileWriter(outputFilePath+uniquePartNumbersCommaSeparatedList));
				)
		{

			JAXBContext jaxbContext = JAXBContext.newInstance(CatalogObjects.class);  
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller(); 
			CatalogObjects catalogObjects = (CatalogObjects)jaxbUnmarshaller.unmarshal(new FileInputStream(inputFilePath+inputFileName));
			List<CatalogObjects.Offer> offers = catalogObjects.getOffer();

			Set<String> uniuePartNumSet = new LinkedHashSet<>();
			Iterator<CatalogObjects.Offer> offersItr = offers.iterator();
			while(offersItr.hasNext()){
				CatalogObjects.Offer currOffer = offersItr.next();
				String partNumber = currOffer.getCatentryPartNumber();
				uniuePartNumSet.add(partNumber);
			}
			Iterator<String> itr = uniuePartNumSet.iterator();
			StringBuilder commaSapratedString = new StringBuilder();
			commaSapratedString.append("'");
			while(itr.hasNext()){
				String partNum = itr.next();
				writer.write(partNum);
				writer.newLine();
				commaSapratedString.append(partNum).append("','");

			}
			commaSapratedString.delete(commaSapratedString.length()-2, commaSapratedString.length());
			commaSapratedListwriter.write(commaSapratedString.toString());
			System.out.println("The partNumbers file genarated at location:: "+outputFilePath+generatedUniquePartNubmersFile);
			System.out.println("comma saprated partNumbers file genarated at location:: "+outputFilePath+uniquePartNumbersCommaSeparatedList);

		}catch(Exception e){
			e.printStackTrace();
			throw e;

		}
	}
	public static void main(String[] args) throws Exception {
		
		generateUniquePartNumbersFromFile();
	}

}
