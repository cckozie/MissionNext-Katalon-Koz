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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor;

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

WebUI.navigateToUrl('https://education.missionnext.org/signup/candidate')

WebUI.waitForPageLoad(10)

element = driver.findElement(By.xpath('/html/body/div[1]/div/div[2]'))

count = 0

JavascriptExecutor executor = (JavascriptExecutor) driver;
Object elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",element);

attrsStart = elementAttributes.toString()
println('start :' + attrsStart)
while(count < 500) {
	elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",element);
	
	attrs = elementAttributes.toString()
	if(attrs != attrsStart) {
		println(count + ':' + attrs)
		break
	}
	count ++
}
elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",element);

attrs = elementAttributes.toString()
println('start :' + attrsStart)

println(count + ':' + attrs)

//WebUI.waitForElementAttributeValue(findTestObject, null, null, 0).call(findTestObject('Object Repository/temp/Element_Spinner'), 
//    10)

//WebUI.waitForElementAttributeValue(findTestObject('temp/Element_Loader'), 'display', 'none', 10)

