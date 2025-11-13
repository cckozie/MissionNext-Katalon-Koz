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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import java.text.SimpleDateFormat

date = new Date()

today = date.format("yyyy-MM-dd")

ignoreBefore = '2025-10'

ignoreBeforeYear = ignoreBefore.substring(0,4).toInteger()

ignoreBeforeMonth = ignoreBefore.substring(5,7).toInteger()

sites = ['Journey', 'Education', 'QuickStart']

GlobalVariable.testCaseErrorFlag = false

domain = GlobalVariable.domain

// Write results to text file
testName = RunConfiguration.getExecutionProperties().get('current_testcase').toString().substring(RunConfiguration.getExecutionProperties().get(
        'current_testcase').toString().lastIndexOf('/') + 1)

myTestCase = testName

suffix = 'Journey'

outFile = new File(((((GlobalVariable.reportPath + myTestCase) + suffix) + ' on ') + domain) + '.txt')

GlobalVariable.outFile = outFile

if(outFile.exists()) {
	outFile.append('\n\nRunning ' + myTestCase + ' on ' + domain + ', ' + today + '.\n')
} else {
	outFile.write('Running ' + myTestCase + ' on ' + domain + ', ' + today + '.\n')
}

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

// Load list 'noProfiles' with the user ID of candidates who do not have a profile
WebUI.click(findTestObject('Object Repository/Admin/Ad Main/a_View Candidates wo Profile'))

tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/table[1]/tbody'

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath(tableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

println(row_count + ' rows found in table.')

noProfiles = []

myClass = ''

for (row = 1; row < row_count; row++) {
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

	userID = Columns.get(2).getText()

	noProfiles.add(userID)
	
}

WebUI.back()

WebUI.click(findTestObject('Object Repository/Admin/Ad Main/a_View  Repair List of Completed Profiles'))

WebUI.waitForPageLoad(30)

WebUI.selectOptionByValue(findTestObject('Object Repository/Admin/AD View and Repair Completed Profiles/select_Type'), 'Candidate', false)

// Load map 'candidates' with user ID, username, and name
candidates = [:]

tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/table/tbody'

for(site in sites) {
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/AD View and Repair Completed Profiles/select_Site'), site, false)
	
	WebUI.click(findTestObject('Object Repository/Admin/AD View and Repair Completed Profiles/button_Go'))
	
	WebUI.waitForPageLoad(30)

	driver = DriverFactory.getWebDriver()
	
	Table = driver.findElement(By.xpath(tableXpath))
	
	Rows = Table.findElements(By.tagName('tr'))
	
	row_count = Rows.size()
	
	println(row_count + ' rows found in table.')
	
	for (row = 1; row < row_count; row++) {
		
		Columns = Rows.get(row).findElements(By.tagName('td'))
	
		userID = Columns.get(1).getText()
		
		username = Columns.get(2).getText()
	
		name = Columns.get(3).getText()
	
		values = [username,name]
	
		candidates.put(userID,values)
	}
	WebUI.back()
}
println(candidates)

WebUI.back()


WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

// Find candidates who have an incorrect ending date
tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/table[3]/tbody'
 
driver = DriverFactory.getWebDriver()

Table = driver.findElement(By.xpath(tableXpath))

Rows = Table.findElements(By.tagName('tr'))

row_count = Rows.size()

println(row_count + ' rows found in table.')

myYear = today.substring(0,4).toInteger()

expiredYear = myYear - 5

jnUsers = []
//	jnUsers.add(16041)

eduUsers = []
//	eduUsers.add(15791)

errorCount = 0

for (row = 2; row < row_count; row++) {
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

	userID = Columns.get(0).getText()
	
	if(userID == 'User ID') { break }

	app = Columns.get(1).getText()

	lastIn = Columns.get(11).getText().substring(0,10)
	
	lastInYear = lastIn.substring(0,4)
	
	lastInYearInt = lastInYear.toInteger()

	expires = Columns.get(12).getText().substring(0,10)
	
	expiresYear = expires.substring(0,4)
	
	expiresYearInt = expiresYear.toInteger()
	
	month = lastIn.substring(5,7)
	
	day = lastIn.substring(8,10)
	
	endYear = lastInYearInt + 5
	
	end = endYear.toString()
	
	end = end + '-12-31'
	
	expired = ''
		
	lastInMonthInt = lastIn.substring(5,7).toInteger()
		
//	if(expires != end && lastInYearInt >= expiredYear && lastInYearInt >= 2025) {
	if(expires != end && lastInYearInt >= ignoreBeforeYear && lastInMonthInt >= ignoreBeforeMonth && !noProfiles.contains(userID)) {
		if(app.length() == 2) {
			app = app + ' '
		}
		println(lastInYearInt + '-' + lastInMonthInt)
		values = candidates.get(userID) // username, name
		outText = 'For user ' + userID + ', ' + values[0] + ' on ' + app + ', last login is ' + lastIn + ', end date is ' + expires + ', but should be ' + end + '.'
		println(outText)
		outFile.append(outText + '\n')
		if(noProfiles.contains(userID)) {
			outText = 'User ' + userID + ' has no profile.'
			println(outText)
			outFile.append(outText + '\n\n')
		}
		
//		values = candidates.get(userID)
//		outText = values[0] + ' ' + values[1]
//		println(outText)
//		outFile.append(outText + '\n\n')
		if(app == 'EDU') {
			eduUsers.add(userID)
		} else if(app == 'JN') {
			jnUsers.add(userID)
		}
		errorCount++
	}
	
}
println(eduUsers)
println(jnUsers)


outFile.append(errorCount + ' errors encountered.\n')

outFile.append('------------------------------------------------------------\n')
WebUI.click(findTestObject('Admin/Ad Subscription Utility/select_Website'))

WebUI.delay(1)

// Ensure that none of users are actually parnters

siteUsers = []

for(site in sites) {
	
	siteCount = 0
	
	if(site == 'Journey') {
		siteUsers = jnUsers
	} else {
		siteUsers = eduUsers
	}
	
	println(siteUsers)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Website'), site, false)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Role'), 'Organization', false)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Object Repository/Admin/AD Matching/btn_Go'))

	tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/p[3]/table/tbody'

	driver = DriverFactory.getWebDriver()
	
	Table = driver.findElement(By.xpath(tableXpath))
	
	Rows = Table.findElements(By.tagName('tr'))
	
	row_count = Rows.size()
	
	println(row_count + ' rows found in table.')
	
	for (row = 1; row < row_count; row++) {
		
		Columns = Rows.get(row).findElements(By.tagName('td'))
	
		rowText = Columns.get(0).getText()
		
		println(rowText)
		
		if(!rowText.contains('Account') && !rowText.contains('subscriptions')) {
			println(userID)
		
			userID = Columns.get(1).getText()
			
			if(siteUsers.contains(userID.toInteger())) {
				outText = ('User ID ' + userID + ' is a ' + site + ' partner.')
				outFile.append(outText + '\n')
				siteCount++
			}
		}
	}
//	outFile.append(siteCount + ' ' + site + ' partners were found.\n\n')
	
	WebUI.back()
}

WebUI.closeBrowser()