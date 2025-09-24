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
import groovy.console.ui.SystemOutputInterceptor as SystemOutputInterceptor
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.*
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import javax.swing.*;
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration


// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '2qs') {
	println('The Execution Profile must be set to "Quickstart"')

	System.exit(0)
}

//######################################################################################################
registerOnly = true //Set this flag to true if you do not want to complete the tabs

if(GlobalVariable.testSuiteRunning) {
	registerOnly = true
}
//######################################################################################################

suffix = '-Register Only'
if(!registerOnly) {
	suffix = '-Full Profile'
}

GlobalVariable.screenshotOnly = false

domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://quickstart.' + domain) + '/signup/candidate')

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)


if(GlobalVariable.testSuiteRunning) {
	myTestCase = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
} else {
	myTestCase = testName
}

outFile = new File(GlobalVariable.reportPath + myTestCase + suffix + ' on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing ' + myTestCase + ' on ' + domain) + '.\n')

// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
// !!!!!!!!! LOOK HERE! Input variables (parms) are defaulted to null in Variables tab !!!!!!!!!!!
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
parms = [['BUSINESS AS MISSION', 'ENGINEERING', 'INFORMATION TECHNOLOGY', 'SUPPORT PROFESSIONAL']]

//xpath of the Job Categories group
job_categories = "//input[@id='registration_group-1595728938.496_job_categories']"

xpaths = [job_categories]


if(GlobalVariable.allow_delete) {
	//================================== Delete the user ===============================================
	WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
}

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/quickstart/register/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'QuickStart Candidate Profile/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Username') : 'img_Username_field-tooltip',
('Email') : 'img_Email_field-tooltip',
('Password') : 'img_Password_field-tooltip',
('First Name') : 'img_First Name_field-tooltip',
('Last Name') : 'img_Last Name_field-tooltip',
('Age Bracket') : 'img_Age Bracket_field-tooltip',
('Job Categories') : 'img_Job Categories_field-tooltip',
('Terms and Conditions') : 'img_Terms and Conditions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
    , ('Email') : 'Your primary email address and must be unique in our database.'
	,('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
    , ('First Name') : 'May include your middle initial; enter last name below.', ('Last Name') : 'Family Name', ('How did you learn about us') : 'It is helpful to know how people are learning about us.'
    , ('Age Bracket') : 'Required to help us help you.', ('Job Cagtegories') : 'Select at least one that is the most appropriate. These categories are used in job matching.'
	,('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']

// Define the page's links and the text to search for on the linked page
//ministry positions links to a pdf so the text cannot be examined. ministry positions link is tested by the url
pageLinks = [('Privacy Policy') : 'Privacy Policy', ('Terms and Conditions') : 'Terms and Conditions', ]

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Email') : 'Please enter a valid email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('First Name') : 'The First Name field is required.',
('Last Name') : 'The last name field is required.',
('Zip') : 'The zip field is required.',
('Age Bracket') : 'The age bracket field is required.',
('Country') : 'The country field is required.',
('Job Categories') : 'The job categories field is required.',
('Terms and Conditions') : 'The terms and conditions field is required.']

//================================== Create the Journey partner ==================================
WebUI.openBrowser('')

WebUI.maximizeWindow()

if(GlobalVariable.mobileScreen) {
	
	WebUI.setViewPortSize(600, 1200)
	
	WebUI.delay(1)
}

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Register'], 
    FailureHandling.STOP_ON_FAILURE)
println(tooltipTextMap)

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.CONTINUE_ON_FAILURE)

outText = 'Verifying the links to other pages.\n'

outFile.append(outText)

// Click the other hyperlinks and verify pages opened
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks') : [('ministry positions') : 'Preferred-Position'], ('varObjectPath') : testObjectFolder, ('varTestType') : 'URL'], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks') : pageLinks, ('varObjectPath') : testObjectFolder], FailureHandling.CONTINUE_ON_FAILURE)

// Submit the empty page
object = 'QuickStart Candidate Profile/Register/button_Next'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for username, email, and password required messages
fieldList = ['Username', 'Email', 'Password']

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_Username'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.email], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/button_Next'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)
//System.exit(0)

// Test for first name, last name, and phone number required messages
fieldList = ['First Name', 'Last Name', 'Zip', 'Country', 'Age Bracket', 'Job Categories', 'Terms and Conditions']

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)

//Enter the password, first and last names, country, and phone number, and optional fields except Terms and Conditions
object = 'QuickStart Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.first_name], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.last_name], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_PostZip Code'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.zip], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/select_Age Bracket'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.age_bracket], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.country], FailureHandling.STOP_ON_FAILURE)

object = 'QuickStart Candidate Profile/Register/input_Best Phone Number'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.phone_number], FailureHandling.STOP_ON_FAILURE)

//Set the checkboxes and radio buttons
WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : parms], FailureHandling.STOP_ON_FAILURE)

outText = 'Submitting the finished page.\n'

outFile.append(outText)

//Complete and submit the registration
object = 'QuickStart Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

//WebUI.delay(10)

object = 'Object Repository/QuickStart Candidate Profile/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/QuickStart Candidate Profile/Register/button_Next'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

WebUI.callTestCase(findTestCase('_Functions/QuickStart Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)

found = WebUI.verifyTextPresent('Your QuickStart Profile will allow you to view ministry opportunities', false, FailureHandling.OPTIONAL)

if(found) {
	outText = '+++ Quickstart registration was successful.'
	
	println(outText)
	
	outFile.append(outText + '\n')
	
	if (!(registerOnly)) {

	    pages = WebUI.callTestCase(findTestCase('QuickStart Candidate Profile/Complete QuickStart Candidate Profile'), [('varCalled') : true], 
	        FailureHandling.CONTINUE_ON_FAILURE)
		
		if(pages == null || pages.isEmpty() || pages == 'All') {
			
			myURL = WebUI.getUrl()
	
	        if (myURL.contains('https://journey.missionnext.org/mn-thank-you-page/')) {
				outText = '\n+++ Journey candidate registration successful. Thank you page was found.\n'
				
				outFile.append(outText)
				
				WebUI.callTestCase(findTestCase('_Functions/Journey Candidate Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)
				
				myURL = WebUI.getUrl()
				
				if(myURL.contains('journey.missionnext.org/dashboard')) {
					outText = '+++ Journey candidate login after profile creation was successful.\n'
				} else {
					outText = '--- Journey candidate login after profile creation failed.\n'				
				}
				outFile.append(outText)
								
	        } else {
				outText = '\n--- Journey candidate registration FAILED. Thank you page was NOT found.\n'
				
				outFile.append(outText)
	
			}
		}
	}
	
} else {
	outText = '--- Quickstart registration was not successful.'
	
	println(outText)
	
	outFile.append(outText + '\n')
}

WebUI.closeBrowser()

