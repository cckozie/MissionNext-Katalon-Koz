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
import java.io.File as File
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration


myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

screenshotFolder = '/Users/cckozie/Documents/MissionNext/Test data/Screenshots/Unassigned Users/'

dataFile = new File('/Users/cckozie/Documents/MissionNext/Test Data/' + 'Unassigned Candidate List.csv')

//dataFile.write('Username,Email,Last Login,Start Date,End Date,New End Date,Updated\n')

String[] usernames = new File('/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/input temp/unassigned candidates copy.txt')

println(usernames)

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

WebUI.waitForPageLoad(60)

WebUI.selectOptionByValue(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Role'), 'Candidate', false)

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/radio_Unassigned'))

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/button_Go'))

WebUI.waitForPageLoad(60)

WebDriver driver = DriverFactory.getWebDriver()

//WebElement Table = driver.findElement(By.xpath('//p[4]/table'))
WebElement Table = driver.findElement(By.xpath('/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/p[3]/table'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

unassignedUsers = []

print('There are ' + row_count + 'unassigned candidates.')

for (row = 1; row < row_count; row++) {
//for (row = 1; row < 5; row++) {
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

	user = Columns.get(9).getText()
	
	unassignedUsers.add(user)
}

unassignedUsers.each {
	println(it)
}

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Admin Section Home'))

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

for(username in unassignedUsers) {
	
//	username = 'abhallman'
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))
	
	WebUI.delay(1)
	
//	WebDriver driver = DriverFactory.getWebDriver()
	
//	WebElement Table = driver.findElement(By.xpath('//table[2]'))
	Table = driver.findElement(By.xpath('//table[2]'))
	
//	List<WebElement> Rows = Table.findElements(By.tagName('tr')) 
	Rows = Table.findElements(By.tagName('tr'))
	
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
		
		object = 'Admin/Ad User Viewer Utility/a_Add subscription record'
		
		WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction') : 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
		
		WebUI.delay(1)
		
//		WebUI.click(findTestObject('Object Repository/Admin/Ad User Viewer Utility/radio_App Journey'))
		
//		object = 'Admin/Ad User Viewer Utility/input_YYYY-MM-DD_start_date'

		start = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_Start Date'))
		
		end = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_End Date'))
		
		lastLogin2 = WebUI.getText(findTestObject('Object Repository/Admin/Ad Subscription Utility/text_users Table Last Login'))
		
		if(lastLogin != lastLogin2) {
			println('lastLogin is ' + lastLogin)
			println('lastLogin2 is ' + lastLogin2)
			System.exit(0)
		}
		
//		username = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_Username'))
		
//		email = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/text_Email'))
		
		println(start)
		
		println(end)
		
		println(username)
		
		println(lastLogin)
	
		println(email)
		
		year = lastLogin.substring(0,4).toInteger()
		
		endYear = year + 5
		
		endDate = endYear + '-12-31'
		
		println(endDate)
		
		WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/Subscription Edit/radio_App Journey'))
		
		WebUI.setText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/input_YYYY-MM-DD_start_date'), lastLogin)
		
		WebUI.setText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/input_YYYY-MM-DD_end_date'), endDate)
		
		WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/Subscription Edit/button_Add Subscription Entry'))
		
		success = WebUI.verifyTextPresent('Record is successfully added.', false, FailureHandling.OPTIONAL)
		
		println(success)
		
		dataFile.append(username + ',' + email + ', ' + lastLogin + ', ' + start + ', ' +  end + ', ' + endDate + ', ' +  success + '\n')
		
		WebUI.takeFullPageScreenshot(screenshotFolder + username + '_screenshot.png')
		
		WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(0)
		
		WebUI.delay(2)
		

/*
		System.exit(0)
		
		WebUI.click(findTestObject('Object Repository/Admin/Ad User Viewer Utility/button_Add Subscription Entry'))
		
		System.exit(1)
	
		outText = ('User ' + username + ' was successfully deleted.')
		
		println(outText)
	
		outFile.append(outText + '\n')
*/
	} else {
		
		outText = ('Failed to find user ' + username + '.')
		
		println(outText)
	
		dataFile.append(outText + '\n')
	
	}
	
}
WebUI.closeBrowser()

