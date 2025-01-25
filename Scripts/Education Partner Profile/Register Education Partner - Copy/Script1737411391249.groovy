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
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.*
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import java.awt.Desktop as Desktop
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.*
import org.apache.commons.lang.WordUtils as WordUtils
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

    System.exit(0)
}

//######################################################################################################
registerOnly = false //Set this flag to true if you do not want to complete the tabs
//######################################################################################################

//================================== Initialize ===============================================
// Get the domain and set the url
domain = GlobalVariable.domain

username = GlobalVariable.username

url = (('https://education.' + domain) + '/signup/organization')

// Specify file to contain test case results, update the global variable, and write the first line of text
outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Partner on ' + domain) + 
'.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Education Partner on ' + domain) + '.\n')

// Define path to tooltip text images
tooltipImagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'

// Define the folder with the tooltip test objects live
testObjectFolder = 'Education Partner Profile/Register/'

// Define the names of the tooltip fields and the unique part of the related test object
// ('dummy' is a necessary fake 'element' because Sikulix does not do an image compare correctly on the first element tested)
tooltips = [
('dummy') : 'dummy',
('Username') : 'img_Username_field-tooltip',
('Password') : 'img_Password_field-tooltip',
('Key Contact Email') : 'img_Key Contact Email_field-tooltip',
('Organization') : 'img_Organization_field-tooltip',
('Abbreviation') : 'img_Abbreviation_field-tooltip',
('Description') : 'img_Description_field-tooltip',
('Website Address') : 'img_Website Address_field-tooltip',
('How did you hear about MissionNext') : 'img_How did you hear about MissionNext_field-tooltip']

// Define the expected tooltip texts
tooltipText = [
('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Key Contact Email') : 'Email address must be unique. Use another for a different MissionNext account.',
('Organization') : 'Name of your Organization.(Shorten, if too long to fit in field)',
('Abbreviation') : 'Generally, the first initials of your organization name; this is used in some displays.',
('Description') : 'Hint: Take a paragraph or two from your website. If copying & pasting, you must manually type in any special characters, i.e, quotation marks, apostrophe, ampersand, etc.',
('Website Address') : 'Must start with https://',
('How did you hear about MissionNext') : 'Please complete this field.  It helps us to know where we should focus our efforts.']

// Define the page's links and the text to search for on the linked page
pageLinks = [('Affiliated Organizations') : 'Affiliated Organizations', ('countries by region') : 'Countries by Region', ('Partnership Agreement') : 'Partnership Agreement'
    , ('Privacy Policy') : 'Privacy Policy', ('School Qualifications') : 'SCHOOL QUALIFICATIONS', ('Terms and Conditions') : 'Terms and Conditions']

// Define the field missing error message test objects
requiredFieldMsgs = [
('Username') : 'Username must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.',
('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.',
('Key Contact Email') : 'Please enter a valid email address.',
('Key Contact First Name') : 'The First Name field is required.',
('Key Contact Last Name') : 'The last name field is required.',
('Organization') : 'The organization name field is required.',
('Description') : 'The description field is required.',
('Website Address') : 'The website address field is required.',
('Meet Christian School Qualifications?') : 'The meet qualifications for christian  field is required.',
('Partnership Agreement') : 'The partner agreement field is required.',
('Terms and Conditions') : 'The terms and conditions field is required.']

/*
//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing emails ====================================
WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Preopen the email app for testing the link for Custstomer Support 
File file = new File('/Applications/Microsoft Outlook.app')

Desktop.getDesktop().open(file)
*/
//================================== Create the education partner ==================================
WebUI.openBrowser(url)

WebUI.maximizeWindow()

WebUI.navigateToUrl(url)

WebUI.waitForPageLoad(10)

tooltipTextMap = WebUI.callTestCase(findTestCase('_Functions/Get Screenshot and Tooltip Text'), [('varExtension') : 'Register'],
	FailureHandling.STOP_ON_FAILURE)

tooltipTextMap.each {
	println(it)
}

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)


// Call the tooltip testing script
WebUI.callTestCase(findTestCase('_Functions/Test Tooltips'), [('varTooltipImagePath') : tooltipImagePath, ('varTooltips') : tooltips
		, ('varTooltipText') : tooltipText, ('varTestObjectFolder') : testObjectFolder, ('varTooltipTextMap') : tooltipTextMap],
	FailureHandling.STOP_ON_FAILURE)

// Submit the empty page
object = 'Education Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)


//For script setup only - finds the required field error messages
//WebUI.callTestCase(findTestCase('Utilities/Find error messages'), [:], FailureHandling.STOP_ON_FAILURE)


outText = 'Verifying the required field messages.\n'

outFile.append(outText)

// Test for username, email, and password required messages
fieldList = ['Username', 'Password', 'Key Contact Email']

WebUI.callTestCase(findTestCase('_Functions/Test Field Error Messages'), [('varFieldList') : fieldList,
	('varRequiredFieldMsgs') : requiredFieldMsgs], FailureHandling.STOP_ON_FAILURE)

// Click on the Customer Support link and use Sikulix to verify the opening of an email addressed to us
Screen s = new Screen()

object = 'Object Repository/Education Partner Profile/Register/a_Customer Support'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(2)

csPath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/email/'

myImage = (csPath + 'emailAddress.png')

f = new File(myImage)

println(f)

if (f.exists()) {
    println('Looking for ' + myImage)

    Pattern icn = new Pattern(myImage).similar(0.70)

    found = s.exists(icn)

    println(found)

    if (found != null) {
        foundStr = found.toString()

		matchP = foundStr.indexOf('%:')
		
		pct = foundStr.substring(matchP + 1, matchP + 6)
		
		outText = ('+++ Image match for email address is ' + pct + '%')
		
		pctF = pct.toFloat()
		
		min = 80
		
		if(pct.toFloat() < min.toFloat()) {
			outText = outText + ' <<<<'
		}	
		
        outFile.append(((outText + ' : ') + pctVal) + '%\n')
    } else {
        outText = '--- Unable to find email address image'

        println(outText)

        outFile.append(outText + '\n')

        KeywordUtil.markError('\n' + outText)
    }
} else {
    outText = ('Unable to find image file ' + myImage)

    outFile.append(outText + '\n')

    println(outText)

    KeywordUtil.markError('\n' + outText)
}

// Close the email window and app
Robot robot = new Robot()

//Close the email window
robot.keyPress(KeyEvent.VK_META) //META is the Mac Command key

robot.keyPress(KeyEvent.VK_W)

robot.keyRelease(KeyEvent.VK_W)

robot.keyRelease(KeyEvent.VK_META)

WebUI.delay(1)

//Take the Don't Save option
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_D)

robot.keyRelease(KeyEvent.VK_D)

robot.keyRelease(KeyEvent.VK_META)

WebUI.delay(1)

//Quit the app
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_Q)

robot.keyRelease(KeyEvent.VK_Q)

robot.keyRelease(KeyEvent.VK_META)

if (found != null) {
    outText = '+++++ Found email to customer support'
} else {
    outText = '----- Failed to find email to customer support'

    KeywordUtil.markError('\n' + outText)
}

println(outText)

outFile.append(outText + '\n')

// Click the other hyperlinks and verify pages opened
pageLinks.each({ 
        myElement = ('a_' + it.key)

        myText = it.value

        WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/' + myElement))

        WebUI.switchToWindowIndex(1)

        WebUI.delay(1)

        textFound = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)

        if (textFound) {
            outText = (((('+++++ The text "' + myText) + '" was found after clicking on the ') + it.key) + ' link')

            println(outText)

            outFile.append(outText + '\n')
        } else {
            outText = (((('----- The text "' + myText) + '" was NOT found after clicking on the ') + it.key) + ' link')

            println(outText)

            outFile.append(outText + '\n')

            KeywordUtil.markError('\n' + outText)
        }
        
        WebUI.closeWindowIndex(1)

        WebUI.delay(1)

        WebUI.switchToWindowIndex(0)

        WebUI.delay(1)
    })


	//WebUI.scrollToElement(findTestObject('Education Partner Profile/Register/button_Sign up'), 0)
	// Submit the form with all of the fields empty
	WebUI.click(findTestObject('Education Partner Profile/Register/button_Sign up'))
	
	fieldMessages.each({ 
        myObj = it.key

        myMsg = it.value

        println(((('Testing for ' + myObj) + ' of "') + myMsg) + '".')

        WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_' + myObj))

        msg = WebUI.getText(findTestObject('Education Partner Profile/Register/div_' + myObj))

        if (msg != myMsg) {
            outText = (((((('----- ' + myObj) + ' is:\n"') + msg) + '"\n     but it should be \n"') + myMsg) + '"')

            println(outText + '\n')

            outFile.append(outText + '\n')

            KeywordUtil.markError('\n' + outText)
        }
        
        if (myObj == 'Email message') {
            // Set username, password, and email and then test for the other missing data error messages
            WebUI.setText(findTestObject('Education Partner Profile/Register/input_Username'), GlobalVariable.username)

            WebUI.setEncryptedText(findTestObject('Education Partner Profile/Register/input_Password'), GlobalVariable.password)

            WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)

            WebUI.click(findTestObject('Education Partner Profile/Register/button_Sign up'))

            WebUI.waitForPageLoad(10)
        }
    })

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Education Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)

object = 'Education Partner Profile/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

// Fill in the other fields and submit
WebUI.setEncryptedText(findTestObject('Education Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact First Name'), GlobalVariable.first_name)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Last Name'), GlobalVariable.last_name)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Organization'), GlobalVariable.organization)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Abbreviation'), GlobalVariable.abbreviation)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_World Region'), GlobalVariable.world_region, 
    false)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_Country'), GlobalVariable.country, false)

WebUI.setText(findTestObject('Education Partner Profile/Register/textarea_Description'), GlobalVariable.description)

website = ('https://' + GlobalVariable.domain)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Website Address'), website)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
    myObject = ('checkbox_' + org)

    WebUI.click(findTestObject('Education Partner Profile/Register/' + myObject))
}

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_School Qualifications'), GlobalVariable.meet_qualifications, 
    false)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_How did you hear about MissionNext'), 
    GlobalVariable.learn_about, false)

WebUI.setText(findTestObject('Object Repository/Education Partner Profile/Register/textarea_Referral or Other Comment'), 
    GlobalVariable.other_comment)

WebUI.click(findTestObject('Education Partner Profile/Register/checkbox_Partnership Agreement'))

WebUI.click(findTestObject('Education Partner Profile/Register/checkbox_Terms and Conditions'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/button_Sign up'))

WebUI.delay(1)

// Delay, then test for the Approval Pending page
WebUI.waitForPageLoad(10)

pending = WebUI.verifyTextPresent('Approval Pending', false, FailureHandling.OPTIONAL)

if (pending) {
    outText = 'Approval Pending page was found.'

    println(outText)

    outFile.append(outText + '\n')
} else {
    outText = 'Failed to find the Approval Pending page.'

    println(outText)

    outFile.append(outText + '\n')

    KeywordUtil.markError('\n' + outText)
}

//================================== Wait for the approval pending email for the new education partner =========
WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
        , ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
    println(('Approval request email for ' + username) + ' was found')

    WebUI.closeBrowser()

    //================================== Grant access for the new education partner ==================================
    WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
	System.exit(0)
    //================================== Create a subscriptioon for the new education partner ========================
    WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Education'
            , ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)
	
    //================================== Complete the Education Partner tabs ========================
	WebUI.callTestCase(findTestCase('Education Partner Profile/Complete Education Partner Profile'), [:], FailureHandling.STOP_ON_FAILURE)
	
	
}
