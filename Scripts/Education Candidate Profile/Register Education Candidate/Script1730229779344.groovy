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


// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Candidate"')

    System.exit(0)
}

//######################################################################################################
registerOnly = false //Set this flag to true if you do not want to complete the tabs

//######################################################################################################
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//	Need to add tests for the tooltips on all of the tabs. 
//  Consider using a called script to test all tooltips.
//  Write all failures to the output file
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://education.' + domain) + '/signup/candidate')

// Write results to text file
outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Candidate on ' + domain) + 
'.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Education Candidate on ' + domain) + '.\n')

//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education candidate/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'Education Candidate Profile/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [('dummy') : 'dummy', ('Username') : 'img_Username_field-tooltip', ('Email') : 'img_Email_field-tooltip', ('Password') : 'img_Password_field-tooltip'
    , ('First Name') : 'img_First Name_field-tooltip', ('Last Name') : 'img_Last Name_field-tooltip', ('Learn About Us') : 'img_Learn About Us_field-tooltip'
    , ('Terms and Conditions') : 'img_Terms and Conditions_field-tooltip']

// Define the expected tooltip texts
tooltipText = [('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
    , ('Email') : 'Your primary email address and must be unique in our database.', ('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
    , ('First Name') : 'May include your middle initial; enter last name below.', ('Last Name') : 'Family Name', ('How did you learn about us') : 'It is helpful to know how people are learning about us.'
    , ('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Privacy Policy') : 'Privacy Policy', ('Terms and Conditions') : 'Terms and Conditions']

// Define the required field missing error message test objects
requiredFieldMsgs = [('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
    , ('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
    , ('Email') : 'Please enter a valid email address.', ('First Name') : 'The First Name field is required.', ('Last Name') : 'The last name field is required.'
    , ('Country') : 'The country field is required.', ('Phone Number') : 'The phone number field is required.', ('Terms and Conditions') : 'The terms and conditions field is required.']

//================================== Create the education partner ==================================
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Register'], 
    FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
        , ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap], 
    FailureHandling.STOP_ON_FAILURE)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Submit the empty page
object = 'Education Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

outText = 'Verifying the required field messages.\n'

outFile.append(outText)

// Test for username, email, and password required messages
fieldList = ['Username', 'Email', 'Password']

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/input_Username'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/input_Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.email], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for first name, last name, and phone number required messages
fieldList = ['First Name', 'Last Name', 'Country', 'Phone Number', 'Terms and Conditions']

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

//Enter the password, first and last names, country, and phone number, and optional fields except Terms and Conditions
object = 'Education Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/input_First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/input_Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.last_name], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.country], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/input_Best Phone Number'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.phone_number], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.prefer_text) {
	object = 'Education Candidate Profile/Register/checkbox_Prefer Text Message'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

object = 'Education Candidate Profile/Register/select_Learn About Us' 
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.learn_about], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/textarea_Other Comment'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.other_comment], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for terms and conditions required messages
fieldList = ['Terms and Conditions']

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

outText = 'Verifying the links to other pages.\n'

outFile.append(outText)

// Click the other hyperlinks and verify pages opened
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks') : pageLinks, ('varObjectPath') : 'Object Repository/Education Partner Profile/Register/'],
	FailureHandling.STOP_ON_FAILURE)

outText = 'Submitting the finished page.\n'

outFile.append(outText)

//Complete and submit the registration
object = 'Education Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

//WebUI.delay(10)

object = 'Object Repository/Education Candidate Profile/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

if (!(registerOnly)) {
    pages = WebUI.callTestCase(findTestCase('Education Candidate Profile/Complete Education Candidate Profile'), [('varCalled') : true], 
        FailureHandling.STOP_ON_FAILURE)

	if(pages.size() == 0 || pages == 'All') {

        success = WebUI.verifyTextPresent('Thank you for submitting your profile on MissionNext Education!', false)

        if (success) {
			outText = '\n+++ Education candidate registration successful. Thank you page was found.\n'
			
			outFile.append(outText)
			
			WebUI.callTestCase(findTestCase('_Functions/Education Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
			
			optionsComment = WebUI.verifyTextPresent('HELLO, CKTEST04EC KOSIERACKI', false)
			
			if(optionsComment) {
				outText = '+++ Education login after profile creation was successful.\n'
			} else {
				outText = '--- Education login after profile creation failed.\n'				
			}
			outFile.append(outText)
			
        } else {
			outText = '\n--- Education candidate registration FAILED. Thank you page was NOT found.\n'
			
			outFile.append(outText)

		}
	}
}

//WebUI.closeBrowser()

