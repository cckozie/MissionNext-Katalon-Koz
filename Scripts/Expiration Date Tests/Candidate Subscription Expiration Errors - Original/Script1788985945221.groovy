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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import java.io.File as File

webSites = ['Journey', 'Education', 'QuickStart']	//

editCount = 0

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

WebUI.waitForPageLoad(10)

for(site in webSites) {

	outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + 'for ' + site + '.txt')
	
	dataFile = new File('/Users/cckozie/Documents/MissionNext/Test Data/' + 'Subscription Expiration Errors on ' + site + '.csv')
	
	dataFile.write('Running ' + myTestCase + ' for ' + site + '.\n\n')
	
	dataFile.append('line,id,days,last,first,username,last in,expires,should be,apps')
	
	dataFile.append('\n')
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Website'), site, false)
	
	WebUI.delay(1)
	
	WebUI.selectOptionByLabel(findTestObject('Object Repository/Admin/Ad Subscription Utility/select_Role'), 'Candidate', false)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad Subscription Utility/btn_Add Subscription Entry'))
	
	WebUI.waitForPageLoad(30)
	
	myXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/form/p[3]/table/tbody'
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath(myXpath))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size()
	
	println(row_count + ' rows found')
	
	mmdd = '-12-31'
	
	//row_count = 30
	
	for (row = 1; row < row_count; row++) {
	    //for (row = 1; row < 36; row++) {
	    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	    lineNum = Columns.get(0).getText().trim()
	
	    if (lineNum.contains('Account')) {
	        break
	    }
	    
	    id = Columns.get(1).getText().trim()
	
	    days = Columns.get(2).getText().trim()
	
	    lastName = Columns.get(3).getText().trim()
	
	    lastName = lastName.replace(',', '')
	
	    firstName = Columns.get(4).getText().trim()
	
	    firstName = firstName.replace(',', '')
	
	    apps = Columns.get(6).getText()
	
	    myApps = apps.replace(',', '')
	
	    lastIn = Columns.get(7).getText().trim()
	
	    expireDate = Columns.get(8).getText().trim()
	
	    username = Columns.get(10).getText().trim()
	
	//    println((((((((((((((((lineNum + ', ') + id) + ', ') + days) + ', ') + lastName) + ', ') + username) + ', ') + firstName) + 
	//        ', ') + lastIn) + ', ') + expireDate) + ', ') + myApps)
	
	    int loginYear = lastIn as int
	
	    int expireYear = ((expireDate.substring(0, 4)) as int)
	
	    int endYear = loginYear + 5
	
	    md = expireDate.substring(4, 10)
	
	//    println('md is ' + md)
	
	    if ((expireYear != endYear) || (md != mmdd)) {
	        
	        newDate = (endYear + mmdd)
			
			dataFile.append(lineNum + ', ' + id + ', ' + days + ', ' + lastName + ', ' + firstName + ', ' + username + ', ' + lastIn + ', ' + expireDate + ', ' + newDate + ', ' + myApps)
			
			dataFile.append('\n')
	    }
	}
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/u_Admin Section Home'))
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))
	
	WebUI.waitForPageLoad(10)

}
