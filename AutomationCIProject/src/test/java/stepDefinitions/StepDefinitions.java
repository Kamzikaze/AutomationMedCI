package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

	WebDriver driver;

	@Given("I have opened the webite")
	public void i_have_opened_the_webite() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\Programs\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://login.mailchimp.com/signup/");

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#onetrust-accept-btn-handler")));

		WebElement acceptCookiesButton = driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]"));

		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].click();", acceptCookiesButton);

		System.out.println("Before...");
	}

	@Given("I want to enter {string} email adress")
	public void i_want_to_enter_email_adress(String type) {
		WebElement emailTextBox = driver.findElement(By.xpath("//*[@id=\"email\"]"));
		String emailToEnter = "";
		
		if(type.equalsIgnoreCase("regular"))
		{
			emailToEnter = randomizeString(10) + "@email.com";
		}
		if(type.equalsIgnoreCase("empty"))
		{
			emailToEnter = "";
		}

		emailTextBox.sendKeys(emailToEnter);

		System.out.println(emailToEnter + " entered...");
	}

	@Given("I want to enter a {string} username")
	public void i_want_to_enter_a_username(String type) {
		WebElement usernameTextBox = driver.findElement(By.xpath("//*[@id=\"new_username\"]"));
		String usernameToEnter = "user" + randomizeString(5);

		
		if(type.equalsIgnoreCase("regular"))
		{
			usernameToEnter = "user" + randomizeString(10);
		}
		if(type.equalsIgnoreCase("long"))
		{
			usernameToEnter = "user" + randomizeString(101);
		}
		if(type.equalsIgnoreCase("exists"))
		{
			usernameToEnter = "iAlreadyMadeThisUserOnceBefore";
		}
		
		usernameTextBox.sendKeys(usernameToEnter);
		

		System.out.println("Username entered...");
	}


	@Given("I want to enter a password")
	public void i_want_to_enter_a_password() {
		WebElement passwordTextBox = driver.findElement(By.xpath("//*[@id=\"new_password\"]"));

		passwordTextBox.sendKeys("Pwd1234!");

		System.out.println("Password entered...");
	}

	@Given("I tick the box")
	public void i_tick_the_box() {
		WebElement tickBox = driver.findElement(By.xpath("//*[@id=\"marketing_newsletter\"]"));
		tickBox.click();

		System.out.println("Box ticked...");
	}

	@Then("I press the sign up button")
	public void i_press_the_sign_up_button() {
		WebElement signUpButton = driver.findElement(By.xpath("//*[@id=\"create-account\"]"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", signUpButton);

		System.out.println("Sign up clicked...");
	}

	@Then("I check the result")
	public void i_check_the_result() {
		WebElement checkText = driver.findElement(By.xpath("//*[@id=\"signup-content\"]/div/div/div/h1"));
		
		assertEquals("Check your email", checkText.getText());
		
		//Enter a value less than 100 characters long
		//Another user with this username already exists. Maybe it's your evil twin. Spooky.
		//Please enter a value
		
	}

	String randomizeString(int length) {

		Random rnd = new Random(System.currentTimeMillis());
		String strToSendBack = "";

		for (int i = 0; i < length; i++) {
			strToSendBack = strToSendBack + String.valueOf(rnd.nextInt(10));
		}

		return strToSendBack;
	}

}
