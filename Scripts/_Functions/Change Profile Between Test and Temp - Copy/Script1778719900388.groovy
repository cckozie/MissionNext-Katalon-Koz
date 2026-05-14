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

// Assumes user is logged in and the My Profile tab is being displayed

outFile = GlobalVariable.outFile

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
	WebUI.click(findTestObject('Object Repository/' + site + ' Partner Profile/Dashboard/a_My Dashboard'))
	WebUI.waitForPageLoad(30)
} else {
	outFile.append('\nERROR: User is not on the Dashboard.\n')
	return 'error: Not on the Dashboard.'
}

isPartner = WebUI.verifyTextPresent('Manage Folders', false, FailureHandling.OPTIONAL)

if(isPartner) {
	
	organization = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Organization'), 'value')

	outFile.append('organization is ' + organization + '\n')
	
	contactFirstName = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Key Contact First Name'), 'value')
	
	tempOrg = organization.replace('TEST', 'TEMP')
	
	outFile.append('tempOrg is ' + tempOrg + '\n')

	tempName = contactFirstName.replace('TEST', 'TEMP')
	
	WebUI.setText(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Organization'), tempOrg)
	
	WebUI.setText(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Key Contact First Name'), tempOrg)
	
	object = 'Object Repository/' + site + ' Partner Profile/Tabs/btn_Complete Submit'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.waitForPageLoad(30)
	
	newOrg = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Organization'), 'value')

	newName  = WebUI.getAttribute(findTestObject('Object Repository/' + site + ' Partner Profile/Tabs/Contact Info/input_Key Contact First Name'), 'value')
	
	outFile.append('Changed Organization from ' + organization + ' to ' + newOrg + '.\n')
	
	outFile.append('Changed Key Contact First Name from ' + contactFirstName + ' to ' + newName + '.\n')
	
	println('tempOrg is ' + tempOrg + ' and length is ' + tempOrg.length())
	
	println('newOrg is ' + newOrg + ' and length is ' + newOrg.length())
	
	println('tempName is ' + tempName + ' and length is ' + tempName.length())
	
	println('newName is ' + newName + ' and length is ' + newName.length())
	
	if(newOrg == tempOrg && newName == tempName) {
		retCode = 'success'
	}	else {
		retCode = 'error: Values not updated correctly'
	}
	return retCode
}
	