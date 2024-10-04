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

WebUI.navigateToUrl('https://education.explorenext.org/education-home/register/')

WebUI.click(findTestObject('Object Repository/Education Affiliate/a_Apply Now'))

WebUI.switchToWindowTitle('Education')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Username_registrationmain_fieldsusername'), 'cktest08ea')

WebUI.setEncryptedText(findTestObject('Object Repository/Education Affiliate/input_Password_registrationmain_fieldspassword'), 
    'SyglB4jyDQZpoyScDF2qLg==')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Contact First Name_registrationgroup1_fa762a'), 
    'cktest08ea TEST ED AFFILIATE')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Contact Last Name_registrationgroup1l_dae8c4'), 
    'Kosieracki')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Contact Email_registrationgroup1email'), 'cktest08@missionnext.org')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Contact Phone_registrationgroup1key_c_5e3140'), 
    '952-442-1703')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_City_registrationgroup1city'), 'Waconia')

WebUI.selectOptionByValue(findTestObject('Object Repository/Education Affiliate/select_AlabamaAlaskaArizonaArkansasCaliforn_3be247'), 
    'Minnesota', true)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_PostalZip Code_registrationgroup1srvc_326d84'), 
    '55387')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Organization Full Name_registrationgr_b0384c'), 
    'CCK Education Affiliate')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Abbreviation_registrationgroup-146470_26a1ce'), 
    'CCKEA')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Web Address_registrationgroup-1464701_7a30be'), 
    'https://explorenext.org')

WebUI.click(findTestObject('Object Repository/Education Affiliate/input_MissionNext_registrationgroup-1464701_606ca5'))

WebUI.click(findTestObject('Object Repository/Education Affiliate/input_Web Address_registrationgroup-1464701_7a30be'))

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Board of Directors_registrationgroup-_d6bfee'), 
    'https://explorenext.org')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/input_Statement of Faith_registrationgroup-_bf62ab'), 
    'https://explorenext.org')

WebUI.setText(findTestObject('Object Repository/Education Affiliate/textarea_References_registrationgroup-14647_9feed9'), 
    'https://explorenext.org')

WebUI.click(findTestObject('Object Repository/Education Affiliate/input_Partnership Agreement_registrationgro_ccbf5d'))

WebUI.click(findTestObject('Object Repository/Education Affiliate/input_Terms and Conditions_registrationgrou_fd616e'))

WebUI.click(findTestObject('Object Repository/Education Affiliate/button_Sign up'))

