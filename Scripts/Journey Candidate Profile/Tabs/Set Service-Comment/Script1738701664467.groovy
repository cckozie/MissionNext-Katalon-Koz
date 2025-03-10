import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
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

if(username[-3..-1] != '5jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

    System.exit(0)
}

// Set output file
testName = 'Journey Candidate Service-Comment Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varPreferred_regions, varLanguages]

//xpath of the Preferred Regions group
preferred_regions = "//input[@id='profile_group-1623987079.732_region_preferences']"

//xpath of the Languages group
languages = "//input[@id='profile_group-1623987079.732_languages']"

xpaths = [preferred_regions, languages]
///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Journey Candidate Profile/Tabs/Service-Comment/')


// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Language(s)') : 'img_Language(s)_field-tooltip',
('Additional Language(s)') : 'img_Additional Language(s)_field-tooltip',
('Vision Trip') : 'img_Vision Trip_field-tooltip',
('Awareness Trip') : 'img_Awareness Trip_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Language(s)') : 'If Other, please specify below.',
('Additional Language(s)') : 'Other language in which you have some level of fluency:',
('Vision Trip') : 'Are you interested in taking a short-term Vision Trip?',
('Awareness Trip') : 'Are you interested in taking a short-term Awareness Trip?']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Preferred Regions') : 'The region preferences field is required.',
('Languages') : 'The languages field is required.']

//Go to the Service-Comment tab
WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Service-Comment'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], FailureHandling.STOP_ON_FAILURE)

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

//Set the checkbox options
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

// Set the dropdowns and text fields
if (varAdditional_languages != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Service-Comment/input_Additional Language(s)'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varAdditional_languages], FailureHandling.STOP_ON_FAILURE)
}

if (varVision_trip != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Service-Comment/select_Vision Trip'
	parm1 = varVision_trip
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : parm1], FailureHandling.STOP_ON_FAILURE)
}

if (varAwareness_trip != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Service-Comment/select_Awareness Trip'
	parm1 = varAwareness_trip
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : parm1], FailureHandling.STOP_ON_FAILURE)
}

if (varComments != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Service-Comment/textarea_Comments'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varComments], FailureHandling.STOP_ON_FAILURE)
}

object = 'Journey Candidate Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
