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

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://journey.missionnext.org/journey-home/login-here/')

WebUI.click(findTestObject('Object Repository/temp/i_Wordpress_eicon-close'))

WebUI.setText(findTestObject('Object Repository/temp/input_Username_log'), 'cktest01@missionnext.org')

WebUI.setEncryptedText(findTestObject('Object Repository/temp/input_Password_pwd'), '5lwQhML0qdMWXTXRrgjfVw==')

WebUI.click(findTestObject('Object Repository/temp/button_Log In'))

WebUI.click(findTestObject('Object Repository/temp/a_My Profile'))

WebUI.click(findTestObject('Object Repository/temp/input_Email_profilegroup-1444754265.711email'))

WebUI.click(findTestObject('Object Repository/temp/a_Your Ministry Prefs'))

//WebUI.delay(1)
WebUI.scrollToElement(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/a_Facebook-f'), 10)

//WebUI.delay(1)
WebUI.verifyElementVisible(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/a_X-twitter'))

WebUI.verifyElementPresent(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/i_X-twitter_fab fa-x-twitter'), 10)

WebUI.verifyElementVisible(findTestObject('Misc Saved Objects/Page_Serve in Missions - MissionNext.org/i_X-twitter_fab fa-x-twitter'))

WebUI.delay(10)

