import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generated.CatalogObjects;



public class PriceFileGenerator {
	
	static String inputFilePath=null;
	private static String inputFileName=null;
	private static  String searcListFileName = null;
	private static  String outputFileName = null;
	private static String outputFilePath = null;
	static boolean foundMatch = false; // true if want to write matching records else false.

	static {
		try{
			Properties prop = new Properties();
			//prop.load(new FileInputStream("configuration.properties"));
			prop.load(ClassLoader.getSystemResourceAsStream("configuration.properties"));
			inputFilePath = prop.getProperty("inputFilePath");
			inputFileName = prop.getProperty("originalPriceFileName");
			searcListFileName = prop.getProperty("partnumbersList");
			outputFilePath = prop.getProperty("outputFilePath");
			outputFileName = prop.getProperty("generatedPriceFileName");
			foundMatch = Boolean.valueOf(prop.getProperty("generateForMatchedRecord"));
			
			//System.out.println(inputFilePath);
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError(e.getMessage());
		}
	} 	
	
	public static void generatePriceFile() throws Exception{
		List<String> searchList = new ArrayList<>();
		try(
				BufferedReader SrReader =new BufferedReader(new FileReader(inputFilePath+searcListFileName));
				)
		{

			JAXBContext jaxbContext = JAXBContext.newInstance(CatalogObjects.class);  

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			/*
			 * UnComment below two lines if want to remove stadalone attribute from xml declaration 
			 * jaxbMarshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
				jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				
			*/

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			CatalogObjects catalogObjects = (CatalogObjects)jaxbUnmarshaller.unmarshal(new FileInputStream(inputFilePath+inputFileName));

			List<CatalogObjects.Offer> offers = catalogObjects.getOffer();
			searchList =readFileAsList(SrReader);
			if(offers.size()>0 && searchList.size() >0){
				if (!foundMatch){
					Iterator<String> itr = searchList.iterator();
					while(itr.hasNext()){
						String value =  itr.next();
						Iterator<CatalogObjects.Offer> offersItr = offers.iterator();
						while(offersItr.hasNext()){
							CatalogObjects.Offer currOffer = offersItr.next();
							String partNumber = currOffer.getCatentryPartNumber();
							if(value.equals(partNumber)){
								offersItr.remove();
							}
						}


					}

					jaxbMarshaller.marshal(catalogObjects, new File(outputFilePath+outputFileName));
					System.out.println("Successful removed the offers from the file and generated new file at location:: "+outputFilePath+outputFileName);

				}else{
					CatalogObjects outputCatalogObjects = new CatalogObjects();
					List<CatalogObjects.Offer> filteredOffers  = new ArrayList<>();
					Iterator<String> itr = searchList.iterator();
					while(itr.hasNext()){
						String value =  itr.next();
						Iterator<CatalogObjects.Offer> offersItr = offers.iterator();
						while(offersItr.hasNext()){
							CatalogObjects.Offer currOffer = offersItr.next();
							String partNumber = currOffer.getCatentryPartNumber();
							if(value.equals(partNumber)){
								filteredOffers.add(currOffer);
							}
						}


					}
					outputCatalogObjects.setOffer(filteredOffers);
					outputCatalogObjects.setLoadItemName("Offer");
					jaxbMarshaller.marshal(outputCatalogObjects, new File(outputFilePath+outputFileName));
					System.out.println("Successful generated new filtered file at location:: "+outputFilePath+outputFileName);

				}

			}

		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	public static void main(String ... args) throws Exception{

		generatePriceFile();

	}
		private  static List<String> readFileAsList(BufferedReader br) throws Exception{
			String line =null;
			List<String> list =new ArrayList<>();
			while ((line = br.readLine()) !=null) {
				if(line.length()==0)
					continue;
				list.add(line);

			}
			return list;
		}

}
