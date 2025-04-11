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

scrollFirst = false

WebUI.openBrowser('')

//WebUI.navigateToUrl('https://journey.missionnext.org/signup/candidate')

WebUI.navigateToUrl('https://missionnext.org')

WebUI.waitForPageLoad(5)

WebUI.click(findTestObject('Scroll Test/img_Email_field-tooltip'))

WebUI.delay(1)

if(scrollFirst) {	
	WebUI.scrollToElement(findTestObject('Scroll Test/img_How did you learn about us_field-tooltip'), 2)
	
	WebUI.delay(1)
}

WebUI.click(findTestObject('Scroll Test/img_How did you learn about us_field-tooltip'))

WebUI.delay(1)

