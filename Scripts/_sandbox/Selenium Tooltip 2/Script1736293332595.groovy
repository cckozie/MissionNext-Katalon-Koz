import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.errorprone.annotations.Var;

		driverPath = "/Applications/Katalon Studio Free Arm64.app/Contents/Eclipse/configuration/resources/drivers/chromedriver_mac/chromedriver"
		
		// Set the path to the ChromeDriver executable
//		System.setProperty("webdriver.chrome.driver", "chromeDriverPath");
		System.setProperty("webdriver.chrome.driver", driverPath);
		
		// Initialize WebDriver
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		// Navigate to Google.com
		driver.get("https://www.google.com");

		// Find the Google Apps icon element (9-dot grid) and extract its tooltip
		WebElement googleAppsIcon = driver.findElement(By.cssSelector("a[aria-label='Google apps']"));
		
		Thread.sleep(5000);
		println(googleAppsIcon.getText())

		// Get the tooltip text from the "aria-label" attribute
		String tooltipText = googleAppsIcon.getAttribute("aria-label");

		// Print the tooltip text to the console
		System.out.println("Tooltip text: " + tooltipText);

		// Add a short wait to observe the result (optional)
		Thread.sleep(2000);

		
		
		
		
		
		
	/*	
		driver.get("https://www.geeksforgeeks.org");
		driver.manage().window().maximize();
		
		Thread.sleep(5000);

		// Locate the element with the tooltip
		WebElement elementWithTooltip = driver.findElement(By.className("darkMode-wrap"));
		
		shows = elementWithTooltip.isDisplayed()
		println(shows)

		// Use Actions class to hover over the element
		Actions actions = new Actions(driver);
		actions.moveToElement(elementWithTooltip).perform();

		Thread.sleep(5000);

		shows = elementWithTooltip.isDisplayed()
		println(shows)

		// Assuming the tooltip appears in a span element with a specific class
		WebElement tooltipElement = driver.findElement(By.className("darkModeTooltipText"));
		
		

		// Fetch the tooltip text
		String tooltipText = tooltipElement.getText();

		//Printing the tooltipText
		System.out.println(tooltipText);
		
		// Verify the tooltip text
		String expectedTooltipText = "Switch to Dark Mode";
		if (tooltipText.equals(expectedTooltipText)) {
			System.out.println("Tooltip verification passed!");
		} else {
			System.out.println("Tooltip verification failed!");
		}
*/
		// Close the browser
//		driver.quit();
