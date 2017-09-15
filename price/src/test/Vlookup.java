package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Vlookup {
	static String basePath="C:/shailendra/eclipse workspace/test/";
	private static String fullListFileName="FullList.txt";
	private static  String searcListFileName = "searchList.txt";
	private static  String result = "result.txt";
	private static String saprator="\n"; // valid value , and \n
	static boolean foundMatch = true; // true if want to write matching records else false.
	
	public static void main(String[] args) {
		List<String> fullList = new ArrayList<>(); 
		List<String> searchList = new ArrayList<>();
		List<String> resultList = new ArrayList<>();
		
		try(
				BufferedReader flReader =new BufferedReader(new FileReader(basePath+fullListFileName));
				BufferedReader SrReader =new BufferedReader(new FileReader(basePath+searcListFileName));
				BufferedWriter writer = new BufferedWriter(new FileWriter(basePath+result))
						
				
			){
			fullList = readFileAsList(flReader);
			searchList =readFileAsList(SrReader);
			if(fullList.size()>0 && searchList.size() >0){
				Iterator<String> itr = searchList.iterator();
				while(itr.hasNext()){
					String value =  itr.next();
					if(foundMatch){
						if(fullList.contains(value))
							resultList.add(value);
					}else{
						if(!fullList.contains(value))
							resultList.add(value);
					}
				}
			}
			if(resultList.size()>0){
				if(foundMatch){
					writer.write("Below records are found in the list");
				}else{
					writer.write("Below records are not found in the list");
				}
				writer.newLine();
				Iterator<String> itr = resultList.iterator();
				while(itr.hasNext()){
					String value =  itr.next();
					writer.write(value);
					writer.newLine();
				}
			}else{
				if(foundMatch){
					System.err.println("No Records Match");
					writer.write("No Records Match");
				}else{
					System.err.println("All Records Match");
					writer.write("All Records Match");
				}
			}
			System.out.println("Executed Successfully!! Please refer the result file at path :: "+basePath+result);
		}catch(Exception e){
			e.printStackTrace();
		}
		/*// TODO Auto-generated method stub
		String[] secondArr = { "30213177", "30214550", "30214621", "30213176", "30214631", "30214634", "30213174",
				"30213175", "30214639", "30214643", "30214646", "30214549", "30214632", "30214633", "30214636",
				"30214637", "30214640", "30214642", "30214644", "30214645", "30214635", "30214638", "30214641",
				"30214653", "30222729", "30222728", "30222730", "30222743", "30222742", "30222741", "30219507",
				"30219498", "30219522", "30219524", "30219508", "30219499", "30219500", "30219521", "30219523",
				"30207133", "30207132", "30207134" };
		String fullArray[] = { "30170231", "30174206", "325365", "30174186", "30026914003", "30026914004",
				"30026914001", "30026914002", "30026914005", "30077456", "30180401", "30180405", "30026895005",
				"30026921001", "30077457", "30180402", "30026895002", "30026895008", "30026921002", "30026921004",
				"30077458", "30180403", "30026895011", "30026921003", "30026921005", "30077426", "30077459", "30180404",
				"30026895014", "30147674", "30147675", "30147678", "30151539", "30151671", "30151675", "30151679",
				"30151689", "30147676", "30151537", "30151540", "30151672", "30151676", "30151680", "30147677",
				"30151673", "30151677", "30151687", "30151691", "30151538", "30151631", "30151674", "30151678",
				"30151688", "30146135", "30140230", "30099255", "30172604" };

		for (int i = 0; i < secondArr.length; i++) {
			boolean found = false;
			for (int j = 0; j < fullArray.length; j++) {
				if (fullArray[j].equals(secondArr[i])) {
					found = true;
					break;
				}

			}
			// System.out.println(i);
			if (!found)
				System.out.println(secondArr[i]);
		}*/
	}
	private  static List<String> readFileAsList(BufferedReader br) throws Exception{
		String line =null;
		List<String> list =new ArrayList<>();
		while ((line = br.readLine()) !=null) {
			if(line.length()==0)
				continue;
			if(",".equals(saprator) && line.indexOf(",")!=-1){
				String [] lineValues = line.split(",");
				list.addAll(Arrays.asList(lineValues));
			}else{
				list.add(line);
			}
			
		}
		return list;
	}

}
