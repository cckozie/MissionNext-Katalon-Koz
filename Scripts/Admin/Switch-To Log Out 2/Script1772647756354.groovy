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
import org.sikuli.script.*
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

site = varSite

imageFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/log_out?.png'

Screen s = new Screen()

WebUI.click(findTestObject('Object Repository/Switch To/a_Switch back to Chris Kosieracki'))

WebUI.waitForPageLoad(15)

WebDriver driver = DriverFactory.getWebDriver()

WebElement adminUserName = driver.findElement(By.xpath("//li[@id='wp-admin-bar-my-account']/a/span"))

WebElement adminLogOut = driver.findElement(By.xpath("//li[@id='wp-admin-bar-logout']/a"))

Actions actions = new Actions(driver);

actions.moveToElement(adminUserName).perform();

Thread.sleep(1000); // Wait for 1 second (use explicit waits in real projects)

actions.moveToElement(adminLogOut).perform();

Thread.sleep(2000); // Wait for 1 second (use explicit waits in real projects)

adminLogOut.click()

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/a_log out (you are attempting to)'))

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/a_Logout in Parenthesis'))

WebUI.waitForPageLoad(15)
/*
while(s.exists(imageFile)) {

	WebUI.delay(2)
	
	s.click(imageFile)
}

WebUI.click(findTestObject('Object Repository/Switch To/a_log out (you are attempting to)'))

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/a_Final Log Out ' + site))
*/