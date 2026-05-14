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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration

// Assumes user is logged in and the dashboard is being displayed

testCase = RunConfiguration.getExecutionProperties().get("current_testcase")

start = testCase.lastIndexOf('/')

myTestCase = testCase[start + 1..-1]

outFile = GlobalVariable.outFile

outFile.append('\nRunning ' + myTestCase + '.\n')

if(varTestOrTemp.toLowerCase() != 'temp' && varTestOrTemp.toLowerCase() != 'test') {
	outFile('varTestOrTemp is ' + varTestOrTemp + ' but it needs to be either "TEST" or "TEMP".')
	return 'Invalid value ' + varTestOrTemp + ' for varTestOrTemp'
}

url = WebUI.getUrl().toLowerCase()

if(url.contains('journey')) {
	site = 'Journey'
} else if(url.contains('education')) {
	site = 'Education'
} else {
	outFile.append('\nERROR: User is not on Journey or Education.\n')
	return 'error: Not on Journey or Education site.'
}

onDashboard = WebUI.verifyTextPresent('My Dashboard', false, FailureHandling.OPTIONAL)

if(onDashboard) {
	WebUI.click(findTestObject('Object Repository/' + site + ' Partner Profile/Dashboard/a_My Profile'))
	WebUI.waitForPageLoad(30)
} else {
	outFile.append('\nERROR: User is not on the Dashboard.\n')
	return 'error: Not on the Dashboard.'
}

isPartner = WebUI.verifyTextPresent('Manage Folders', false, FailureHandling.OPTIONAL)

if(isPartner) {
	
	organization = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Organization'), 'value')

	contactFirstName = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Key Contact First Name'), 'value')
	
	if(varTestOrTemp.toLowerCase() == 'temp') {
		
		outFile.append('--- Changing ' + site + ' partner to non-test profile.\n')

		tempOrg = organization.replace('TEST', 'TEMP')
	
		tempName = contactFirstName.replace('TEST', 'TEMP')
	
	} else if(varTestOrTemp.toLowerCase() == 'test') { 

		outFile.append('--- Changing ' + site + ' partner to test profile.\n')

		tempOrg = organization.replace('TEMP', 'TEST')
		
		tempName = contactFirstName.replace('TEMP', 'TEST')
	}
	
	WebUI.setText(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Organization'), tempOrg)
	
	WebUI.setText(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Key Contact First Name'), tempOrg)
		
	object = 'Object Repository/' + site + ' Partner Profile/Tabs/btn_Complete Submit'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.waitForPageLoad(30)
	
	newOrg = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Organization'), 'value')

	newName  = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Key Contact First Name'), 'value')
	
	outFile.append('--- Changed Organization from ' + organization + ' to ' + newOrg + '.\n')
	
	outFile.append('--- Changed Key Contact First Name from ' + contactFirstName + ' to ' + newName + '.\n')
	
	println('tempOrg is ' + tempOrg + ' and length is ' + tempOrg.length())
	
	println('newOrg is ' + newOrg + ' and length is ' + newOrg.length())
	
	println('tempName is ' + tempName + ' and length is ' + tempName.length())
	
	println('newName is ' + newName + ' and length is ' + newName.length())
	
	if(newOrg == tempOrg && newName == tempName) {
		retCode = '--- success'
	}	else {
		retCode = '--- error: Values not updated correctly'
	}
	return retCode
	
} else {	// is candidatge
		
	firstName = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Candidate Profile/Tabs/Contact Info/input_First Name'), 'value')
	
	if(varTestOrTemp.toLowerCase() == 'temp') {

		outFile.append('--- Changing ' + site + ' candidate to non-test profile.\n')
		
		tempName = firstName.replace('TEST', 'TEMP')
	
	} else if(varTestOrTemp.toLowerCase() == 'test') {

		outFile.append('--- Changing ' + site + ' candidate to test profile.\n')
		
		tempName = firstName.replace('TEMP', 'TEST')
	}
	
	WebUI.setText(findTestObject('Object Repository/' + site + ' Candidate Profile/Tabs/Contact Info/input_First Name'), tempName)
		
	object = 'Object Repository/' + site + ' Partner Profile/Tabs/btn_Complete Submit'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.waitForPageLoad(30)
	
	newName  = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Candidate Profile/Tabs/Contact Info/input_First Name'), 'value')
	
	outFile.append('--- Changed First Name from ' + firstName + ' to ' + newName + '.\n')
	
	println('tempName is ' + tempName + ' and length is ' + tempName.length())
	
	println('newName is ' + newName + ' and length is ' + newName.length())
	
	if(newName == tempName) {
		retCode = 'success'
	}	else {
		retCode = 'error: Values not updated correctly'
	}
	return retCode

}
	