package ui.automation;

import com.github.javafaker.Faker;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ui.automation.pages.HotelAppPage;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static ui.automation.BrowserFactory.configureBrowser;
import static ui.automation.utils.HotelSupport.getDateInCorrectFormat;
import static ui.automation.utils.HotelSupport.getRandomInt;

public class Stepdefs {

    private WebDriver driver;
    private HotelAppPage hotelAppPage;
    private Map<String, String> formValues;
    private Faker faker = new Faker();


    public Stepdefs() {
        driver = configureBrowser();
        hotelAppPage = new HotelAppPage(driver);
        formValues = new HashMap<>();
    }

    @Given("I am on the Hotel management app")
    public void i_am_on_the_Hotel_management_app() {
       hotelAppPage.load();
    }

    @When("I add a new reservation for")
    public void i_add_a_new_reservation_for(Map<String, String> values) {
        int daysInTheFuture = getRandomInt();
        formValues.putAll(values);
        formValues.put("checkin", generateBookingDate(daysInTheFuture));
        formValues.put("checkout", generateBookingDate(daysInTheFuture+1));
        hotelAppPage.completeForm(formValues);
    }

    private String generateBookingDate(int daysInTheFututre) {
        return getDateInCorrectFormat(daysInTheFututre);
    }

    @Then("I should see it displayed in the list of reservations")
    public void i_should_see_it_displayed_in_the_list_of_reservations() throws InterruptedException {
        boolean isRowVisible = hotelAppPage.isTheNewRowVisible(formValues);
        assertThat("", isRowVisible, equalTo(true));
    }

    @Given("I can see a reservation that I want to remove")
    public void i_can_see_a_reservation_that_I_want_to_remove() throws InterruptedException {
        formValues = createHotelBooking();
        hotelAppPage.completeForm(formValues);
        boolean isRowVisible = hotelAppPage.isTheNewRowVisible(formValues);
        assertThat("", isRowVisible, equalTo(true));
    }

    private Map<String, String> createHotelBooking() {
        Map<String, String> newUserMap = new HashMap<>();
        newUserMap.put("firstname", faker.firstName());
        newUserMap.put("surname", faker.lastName());
        newUserMap.put("price", "50");
        newUserMap.put("deposit", "true");
        newUserMap.put("checkin", generateBookingDate(20));
        newUserMap.put("checkout", generateBookingDate(30));
        return newUserMap;
    }

    @When("I remove the reservation")
    public void i_remove_said_reservation() {
        hotelAppPage.removeBooking();
    }

    @Then("I should see it disappear from the list")
    public void i_should_see_it_disappear_from_the_list() throws InterruptedException {
        boolean isRowVisible = hotelAppPage.isTheNewRowVisible(formValues);
        assertThat("", isRowVisible, equalTo(false));
    }

    @After
    public void tearDown(Scenario scenario) {
        takeScreenshot(scenario);
        hotelAppPage.cleanUpData();
        hotelAppPage.closeBrowser();
    }

    public void takeScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }
    }
}
