import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions

import com.google.errorprone.annotations.Var;

WebUI.openBrowser("https://www.geeksforgeeks.org")

WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

// Locate the element with the tooltip
WebElement elementWithTooltip = driver.findElement(By.className("darkMode-wrap"));

// Use Actions class to hover over the element
Actions actions = new Actions(driver);
actions.moveToElement(elementWithTooltip).perform();

Thread.sleep(3000);

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

