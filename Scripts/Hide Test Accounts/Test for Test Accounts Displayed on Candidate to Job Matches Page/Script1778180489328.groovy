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
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader as ExecutionProfilesLoader
import org.openqa.selenium.interactions.Actions;

debug = true

siteUser = ['Journey' : ['Journey Candidate 15', '//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody'],
	 	  'Education' : ['Education Candidate 14', '//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody']]

site = 'Journey'

matchType = 'Job'

user = 'Journey Candidate 15'

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

myTestCase += '-' + GlobalVariable.host

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

//siteUser.each {
for(it in siteUser) {
	site = it.key
	
	user = it.value[0]
	
	jobTableXpath = it.value[1]
	
	outFile.append('\nTesting for test accounts found on ' + site + ' candidate job matches page.\n')

	new ExecutionProfilesLoader().loadProfile(user)
	
	candidateUsername = GlobalVariable.username
	
	candidatePassword = GlobalVariable.password
	
	WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : user, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.waitForPageLoad(60)
	
	userTab = WebUI.getWindowIndex()
	
	WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_View Job Matches'))
	
	WebUI.waitForPageLoad(30)
	
//	jobTableXpath = '//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody'
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath(jobTableXpath))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size() - 8
	
	println(row_count)
	
	testFoundCount = 0
	
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
				
				agency = Columns.get(2).getText().toLowerCase()
				
				println('agency is ' + agency)
				
				if(agency.contains('test')) {
					outText = '##### The agency ' + agency + ' on line ' + jobLine + ' contains the word "test".'
					outFile.append(outText + '\n')
					testFoundCount++
				}
					
				jobTitle = Columns.get(3).getText()
		
				println('jobTitle is ' + jobTitle)
	        }
	    }
	}
	
	if(testFoundCount > 0) {
		outFile.append('\n' + testFoundCount + ' instances of "test" were found on ' + site + '.\n')
	} else {
		outFile.append('\n' + ' no instances of "test" were found on ' + site + '.\n')
	}
	
	WebUI.closeBrowser()
}
