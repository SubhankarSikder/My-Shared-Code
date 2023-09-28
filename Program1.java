//1. Write a program to find the sum of even numbers between 25 to 51.


//By- Subhankar Sikder



package firstpackage;

public class Program1 {

	public static void main(String[] args) {
		int sum=0;
		//loop to iterate over the specified numbers
		for(int i=26;i<51;i++) {
			//sum of only the prime numbers
			if(i%2==0) {
				sum+=i;
			}
		}
		System.out.println("Sum is : "+sum);
	}

}
