package com.hexaware.MLP203.util;

import java.util.Scanner;

import com.hexaware.MLP203.model.Employee;
import com.hexaware.MLP203.model.Leave;

/**
 * Class CliMain provides the command line interface to the Leave management
 * application.
 */
public class CliMain {
  private Scanner option = new Scanner(System.in, "UTF-8");

  private void mainMenu() {
    System.out.println("Leave Management System");
    System.out.println("-----------------------");
    System.out.println("1. List All Employees Info");
    System.out.println("2. Display Employee Info");
    System.out.println("3. Exit");
    System.out.println("Enter your choice:");
    int menuOption = option.nextInt();
    mainMenuDetails(menuOption);
  }
  private void mainMenuDetails(final int selectedOption) {
    switch (selectedOption) {
      case 1:
        listEmployeesDetails();
        break;
      case 2:
        listEmployeeDetail();
        break;
      case 3:
        // halt since normal exit throws a stacktrace due to jdbc threads not responding
        Runtime.getRuntime().halt(0);
      default:
        System.out.println("Choose either 1, 2 or 3");
    }
    mainMenu();
  }
  public void login()
  {
   System.out.println("Enter user name");
   Scanner sc=new Scanner(System.in);
   int n=sc.nextInt();
   System.out.println("Enter password");
   String str=sc.next();

   if((n==10244)&&(str.equals("dmp@fg56")))
   {
    System.out.println("You are successfully logged in");
    mainMenu();   
   }
    else
    {
      System.out.println("Invalid Username or Password");
    }
  }
  private void listEmployeeDetail() {
    System.out.println("Enter an Employee Id");
    int empId = option.nextInt();
    Employee employee = Employee.listById(empId);
    if (employee == null) {
      System.out.println("Sorry, No such employee");
    } else {
      System.out.println(employee.getEmpId());
    }
  }
  private void listEmployeesDetails() {
    Employee[] employee = Employee.listAll();
    for (Employee e : employee) {
      System.out.println(e.getEmpId());
    }
  }
  /**
   * The main entry point.
   * @param ar the list of arguments
   */
  public static void main(final String[] ar) {
    final CliMain mainObj = new CliMain();

    Employee emp=new Employee();

    //// login method call ////

    mainObj.leaveHistory();
   
  }

  private void leaveHistory() {
    int empId = 0;
    while (true) {
      System.out.println("Enter an Employee Id");
      empId = option.nextInt();
      try {
      // empId = Integer.parseInt(empid);
        Leave.listById(empId);
        Leave[] leaves = Leave.leaveHistory(empId);
        if (leaves.length == 0) {
          System.out.println("No such employee History");
        } else {
          System.out.println();
          for (Leave e : leaves) {
           System.out.println(e.getleave_id() + " | " + e.getleave_from() + " | " + e.getleave_to() + " | "
            + e.getno_ldays() + " | "+ e.getl_type() + " | " + e.getl_status() + " | " + e.getl_reason() + e.getlapply_date() );
        }
      }
      break;
    } catch (NumberFormatException nfe) {
      System.out.println("-------------------------------------------");
      System.out.println("-----please enter correct employee id------");
      System.out.println("-------------------------------------------");
      listEmployeeDetail();
    } catch (NullPointerException npe) {
      System.out.println("--------------No Such Employee---------------");
      System.out.println("---------------------------------------------");
      System.out.println("-----please enter correct employee id--------");
      System.out.println("---------------------------------------------");
      listEmployeeDetail();
    }
  }
}
}
