package ui.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class HotelAppPage extends Page {

    private List<WebElement> toBeRemoved = new ArrayList<>();

    @FindBy(id = "firstname")
    private WebElement firstnameInput;

    @FindBy(id = "lastname")
    private WebElement lastnameInput;

    @FindBy(id = "totalprice")
    private WebElement totalPriceInput;

    @FindBy(id = "depositpaid")
    private WebElement depositPaidInput;

    @FindBy(id = "checkin")
    private WebElement checkInInput;

    @FindBy(id = "checkout")
    private WebElement checkOutInput;

    @FindBy(css = "[value*='Save']")
    private WebElement saveButton;

    @FindAll({@FindBy(css = "[id='bookings'] > div ")})
    private List<WebElement> bookings;

    public HotelAppPage(WebDriver driver) {
        super(driver);
    }

    public HotelAppPage enterFirstName(String firstName) {
        firstnameInput.sendKeys(firstName);
        return this;
    }

    public HotelAppPage enterSurname(String surname) {
        lastnameInput.sendKeys(surname);
        return this;
    }

    public HotelAppPage enterTotalPrice(String price) {
        totalPriceInput.sendKeys(price);
        return this;
    }

    public HotelAppPage enterDeposit(String deposit) {
        Select selectDeposit = new Select(depositPaidInput);
        selectDeposit.selectByVisibleText(deposit);
        return this;
    }

    public HotelAppPage enterCheckindate(String checkinDate) {
        checkInInput.sendKeys(checkinDate);
        return this;
    }

    public HotelAppPage enterCheckoutdate(String checkoutDate) {
        checkOutInput.sendKeys(checkoutDate);
        return this;
    }

    public HotelAppPage completeForm(Map<String, String> values) {
        LOGGER.info("Adding new reservation");
        enterFirstName(values.get("firstname"));
        enterSurname(values.get("surname"));
        enterTotalPrice(values.get("price"));
        enterDeposit(values.get("deposit"));
        enterCheckindate(values.get("checkin"));
        enterCheckoutdate(values.get("checkout"));
        waitForElementToBeClickable(saveButton);
        saveButton.click();
        return this;
    }

    public boolean isTheNewRowVisible(Map savedValues) {
        long end = System.currentTimeMillis() + getTimeoutTreshold();
        while (System.currentTimeMillis() < end) {
            for (WebElement booking : bookings) {
                try {
                    List<WebElement> rowContents = booking.findElements(By.cssSelector("div"));
                    boolean isPresent = rowContents.stream().filter(p -> p.getText().equals(savedValues.get("surname"))).findAny().isPresent();
                    if(isPresent) {
                        isPresent = rowContents.stream().filter(p -> p.getText().equals(savedValues.get("checkin"))).findFirst().isPresent();
                        if(isPresent) {
                            WebElement deleteButton = booking.findElement(By.cssSelector("[value*='Delete']"));
                            toBeRemoved.add(deleteButton);
                            return true;
                        }
                    }
                } catch (NoSuchElementException | org.openqa.selenium.NoSuchElementException exception) {
                    LOGGER.info("No value was found yet");
                } catch (StaleElementReferenceException exception) {
                    LOGGER.info("Booking has already been removed");
                    return false;
                }
            }
        }
        return false;
    }

    public HotelAppPage removeBooking() {
        if (toBeRemoved.size() > 0) {
            waitForElementToBeClickable(toBeRemoved.get(0));
            toBeRemoved.get(0).click();
            toBeRemoved.remove(0);
        }
        return this;
    }

    public void cleanUpData() {
        if (toBeRemoved.size() > 0) {
            LOGGER.info("Removing booking for:" + toBeRemoved.get(0).getText());
            waitForElementToBeClickable(toBeRemoved.get(0));
            toBeRemoved.get(0).click();
        }
    }
}
