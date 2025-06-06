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

println(varProfile)
if(varProfile != '') {
	new ExecutionProfilesLoader().loadProfile(varProfile)
	
	site = varProfile[0..varProfile.indexOf(' ') - 1]
	
	username = GlobalVariable.username
	
	password = GlobalVariable.password
	
} else {
	site = varSite
	
	username = varUsername
	
	password = varPassword
}

//Only open a browser if there is not already one open
try {
	windowIndex = WebUI.getWindowIndex()
} catch(e1) {
	windowIndex = -1
}
//windowIndex = WebUI.getWindowIndex()

if(windowIndex < 0) {

	WebUI.openBrowser('')
	
	WebUI.maximizeWindow()
}

if(GlobalVariable.mobileScreen) {
	
	WebUI.setViewPortSize(600, 1200)
	
	WebUI.delay(1)
}

WebUI.navigateToUrl('https://' + site + '.' + GlobalVariable.domain + '/' + site + '-home/login-here/')

WebUI.setText(findTestObject('Object Repository/' + site + ' Candidate Profile/Login/input_Username'), username)

WebUI.setEncryptedText(findTestObject('Object Repository/' + site + ' Candidate Profile/Login/input_Password'), password)

WebUI.click(findTestObject('Object Repository/' + site + ' Candidate Profile/Login/button_Log In'))

