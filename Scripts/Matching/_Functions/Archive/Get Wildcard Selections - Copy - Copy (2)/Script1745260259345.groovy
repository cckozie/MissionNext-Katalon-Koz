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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor


WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Journey Candidate 05'], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

//WebElement Table = driver.findElement(By.xpath('(.//*[normalize-space(text()) and normalize-space(.)="Start Again"])[1]/following::table[1]'))
//WebElements fields = driver.find_elements(By.XPATH, f'//input[@name="{text}"]'

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Availability'))



JavascriptExecutor executor = (JavascriptExecutor) driver;

/*
//List<WebElement> tabs = driver.findElements(By.xpath("//*[@role='tabpanel']"))
//for(tab in tabs) {
//	if(tab.getAttribute("aria-hidden") == false) {
	if(1 == 1) {
			
		println(tabs.size())
		//System.exit(0)
		
//		List<WebElement> fields = driver.findElements(By.xpath("//*[@class='control-label']"))

*/
List<WebElement> tabs = driver.findElements(By.xpath("//*[@role='tabpanel']"))
println(tabs.size())
//System.exit(0)
for(tab in tabs) {
	elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	tab)
	attrs = elementAttributes.toString()
//	println(attrs)
	hidden = tab.getAttribute('aria-hidden')
	if(hidden == 'false') {
//		println('tab is ' + tab.text)
		List<WebElement> inputFields = tab.findElements(By.tagName('input'))
		println(inputFields.size())

		for (def inputField : inputFields) {
			myName = inputField.getAttribute("name")
			myValue = inputField.getAttribute('value')
			myType = inputField.getAttribute('type')
//			println('myValue is ' + myValue + ' and myType is ' + inputField.getAttribute('type'))
			if(myValue.contains('!')) {
				WebElement label = inputField.findElement(By.xpath("following-sibling::*"))
				myLabel = label.getAttribute('innerHTML')
				println('type is ' + myType + ', label is ' + myLabel + ', wildcard value is ' + myValue + '\n\n')
			}		
		} 

	}
	
}	

	//<input id="profile_group-1623986423.799_time_commitment" type="checkbox" name="profile[group-1623986423.799][time_commitment][]" checked="checked" value="Long Term">

//