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

WebUI.navigateToUrl('https://education.explorenext.org/signup/candidate')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Username_registrationmain_fieldsusername'), 'cktest09ep')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Email_registrationmain_fieldsemail'), 'cktest09@missionnext.org')

WebUI.setEncryptedText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Password_registrationmain_fieldspassword'), 
    'WY2oVvvWQqMllnWN6n70nA==')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_First Name_registrationgroup1first_name'), 'cktest09ep TEST')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Last Name_registrationgroup1last_name'), 'Kosieracki')

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Best Phone Number_registrationgroup1c_caa017'), 
    '952-442-1703')

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Terms and Conditions_registrationgrou_47df81'))

WebUI.doubleClick(findTestObject('Object Repository/temp/Recorded Education Candidate/button_Sign up'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/radio_Female'))

WebUI.setText(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Birth Year_profilegroup-1443546232.86_435965'), 
    '1949')

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/div_Check to Agree with Terms and Condition_15a9a0'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/btn_Complete Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/a_Experience'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/input_I have attended a missions event_prof_989a21'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/btn_Complete Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/a_Education'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Bible_profilegroup-1449793011.346teac_b7479f'))

WebUI.selectOptionByValue(findTestObject('Object Repository/temp/Recorded Education Candidate/select_RudimentaryElementaryIntermediatePro_102d7d'), 
    'Proficient', true)

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/btn_Complete Submit'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/a_Preferences'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/input_Bible Teacher_profilegroup-1449972047_4e64a3'))

WebUI.click(findTestObject('Object Repository/temp/Recorded Education Candidate/btn_Complete Submit'))

