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

//WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://journey.missionnext.org/journey-home/login-here/')

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Login - Journey/i_Wordpress_eicon-close'))

WebUI.setText(findTestObject('Object Repository/Create Profile/Page_Login - Journey/input_Username_log'), 'cktest01@missionnext.org')

WebUI.setText(findTestObject('Object Repository/Create Profile/Page_Log In  Journey  WordPress/input_Password_pwd'), 'cktest01')

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Log In  Journey  WordPress/input_Remember Me_wp-submit'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/a_My Profile'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/a_Your Ministry Prefs'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/input_Bible Teaching_profilegroup-162398760_42c909'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/input_Bible Teaching_profilegroup-162398760_42c909'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/input_Bible Teaching_profilegroup-162398760_42c909'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/input_Bible Teaching_profilegroup-162398760_42c909'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/input_Bible Teaching_profilegroup-162398760_42c909'))

WebUI.click(findTestObject('Object Repository/Create Profile/Page_Journey/input_Bible Teaching_profilegroup-162398760_42c909'))

