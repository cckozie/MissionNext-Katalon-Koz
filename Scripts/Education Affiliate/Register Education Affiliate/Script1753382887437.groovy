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
println(username[-3..-1])
if(username[-3..-1] != '9ea') {
	println('The Execution Profile must be set to "Education Affiliate"')

	System.exit(0)
}

//######################################################################################################
registerOnly = false //Set this flag to true if you do not want to complete the tabs
if(GlobalVariable.testSuiteRunning) {
	registerOnly = false	//Need to wait for access to be granted
}
//######################################################################################################
//================================== Initialize ===============================================

suffix = '-Register Only'
if(!registerOnly) {
	suffix = '-Full Profile'
}

domain = GlobalVariable.domain

url = (('https://education.' + domain) + '/education-home/register/')

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)


if(1 == 2) { //GlobalVariable.testSuiteRunning) {
	myTestCase = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
} else {
	myTestCase = testName
}

outFile = new File(GlobalVariable.reportPath + myTestCase + suffix + ' on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing ' + myTestCase + ' on ' + domain) + '.\n')

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education affiliate/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'Education Affiliate/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Username') : 'img_Username_field-tooltip',
('Password') : 'img_Password_field-tooltip',
('Contact Last Name') : 'img_Contact Last Name_field-tooltip',
('Contact Email') : 'img_Contact Email_field-tooltip',
('Abbreviation') : 'img_Abbreviation_field-tooltip',
('Web Address') : 'img_Web Address_field-tooltip',
('Description') : 'img_Description_field-tooltip',
('Board of Directors') : 'img_Board of Directors_field-tooltip',
('Statement of Faith') : 'img_Statement of Faith_field-tooltip',
('References') : 'img_References_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Contact Last Name') : 'Person working the website to recruit candidates.',
('Contact Email') : 'Email address must be unique.',
('Abbreviation') : 'Used in some displays.',
('Web Address') : 'Must be a full web address and begin with https://',
('Description') : 'Hint: Take a paragraph or two from your website.',
('Board of Directors') : 'Give web address where this can be found.',
('Statement of Faith') : 'Give web address where this can be found.',
('References') : 'Name and contact info of third-party references or send by email.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Affiliated Organizations') : 'Affiliated Organizations', ('Partnership Agreement') : 'Partnership Agreement'
	, ('Privacy Policy') : 'MissionNext respects the need to maintain the privacy', ('Terms and Conditions') : 'PLEASE READ THESE TERMS AND CONDITIONS']

// Define the required field missing error message test objects
requiredFieldMsgs = [
('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Contact Email') : 'Please enter a valid email address.',
('Contact First Name') : 'The First Name field is required.',
('Contact Last Name') : 'The last name field is required.',
('Contact Phone') : 'The key contact phone field is required.',
('City') : 'The city field is required.',
('State') : 'The state field is required.',
('Country') : '',	//Currently preselected 
('Postal/Zip Code') : 'The srvc agency postal/zip code field is required.',
('Organization Full Name') : 'The agency full name field is required.',
('Abbreviation') : 'The abbreviation field is required.',
('Web Address') : 'The web address field is required.',
('Board of Directors') : 'The board of directors field is required.',
('Statement of Faith') : 'The statement of faith field is required.',
('References') : 'The references field is required.',
('Partnership Agreement') : 'The partnership agreement field is required.',
('Terms and Conditions') : 'The agree with terms field is required.']


//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing MN emails ====================================
WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Create the education partner ==================================
WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register/a_Apply Now'))
object = 'Object Repository/Education Affiliate/Register/a_Apply Now'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.switchToWindowTitle('Education')

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Education Affiliate Register'],
	FailureHandling.STOP_ON_FAILURE)

tooltipTextMap.each({
		println(it)
	})

WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
	, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
FailureHandling.CONTINUE_ON_FAILURE)

// Click the other hyperlinks and verify pages opened
WebUI.callTestCase(findTestCase('_Functions/Test External Links'), [('varPageLinks') : pageLinks, ('varObjectPath') : 'Object Repository/Education Affiliate/Register/'], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Submit the form with all of the fields empty
object = 'Object Repository/Education Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

// Test for username, email, and password required messages
fieldList = ['Username', 'Password', 'Contact Email']

outText = (('Verifying the field messages for ' + fieldList) + '.\n')

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Set username, password, and email and then test for the other missing data error messages
object = 'Object Repository/Education Affiliate/Register/input_Username'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.username],
	FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object
		, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Contact Email'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object, ('varParm1') : GlobalVariable.email],
	FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)

// Test for other field messages
fieldList = ['Contact First Name', 'Contact Last Name', 'Contact Phone', 'Country', 'City', 'State', 'Postal/Zip Code', 
	'Organization Full Name', 'Abbreviation', 'Web Address', 'Board of Directors', 'Statement of Faith',
    'References', 'Partnership Agreement', 'Terms and Conditions']

outText = (('Verifying the field messages for ' + fieldList) + '.\n')

outFile.append(outText)

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList, ('varRequiredFieldMsgs') : requiredFieldMsgs], 
    FailureHandling.CONTINUE_ON_FAILURE)

// Fill in the other fields and submit
object = 'Object Repository/Education Affiliate/Register/input_Password'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setEncryptedText', ('varObject') : object
		, ('varParm1') : GlobalVariable.password], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Contact First Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.first_name], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Contact Last Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.last_name], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Contact Title'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.title], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Contact Phone'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.phone], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_City'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.city], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/select_State'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
	, ('varParm1') : GlobalVariable.state], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_PostalZip Code'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.zip], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Organization Full Name'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.organization_name], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Abbreviation'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.abbreviation], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Web Address'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/textarea_Description'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.description], FailureHandling.STOP_ON_FAILURE)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
    myObject = ((testObjectFolder + 'checkbox_') + org)

    WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : myObject], FailureHandling.STOP_ON_FAILURE)
}

object = 'Object Repository/Education Affiliate/Register/input_Board of Directors'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Board of Directors'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/input_Statement of Faith'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.web_address], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/textarea_References'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'setText', ('varObject') : object
	, ('varParm1') : GlobalVariable.references], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/checkbox_Partnership Agreement'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/checkbox_Terms and Conditions'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Education Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//Verify that the correct Approval Pending page is displayed
found = WebUI.verifyTextPresent('If you completed the Affiliate Application ', false)

if(found) {
	outText = '\nThe appropriate Approval Pending page was displayed'
	
} else {
	outText = '\n##### The appropriate Approval Pending page was NOT displayed #####'
	KeywordUtil.markError('\n' + outText)
}

//================================== Wait for the approval pending email for the new education affiliate =========
emailFound = WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
		, ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (emailFound) {
	outText = (('+++++ Approval request email for ' + username) + ' was found')
	
	outFile.append(outText + '\n')
	
	WebUI.closeBrowser()
	
	//================================== Grant access for the new education partner ==================================
	WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
	
	//================================== Create a subscriptioon for the new education partner ========================
	WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Education'
			, ('varRole') : 'Agency'], FailureHandling.CONTINUE_ON_FAILURE)

	WebUI.callTestCase(findTestCase('_Functions/Education Affiliate Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	found = WebUI.verifyTextPresent('If you completed the Affiliate Application ', false)
	
	if(found) {
		outText = '\n***** The login after registering as an Education Affiliate was successful. *****'
		
	} else {
		outText = '\n##### The login after registering as an Education Affiliage was NOT successful. #####'
		KeywordUtil.markError('\n' + outText)
		GlobalVariable.testCaseErrorFlag = true
	}
	
	outFile.append(outText + '\n')
	
	WebUI.closeBrowser()
	
} else {
	
	outText = (('----- Approval request email for ' + username) + ' was NOT found')
	
	outFile.append(outText + '\n\n')
	GlobalVariable.testCaseErrorFlag = true
}

