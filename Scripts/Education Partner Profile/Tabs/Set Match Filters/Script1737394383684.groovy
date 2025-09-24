import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import java.security.spec.MGF1ParameterSpec as MGF1ParameterSpec
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

username = GlobalVariable.username

domain = GlobalVariable.domain

// Ensure that we are using the correct execution profile
if(username[-3..-1] != '6ep') {
	println('The Execution Profile must be set to "Education Partner"')

	System.exit(0)
}

testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = GlobalVariable.outFile

outFile.append('\nTesting ' + testName + ' on ' + domain + '.\n')


// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '6ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

//Check to see if we're writing printed output to a file
domain = GlobalVariable.domain

writeFile = false

// Set output file
testName = 'Education Partner Match Filters Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varDegree, varExperience, varCredentials, varEnglish, varTravel] //, varPaid_volunteer] - moved to Readiness tab

println(parms)

//xpath of the Formal Education Degreee group 
degree = '//input[@id=\'profile_group-1456436956.575_school_formal_education_degree\']'

//xpath of the Experiencee group
experience = '//input[@id=\'profile_group-1456436956.575_school_classroom_experience\']'

//xpath of the Credentials group
credentials = '//input[@id=\'profile_group-1456436956.575_has_teaching_credentials\']'

//xpath of the English group
english = '//input[@id=\'profile_group-1456436956.575_english_skills\']'

//xpath of the Travel group
travel = '//input[@id=\'profile_group-1456436956.575_travel_support\']'

//xpath of the Paid_volunteer group
//paid_volunteer = '//input[@id=\'profile_group-1456436956.575_financial_support\']'

xpaths = [degree, experience, credentials, english, travel] //, paid_volunteer]
///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/tabs/match filters/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Education Partner Profile/Tabs/Match Filters/')


// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Formal Education Degree') : 'img_Formal Education Degree_field-tooltip',
('Classroom Experience') : 'img_Classroom Experience_field-tooltip',
('Formal Teaching Credentials') : 'img_Formal Teaching Credentials_field-tooltip',
('English Proficiency') : 'img_English Proficiency_field-tooltip',
('Affiliated with a Church') : 'img_Affiliated with a Church_field-tooltip']
//('Paid  Volunteer Positions') : 'img_Paid  Volunteer Positions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Formal Education Degree') : 'No will match all candidates',
('Classroom Experience') : 'No will match all candidates.',
('Formal Teaching Credentials') : 'No will match all candidates. ',
('English Proficiency') : 'Select all options that best fit job requirements',
('Affiliated with a Church') : "Select 'Yes' for candidates who indicate they are affiliated with a church and could get a church staff reference. "]
//('Paid & Volunteer Positions') : "Select all that apply to your typical assignments.  'No Preference' will not filter out profiles based on a person's financial preference for a position."]

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Formal Education Degree') : 'The formal education degree field is required.',
('Classroom Experience') : 'The classroom experience field is required.',
('Formal Teaching Credentials') : 'The has teaching credentials field is required.',
('English Proficiency') : 'The english skills field is required.',
('Travel Options') : 'The travel support field is required.']
//('Paid & Volunteer Positions') : 'The financial support field is required.']

//Go to the Service Options tab
WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Match Filters'))

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName], FailureHandling.STOP_ON_FAILURE)

if(tooltipTextMap.size() != tooltipText.size()) {
	outText = '----- There were ' + tooltipText.size() + ' tooltips expected, but ' + tooltipTextMap.size() + ' were found.'
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

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths,
	('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Partner Profile/Tabs/Match Filters/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
// The tab may not be found since this is typically the last tab. In that case, test for the Thank You page
WebUI.waitForPageLoad(10)

profile = WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Tabs/a_Contact Info'), FailureHandling.OPTIONAL)

if (profile) {
	myClass = WebUI.getAttribute(findTestObject('Education Partner Profile/Tabs/a_Match Filters'), 'class', FailureHandling.OPTIONAL)
	if(!myClass.contains('error')) {
		outText = testName + ' was successfully completed.\n'
	} else {
		outText = 'Unable to successfully complete ' + testName + '.\n'
		KeywordUtil.markError(outText)
	}
} else {
	myURL = WebUI.getUrl()
	
	if(myURL.contains('https://education.missionnext.org/dashboard')) {
		outText = (testName + ' was successfully completed.\n')
	} else {
		outText = (('Unable to successfully complete ' + testName) + '.\n')

		KeywordUtil.markError(outText)
	}
}

println(outText)
outFile.append(outText)

