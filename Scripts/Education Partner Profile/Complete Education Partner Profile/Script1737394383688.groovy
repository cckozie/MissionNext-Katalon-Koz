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

///#########################################
///#########################################
//	Test access to tabs when profile was previously completed
//	Add test for external links on all tabs
//	Test logging, including image match percentages
///#########################################
///#########################################

// Set to page(s) to run, or empty or 'All' to run all pages
pages = []

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest06ep') {
    println('The Execution Profile must be set to "Education Partner"')

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
	outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Test Complete Education Partner Profile on ' +
	domain) + '.txt')
	
	GlobalVariable.outFile = outFile
	
	outFile.write(('Testing Complete Education Partner Profile on ' + domain) + '\n')
} else {
	outFile.append(('\nTesting Complete Education Partner Profile on ' + domain) + '\n')
	
}

url = WebUI.getUrl(FailureHandling.OPTIONAL)

if (url == null) {
	WebUI.callTestCase(findTestCase('_Functions/Education Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
}

dashboard = WebUI.verifyElementPresent(findTestObject('Education Partner Profile/Dashboard/a_My Profile'), 2,
	FailureHandling.OPTIONAL)

if(dashboard) {
		WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'))
}

if(pages.size() == 0 || 'All' in pages || 'Contact Info' in pages) {
	// Complete the Contact Info tab
	key_contact_phone = '952-442-1703'
	
	organization_phone = '612-209-9611'
	
	organization_city = 'Waconia'
	
	province_state_region = 'Minnesota'
	
	world_region = 'North America'
	
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Contact Info'), [('varKey_contact_phone') : key_contact_phone
			, ('varOrganization_phone') : organization_phone, ('varOrganization_city') : organization_city
			, ('varProvince_state_region') : province_state_region
			, ('varWorld_region') : world_region], FailureHandling.STOP_ON_FAILURE)
}


if(pages.size() == 0 || 'All' in pages || 'School Info' in pages) {
	// Complete the School Info tab
	description = 'The Kosieracki Christian K-12 School'
	
	year_founded = '1971'
	
	vision_trip = 'Yes'
	
	vision_trip_description = '2 week internship.'
	
	school_year_starts = 'Early August'
	
	school_accredited = 'No'
	
	student_capacity = '200'
	
	website_address = 'https://www.swchs.org'
	
	hide_listing = 'Yes'
	
	reason_for_hiding = 'Creative access country'
	
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set School Info'), [('varDescription') : description
			, ('varYear_founded') : year_founded, ('varVision_trip') : vision_trip, ('varVision_trip_description') : vision_trip_description
			, ('varSchool_year_starts') : school_year_starts, ('varSchool_accredited') : school_accredited
			, ('varStudent_capacity') : student_capacity, ('varWebsite_address') : website_address
			, ('varHide_listing') : hide_listing, ('varReason_for_hiding') : reason_for_hiding], FailureHandling.STOP_ON_FAILURE)
}

if(pages.size() == 0 || 'All' in pages || 'Positions Needed' in pages) {
	//Complete the Positions Needed tab
	available_positions = ['Assistant Principal', 'Principal', 'Childcare Director', 'English Teacher', 'Manager, Business']
	
	experience_preferred = ['Administrator', 'Non-Traditional', 'Teacher\'s Aide', 'Computer Science','ESL']
	
	available_other_positions = 'None at this time'
	
	other_experience_comment = 'Will consider internships as work experience.'
	
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Positions Needed'), [('varAvailable_positions') : available_positions
		, ('varExperience_preferred') : experience_preferred, ('varAvailable_other_positions') : available_other_positions
		, ('varOther_experience_comment') : other_experience_comment], FailureHandling.OPTIONAL)
}
	
if(pages.size() == 0 || 'All' in pages || 'Service Options' in pages) {
//Complete the Service Options tab
	
	time_commitments = ['One year', 'One year to two years']
	
	start_options = ['Within 12 months']
	
	school_terms_available = ['Open']
	
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Service Options'), 
		[('VarTime_commitments') : time_commitments, ('varStart_options') : start_options,
			('varSchool_terms_available') : school_terms_available], FailureHandling.OPTIONAL)
}
	
	
if(pages.size() == 0 || 'All' in pages || 'Readiness' in pages) {
	//Complete the Readiness tab
	process_stage = ['I am actively investigating missions', 'I am ready to select a ministry']
		
	cross_cultural = ['Not served in a culture other than my own', 'Occasionally served in cultures other than my own',
		 'Extensive experience serving in cultures other than my own']
	
	bible_training = ['Some Bible school classes', 'Bible school degree or equivalent']
	
	perspectives = ['I have not taken the Perspectives Course', 'I am planning to take the Perspectives Course',
		'I have taken or am taking the Perspectives Course']
	
	missions_experience = ['I have attended a missions event', 'I have taken a short-term missions trip',
		'I have served in ministry or missions full-time', 'None of the above']
	
	relocation = ['I am willing to relocate within North America', 'I am willing to relocate overseas']
	
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Readiness'), [('varProcess_stage') : process_stage,
		('varCross_cultural') : cross_cultural, ('varBible_training') : bible_training, 
		('varPerspectives') : perspectives,	('varMissions_experience') : missions_experience, 
		('varRelocation') : relocation], FailureHandling.OPTIONAL)
}

if(pages.size() == 0 || 'All' in pages || 'Match Filters' in pages) {
	//Complete the Match Filters tab
	degree = ['No']
	
	experience = ['Yes']
	
	credentials = ['Yes', 'No', 'Pending']
	
	english = ['Not Applicable']
	
	travel = ['Some Travel funds available', 'Travel funding negotiated']
	
	paid_volunteer = ['Volunteer/self-supported position', 'Self-supported, need travel and overhead',
	 'Position requires raising your support', 'Position requires raising some support']
	
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Match Filters'), [('varDegree') : degree,
		('varExperience') : experience, ('varCredentials') : credentials, ('varEnglish') : english,
		('varTravel') : travel, ('varPaid_volunteer') : paid_volunteer], FailureHandling.OPTIONAL)
}

if(pages.size() == 0 || 'All' in pages || 'Admin Info' in pages) {
	// Complete the Admin Info tab
	mission_statement = 'Will submit by email.'
	
	meet_christian_school_qualifications = 'Not sure. Contact MissionNext Education'
	
	partnership_agreement = []
	
	terms_and_conditions = []
		
	WebUI.callTestCase(findTestCase('Education Partner Profile/Tabs/Set Admin Info'), [('varMission_statement') : mission_statement
		, ('varMeet_christian_school_qualifications') : meet_christian_school_qualifications
		, ('varPartnership_agreement') : partnership_agreement, ('varTerms_and_conditions') : terms_and_conditions]
		, FailureHandling.STOP_ON_FAILURE)
}
