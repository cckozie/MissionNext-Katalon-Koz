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

siteUser = ['Journey' : 'Journey Partner 17', 'Education' : 'Education Partner 16']

testAccount = ['temp' : ['TEMP', 'TEST'], 'test' : ['TEST', 'TEMP']]

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

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

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
	
	for(type in testAccount) {
		
		if(type.key == 'temp') {
			typeText = ' non-test '
		} else {
			typeText = ' test '
		}
		
		outFile.append('\n*** Testing for test accounts listed on the' + typeText + site + ' candidate job matches page. ***\n')
		
		retCode = WebUI.callTestCase(findTestCase('_Functions/Change Profile Between Test and Temp'), [('varTestOrTemp'):type.value[0]], FailureHandling.STOP_ON_FAILURE)
		
		outFile.append('--- Return code is ' + retCode + '\n\n')
	
		if(site == 'Journey') {
			WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Candidate Matches'))
		} else {
			WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))
		}
		
		WebUI.switchToWindowIndex(1)
		
		WebUI.waitForPageLoad(60)
		
		testAcctLink = WebUI.verifyElementClickable(findTestObject('Object Repository/Journey Partner Profile/Matching/a_View TEST candidates'), FailureHandling.OPTIONAL)
		
		if(type.key == 'temp' && testAcctLink) {
			outFile.append('ERROR: The link to show test accounts was found on ' + site + '.\n\n')
		} else if(type.key == 'test' && !testAcctLink) {
			outFile.append('ERROR: A link to show test accounts was not found on ' + site + '.\n\n')
		}
		
		if(type.key == 'test' && testAcctLink) {
			outFile.append('Clicking the link to display test accounts on ' + site + '.\n')
			
			WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/a_View TEST candidates'))
		}
			
		WebDriver driver = DriverFactory.getWebDriver()
		
		WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'))
//													   '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/p[1]/table/tbody'
		
		List<WebElement> Rows = Table.findElements(By.tagName('tr'))
		
		int row_count = Rows.size() - 5
		
		testFoundCount = 0
		
		if(row_count > 0) {
		
			for (row = 3; row < row_count + 3; row++) {
				
				println('row is ' + row)
				
				List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
			
				line = Columns.get(0).getText()
				
				println('line is ' + line)
				
				if(line == ' ') {
					break
				}
				
				firstName = Columns.get(1).getText()
				
				println('firstName is ' + firstName)
				
				lastName = Columns.get(2).getText()
				
				println('lastName is ' + lastName)
				
				name = (firstName + lastName).toLowerCase()
				
				if(name.contains('test')) {
					testFoundCount++
					if(type.key == 'temp' && name.contains('test')) {
						outText = 'ERROR: Candidate ' + firstName + ' ' + lastName + ' on line ' + line + ' contains the word "test".'
						outFile.append(outText + '\n')
						errorsFlag = true
					}
				}				
			}
			
			if(type.key == 'test') {
				if(testFoundCount == 0) {
					outFile.append('\nERROR: No instances of "test" were found on ' + site + '.\n')
					errorsFlag = true
				} else {
					outFile.append('\n' + testFoundCount + ' instances of "test" were found on ' + site + '.\n')
				}
			} else {
				if(testFoundCount == 0) {
					outFile.append('\nNo instances of "test" were found on ' + site + '.\n')
				} else {
					outFile.append('\nERROR: ' + testFoundCount + ' instances of "test" were found on ' + site + '.\n')
					errorsFlag = true
				}
			}
		} else {
			outFile.append('ERROR: No candidate matches were found on ' + site + '\n')
			errorsFlag = true
		}
		
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
		WebUI.refresh()
		
		WebUI.waitForPageLoad(30)
	}
	
	WebUI.closeBrowser()
}

if(errorsFlag) {
	outFile.renameTo('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '-ERRORS FOUND.txt')
}

