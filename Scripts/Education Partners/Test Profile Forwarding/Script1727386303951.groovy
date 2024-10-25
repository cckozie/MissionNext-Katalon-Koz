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

//////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
// Remove the loop and look for both emails after forwarding it	- Completed 10/21/24
// Verify education partner exists and create profile is not
//////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Forward Candidate Profile: Issues 52 and 91 on ' + 
GlobalVariable.domain) + '.txt')

GlobalVariable.outFile = outFile

outFile.write('')

toEmail = 'cktest01mn@gmail.com'

fromEmails = ['do_not_reply@info.missionnext.org', 'Chris.Kosieracki@missionnext.org' //text case is important
]

subjects = ['Candidate Profile for Taiwo Adejinmi from MissionNext', 'Candidate Profile for Taiwo Adejinmi from MissionNext']

searchKeys = ['NA', 'NA', 'NA']

outFile.append(((('\nTesting the forwarding of a candidate profile to ' + toEmail) + ' on ') + GlobalVariable.domain) + 
    '.\n')

//================================== Delete any existing emails ====================================
//WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

//Log in as education partner, or create the profile if none exits
WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

profileExists = WebUI.verifyTextPresent('My Dashboard', false, FailureHandling.OPTIONAL)

println(profileExists)

if (!(profileExists)) {

	WebUI.callTestCase(findTestCase('Education Partners/Register Education Partner'), [:], FailureHandling.STOP_ON_FAILURE)

}

//Bring up the candicate profile
WebUI.navigateToUrl(('https://education.' + GlobalVariable.domain) + '/candidate/14992')

//Click the print/forward button and switch to the new tab
WebUI.click(findTestObject('Object Repository/Education Partner Profile/Candidate Profile/button_PrintForward Profile'))

WebUI.delay(3)

WebUI.switchToWindowIndex(1)

//Enter the email details and send
WebUI.setText(findTestObject('Object Repository/Education Partner Profile/Candidate Profile/input_Mail to'), toEmail)

WebUI.setText(findTestObject('Object Repository/Education Partner Profile/Candidate Profile/textarea_Note'), ('Testing issues #52 and #91 on ' + 
    GlobalVariable.domain) + '.')

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Candidate Profile/btn_Send'))

//Test for the email sent message
msgFound = WebUI.verifyTextPresent('An email with a link to this profile was sent.', false, FailureHandling.OPTIONAL)

if (msgFound) {
    timeStart = new Date()

    outText = 'The email sent message was found.'

    println(outText)

    outFile.append(outText + '\n')

    WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : fromEmails, ('varSubjectKey') : subjects
            , ('varSearchKey') : searchKeys], FailureHandling.STOP_ON_FAILURE)

    timeStop = new Date()

    float duration = (timeStop.getTime() - timeStart.getTime()) / 1000

    if (GlobalVariable.returnCode == 'found') {
        outText = (('An email with the candidate profile was found. Wait time was ' + duration.toString()) + ' seconds.\n TEST PASSED')
    } else {
        outText = (('An email with the candidate profile was NOT found after waiting ' + duration.toString()) + ' seconds.\n TEST FAILED')
    }
    
    println(outText)

    outFile.append(outText + '\n')
} else {
    outText = 'The email sent message was NOT found.\n TEST FAILED'

    println(outText)

    outFile.append(outText + '\n')
}

WebUI.closeBrowser()

WebUI.closeBrowser()

