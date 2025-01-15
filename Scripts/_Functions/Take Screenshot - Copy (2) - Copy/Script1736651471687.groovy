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
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor

//Maximize the length of the viewport to include everything down to and including the page footer
//Build the screenshot file name 
//Execute the screenshot and save to local machine
WebUI.openBrowser('https://journey.missionnext.org/signup/candidate')
WebUI.maximizeWindow()

WebDriver driver = DriverFactory.getWebDriver()

path = '/Users/cckozie/Documents/MissionNext/Screenshots/'

footer = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Global/div_Footer'), 1)

// Set the calling parameter values or defaults
if (binding.hasVariable('varExtension')) {
	extension = '_' + varExtension
} else {
	extension = ''
}

WebUI.waitForPageLoad(1)

screen_size = driver.manage().window().getSize();
width = screen_size.getWidth();
height = screen_size.getHeight()
println(height)
yLocation = footer.getLocation()
y = yLocation.getY()
println(y)
zoomFactor = height / y
println(zoomFactor)
zoomFactor = zoomFactor - 0.16
println(zoomFactor)
WebUI.executeJavaScript('document.body.style.zoom=' + zoomFactor, null)
WebUI.delay(2)
zoomFactor = 1.0
WebUI.executeJavaScript('document.body.style.zoom=' + zoomFactor, null)
System.exit(0)
WebUI.setViewPortSize(width, y + 240)

url = WebUI.getUrl()
title = WebUI.getWindowTitle() + extension
/*
file = path  + 'T-' + title + '.png'
println(file)
WebUI.takeScreenshot(file)
*/

println(url)
url = url.substring(8)
qm = url.indexOf('?')
if(qm >= 0) {
	url = url.substring(0,qm)
}
println(url)
myUrl = url.replaceAll('/','_')
println(myUrl)
file = path  + myUrl + '_' + title + '.png'
println(file)
WebUI.takeScreenshot(file)

