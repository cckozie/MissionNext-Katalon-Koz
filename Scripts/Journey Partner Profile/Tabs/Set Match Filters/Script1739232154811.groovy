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

if (username != 'cktest07jp' && username != 'cktest02jp') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

// Set output file
testName = 'Journey Partner Match Filters Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

lastY = -100

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varPaid_volunteer_positions, varProfile_years]

//xpath of the Paid & Volunteer Positions group
paid_volunteer_position = "//input[@id='profile_group-1446521957.744_financial_support']"

//xpath of Profile Years group
profile_years = "//input[@id='profile_group-1446521957.744_profile_years']"

xpaths = [paid_volunteer_position, profile_years]

///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Journey Partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey Partner Profile/Tabs/Match Filters/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Affiliated with a Church') : 'img_Affiliated with a Church_field-tooltip',
('Match Percent Rate') : 'img_Match Percent Rate_field-tooltip',
('Paid  Volunteer Positions') : 'img_Paid  Volunteer Positions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Affiliated with a Church') : "Select 'Yes' for candidates who indicate they are affiliated with a church and could get a church staff reference.",
('Paid & Volunteer Positions') : "Select all that apply to your typical assignments.  'No Preference' will not filter out profiles based on a person's financial preference for a position.",
('Match Percent Rate') : 'Candidates with a match percentage rate below the level selected will be hidden. ']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Paid & Volunteer Positions') : 'The financial support field is required.']

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as Journey Partner if not on dashboard page
if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
	WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Match Filters'))
}

//Go to the Match Filters tab
WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Match Filters'))

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

//Set the checkboxes and radio buttons
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

if (varAffiliated_with_a_church != null) {
	object = 'Object Repository/Journey Partner Profile/Tabs/Match Filters/select_Affiliated with a Church'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varAffiliated_with_a_church], FailureHandling.STOP_ON_FAILURE)
}

if (varMatch_percent_rate != null) {
	object = 'Object Repository/Journey Partner Profile/Tabs/Match Filters/select_Match Percent Rate'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varMatch_percent_rate], FailureHandling.STOP_ON_FAILURE)
}

object = ('Journey Partner Profile/Tabs/btn_Complete Submit')
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
