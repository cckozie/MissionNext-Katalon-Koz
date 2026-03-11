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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import java.awt.Desktop as Desktop
import org.openqa.selenium.interactions.Actions;

user = 'office'

site = 'Journey'

WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : user , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(30)

WebUI.delay(2)

WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out 2'), [varSite:site], FailureHandling.STOP_ON_FAILURE)

System.exit(0)

siteDesignations = ['Journey' : 2,'Education' : 3,'QuickStart' : 6,'Journey Guide' : 7]

index = WebUI.getWindowIndex(FailureHandling.OPTIONAL)

if(index < 0) {
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
}

WebUI.navigateToUrl('https://missionnext.org/managerlogin/')

WebUI.waitForPageLoad(60)

WebUI.delay(2)

WebUI.setText(findTestObject('Object Repository/Manager/input_Username or Email Address'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Object Repository/Manager/input_Password'), 'xPc6erizqpZS5RHkbVxVdKyWkQxV4SRKassnbXTAWhQ=')

WebUI.click(findTestObject('Object Repository/Manager/input_Log In'))

WebUI.waitForPageLoad(60)

WebUI.delay(2)

WebUI.navigateToUrl("https://missionnext.org/managerlogin/?action=logout&amp")

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/a_log out (you are attempting to)'))

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/a_Logout in Parenthesis'))

WebUI.waitForPageLoad(15)

while(s.exists(imageFile)) {

	WebUI.delay(2)
	
	s.click(imageFile)
}

//WebUI.click(findTestObject('Object Repository/Switch To/a_log out (you are attempting to)'))

WebUI.waitForPageLoad(15)


