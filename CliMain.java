package com.hexaware.MLP203.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

// import com.hexaware.MLP203.model.*;

import java.util.Scanner;

import com.hexaware.MLP203.model.Employee;
import com.hexaware.MLP203.model.EnumLeaveStatus;
import com.hexaware.MLP203.model.Leave;

/**
 * Class CliMain provides the command line interface to the Leave management
 * application.
 */
public class CliMain {
  private final Scanner option = new Scanner(System.in, "UTF-8");

  /**
   * The main entry point.
   *
   * @param ar the list of arguments
   */
   
  public static void main(final String[] ar) {
    final CliMain mainObj = new CliMain();

    mainObj.login();

  }


// Menu List//

  private void mainMenu() {
    System.out.println("Leave Management System");
    System.out.println("-----------------------");
    System.out.println("1. List All Employees");
    System.out.println("2. List Employee Details");
    System.out.println("3. List Manager Details");
    System.out.println("4. Apply Leave");
    System.out.println("5. Update Leave");
    System.out.println("6. Approve or Deny Leave");
    System.out.println("7: Employee Leave History");
    System.out.println("8. Pending Leaves Status");    
    System.out.println("9. Exit");
    System.out.println("Enter your choice:");
    final int menuOption = option.nextInt();
    mainMenuDetails(menuOption);
  }


//Function calling through Swich Case//

  private void mainMenuDetails(final int selectedOption) {
    switch (selectedOption) {
    case 1:
      listEmployeesDetails();
      break;
    case 2:
      listEmployeeDetail();
      break;
    case 3:
      listManagerDetails();
      break;
    case 4:
      applyLeave();
      break;
    case 5:
      update_Leave();
      break;
    case 6:
      approve_denyLeave();
      break;
    case 7:
      leaveHistory();
      break;
    case 8:
      pending_leavestatus();
      break;
    case 9:
      // halt since normal exit throws a stacktrace due to jdbc threads not responding
      Runtime.getRuntime().halt(0);
    default:

    }
    mainMenu();

  }

// Defining all methods //

  private void applyLeave(){
      int empid = 0;
 
      System.out.println("Enter an Employee Id");
      String empId = option.next();
        empid = Integer.parseInt(empId);
    System.out.println();
    System.out.println("Your available leave balance is: " + Employee.listById(empid).getavailleaves());
    String endDate = null;
    String startDate = null;
    while (true) {
      try {
        System.out.println("Enter Start Date of your leave (yyyy-MM-dd): ");
        startDate = option.next();
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        sdfrmt.parse(startDate);
        System.out.println("Enter End Date of your leave (yyyy-MM-dd): ");
        endDate = option.next();
        SimpleDateFormat sdfrm = new SimpleDateFormat("yyyy-MM-dd");
        sdfrm.setLenient(false);
        sdfrm.parse(endDate);
        break;
      } catch (ParseException ex) {
        System.out.println("------------------------------");
        System.out.println("Please enter in correct date/format.");
        System.out.println("------------------------------");
      }
    }
    System.out.println("Enter the number of days you want leave for: ");
    int noOfdays = option.nextInt();
    System.out.println("Enter the type of leave you want. (casual / privileged / paternity / earned): ");
    String leaveType = option.next();
    System.out.println("Please mention the reason for taking leave: ");
    String leaveReason = option.next();
    String res = null;
    try {
      res = Leave.apply_for_leave(empid, startDate,
                    endDate, noOfdays, leaveType.toLowerCase(),
                    leaveReason);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(res);

  }

  private void update_Leave(){
      int leaveid = 0;
      int empid = 0;

      System.out.println("Enter an employee Id");
      empid = option.nextInt();
      System.out.println("Enter an Leave Id");
      leaveid = option.nextInt();

        System.out.println(Leave.listById(leaveid).getEmployeeId());
    System.out.println();
    System.out.println("Your available leave balance is: " + Employee.listById(empid).getavailleaves());
    String eDate = null;
    String sDate = null;
   
    while (true) {
      try {
        System.out.println("Enter Start Date of your leave (yyyy-MM-dd): ");
        sDate = option.next();
        SimpleDateFormat sdfrmt = new SimpleDateFormat("yyyy-MM-dd");
        sdfrmt.setLenient(false);
        sdfrmt.parse(sDate);
        System.out.println("Enter End Date of your leave (yyyy-MM-dd): ");
        eDate = option.next();
        SimpleDateFormat sdfrm = new SimpleDateFormat("yyyy-MM-dd");
        sdfrm.setLenient(false);
        sdfrm.parse(eDate);
        break;
      } catch (ParseException exp) {
        System.out.println("------------------------------");
        System.out.println("Please enter in correct date/format.");

      }
    }
    System.out.println("Enter the number of days you want leave for: ");
    int ndays = option.nextInt();
    System.out.println("Enter the type of leave you want. (casual / privileged / paternity / earned): ");
    String lType = option.next();
    System.out.println("Please mention the reason for taking leave: ");
    String lReason = option.next();
    String r = null;
    try {
      r = Leave.update_leave(leaveid,empid, sDate,
                    eDate, ndays, lType.toUpperCase(),
                    lReason );
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(r);

  }


  private void approve_denyLeave(){
     Scanner sc = new Scanner(System.in);
    System.out.println("Enter a leave ID:");
    int leaveId = sc.nextInt();
    System.out.println("Enter Employ Id  ");
    int mgrId = sc.nextInt();
    System.out.println("Decide what you want to do (APPROVE / DENY): ");
    String ch = sc.next() ;
    EnumLeaveStatus LeaveStatus = EnumLeaveStatus.valueOf(ch);
    String res = Leave.approveDeny(leaveId, mgrId, LeaveStatus);
    System.out.println(res);
    sc.close();
  }
 
 

   
private void leaveHistory() {
  
  while (true) {
    System.out.println("Enter an Employee Id");
    int empid = option.nextInt();
  
  
    try {
      Leave[] leaves = Leave.leaveHis(empid);
      if (leaves.length == 0) {
        System.out.println("No such employee History");
      } else {
        System.out.println();
        
        for (Leave e : leaves) {
         System.out.println(e.getLeaveId() + " | " + e.getLeaveFrom() + " | " + e.getLeaveTo()
             + " | " + e.getNoLeaveDays() + " | " + e.getLeaveType() + " | " + e.getLeaveStatus()
             + " | " + e.getLeaveReason() +" | "  + e.getLeaveApplyDate());
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

  private void pending_leavestatus(){
      int mgrId;
    System.out.println("Enter Employ Id");
    // mgrId = Integer.parseInt(option.next());
     mgrId = option.nextInt();
    Leave[] pending_leave = Leave.listPending(mgrId);
    for (Leave e : pending_leave) {
      System.out.println(e.getLeaveId() + " | " + e.getEmployeeId() + " | "
          + e.getLeaveFrom() + " | " + e.getLeaveTo() + " | " + e.getNoLeaveDays() + " | "
          + e.getLeaveType() + " | " + e.getLeaveStatus() + " | " + e.getLeaveReason()
          + e.getLeaveApplyDate());
    }

  }

  public void login() {
    System.out.println("Enter user name");
    // Scanner sc=new Scanner(System.in);
    final int n = option.nextInt();
    System.out.println("Enter password");
    final String str = option.next();

    if ((n == 1) && (str.equals("123"))) {
      System.out.println("You are successfully logged in");
      mainMenu();
    } else {
      System.out.println("Invalid Username or Password");
    }
  }

  private void listManagerDetails() {
    while(true){
   try{

    int empId = option.nextInt();
    Employee[] e = Employee.listManagerById(empId);
    if (e != null){
      for(Employee e1: e){
        System.out.println(e1.getEmpId()+" | "+e1.getMgr_id()+" | "+e1.getFname()+" | "+e1.getLname()+" | "+e1.getDept()+" | "+ e1.getContactno()+" | "
      +e1.getMail()+" | "+e1.getContactno()+" | "+e1.getavailleaves()+" | "+e1.getdoj());
        break;
      }
    }
  }
     catch(NumberFormatException nfe)
     {
      System.out.println("Sorry, No such employee");
     } 

     catch(NullPointerException n)
     {
       System.out.println("No Such Employee");
       System.out.println("Please enter correct employee id");
     }

    }
  }

  private void listEmployeeDetail() {
     
    while(true){
      try{
    System.out.println("Enter an Employee Id");
    final int empId = option.nextInt();

    System.out.println(empId);
    final Employee employee = Employee.listById(empId);
    if (employee == null) {
      System.out.println("Sorry, No such employee");
    } else {
      System.out.println(employee.getEmpId() + " | " + employee.getMgr_id() + " | " + employee.getFname() +" | "
          + employee.getLname() + " | "+employee.getDept() +" | "+ employee.getContactno() +" | "+ employee.getMail()
          + " | "+employee.getContactno() +" | " + employee.getavailleaves() + " | " + employee.getdoj());
        break;
    }
  }
    catch(NumberFormatException nfe)
    {
      System.out.println("Enter correct employee id");
    }
    catch(NullPointerException n)
    {
      System.out.println("No Such Employee");
      System.out.println("Please enter correct employee id");
    }
  }
}

  private void listEmployeesDetails() {
    final Employee[] employee = Employee.listAll();
    for (final Employee e : employee) {
      System.out.println(e.getEmpId()+" | "+e.getMgr_id()+" | "+e.getFname()+" | "+e.getLname()+" | "+e.getDept()+" | "+ e.getContactno()+" | "
      +e.getMail()+" | "+e.getContactno()+" | "+e.getavailleaves()+" | "+e.getdoj());
    }
  }


}