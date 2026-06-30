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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.testobject.SelectorMethod
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver



WebUI.openBrowser('MissionNext.org')

WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

testObject = 'span_Email'

folderBase = 'MissionWorks Headers and Footers/Footer'

testObjectFolders = ['/', '/Custom1/', '/Custom2/', '/Custom3/']

for(folder in testObjectFolders) {
	
	object = folderBase + folder + testObject
	
	println(object)
/*
	try {
		element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
		println('found in ' + folder)
		exists = true
	} catch (error) {
		println('oops')
	}
*/

	
	// 1. Fetch the object from your Object Repository
	TestObject myObject = findTestObject(object)
	
	// 2. Retrieve its compiled XPath locator
	String objectXpath = myObject.getSelectorCollection().get(SelectorMethod.XPATH)
	
	// 3. Print the output to the Console log
	println("The element XPath is: " + objectXpath)
	
	// By.xpath or By.cssSelector bypassing Katalon's repository tracking
	boolean existsDirect = !driver.findElements(By.xpath(objectXpath)).isEmpty()
	
	if (existsDirect) {
		println('found in ' + folder)
	} else {
		println('oops3!')
	}
	
}