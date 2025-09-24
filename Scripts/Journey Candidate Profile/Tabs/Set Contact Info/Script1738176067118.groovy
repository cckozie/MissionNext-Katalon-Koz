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

if(username[-3..-1] != '5jc') {
    println('The Profile must be set to "Journey Candidate"')

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

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/tabs/contact info/'

// Define the folder where the tooltip test objects live
testObjectFolder = ('Journey Candidate Profile/Tabs/Contact Info/')

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('First Name') : 'img_First Name_field-tooltip',
('Last Name') : 'img_Last Name_field-tooltip',
('Your Email') : 'img_Your Email_field-tooltip',
('State') : 'img_State_field-tooltip',
('Your Country of Citizenship') : 'img_Your Country of Citizenship_field-tooltip',
('Birth Year') : 'img_Birth Year_field-tooltip',
('Terms and Conditions') : 'img_Terms and Conditions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('First Name') : 'May include your middle initial; enter last name below.',
('Last Name') : 'Family Name',
('Your Email') : 'Your primary email address',
('State') : 'List is country-specific. Selection is required.',
('Your Country of Citizenship') : 'If you hold dual citizenship, select the country where you normally reside. ',
('Birth Year') : 'Four Digit Year',
('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Gender') : 'The gender field is required.',
('State') : 'The state field is required.',
('Your Country of Citizenship') : 'The citizenship country field is required.',
('Birth Year') : 'The birth year field is required.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Terms and Conditions') : 'Terms and Conditions']

//Go to the Contact Info tab
WebUI.click(findTestObject('Journey Candidate Profile/Tabs/a_Contact Info'))

//Get the actual tooltip text
tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : testName],
	FailureHandling.STOP_ON_FAILURE)

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

//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Set the input fields provided
if (varGender == 'Male') {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/radio_Male'
} else if (varGender == 'Female') {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/radio_Female'
}
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

if (varState != null) {
		object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/select_State'
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
			('varObject') : object, ('varParm1') : varState], FailureHandling.STOP_ON_FAILURE)
	}
	
if (varCountry != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/select_Country'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varCountry], FailureHandling.STOP_ON_FAILURE)
}

if (varCountry_of_Citizenship != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/select_Country_of_Citizenship'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varCountry_of_Citizenship], FailureHandling.STOP_ON_FAILURE)
}

if (varBirth_year != null) {
    object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/input_Birth Year'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
		('varObject') : object, ('varParm1') : varBirth_year], FailureHandling.STOP_ON_FAILURE)
}

if (varMarital_status != null) {
//	WebUI.selectOptionByValue(findTestObject('Object Repository/Journey Candidate Profile/Tabs/Contact Info/select_Marital Status'), varMarital_status, false)
    object = 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/select_Marital Status'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
		('varObject') : object, ('varParm1') : varMarital_status], FailureHandling.STOP_ON_FAILURE)
}

// Test the external page links
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks'):pageLinks,
	('varObjectPath') : 'Object Repository/Journey Candidate Profile/Tabs/Contact Info/'], FailureHandling.CONTINUE_ON_FAILURE)

object = 'Journey Candidate Profile/Tabs/btn_Complete Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test to see if the tab is complete (not colored red, class does not contain 'error')
WebUI.waitForPageLoad(10)
myClass = WebUI.getAttribute(findTestObject('Journey Candidate Profile/Tabs/a_Contact Info'), 'class', FailureHandling.OPTIONAL)
if(!myClass.contains('error')) {
	outText = testName + ' was successfully completed.\n'
} else {
	outText = 'Unable to successfully complete ' + testName + '.\n'
	KeywordUtil.markError(outText)
}
println(outText)
outFile.append(outText)

