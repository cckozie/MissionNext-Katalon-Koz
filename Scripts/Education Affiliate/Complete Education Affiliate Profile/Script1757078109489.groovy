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

///#########################################
///#########################################
//	Test access to tabs when profile was previously completed
//	Add test for external links on all tabs
//	Test logging, including image match percentages
///#########################################
///#########################################

// Set to page(s) to run, or empty or 'All' to run all pages
pages = ['Settings']

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
/*
//Check to see if we're writing printed output to a file
writeFile = false

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}
*/
if(pages.size() == 0 || 'All' in pages) {
	myTabs = 'All'
} else {
	myTabs = pages
}

/*
if(!writeFile) {
	outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Education Affiliate Profile on ' +
	domain + ' - ' + myTabs + '.txt')
	
	GlobalVariable.outFile = outFile
	
	outFile.write(('Testing Complete Education Affiliate Profile on ' + domain) + '\n')
} else {
	outFile.append(('\nTesting Complete Education Affiliate Profile on ' + domain) + '\n')
}
*/

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf('/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
} else {
	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')

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

dashboard = WebUI.verifyElementPresent(findTestObject('Education Affiliate/Dashboard/a_My Profile'), 2,
	FailureHandling.OPTIONAL)

if(dashboard) {
		WebUI.click(findTestObject('Object Repository/Education Affiliate/Dashboard/a_My Profile'))
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	// Complete the Contact Info tab
	recruiting_fax = '952-442-1703'
	
	mailing_address = '1250 Waconia Pkwy N'
	
	WebUI.callTestCase(findTestCase('Education Affiliate/Tabs/Set Contact Info'), [('varRecruiting_fax') : recruiting_fax
			, ('varMailing_address') : mailing_address], FailureHandling.CONTINUE_ON_FAILURE)
}


/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Account Information' in pages) {
	// Complete the Account Info tab
	settings = 'Yes'
	
	WebUI.callTestCase(findTestCase('Education Affiliate/Tabs/Set Account Information'), [('varSettings') : settings], FailureHandling.CONTINUE_ON_FAILURE)
	
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
settingsVisible = WebUI.verifyElementVisible(findTestObject('Object Repository/Education Affiliate/Tabs/a_Settings'), FailureHandling.OPTIONAL)

if(settingsVisible && (pages.size() == 0 || 'All' in pages || 'Settings' in pages)) {
	//Complete the Service Options tab
	profile_years = ['2024', '2025', '2026']
	
	visible_to_public = ['No']
	
	WebUI.callTestCase(findTestCase('Education Affiliate/Tabs/Set Settings'), 
		[('varProfile_years') : profile_years, ('varVisible_to_public') : visible_to_public], FailureHandling.CONTINUE_ON_FAILURE)
}
	
	
