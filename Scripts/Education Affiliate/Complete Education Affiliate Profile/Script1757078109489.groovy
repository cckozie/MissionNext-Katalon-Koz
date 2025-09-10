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

if(username[-3..-1] != '9ea') {
    println('The Execution Profile must be set to "Education Affiliate"')

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
	 
	System.exit(0)
}


url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
	WebUI.callTestCase(findTestCase('_Functions/Education Affiliate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	dashboard = WebUI.verifyElementPresent(findTestObject('Education Affiliate/Dashboard/a_My Profile'), 2,
		FailureHandling.OPTIONAL)
	
	if(dashboard) {
			WebUI.click(findTestObject('Object Repository/Education Affiliate/Dashboard/a_My Profile'))
	}
	
	// Complete the Contact Info tab
	recruiting_fax = '952-442-1703'
	
	mailing_address = '1250 Waconia Pkwy N'
	
	WebUI.callTestCase(findTestCase('Education Affiliate/Tabs/Set Contact Info'), [('varRecruiting_fax') : recruiting_fax
			, ('varMailing_address') : mailing_address], FailureHandling.CONTINUE_ON_FAILURE)
}


/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Account Information' in pages) {
	dashboard = WebUI.verifyElementPresent(findTestObject('Education Affiliate/Dashboard/a_My Profile'), 2,
		FailureHandling.OPTIONAL)
	
	if(dashboard) {
			WebUI.click(findTestObject('Object Repository/Education Affiliate/Dashboard/a_My Profile'))
	}
	
	// Complete the Account Info tab
	settings = 'Yes'
	
	WebUI.callTestCase(findTestCase('Education Affiliate/Tabs/Set Account Information'), [('varSettings') : settings], FailureHandling.CONTINUE_ON_FAILURE)
	
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
settingsVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/Education Affiliate/Tabs/a_Settings'), FailureHandling.OPTIONAL)

if(settingsVisible && (pages.size() == 0 || 'All' in pages || 'Settings' in pages)) {
	dashboard = WebUI.verifyElementPresent(findTestObject('Education Affiliate/Dashboard/a_My Profile'), 2,
		FailureHandling.OPTIONAL)
	
	if(dashboard) {
			WebUI.click(findTestObject('Object Repository/Education Affiliate/Dashboard/a_My Profile'))
	}
	
	//Complete the Service Options tab
	profile_years = ['2024', '2025', '2026']
	
	visible_to_public = ['No']
	
	WebUI.callTestCase(findTestCase('Education Affiliate/Tabs/Set Settings'), 
		[('varProfile_years') : profile_years, ('varVisible_to_public') : visible_to_public], FailureHandling.CONTINUE_ON_FAILURE)
}	
/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

WebUI.closeBrowser()

if(1 == 1 || pages == null || pages.size() == 0 || pages == 'All') {
	
	WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	WebUI.delay(2)
	
	found = WebUI.verifyTextPresent('HELLO, THE CCK TEST09 EDUCATION AFFILATE', false, FailureHandling.OPTIONAL)
	
	println(found)
	
	if(found) {
		outText = '\n***** The login after registering as an Education Affiliate was successful. *****'
		
	} else {
		outText = '\n##### The login after registering as an Education Affiliate was NOT successful. #####'
		KeywordUtil.markError('\n' + outText)
	}
	outFile.append(outText + '\n')
		
}
	
WebUI.closeBrowser()


