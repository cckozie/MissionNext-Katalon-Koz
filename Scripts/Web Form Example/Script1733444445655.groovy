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

WebUI.navigateToUrl('https://formsmarts.com/html-form-example')

WebUI.setText(findTestObject('Object Repository/Page_Web Form Example/input_First Name_u_Ck8_4607'), 'Chris')

WebUI.setText(findTestObject('Object Repository/Page_Web Form Example/input_Last Name_u_Ck8_338354'), 'Kosieracki')

WebUI.setText(findTestObject('Object Repository/Page_Web Form Example/input_Email_u_Ck8_4608'), 'cckozie222@gmail.com')

WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Web Form Example/select_Select an optionSales InquirySupport_e21ee1'), 
    'Website Feedback', true)

WebUI.selectOptionByValue(findTestObject('Object Repository/Page_Web Form Example/select_Select an optionSales InquirySupport_e21ee1'), 
    'Sales Inquiry', true)

WebUI.setText(findTestObject('Object Repository/Page_Web Form Example/textarea_Inquiry_u_Ck8_4609'), 'How much is it?')

