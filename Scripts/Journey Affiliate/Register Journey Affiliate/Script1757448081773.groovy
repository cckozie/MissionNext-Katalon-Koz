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
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.io.File as File


// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '3ja') {
	println('The Execution Profile must be set to "Journey Affiliate"')

	System.exit(0)
}

//######################################################################################################
registerOnly = true //Set this flag to true if you do not want to complete the tabs
if(GlobalVariable.testSuiteRunning) {
	registerOnly = true	//Need to wait for access to be granted
}
//######################################################################################################

suffix = '-Register Only'
if(!registerOnly) {
	suffix = '-Full Profile'
}

//================================== Initialize ===============================================
// Get the domain and set the url

domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://journey.' + domain) + '/journey-home/register/')

myTestCase = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

// Write results to text file
outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Journey Affiliate on ' + GlobalVariable.domain) + '.\n')

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey affiliate/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'Journey Affiliate/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Username') : 'img_Username_field-tooltip',
('Contact Email') : 'img_Contact Email_field-tooltip',
('Password') : 'img_Password_field-tooltip',
('Contact Last Name') : 'img_Contact Last Name_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Email') : 'Your primary email address and must be unique in our database.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Contact Last Name') : 'Person working the website to recruit candidates.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Partnership Agreement') : 'Partnership Agreement', ('Terms and Conditions') : 'PLEASE READ THESE TERMS AND CONDITIONS',
	('Privacy Policy') : 'MissionNext respects the need to maintain the privacy of information']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Contact Email') : 'Please enter a valid email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Contact First Name') : 'The First Name field is required.',
('Contact Last Name') : 'The last name field is required.',
('Organization Full Name') : 'The agency full name field is required.',
('Partnership Agreement') : 'The partnership agreement field is required.',
('Terms and Conditions') : 'The agree with terms field is required.']


//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing MN emails ====================================
WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Create the Journey partner ==================================
WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

//WebUI.click(findTestObject('Object Repository/Journey Affiliate/Register/a_Apply Now'))
object = 'Object Repository/Journey Affiliate/Register/a_Apply Now'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//WebUI.switchToWindowTitle('Journey')

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Journey Affiliate Register'],
	FailureHandling.STOP_ON_FAILURE)

tooltipTextMap.each({
		println(it)
	})


WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
	, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
FailureHandling.CONTINUE_ON_FAILURE)

// Click the hyperlinks and verify pages opened
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks') : pageLinks, ('varObjectPath') : 'Object Repository/Journey Affiliate/Register/'], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Submit the form with all of the fields empty
object = 'Object Repository/Journey Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

// Test for username, email, and password required messages
fieldList = ['Username', 'Password', 'Contact Email']

outText = (('Verifying the field messages for ' + fieldList) + '.\n')

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Set username, password, and email and then test for the other missing data error messages
object = 'Object Repository/Journey Affiliate/Register/input_Username'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.username],
	FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object
		, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Contact Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.email],
	FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)
//System.exit(0)

// Test for other field messages
fieldList = ['Contact First Name', 'Contact Last Name',	'Organization Full Name', 'Partnership Agreement', 'Terms and Conditions']

outText = (('Verifying the field messages for ' + fieldList) + '.\n')

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Fill in the other fields and submit
object = 'Object Repository/Journey Affiliate/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object
		, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Contact First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.first_name], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Contact Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.last_name], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Organization Full Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.organization_name], FailureHandling.STOP_ON_FAILURE)
/*
object = 'Object Repository/Journey Affiliate/Register/input_Abbreviation'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.abbreviation], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Web Address'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/textarea_Description'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.description], FailureHandling.STOP_ON_FAILURE)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
    myObject = ((testObjectFolder + 'checkbox_') + org)

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : myObject], FailureHandling.STOP_ON_FAILURE)
}

object = 'Object Repository/Journey Affiliate/Register/input_Board of Directors'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Board of Directors'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/input_Statement of Faith'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/textarea_References'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.references], FailureHandling.STOP_ON_FAILURE)
*/
object = 'Object Repository/Journey Affiliate/Register/checkbox_Partnership Agreement'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Journey Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//Verify that the correct Approval Pending page is displayed
found = WebUI.verifyTextPresent('Thank You for Applying for a MissionNext Journey Partnership', false, FailureHandling.OPTIONAL)

if(found) {
	outText = '\nThe appropriate Approval Pending page was displayed'
	
} else {
	outText = '\n##### The appropriate Approval Pending page was NOT displayed #####'
	KeywordUtil.markError('\n' + outText)
}

outFile.append(outText + '\n')

//================================== Wait for the approval pending email for the new Journey affiliate =========
emailFound = WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
		, ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (emailFound) {
	outText = (('Approval request email for ' + username) + ' was found\n')

	println(outText)

	outFile.append(outText)

	WebUI.closeBrowser()
}

//================================== Grant access for the new Journey partner ==================================
WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Create a subscriptioon for the new Journey partner ========================
WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Journey'
		, ('varRole') : 'Agency'], FailureHandling.CONTINUE_ON_FAILURE)

WebUI.callTestCase(findTestCase('_Functions/Journey Affiliate Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)

found = WebUI.verifyTextPresent('Thank You for Applying for a MissionNext Journey Partnership', false, FailureHandling.OPTIONAL)

if(found) {
	outText = '\n***** The login after registering as an Journey Affiliate was successful. *****'
	
} else {
	outText = '\n##### The login after registering as an Journey Affiliage was NOT successful. #####'
	KeywordUtil.markError('\n' + outText)
}

outFile.append(outText + '\n')



WebUI.closeBrowser()
