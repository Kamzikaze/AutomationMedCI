package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;
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
		System.setProperty("webdriver.chrome.driver", "D:\\Programs\\SeleniumDriver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://login.mailchimp.com/signup/");

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#onetrust-accept-btn-handler")));
		//vi v�ntar med att forts�tta till vi kan se cookies elementet.

		WebElement acceptCookiesButton = driver.findElement(By.cssSelector("#onetrust-accept-btn-handler"));

		JavascriptExecutor Js = (JavascriptExecutor) driver;
		Js.executeScript("arguments[0].click();", acceptCookiesButton);
		//eftersom att elementet r�r p� sig s� kan ett vanligt "click" ha sv�rt f�r att klicka ordentligt, 
		//d�rf�r k�r vi ett javascript ist�llet som klickar �t oss.

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
		//de h�r if-satserna v�ljer om vi ska skapa en randomized string f�r epostadressen eller om f�ltet ska l�mnas tomt.

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
		//likt if-satserna f�r epost s� g�r dessa 3 samma sak, randomiserade strings f�r usernames 
		//beroende p� vad som st�r i scenario outline.

		usernameTextBox.sendKeys(usernameToEnter);

		System.out.println("Username entered...");
	}

	@Given("I want to enter a password")
	public void i_want_to_enter_a_password() {
		WebElement passwordTextBox = driver.findElement(By.cssSelector("#new_password"));

		passwordTextBox.sendKeys("Pwd1234!");
		//h�r skickar vi ett l�senord som uppfyller alla krav som hemsidan har.
		
		System.out.println("Password entered...");
	}

	@Given("I tick the box")
	public void i_tick_the_box() {
		WebElement tickBox = driver.findElement(By.cssSelector("#marketing_newsletter"));
		tickBox.click();
		//vi vill inte ha hemsidans spam s� vi bockar i rutan som s�ger att vi inte vill ha deras utskick.
		
		System.out.println("Box ticked...");
	}

	@Then("I press the sign up button")
	public void i_press_the_sign_up_button() {
		WebElement signUpButton = driver.findElement(By.cssSelector("#create-account"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", signUpButton);
		//vissa element p� hemsidan d�ljer ibland knappen f�r att signa upp, med JSExecutor 
		//s� kommer klicket alltid hamna p� r�tt element.
		
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
		//i scenario outline s� har vi definerat vilka felmeddelande som vi f�rv�ntar oss f� f�r varje scenario,
		//h�r kontrollerar vi att dem st�mmer genom en if-sats som f�ngar alla felmeddelanden i en lista och ser 
		//om n�got av dem st�mmer �verrens med v�rt f�rv�ntade resultat.

		assertEquals(failMessage, checkString);
		
		//h�r har vi de olika felmeddelandena.
		// Enter a value less than 100 characters long
		// Another user with this username already exists. Maybe it's your evil twin.
		// Spooky.
		// Please enter a value

	}

	//min funktion som tar emot en int som symboliserar antal tecken att randomisera, 
	//och skickar tillbaka den nya randomiserade str�ngen.
	String randomizeString(int length) {

		Random rnd = new Random(System.currentTimeMillis());
		String strToSendBack = "";

		for (int i = 0; i < length; i++) {
			strToSendBack = strToSendBack + String.valueOf(rnd.nextInt(10));
		}

		return strToSendBack;
	}

}
