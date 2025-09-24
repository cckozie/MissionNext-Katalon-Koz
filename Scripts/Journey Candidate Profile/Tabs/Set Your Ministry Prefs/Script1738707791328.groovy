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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '5jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')


// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varJob_categories, varPreferred_positions]

//xpath of the Job Categories group
job_categories = "//input[@id='profile_group-1623987608.435_job_categories']"

//xpath of the Preferred Positions group 
preferred_positions = "//input[@id='profile_group-1623987608.435_ministry_preferences']"

xpaths = [job_categories, preferred_positions]

// Define path to tooltip text images
//tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = 'Journey Candidate Profile/Tabs/Your Ministry Prefs/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Job Categories') : 'img_Job Categories_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Job Categories') : 'Select at least one that is the most appropriate. These categories are used in job matching.']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Job Categories') : 'The job categories field is required.',
('Preferred Positions') : 'The ministry preferences field is required.']

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

if(GlobalVariable.screenshotOnly) {
	return
}

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.CONTINUE_ON_FAILURE)

url = WebUI.getUrl(FailureHandling.OPTIONAL)

//Log in as Journey Candidate if not on dashboard page
if (!(url) == (('https://education.' + GlobalVariable.domain) + '/profile?requestUri=/dashboard')) {
    WebUI.callTestCase(findTestCase('_Functions/Journey Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Your Ministry Prefs'))

// Test for all required field error messages
fieldList = []

requiredFieldMsgs.each({
		fieldList.add(it.key)
	})

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs],
	FailureHandling.CONTINUE_ON_FAILURE)

if(!GlobalVariable.fastPath) {
	WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)
} else {
	object = 'Journey Candidate Profile/Tabs/Your Ministry Prefs/input_BUSINESS AS MISSION'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

	object = 'Journey Candidate Profile/Tabs/Your Ministry Prefs/input_Business'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

if (varOther_people_group != null) {
	object = 'Object Repository/Journey Candidate Profile/Tabs/Your Ministry Prefs/textarea_Other People Group'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
		, ('varParm1') : varOther_people_group], FailureHandling.STOP_ON_FAILURE)
}

//Take another screenshot of the page with selections
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName + '_selected' + '_' + GlobalVariable.username],
	FailureHandling.STOP_ON_FAILURE)


//WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs//btn_Complete Submit'))

object = 'Object Repository/Journey Candidate Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)



// Test to see if the tab is complete (not colored red, class does not contain 'error')
// The tab may not be found since this is typically the last tab. In that case, test for the Thank You page
WebUI.waitForPageLoad(10)

profile = WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Tabs/a_Contact Info'), FailureHandling.OPTIONAL)

dashboard = WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'), FailureHandling.OPTIONAL)

if (profile || dashboard) {
    outText = (testName + ' was successfully completed.\n')
} else {
    outText = (('Unable to successfully complete ' + testName) + '.\n')
	KeywordUtil.markError(outText)
}

println(outText)

outFile.append(outText)

