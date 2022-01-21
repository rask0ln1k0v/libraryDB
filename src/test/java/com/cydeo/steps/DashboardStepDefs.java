package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class DashboardStepDefs
{
    String actualUserNumbers;
    String actualBookNumbers;
    String actualBorrowedBookNumbers;

    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String user) {
        new LoginPage().login(user);
        BrowserUtil.waitFor(4);
    }
    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {
        DashBoardPage dashBoardPage=new DashBoardPage();

        actualUserNumbers = dashBoardPage.usersNumber.getText();
        System.out.println("actualUserNumbers = " + actualUserNumbers);
        actualBookNumbers = dashBoardPage.booksNumber.getText();
        System.out.println("actualBookNumbers = " + actualBookNumbers);
        actualBorrowedBookNumbers = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNumbers = " + actualBorrowedBookNumbers);

    }

    String actualNumber;

    @When("user gets number of  {string}")
    public void userGetsNumberOf(String module) {

         actualNumber = new DashBoardPage().getModuleCount(module);
        System.out.println("actualNumber = " + actualNumber);

    }

}
