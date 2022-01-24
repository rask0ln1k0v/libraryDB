package com.cydeo.steps;

import com.cydeo.pages.BookPage;
import com.cydeo.pages.DashBoardPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class BooksStepDefs {
    BookPage bookPage=new BookPage();
    List<String> actualCategoryList;





    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        new DashBoardPage().navigateModule(moduleName);
    }


    @When("the user gets all book categories in webpage")
    public void the_user_gets_all_book_categories_in_webpage() {
        actualCategoryList=BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);
        System.out.println("expectedCategoryList = " + actualCategoryList);
    }

    @Then("user should be able to see following categories")
    public void user_should_be_able_to_see_following_categories(List<String> expectedCategoryList) {


        Assert.assertEquals(expectedCategoryList, actualCategoryList);

    }


    @When("I open book {string}")
    public void i_open_book(String bookName) {

        System.out.println("bookName = " + bookName);
        BrowserUtil.waitForClickablility(bookPage.search, 5).sendKeys(bookName);
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();

    }


    @Then("verify book categories must match book categories")
    public void verifyBookCategoriesMustMatchBookCategories() {
        DB_Util.runQuery("select name from book_categories");
        List <String> expectedCategories =  DB_Util.getColumnDataAsList("name");

        Assert.assertEquals(expectedCategories,actualCategoryList);


    }


    @Then("book information must match the database for {string}")
    public void bookInformationMustMatchTheDatabaseFor(String bookname) {

//        List<WebElement> actualWebElementInfo = bookPage.cleanCodeRow;
//        List<String> actualInfos = BrowserUtil.getElementsText(actualWebElementInfo);
//
//        System.out.println("actual" + actualInfos);




//        List <String> expectedInfos = DB_Util.getRowDataAsList(1);
//        Assert.assertEquals(expectedInfos,actualInfos);



        //get UI book info
        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthor = bookPage.author.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        String actualDescription = bookPage.description.getAttribute("value");




        String query = "select name,isbn,year,author,description from books"+" where name like '"+bookname+"'";

        DB_Util.runQuery(query);
        Map<String, String> dbData = DB_Util.getRowMap(1);

        System.out.println("dbData = " + dbData);
        String expectedBookName = dbData.get("name");
        String expectedAuthor = dbData.get("author");
        String expectedISBN = dbData.get("isbn");
        String expectedYear = dbData.get("year");
        String expectedDescription = dbData.get("description");

        Assert.assertEquals(expectedAuthor,actualAuthor);
        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedYear,actualYear);
        Assert.assertEquals(expectedISBN,actualISBN);
        Assert.assertEquals(expectedDescription,actualDescription);

        /* ANOTHER STUDENT'S SOLUTION
         //UI data
        List<String> actualBookDetails = new ArrayList<>();
        actualBookDetails.add(bookPage.bookName.getAttribute("value"));
        actualBookDetails.add(bookPage.isbn.getAttribute("value"));
        actualBookDetails.add(bookPage.year.getAttribute("value"));
        actualBookDetails.add(bookPage.author.getAttribute("value"));
        System.out.println("actualBookDetails = " + actualBookDetails);

        //DB data
        DB_Util.runQuery("select name,isbn,year, author from books where name="+ "\"" +bookName + "\"");
        List<String> expectedBookDetails = DB_Util.getRowDataAsList(1);
        System.out.println("expectedBookDetails = " + expectedBookDetails);

        //Assert
        Assert.assertEquals(expectedBookDetails, actualBookDetails);
         */

    }
}
