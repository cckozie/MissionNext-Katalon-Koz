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

// Write results to text file
outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Journey Register.txt')

outFile.write('Testing Journey Register\n')

// Define path to tooltip icons and text images
path = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Journey Profile/'

// Define the Xpath for each of the tooltip test objects
tooltipXpaths = [('username') : '//div[@id=\'main_fields\']/div[1]/div/img', ('email') : '//div[@id=\'main_fields\']/div[2]/div/img'
    , ('password') : '//div[@id=\'main_fields\']/div[3]/div/img', ('first_name') : '//*[@id=\'group1\']/div[1]/div[1]/img'
    , ('last_name') : '//*[@id=\'group1\']/div[2]/div[1]/img', ('learn_about_us') : '//*[@id=\'group1\']/div[6]/div[1]/img'
    , ('terms_and_conditions') : '//*[@id="group1"]/div[8]/div[3]/img']

tooltips = [('email') : 'img_Email_field-tooltip', ('first_name') : 'img_First Name_field-tooltip', ('last_name') : 'img_Last Name_field-tooltip'
    , ('learn_about_us') : 'img_Learn About Us_field-tooltip', ('password') : 'img_Password_field-tooltip', ('terms_and_conditions') : 'img_Terms and Conditions_field-tooltip'
    , ('username') : 'img_Username_field-tooltip']

// Define the names of the tooltip fields
tooltips = ['username', 'email', 'password', 'first_name', 'last_name', 'learn_about_us', 'terms_and_conditions']

// Define the required field missing error message test objects (Currently not used. Awaiting decisions on desired behavior)
requiredFieldMsgs = [('div_Password must be at least 6 characters long-Msg') : ' ', ('div_Please enter a valid email address-Msg') : ' '
    , ('div_The Fiirst Name field is required-Msg') : ' ', ('div_The cell phone field is required-Msg') : ' ', ('div_The last name field is required-Msg') : ' '
    , ('div_The terms and conditions field is required-Msg') : ' ', ('div_Username must be at least 6 characters long-Msg') : ' ']

// Open the Journey login page
WebUI.callTestCase(findTestCase('_Functions/Open Journey Login Page'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.comment('This is my message')

WebUI.delay(3)

// Prep for selenium and sikulix funtions
WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

Screen s = new Screen()

WebUI.click(findTestObject('Journey Candidate Profile/Login/span_Apply Now - Journey'))

WebUI.click(findTestObject('Journey Candidate Profile/Register/button_Sign up'))

// Test for username, email, and password required messages
WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_Username must be at least 6 characters long-Msg'))

WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_Please enter a valid email address-Msg'))

WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_Password must be at least 6 characters long-Msg'))

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Username'), GlobalVariable.username)

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Email'), GlobalVariable.username)

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Password'), GlobalVariable.password)

WebUI.click(findTestObject('Journey Candidate Profile/Register/button_Sign up'))

// Test for first name, last name, and phone number required messages
WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_The Fiirst Name field is required-Msg'))

WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_The last name field is required-Msg'))

WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_The cell phone field is required-Msg'))

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Password'), GlobalVariable.password)

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_First Name'), GlobalVariable.username)

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Last Name'), GlobalVariable.last_name)

WebUI.selectOptionByLabel(findTestObject('Journey Candidate Profile/Register/select_Country'), GlobalVariable.zip, false)

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Best Phone Number'), GlobalVariable.phone_number)

WebUI.click(findTestObject('Journey Candidate Profile/Register/button_Sign up'))

WebUI.setText(findTestObject('Journey Candidate Profile/Register/input_Password'), GlobalVariable.password)

if (GlobalVariable.prefer_text) {
    WebUI.click(findTestObject('Journey Candidate Profile/Register/checkbox_Prefer Text Message'))
}

WebUI.selectOptionByLabel(findTestObject('Journey Candidate Profile/Register/select_Learn About Us'), GlobalVariable.learn_about, 
    false)

WebUI.setText(findTestObject('Journey Candidate Profile/Register/textarea_Other Comment'), GlobalVariable.other_comment)

WebUI.click(findTestObject('Journey Candidate Profile/Register/button_Sign up'))

// Test for terms and conditions required messages
WebUI.verifyElementVisible(findTestObject('Journey Candidate Profile/Register/div_The terms and conditions field is required-Msg'))

for (def tooltip : tooltips) {
    if (tooltip == 'username') {
        WebUI.scrollToElement(findTestObject('Journey Candidate Profile/Register/h1_JOURNEY CANDIDATE REGISTRATION'), 0)
    } else {
        if (tooltip == 'learn_about_us') {
            WebUI.scrollToElement(findTestObject('Journey Candidate Profile/Register/img_Learn About Us_field-tooltip'), 0)
        }
    }
    
    xpath = tooltipXpaths.get(tooltip)

    WebElement element = driver.findElement(By.xpath(xpath))

    action.moveToElement(element).perform()

    WebUI.delay(1)

    myImage = (((path + tooltip) + '_tooltip') + '.png')

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
}

WebUI.closeBrowser()

