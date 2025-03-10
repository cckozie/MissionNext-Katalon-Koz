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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '4ec') {
    println('The Execution Profile must be set to "Education Candidate"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Candidate Spouse Info Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Education Candidate Profile/Tabs/Spouse Info/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Spouse Birth Year') : 'img_Spouse Birth Year_field-tooltip',
('Spouse Citizenship Country') : 'img_Spouse Citizenship Country_field-tooltip',
('Spouse Serving with You') : 'img_Spouse Serving with You_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Spouse Birth Year') : 'Four Digit Year',
('Spouse Citizenship Country') : 'If you have dual citizenship, select the country where you normally reside.',
('Spouse Serving with You') : "Normally, this will be 'Yes' if you are planning to serve overseas six months or more."]

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Spouse First Name') : 'The spouse first name: field is required.',
('Spouse Birth Year') : 'The spouse birth year field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Terms and Conditions') : 'Terms and Conditions']

//Go to the Spouse Info tab
WebUI.click(findTestObject('Education Candidate Profile/Tabs/a_Spouse Info'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath ,
	('varTooltips') : tooltips, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder,
	('varTooltipTextMap') : tooltipTextMap], FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each {
	fieldList.add(it.key)
}

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)

// Set the input fields provided
if (varSpouse_first_name != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/input_Spouse First Name'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_first_name], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_last_name != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/input_Spouse Last Name'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_last_name], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_birth_year != null) {
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/input_Spouse Birth Year'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varSpouse_birth_year], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_citizenship_country != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/select_Spouse Citizenship Country'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varSpouse_citizenship_country], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_ethnicity != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/select_Spouse Ethnicity'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varSpouse_ethnicity], FailureHandling.STOP_ON_FAILURE)
}

if (varSpouse_serving_with_you == 'Yes') {
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/input_Spouse Serving with You Yes'
} else if (varSpouse_serving_with_you == 'No') {
	object = 'Object Repository/Education Candidate Profile/Tabs/Spouse Info/input_Spouse Serving with You No'
}
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)


object = 'Education Candidate Profile/Tabs/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

