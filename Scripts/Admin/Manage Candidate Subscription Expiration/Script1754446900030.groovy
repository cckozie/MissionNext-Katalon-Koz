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


// clicking back 3 times goes back to original page


myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
	'/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

dataFile = new File('/Users/cckozie/Documents/MissionNext/Test Data/' + 'Subscription Edits.csv')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

dataFile.write('line,id,days,last,first,last in,expires,change to')

dataFile.append('\n')

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

WebUI.waitForPageLoad(10)

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

//System.exit(0)

for (row = 2; row < row_count; row++) {
//for (row = 1; row < 36; row++) {
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	lineNum = Columns.get(0).getText().trim()
	
	if(lineNum.contains('Account')) {
		break
	}
	
	id = Columns.get(1).getText().trim()
	
	days = Columns.get(2).getText().trim()
	
	lastName = Columns.get(3).getText().trim()
	
	lastName = lastName.replace(',', '')
	
	firstName = Columns.get(4).getText().trim()
	
	firstName = firstName.replace(',', '')
	
	lastIn = Columns.get(7).getText().trim()
	
	expireDate = Columns.get(8).getText().trim()
	
	println(lineNum + ', ' + id + ', ' + days + ', ' + lastName + ', ' + firstName + ', ' + lastIn + ', ' + expireDate)
	
	int loginYear = lastIn as int
	
	int expireYear = expireDate.substring(0,4) as int
	
	int endYear = loginYear + 5
	
	md = expireDate.substring(4, 10)
	
	println('md is ' + md)
	
	if(expireYear != endYear || md != mmdd) {
//		println('Year should be ' + endYear)
		newDate = endYear + mmdd
		println('Expire date should be ' + newDate)
	} else {
		newDate = ''
	}
	
	dataFile.append(lineNum + ', ' + id + ', ' + days + ', ' + lastName + ', ' + firstName + ', ' + lastIn + ', ' + expireDate + ', ' + newDate)
	//line,id,days,lastName,firstName,lastIn,expireDate,newDate)
	dataFile.append('\n')
	
	
//	Columns.get(5).click()
	
//	System.exit(0)
}
/*
	if(orgField == '') { //bottom of table reached
		break
	}

	cndField = Columns.get(2).getText().trim()
	
	weight = Columns.get(4).getText().toString()
	
	single = Columns.get(5).getText().toString()
	
	couple = Columns.get(6).getText().toString()
	
	orgFields.add(orgField)
	
	cndFields.add(cndField)
	
	if(varMatchType == 'Org') { //default in Katalon 'variables' tab
		
		matchValues.put(orgField, [cndField, weight, single, couple])
	
	} else {
		
		matchValues.put(cndField, [orgField, weight, single, couple])
	}
	
}
*/


