package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

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

    @Then("the informations should be same with database")
    public void theInformationsShouldBeSameWithDatabase() {

        //get all information from UI -> we already have it
        //get all data from DB
                //first connect DB
//        DB_Util.createConnection(); we don't need this step since we added it to Hooks.

                //run query
        DB_Util.runQuery("select count(*) from users");
                //get related data
        String expectedCount = DB_Util.getFirstRowFirstColumn(); //this is expected because we didn't add anything to UI
                //close connection
//            DB_Util.destroy(); we have this at hooks. it runs automatically with the tagname
        //make comparison
        Assert.assertEquals(expectedCount,actualUserNumbers);


    }
}
