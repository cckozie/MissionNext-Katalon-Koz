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

WebUI.navigateToUrl('https://education.missionnext.org/education-home/login-here/?doing_wp_cron=1729709761.2382540702819824218750')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/input_Username_log'), 'cktest06ep')

WebUI.setEncryptedText(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/input_Password_pwd'), '54sGs6IdgS9Or2VrKr+d9g==')

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/button_Log In'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/a_My Profile'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/a_Contact Info'))

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/input_Key Contact Phone'), 
    '952-442-1703')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/input_Organization Phone'), 
    '952-442-1703')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/input_City'), 
    'WACONIA')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/input_ProvinceStateRegion'), 
    'MN')

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/btn_Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/a_Positions Needed'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Available Positions administrator'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Experience Preferred administrator'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/btn_Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/a_Service Options'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_One year'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_School Term open'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/btn_Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/a_Readiness'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Process Stage beginning to look'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Process Stage beginning to look'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Bible Training not applicable'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Attended Perspectives I have not'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Relocation Options In North America'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/btn_Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/a_Match Filters'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Formal Education Degree yes'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Classroom Experience yes'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Formal Teaching Credential yes'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_English Proficiency proficient'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Traver Options negotiated'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/checkbox_Paid and Volunteer self-supported need travel'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Partner Tabs/btn_Submit'))

