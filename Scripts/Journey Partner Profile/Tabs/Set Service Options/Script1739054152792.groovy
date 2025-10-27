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

username = GlobalVariable.username

domain = GlobalVariable.domain

// Ensure that we are using the correct execution profile
if(username[-3..-1] != '7jp') {
	println('The Profile must be set to "Journey Partner"')

	System.exit(0)
}

// Set output file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varTime_commitments, varAvailable_start_options, varTravel_options, varTime_hours_needed, varAwareness_trip,
	varVision_trip, varPreferred_regions, varLanguages]

//xpath of the Time Commitments group
time_commitments = "//input[@id='profile_group-1446521622.636_time_commitments']"

//xpath of the Available Start Options group
available_start_options = "//input[@id='profile_group-1446521622.636_available_start_options']"

//xpath of the Travel Options group
travel_options= "//input[@id='profile_group-1446521622.636_travel_support']"

//xpath of Time/Hours Needed group
time_hours_needed = "//input[@id='profile_group-1446521622.636_time_availability']"

//xpath of Awareness Trip group
awareness_trip = "//input[@name='profile[group-1446521622.636][awareness_trip]']"

//xpath of Vision Trip group
vision_trip = "//input[@name='profile[group-1446521622.636][vision_trip]']"

//xpath of Need_candidates_for_short_term_assignments
need_candidates_for_short_term_assignments = "//input[@id='profile_group-1446521622.636_need_positions_in_short-term_specifically']"

//xpath of the Short-Term Trip Lengths group
short_term_trip_length = "//input[@id='profile_group-1694885436.586_trip_lengths']"

//xpath of the Short-Term Availability group
short_term_availability = "//input[@id='profile_group-1694885436.586_short-term_availability']"

//xpath of the Short-Term Objective group
short_term_objective = "//input[@id='profile_group-1694885436.586_short-term_objective']"

//xpath of the Preferred Region(s) group
preferred_regions = "//input[@id='profile_group-1446521622.636_regions_of_the_world']"

//xpath of the Languages group
languages = "//input[@id='profile_group-1446521622.636_languages']"

xpaths = [time_commitments, available_start_options, travel_options, time_hours_needed, awareness_trip, vision_trip,
	preferred_regions, languages]

///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Journey Partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey Partner Profile/Tabs/Service Options/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Time Commitments') : 'img_Time Commitments_field-tooltip',
('TimeHours Needed') : 'img_TimeHours Needed_field-tooltip',
('Awareness Trip') : 'img_Awareness Trip_field-tooltip',
('Vision Trip') : 'img_Vision Trip_field-tooltip',
//('Vision Trip Description') : 'img_Vision Trip Description_field-tooltip',
//('Short-Term Trip Lengths') : 'img_Short-Term Trip Lengths_field-tooltip',
//('Short-Term Availability') : 'img_Short-Term Availability_field-tooltip',
//('Short-Term Objective') : 'img_Short-Term Objective_field-tooltip',
//('Short-Term Statement') : 'img_Short-Term Statement_field-tooltip',
('Preferred Region(s)') : 'img_Preferred Region(s)_field-tooltip',
('Languages') : 'img_Languages_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Time Commitments') : 'Check all that generally apply. Open will match all candidates.',
('TimeHours Needed') : 'Check all that generally apply for your assignments.',
('Awareness Trip') : 'Does your organization accommodate mission Awareness Trips?',
('Vision Trip') : 'Does your organization offer Vision Trips?',
//('Short-Term Trip Lengths') : 'The Open selection will match all short-term candidates',
//('Short-Term Availability') : 'The Open selection will match all candidates',
//('Short-Term Objective') : 'The candidates make a single choice.',
('Preferred Region(s)') : 'Check all regions where your agency has activity. If there are needs at the home office, select the region where your organization has its headquarters.',
('Languages') : "Check all that are needed and/or helpful. If selecting 'Other,' complete the field below."]

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Time Commitments') : 'The time commitments field is required.',
('Available Start Options') : 'The available start options field is required.',
('Time/Hours Needed') : 'The time availability field is required.',
('Preferred Region(s)') : 'The regions of the world field is required.',
('Languages') : 'The languages field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('countries by region') : 'Countries by Region']

//Go to the Service Options tab
WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Service Options'))

//Must select some options for all fields and tooltips to be displayed
object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/input_Awareness Trip'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click',
	('varObject') : object, ('varParm1') : null], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/input_Vision Trip'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click',
	('varObject') : object, ('varParm1') : null], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/input_Need Candidates for Short-Term Assignments'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click',
	('varObject') : object, ('varParm1') : null], FailureHandling.STOP_ON_FAILURE)


tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], 
    FailureHandling.STOP_ON_FAILURE)

if(tooltipTextMap.size() != tooltipText.size()) {
	outText = '----- There were ' + tooltipText.size() + ' tooltips expected, but ' + tooltipTextMap.size() + ' were found.'
	GlobalVariable.testCaseErrorFlag = true
} else {
	outText = 'There were ' + tooltipTextMap.size() + ' tooltips found as expected.'
}

println(outText)

outFile.append(outText + '\n')

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

//Set the checkboxes and radio buttons
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

// Set the Need Candidates for Short-Term Assignments checkbox
if (varNeed_candidates_for_short_term_assignments == 'Yes') {
	WebDriver driver = DriverFactory.getWebDriver()
    object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/input_Need Candidates for Short-Term Assignments'
	element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)
	myStatus = element.isSelected()
	if((varNeed_candidates_for_short_term_assignments == 'Yes' && !myStatus) || (varNeed_candidates_for_short_term_assignments == 'No' && myStatus)) {
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object, ('varParm1') : null],
			FailureHandling.STOP_ON_FAILURE)
		WebUI.delay(1)
	}
	
	parms = [varShort_term_trip_length, varShort_term_availability, varShort_term_objective]
	xpaths = [short_term_trip_length, short_term_availability, short_term_objective]
	WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)
}

if (varAwareness_trip == 'Yes') {
	object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/textarea_Awareness Trip Description'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varAwareness_trip_description], FailureHandling.STOP_ON_FAILURE)
}

if (varVision_trip == 'Yes') {
	object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/textarea_Vision Trip Description'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varVision_trip_description], FailureHandling.STOP_ON_FAILURE)
}

if (varShort_term_statement != null) {
	object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/textarea_Short-Term Statement'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varShort_term_statement], FailureHandling.STOP_ON_FAILURE)
}

if (varOther_languages != null) {
	object = 'Object Repository/Journey Partner Profile/Tabs/Service Options/input_Other Languages'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varOther_languages], FailureHandling.STOP_ON_FAILURE)
}

// Test the external page links
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks'):pageLinks,
	('varObjectPath') : 'Object Repository/Journey Partner Profile/Tabs/Service Options/'], FailureHandling.CONTINUE_ON_FAILURE)

object = ('Journey Partner Profile/Tabs/btn_Complete Submit')
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Partner Profile/Tabs/a_Service Options'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
	GlobalVariable.testCaseErrorFlag = true
}
println(outText)
outFile.append(outText)
