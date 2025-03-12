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
import com.kms.katalon.core.configuration.RunConfiguration
import java.io.File as File


myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf('/')+1)
myTestCase = myTestCase.substring(0,myTestCase.length()-3)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

outFile.write('Running ' + myTestCase + '\n')

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Education Candidate 14'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Dashboard/a_My Profile'))

WebUI.click(findTestObject('Object Repository/Education Candidate Profile/Tabs/a_Contact Info'))

notFound = WebUI.verifyTextNotPresent('Thank you for completing your Profile.', false, FailureHandling.OPTIONAL)

if(!notFound) {
	outText = 'ERROR: The greenbar text was displayed upon logging in.'
} else {
	outText = 'The greenbar was not displayed upon logging in.'
}
outFile.append(outText + '\n')
println(outText)

object = 'Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : 'Brazil'], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Tabs/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.OPTIONAL)

notFound = WebUI.verifyTextNotPresent('Thank you for completing your Profile.', false, FailureHandling.OPTIONAL)

if(!notFound) {
	outText = 'ERROR: The greenbar text was found after modifying the country.'
} else {
	outText = 'The greenbar was not displayed after modifying the country.'
}
outFile.append(outText + '\n')
println(outText)

object = 'Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Country'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : 'United States'], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Tabs/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.OPTIONAL)

notFound = WebUI.verifyTextNotPresent('Thank you for completing your Profile.', false, FailureHandling.OPTIONAL)

if(!notFound) {
	outText = 'ERROR: The greenbar text was found again after changing to the orginal country.'
} else {
	outText = 'The greenbar was not displayed after changing to the orginal country.'
}
outFile.append(outText + '\n')
println(outText)

object = 'Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Country_of_Citizenship'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : 'Brazil'], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Tabs/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.OPTIONAL)

notFound = WebUI.verifyTextNotPresent('Thank you for completing your Profile.', false, FailureHandling.OPTIONAL)

if(!notFound) {
	outText = 'ERROR: The greenbar text was found after changing the country of residence.'
} else {
	outText = 'The greenbar was not displayed after changing the country of residence.'
}
outFile.append(outText + '\n')
println(outText)

object = 'Object Repository/Education Candidate Profile/Tabs/Contact Info/select_Country_of_Citizenship'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue',
	('varObject') : object, ('varParm1') : 'United States'], FailureHandling.STOP_ON_FAILURE)

object = 'Education Candidate Profile/Tabs/btn_Submit'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.OPTIONAL)

notFound = WebUI.verifyTextNotPresent('Thank you for completing your Profile.', false, FailureHandling.OPTIONAL)

if(!notFound) {
	outText = 'ERROR: The greenbar text was found after changing the country of residence back to the original.'
} else {
	outText = 'The greenbar was not displayed after changing the country of residence back to the original.'
}
outFile.append(outText + '\n')
println(outText)

