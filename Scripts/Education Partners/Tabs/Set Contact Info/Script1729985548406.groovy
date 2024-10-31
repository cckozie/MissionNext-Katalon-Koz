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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as education partner if not on dashboard page
if(!url == 'https://education.' + GlobalVariable.domain + '/profile?requestUri=/dashboard') {
	WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Contact Info'))


WebUI.setText(findTestObject('Education Partner Profile/Tabs/Contact Info/input_Key Contact Phone-R'), '952-442-1703')

WebUI.setText(findTestObject('Education Partner Profile/Tabs/Contact Info/input_Organization Phone-R'), '952-442-1703')

WebUI.setText(findTestObject('Education Partner Profile/Tabs/Contact Info/input_City-R'), '952-442-1703')

WebUI.setText(findTestObject('Education Partner Profile/Tabs/Contact Info/input_ProvinceStateRegion-R'), '952-442-1703')

WebUI.click(findTestObject('Education Partner Profile/Tabs/Contact Info/btn_Complete Submit'))

