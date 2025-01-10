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

registration = false

if(registration) {
	page = 'Registration'
	url = 'https://journey.missionnext.org/signup/candidate'
} else {
	page = 'Home'
	url = 'https://missionnext.org/'
}

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Element Locations on ' + page + ' Page.txt')

GlobalVariable.outFile = outFile

outFile.write(page + ' Page Element Positions, Names, and Classes \n')

outFile.append('(x,y) > Field name : Class \n')

WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

//elements = driver.findElements(By.xpath(varXpaths[i]))
//elements1 = driver.findElements(By.cssSelector("*"))
//elements = driver.findElements(By.xpath('//*[@class = \'mn-group-choices\']'))
elements = driver.findElements(By.xpath('//*[@id]'))
//driver.findElement(By.xpath("//input[@id
println(elements.size())

for (def element : elements) {
	xy = element.location
	
	println(xy)
	
	myType = element.getAttribute("type")
	
	myClass = element.getAttribute("class")
	println(myClass)
//	if(myClass != null) {
//		System.exit(1)
//	}
	
//	println(myType)
	
	myName = element.getAttribute("name")
	println(myName)
	
	if(myName != null) {
		outFile.append(xy)	
		outFile.append(' > ' + myName)
	} else {
		outFile.append(xy)
	}
	if(myClass != '') {
		outFile.append(' : ' + myClass)
	}
	outFile.append('\n')
	
/*
    myObj = WebUI.convertWebElementToTestObject(element)

    WebUI.scrollToElement(myObj, 2)

    WebUI.delay(1)
*/
}

