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
import java.io.File as File
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

domain = GlobalVariable.domain

url = 'https://ad.' + domain

sites = ['Journey', 'Education', 'QuickStart']

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
	'/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
} else {
	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')

GlobalVariable.outFile = outFile

outFile.write('Running ' + myTestCase + ' on ' + domain + '\n\n')

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Object Repository/Admin/Ad Fix Last Login Dates/a_Update Table to Examine Profiles for Completeness'))

WebUI.delay(1)

for(site in sites) {
	
	outFile.append('Running fix last login dates on ' + site + '\n')

	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Fix Last Login Dates/select_Website'), site, false)
	
	//WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Fix Last Login Dates/select_Role'), role, false)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad Fix Last Login Dates/button_Go'))
	
	WebUI.waitForPageLoad(90)
	
	msgFound = WebUI.verifyTextPresent('For Web Application:', false, FailureHandling.OPTIONAL)
	
	msg = 'found.'
	
	if(!msgFound) {
		msg = 'NOT ' + msg
	}
	
	outFile.append('Completion message was ' + msg + ' for ' + site + '\n')
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad Fix Last Login Dates/a_Start Again'))
}

WebUI.closeBrowser()