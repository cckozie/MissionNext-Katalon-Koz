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
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython

//varUsername = 'mytnschools'

Screen s = new Screen()

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/'

index = WebUI.getWindowIndex(FailureHandling.OPTIONAL)

if(index < 0) {
	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
}

WebUI.navigateToUrl('https://missionnext.org/managerlogin/')

WebUI.waitForPageLoad(20)

WebUI.delay(2)

WebUI.setText(findTestObject('Object Repository/Manager/input_Username or Email Address'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Object Repository/Manager/input_Password'), 'xPc6erizqpZS5RHkbVxVdKyWkQxV4SRKassnbXTAWhQ=')

WebUI.click(findTestObject('Object Repository/Manager/input_Log In'))

WebUI.waitForPageLoad(30)

myURL = WebUI.getUrl()

s.hover(imagePath + 'My Sites.png')

siteImage = imagePath + 'Dashboard ' + varSite

s.hover(siteImage)

s.click(imagePath + 'Dashboard.png')

newURL = WebUI.getUrl()

while(newURL == myURL) {
	
	WebUI.delay(1)
	
	newURL = WebUI.getUrl()
}

//WebUI.waitForPageLoad(20)

WebUI.delay(2)

s.hover(imagePath + 'Users.png')

WebUI.delay(1)

s.click(imagePath + 'All Users.png')

if(varUsername != null && varUsername != '') {
	
	WebUI.delay(1)

	WebUI.setText(findTestObject('Object Repository/Manager/input_Search Users'), varUsername)
	
	WebUI.click(findTestObject('Object Repository/Manager/input_Search Users Submit'))
	
	WebUI.waitForPageLoad(20)
	
	WebUI.delay(1)
	
	s.hover(imagePath + 'User Hover.png')
	
	WebUI.delay(1)
	
	s.click(imagePath + 'switch to.png',5)
	
	
//	if(s.exists(imagePath + 'Yes Switch',3)) {
//		
//		s.click(imagePath + 'Yes Switch')
//	}
}
	
	
	
	
