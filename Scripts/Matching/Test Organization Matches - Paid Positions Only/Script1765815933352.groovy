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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
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

/////////

// Add the test for Paid & Volunteer Positions selection from  Test Organization - Volunteer Only


debug = false

site = 'Journey' // Journey or Education

if(site == 'Journey') {
	profile = 'Journey Partner Temp 07'
}

if(site == 'Education') {
	profile = 'Education Partner 16'
}

new ExecutionProfilesLoader().loadProfile(profile)

orgUsername = GlobalVariable.username

println(orgUsername)

orgPassword = GlobalVariable.password

orgEmail = GlobalVariable.email

if(GlobalVariable.testSuiteRunning) {
	users = ['cktemp07jp']
} else {
//	users = [orgUsername, 'office']
	users = ['cktemp07jp']
}

firstName = ''

lastName = ''

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName + '-' + site
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3) + '-' + site
}

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

outFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

WebUI.openBrowser(null)

WebUI.maximizeWindow()

switchTo = false

errorCount = 0
	
for(user in users) {
	
	if(user.substring(0,4) == 'ckte') {
		println(profile)
		println(user)
		println(orgUsername)

		WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : profile, ('varUsername') : orgUsername, ('varPassword') : orgPassword
		        , ('varSite') : site], FailureHandling.STOP_ON_FAILURE //***
		    )
	} else {
		WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : user, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
		
		WebUI.waitForPageLoad(60)
		
		switchTo = true

	}
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Candidate Matches'))
	
	WebUI.waitForPageLoad(30)
	
	WebUI.refresh()
	
	WebUI.waitForPageLoad(30)
	
	object = 'Object Repository/Journey Partner Profile/Matching/img_Folder - Paid Position Only'
	
	//WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	tableWindowTitle = WebUI.getWindowTitle()
	
	WebUI.switchToWindowIndex(1)
	
	WebUI.waitForPageLoad(60)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size()
	
	found = false
	
	paidOnly = false
		
	noErrors = true
	
	if(row_count > 0) {
	
		for (row = 3; row < row_count + 3; row++) {
			
			println('row is ' + row)
			
			myRow = Rows.get(row)
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			js.executeScript("arguments[0].scrollIntoView(true);", myRow);
			
			List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
		
			line = Columns.get(0).getText()
			
			if(!found && !line.contains('Paid Position Only')) {
				continue
			} else if(!paidOnly) {
				found = true
				
				List<WebElement>link = myRow.findElements(By.tagName('a'))
				
				link[0].click()
			
//				WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/img_Folder - Paid Position Only'))
				
				WebUI.delay(2)
				
				Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))
				
				Rows = Table.findElements(By.tagName('tr'))
				
				new_row_count = Rows.size()
				
				paidOnly = true
				
				clickRow = row
				
//				continue
			}
		}
	}
	
	
	
	println(row_count + ':' + new_row_count)
	
	if(new_row_count > row_count + 2) {
		
		outText = '\nPaid only candidates for user ' + user + ':'
		
		outFile.append(outText + '\n')
	
		
		for(row = clickRow; row < new_row_count; row++) {
			
			Columns = Rows.get(row).findElements(By.tagName('td'))
			
			line = Columns.get(0).getText()
			
			println(row + ':' + line)
				
			if(!line.isNumber()) {
				continue
			}
		
			println('line is ' + line)
			
			firstName = Columns.get(1).getText()
			
			println('firstName is ' + firstName)
			
			lastName = Columns.get(2).getText()
			
			println('lastName is ' + lastName)
			
			pct = Columns.get(8).getText()
			
			println('Table pct is ' + pct)
			
			tablePct = pct.toInteger()
						
			println('Table percent is ' + tablePct)
					
			WebElement spyGlass = Columns.get(9).findElement(By.tagName("img"))
			
			spyGlass.click()
			
//			WebUI.delay(5)
			
			WebUI.switchToWindowIndex(2)
			
			WebUI.waitForPageLoad(30)
			
			paid_volunteer = WebUI.getText(findTestObject('Object Repository/Journey Partner Profile/Matching/text_Paid and Volunteer Positions Match Text'))
			
			popupMatch = WebUI.getText(findTestObject('Object Repository/Journey Partner Profile/Matching/text_Popup Match Percent')).replace('%','')
			
			popupMatch = popupMatch.trim()
			
			println(popupMatch)
			
			popupPct = popupMatch.toInteger()
			
			println(popupPct)
			
			if(tablePct == 100 || popupPct != tablePct || paid_volunteer == 'Open' || paid_volunteer == 'Volunteer/self-supported position') {
				errorText = ' ##### ERROR'
				GlobalVariable.testCaseErrorFlag = true
			} else {
				errorText = ''
			}
			outText = firstName + ' ' + lastName + ' has a table match percentage of ' + tablePct + ' and popup of ' + popupPct + '. Paid & Volunteer positions shows ' + paid_volunteer + '. ' + errorText
			
			outFile.append(outText + '\n')
			
			testPaidVolunteer()
			
//			WebUI.closeWindowIndex(2)
			
//			WebUI.switchToWindowIndex(1)
		}
	}
	
	if(switchTo) {
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
		WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [('varSite') : site], FailureHandling.STOP_ON_FAILURE)
	}
	
	WebUI.closeBrowser()
}

if(errorCount == 0) {
	errorFile.delete()
}

def testPaidVolunteer() {
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
		
		if(sum > 2) {
			outText = ('## ERROR: ' + outText)
			errorFile.append(outText + '\n')
			errorCount++
		}
		
		outFile.append(outText + '\n')
		
		WebUI.closeWindowIndex(2)
		
		WebUI.switchToWindowIndex(1)
				
	}
}