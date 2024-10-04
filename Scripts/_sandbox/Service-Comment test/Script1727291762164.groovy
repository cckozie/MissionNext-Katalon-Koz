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

preferred_regions = ['Africa, North', 'Africa, Sub-Sahara', 'Asia, Central', 'Asia, Northern', 'Southeast Asia', 'Asia, Southern'
    , 'Caribbean', 'Central America', 'Europe, East', 'Europe, West', 'Middle East', 'North America', 'Oceania', 'South America'
    , 'No Preference']

languages = ['English is a native language', 'English is a second language', 'Spanish', 'French', 'German', 'Japanese', 'Mandarin'
    , 'Portuguese', 'Russian', 'Other']

WebUI.openBrowser('')

WebUI.navigateToUrl('https://journey.explorenext.org/login-here/')

WebUI.click(findTestObject('Object Repository/temp/Service-Comment/body_Skip to contentThe Mission App is a Mi_caf42e'))

WebUI.setText(findTestObject('Object Repository/temp/Service-Comment/input_Username_log'), 'cktest05jc')

WebUI.setEncryptedText(findTestObject('Object Repository/temp/Service-Comment/input_Password_pwd'), 'Wghj4XX0rEWAthMh0R18Tg==')

WebUI.click(findTestObject('Object Repository/temp/Service-Comment/span_Log In'))

WebUI.click(findTestObject('Object Repository/temp/Service-Comment/a_ServiceComment'))

//WebUI.click(findTestObject('Object Repository/temp/Service-Comment/input_Preferred Region(s)_profilegroup-1623_6afa0e'))

for(region in preferred_regions) {
	WebUI.click(findTestObject('Object Repository/temp/Service-Comment/checkbox_Preferred Region(s) - Parm', [('region') : region]))
	WebUI.delay(1)
}

for(region in preferred_regions) {
	WebUI.click(findTestObject('Object Repository/temp/Service-Comment/checkbox_Preferred Region(s) - Parm', [('region') : region]))
	WebUI.delay(1)
}

//WebUI.click(findTestObject('Object Repository/temp/Service-Comment/input_English is a native language_profileg_c5804c'))

