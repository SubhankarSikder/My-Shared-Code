//2. Write a program to Menu driven program using switch statement ( Find addition, subtraction,
//		multiplication and division of to integer numbers 

//By- Subhankar Sikder
package firstpackage;

import java.util.Scanner;

public class Switch {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		// taking both numbers as user-input and asking for operation
		System.out.println("Enter two integers : ");
		int num1 = s.nextInt();
		int num2 = s.nextInt();
		System.out.println(
				"Press 1 for Addition, 2 for Substraction, 3 for" + " Multiplication, 4 for Division and 5 to exit.");
		int con = s.nextInt();
		switch (con) {
		case 1:
			System.out.println("Addition" + (num1 + num2));
			break;
		case 2:
			System.out.println("Substraction" + (num1 - num2));
			break;
		case 3:
			System.out.println("Multiplication" + (num1 * num2));
			break;
		case 4:
			System.out.println("Division" + (num1 / num2));
			break;
		case 5:
			break;

		}

		s.close();
	}

}
