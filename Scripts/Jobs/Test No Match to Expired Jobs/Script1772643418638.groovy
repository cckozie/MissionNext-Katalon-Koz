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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword as WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.lang.Math as Math
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// Get 'office' jobs (j1Jobs)
// Get 'cktest37jp' jobs (j2Job)
// Find job matches to 'office' jobs (j1Matches)
// Find job matches to 'cktest37jp jobs (j2Matches)
// Verify all j1Matches are also in j2Matches
// Verify no j1Matches to expired jobs

debug = false

site = 'Journey'

partnerProfile = 'Journey Partner 37'

candidateProfile = 'Journey Candidate 15'

// Load the partners's execution profile
new ExecutionProfilesLoader().loadProfile(partnerProfile)

username = GlobalVariable.username

password = GlobalVariable.password

switchToUser = 'office' // office or cktest17jp

today = new Date().format("yyyy-MM-dd")

todayDate = Date.parse("yyyy-MM-dd", today)

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

myTestCase += '-' + GlobalVariable.host

//filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

//jobFile = (filePath + 'jobprofile.txt')

j1Jobs = [:]

j2Jobs = [:]

state = [false, true]

for(switchTo in state) {
	
	if(switchTo) {
		WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : switchToUser , ('varSite') : 'Journey'], FailureHandling.STOP_ON_FAILURE)
		
		outFile.append('\nOffice Jobs\n')
		
		WebUI.delay(2)
		
		j1Jobs = getJobsList()
		
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
		WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out 2'), [varSite:site], FailureHandling.STOP_ON_FAILURE)
		
	} else {
		WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:])
		
		outFile.append('\n' + partnerProfile + ' Jobs\n')
		
		j2Jobs = getJobsList()
		
	}
	
	WebUI.closeBrowser()
}

println("Office jobs")
j1Jobs.each {
	println(it)
}

println("cktest37jp jobs")
j2Jobs.each {
	println(it)
}

if(j1Jobs.size() == 0) {
	outFile.append('\nNO ACTIVE ' + switchToUser + ' JOBS WERE FOUND.\n')
}
/*
j1Jobs = [
	'BUSINESS AS MISSION|Ag business manager=false',
	'SUPPORT PROFESSIONAL|MissionNext Education Strategist (Part-time Volunteer)=false',
	'SUPPORT PROFESSIONAL|National Director of Mobilization (Part-time, Volunteer)=false',
	'INFORMATION TECHNOLOGY|PHP / Laravel Developer (Part-time, Volunteer)=false',
	'COMMUNICATIONS|Chief Communications Officer (Volunteer, Part-time)=false',
	'SUPPORT PROFESSIONAL|Partnership Development Coordinator (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Grant Writer=false',
	'INFORMATION TECHNOLOGY|Linux Server Expert (Part-time, Volunteer)=false',
	'INFORMATION TECHNOLOGY|Web Manager, (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Journey Guide (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Chief Program Officer (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Education Specialist (Part-time Volunteer)=false',
	'INFORMATION TECHNOLOGY|Digital Marketing Manager=false',
	'SUPPORT PROFESSIONAL|Donor Development Specialist (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|School Recruiter for MissionNext Education (Part-time Volunteer)=false',
	'SUPPORT HELPS|Administrative Assistant=false',
	'SUPPORT PROFESSIONAL|MissionNext Journey Strategist (Part-time Volunteer)=false',
	'SUPPORT HELPS|Chief Advancement Officer (Part-time, Volunteer)=false',
	'INFORMATION TECHNOLOGY|Web Developer (Part-time Volunteer)=false',
	'BUSINESS AS MISSION|BAM Coordinator for MIssionNext=false',
	'SUPPORT PROFESSIONAL|Member Care Leader (Part-time, Volunteer)=false',
	'INFORMATION TECHNOLOGY|Microsoft 365 Technical Support Administrator=false',
	'COMMUNICATIONS|Virtual Store Manager (Part-time, Volunteer)=false',
	'JUSTICE/ADVOCACY|Corporate Attorney=false',
	'SUPPORT PROFESSIONAL|Partner Care Team Rep (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|MissionNext Analyst (Part-time Volunteer)=false',
	'SUPPORT PROFESSIONAL|Chief of Staff=false',
	'SUPPORT PROFESSIONAL|Journey Guide Director (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Education Development Coordinator (Part-time Volunteer)=false',
	'SUPPORT PROFESSIONAL|Chief Operations Officer=false',
	'SUPPORT PROFESSIONAL|Chief Development Officer (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Regional Mobilizer (Part-time, Volunteer)=false']

j2Jobs = [
	'JUSTICE/ADVOCACY|Corporate Attorney=false',
	'SUPPORT HELPS|Administrative Assistant=false',
	'SUPPORT PROFESSIONAL|National Director of Mobilization (Part-time, Volunteer)=false',
	'INFORMATION TECHNOLOGY|PHP / Laravel Developer (Part-time, Volunteer)=false',
	'SUPPORT PROFESSIONAL|Journey Guide (Part-time, Volunteer)=false',
	'BUSINESS AS MISSION|BAM Coordinator for MIssionNext=true',
	'SUPPORT PROFESSIONAL|Grant Writer=true',
	'SUPPORT PROFESSIONAL|Regional Mobilizer (Part-time, Volunteer)=true',
	'INFORMATION TECHNOLOGY|Web Developer (Part-time Volunteer)=true',
	'INFORMATION TECHNOLOGY|Linux Server Expert (Part-time, Volunteer)=true',
	'COMMUNICATIONS|Chief Communications Officer (Volunteer, Part-time)=true',
	'INFORMATION TECHNOLOGY|Digital Marketing Manager=true',
	'SUPPORT PROFESSIONAL|MissionNext Journey Strategist (Part-time Volunteer)=true',
	'SUPPORT PROFESSIONAL|School Recruiter for MissionNext Education (Part-time Volunteer)=true',
	'SUPPORT PROFESSIONAL|Partner Care Team Rep (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|MissionNext Education Strategist (Part-time Volunteer)=true',
	'SUPPORT PROFESSIONAL|MissionNext Analyst (Part-time Volunteer)=true',
	'SUPPORT PROFESSIONAL|Member Care Leader (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Education Development Coordinator (Part-time Volunteer)=true',
	'SUPPORT PROFESSIONAL|Donor Development Specialist (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Chief Program Officer (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Chief Development Officer (Part-time, Volunteer)=true',
	'SUPPORT HELPS|Chief Advancement Officer (Part-time, Volunteer)=true',
	'COMMUNICATIONS|Virtual Store Manager (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Education Specialist (Part-time Volunteer)=true',
	'INFORMATION TECHNOLOGY|Microsoft 365 Technical Support Administrator=true',
	'INFORMATION TECHNOLOGY|Web Manager, (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Journey Guide Director (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Chief Operations Officer=true',
	'SUPPORT PROFESSIONAL|Partnership Development Coordinator (Part-time, Volunteer)=true',
	'SUPPORT PROFESSIONAL|Chief of Staff=true']
*/
// Load the candidates's execution profile
new ExecutionProfilesLoader().loadProfile(candidateProfile)

candidateUsername = GlobalVariable.username

candidatePassword = GlobalVariable.password

orgName = GlobalVariable.organization

officeName = 'MissionNext'

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : candidateProfile, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(60)

userTab = WebUI.getWindowIndex()

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_View Job Matches'))

WebUI.waitForPageLoad(30)

jobTableXpath = '//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody'

driver = DriverFactory.getWebDriver()

Table = driver.findElement(By.xpath(jobTableXpath))

Rows = Table.findElements(By.tagName('tr'))

row_count = Rows.size() - 8

println(row_count + ' rows found')

orgMatches = []

officeMatches = []

morePages = true

while(morePages) {

	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath(jobTableXpath))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size() - 8
	
	println(row_count + ' rows found')
	
	if (row_count > 0) {
		for (row = 1; row < row_count; row++) {
			println('row is ' + row)
	
			rowClass = Rows.get(row).getAttribute("class")
			
			if(rowClass.contains('item')) {
					
				List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
		
				jobLine = Columns.get(0).getText()
		
				println('jobLine is ' + jobLine)
				
				categoryElement = Columns.get(1)
		
				jobCategory = categoryElement.getText()
		
				println('jobCategory is ' + jobCategory)
				
				agency = Columns.get(2).getText()
				
				println('agency is ' + agency)
					
				jobTitle = Columns.get(3).getText()
		
				println('jobTitle is ' + jobTitle)
				
				myKey = jobCategory + '|' + jobTitle
				
				if(agency == orgName) {
					
					orgMatches.add(myKey)
					
				} else if(agency == officeName) {
					
					officeMatches.add(myKey)
				}
			}
		}
	}

	morePages = WebUI.verifyElementPresent(findTestObject('Object Repository/Journey Candidate Profile/Matching/a_Next Page'), 1, FailureHandling.OPTIONAL)

	if(morePages) {
		object = 'Object Repository/Journey Candidate Profile/Matching/a_Next Page'
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
		
		WebUI.waitForPageLoad(30)
	}	
}

println('partner matches')
orgMatches.each {
	println(it)
}

println('office matches')
officeMatches.each {
	println(it)
}

outFile.append('\n TEST JOB MATCHES\n')
// Verify that all matches to office are also in partner
for(job in officeMatches) {
	if(job in orgMatches) {
		println('job ' + job + ' matches in both partners')
		outFile.append('Job ' + job + ' was a match for both ' + switchToUser + ' and ' + partnerProfile + '\n')
		
	} else {
		println('job ' + job + ' is not in the partner matches')
		outFile.append('##### ERROR: Job ' + job + ' was a match for ' + switchToUser + ' but not for ' + partnerProfile + '\n')
		error = true
	}
}

outFile.append('\n TEST JOB MATCHES TO ONLY ACTIVE JOBS\n')
// Verify that all matches to office are to non-expired jobs
for(job in officeMatches) {
	expired = j2Jobs.get(job, null)
	
	if(expired) {
		println('job ' + job + ' matched but is expired.')
		outFile.append('##### ERROR: Job ' + job + ' was a match for ' + switchToUser + ' but the job has EXPIRED.\n')
		
	} else {
		println('job ' + job + ' matched and is active.')
		outFile.append('Job ' + job + ' was a match for an active ' + switchToUser + ' job.\n')
	}
}

WebUI.closeBrowser()



def getJobsList() {
	
	jobs = [:]
	
	WebUI.waitForPageLoad(60)
	
	WebUI.delay(2)
	
	orgTab = WebUI.getWindowIndex()
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Job Matches'))
	
	WebUI.switchToWindowIndex(1)
	
	WebUI.waitForPageLoad(60)
	
	WebUI.delay(1)
	
	myTable = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[1]/tbody'
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath(myTable))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size() - 2
	
	println(row_count + ' rows found.')
	
	if(row_count > 0) {
		
		for (row = 2; row < row_count + 2; row++) {
			
			println('row is ' + row)
			
			myRow = row + 1
			
			List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
			
			jobID = Columns.get(0).getText()
			
			jobCategory = Columns.get(1).getText()
			
			jobTitle = Columns.get(2).getText()
			
			job = jobCategory + '|' + jobTitle
			
			expiration = Columns.get(4).getText()
			
			println(expiration)
									
			println('today is ' + today)
			
			println('todayDate is ' + todayDate)
			
			expired = false
			
			status = 'Active'
			
			if(today > expiration) {
				
				expired = true
				
				status = 'Expired'
			}
			
			outFile.append(jobCategory + ' : ' + jobTitle + ' : ' + expiration + ' : ' + status + '\n')
			
			jobs.put(job,expired)
		}
	}
	println(jobs)
	jobs.each {
		println(it)
	}
	
	return jobs
}
