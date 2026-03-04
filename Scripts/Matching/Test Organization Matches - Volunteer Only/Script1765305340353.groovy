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

// FOR NON-MATCHES, NEED TO SEE IF THE OPTIONS WERE ONE OR BOTH OF "A salary provided; enough to live locally" OR "Only envision serving in paid positions"

maxMatches = 500 //Overridden if test suite running

debug = false

if(varSite != null) {
	site = varSite
} else {
	site = 'Journey' // Journey or Education
}

matchType = 'Org'	// Org or Job

if(site == 'Journey') {
	user = 'Journey Partner Temp 07'
}

if(site == 'Education') {
	user = 'Education Partner 16'
}

new ExecutionProfilesLoader().loadProfile(user)

orgUsername = GlobalVariable.username

orgPassword = GlobalVariable.password

orgEmail = GlobalVariable.email

firstName = ''

lastName = ''

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName + '-' + site
	
	maxMatches = 100
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3) + '-' + site
}

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

outFile.write(('Running ' + myTestCase) + '\n\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

lastEmailAddress = ''

tab = '\t'

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : user, ('varUsername') : orgUsername, ('varPassword') : orgPassword
        , ('varSite') : site], FailureHandling.STOP_ON_FAILURE) 

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Candidate Matches'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(60)

test = WebUI.verifyElementVisible(findTestObject('Object Repository/Journey Partner Profile/Matching/a_View TEST candidates'), FailureHandling.OPTIONAL)

if(test) {
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/a_View TEST candidates'))
}

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size() - 5

if(maxMatches < row_count) {
	row_count = maxMatches
}

found = false
	
noErrors = true

count = 0

errorCount = 0

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

tempFile = new File(filePath + 'journey candidate profile.txt')


if(row_count > 0) {

	for (row = 3; row < row_count + 3; row++) {
		
		error = false
		
		println('row is ' + row)
		
		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
		line = Columns.get(0).getText()
		
		println('line is ' + line)
		
		firstName = Columns.get(1).getText()
		
		println('firstName is ' + firstName)
		
		lastName = Columns.get(2).getText()
		
		println('lastName is ' + lastName)
		
		pct = Columns.get(8).getText()
		
		println('Table pct is ' + pct)
		
		tablePct = pct.toInteger()
		
		println('Table percent is ' + tablePct)
		
		spyGlass = Columns.get(9)
		
		myRow = row + 1
		
		element = '/html[1]/body[1]/center[1]/table[1]/tbody[1]/tr[4]/td[1]/table[1]/tbody[1]/tr[1]/td[3]/span[@class="body"]/form[1]/p[1]/table[1]/tbody[1]/tr[' + myRow + ']/td[10]/a[1]/img[1]'
		
		println(element)
		
		driver.findElement(By.xpath(element)).click()
		
		WebUI.switchToWindowIndex(2)
		
		paidVolunteer = WebUI.getText(findTestObject('Object Repository/Journey Partner Profile/Matching/text_Paid and Volunteer Positions Match Text'))
		
		if(paidVolunteer == 'Open' || paidVolunteer == 'Volunteer/self-supported position') {
			outText = ('Line ' + line + ', ' + firstName + ' ' + lastName + ' has ' + paidVolunteer + ' for Paid & Volunteer Positions.')
			outFile.append(outText + '\n')
			noMatch = false
		} else {
			noMatch = true
		}
		
		WebUI.closeWindowIndex(2)
		
		WebUI.switchToWindowIndex(1)
		
		if(noMatch) { 
			linkColumn = Columns.get(2)
			
			WebElement link = linkColumn.findElement(By.tagName('a'))
			
			link.click()
			
			WebUI.switchToWindowIndex(2)
		
			WebUI.waitForPageLoad(30)
			
			Robot robot = new Robot()
		
			//Select All
			robot.keyPress(KeyEvent.VK_META)
		
			robot.keyPress(KeyEvent.VK_A)
		
			robot.keyRelease(KeyEvent.VK_A)
		
			robot.keyRelease(KeyEvent.VK_META)
		
			WebUI.delay(1)
		
			//Copy
			robot.keyPress(KeyEvent.VK_META)
		
			robot.keyPress(KeyEvent.VK_C)
		
			robot.keyRelease(KeyEvent.VK_C)
		
			robot.keyRelease(KeyEvent.VK_META)
		
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()
			
			String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()
			
			println(data)
			
			myField = 'Paid & Volunteer Positions:'
			
			paidAndVol = data.indexOf(myField) + myField.length() + 2
			
			nextColon = data.indexOf(':', paidAndVol)
			
			subData = data.substring(paidAndVol,nextColon).replace(',', '|')
			
			println(subData)
			
			
			selections = subData.split('\n')
			
			println(selections.getClass())
			
			println(selections)
			
			println(selections.size())
			
			lastOne = selections[selections.size() - 1]
			
			mySelections = []
			
			for(i = 0; i < selections.size() - 1; i++ ) {
				mySelections.add(selections[i].replace('|', ','))
			}
			
			println(mySelections)
			
			outText = 'Line ' + line + ', ' + firstName + ' ' + lastName + ' has ' + mySelections + ' for Paid & Volunteer Positions.'
			
			sum = 0
			
			for(selection in mySelections) {
				if(selection == 'A salary provided; enough to live locally' || mySelections.contains('Only envision serving in paid positions')) {
					sum++
				} else {
					sum = sum + 5
				}
			}
			
			if(sum == 1 || sum == 2) {
				outText = ('## ERROR: ' + outText)
				errorFile.append(outText + '\n')
				errorCount++
			}
			
			outFile.append(outText + '\n')
			
			WebUI.closeWindowIndex(2)
			
			WebUI.switchToWindowIndex(1)
					
		}
		
//		WebUI.delay(1)
		
		count++
	}
}

outText = '\n ' + count + ' records tested.'

println(outText)

outFile.append(outText + '\n')

errorFile.append(outText + '\n')

outText = errorCount + ' errors found.\n'

println(outText)

outFile.append(outText + '\n')

errorFile.append(outText + '\n')

if(errorCount == 0) {
	errorFile.delete()
}

WebUI.closeBrowser()

