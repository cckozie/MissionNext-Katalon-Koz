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
import org.sikuli.script.*

textMap = ["Username":"Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.", "Email":"Your primary email address and must be unique in our database.", "Password":"The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.", "First Name":"May include your middle initial; enter last name below.", "Last Name":"Family Name", "How did you learn about us?":"It is helpful to know how people are learning about us.", "Terms and Conditions":"Please read and agree with MissionNext Terms and Conditions to continue"]

tooltipText = [('Username') : 'Must be unique; at least 6 characters; contain only lowercase letters; allowable characters: numbers, @, dash, underscore or period, and can be an email address.'
	, ('Email') : 'Your primary email address and must be unique in our database.', ('Password') : 'The password should be at least twelve characters long; should include numbers, letters, capitals; may have special characters (@, #, *, spaces, etc.) and may include a passphrase.'
	, ('First Name') : 'May include your middle initial; enter last name below.', ('Last Name') : 'Family Name'
	, ('How did you learn about us?') : 'It is helpful to know how people are learning about us.'
	, ('Terms and Conditions') : 'Please read and agree with MissionNext Terms and Conditions to continue']


println(textMap.get('Username'))

for (it in tooltipText) {
	myKey = it.key
	myText = it.value
	actualText = textMap.get(myKey)
	println(myKey + ':' + actualText)
	if(actualText != myText) {
		println('####### ERROR: The tooltip text for ' + myKey + ' should be ' + myText + ' but instead is ' + actualText + '.')
	}
}
