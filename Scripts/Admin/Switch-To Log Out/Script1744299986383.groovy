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

imageFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/log_out?.png'

Screen s = new Screen()

WebUI.click(findTestObject('Object Repository/Switch To/a_Switch back to Chris Kosieracki'))

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/a_Logout in Parenthesis'))

WebUI.waitForPageLoad(15)

while(s.exists(imageFile)) {

	WebUI.delay(2)
	
	s.click(imageFile)
}

//WebUI.click(findTestObject('Object Repository/Switch To/a_log out (you are attempting to)'))

WebUI.waitForPageLoad(15)

WebUI.click(findTestObject('Object Repository/Switch To/div_You are Logged in as - (Logout)'))