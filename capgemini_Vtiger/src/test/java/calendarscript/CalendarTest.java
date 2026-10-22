package calendarscript;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericutility.BaseClass;
import genericutility.ExcelUtility;
import objrepo.CalendarPage;

public class CalendarTest extends BaseClass {

    @Test(groups = {"Functional", "Positive"})
    public void verifyCreateCallAndInviteUser() throws Exception {

        ExcelUtility eu = new ExcelUtility();
        CalendarPage calendar = new CalendarPage(driver);

        // Step 1: Open Calendar
        calendar.clickCalendarTab();

        // Step 2: Open Add > Call form
        calendar.openCreateCallForm();

        // Step 3: Read event details from Excel
        String eventName =
                eu.getDataFromExcel("Calendar", 1, 0);

        String eventDescription =
                eu.getDataFromExcel("Calendar", 1, 1);

        String eventLocation =
                eu.getDataFromExcel("Calendar", 1, 2);

        // Step 4: Select Event Type
        calendar.selectEventTypeByIndex(0);

        // Step 5: Enter event details
        calendar.enterEventName(eventName);
        calendar.enterDescription(eventDescription);
        calendar.enterLocation(eventLocation);

        // Step 6: Select Planned status
        calendar.selectStatusByIndex(0);

        // Step 7: Select Assigned To
        calendar.selectAssignedToByIndex(3);

        // Step 8: Select Priority
        calendar.selectPriorityByIndex(1);

        // Step 9: Set start and end time.
        // These indexes depend on the dropdown option order.
        calendar.selectStartHourByIndex(9);
        calendar.selectStartMinuteByIndex(5);
        calendar.selectEndHourByIndex(9);
        calendar.selectEndMinuteByIndex(10);

        // Step 10: Select a user dynamically and click Add >>
        String selectedUser =
                calendar.selectUserUsingDownArrowAndAdd();

        // Step 11: Verify the user before saving
        Assert.assertTrue(
                calendar.isUserSelected(selectedUser),
                "User was not added to Selected Users: "
                        + selectedUser
        );

        Reporter.log(
                "Invitee added successfully: " + selectedUser,
                true
        );

        // Step 12: Save the event once
        calendar.clickSave();

        Reporter.log(
                "Save button clicked for event: " + eventName,
                true
        );
    }
    
  
    @Test(groups = {"Functional", "Positive"})
    public void updateOwnerName() {

        CalendarPage calendar = new CalendarPage(driver);

        calendar.clickCalendarTab();
        calendar.clickAllEventsAndTodos();
        calendar.selectMeetingCheckbox("meeting");
        calendar.clickChangeOwner();

        // Select the third owner in the dropdown
        calendar.clickOwnership(2);

        calendar.clickUpdateOwner();

        Reporter.log("Owner updated successfully.", true);
    }
    

    @Test(groups = {"System", "Positive"})
    public void verifyCompleteCalendarEventFlow() throws Exception {

        ExcelUtility eu = new ExcelUtility();
        CalendarPage calendar = new CalendarPage(driver);

        // Step 1: Open Calendar
        calendar.clickCalendarTab();

        // Step 2: Open Add > Call form
        calendar.openCreateCallForm();

        // Step 3: Read event details from Excel
        String eventName =
                eu.getDataFromExcel("Calendar", 1, 0);

        String eventDescription =
                eu.getDataFromExcel("Calendar", 1, 1);

        String eventLocation =
                eu.getDataFromExcel("Calendar", 1, 2);

        // Step 4: Select Event Type
        calendar.selectEventTypeByIndex(0);

        // Step 5: Enter event details
        calendar.enterEventName(eventName);
        calendar.enterDescription(eventDescription);
        calendar.enterLocation(eventLocation);

        // Step 6: Select Planned status
        calendar.selectStatusByIndex(0);

        // Step 7: Select Assigned To
        calendar.selectAssignedToByIndex(3);

        // Step 8: Select Priority
        calendar.selectPriorityByIndex(1);

        // Step 9: Set start and end time.
        // These indexes depend on the dropdown option order.
        calendar.selectStartHourByIndex(9);
        calendar.selectStartMinuteByIndex(5);
        calendar.selectEndHourByIndex(9);
        calendar.selectEndMinuteByIndex(10);

        // Step 10: Select a user dynamically and click Add >>
        String selectedUser =
                calendar.selectUserUsingDownArrowAndAdd();

       calendar.clickAllEventsAndTodos();
       
       Assert.assertTrue(
               calendar.isEventDisplayed(eventName),
               "System test failed: Event was not found: " + eventName
       );

       Reporter.log(
               "SYSTEM TEST PASSED: Event created and displayed: "
                       + eventName,
               true
       );
    }
    


    
}