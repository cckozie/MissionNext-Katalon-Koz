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

step = 0

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '5jc') {
    println('The Execution Profile must be set to "Journey Candidate"')

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

url = (('https://journey.' + domain) + '/signup/candidate')

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

//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

// Define the required field missing error message test objects
requiredFieldMsgs = [('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
    , ('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
    , ('Email') : 'Please enter a valid email address.', ('First Name') : 'The First Name field is required.', ('Last Name') : 'The last name field is required.'
    , ('Country') : 'The country field is required.', ('Phone Number') : 'The phone field is required.', ('Terms and Conditions') : 'The terms and conditions field is required.']

// Define the top 3 required field test objects and type
requiredTopFields = ['input_Username' : 'setText', 'input_Email' : 'setText', 'input_Password' : 'setEncryptedText']

// Define the other required field test objects and type
requiredTopFields = ['input_First Name' : 'setText', 'input_Last Name' : 'setText', 'input_Country' : 'selectOptionByValue',
	'input_Best Phone Number' : 'setText', 'checkbox_Terms and Conditions' : 'click']




//================================== Create the Journey candidate ==================================
WebUI.openBrowser('')

WebUI.maximizeWindow()

if(GlobalVariable.mobileScreen) {
	
	WebUI.setViewPortSize(600, 1200)
	
	WebUI.delay(1)
}

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

// Submit the empty page
object = 'Journey Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

/*
// Test for username, email, and password required messages
fieldList = ['Username', 'Email', 'Password']

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)
*/

WebUI.executeJavaScript('window.open();', [])

WebUI.switchToWindowIndex(1)

step++

found = WebUI.callTestCase(findTestCase('Admin/Test for User in AD'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

println('New user found after step ' + step + ' is ' + found)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

object = 'Journey Candidate Profile/Register/input_Username'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.username], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/input_Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.email], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

/*
object = 'Journey Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for first name, last name, and phone number required messages
fieldList = ['First Name', 'Last Name', 'Country', 'Phone Number', 'Terms and Conditions']

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)
*/

//Enter the password, first and last names, country, and phone number, and optional fields except Terms and Conditions
object = 'Journey Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/input_First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.first_name], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/input_Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.last_name], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.country], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/input_Best Phone Number'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.phone_number], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.prefer_text) {
	object = 'Journey Candidate Profile/Register/checkbox_Prefer Text Message'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
}

object = 'Journey Candidate Profile/Register/select_Learn About Us' 
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : GlobalVariable.learn_about], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/textarea_Other Comment'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : GlobalVariable.other_comment], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Test for terms and conditions required messages
fieldList = ['Terms and Conditions']

outText = 'Verifying the required field messages for ' + fieldList + '.\n'

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.CONTINUE_ON_FAILURE)

outText = 'Verifying the links to other pages.\n'

outFile.append(outText)

outText = 'Submitting the page.\n'

outFile.append(outText)

//Complete and submit the registration
object = 'Journey Candidate Profile/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setEncryptedText',
	('varObject') : object, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Candidate Profile/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Journey Candidate Profile/Register/input_First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'setText',
	('varObject') : object, ('varParm1') : ''], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Candidate Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.executeJavaScript('window.open();', [])

WebUI.switchToWindowIndex(1)

step++

found = WebUI.callTestCase(findTestCase('Admin/Test for User in AD'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

println('New user found is ' + found)

WebUI.closeBrowser()

