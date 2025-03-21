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

if(username[-3..-1] != '5jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

    System.exit(0)
}

// Set output file
testName = 'Journey Candidate Availability Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

lastY = -100

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varTime_commitments, varTime_available, varPaid_and_volunteer_positions, varLength_of_assignment, varShort_term_availability,
	varShort_term_objective, varTravel_options]

//xpath of the Time Commitments group
time_commitments = "//input[@id='profile_group-1623986423.799_time_commitment']"

//xpath of the Time Available group
time_available = "//input[@id='profile_group-1623986423.799_time_availability']"

//xpath of the Paid and Volunteer Positions group
paid_and_volunteer_positions = "//input[@id='profile_group-1623986423.799_financial_support']"

//xpath of Length of Assignment group
length_of_assignment = "//input[@id='profile_group-1694658591.764_trip_lengths']"

//xpath of Short-term Availability group
short_term_availability = "//input[@id='profile_group-1694658591.764_short-term_availability']"

//xpath of Short-term Objectivde group
short_term_objective = "//input[@name='profile[group-1694658591.764][short-term_objective]']"

//xpath of the Travel Options group
travel_options = "//input[@id='profile_group-1623986423.799_travel_support']"

xpaths = [time_commitments, time_available, paid_and_volunteer_positions, length_of_assignment, short_term_availability,
	short_term_objective, travel_options]

///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey Candidate Profile/Tabs/Availability/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Time Commitment(s)') : 'img_Time Commitment(s)_field-tooltip',
('Paid & Volunteer Positions') : 'img_Paid Volunteer Positions_field-tooltip',
('Interested in Short-Term') : 'img_Interested in Short-Term_field-tooltip',
('Relocation Option(s)') : 'img_Relocation Option(s)_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Time Commitment(s)') : 'Check commitments you would consider.',
('Paid & Volunteer Positions') : 'Check all situations you are willing to consider. (The self-support categories will display the most options.)',
('Interested in Short-Term') : 'If willing to start with short-term exposure or internship check for a few more questions.',
('Relocation Option(s)') : 'This choice indicates to mission agencies your availability and flexibility.']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Time Commitment(s)') : 'The time commitment field is required.',
('Time/Hours Available') : 'The time availability field is required.',
('Paid and Volunteer Positions') : 'The financial support field is required.',
('Travel Options') : 'The travel support field is required.']
//('Relocation Options') : 'The relocation possibilities field is required.'] //Message disappears when Submit button is clicked

//Go to the Availability tab
WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Availability'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], 
    FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
outText = 'Verifying the required field messages.\n'

outFile.append(outText)

fieldList = []

requiredFieldMsgs.each({ 
        fieldList.add(it.key)
    })

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Set the dropdown lists
if (varWhen_available != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Availability/select_When Available'
	parm1 = varWhen_available
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue', 
		('varObject') : object, ('varParm1') : parm1], FailureHandling.STOP_ON_FAILURE)
}

// Set the Interested in Short-Term checkbox
if (varInterested_in_short_term == 'Yes') {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Availability/checkbox_Interested in Short-Term'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	WebUI.delay(1)
}

if (varShort_term_comment != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Availability/textarea_Short-Term Comment'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varShort_term_comment], FailureHandling.STOP_ON_FAILURE)
}

if (varRelocation_options != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Availability/select_Relocation Options'
	parm1 = varRelocation_options
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : parm1], FailureHandling.STOP_ON_FAILURE)
}

//Set the checkboxes and radio buttons
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

object = ('Journey Candidate Profile/Tabs/btn_Complete Submit')
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Candidate Profile/Tabs/a_Availability'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)
