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

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.csv')
//outFile2 = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '2.csv')
//outFile1 = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

outFile.write('User ID,Username,Email,First Name,Last Name,Last Login,End Date,Calculated,Errors\n')
//outFile1.write('User ID,Username,Email,Created,Updated,Last Login,End Date,Calculated,Errors\n')
//outFile2.write('User ID,Username,Email,Created,Updated,Last Login,End Date,Calculated,Errors\n')

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

WebUI.waitForPageLoad(10)

//WebUI.selectOptionByValue(findTestObject('Object Repository/Admin/Ad User Viewer Utility/select_Website'), 'Journey', false)
	
WebUI.selectOptionByValue(findTestObject('Object Repository/Admin/Ad User Viewer Utility/select_Role'), 'Candidate', false)

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/button_Go'))

WebUI.delay(1)

WebUI.click(findTestObject('Object Repository/Admin/Ad User Viewer Utility/button_Search'))

WebUI.waitForPageLoad(60)

WebDriver driver = DriverFactory.getWebDriver()

tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/table/tbody'
WebElement Table = driver.findElement(By.xpath(tableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

candidates = [:]

print('There are ' + row_count + ' candidates.')

for (row = 1; row < row_count; row++) {
		
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	userID = Columns.get(1).getText()
	
	lastName = Columns.get(3).getText()
	
	firstName = Columns.get(4).getText()
	
	username = Columns.get(11).getText()
	
	email = Columns.get(12).getText()
	
	values = [username,firstName,lastName,email]
	
	candidates.put(userID,values)
}

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Admin Section Home'))

WebUI.delay(2)

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

WebUI.waitForPageLoad(30)

count = 1

for(it in candidates) {
	
	myKey = it.key
	
	myValue = it.value
		
	tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/table[3]/tbody'
	
	Table = driver.findElement(By.xpath(tableXpath))
	
	Rows = Table.findElements(By.tagName('tr'))
	
	row_count = Rows.size()
	
	found = false
		
	if(row_count > 0) {
	
		for (row = 1; row < row_count; row++) {
		    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
			
			userID = Columns.get(0).getText()
			
			values = candidates.get(userID, null)
			
			if(values != null) {
				
				println(values)
		
				lastLogin = Columns.get(9).getText().substring(0,10)
				
				endDate = Columns.get(12).getText().substring(0,10)
				
				println(endDate)
			
				parts = lastLogin.split('-')
		
				year = parts[0]
				
				println(year)
				
				year =  year.toInteger() + 5
				
				println(year)
				
				String expires = year + '-12-31'
				
				if(expires == endDate) {
					status = ''
				} else {
					status = 'ERROR'
				}
				
				user = values[0]
				
				firstName = values[1]
				
				lastName = values[2]
				
				email = values[3]
				
				outFile.append(userID + ",'" + user + ",'" + email + ",'" + firstName + ",'" + lastName + "','"+ lastLogin + "','" + endDate + "','" + expires + "'," + status + "\n")
/*				
				count++
				if(count > 5 ) {
					System.exit(0)
				}
*/
			}
		}
	}
}
WebUI.closeBrowser()

