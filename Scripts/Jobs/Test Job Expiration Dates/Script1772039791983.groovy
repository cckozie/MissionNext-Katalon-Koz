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


debug = false

site = 'Journey'

switchToUser = 'office' // office or cktest17jp

switchTo = true

if(!switchTo) {
	username = GlobalVariable.username
	
	user = username
	
	if(username[-3..-1] != '7jp') {
		println('The Execution Profile must be set to "Journey Partner"')
		
		System.exit(0)
	}
} else {
	user = switchToUser
}

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

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + ' Partner-' + user + '.txt')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

jobFile = (filePath + 'jobprofile.txt')

jobFile = (filePath + 'jobprofile.txt')

if(!switchTo) {
	WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:])
} else {
	WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : user , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
}

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

Robot robot = new Robot()

expiredJobs = []

if(row_count > 0) {
	
	for (row = 2; row < row_count + 2; row++) {
		
		println('row is ' + row)
		
		myRow = row + 1
		
		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
		
		jobID = Columns.get(0).getText()
		
		jobCategory = Columns.get(1).getText()
		
		jobTitle = Columns.get(2).getText()
		
		expiration2 = Columns.get(4).getText()
		
		println(expiration2)
		
		category = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[1]/tbody/tr[' + myRow + ']/td[2]/a'
		
		WebElement categoryLink = driver.findElement(By.xpath(category))
		
		categoryLink.click()
		
		WebUI.delay(1)
		
		WebUI.switchToWindowIndex(2)
		
		WebUI.waitForPageLoad(30)
		
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
		
		jobProfileFile = new File(jobFile)
		
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()
		
		String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()
		
		data = data.replace(' ','')
		
		data = data.replace('\t','')
		
		println(data)
		
		expirationPos = data.indexOf('ListingExpiration:')
		
		expiration1 = data.substring(expirationPos + 18,expirationPos + 28)
		
		expireDate = Date.parse("yyyy-MM-dd", expiration1)
		
		println(expiration1)
		
		expired = false
		
		if(todayDate > expireDate) {
			
			expiredJobs.add(jobID)
			
			expired = true
		}
		
		outText = ('Job ID: ' + jobID + ', Category: ' + jobCategory + ', Title: ' + jobTitle + ', Table Expiration: ' + expiration2 + ', Job Expiration: ' + expiration1)
		
		if(expiration2 != expiration1) {
			outText = '***** ERROR ' + outText
		}
		
		if(expired) {
			outText = '--EXPIRED ' + outText
		}
		
		outFile.append(outText + '\n')
		
		WebUI.closeWindowIndex(2)
		
		WebUI.switchToWindowIndex(1)
		
		Table = driver.findElement(By.xpath(myTable))
		
		Rows = Table.findElements(By.tagName('tr'))
						
	}
}

//Switch to partner dashboard
WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

// Log out of office
if(switchTo) {
	WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [varSite:site], FailureHandling.STOP_ON_FAILURE)
} else {
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Logout'))
}

WebUI.closeBrowser()

if(expiredJobs.size() > 0) {
	
}

