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

///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//	Need to verify off page links on profile page
//
//
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Write results to text file
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Partner.txt')

GlobalVariable.outFile = outFile

domain = GlobalVariable.domain

outFile.write('Testing Register Education Partner in ' + domain + '\n')

// Define path to tooltip text images
path = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/journey partner application/'

// Define the names of the tooltip fields and unique part of the related test object
tooltips = [('username') : 'img_Username_field-tooltip', ('password10') : 'img_Password_field-tooltip', ('email') : 'img_Key Contact Email_field-tooltip'
    , ('organization') : 'img_Organization_field-tooltip', ('abbreviation') : 'img_Abbreviation_field-tooltip', ('description') : 'img_Description_field-tooltip'
    , ('website_url') : 'img_Website Address_field-tooltip', ('hear_about') : 'img_How did you hear about MissionNext_field-tooltip']

// Define applicant fields ==================================================================================================
firstName = GlobalVariable.first_name //'Chris TEST EP'

lastName = GlobalVariable.last_name //'Kosieracki'

username = GlobalVariable.username

password = GlobalVariable.password

email = GlobalVariable.email

organization = 'The CCK TEST Christian School'

abbreviation = 'CCK CS'

worldRegion = 'North America'

country = 'United States'

description = 'k-8 Christian School'

website = 'https://missionnext.org'

affiliatedOrgs = ['ECFA', 'AIMS', 'Accord Network']

qualifications = 'Yes, my school is a Christian School'

hearAbout = 'My Church'

referral = 'Brad Benson'

//================================== Delete the user ===============================================

WebUI.callTestCase(findTestCase('Admin/Delete User'), ['varUsername' : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing emails ===============================================

WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//================================== Create the education partner ==================================

url = 'https://education.' + domain + '/signup/organization'

//WebUI.openBrowser('https://education.explorenext.org/signup/organization')
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

        myImage = ((path + myKey) + '.png')

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

                //			pctVal = pct.replace('.','').toInteger() *10
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

// Submit the form with all of the fields empty
WebUI.click(findTestObject('Education Partner Profile/Register/button_Sign up'))

// Test for username, password, and email required messages
WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_Username must be at least 6 characters long, no spaces, lowercase letters and numbers only, no special or unique characters'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_Password must be at least 6 characters long, no spaces, lowercase letters and numbers only, no special or unique characters'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_Please enter a valid email address'))

pswMsg = WebUI.getText(findTestObject('Education Partner Profile/Register/div_Password must be at least 6 characters long, no spaces, lowercase letters and numbers only, no special or unique characters'))

if ((pswMsg.indexOf('at least 6 characters') >= 0) || pswMsg.indexOf('no special or unique characters')) {
    outText = 'Invalid error message for password.'

    outFile.append(outText + '\n')

    println(outText)
}

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Username'), username)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Password'), password)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Email'), email)

WebUI.click(findTestObject('Education Partner Profile/Register/button_Sign up'))

WebUI.waitForPageLoad(10)

// Test for first name, last name, and phone number required messages
WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The Fiirst Name field is required'))

firstNameMsg = WebUI.getText(findTestObject('Education Partner Profile/Register/div_The Fiirst Name field is required'))

println(firstNameMsg)

pos = firstNameMsg.indexOf('Fiirst')

if (pos >= 0) {
    outText = '"First" is misspelled as "Fiirst" in the first name required error message'

    outFile.append(outText + '\n')

    println(outText)
}

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The Fiirst Name field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The last name field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The organization name field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The description field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The website address field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The meet qualifications for christian school field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The partner agreement field is required'))

WebUI.verifyElementVisible(findTestObject('Education Partner Profile/Register/div_The terms and conditions field is required'))

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Password'), password)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact First Name'), firstName)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Key Contact Last Name'), lastName)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Organization'), organization)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Abbreviation'), abbreviation)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_World Region'), worldRegion, false)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_Country'), country, false)

WebUI.setText(findTestObject('Education Partner Profile/Register/textarea_Description'), description)

WebUI.setText(findTestObject('Education Partner Profile/Register/input_Website Address'), website)

for (def org : affiliatedOrgs) {
    WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/checkbox_Affiliation-Parm', [('org') : org]))
}

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_School Qualifications'), qualifications, 
    false)

WebUI.selectOptionByValue(findTestObject('Education Partner Profile/Register/select_How did you hear about MissionNext'), 
    hearAbout, false)

WebUI.setText(findTestObject('Object Repository/Education Partner Profile/Register/textarea_Referral or Other Comment'), 
    referral)

WebUI.click(findTestObject('Education Partner Profile/Register/checkbox_Partnership Agreement'))

WebUI.click(findTestObject('Education Partner Profile/Register/checkbox_Terms and Conditions'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Register/button_Sign up'))

WebUI.delay(1)

WebUI.waitForPageLoad(10)

pending = WebUI.verifyTextPresent('Approval Pending', false, FailureHandling.OPTIONAL)

if(pending) {
	
	outText = ('Approval Pending page was found.')
	
	println(outText)

	outFile.append(outText + '\n')

} else {
	
	outText = ('Failed to find the Approval Pending page.')
	
	println(outText)

	outFile.append(outText + '\n')

}

//================================== Wait for the approval pending email for gthe new education partner =========

WebUI.callTestCase(findTestCase('_Functions/Wait for Email'), [('varSubjectKey') : 'Approval request', 'varUsername' : username], FailureHandling.STOP_ON_FAILURE)

if (GlobalVariable.returnCode == 'found') {
	
    println('Approval request email for ' + username + ' was found')
	
	WebUI.closeBrowser()
	
//================================== Grant access for the new education partner ==================================

	WebUI.callTestCase(findTestCase('Admin/Grant Access'), ['varUsername' : username], FailureHandling.STOP_ON_FAILURE)

//================================== Create a subscriptioon for the new education partner ========================

    WebUI.callTestCase(findTestCase('Admin/Create Subscription'), ['varUsername' : username, 'varType' : 'Education', 'varRole' : 'Organization'], FailureHandling.STOP_ON_FAILURE)
	
}
