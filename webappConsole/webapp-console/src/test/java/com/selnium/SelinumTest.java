package com.selnium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class SelinumTest {
	
	static WebDriver driver;

	@Test
	public void jenkins() {

		try {

			// Needed a chrome Driver to run the application on the Chrome
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
			System.out.println("Welcome To Jenkins Selinum Test");
			driver.get("http://localhost:8083/pocwebapp/");
			// to hold the screen so that user can see what is happening
			Thread.sleep(5000);
			WebElement usernameBox = driver.findElement(By.name("ssoId"));
			usernameBox.sendKeys("divvyas");
			WebElement passwordBox = driver.findElement(By.name("password"));
			passwordBox.sendKeys("divyam");
			WebElement loginButton = driver.findElement(By.name("login"));
			loginButton.click();
			WebElement userMan = driver.findElement(By.linkText("User Management"));
			Actions action = new Actions(driver);
			action.moveToElement(userMan).build().perform();
			WebElement userButton = driver.findElement(By.linkText("User"));
			System.out.println("Clicked on User ");
			userButton.click();
			Thread.sleep(5000);
			System.out.println(driver.getTitle());
			driver.close();

		}

		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Let the user actually see something!

	}
}
