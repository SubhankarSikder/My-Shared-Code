package com.package1;

import java.sql.*;
import java.util.Scanner;

public class Test {

	static Scanner s = new Scanner(System.in);
	private static final String DB_URL = "jdbc:mysql://localhost:3306/employeesalary";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "*****";

	public static void main(String[] args) {

		boolean flag = true;
		while (flag) {
			menu();
			int option = s.nextInt();
			switch (option) {
			case 1:
				insertRecord();
				break;
			case 2:
				showAll();
				break;
			case 3:
				updateRecord();
				break;
			case 4:
				flag = false;
				break;
			}
		}

		// createTable();
		// insertRecord();
		// updateRecord();
	}

	private static void menu() {
		System.out.println("Welcome to employee salary management");
		System.out.println("======================================");
		System.out.println("Press 1 to add employee record");
		System.out.println("Press 2 to view record");
		System.out.println("Press 3 to update record");
		System.out.println("Press 4 to exit");
	}

	private static void createTable() {
		try {
			// step2 load driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// step3 establish connection
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			if (con != null) {
				System.out.println("Connected");
			} else {
				System.out.println("Not connected");
			}

			// step4 prepare statement to execute query
			Statement st = con.createStatement();
			String query = "create table employee(id int primary key, name varchar(30) not null, daysWorked int not null,unpaid_leave  int, salary int)";

			// step5 execute query
			boolean isTableCreated = st.execute(query);

			// step6 close the connection
			st.close();
			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertRecord() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			PreparedStatement st = con
					.prepareStatement("insert into employee(id,name,daysWorked,unpaid_leave,salary) values(?,?,?,?,?)");

			System.out.println("Enter id of employee :");
			int id = s.nextInt();
			// idChecking(id);
			if (idChecking(id)) {
				s.nextLine();
				System.out.println("Enter name of employee :");
				String name = s.nextLine();
				System.out.println("Enter daysWorked for " + name + " :");
				int days = s.nextInt();
				s.nextLine();
				System.out.println("Enter number of unpaid leave for " + name + " :");
				int unpaid_leave = s.nextInt();

				st.setInt(1, id);
				st.setString(2, name);
				st.setInt(3, days);
				st.setInt(4, unpaid_leave);
				st.setInt(5, (days * 700) - (unpaid_leave * 450));

				int rows = st.executeUpdate();
				if (rows > 0) {
					System.out.println("Data inserted successfully");
				} else {
					System.out.println("Something went wrong to insert record");
				}

				st.close();
				con.close();
			} else {
				System.err.println("This employee id already present. ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean idChecking(int id) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			PreparedStatement st = con.prepareStatement("select id from employee");
			ResultSet res = st.executeQuery();
			while (res.next()) {
				int value = res.getInt("id");
				if (value == id) {
					return false;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;

	}

	private static void updateRecord() {
		int a = 0;
		try {
			System.out.println("Before update-----------------");
			showAll();

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			System.out.println("Welcome to Update Record");
			// System.out.println("What do you want to update ?");
			// while(a!=3) {
			System.out.println("Press 1 if you want to update Employee Name ");
			System.out.println("Press 2 if you want to update Days Worked ");
			System.out.println("Press 3 if you want to update number of unpaid leave ");
			// System.out.println("Press 3 To Exit.");
			a = s.nextInt();
			switch (a) {
			case 1:
				PreparedStatement st = con.prepareStatement("update employee set name=? where id=?");
				System.out.println("Enter id of employee");
				int id = s.nextInt();
				s.nextLine();
				if (idChecking(id) != true) {
					System.out.println("Enter new name");
					String name = s.nextLine();
					st.setInt(2, id);
					st.setString(1, name);

					int rows = st.executeUpdate();
					if (rows > 0) {
						System.out.println("Data updated successfully");
					} else {
						System.out.println("Something went wrong to insert record");
					}
					System.out.println("After update-----------------");
					showAll();
					st.close();

				} else {
					System.err.println("Invalid id. Please enter correct id.");
				}

				break;

			case 2:
				System.out.println("Enter id of employee");
				id = s.nextInt();
				if (idChecking(id) != true) {
					System.out.println("Enter new daysWorked");
					int daysWorked = s.nextInt();

					PreparedStatement st1 = con.prepareStatement("update employee set daysWorked=? where id=?");
					st1.setInt(2, id);
					st1.setInt(1, daysWorked);

					int rows = st1.executeUpdate();
					PreparedStatement st3 = con.prepareStatement("select unpaid_leave from employee where id =?");
					st3.setInt(1, id);
					ResultSet rs = st3.executeQuery();
					int unpaid_leave = 0;
					if (rs.next()) {
						unpaid_leave = rs.getInt("unpaid_leave");
					}
					PreparedStatement st4 = con.prepareStatement("update employee set salary=? where id=?");
					st4.setInt(2, id);
					st4.setInt(1, ((daysWorked * 700) - (unpaid_leave * 450)));
					st4.executeUpdate();
					if (rows > 0) {
						System.out.println("Data updated successfully");
					} else {
						System.out.println("Something went wrong to insert record");
					}
					System.out.println("After update-----------------");
					// showAll();
					st1.close();
				} else {
					System.err.println("Invalid id. Please enter correct id.");
				}
				break;

			case 3:
				System.out.println("Enter id of employee");
				id = s.nextInt();
				if (idChecking(id) != true) {
					System.out.println("Enter unpaid leave");
					int unpaid_leave = s.nextInt();

					PreparedStatement st2 = con.prepareStatement("update employee set unpaid_leave=? where id=?");
					st2.setInt(2, id);
					st2.setInt(1, unpaid_leave);

					int rows = st2.executeUpdate();
					PreparedStatement st5 = con.prepareStatement("select daysWorked from employee where id =?");
					st5.setInt(1, id);
					ResultSet rs = st5.executeQuery();
					int daysWorked = 0;
					if (rs.next()) {
						daysWorked = rs.getInt("daysWorked");
					}
					PreparedStatement st6 = con.prepareStatement("update employee set salary=? where id=?");
					st6.setInt(2, id);
					st6.setInt(1, ((daysWorked * 700) - (unpaid_leave * 450)));
					st6.executeUpdate();

					if (rows > 0) {
						System.out.println("Data updated successfully");
					} else {
						System.out.println("Something went wrong to insert record");
					}
					System.out.println("After update-----------------");
					showAll();
					st2.close();
				} else {
					System.err.println("Invalid id. Please enter correct id.");
				}
				break;

			default:
				break;
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void showAll() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

			PreparedStatement st = con.prepareStatement("select * from employee");

			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int daysWorked = rs.getInt("daysWorked");
				int unpaid_leave = rs.getInt("unpaid_leave");
				int salary = rs.getInt("salary");
				System.out.println("Id: " + id + " Name: " + name + " Days Worked: " + daysWorked + " Unpaid Leave: "
						+ unpaid_leave + " Salary: " + salary);
			}

			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
