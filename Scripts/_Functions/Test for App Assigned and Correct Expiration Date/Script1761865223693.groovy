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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.*

username = varUsername

appAssigned = false

retArray = []	// Return array [app assigned (null if none), role, correct end date T/F]

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [('varUsername') : 'username', ('varNewBrowser') : true], FailureHandling.STOP_ON_FAILURE)

//Check to see if we're writing printed output also to a file
writeFile = false

if(GlobalVariable.outFile != '') {
	myFile = GlobalVariable.outFile
	outFile = new java.io.File(myFile)
	writeFile = true
}

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)

WebUI.delay(1)

WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//table[2]'))
//	Table = driver.findElement(By.xpath('//table[2]'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))
//	Rows = Table.findElements(By.tagName('tr'))

row_count = Rows.size()

found = false
	
if(row_count > 0) {

	for (row = 1; row < row_count; row++) {
		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
		user = Columns.get(2).getText()
		
		email = Columns.get(3).getText()
		
		lastLogin = Columns.get(6).getText()
	
		if (user.indexOf(username) >= 0) {
			
			found = true
			
			link = Columns.get(2).findElement(By.tagName('a'))
			
			link.click()
		
			WebUI.delay(2)
	
			break
			
		}
	}
}

if(found) {
	
	outText = 'User ' + username + ' was found. Checking for App assigned.'
	
	println(outText)
	
	if(writeFile) {
		outFile.append(outText + '\n')
	}
	
	WebUI.delay(1)
	
	WebUI.switchToWindowIndex(1)
	
	WebUI.delay(1)
	
	appXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/p/table/tbody/tr[2]'
	
	WebElement appInfo = driver.findElement(By.xpath(appXpath))
	
	appMsg = appInfo.getAttribute("innerHTML")
	
	if(!appMsg.contains('No app record')) {
		appAssigned = true
		
		app = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_Assigned App'))
	}
	
	if(appAssigned) {
		outText = '+++++ ' + app + ' was assigned to user ' + username
		retArray.add(app)
		
	} else {
		outText = '##### ERROR: NO APP was assigned to user ' + username
		retArray.add(null)
	}
	
	println(outText)
	
	if(writeFile) {
		outFile.append(outText + '\n')
	}
	
	role = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_User Role'))
	
	retArray.add(role)
	
	if(role == 'Candidate' && appAssigned) {
		start = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_User Start Date'))
		
		end = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_User End Date'))
		
		yyyy = start.substring(0,4).toInteger()
		
		endYear = yyyy + 5
		
		calcEnd = endYear.toString() + '-12-31'
		
		if(end == calcEnd) {
			outText = '+++++ The correct end date was applied to user ' + username
			retArray.add(true)
			
		} else {
			outText = '##### ERROR: A wrong end date was applied to user ' + username + '. Correct end date is ' + calcEnd  + ', but ' + end + ' was applied.'
			retArray.add(false)
		}
		
		println(outText)
		
		if(writeFile) {
			outFile.append(outText + '\n')
		}
	}
}

return retArray
