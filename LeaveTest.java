package com.hexaware.MLP203.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.hexaware.MLP203.persistence.EmployeeDAO;
import com.hexaware.MLP203.persistence.LeaveDAO;

import org.junit.Test;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
// import static org.junit.Assert.assertNull;
// import static org.junit.Assert.assertTrue;

public class LeaveTest {
  
    public void testLeaveGetters() throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date getleave_from = sdf.parse("2020-02-10");
        Date getleave_to = sdf.parse("2020-02-16");
        Date getLeaveApplyDate = sdf.parse("2019-12-25");

        Leave l1 = new Leave(77,50972, getleave_from, getleave_to, 6, getLeaveApplyDate, EnumLeaveStatus.approved,
        "maternity leave", EnumLeaveTypes.earned);
        
        assertEquals(77,l1.getLeaveId());
        assertEquals(50972,l1.getEmployeeId());
        assertEquals(getleave_from, l1.getLeaveFrom());
        assertEquals(getleave_to , l1. getLeaveTo());
        assertEquals(6, l1.getNoLeaveDays());
        assertEquals(getLeaveApplyDate, l1.getLeaveApplyDate());
        assertEquals("earned", l1.getLeaveType());
        assertEquals("maternity leave", l1.getLeaveReason());
        assertEquals("approved", l1.getLeaveStatus());
       

        Leave l2 = new Leave();
        assertFalse(l1.equals(l2));

    }

    @Test
    public void testLeaveSetters() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date setLeaveFrom = sdf.parse("2020-02-10");
        Date setLeaveTo = sdf.parse("2020-02-16");
        Date setLeaveApplyDate = sdf.parse("2019-01-26 15:30:00");
        Leave l = new Leave();
        l.setLeaveId(77);
        l.setEmployeeId(50972);
        l.setLeaveFrom( setLeaveFrom);
        l.setLeaveTo(setLeaveTo);
        l.setNoLeaveDays(6);
        l.setLeaveApplyDate(setLeaveApplyDate);
        l.setLeaveStatus(EnumLeaveStatus.approved);
        l.setLeaveReason("maternity leave");

        l.setLeaveType(EnumLeaveTypes.earned);

    }
    // test for default constructor

    @Test
    public void testLeaveDefault() {
        Leave newleave = new Leave();
        assertEquals("hashcode", newleave.hashCode(), new Leave().hashCode());

    }


  //test for equals
    @Test
    public void testEquals()throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date LeaveApplyDate1 = sdf.parse("2019-12-25 15:30:0");
        Date LeaveApplyDate2 = sdf.parse("2020-01-10 12:10:00");
        Date LeaveApplyDate3 = sdf.parse("2019-12-15 16:15:00");
        Date LeaveApplyDate4 = sdf.parse("2020-01-02 9:45:00");
        Date LeaveFrom1 = sdf.parse("2020-02-10");
        Date LeaveFrom2 = sdf.parse("2020-02-18");
        Date LeaveFrom3= sdf.parse("2020-02-01");
        Date LeaveFrom4= sdf.parse("2020-02-15");
        Date LeaveTo1 = sdf.parse("2020-02-16");
        Date LeaveTo2 = sdf.parse("2020-02-26");
        Date LeaveTo3 = sdf.parse("2020-02-07");
        Date LeaveTo4= sdf.parse("2020-02-18");
        Leave l = new Leave(77,50972,LeaveFrom1,LeaveTo1,6,LeaveApplyDate1, EnumLeaveStatus.approved,
        "maternity leave", EnumLeaveTypes.earned);
        Leave l1 = new Leave(44,50102, LeaveFrom2,LeaveTo2,8,LeaveApplyDate2, EnumLeaveStatus.approved,
        "health problem leave", EnumLeaveTypes.paternity);
        Leave l2 = new Leave(99,50973,LeaveFrom3,LeaveTo3,6,LeaveApplyDate3 , EnumLeaveStatus.pending, "sick leave",
        EnumLeaveTypes.casual);
        Leave l3 = new Leave(55,50103,LeaveFrom4,LeaveTo4,3,LeaveApplyDate4 , EnumLeaveStatus.denied,
        "leave for cousin marriage", EnumLeaveTypes.privileged);
        assertNotEquals(l, l1);
        assertNotEquals(l2, l3);
        assertNotEquals(l1, l3);
    }

    //test for parameterized constructor

    @Test
    public void testLeave1()
    {
        Leave leave = new Leave();
        assertNotEquals(77, leave.getLeaveId());
    }

  /**
   * test hashCode
   * @throws ParseException throws Parse Exception
   */
  @Test
  public final void testHashCode() throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date1 = sdf.parse("2018-11-13");
    Date date2 = sdf.parse("2018-11-15");
    Date date3 = sdf.parse("2018-11-10");
    Leave ld1 = new Leave(1, 1000, sdf.parse("2020-03-10"),
    sdf.parse("2020-03-12"), 3,sdf.parse("2020-02-10"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual);
    Leave ld2 = new Leave(2, 2000, sdf.parse("2020-03-01"),
    sdf.parse("2020-03-06"), 8,sdf.parse("2020-02-20"),EnumLeaveStatus.pending, "Maternity Leave",EnumLeaveTypes.casual);
    Leave ld3 = new Leave(1, 1000, sdf.parse("2020-03-08"),
    sdf.parse("2020-03-12"), 6,sdf.parse("2020-02-01"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual);
    assertNotEquals(ld1.hashCode(), ld2.hashCode());
    assertNotEquals(ld1.hashCode(), ld3.hashCode());
    assertNotEquals(ld2.hashCode(), ld3.hashCode());
  }


  /**
   * Tests that a list with some pending leaves is handled correctly.
   * @param dao mocking the dao class
   * @throws ParseException mocking the dao class
   */
  @Test
  public final void testListPending(@Mocked final LeaveDAO dao) throws ParseException {
    new Expectations() {
      {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final ArrayList<Leave> es1 = new ArrayList<Leave>();
        final ArrayList<Leave> es2 = new ArrayList<Leave>();

        es1.add(new Leave(1, 1000, sdf.parse("2020-03-10"),
        sdf.parse("2020-03-12"), 3,sdf.parse("2020-02-10"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual));

        es1.add(new Leave(2, 2000, sdf.parse("2020-03-01"),
        sdf.parse("2020-03-06"), 8,sdf.parse("2020-02-20"),EnumLeaveStatus.pending, "Maternity Leave",EnumLeaveTypes.casual));

        es1.add(new Leave(1, 1000, sdf.parse("2020-03-08"),
        sdf.parse("2020-03-12"), 6,sdf.parse("2020-02-01"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual));

        es1.add(new Leave(1, 1000, sdf.parse("2020-04-02"),
        sdf.parse("2020-04-10"), 8,sdf.parse("2020-02-24"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual));
        dao.pending(4); result = es1;
        dao.pending(5); result = es2;
      }
    };
    new MockUp<Leave>() {
      @Mock
      LeaveDAO dao() {
        return dao;
      }
    };
    Leave[] l = Leave.listPending(4);
    assertEquals(4, l.length);
    Leave[] l2 = Leave.listPending(5);
    assertEquals(0, l2.length);
  }

  /**
   * Test to check the functionalty of approveDeny.
   * @param dao to mock the dao class
   * @param edao to mock the edao class
   * @throws ParseException mocking the dao class
   * @throws NullPointerException mocking the dao class
   */
  @Test
  public final void testapply_for_leave(@Mocked final EmployeeDAO edao, @Mocked final LeaveDAO dao)
   throws ParseException, NullPointerException {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final Date today = new Date();
    new Expectations() {
      // {
      //   dao.count(1, "2018-12-27", "2018-12-28"); result = 0;
      //   dao.count(1, "2018-12-30", "2018-12-30"); result = 1;
      // }
    };
    new Expectations() {
      {
        edao.find(1); result = new Employee(1, 2, "Rahul", "Kanna", "developer", "rahul157@gmail.com",8907,
                                           10, sdf.parse("2017-11-11"));
        edao.find(2); result = new Employee(2, 3, "Rahul", "Kanna", "testing", "rahul157@gmail.com",8907,
                                           5,sdf.parse("2017-11-11"));
        edao.find(3); result = null;
      }
    }; 
    new Expectations() {
      {
        dao.apply_for_leave(1,2, sdf.parse("2018-12-27"),sdf.parse("2018-12-28"), 2,EnumLeaveStatus.denied,"sick",EnumLeaveTypes.casual,sdf.parse("2018-12-10"));
        dao.apply_for_leave(1,2, sdf.parse("2018-12-27"),sdf.parse("2018-12-28"), 2,EnumLeaveStatus.approved,"maternity",EnumLeaveTypes.casual,sdf.parse("2018-12-20"));
      }
    };
    new MockUp<Employee>() {
      @Mock
      EmployeeDAO dao() {
        return edao;
        }
    };
    new MockUp<Leave>() {
      @Mock
      LeaveDAO dao() {
        return dao;
        }
    };
    String str1 = Leave.apply_for_leave(1,2, "2018-12-27","2018-12-28", 2,"casual","sick");
    assertEquals(str1, "Leave Applied Successfully For 2 Days.");
    String str2 = Leave.apply_for_leave(2,4, "2018-12-27", "2018-12-28", 2, "priviledged",
                                        "sick");
    assertEquals(str2, "Leave Applied Successfully...");
    String str3 = Leave.apply_for_leave(1,4, "2018-12-30", "2018-12-30", 1, "earned",
                                        "sick");
    assertEquals(str3, "already applied on given date");
    // String str1 = LeaveDetails.applyLeave(1, "2018-11-26", "2018-11-28", 3, "SL",
    //                                     "sick");
    // assertEquals(str1, "Start should be greater or equal to current date");
    String str4 = Leave.apply_for_leave(1,6,"2018-12-27", "2018-12-28", 5, "casual",
                                        "sick");
    assertEquals(str4, "NO Of Days Should be right");
    String str5 = Leave.apply_for_leave(1,4,"2018-12-27", "2019-12-28", 112, "earned",
                                       "sick");
    assertEquals(str5, "insufficient leav balance");
    String str6 = Leave.apply_for_leave(3,5,"2018-12-27", "2018-12-28", 3, "casual",
                                        "sick");
    assertEquals(str6, "invalid employee");
    String str7 = Leave.apply_for_leave(1,2,"2018-11-28", "2018-11-30", 3, "priviledged",
                                        "sick");
    assertEquals(str7, "Start should be greater or equal to current date");
    String str8 = Leave.apply_for_leave(1,3, "2018-12-28", "2018-12-26", 3, "casual",
                                        "sick");
    assertEquals(str8, "Start Date Must be Greater than EndDate...");
  }

/**
   * Test to check the functionalty of approveDeny.
   * @param dao to mock the dao class
   * @throws ParseException mocking the dao class
   */
  @Test
  public final void testapproveDeny(@Mocked final LeaveDAO dao) throws ParseException {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    final Leave ld1 = new Leave(1, 1000, sdf.parse("2020-03-10"),
    sdf.parse("2020-03-12"), 3,sdf.parse("2020-02-10"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual);
    final Leave ld2 = new Leave(2, 2000, sdf.parse("2020-03-01"),
    sdf.parse("2020-03-06"), 8,sdf.parse("2020-02-20"),EnumLeaveStatus.denied, "Maternity Leave",EnumLeaveTypes.casual);
    final Leave ld3 = new Leave(1, 1000, sdf.parse("2020-03-08"),
    sdf.parse("2020-03-12"), 6,sdf.parse("2020-02-01"),EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.privileged);
    final Leave ld4 = new Leave(1, 1000, sdf.parse("2020-04-02"),
    sdf.parse("2020-04-10"), 8,sdf.parse("2020-02-24"),EnumLeaveStatus.approved, "Sick Leave",EnumLeaveTypes.earned);

    new Expectations() {
      // {
      //   dao.find(200); result = ld1;
      //   dao.find(300); result = ld2;
      //   dao.find(600); result = ld3;
      //   dao.find(400); result = ld4;
      //   dao.find(500); result = null;
      // }
    };
    new Expectations() {
      {
        dao.showManager(1); result = 1001;
        dao.showManager(300); result = 2002;
        dao.showManager(200); result = 1001;
        dao.showManager(400); result = 3002;
        dao.showManager(600); result = 4002;
      }
    };
    new Expectations() {
      {
        dao.comment("pending", 3);
        dao.comment("approved", 4);
      }
    };
    new MockUp<Leave>() {
      @Mock
      LeaveDAO dao() {
        return dao;
        }
    };
    String str1 = Leave.approveDeny(500, 1001,EnumLeaveStatus.pending);
    assertNotEquals(str1, "Invalid LeaveId");
    String str2 = Leave.approveDeny(300, 2002,EnumLeaveStatus.denied);
    assertNotEquals(str2, "Leave Approved Successfully");
    String str3 = Leave.approveDeny(200, 2002,EnumLeaveStatus.approved);
    assertEquals(str3, "You are not authorised to access this employee.");
    String str4 = Leave.approveDeny(300, 2002,EnumLeaveStatus.pending);
    assertNotEquals(str4, "Leave Rejected");
    String str5 = Leave.approveDeny(400, 3002,EnumLeaveStatus.pending);
    assertNotEquals(str5, "Leave Rejected");
    String str6 = Leave.approveDeny(600, 4002,EnumLeaveStatus.denied);
    assertNotEquals(str6, "Leave Approved Successfully");
    String str7 = Leave.approveDeny(400, 3002,EnumLeaveStatus.approved);
    assertNotEquals(str7, "Already on given status");
  }

 /**
   * tests that empty leave list is handled correctly.
   * @param dao mocking the dao class
   */
  @Test
  public final void testListAllEmpty(@Mocked final LeaveDAO dao) {
    new Expectations() {
      {
        dao.list(); result = new ArrayList<Leave>();
      }
    };
    new MockUp<Leave>() {
      @Mock
      LeaveDAO dao() {
        return dao;
      }
    };
    Leave[] es = Leave.listAll();
    assertEquals(0, es.length);
  } 

  /**
   * test that checks functionality of update leave method.
   * @param dao mocking the dao class
   * @param dao to mock the edao class
   * @throws ParseException mocking the dao class
   * @throws NullPointerException mocking the dao class
   */
  @Test
  public final void testupdateleave(@Mocked final LeaveDAO dao, @Mocked final EmployeeDAO edao) throws ParseException, NullPointerException {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    final Leave ld1 = new Leave(1, 1000, sdf.parse("2020-03-10"),sdf.parse("2020-03-12"), 3,sdf.parse("2020-02-10"),
    EnumLeaveStatus.pending, "Sick Leave",EnumLeaveTypes.casual);
    final Leave ld2 = new Leave(2, 2000, sdf.parse("2020-03-01"),sdf.parse("2020-03-06"), 8,sdf.parse("2020-02-20"),
    EnumLeaveStatus.denied, "Maternity Leave",EnumLeaveTypes.casual);

    new Expectations() {
      {
        dao.update_leave("2018-12-27","2018-12-28", 10,"casual","approved","sick",10);
        dao.update_leave("2018-12-27","2018-12-28", 2,"casual","denied","maternity",12);
      }
    };
    new Expectations() {
      {
        edao.find(1);
         result = new Employee(1, 2, "Rahul", "Kanna", "developer", "rahul157@gmail.com",8907,
                                           10, sdf.parse("2017-11-11"));
        edao.find(2);
         result = new Employee(2, 3, "Rahul", "Kanna", "testing", "rahul157@gmail.com",8907,
                                           5,sdf.parse("2017-11-11"));
        edao.find(3);
         result = null;
      }
    };
    String str1=Leave.update_leave(12,20, "2020-03-10", "2020-03-20", 10, "casual", "sick");
    assertEquals(str1, "leave updated");
    String str2 = Leave.update_leave(1,3, "2018-12-28", "2018-12-26", 3, "casual","sick");
    assertEquals(str2, "leave updated successfully");
  }
}
