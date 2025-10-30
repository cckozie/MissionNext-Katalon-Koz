import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.security.spec.MGF1ParameterSpec

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
import org.openqa.selenium.JavascriptExecutor;
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
parms = [varProcess_stage, varCross_cultural, varBible_training, varPerspectives, varRelocation, varPaid_volunteer]

//xpath of the Process Stage group 
process_stage = "//input[@id='profile_group-1446521801.898_candidate_process_stages']"

//xpath of the Cross-cultural group
cross_cultural = "//input[@id='profile_group-1446521801.898_cross-cultural_experience']"

//xpath of the Bible_training group
bible_training = "//input[@id='profile_group-1446521801.898_bible_school_training']"

//xpath of the Perspectives group
perspectives = "//input[@id='profile_group-1446521801.898_attended_perspectives?']"

//xpath of the Relocation group
relocation = "//input[@id='profile_group-1446521801.898_relocation_question']"

//xpath of the Paid & Volunteer Positions group
paid_volunteer = "//input[@id='profile_group-1446521801.898_financial_support']"


xpaths = [process_stage, cross_cultural, bible_training, perspectives, relocation, paid_volunteer]

///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Journey partner/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Journey Partner Profile/Tabs/Readiness/')


// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Process Stage') : 'img_Process Stage_field-tooltip',
('Cross-cultural Experience') : 'img_Cross-cultural Experience_field-tooltip',
('Bible Training') : 'img_Bible Training_field-tooltip',
('Attended Perspectives') : 'img_Attended Perspectives_field-tooltip',
('Relocation Option(s)') : 'img_Relocation Option(s)_field-tooltip',
('Paid  Volunteer Positions') : 'img_Paid  Volunteer Positions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Process Stage') : 'Candidates are asked where they are in the exploration process. The first choice, beginning, matches all candidates.',
('Cross-cultural Experience') : 'Select those that would be acceptable for most positions. The first choice will match all candidates.',
('Bible Training') : "Select all options that best fit the typical requirements for your positions. 'Not Applicable' will not filter out profiles based on a candidate's Bible Training.",
('Attended Perspectives') : 'The first selection, Not taken Perspectives, will match all candidates.',
('Relocation Option(s)') : "Check all candidate selections you could consider based on your available positions. If selecting 'Not a match criteria', there's no need to check the others.",
('Paid  Volunteer Positions') : "Select all that apply to your typical assignments. 'No Preference' will not filter out profiles based on a person's financial preference for a position."]

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Process Stage') : 'The candidate process stages field is required.',
('Cross-cultural Experience') : 'The cross-cultural experience field is required.',
('Relocation Options') : 'The relocation question field is required.',
('Paid & Volunteer Positions') : 'The financial support field is required.']


//Go to the Service Options tab
WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Readiness'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], FailureHandling.STOP_ON_FAILURE)

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
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath ,
	('varTooltips') : tooltips, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder,
	('varTooltipTextMap') : tooltipTextMap], FailureHandling.CONTINUE_ON_FAILURE)

// Test for all required field error messages
fieldList = []

requiredFieldMsgs.each {
	fieldList.add(it.key)
}

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms],
	FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Partner Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Partner Profile/Tabs/a_Readiness'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
	GlobalVariable.testCaseErrorFlag = true
}
println(outText)
outFile.append(outText)
