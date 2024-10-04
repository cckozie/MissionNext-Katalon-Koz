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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest07jp') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

// Write results to text file
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Journey Partner.txt')

outFile.write('Testing Register Journey Partner\n')

// Define path to tooltip text images
path = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey partner/journey partner application/'

// Define the names of the tooltip fields and unique part of the related test object
tooltips = [('username') : 'img_Username_field-tooltip', ('password') : 'img_Password_field-tooltip', ('organization') : 'img_Organization_field-tooltip'
    , ('abbreviation') : 'img_Abbreviation_field-tooltip', ('email') : 'img_Key Contact Email_field-tooltip', ('tier') : 'img_Tier Level_field-tooltip'
    , ('zip') : 'img_PostZip Code_field-tooltip', ('bod') : 'img_Board of Directors_field-tooltip', ('description') : 'img_Description_field-tooltip'
    , ('website_url') : 'img_Website Address_field-tooltip', ('mission_statement') : 'img_Mission Statement_field-tooltip'
    , ('bod') : 'img_Board of Directors_field-tooltip', ('statement_of_faith') : 'img_Statement of Faith_field-tooltip', ('references') : 'img_References_field-tooltip'
    , ('hear_about') : 'img_How did you hear about MissionNext_field-tooltip']

// Define applicant fields
firstName = GlobalVariable.first_name

lastName = GlobalVariable.last_name

username = GlobalVariable.username

password = GlobalVariable.password

email = GlobalVariable.email

organization = 'The CCK TEST Journey Partner'

abbreviation = 'CCK JP'

title = 'CTO'

phone = GlobalVariable.phone_number

tier = 'Tier 2: $500K to $3M'

address = '1250 Waconia Pkwy N.'

city = 'Waconia'

state = 'MN'

zip = '55387'

description = 'A journey partner ministry'

website = 'https://missionnext.org'

affiliatedOrgs = ['ECFA', 'AIMS', 'Accord Network']

mission_statement = (website + '/mission')

bod = (website + '/bod')

sof = (website + '/sof')

references = 'Brad Benson'

hear_about = 'My Church'

referral = 'None'

//WebUI.openBrowser('https://journey.missionnext.org/signup/organization')
WebUI.openBrowser(('https://journey.' + GlobalVariable.domain) + '/signup/organization')

WebUI.maximizeWindow()

/*
WebUI.setText(findTestObject('Object Repository/Journey Partner Profile/Register/input_Username'), '=====> WAITING FOR SIKULI TO LOAD <=====')

Screen s = new Screen()

WebUI.clearText(findTestObject('Object Repository/Journey Partner Profile/Register/input_Username'))

// Use Sikulix to verify the tooltip messages are displayed. % match numbers are sent to output file
tooltips.each({ 
        myKey = it.key

        myValue = it.value

        println(myKey)

        println(myValue)

//        tObj = ('Partner Journey Profile/Register/' + myValue)
		tObj = ('Journey Partner Profile/Register/' + myValue)
		
        println(tObj)

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
*/
// Submit the form with all of the fields empty
WebUI.click(findTestObject('Journey Partner Profile/Register/button_Sign up'))

// Test for username, password, and email required messages
WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Username must be at least 6 characters long, no spaces, lowercase letters and numbers only, no special or unique characters'))

WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Password must be at least 6 characters long, no spaces, lowercase letters and numbers only, no special or unique characters'))

WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_Please enter a valid email address'))

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Username'), GlobalVariable.username)

WebUI.setEncryptedText(findTestObject('Journey Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)

WebUI.click(findTestObject('Journey Partner Profile/Register/button_Sign up'))

WebUI.waitForPageLoad(10)

// Test for first name, last name, and phone number required messages
WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_The Fiirst Name field is required'))

firstNameMsg = WebUI.getText(findTestObject('Journey Partner Profile/Register/div_The Fiirst Name field is required'))

println(firstNameMsg)

pos = firstNameMsg.indexOf('Fiirst')

if (pos >= 0) {
    outText = 'ERROR: "First" is misspelled as "Fiirst" in the first name required error message'

    outFile.append(outText + '\n')

    println(outText)
}

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The organization name field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The Fiirst Name field is required'))

WebUI.verifyElementVisible(findTestObject('Journey Partner Profile/Register/div_The last name field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The key contact phone field is required'))

//Define the fields that incorrectly referred to 'school' instead of 'organization'
fields = ['mailing address', 'city', 'post code']

for (def field : fields) {
    print('the field is ' + field)

    WebUI.verifyElementVisible(findTestObject(('Object Repository/Journey Partner Profile/Register/div_The ' + field) + 
            ' field is required'))

    addressMessage = WebUI.getText(findTestObject(('Object Repository/Journey Partner Profile/Register/div_The ' + field) + 
            ' field is required'))

    if (addressMessage.indexOf('school') >= 0) {
        outText = (('ERROR: The ' + field) + ' required error message references a school instead of an organization.')

        outFile.append(outText + '\n')

        println(outText)
    }
}

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The state field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The description field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The website address field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The memberships field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The board of directors field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The statement of faith field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The references field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The partner agreement field is required'))

WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Register/div_The terms and conditions field is required'))

WebUI.setEncryptedText(findTestObject('Journey Partner Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Object Repository/Journey Partner Profile/Register/input_Organization'), GlobalVariable.organization)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Abbreviation'), GlobalVariable.abbreviation)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Title'), GlobalVariable.title)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact First Name'), GlobalVariable.first_name)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Last Name'), GlobalVariable.last_name)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Email'), GlobalVariable.email)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Key Contact Phone'), GlobalVariable.phone_number)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_Tier Level'), GlobalVariable.tier, false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_Mailing Address'), GlobalVariable.address)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_City'), GlobalVariable.city)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_State'), GlobalVariable.state, false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_PostZip Code'), GlobalVariable.zip)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_Country'), GlobalVariable.country, false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_Description'), GlobalVariable.description)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Website Address'), GlobalVariable.website)

orgs = GlobalVariable.affiliated_orgs

for (def org : orgs) {
    myObject = ('checkbox_' + org)

    WebUI.click(findTestObject('Journey Partner Profile/Register/' + myObject))
}

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Mission Statement'), GlobalVariable.mission_statement)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Board of Directors'), GlobalVariable.bod)

WebUI.setText(findTestObject('Journey Partner Profile/Register/input_Statement of Faith'), GlobalVariable.sof)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_References'), GlobalVariable.references)

WebUI.selectOptionByValue(findTestObject('Journey Partner Profile/Register/select_Referral'), GlobalVariable.hear_about, 
    false)

WebUI.setText(findTestObject('Journey Partner Profile/Register/textarea_Referral or Other Comment'), GlobalVariable.referral)

WebUI.click(findTestObject('Journey Partner Profile/Register/checkbox_Partnership Agreement'))

WebUI.click(findTestObject('Journey Partner Profile/Register/checkbox_Terms and Conditions'))

WebUI.click(findTestObject('Journey Partner Profile/Register/button_Sign up'))

