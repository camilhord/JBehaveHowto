package com.sni;

//Java libraries for URL handling 
import java.net.MalformedURLException;
import java.net.URL;

//Libraries for JBehave
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
 
//Libraries for Selenium
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//Libraries for Sauce Labs
import com.saucelabs.common.SauceOnDemandAuthentication;


public class JBehaveSteps {
	
	// Object used for Selenium Remote web Driver
	private SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication("ScrippsQA1", "2e780720-5e06-4e26-bf60-39960657a049");
	// Object used for Selenium for "driving" the browser
	public WebDriver driver;

	// Annotation to let know Jbehave this method is mapped to the step "Given I am on Google.com"
	@Given("I am on Google.com")
	public void onGooglecom() throws MalformedURLException {
		
		// The following three lines define the object capabilities of Selenium
		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
	    capabilities.setCapability("version", "17");
	    capabilities.setCapability("platform", Platform.XP);
	    // Convert the driver into a remoteWebDriver object, with the parameters of authentication defined previously   
	    driver = new RemoteWebDriver(
	            new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com:80/wd/hub"),
	            capabilities);
	    // Open www.google.com in Sauce Labs. 
		driver.get("http://www.google.com");
	}
	// Annotation to let know Jbehave this method is mapped to the step "When I am searching for Scripps Networks"
	@When("I am searching for $item")
	public void searchingForItem(String item) {
		//Use the Selenium driver to create and object by finding an element in the web page (the Search text box of Google)
		WebElement searchbox = driver.findElement(By.name("q"));
		//Use the object just created, to drive the browser sending text to the text box and sending the Key "Enter" (twice)
		searchbox.sendKeys(item);
		searchbox.sendKeys(Keys.ENTER);
		searchbox.sendKeys(Keys.ENTER);
	}
	// Annotation to let know Jbehave this method is mapped to the step "Then the window title is Scripps Networks"
	@Then("the Window title is $item")
	public void checkWindowTitle(final String item) {
		//Create and object to wait 10 second for the page to show the results
		WebDriverWait wait = new WebDriverWait (driver,10);
		//Wait until the page title is equal to Scripps Networks
		wait.until(ExpectedConditions.titleContains(item));
		//Assert if the title is the one expected. 
		assert (driver.getTitle().contains(item)):"The tittle does not contain" + item;
		//Close the browser
		driver.quit();
	}	
}