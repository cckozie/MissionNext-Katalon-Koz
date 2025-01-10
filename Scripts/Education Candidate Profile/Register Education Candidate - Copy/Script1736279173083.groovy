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

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest04ec') {
    println('The Execution Profile must be set to "Education Candidate"')

    System.exit(0)
}

///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//  Need to modify for new password tooltip text
//	Need to call other test cases for the Experience, Availability, Service/Comment, and Your Ministry Prefs tabs
///////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
domain = GlobalVariable.domain

username = GlobalVariable.username

// Write results to text file
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Register Education Candidate on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write('Testing Register Education Candidate on ' + domain + '.\n')

//================================== Delete the user ===============================================
//WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

// Define path to tooltip icons and text images
path = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/journey candidate/journey contact registration/'

// Define the Xpath for each of the tooltip test objects
tooltipXpaths = [('username') : '//div[@id=\'main_fields\']/div[1]/div/img', ('email') : '//div[@id=\'main_fields\']/div[2]/div/img'
    , ('password') : '//div[@id=\'main_fields\']/div[3]/div/img', ('first_name') : '//*[@id=\'group1\']/div[1]/div[1]/img'
    , ('last_name') : '//*[@id=\'group1\']/div[2]/div[1]/img', ('learn_about_us') : '//*[@id=\'group1\']/div[6]/div[1]/img'
    , ('terms_and_conditions') : '//*[@id="group1"]/div[8]/div[3]/img']

// Define the names of the tooltip fields
tooltips = ['username', 'email', 'password', 'first_name', 'last_name', 'learn_about_us', 'terms_and_conditions']

// Define the required field missing error message test objects (Currently not used. Awaiting decisions on desired behavior)
requiredFieldMsgs = [('div_Password must be at least 6 characters long-Msg') : ' ', ('div_Please enter a valid email address-Msg') : ' '
    , ('div_The Fiirst Name field is required-Msg') : ' ', ('div_The cell phone field is required-Msg') : ' ', ('div_The last name field is required-Msg') : ' '
    , ('div_The terms and conditions field is required-Msg') : ' ', ('div_Username must be at least 6 characters long-Msg') : ' ']

// Open the Education login page
WebUI.callTestCase(findTestCase('_Functions/Open Education Candidate Login Page'), [:], FailureHandling.STOP_ON_FAILURE)

// Prep for selenium and sikulix funtions
WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

WebUI.click(findTestObject('Education Candidate Profile/Education Login/btn_Apply for Education'))

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Login/a_Register Now'))

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/input_Username'), '=====> WAITING FOR SIKULIX <=====')

Screen s = new Screen()

WebUI.clearText(findTestObject('Education Candidate Profile/Education Register/input_Username'))

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Register/button_Sign up'))
// Test for username, email, and password required messages
WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_Username must be at least 6 characters long-Msg'))

WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_Please enter a valid email address-Msg'))

WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_Password must be at least 6 characters long-Msg'))

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/input_Username'), GlobalVariable.username)

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/input_Email'), GlobalVariable.email)

WebUI.setEncryptedText(findTestObject('Education Candidate Profile/Education Register/input_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Education Candidate Profile/Education Register/button_Sign up'))

// Test for first name, last name, and phone number required messages
WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_The Fiirst Name field is required-Msg'))

WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_The last name field is required-Msg'))

WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_The cell phone field is required-Msg'))

WebUI.setEncryptedText(findTestObject('Education Candidate Profile/Education Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/input_First Name'), GlobalVariable.username)

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/input_Last Name'), GlobalVariable.last_name)

WebUI.selectOptionByLabel(findTestObject('Education Candidate Profile/Education Register/select_Country'), GlobalVariable.country, 
    false)

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/input_Best Phone Number'), GlobalVariable.phone_number)

WebUI.click(findTestObject('Education Candidate Profile/Education Register/button_Sign up'))

WebUI.setEncryptedText(findTestObject('Education Candidate Profile/Education Register/input_Password'), GlobalVariable.password)

if (GlobalVariable.prefer_text) {
    WebUI.click(findTestObject('Education Candidate Profile/Education Register/checkbox_Prefer Text Message'))
}

WebUI.selectOptionByLabel(findTestObject('Education Candidate Profile/Education Register/select_Learn About Us'), GlobalVariable.learn_about, 
    false)

WebUI.setText(findTestObject('Education Candidate Profile/Education Register/textarea_Other Comment'), GlobalVariable.other_comment)

WebUI.click(findTestObject('Education Candidate Profile/Education Register/button_Sign up'))

// Test for terms and conditions required messages
WebUI.verifyElementVisible(findTestObject('Education Candidate Profile/Education Register/div_The terms and conditions field is required-Msg'))

WebUI.scrollToElement(findTestObject('Education Candidate Profile/Education Register/a_Privacy Policy'), 3)

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Register/a_Privacy Policy'))

WebUI.switchToWindowIndex(1)

WebUI.delay(1)

newUrl = WebUI.getUrl()

if (newUrl.indexOf('org/privacy') < 0) {
    println('######## Failed to find Privacy page')
}

WebUI.delay(1)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

//WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Register/a_Terms and Conditions'))
WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Register/a_Terms and Conditions'))

WebUI.switchToWindowIndex(1)

WebUI.delay(1)

newUrl = WebUI.getUrl()

if (newUrl.indexOf('org/terms') < 0) {
    println('######## Failed to find Terms and Conditions page')
}

WebUI.delay(1)

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

WebUI.delay(1)

WebUI.scrollToElement(findTestObject('Education Candidate Profile/Education Register/h1_MISSIONNEXT EDUCATION CANDIDATE REGISTRATION'), 2)

// Test for privacy policy page
// Use Sikulix to verify the tooltip messages are displayed. % match numbers are sent to output file
for (def tooltip : tooltips) {
    if (tooltip == 'learn_about_us') {
        WebUI.scrollToElement(findTestObject('Education Candidate Profile/Education Register/img_Learn About Us_field-tooltip'),0)
    }
    
    xpath = tooltipXpaths.get(tooltip)

    WebElement element = driver.findElement(By.xpath(xpath))

    action.moveToElement(element).perform()

    WebUI.delay(1)

    myImage = (((path + tooltip) + '_tooltip') + '.png')

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

            outText = ('+++++++++++++++ Found tooltip text for ' + tooltip)

            println(outText)

            pctVal = new BigDecimal(pct)

            pctVal = (pctVal * 100).intValue()

            //			pctVal = pct.replace('.','').toInteger() *10
            outFile.append(((outText + ' : ') + pctVal) + '%\n')
        } else {
            outText = ('----------- Unable to find tooltip text for ' + tooltip)

            println(outText)

            outFile.append(outText + '\n')
        }
    } else {
        outText = ('Unable to find image file ' + myImage)

        outFile.append(outText + '\n')

        println(outText)
    }
}

WebUI.setEncryptedText(findTestObject('Education Candidate Profile/Education Register/input_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Register/checkbox_Terms and Conditions'))

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Education Register/button_Sign up'))

// Complete the required entries on the Contact Info tab
WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Contact Info'), [:], FailureHandling.STOP_ON_FAILURE)

// Complete the required entries on the Situation tab
WebUI.callTestCase(findTestCase('Education Candidate Profile/Tabs/Set Situation'), [:], FailureHandling.STOP_ON_FAILURE)

System.exit(1)

WebUI.callTestCase(findTestCase('Recorded Scripts/Education Candidate Additional Tabs'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.verifyTextPresent('Thank you for submitting your profile on MissionNext Education!', false)

