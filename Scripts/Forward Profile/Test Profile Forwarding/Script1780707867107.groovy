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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
//import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.apache.commons.io.FileUtils as FileUtils
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader
import java.time.Instant
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
//import org.openqa.selenium.Actions
import org.openqa.selenium.interactions.Actions

siteUser = ['Journey' : 'Journey Partner 17', 'Education' : 'Education Partner 16', 'Journey Guide' : 'Journey Guide 01']
//siteUser = ['Journey Guide' : 'Journey Guide 01']
//siteUser = ['Journey' : 'Journey Partner 17']
//siteUser = ['Education' : 'Education Partner 16']

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
		'/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
	
	maxMatches = 100
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

myTestCase += '-' + GlobalVariable.host

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

//GlobalVariable.outFile = outFile // prevent called test cases from writing to outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

outFile.append('Deleting any existing emails.\n')

WebUI.callTestCase(findTestCase('_Functions/Delete Emails'), [:], FailureHandling.STOP_ON_FAILURE)

errorsFlag = false

for(it in siteUser) {
	
	site = it.key
	
	myProfile = it.value

	new ExecutionProfilesLoader().loadProfile(myProfile)
	
	candidateUsername = GlobalVariable.username
	
	candidatePassword = GlobalVariable.password
	
	WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : candidateUsername, ('varPassword') : candidatePassword
			, ('varSite') : site], FailureHandling.STOP_ON_FAILURE) //***
		
	WebUI.waitForPageLoad(60)
	
	WebUI.delay(2)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	if(site == 'Journey Guide') { 
		
		firstNameColumn = 0
		
		WebUI.click(findTestObject('Object Repository/Journey Guide/img_CANDIDATE DASHBOARD'))
		
		WebUI.waitForPageLoad(20)
		
		WebUI.setText(findTestObject('Object Repository/Journey Guide/input_Candidate Name'), 'ck')
		
		WebUI.click(findTestObject('Object Repository/Journey Guide/button_Candidate Name_Search'))
		
		WebUI.waitForPageLoad(20)
		
//		WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody/tr/td/table/tbody'))
		
//		println(Table)
		
	} else {
		
		firstNameColumn = 1
		
		if(site == 'Journey') {
			WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Candidate Matches'))
		} else if(site == 'Education') {
			WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))
		}
		
		WebUI.switchToWindowIndex(1)
		
		WebUI.waitForPageLoad(60)
		
//		Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))
	}
	
	WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size() - 5
	
	if(row_count > 0) {
	
		row = 3 // Top row in list
				
		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
		line = Columns.get(0).getText()
		
		println('line is ' + line)
		
		if(line == ' ') {
			break
		}
		
		firstName = Columns.get(firstNameColumn).getText()
		
		println('firstName is ' + firstName)
		
		lastName = Columns.get(2).getText()
		
		println('lastName is ' + lastName)
		
		link = Columns.get(2)
		
//		element = link.findElement(By.xpath("./a"))
		element = link.findElement(By.tagName('a'))
		
		println(element)
		
		Actions actions = new Actions(driver);
		
		actions.moveToElement(element).perform();
		
		Thread.sleep(1000); // Wait for 1 second (use explicit waits in real projects)
					
		element.click()
		
		if(site != 'Journey Guide') {
		
			WebUI.switchToWindowIndex(2)
			
			WebUI.waitForPageLoad(60)
					
			WebUI.click(findTestObject('Object Repository/Forward Profile/button_PrintForward Profile'))
			
			WebUI.switchToWindowIndex(3)
			
		} else {
			
			WebUI.switchToWindowIndex(1)
		}
		
		WebUI.waitForPageLoad(60)
		
		object = 'Object Repository/Forward Profile/input_Mail To'
		
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'scroll', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
		
		WebUI.setText(findTestObject('Object Repository/Forward Profile/input_Mail To'), 'cckozie@hotmail.com')
		
		WebUI.check(findTestObject('Object Repository/Forward Profile/checkbox_Copy To'))
		
		WebUI.setText(findTestObject('Object Repository/Forward Profile/textarea_Note'), 'Testing profile fowarding on ' + site)
		
		WebUI.delay(1)
		
		WebUI.click(findTestObject('Object Repository/Forward Profile/button_Send'))
		
		WebUI.delay(1)
		
	}
	
	WebUI.closeBrowser()

	fromEmails = [GlobalVariable.email]
	
	subjects = ['Candidate Profile for ' + firstName + ' ' + lastName]
	
	searchKeys = ['NA', 'NA', 'NA']
	
	emailFound = WebUI.callTestCase(findTestCase('_Functions/Generic Wait for Email'), [('varFromKey') : fromEmails,
		('varSubjectKey') : subjects, ('varSearchKey') : searchKeys], FailureHandling.STOP_ON_FAILURE)
	
	println(emailFound)
	
	if(emailFound) {
		outFile.append('\n+++ ' + subjects[0] + ' forwarded from ' + fromEmails[0] + ' on ' + site + ' was received.\n')
	} else {
		outFile.append('\n### ERROR: ' + subjects[0] + ' forwarded from ' + fromEmails[0] + ' on ' + site + ' was NOT received.\n')
		errorsFlag = true
	}
}

if(errorsFlag) {
	outFile.renameTo('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '-ERRORS FOUND.txt')
	GlobalVariable.errorsFlag = true
	GlobalVariable.outFile = outFile
}

