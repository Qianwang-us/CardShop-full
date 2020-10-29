package com.qianwang.cardshop.selenium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class is used to test browser behavior with Selenium test
 * @author qianwang
 *
 */
@SpringBootTest
class SeleniumTests {
	
	@Autowired
	private WebDriver driver;
	
	/**
	 * This method is to test if home page is launched successfully
	 */
	@Test
	void testHomePage() {
		// Opens the login page for this web application
		driver.get("http://localhost:8080/");
		assertEquals("iCard", driver.getTitle());
	}
	
	/**
	 * This method is to test if user could successfully login with valid username and password
	 */
	@Test
//	@Disabled
	void testLoginInput() {
		// go to login page
		driver.get("http://localhost:8080/login");
		// Locate username field by CSS selector and input "Joan"
		WebElement usernameField = driver.findElement(By.name("username"));
		usernameField.sendKeys("test@test.com");
		// Locate password field by CSS selector and input "joan1234"
		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys("test");
		// Click the submit button
		driver.findElement(By.cssSelector("body > main > form > div > button")).click();
		
		// Home page should be displayed - test welcome message
		WebElement userFirstName = driver.findElement(By.cssSelector("body > header > div.header-top-container > div:nth-child(3) > span"));
		assertEquals(userFirstName.getText(), "Qian");
		
		/* Thread sleep is optional and is to allow visual inspection of final 
		 * state of browser. */
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}