

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class FormatXML {
	static String inputFilePath=null;
	private static String inputFileName=null;
	//private static String outputFilePath = null;

	static {
		try{
			Properties prop = new Properties();
			//prop.load(new FileInputStream("configuration.properties"));
			prop.load(ClassLoader.getSystemResourceAsStream("configuration.properties"));
			inputFilePath = prop.getProperty("inputFilePath");
			inputFileName = prop.getProperty("originalPriceFileName");
			//outputFilePath = prop.getProperty("outputFilePath");
			
			//System.out.println(inputFilePath);
		}catch(Exception e){
			e.printStackTrace();
			throw new ExceptionInInitializerError(e.getMessage());
		}
	} 	
	
  public static void main(String[] argv) throws Exception {
	  String inputFile = inputFilePath+inputFileName;
	  String outputFile = inputFilePath+"Formated_"+inputFileName;
	  formatXMLFile(inputFile, outputFile);
  }

  static void formatXMLFile(String inputFile,String outputFile) throws Exception{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(new InputSource(new InputStreamReader(new FileInputStream(
    		inputFile))));

    Transformer xformer = TransformerFactory.newInstance().newTransformer();
    xformer.setOutputProperty(OutputKeys.METHOD, "xml");
    xformer.setOutputProperty(OutputKeys.INDENT, "yes");
    xformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    xformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
    xformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
    Source source = new DOMSource(document);
    Result result = new StreamResult(new File(outputFile));
    xformer.transform(source, result);
    System.out.println("Formated Successfully! "+outputFile);
  }
}
