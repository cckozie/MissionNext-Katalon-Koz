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

// ----------- THIS BEGAN AS A RECORDED SCRIPT THEN TWEAKED ---------------------
userProfile = 'Education Affiliate 09'

// Load the user's execution profile
new ExecutionProfilesLoader().loadProfile(userProfile)

username = GlobalVariable.username

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
	'/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')
	
//================================== Delete the user ===============================================
WebUI.callTestCase(findTestCase('Admin/Delete User'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

//================================== Delete any existing MN emails ====================================
WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.navigateToUrl('https://education.missionnext.org/education-home/register/')

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/a_Apply Now'))
object = 'Object Repository/Education Affiliate/Register/a_Apply Now'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.switchToWindowTitle('Education')
System.exit(0)
WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Username'), 
	username)

WebUI.setEncryptedText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Password'), 
    GlobalVariable.password)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Contact First Name'), 
    GlobalVariable.first_name)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Contact Last Name'), 
    GlobalVariable.last_name)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Contact Email'), 
	GlobalVariable.email)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Contact Phone'), 
    GlobalVariable.phone)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_City'), 
	GlobalVariable.city)

//WebUI.selectOptionByValue(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/select_State'), 
//    'Minnesota', true)
object = 'Object Repository/Education Affiliate/Register/select_AlabamaAlaskaArizonaArkansasCaliforn_3be247'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'selectOptionByValue', ('varObject') : object
	, ('varParm1') : GlobalVariable.state], FailureHandling.STOP_ON_FAILURE)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_PostalZip Code'), 
    GlobalVariable.zip)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Organization Full Name'), 
    GlobalVariable.organization_name)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Abbreviation'), 
    GlobalVariable.abbreviation)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Web Address'), 
    GlobalVariable.web_address)

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_MissionNext_registrationgroup-1464701_606ca5'))
object = 'Object Repository/Education Affiliate/Register/input_MissionNext_registrationgroup-1464701_606ca5'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Web Address'))
object = 'Object Repository/Education Affiliate/Register/input_Web Address_registrationgroup-1464701_7a30be'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Board of Directors'), 
    GlobalVariable.web_address)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Statement of Faith'), 
    GlobalVariable.web_address)

WebUI.setText(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/textarea_References'), 
    GlobalVariable.references)

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Partnership Agreement_registrationgro_ccbf5d'))
object = 'Object Repository/Education Affiliate/Register/input_Partnership Agreement_registrationgro_ccbf5d'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/input_Terms and Conditions'))
object = 'Object Repository/Education Affiliate/Register/input_Terms and Conditions_registrationgrou_fd616e'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//WebUI.click(findTestObject('Object Repository/Education Affiliate/Register Education Affiliate/button_Sign up'))
object = 'Object Repository/Education Affiliate/Register/button_Sign up'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

//================================== Wait for the approval pending email for the new education affiliate =========
emailFound = WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : 'chris.kosieracki@missionnext.org'
		, ('varSubjectKey') : 'Approval request', ('varSearchKey') : username], FailureHandling.STOP_ON_FAILURE)

if (emailFound) {
	outText = (('Approval request email for ' + username) + ' was found')

	println(outText)

	outFile.append(outText)

	WebUI.closeBrowser()

	//================================== Grant access for the new education partner ==================================
	WebUI.callTestCase(findTestCase('Admin/Grant Access'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
}
