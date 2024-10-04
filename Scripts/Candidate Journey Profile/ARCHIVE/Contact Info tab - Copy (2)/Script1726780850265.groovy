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

// Map of form fields and values
inputFields = [('input_First Name') : 'cktest02Firstname', ('img_First Name_field-tooltip') : ' ', ('input_Last Name') : 'cktest02Lastname'
    , ('img_Last Name_field-tooltip') : ' ', ('radio_Gender_Female') : 'N', ('radio_Gender_Male') : 'Y', ('input_Email') : 'cktest02@missionnext.org'
    , ('img_Email_field-tooltip') : ' ', ('input_Best Phone Number') : '952-442-1703', ('input_City') : 'Waconia', ('select_State') : 'Minnesota'
    , ('img_State_field-tooltip') : ' ', ('input_StateProvinceRegion') : ' ', ('input_PostZip Code') : '55387', ('select_Country') : 'United States'
    , ('select_Country_of_Citizenship') : 'United States', ('img_Country of Citizenship_field-tooltip') : ' ', ('select_Select_Ethnicity') : 'WHITE/CAUCASIAN'
    , ('input_Birth_Year') : '1949', ('img_Birth_Year_field-tooltip') : ' ', ('select_Marital_Status') : 'Widowed', ('checkbox_Terms_and_Conditions') : 'Y'
    , ('img_Terms_and_Conditions_field-tooltip') : ' ', ('btn_Submit') : 'Y']

// Set path to page test objects
testObjectPath = 'Journey Profile/Contact Info tab/'

WebUI.callTestCase(findTestCase('_Functions/Profile Log In to Journey'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Journey Candidate Profile/Journey Dashboard/a_My Profile'))

WebUI.click(findTestObject('Journey Candidate Profile/tab_Contact_Info'))

WebUI.setText(findTestObject('Journey Candidate Profile/Tab-Contact Info/input_First Name'), '')

WebUI.setText(findTestObject('Journey Candidate Profile/Tab-Contact Info/input_Last Name'), '')

WebUI.setText(findTestObject('Journey Candidate Profile/Tab-Contact Info/input_Email'), 'cckozie@gmail.com')

WebUI.click(findTestObject('Journey Candidate Profile/Tab-Contact Info/btn_Submit'))

/*
for (def entry : inputFields) {
    key = entry.key

    println(key)

    testObject = (testObjectPath + key)

    println(testObject)

    value = entry.value

    println(value)

    if (key.substring(0, 5) == 'input') {
        WebUI.setText(findTestObject(testObject), value)
    }
}

WebUI.delay(10)
*/
WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('_sandbox/java email new'), [:], FailureHandling.STOP_ON_FAILURE)

