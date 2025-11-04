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
import java.io.File as File
import groovy.json.JsonSlurper
import org.openqa.selenium.WebDriver as Driver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

// Create previously captured jobs for a partner
// 10/30/25 - Modified to force me as the contact and email

username = GlobalVariable.username

partnerUser = 'billtreat'

site = 'Education'

if(site == 'Education') {
	suffix = '6ep'
} else {
	suffix = '7jp'
}

if(username[-3..-1] != suffix) {
	println('The Execution Profile must be set to "' + site + ' Partner"')

	System.exit(0)
}

domain = GlobalVariable.domain

myTestCase = RunConfiguration.getExecutionProperties().get("current_testcase").toString().substring(RunConfiguration.getExecutionProperties().get("current_testcase").toString().lastIndexOf('/') + 1)

outFile = new File(GlobalVariable.reportPath + myTestCase + ' on ' + domain + '.txt')

outFile.write('Testing  ' + myTestCase + ' on ' + domain + '.\n\n')

GlobalVariable.outFile = outFile
/*
//.First check to see if access has been granted to the user
granted = WebUI.callTestCase(findTestCase('Admin/Test for Access Granted'), [varUsername : username], FailureHandling.STOP_ON_FAILURE)

if(granted == false || granted == null) {
	outText = '##### - ' + username + ' has NOT been granted access. Complete profile script is terminated.'
	
	outFile.append(outText + '\n')
	
	WebUI.closeBrowser()
	 
	GlobalVariable.testCaseErrorFlag = true
	System.exit(0)
}
*/
newJobsFilePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Jobs/'	// Path to new jobs data files

newJobsPath = 'Object Repository/' + site + 'Partner Profile/New Jobs/'	// Test Object base path

newJobsTabsPath = newJobsPath + 'Tabs/'

// tabs map key = tab name, value = [required test object1 name, required test object2 name, etc]
tabs = [
	'Job Category':['select_Category'],// ['checkboxes- BUSINESS AS MISSION', 'checkboxes- CHURCH DEVELOPMENT', 'checkboxes- COMMUNICATIONS', 'checkboxes- COMMUNITY DEVELOPMENT', 'checkboxes- CONSTRUCT/MAINTAIN', 'checkboxes- DISCIPLESHIP', 'checkboxes- DISCIPLE YOUTH', 'checkboxes- EDUCATION', 'checkboxes- ESLTESOL', 'checkboxes- ENGINEERING', 'checkboxes- EVANGELISM', 'checkboxes- EVANGELISM SUPPORT', 'checkboxes- HEALTH CARE', 'checkboxes- INFORMATION TECHNOLOGY', 'checkboxes- JUSTICE/ADVOCACY', 'checkboxes- RELIEF AND DEVELOPMENT', 'checkboxes- RESOURCE MANAGEMENT', 'checkboxes- SUPPORT HELPS', 'checkboxes- SUPPORT PROFESSIONAL']],
	'Job Classification':['input_Alternate Job Title', 'select_World Region'],
	'Assignment Detail':['select_Start_Requested', 'checkboxes_Time Commitment', 'checkboxes_TimeHours Available', 'checkboxes_Cross-Cultural Experience', 'checkboxes_Languages'],
	'Logistics':['checkboxes_Paid and Volunteer Positions', 'checkboxes_Travel Support', 'checkboxes_Relocation Question'],
	'Contact Details':['input_Contact Name', 'input_Contact Email'],
	'Other Criteria':['checkboxes_Ministry Preferences']
	]

elementXpaths = [
	'- BUSINESS AS MISSION' : "//input[@id='job_group-1613486301.149_subset_business_as_mission']",
	'- CHURCH DEVELOPMENT' : "//input[@id='job_group-1613487025.929_subset_church_development']",
	'- COMMUNICATIONS' : "//input[@id='job_group-1613487009.143_subset_communications']",
	'- COMMUNITY DEVELOPMENT' : "//input[@id='job_group-1613487041.784_subset_community_development']",
	'- CONSTRUCT/MAINTAIN' : "//input[@id='job_group-1613487057.203_subset_construction/maintenence']",
	'- DISCIPLESHIP' : "//input[@id='job_group-1613487072.517_subset_discipleship']",
	'- DISCIPLE YOUTH' : "//input[@id='job_group-1613487089.233_subset_disciple_youth']",
	'- EDUCATION' : "//input[@id='job_group-1613487111.532_subset_education']",
	'- ESLTESOL' : "//input[@id='job_group-1613487128.158_subset_esl/tesol']",
	'- ENGINEERING' : "//input[@id='job_group-1613487147.166_subset_engineering']",
	'- EVANGELISM' : "//input[@id='job_group-1613487166.934_subset_evangelism']",
	'- EVANGELISM SUPPORT' : "//input[@id='job_group-1613487207.72_subset_evangelism_support']",
	'- HEALTH CARE' : "//input[@id='job_group-1613487231.488_subset_healthcare']",
	'- INFORMATION TECHNOLOGY' : "//input[@id='job_group-1613487248.416_subset_information_technology']",
	'- JUSTICE/ADVOCACY' : "//input[@id='job_group-1613487271.308_subset_justice/advocacy']",
	'- RELIEF AND DEVELOPMENT' : "//input[@id='job_group-1613487334.329_subset_relief_and_development']",
	'- RESOURCE MANAGEMENT' : "//input[@id='job_group-1613487353.674_subset_resource_management']",
	'- SUPPORT HELPS' : "//input[@id='job_group-1613487293.775_subset_support_helps']",
	'- SUPPORT PROFESSIONAL' : "//input[@id='job_group-1613487316.232_subset_support_professional']",
	'Time Commitment' : "//input[@id='job_group-1613448548.497_time_commitment']",
	'TimeHours Available' : "//input[@id='job_group-1613448548.497_time_availability']",
	'Cross-Cultural Experience' : "//input[@id='job_group-1613448548.497_cross-cultural_experience']",
	'Languages' : "//input[@id='job_group-1613448548.497_languages']",
	'Paid and Volunteer Positions' : "//input[@id='job_group-1613449047.954_financial_support']",
	'Relocation Question' : "//input[@id='job_group-1613449047.954_relocation_question']",
	'Travel Support' : "//input[@id='job_group-1613449047.954_travel_support_(to_and_from_school_location)']",
	'Ministry Preferences' : "//input[@id='job_group-1613449251.023_job_preferences']"
	]

WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Job Matches'))

WebUI.switchToWindowIndex(1)

// Get captured job files
files = new File(newJobsFilePath)

capturedJobs = []

files.eachFileRecurse {
	path = it.absolutePath
	if(path.substring(path.length() - 4) == '.txt') {
		capturedJobs.add(path)
	}
}

capturedJobs.each {
	println(it)
}

// Build list of the category, title, and file name of the new jobs files

newJobs = []

capturedJobs.each {
	values = []
	file = it.substring(newJobsFilePath.length())
	uBar = file.indexOf('_')
	category = file.substring(0,uBar)
	title = file.substring(uBar + 1, file.length() - 4)
	values.add(category)
	values.add(title)
	values.add(it)
	newJobs.add(values)
}

newJobs.each {
	println(it)
}

// Ignore the job files that already exist
jobTableXpath = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[1]/tbody'

Driver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath(jobTableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

println(row_count - 2 + 'jobs found.')

jobFound = false

existingJobs = WebUI.verifyTextNotPresent('There are no Journey Jobs', false, FailureHandling.OPTIONAL)

if (existingJobs && row_count > 2) {
	for (row = 2; row < row_count; row++) {
		println('row is ' + row)

		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

		jobID = Columns.get(0).getText()

		categoryElement = Columns.get(1)

		jobCategory = categoryElement.getText()

		jobTitle = Columns.get(2).getText()
		println('Looking for job ' + jobCategory + '-' + jobTitle)
		for(values in newJobs) {
			if(values[0] == jobCategory && values[1] == jobTitle) {
				println('Found and removing job ' + jobCategory + '-' + jobTitle)
				capturedJobs.remove(values[2])
			}
		}
	}
}

// Print list of actually new job files
capturedJobs.each {
	println(it)
}

for(job in capturedJobs) {
	println(job)
}


//capturedJobs = ['/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Jobs/COMMUNICATIONS_Writer.txt']
//capturedJobs = ['/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Jobs/INFORMATION TECHNOLOGY_AI Expert.txt']

for(job in capturedJobs) {
	
	println('Processing new job file ' + job)
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/button_Add Jobs'))
	
	WebUI.switchToWindowIndex(2)
	
	WebUI.waitForPageLoad(30)
		
	// Read the file's text content
	def jsonText = new File(job).text
	
	// Create a new JsonSlurper to parse the JSON text
	def slurper = new JsonSlurper()
	
	// Parse the text into a map
	def tempMap = slurper.parseText(jsonText)
	
	myMap = [:]
	
	tempMap.each { key, value ->
		key = key.replace('/','')
		key = key.replace('&','and')
		myMap[key] = value
	}
	
	myMap.each {
		println(it)
	}
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/New Jobs/a_Job Description'))
	
	today = new Date()
	
	expiresDate = today.plus(180)
	
	WebUI.setText(findTestObject('Object Repository/Journey Partner Profile/New Jobs/Tabs/Job Description/calendar_Listing Expiration'),expiresDate.format('MM/dd/yyyy'))
	
	for(myTab in tabs) {
		
		println(myTab)
		
		println(myTab.key)
		
		tObj = newJobsPath + 'a_' + myTab.key
		
		println(tObj)
		
		// Click on the tab
		
		WebUI.click(findTestObject(tObj))
		
		pathToMyTab = newJobsTabsPath + myTab.key + '/'
		
		println('>>>>>>>>> pathToMyTab is:' + pathToMyTab)
		
		fields = myTab.value
		
		println(fields)
		
		for(field in fields) {
			
			println(field)
			
			uBar = field.indexOf('_')
			
			prefix = field.substring(0, uBar)
			
			println(prefix)
			
			object = field.substring(uBar + 1).replace('/', '')
			
			print(object)
			
			if(myTab.key == 'Contact Details') {
				if(object == 'Contact Name') {
					value = 'Chris Kosieracki'
				} else if(object == 'Contact Email') { 
					value = 'chris.kosieracki@missionnext.org'
				}
			} else {
				value = myMap.get(object)
			}
			
			println(value)
			
			valueClass = value.getClass().toString()
			
			println(valueClass)
			
			if(prefix == 'select') {
				
				if(valueClass.contains('Array')) {
					myValue = value[0]
				} else {
					myValue = value
				}
				
				println('Selecting ' + myValue + ' for ' + pathToMyTab + field)
					
				WebUI.selectOptionByLabel(findTestObject(pathToMyTab + field), myValue, false)

				if(myTab.key == 'Job Category') {
					
					prefix = 'checkboxes'
					
					object = '- ' + myValue
					
					value = myMap.get(object)
					
					println(value)
				}
			}
			
			if(prefix == 'checkboxes') {
				
				xpath = elementXpaths.get(object)
				
				println(xpath)
				
				xpaths =[]
				
				xpaths.add(xpath)
				
				values = []
				
				values.add(value)
				
				WebUI.callTestCase(findTestCase('_Functions/Click on Group Elements'), [('varXpaths') : xpaths, ('varParms') : values], FailureHandling.STOP_ON_FAILURE)
				
			}
			
			if(prefix == 'input') {
				
				if(valueClass.contains('Array')) {
					myValue = value[0]
				} else {
					myValue = value
				}
				
				println('Entering ' + myValue + ' for ' + pathToMyTab + field)
					
				WebUI.setText(findTestObject(pathToMyTab + field), myValue)
			
				
			}
			
			WebUI.delay(1)
//			System.exit(0)
		}
	}
	
	
	object = 'Object Repository/Journey Partner Profile/New Jobs/button_Create'
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/New Jobs/button_Jobs List'))
	
	WebUI.waitForPageLoad(30)
	
	println('Window index is ' + WebUI.getWindowIndex())
	
	WebUI.closeWindowIndex(2)
	
	WebUI.switchToWindowIndex(1)
	
	WebUI.refresh()
	
}

WebUI.closeBrowser()