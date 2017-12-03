package switchWindowFrames;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/* 
* This class explains how to switch focus to different windows.
*/

public class SwitchWindow {

	WebDriver driver;
	String baseURL;
	JavascriptExecutor js;

	@Before
	public void setUp() throws Exception {
		baseURL = "https://letskodeit.teachable.com/p/practice";
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\kajal\\Desktop\\Job\\Downloads\\chromedriver.exe");

		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		driver.get(baseURL);
	}

	@Test
	public void switchWindows() throws InterruptedException {

		// Get parent handle
		String parentHandle = driver.getWindowHandle();

		js.executeScript("window.scrollBy(0,300);");
		Thread.sleep(3000);

		// Click on 'Open Window' button
		WebElement openButton = driver.findElement(By.id("openwindow"));
		openButton.click();

		// Get handles for parent and second window
		Set<String> handles = driver.getWindowHandles();

		// Switch focus to second window and identify control on it
		for (String handle : handles) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
				WebElement searchBox = driver.findElement(By.id("search-courses"));
				searchBox.sendKeys("python");
				Thread.sleep(2000);
				driver.close();
				break;
			}
		}

		// Switch back to parent window and perform action
		driver.switchTo().window(parentHandle);
		driver.findElement(By.id("name")).sendKeys("Test Successful");

	}

	@After
	public void tearDown() throws InterruptedException {
		Thread.sleep(2000);
		driver.quit();
	}
}