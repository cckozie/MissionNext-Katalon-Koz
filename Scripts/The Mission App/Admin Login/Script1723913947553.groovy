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

WebUI.navigateToUrl('https://www.themissionapp.com/wp-login.php?redirect_to=https%3A%2F%2Fwww.themissionapp.com%2Fwp-admin%2F&reauth=1')

WebUI.setText(findTestObject('Object Repository/The Mission App/input_Username or Email Address_log'), 'chrisskosieracki')

WebUI.setEncryptedText(findTestObject('Object Repository/The Mission App/input_Password_pwd'), '4Q/2PF7UjxevAl0v0kCS+w==')

WebUI.click(findTestObject('Object Repository/The Mission App/input_Remember Me_wp-submit'))

WebUI.mouseOver(findTestObject('Object Repository/The Mission App/a_The Mission App'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/The Mission App/a_Visit Site'))

