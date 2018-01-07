package test.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortTest {
	public static void main(String ...args){
		List<Student> list = new ArrayList<Student>();

		list.add(new Student(15,"abahay","Pratap",4.0f));
		list.add(new Student(15,"abahay","Singh",4.0f));
		list.add(new Student(15,"abahay","kumar",3.0f));
		list.add(new Student(15,"shailendra","kumar",3.5f));
		Collections.sort(list);
		for (Student student : list) {
			System.out.println(student.FirstName+"\t"+student.lastName+"\t"+student.cgpa);

		}
	}


}

class Student  implements Comparable<Student>{
	int age;
	String FirstName;
	String lastName;
	float cgpa;
	
	public Student(int age, String firstName, String lastName, float cgpa) {
		super();
		this.age = age;
		FirstName = firstName;
		this.lastName = lastName;
		this.cgpa = cgpa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((FirstName == null) ? 0 : FirstName.hashCode());
		result = prime * result + age;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (FirstName == null) {
			if (other.FirstName != null)
				return false;
		} else if (!FirstName.equals(other.FirstName))
			return false;
		if (age != other.age)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Student st) {
		int cgapResult = Float.compare(st.cgpa,this.cgpa);
		if(cgapResult==0){
			int firstNameResult = this.FirstName.compareTo(st.FirstName);
			if(firstNameResult==0){
				return this.lastName.compareTo(st.lastName);
				
			}else 
				return firstNameResult;
			
		}else{
			return cgapResult;
		}
	}
	
}