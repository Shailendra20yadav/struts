package test;
 

public class EnumTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Day day = Day.SUNDAY;
		if("Sunday".equalsIgnoreCase(day.name())){
			System.out.println(day);
		}
		if(1==day.getValue()){
			System.out.println(day);
		}
		
		for(Day day1:Day.values()){
			System.out.println(day1);
		}

	}
	

}
enum Day{
	SUNDAY(1),
	MONDAY(2),
	TUESDAY(3),
	WEDNESDAY(4),
	TUURSDAY(5),
	FRIDAY(6),
	SATURDAY(7);
	
	private int value;
	Day(int value){
		this.value= value;
	}
	public int getValue(){
		return value;
	}
	
	enum Direction{
		EAST,
		WEST,
		NORTH,
		SOUTH
	}
	
}
