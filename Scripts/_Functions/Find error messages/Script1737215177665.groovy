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
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions as ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities as DesiredCapabilities
import org.openqa.selenium.JavascriptExecutor;

// Set the calling parameter values or defaults
function = 'count'

if (binding.hasVariable('varFunction')) {
	function = varFunction
}

title = WebUI.getWindowTitle()

println('Page title is ' + title)

WebDriver driver = DriverFactory.getWebDriver()

elements = driver.findElements(By.xpath("//div[@class='col-sm-offset-3 col-sm-9 text-danger']"))
 
if(function == 'count') {
	return elements.size()
}

messages = []
for(e in elements) {
	if(e.isDisplayed()) {
		divText = e.getAttribute('outerHTML')
		println(divText)
		chevron1 = divText.indexOf('>')
		chevron2 = divText.indexOf('<', chevron1 + 2)
		message =  divText.substring(chevron1 + 1, chevron2)
		println(message)
		message = message.trim()
		println(message)
		messages.add(message)
	}
}
map = '// Define the required field missing error message test objects\n'
map = map + 'requiredFieldMsgs = [\n'
messages.each {
	println(it)
	tuple = "('') : '" + it + "',\n"
	map = map + tuple
}
map = map.substring(0,map.length()-2) + ']'
println(map)
System.exit(0)
