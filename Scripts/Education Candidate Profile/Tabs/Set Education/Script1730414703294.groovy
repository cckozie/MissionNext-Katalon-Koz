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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '4ec') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

// Set output file
testName = 'Education Candidate Education Tab'

outFile = WebUI.callTestCase(findTestCase('_Functions/Set Output File'), [('varTestName') : testName], FailureHandling.STOP_ON_FAILURE)

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [varPrevious_experience]

//xpath of the Previous Experience group
previous_experience = '//input[@id=\'profile_group-1449793011.346_teaching_experience\']'

xpaths = [previous_experience]
///////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Education Candidate Profile/Tabs/Education/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Credential Authority') : 'img_Credential Authority_field-tooltip',
('Additional Language(s)') : 'img_Additional Language(s)_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Credential Authority') : 'Enter the organization where you received your credentials',
('Additional Language(s)') : 'Other language in which you have some level of fluency:']

// Define the required field messages
requiredFieldMsgs = [
	('Previous Experience') : 'The teaching experience field is required.',
	('English Proficiency') : 'The english skill field is required.']
	

//Go to the Education tab
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Education'))

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

// Set the text boxes and dropdown lists
if (varFormal_degree != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/select_Formal Degree'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varFormal_degree], FailureHandling.STOP_ON_FAILURE)
}

if (varTeaching_credentials != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/select_Teaching Credentials'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varTeaching_credentials], FailureHandling.STOP_ON_FAILURE)
}

WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

if (varCredential_authority != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/textarea_Credential Authority'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varCredential_authority], FailureHandling.STOP_ON_FAILURE)
}

if (varOther_experience != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/textarea_Other Experience'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varOther_experience], FailureHandling.STOP_ON_FAILURE)
}

if (varEnglish_proficiency != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/select_English Proficiency'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varEnglish_proficiency], FailureHandling.STOP_ON_FAILURE)
}

if (varAdditional_languages != null) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/input_Additional Language(s)'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varAdditional_languages], FailureHandling.STOP_ON_FAILURE)

    WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/Education/textarea_Other Experience'))
}

if ((varAdditional_languages != null) && (varProficiency != null)) {
    object = 'Object Repository/Education Candidate Profile/Tabs/Education/select_Additional Language Proficiency'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varProficiency], FailureHandling.STOP_ON_FAILURE)
}

object = 'Education Candidate Profile/Tabs/Experience/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Education Candidate Profile/Tabs/a_Education'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)

