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

if (username != 'cktest05jc') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

// Set output file
testName = 'Journey Candidate Experience Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)


// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Journey Candidate Profile/Tabs/Experience/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Life Experience') : 'img_Life Experience_field-tooltip',
('IT Professional') : 'img_IT Professional_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Life Experience') : 'May include your testimony or other experience you wish to share.',
('IT Professional') : 'Check to enable IT Skills and experience to be added.']

// Define the required field missing error message test objects
requiredFieldMsgs = []
	
//Go to the Experience tab
WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Experience'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath ,
	('varTooltips') : tooltips, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder,
	('varTooltipTextMap') : tooltipTextMap], FailureHandling.STOP_ON_FAILURE)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each {
	fieldList.add(it.key)
}

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

//For setup only
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Set the text boxes and dropdown lists
if (varHighest_degree != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Experience/select_Highest Earned Degree'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varHighest_degree], FailureHandling.STOP_ON_FAILURE)
}

if (varDegree_field != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Experience/input_Degree Field'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varDegree_field], FailureHandling.STOP_ON_FAILURE)
}

if (varOccupation != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Experience/textarea_Occupation'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varOccupation], FailureHandling.STOP_ON_FAILURE)
}

if (varCross_cultural != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Experience/select_Cross-Cultural Experience'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varCross_cultural], FailureHandling.STOP_ON_FAILURE)
}

if (varLife_experience != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Experience/textarea_Life Experience'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varLife_experience], FailureHandling.STOP_ON_FAILURE)
}

// Set the IT checkbox
if (varIT_professional == 'Yes') {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Experience/checkbox_IT Professional'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

object = 'Journey Candidate Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)


