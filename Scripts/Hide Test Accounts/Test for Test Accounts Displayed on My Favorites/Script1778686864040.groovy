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


siteUser = ['Journey' : ['Journey Candidate 15', 'Agency'], 'Education' : ['Education Candidate 14', 'School']]

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

myTestCase += '-' + GlobalVariable.host

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

searchText = 'Test inquires, if any, will show in this list.'

errorsFlag = false

for(it in siteUser) {
	
	site = it.key
	
	myProfile = it.value[0]

	new ExecutionProfilesLoader().loadProfile(myProfile)
	
	candidateUsername = GlobalVariable.username
	
	candidatePassword = GlobalVariable.password
	
	WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : candidateUsername, ('varPassword') : candidatePassword
	        , ('varSite') : site], FailureHandling.STOP_ON_FAILURE) //***
	    
	WebUI.waitForPageLoad(60)
	
	WebUI.delay(2)
	
	orgTab = WebUI.getWindowIndex()
	
	WebUI.click(findTestObject('Object Repository/' + site + ' Candidate Profile/Dashboard/a_My Favorites'))
	
	WebUI.waitForPageLoad(60)
	
	WebUI.delay(1)
		
	testFoundCount = 0
	
	testsShown = WebUI.verifyTextPresent(searchText, false, FailureHandling.OPTIONAL)
	
	if(testsShown) {
		outFile.append('The text "' + searchText + '" is displayed on ' + site + '.\n\n')
	} else {
		outFile.append('ERROR: The text "' + searchText + '" is NOT displayed on ' + site + '.\n\n')
		errorsFlag = true
	}
	
	myTable = '//*[@id="main"]/div/div/div/div[2]/div[2]/table/tbody'
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	boolean exists = driver.findElements(By.xpath(myTable)).size() > 0;
	
	if(exists) {
	
		WebElement Table = driver.findElement(By.xpath(myTable))
		
		List<WebElement> Rows = Table.findElements(By.tagName('tr'))
		
		println(Rows.size() + ' rows found.')
		
		int row_count = Rows.size()
		
		if(row_count < 1) {
			outFile.append('ERROR: No favorites were found on ' + site + '.\n')
			errorFlag = true
		}
	
		found = false
			
		noErrors = true
		
		if(row_count > 0) {
			
			for (row = 0; row < row_count; row++) {
				
				println('row is ' + row)
				
				List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
			
				line = Columns.get(0).getText()
				
				println('line is ' + line)
				
				org = Columns.get(3).getText()
				
				println('Organization is ' + org)
				
				name = org.toLowerCase()
							
				if(name.contains('test')) {
					outText = it.value[1] + ' ' + org + ' on line ' + row + ' contains the word "test".'
					outFile.append(outText + '\n')
					testFoundCount++
					errorFlag = true
				}
			}
		}
		
		
		
		if(testFoundCount > 0) {
			outFile.append('\n' + testFoundCount + ' instances of "test" were found on ' + site + '.\n\n')
		} else {
			outFile.append('\nERROR: No instances of "test" were found on ' + site + '.\n\n')
			errorsFlag = true
		}
	} else {
		outFile.append('ERROR: No favorites were found on ' + site + '\n')
		errorsFlag = true
	}
	
	WebUI.closeBrowser()
}

if(errorsFlag) {
	outFile.renameTo('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '-ERRORS FOUND.txt')
}
