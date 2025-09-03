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

filePath = '/Users/cckozie/Documents/MissionNext/Test Reports/'

outFile = new File(filePath + 'Active Users with No Avatar.csv')

outFile.write("Active ")

inFile = new File(filePath + 'Users with No Avatar.csv')

lines = inFile.readLines()

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

//WebUI.navigateToUrl('https://ad.explorenext.org')
WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

//for(it in lines) {
lines.each {
//	println(it)
	fields = it.split(',')
	if(fields.size() < 3) {
		println('----' + it)
		outFile.append(it + '\n')
	} else {
		if(!fields[3].contains('Username')) {
			username = fields[3]
			println(fields[3])

			WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)
			
			WebUI.delay(1)
			
			WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))
			
			WebUI.delay(1)
			
			WebDriver driver = DriverFactory.getWebDriver()
			
			WebElement Table = driver.findElement(By.xpath('//table[2]'))
			
			List<WebElement> Rows = Table.findElements(By.tagName('tr'))
			
			int row_count = Rows.size()
			
			found = false
			
			println('rows = ' + row_count)
			
			active = WebUI.verifyTextPresent('active', false, FailureHandling.OPTIONAL)
			
			expired = WebUI.verifyTextPresent('expired', false, FailureHandling.OPTIONAL)
				
			closed = WebUI.verifyTextPresent('closed', false, FailureHandling.OPTIONAL)
				
			if(row_count > 1 && !expired && !closed) {
				
				msg = ' is active'
				
				outFile.append(it + '\n')
								
			} else {
				msg = ' is NOT active'
			}
			
			println(it + msg)
			
		} else {
			outFile.append(it + '\n')
		}
	}
}
	
