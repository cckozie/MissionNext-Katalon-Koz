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

WebDriver driver = DriverFactory.getWebDriver()

path = '/Users/cckozie/Documents/MissionNext/Screenshots/'

// Set the calling parameter values or defaults
if (binding.hasVariable('varExtension')) {
	extension = '_' + varExtension
} else {
	extension = ''
}

if (binding.hasVariable('varBottom')) {
	bottomObject = varBottom
} else {
	bottomObject = null
}

WebUI.waitForPageLoad(1)

if(bottomObject != null) {
	initial_size = driver.manage().window().getSize();
	height = initial_size.getHeight();
	width = initial_size.getWidth();
	yLocation = bottomObject.getLocation()
	y = yLocation.getY()
	WebUI.setViewPortSize(width, y + 200)
}

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

