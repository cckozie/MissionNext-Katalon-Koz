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


site = 'Journey'

type = 'Org'

userEmail = 'misty.a.nolan@gmail.com'

orgEmail = 'headquarters@missionnext.org'

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varType') : type],
	 FailureHandling.STOP_ON_FAILURE)

userFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varUsername') : userEmail], 
	FailureHandling.STOP_ON_FAILURE)

orgFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varUsername') : orgEmail], 
	FailureHandling.STOP_ON_FAILURE)

println('\n matchValues\n')
matchValues.each {
	println(it.key + ':' + it.value)
}

println('\n userFieldValues\n')
userFieldValues.each {
	println(it.key + ':' + it.value)
}

println('\n orgFieldValues\n')
orgFieldValues.each {
	println(it.key + ':' + it.value)
}

matchValues.each { 
	println('Testing user ' + it.key + ', org ' + it.value[0])
	orgValues = orgFieldValues.get(it.value[0])
	userValues = userFieldValues.get(it.key)
	println('org = ' + orgValues + ':' + ' user = ' + userValues)
	for(userValue in userValues) {
		if(userValue in orgValues || 'Open' in orgValues || 'Open' in userValues) {
			println('found match on ' + userValue)
		}
	}
}