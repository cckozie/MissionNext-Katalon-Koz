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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

domain = GlobalVariable.domain

if(username[-3..-1] != '2qs') {
    println('The Execution Profile must be set to "Journey Candidate"')

    System.exit(0)
}

// Set output file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')

lastY = -100

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varTime_commitments, varPaid_and_volunteer_positions, varPreferred_regions]

//xpath of the Time Commitments group
time_commitments = "//input[@id='profile_group1_time_commitment']"

//xpath of the Paid and Volunteer Positions group
paid_and_volunteer_positions = "//input[@id='profile_group1_financial_support']"

//xpath of Preferred Regions
preferred_regions = "//input[@id='profile_group1_region_preferences']"

xpaths = [time_commitments,  paid_and_volunteer_positions, preferred_regions]

///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/quickstart/'

// Define the folder where the tooltip test objects live
testObjectFolder =  'QuickStart Candidate Profile/Tabs/Name and Preferences/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('First Name') : 'img_First Name_field-tooltip',
('Last Name') : 'img_Last Name_field-tooltip',
('Your Email') : 'img_Your Email_field-tooltip',
('Age Bracket') : 'img_Age Bracket_field-tooltip',
('Time Commitment(s)') : 'img_Time Commitment(s)_field-tooltip',
('Paid & Volunteer Positions') : 'img_Paid  Volunteer Positions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('First Name') : 'May include your middle initial; enter last name below.',
('Last Name') : 'Family Name',
('Your Email') : 'Your primary email address',
('Age Bracket') : 'Required to help us help you.',
('Time Commitment(s)') : 'Check commitments you would consider.',
('Paid & Volunteer Positions') : 'Check all situations you are willing to consider. (The self-support categories will display the most options.)']

// Define the required field missing error message test objects
requiredFieldMsgs = []

//Go to the Names & Preferences tab
WebUI.click(findTestObject('QuickStart Candidate Profile/Tabs/a_Name and Preferences'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], 
    FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
fieldList = []

requiredFieldMsgs.each({ 
        fieldList.add(it.key)
    })

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Set the dropdown lists
if (varAvailability != null) {
	object = 'Object Repository/QuickStart Candidate Profile/Tabs/Name and Preferences/select_Availability'
	parm1 = varAvailability
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue', 
		('varObject') : object, ('varParm1') : parm1], FailureHandling.STOP_ON_FAILURE)
}

//Set the checkboxes and radio buttons
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

object = ('QuickStart Candidate Profile/Tabs/button_Submit')
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('QuickStart Candidate Profile/Tabs/a_Name and Preferences'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)
