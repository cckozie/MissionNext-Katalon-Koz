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

// THESE ARE THE MINIMUM REQUIRED FIELD ENTRIES
WebUI.click(findTestObject('Journey Candidate Profile/tab_Contact_Info'))

if (GlobalVariable.gender == 'Male') {
	WebUI.click(findTestObject('Journey Candidate Profile/Tab-Contact Info/radio_Gender_Male'))
} else {
	WebUI.click(findTestObject('Journey Candidate Profile/Tab-Contact Info/radio_Gender_Female'))
}

WebUI.selectOptionByValue(findTestObject('Journey Candidate Profile/Tab-Contact Info/select_State'), GlobalVariable.state,
	false)

WebUI.setText(findTestObject('Journey Candidate Profile/Tab-Contact Info/input_Birth_Year'), GlobalVariable.birth_year)

WebUI.click(findTestObject('Journey Candidate Profile/Tab-Contact Info/btn_Submit'))
