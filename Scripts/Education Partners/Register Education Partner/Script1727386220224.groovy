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
import com.kms.katalon.core.util.KeywordUtil

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
	println('The Execution Profile must be set to "Education Partner"')

	System.exit(0)
}


///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//	Need to verify off page links on profile page - Completed 07/31/24
//	Move the input field data out of the script and into the profile GlobalVariables	-Completed 10/04/24
//  Need to test for the wrong text on the tooltip for password							-Completed 10/04/24
//	Need to test the text of the error messages, not just that they are visible			-Completed 10/04/24
// 	Modify to use Generic Wait for Email												-Completed 10/04/24
//  Create and use image folder for education, not journey
//
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

//================================== Initialize ===============================================
// Get the domain and set the url
domain = GlobalVariable.domain

url = (('https://education.' + domain) + '/signup/organization')

// Specify file to contain test case results, update the global variable, and write the first line of text
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Partner.txt')

GlobalVariable.outFile = outFile

outFile.write(('Testing Register Education Partner in ' + domain) + '\n')

// Define path to tooltip text images
ttPath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/'

// Define the names of the tooltip fields and unique part of the related test object
tooltips = [('username') : 'img_Username_field-tooltip', ('password') : 'img_Password_field-tooltip', ('email') : 'img_Key Contact Email_field-tooltip'
    , ('organization') : 'img_Organization_field-tooltip', ('abbreviation') : 'img_Abbreviation_field-tooltip', ('description') : 'img_Description_field-tooltip'
    , ('website_url') : 'img_Website Address_field-tooltip', ('hear_about') : 'img_How did you hear about MissionNext_field-tooltip']

// Define the page's links and the text to search for on the linked page
pageLinks = ['Affiliated Organizations' : 'Affiliated Organizations', 'countries by region' : 'Countries by Region',
	'Partnership Agreement' : 'Partnership Agreement', 'Privacy Policy' : 'Privacy Policy',
	'School Qualifications' : 'SCHOOL QUALIFICATIONS', 'Terms and Conditions' : 'Terms and Conditions']

// Define the field messages
fieldMessages = 
["Username message" : "Username must be at least 6 characters long, no spaces, lowercase letters and numbers only, no special or unique characters.",
"Password message" : "Password must have at least 10 characters; should include numbers, letters, and capitals; \
may have special characters (@, #, *, spaces, etc.) and may include a passphrase.",
"Email message" : "Please enter a valid email address.",
"First name message" : "The First Name field is required.",
"Last name message" : "The last name field is required.",
"Organization name message" : "The organization name field is required.",
"Description message" : "The description field is required.",
"Website address message" : "The website address field is required.",
"School qualifications message" : "The meet qualifications for christian school field is required.",
"Partner agreement message" : "The partner agreement field is required.",
"Terms and conditions message" : "The terms and conditions field is required."]


//================================== Delete the user ===============================================

WebUI.callTestCase(findTestCase('Admin/Delete User'), ['varUsername' : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing emails ====================================

WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Preopen the email app for testing the link for Custstomer Support 
File file = new File('/System/Applications/Mail.app')

Desktop.getDesktop().open(file)


//================================== Create the education partner ==================================
WebUI.openBrowser(url)

WebUI.maximizeWindow()



WebUI.setText(findTestObject('Object Repository/Education Partner Profile/Register/input_Username'), '=====> WAITING FOR SIKULI TO LOAD <=====')

Screen s = new Screen()

WebUI.clearText(findTestObject('Object Repository/Education Partner Profile/Register/input_Username'))

// Use Sikulix to verify the tooltip messages are displayed. % match numbers are sent to output file
tooltips.each({ 
        myKey = it.key

        myValue = it.value

        println(myKey)

        println(myValue)

        tObj = ('Education Partner Profile/Register/' + myValue)

        WebUI.click(findTestObject(tObj))

        WebUI.delay(1)

        myImage = ((ttPath + myKey) + '.png')

        f = new File(myImage)

        if (f.exists()) {
            println('Looking for ' + myImage)

            Pattern icn = new Pattern(myImage).similar(0.70)

            found = s.exists(icn)

            println(found)

            if (found != null) {
                foundStr = found.toString()

                matchP = foundStr.indexOf('S:')

                println(matchP)

                pct = foundStr.substring(matchP + 2, matchP + 5)

                outText = ('+++++++++++++++ Found tooltip text for ' + myKey)

                println(outText)

                pctVal = new BigDecimal(pct)

                pctVal = (pctVal * 100).intValue()

                outFile.append(((outText + ' : ') + pctVal) + '%\n')
                
            } else {
                outText = ('----------- Unable to find tooltip text for ' + myKey)

                println(outText)

                outFile.append(outText + '\n')
            }
        } else {
            outText = ('Unable to find image file ' + myImage)

            outFile.append(outText + '\n')

            println(outText)
        }
    })

// Click on the Customer Support link and use Sikulix to verify the opening of an email addressed to us
WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/a_Customer Support'))

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
	
	    matchP = foundStr.indexOf('S:')
	
	    println(matchP)
	
	    pct = foundStr.substring(matchP + 2, matchP + 5)
	
	    outText = ('+++++++++++++++ Found email address image')
	
	    println(outText)
	
	    pctVal = new BigDecimal(pct)
	
	    pctVal = (pctVal * 100).intValue()
	
	    outFile.append(((outText + ' : ') + pctVal) + '%\n')
		
    } else {
        outText = ('----------- Unable to find email address image')

        println(outText)

        outFile.append(outText + '\n')
    }
} else {
	
    outText = ('Unable to find image file ' + myImage)

    outFile.append(outText + '\n')

    println(outText)
}
                
// Close the email window and app
Robot robot = new Robot()

//Close the email window
robot.keyPress(KeyEvent.VK_META) //META is the Mac Command key

robot.keyPress(KeyEvent.VK_W)

robot.keyRelease(KeyEvent.VK_W )

robot.keyRelease(KeyEvent.VK_META)

WebUI.delay(1)

//Take the Don't Save option
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_D)

robot.keyRelease(KeyEvent.VK_D )

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
    
}

println(outText)

outFile.append(outText + '\n')


// Click the other hyperlinks and verify pages opened
pageLinks.each ({
	
	myElement = 'a_' + it.key
	
	myText = it.value

	WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/' + myElement))

	WebUI.switchToWindowIndex(1)
	
	WebUI.delay(1)
	
	textFound = WebUI.verifyTextPresent(myText, false, FailureHandling.OPTIONAL)
	
	if(textFound) {
		
		outText = '+++++ The text "' + myText + '" was found after clicking on the ' + it.key + ' link'
		
		println(outText)
		
		outFile.append(outText + '\n')
		
	} else {

		outText = '----- The text "' + myText + '" was NOT found after clicking on the ' + it.key + ' link'
		
		println(outText)
		
		outFile.append(outText + '\n')
		
	}

	WebUI.closeWindowIndex(1)
	
	WebUI.delay(1)
	
	WebUI.switchToWindowIndex(0)
	
	WebUI.delay(1)
	
})

// Submit the form with all of the fields empty
WebUI.click(findTestObject('Education Partner Profile/Register/button_Sign up'))

fieldMessages.each({
	
	myObj = it.key

	myMsg = it.value
	
	println('Testing for ' + myObj + ' of "' + myMsg + '".')
	
	WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_' + myObj))
	
	msg = WebUI.getText(findTestObject('Education Partner Profile/Register/div_' + myObj))

	if(msg != myMsg) {
		
		outText = '----- ' + myObj + ' is:\n"' + msg + '"\n     but it should be \n"' + myMsg + '"'
		
		println(outText + '\n')
		
		outFile.append(outText + '\n')
		
		KeywordUtil.markError('\n' + outText)
		
	}
	
	if(myObj == 'Email message') {
		
		// Set username, password, and email and then test for the other missing data error messages
		WebUI.setText(findTestObject('Education Partner Profile/Register/input_Username'), GlobalVariable.username)
		
		WebUI.setEncryptedText(findTestObject('Education Partner Profile/Register/input_Password'), GlobalVariable.password)
		
		WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)
		
		WebUI.click(findTestObject('Education Partner Profile/Register/button_Sign up'))
		
		WebUI.waitForPageLoad(10)
		
	}
	
})

// Fill in the other fields and submit
WebUI.setEncryptedText(findTestObject('Education Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact First Name'), GlobalVariable.first_name)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Last Name'), GlobalVariable.last_name)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Organization'), GlobalVariable.organization)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Abbreviation'), GlobalVariable.abbreviation)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_World Region'), GlobalVariable.world_region, false)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_Country'), GlobalVariable.country, false)

WebUI.setText(findTestObject('Education Partner Profile/Register/textarea_Description'), GlobalVariable.description)

website = 'https://' + GlobalVariable.domain

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
}

//================================== Wait for the approval pending email for the new education partner =========
//WebUI.callTestCase(findTestCase('_Functions/Wait for Email'), [('varSubjectKey') : 'Approval request', ('varUsername') : username], 
//    FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'Chris.Kosieracki@missionnext.org', 
	('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
	
    println(('Approval request email for ' + username) + ' was found')

    WebUI.closeBrowser()

//================================== Grant access for the new education partner ==================================
    WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Create a subscriptioon for the new education partner ========================
    WebUI.callTestCase(findTestCase('Admin/Create Subscription'), [('varUsername') : username, ('varType') : 'Education'
            , ('varRole') : 'Organization'], FailureHandling.STOP_ON_FAILURE)
}
// We're done!

