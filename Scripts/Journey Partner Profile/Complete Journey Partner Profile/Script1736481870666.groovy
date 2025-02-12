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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import javax.swing.*

// Set to page(s) to run, or empty or 'All' to run all pages
//pages = ['Contact Info', 'Organization Info', 'Service Options', 'Readiness', 'Ministry Prefs', 'IT Positions']
pages = ['Match Filters']

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest07jp' && username != 'cktest02jp') {
    println('The Execution Profile must be set to "Journey Partner"')

    System.exit(0)
}

domain = GlobalVariable.domain

called = false
if (binding.hasVariable('varCalled')) {
	called = varCalled
}

//Check to see if we're writing printed output to a file
writeFile = false

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}

if(!writeFile) {
	outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Journey Partner Profile on ' +
	domain) + '.txt')
	
	GlobalVariable.outFile = outFile
	
	outFile.write(('Testing Complete Journey Partner Profile on ' + domain) + '\n')
} else {
	outFile.append(('\nTesting Complete Journey Partner Profile on ' + domain) + '\n')
	
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)
println(url)

if (url == null) {
    WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)
println(url)


if(url.indexOf('dashboard') >= 0) {
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_My Profile'))
	
}
	

dashboard = WebUI.verifyElementPresent(findTestObject('Journey Partner Profile/Dashboard/a_My Profile'), 2,
	FailureHandling.OPTIONAL)
println(dashboard)
System.exit(1)
if(dashboard) {
		WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_My Profile'))
}

//////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	// Complete the Contact Info tab
	key_contact_phone = '952-442-1703'
	
	organization_phone = '612-209-9611'
	
	organization_city = 'Waconia'
	
	province_state_region = 'Minnesota'
	
	country = 'United States'
	
	WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Contact Info'), [('varKey_contact_phone') : key_contact_phone
			, ('varOrganization_phone') : organization_phone, ('varOrganization_city') : organization_city
			, ('varProvince_state_region') : province_state_region
			, ('varCountry') : country], FailureHandling.STOP_ON_FAILURE)
}

//////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Organization Info' in pages) {
	// Complete the Organization Info tab
	year_founded = '2012'
	
	link_back_url = 'http://myjourneypartner.org'
	
	organization_city = 'Waconia'
	
	hide_listing = 'Yes'
	
	security_explanation = 'Creative access country.'
	
	WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Organization Info'), [('varYear_founded') : year_founded
			, ('varLink_back_url') : link_back_url, ('varHide_listing') : hide_listing
			, ('varSecurity_explanation') : security_explanation], FailureHandling.STOP_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Service Options' in pages) {
// Complete the Service Options tab
time_commitments = ['One year to two years', 'Long Term']

available_start_options = 'In one to two years'

travel_options = 'Travel funding negotiated'

time_hours_needed = ['Part Time ~ 20 Hours/Week', 'Part of Each Year', 'Summer Opportunities']

awareness_trip = 'Yes'

awareness_trip_description = 'Awareness trips to North Africa'

vision_trip = 'Yes'

vision_trip_description = 'Several vision trip options'

need_candidates_for_short_term_assignments = 'Yes'

short_term_trip_length = ['Two Weeks', '3-4 Weeks', 'A few months']

short_term_availability = ['Summer', 'Fall']

short_term_objective = ['Explore opportunities in missions to find a good fit', 'Gain first-hand exposure to missions']

short_term_statement = 'Many short-term opportunities.'

preferred_regions = ['Southeast Asia', 'Central America', "Europe, West", 'North America', 'Central America']

languages = ['English is a native language', 'Other']

other_languages = 'Polish'

WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Service Options'), [('varTime_commitments') : time_commitments
		, ('varAvailable_start_options') : available_start_options, ('varTravel_options') : travel_options
		, ('varTime_hours_needed') : time_hours_needed, ('varAwareness_trip') : awareness_trip
		, ('varAwareness_trip_description') : awareness_trip_description, ('varVision_trip') : vision_trip
		, ('varVision_trip_description') : vision_trip_description
		, ('varNeed_candidates_for_short_term_assignments') : need_candidates_for_short_term_assignments
		, ('varShort_term_trip_length') : short_term_trip_length, ('varShort_term_availability') : short_term_availability
		, ('varShort_term_objective') : short_term_objective, ('varShort_term_statement') : short_term_statement
		, ('varPreferred_regions') : preferred_regions, ('varLanguages') : languages, ('varOther_languages') : other_languages],
	FailureHandling.STOP_ON_FAILURE)
}

//////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Readiness' in pages) {
	//Complete the Readiness tab
	process_stage = ['I am actively investigating missions', 'I am ready to select a ministry']
		
	cross_cultural = ['Not served in a culture other than my own', 'Occasionally served in cultures other than my own',
		 'Extensive experience serving in cultures other than my own']
	
	bible_training = ['Some Bible school classes', 'Bible school degree or equivalent']
	
	perspectives = ['I have not taken the Perspectives Course', 'I am planning to take the Perspectives Course',
		'I have taken or am taking the Perspectives Course']
	
	relocation = ['I am willing to relocate within North America', 'I am willing to relocate overseas']
	
	WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Readiness'), [('varProcess_stage') : process_stage,
		('varCross_cultural') : cross_cultural, ('varBible_training') : bible_training, 
		('varPerspectives') : perspectives,	('varRelocation') : relocation], FailureHandling.OPTIONAL)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Ministry Prefs' in pages) {
	frame = new JFrame("");
	JPanel p = new JPanel();
	JLabel l = new JLabel("LOADING ...", SwingConstants.CENTER);
	frame.add(l);
	frame.setSize(300, 100);
	frame.setLocation(600, 0);
	frame.setAlwaysOnTop (true)
	frame.show();
		
	// Complete the Your Ministry Prefs tab
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Ministry Prefs'))
	
	// Get the job categories and preferences from /Users/cckozie/Documents/MissionNext/Journey Candidate Forms/Your Ministry Prefs.csv
	preferences = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
	[('varFileName') : 'Journey Partner Forms/Ministry Prefs.csv', ('varSelections') : 'Ministry Preferences'], FailureHandling.STOP_ON_FAILURE)
	
	frame.hide();
	
	need_specific_it_positions = 'Yes'
	
	other_people_group = 'None'
	
	WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Ministry Prefs'), [('varMinistry_preferences') : preferences
		, ('varNeed_specific_it_positions') : need_specific_it_positions, ('varOther_people_group') : other_people_group]
		, FailureHandling.STOP_ON_FAILURE)
}

/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'IT Positions' in pages) {
	frame = new JFrame("");
	JPanel p = new JPanel();
	JLabel l = new JLabel("LOADING ...", SwingConstants.CENTER);
	frame.add(l);
	frame.setSize(300, 100);
	frame.setLocation(600, 0);
	frame.setAlwaysOnTop (true)
	frame.show();
	
	WebUI.delay(5)
	// Complete the IT Skills and Interests tab
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_IT Positions'))
	
	// Get the job categories and preferences from /Users/cckozie/Documents/MissionNext/Journey Partner Forms/IT Positions.csv
	positions = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
	[('varFileName') : 'Journey Partner Forms/IT Positions.csv', ('varSelections') : 'IT Expertise Needed'], FailureHandling.STOP_ON_FAILURE)
	
	languages = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
	[('varFileName') : 'Journey Partner Forms/IT Positions.csv', ('varSelections') : 'Computer Languages'], FailureHandling.STOP_ON_FAILURE)
	
	frame.hide();
	
	WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set IT Positions'), [('varIT_expertice_needed') : positions
		, ('varComputer_languages') : languages], FailureHandling.STOP_ON_FAILURE)
}


//////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
if(pages.size() == 0 || 'All' in pages || 'Match Filters' in pages) {
	// Complete the Match Filters tab
	affiliated_with_a_church = 'Yes'
	
	paid_volunteer_positions = ['Position requires raising some support', 'A salary provided; enough to live locally', 'A salary, enough to live locally + debt assistance']
	
	match_percent_rate = '60'
	
	profile_years = ['2023', '2024', '2025']
	
	WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Match Filters')
		, [('varAffiliated_with_a_church') : affiliated_with_a_church, ('varPaid_volunteer_positions') : paid_volunteer_positions
	        , ('varMatch_percent_rate') : match_percent_rate, ('varProfile_years') : profile_years], FailureHandling.STOP_ON_FAILURE)
	}	

	/////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	if(pages.size() == 0 || 'All' in pages || 'Recruiting Countries' in pages) {
		frame = new JFrame("");
		JPanel p = new JPanel();
		JLabel l = new JLabel("LOADING ...", SwingConstants.CENTER);
		frame.add(l);
		frame.setSize(300, 100);
		frame.setLocation(600, 0);
		frame.setAlwaysOnTop (true)
		frame.show();
		
		WebUI.delay(5)
		// Complete the IT Skills and Interests tab
		WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Tabs/a_Recruiting Countries'))
		
		// Get the recruiting countries from /Users/cckozie/Documents/MissionNext/Journey Partner Forms/Recruiting Countries.csv
		countries = WebUI.callTestCase(findTestCase('_Functions/Get Selections from CSV File'),
		[('varFileName') : 'Journey Partner Forms/Recruiting Countries.csv', ('varSelections') : 'Recruit From Countries'], FailureHandling.STOP_ON_FAILURE)
		
		frame.hide();
		
		WebUI.callTestCase(findTestCase('Journey Partner Profile/Tabs/Set Recruiting Countries')
			, [('varRecruit_from_countries') : countries], FailureHandling.STOP_ON_FAILURE)
	}
	
	