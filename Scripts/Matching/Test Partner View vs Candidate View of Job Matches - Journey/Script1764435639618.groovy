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


debug = true

pages = GlobalVariable.matchPages

site = 'Journey'

matchType = 'Job' // Org or Job

user = 'joshua.r@jesusfilm.org' // office or cktest17jp  joshua.r@jesusfilm.org

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
	
	maxMatches = 100
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

//WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : user , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(60)

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Job Matches'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(60)

WebUI.delay(1)

jobTableXpath = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[1]/tbody'

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath(jobTableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size() - 2

for(row = 2; row < row_count; row++) {
	
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	jobTitle = Columns.get(2).getText()
	
	matchButton = Columns.get(7)
	
	WebElement link = matchButton.findElement(By.tagName('a'))
	
	link.click()

	matchTable = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody'
	
	
	Table = driver.findElement(By.xpath(matchTable))
	
	Rows = Table.findElements(By.tagName('tr'))
	
	int match_row_count = Rows.size() - 6
	
	if(match_row_count > 0) {
	
		for (row = 3; row < row_count + 3; row++) {
			
			println('row is ' + row)
			
			Columns = Rows.get(row).findElements(By.tagName('td'))
		
			line = Columns.get(0).getText()
			
			println('line is ' + line)
			
			firstName = Columns.get(2).getText()
			
			println('firstName is ' + firstName)
			
			lastName = Columns.get(1).getText()
			
			println('lastName is ' + lastName)
			
	//		if(lastName != 'Knolle') {
	//			continue
	//		}
			
			pct = Columns.get(8).getText()
			
			println('Table pct is ' + pct)
			
			tablePct = pct.toInteger()
			
			println('Table percent is ' + tablePct)
			
			spyGlass = Columns.get(9)
			System.exit(0)
		}
	}
}
//Switch to partner dashboard
WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

// Log out of office
WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out 2'), [varSite:site], FailureHandling.STOP_ON_FAILURE)

if(noErrors) {
	errorFile.delete()
}

WebUI.closeBrowser()


////// FUNCTIONS
def ifPrint(def msg) {
    if (debug) {
        println(msg)
    }
}
