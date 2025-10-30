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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

///#########################################
///#########################################
//	Test access to tabs when profile was previously completed
//	Add test for external links on all tabs
//	Test logging, including image match percentages
///#########################################
///#########################################

// Set to page(s) to run, or empty or 'All' to run all pages
pages = []

if(GlobalVariable.testSuiteRunning) {
	pages = []
}

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '3ja') {
    println('The Execution Profile must be set to "Journey Affiliate"')

    System.exit(0)
}

domain = GlobalVariable.domain

called = false
if (binding.hasVariable('varCalled')) {
	called = varCalled
}

if(pages.size() == 0 || 'All' in pages) {
	myTabs = 'All'
} else {
	myTabs = pages
}

myTestCase = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)


if(1 == 2) { 	//GlobalVariable.testSuiteRunning || binding.hasVariable('varCalled')) {
	outFile = GlobalVariable.outFile
	outFile.append('\nTesting  ' + myTestCase + ' on ' + domain + '.\n\n')
} else {
	outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')
	outFile.write('Testing  ' + myTestCase + ' on ' + domain + '.\n\n')
	GlobalVariable.outFile = outFile
}

//.First check to see if access has been granted to the user
granted = WebUI.callTestCase(findTestCase('Admin/Test for Access Granted'), [varUsername : username], FailureHandling.STOP_ON_FAILURE)

if(granted == false || granted == null) {
	outText = '##### - ' + username + ' has NOT been granted access. Complete profile script is terminated.'
	
	outFile.append(outText + '\n')
	
	WebUI.closeBrowser()
	 
	GlobalVariable.testCaseErrorFlag = true
	
	System.exit(0)
}


url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
	WebUI.callTestCase(findTestCase('_Functions/Journey Affiliate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}


/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Contact Information' in pages) {
	dashboard = WebUI.verifyElementPresent(findTestObject('Journey Affiliate/Dashboard/a_My Profile'), 2,
		FailureHandling.OPTIONAL)
	
	if(dashboard) {
			WebUI.click(findTestObject('Object Repository/Journey Affiliate/Dashboard/a_My Profile'))
	}
	
	// Complete the Contact Info tab
	mailing_address = '1250 Waconia Pkwy N'
	
	contact_phone = '952-442-1703'
	
	city = 'Waconia'
	
	state = 'Minnesota'
	
	zip = '55387'
	
	WebUI.callTestCase(findTestCase('Journey Affiliate/Tabs/Set Contact Information'), [ ('varMailing_address') : mailing_address,
		('varContact_phone') : contact_phone, ('varCity') : city, ('varState') : state, ('varZip') : zip], FailureHandling.CONTINUE_ON_FAILURE)
}


/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Agency' in pages) {
	dashboard = WebUI.verifyElementPresent(findTestObject('Journey Affiliate/Dashboard/a_My Profile'), 2,
		FailureHandling.OPTIONAL)
	
	if(dashboard) {
			WebUI.click(findTestObject('Object Repository/Journey Affiliate/Dashboard/a_My Profile'))
	}
	
	// Complete the Account Info tab
	settings = 'Yes'
	
	WebUI.callTestCase(findTestCase('Journey Affiliate/Tabs/Set Agency'), [('varSettings') : settings], FailureHandling.CONTINUE_ON_FAILURE)
	
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
settingsVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Affiliate/Tabs/a_Settings'), FailureHandling.OPTIONAL)

if(settingsVisible && (pages.size() == 0 || 'All' in pages || 'Settings' in pages)) {
	dashboard = WebUI.verifyElementPresent(findTestObject('Journey Affiliate/Dashboard/a_My Profile'), 2,
		FailureHandling.OPTIONAL)
	
	if(dashboard) {
			WebUI.click(findTestObject('Object Repository/Journey Affiliate/Dashboard/a_My Profile'))
	}
	
	//Complete the Service Options tab
	
	match_rate = '70'
	
	profile_years = ['2024', '2025', '2026']
	
	visible_to_public = ['No']
	
	WebUI.callTestCase(findTestCase('Journey Affiliate/Tabs/Set Settings'), 
		[('varMatch_rate') : match_rate, ('varProfile_years') : profile_years, ('varVisible_to_public') : visible_to_public], FailureHandling.CONTINUE_ON_FAILURE)
}	
/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

WebUI.closeBrowser()

if(pages == null || pages.size() == 0 || pages == 'All') {
	
	WebUI.callTestCase(findTestCase('_Functions/Journey Affiliate Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.delay(2)
	
	myURL = WebUI.getUrl()
	
	if(myURL.contains('journey.missionnext.org/dashboard')) {
		outText = '\n***** The login after registering as an Journey Affiliate was successful. *****'
		
	} else {
		outText = '\n##### The login after registering as an Journey Affiliate was NOT successful. #####'
		KeywordUtil.markError('\n' + outText)
		GlobalVariable.testCaseErrorFlag = true
	}
	outFile.append(outText + '\n')
		
}
	
WebUI.closeBrowser()


