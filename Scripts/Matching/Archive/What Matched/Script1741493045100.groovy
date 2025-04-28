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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython

Screen s = new Screen()

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/'
/*
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://missionnext.org/managerlogin/')

s.click(imagePath + 'Log In.png')

reg = s.find(imagePath + 'Log In.png')

println(reg)

println(reg.getX())

println(reg.getY())

println(reg.getH())

println(reg.getW())

reg.setH(150)

reg.highlight(3)
*/
WebUI.callTestCase(findTestCase('Admin/Switch to Office'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Jobs List Beta/a_Jobs List Beta'))

WebUI.waitForPageLoad(10)

WebUI.delay(5)
/*
Pattern icn = new Pattern(imagePath + 'Add Jobs.png').similar(0.60)

println(icn)

println(icn.getX())


reg = s.find(Pattern(imagePath + 'Add Jobs.png').similar(0.6))

reg.setH(500)

reg.highlight(5)

matchButtons = reg.findAll(Pattern(imagePath + 'Matches.png').similar(0.73))

for(button in matchButtons) {
	button.hightlight(1)
}


*/
