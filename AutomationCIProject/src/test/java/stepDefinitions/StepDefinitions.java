package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

	WebDriver driver;

	@Given("I have opened the webite")
	public void i_have_opened_the_webite() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\Programs\\SeleniumDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://login.mailchimp.com/signup/");

		driver.manage().window().maximize();
		
		System.out.println("Before...");
	}

	@Given("I want to enter {string} email adress")
	public void i_want_to_enter_email_adress(String type) {
		WebElement emailTextBox = driver.findElement(By.cssSelector("#email"));
		String emailToEnter = "";

		if (type.equalsIgnoreCase("regular")) {
			emailToEnter = randomizeString(10) + "@email.com";
		}
		if (type.equalsIgnoreCase("empty")) {
			emailToEnter = "";
		}
		// de här if-satserna väljer om vi ska skapa en randomized string för
		// epostadressen eller om fältet ska lämnas tomt.

		emailTextBox.sendKeys(emailToEnter);

		System.out.println(emailToEnter + " entered...");
	}

	@Given("I want to enter a {string} username")
	public void i_want_to_enter_a_username(String type) {
		WebElement usernameTextBox = driver.findElement(By.cssSelector("#new_username"));
		String usernameToEnter = "user" + randomizeString(5);

		if (type.equalsIgnoreCase("regular")) {
			usernameToEnter = "user" + randomizeString(10);
		}
		if (type.equalsIgnoreCase("long")) {
			usernameToEnter = "user" + randomizeString(101);
		}
		if (type.equalsIgnoreCase("exists")) {
			usernameToEnter = "iAlreadyMadeThisUserOnceBefore";
		}
		// likt if-satserna för epost så gör dessa 3 samma sak, randomiserade strings
		// för usernames
		// beroende på vad som står i scenario outline.

		usernameTextBox.sendKeys(usernameToEnter);

		System.out.println("Username entered...");
	}

	@Given("I want to enter a password")
	public void i_want_to_enter_a_password() {
		WebElement passwordTextBox = driver.findElement(By.cssSelector("#new_password"));

		passwordTextBox.sendKeys("Pwd1234!");
		// här skickar vi ett lösenord som uppfyller alla krav som hemsidan har.

		System.out.println("Password entered...");
	}

	@Given("I tick the box")
	public void i_tick_the_box() {
		// vi vill inte ha hemsidans spam så vi bockar i rutan som säger att vi inte vill ha deras utskick.
		waitAndClick(By.cssSelector("#marketing_newsletter"));
		
		System.out.println("Box ticked...");
	}

	@Then("I press the sign up button")
	public void i_press_the_sign_up_button() {
		
		waitAndClick(By.cssSelector("#onetrust-accept-btn-handler")); //accepting cookies
		waitAndClick(By.cssSelector("#create-account")); //signing up

		System.out.println("Sign up clicked...");
	}

	@Then("I check the result {string}")
	public void i_check_the_result(String failMessage) {
		String checkString = "";

		List<WebElement> checktexts = driver.findElements(By.className("invalid-error"));

		for (WebElement webElement : checktexts) {
			if (failMessage.equalsIgnoreCase(webElement.getText())) {
				checkString = webElement.getText();
				System.out.println(checkString);
			}
		}
		// i scenario outline så har vi definerat vilka felmeddelande som vi förväntar
		// oss få för varje scenario,
		// här kontrollerar vi att dem stämmer genom en if-sats som fångar alla
		// felmeddelanden i en lista och ser
		// om något av dem stämmer överrens med vårt förväntade resultat.

		assertEquals(failMessage, checkString);

		// här har vi de olika felmeddelandena.
		// Enter a value less than 100 characters long
		// Another user with this username already exists. Maybe it's your evil twin.
		// Spooky.
		// Please enter a value

	}

	// min funktion som tar emot en int som symboliserar antal tecken att
	// randomisera,
	// och skickar tillbaka den nya randomiserade strängen.
	String randomizeString(int length) {

		Random rnd = new Random(System.currentTimeMillis());
		String strToSendBack = "";

		for (int i = 0; i < length; i++) {
			strToSendBack = strToSendBack + String.valueOf(rnd.nextInt(10));
		}

		return strToSendBack;
	}


	// här tar vi emot sättet att identifiera elementet vi först vill vänta på, och
	// sedan klickar vi på det.
	void waitAndClick(By by) {

		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = driver.findElement(by);

		 JavascriptExecutor Js = (JavascriptExecutor) driver;
		 Js.executeScript("arguments[0].click();", element);

	}

}
