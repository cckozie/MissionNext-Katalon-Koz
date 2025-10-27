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
import java.text.SimpleDateFormat

// MODIFIED 10-14-25 TO USE USER INFO INSTEAD OF SUBSCRIPTION INFORMATION BECAUSE OF THE NEW PROCESSING OF TEST ACCOUNTS
// TURNS OUT IT'S NOT A TEST ACCOUNT ISSUE. NEED TO MODIFY SCRIPT TO TEST FOR UNASSIGNED APP.

username = varUsername

type = varType

role = varRole


println('username is ' + username)
println('type is ' + type)
println(('role is ' + role))

//Check to see if we're writing printed output also to a file
writeFile = false
if(GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile
	println(myFile)
	outFile = new java.io.File(myFile)
	writeFile = true
}

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.waitForPageLoad(30)

//WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Admin Section Home'))

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
			
			outText = 'User ' + username + ' was found. Attempting to assign app.'
			
			println(outText)
			
//				if(writeFile) {
//					outFile.append(outText + '\n')
//				}
	
			link = Columns.get(2).findElement(By.tagName('a'))
			
			link.click()
		
			WebUI.delay(2)
	
			break
			
		}
	}
}

if(found) {
	
	WebUI.delay(1)
	
	WebUI.switchToWindowIndex(1)
	
	WebUI.delay(1)
//		WebUI.waitForPageLoad(10)
	
	object = 'Admin/Ad Subscription Utility/a_Add subscription record'
	
	WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
	
	WebUI.delay(1)
	
	// TEST FOR EXISTING APP DESIGNATION HERE
	apps = ['Journey', 'Education', 'Journey Guide', 'Canada', 'Urbana', 'QuickStart']
	
	assigned = false
	for(app in apps) {
		if(WebUI.verifyElementChecked(findTestObject('Object Repository/Admin/Ad Subscription Utility/radio_Journey'), 1, FailureHandling.OPTIONAL)) {
			outText = app + ' has been assigned.'
			
			assigned = true
			
			break
		}
	} 
	
	if(!assigned) {
		outText = '##### ERROR: No app has been assigned. Now assigning ' + type + '.'

		WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/radio_' + type))		
	}
	
	println(outText)
	
	outFile.append(outText + '\n')
	
	//Calculate and set start and end dates
	date = new Date()
	
	start = date.format("yyyy-MM-dd")
	
	year = start.substring(0,4)
	
	month = start.substring(5,7)
	
	day = start.substring(8,)
	
	if(role == 'Candidate') {
		endYear = year.toInteger() + 5
		end = endYear + '-12-31'
	} else {
		endYear = year.toInteger() + 1
		end = endYear + '-' + month + '-' + day
	}
	
	outText = 'Setting Start Date to ' + start + ', and End Date to ' + end + '.'
	
	println(outText)
	
	outFile.append(outText + '\n')
	
	WebUI.setText(findTestObject('Object Repository/Admin/Ad Subscription Utility/input_Start Date'),start)
	
	WebUI.setText(findTestObject('Object Repository/Admin/Ad Subscription Utility/input_End Date'), end)
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/button_Add Subscription Entry'))
	
	WebUI.delay(2)
	
	created = WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/Ad Subscription Utility/li_Record is successfully added'), FailureHandling.STOP_ON_FAILURE)

	println(created)
	
	if(created) {
		
		outText = 'Subscription created for ' + username
		
		println('=====> '+ outText)
		
		if(writeFile) {
			outFile.append(outText + '\n')
		}
		
	} else {
		outText = 'Failed to verify that a subscription was created for ' + username
		
		println('=====> '+ outText)
		
		if(writeFile) {
			outFile.append(outText + '\n')
		}
	}
				
}
	


if(!found) {
	
	outText = 'Failed to find user ' + username + ' in the user list.'
	
	println('=====> '+ outText)
	
	if(writeFile) {
		outFile.append(outText + '\n')
	}

}


WebUI.closeBrowser()