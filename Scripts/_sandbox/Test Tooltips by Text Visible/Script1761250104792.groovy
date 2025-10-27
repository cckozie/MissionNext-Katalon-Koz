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
import org.openqa.selenium.interactions.Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor;

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '4ec') {
	println('The Execution Profile must be set to "Education Candidate"')

	System.exit(0)
}

domain = GlobalVariable.domain

WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Dashboard/a_My Profile'))

WebUI.waitForPageLoad(10)

WebDriver driver = DriverFactory.getWebDriver()

tooltips = [
	('First Name') : 'img_First Name_field-tooltip',
	('Last Name') : 'img_Last Name_field-tooltip',
	('Your Email') : 'img_Your Email_field-tooltip',
	('State') : 'img_State_field-tooltip',
	('Your Country of Citizenship') : 'img_Your Country of Citizenship_field-tooltip',
	('Birth Year') : 'img_Birth Year_field-tooltip',
	('Terms and Conditions') : 'img_Terms and Conditions_field-tooltip']
	
tooltipText = [
	('First Name') : 'May include your middle initial; enter last name below.',
	('Last Name') : 'Family Name',
	('Your Email') : 'Your primary email address',
	('State') : 'List is country-specific. Selection is required.',
	('Your Country of Citizenship') : 'If you hold dual citizenship, select the country where you normally reside.',
	('Birth Year') : 'Four Digit Year',
	('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']
	

for(it in tooltips) {
	
	myObject = 'Object Repository/Education Candidate Profile/Tabs/Contact Info/' + it.value
	
	myKey = it.key
	
	myText = tooltipText.get(myKey)

	visible = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)
	
	println(visible)
	
	Actions actions = new Actions(DriverFactory.getWebDriver());
	
	WebElement element = WebUiCommonHelper.findWebElement(findTestObject(myObject), 3);
	
	actions.clickAndHold(element).perform();
	
	visible1 = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)
	
	println(visible1)
	
	WebUI.delay(1)
}

System.exit(0)

element = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Education Candidate Profile/Tabs/Contact Info/img_Last Name_field-tooltip'), 3);
actions.clickAndHold(element).perform();

myText = 'Family Name'

visible1 = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)

println(visible1)
System.exit(0)

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/Contact Info/input_Your Email'))


/*
 //WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/Contact Info/input_Your Email'))
 int x_offset = 100
 int y_offset = 50
 actions.move_by_offset(x_offset, y_offset).perform()
 
 actions.click().perform()
 */
/*
 //WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/Contact Info/input_Your Email'))
 int x_offset = 100
 int y_offset = 50
 actions.move_by_offset(x_offset, y_offset).perform()
 
 actions.click().perform()
 */
 
 JavascriptExecutor js = (JavascriptExecutor) driver;
 
 //driver.execute_script("arguments[0].dispatchEvent(new MouseEvent('mouseup', {bubbles: true}));", element)
 //js.executeScript("arguments[0].dispatchEvent(new MouseEvent('mouseup', {bubbles: true}));", element)
 
 //actions.release().perform();
 //actions.release(element).perform()
 