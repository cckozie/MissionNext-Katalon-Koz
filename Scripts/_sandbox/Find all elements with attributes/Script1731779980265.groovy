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

//page = 'Registration'
//page = 'Home'
//page = 'Goer'
page = 'Blog'


if(page == 'Registration') {
	url = 'https://journey.' + GlobalVariable.domain + '/signup/organization'
} else if(page == 'Home') {
	url = 'https://' + GlobalVariable.domain + '/'
} else if(page == 'Goer'){
	url = 'https://journey.' + GlobalVariable.domain + '/journey-home/im-an-individual/'
} else {
	url = 'https://' + GlobalVariable.domain + '/homepage/blog-vlog'
}

//outFile = new File('/Users/cckozie/Documents/' + GlobalVariable.domain + '/Test Reports/Element Locations and Attributes on ' + page + ' Page.txt')
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Element Locations and Attributes on ' + GlobalVariable.domain + ' ' + page + ' Page.txt')

println(outFile)

GlobalVariable.outFile = outFile

outFile.write(page + ' Page Element Positions with All Attributes \n')

outFile.append('(x,y) [ attributes ] \n')

WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebUI.delay(10)

WebDriver driver = DriverFactory.getWebDriver()

//elements = driver.findElements(By.xpath(varXpaths[i]))
//elements1 = driver.findElements(By.cssSelector("*"))
//elements = driver.findElements(By.xpath('//*[@class = \'mn-group-choices\']'))
elements = driver.findElements(By.xpath('//*[@id]'))

outFile.append(elements.size() + ' elements on the page.\n\n')

for (def element : elements) {
	xy = element.location
	
//	println(xy)
	
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	Object elementAttributes = executor.executeScript("var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;",element);
	
//	println(elementAttributes.toString());
	outFile.append(xy)
	outFile.append(elementAttributes.toString())
	outFile.append('\n\n')
	
}

WebUI.closeBrowser()
