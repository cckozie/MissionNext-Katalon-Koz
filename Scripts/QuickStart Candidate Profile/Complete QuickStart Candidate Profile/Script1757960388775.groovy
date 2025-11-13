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
import javax.swing.*;
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration


// Set to page(s) to run, or empty or 'All' to run all pages
//pages = ['Experience','IT Skills and Interest']
pages = []


if(GlobalVariable.testSuiteRunning) {
	pages = []
}

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if(username[-3..-1] != '2qs') {
    println('The Execution Profile must be set to "QuickStart Candidate"')

    System.exit(0)
}

domain = GlobalVariable.domain

myTestCase = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

if(1 == 2) { 	//GlobalVariable.testSuiteRunning || binding.hasVariable('varCalled')) {
	outFile = GlobalVariable.outFile
	outFile.append('\nTesting  ' + myTestCase + ' on ' + domain + '.\n\n')
} else {
	outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')
	outFile.write('Testing  ' + myTestCase + ' on ' + domain + '.\n\n')
	GlobalVariable.outFile = outFile
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
    WebUI.callTestCase(findTestCase('_Functions/QuickStart Candidate Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

dashboard = WebUI.verifyElementPresent(findTestObject('QuickStart Candidate Profile/Dashboard/a_My Profile'), 2, FailureHandling.OPTIONAL)

if (dashboard) {
    WebUI.click(findTestObject('Object Repository/QuickStart Candidate Profile/Dashboard/a_My Profile'))
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Name & Preferences' in pages) {
// Complete the Name & Preferences tab
	
	preferred_regions = ['Caribbean', 'Central America', 'North America']
	
	time_commitments = ['One year to two years', 'Long Term']
	
	availability = 'Within 12 months' 
	
	paid_and_volunteer_positions = ['Position requires raising some support', 'A salary provided; enough to live locally']
	
	WebUI.callTestCase(findTestCase('QuickStart Candidate Profile/Tabs/Set Name and Preferences'), [('varPreferred_regions') : preferred_regions,
		('varTime_commitments') : time_commitments,	('varAvailability') : availability, ('varPaid_and_volunteer_positions') : paid_and_volunteer_positions]
	, FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	// Complete the Contact Info tab
	country = 'United States'
	
	country_of_citizenship = 'United States'
	
	comments = 'I am looking forward to finding a good mission opportunity'
		
	WebUI.callTestCase(findTestCase('QuickStart Candidate Profile/Tabs/Set Contact Info'), [('varCountry') : country, ('varComments') : comments],
		 FailureHandling.CONTINUE_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Ministry Positions' in pages) {
	if(!GlobalVariable.fastPath) {
		// Complete the Your Ministry Prefs tab
		WebUI.click(findTestObject('Object Repository/QuickStart Candidate Profile/Tabs/a_Ministry Positions'))
		
		// Get the job categories and preferences from /Users/cckozie/Documents/MissionNext/QuickStart Candidate Forms/Ministry Positions.csv
		categories = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
		[('varFileName') : 'QuickStart Candidate Forms/Ministry Positions.csv', ('varSelections') : 'Job Categories'], FailureHandling.STOP_ON_FAILURE)
		
		positions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
			[('varFileName') : 'QuickStart Candidate Forms/Ministry Positions.csv', ('varSelections') : 'Preferred Position(s)'], FailureHandling.STOP_ON_FAILURE)
		
	} else {
		categories = ''
		positions = ''
	}
	
	WebUI.callTestCase(findTestCase('QuickStart Candidate Profile/Tabs/Set Ministry Positions'), [('varJob_categories') : categories
		, ('varPreferred_positions') : positions], FailureHandling.CONTINUE_ON_FAILURE)
}

if(pages == null || pages.size() == 0 || pages == 'All') {
	verifyCategories()
}

WebUI.closeBrowser()


if(pages == null || pages.size() == 0 || pages == 'All') {
	
	WebUI.callTestCase(findTestCase('_Functions/QuickStart Candidate Login'), [:], FailureHandling.CONTINUE_ON_FAILURE)
	
	myURL = WebUI.getUrl()
	
	println(myURL)
	
	userCaps = username.toUpperCase()
	
	found = WebUI.verifyTextPresent('HELLO, ' + userCaps + ' TEST KOSIERACKI', false, FailureHandling.OPTIONAL)
	
//	if(myURL.contains('quickstart.missionnext.org/profile?requestUri=/dashboard')) {
	if(found) {
		outText = '\n+++ QuickStart Candidate login after profile creation was successful.\n'
		
		outFile.append(outText)
		
		verifyCategories()
		
	} else {
		outText = '\n--- QuickStart Candidate login after profile creation failed.\n'
		
		outFile.append(outText)
	}
	
	retArray = WebUI.callTestCase(findTestCase('_Functions/Test for App Assigned and Correct Expiration Date'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)
	println(retArray)
	if((retArray[0] == 'Journey' || retArray[0] == 'QuickStart') && retArray[1] == 'Candidate' && retArray[3] == true) {
		prefix = '+++++ '
		suffix = ' as expected.'
	} else {
		prefix = '##### ERROR:'
		suffix = '.'
	}
	println(prefix + 'Site is ' + retArray[0] + ', role is ' + retArray[1] + ', end date is ' + retArray[2] + suffix)
}

WebUI.closeBrowser()

def verifyCategories() {
	categories = ['ADD: SUPPORT PROFESSIONAL', 'ADD: BUSINESS AS MISSION', 'ADD: RELIEF AND DEVELOPMENT']
	
	for(category in categories) {
		
		found = WebUI.verifyTextPresent(category, false, FailureHandling.OPTIONAL)
		
		if(found) {
			outText = '+++ The text ' + category + ' was found on the dashboard.'
		} else {
			outText = '--- The text ' + category + ' was NOT found on the dashboard.'
		}
		
		outFile.append(outText + '\n')
	}

}
