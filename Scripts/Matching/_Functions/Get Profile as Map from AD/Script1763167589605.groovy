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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
//import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

username = varUsername
println(username)

emailAddress = varEmail
println(emailAddress)

searchType = varSearchType
println(searchType)

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [('varUsername') : ''], FailureHandling.STOP_ON_FAILURE)

mainPage = WebUI.verifyElementPresent(findTestObject('Admin/Ad Main/a_User Information  Administration'), 1, FailureHandling.OPTIONAL)

if(mainPage) {
	WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

	WebUI.waitForPageLoad(10)
}

if(searchType == 'username') {
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)

	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))

} else {
	
	WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Email Address'), emailAddress)
	
	WebUI.delay(1)
	
	WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Email Address'))

}

WebUI.waitForPageLoad(10)

//println('On AD search results window index is ' + WebUI.getWindowIndex())

userFound = WebUI.verifyElementPresent(findTestObject('Object Repository/Admin/Ad User Viewer Utility/a_Top Line Username'),
	3, FailureHandling.OPTIONAL)

if(userFound) {
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad User Viewer Utility/a_Top Line Username'))
	
	WebUI.delay(1)
	
	WebUI.switchToWindowIndex(1)
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/table/tbody'
	//tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/table/tbody'
	//tableXpath = "(.//*[normalize-space(text()) and normalize-space(.)='Edit this profile'])[1]/following::table[1]"
	//tableXpath = '/html/body/center/table/tbody/tr[2]/td[2]/table/tbody/tr[3]/td/table/tbody/tr/td[3]/table/tbody/tr/td[2]/span/div/table'
	
	// Assuming 'myTestObject' is an existing TestObject in your Object Repository or created dynamically
	//myTestObject = 'Object Repository/temp/table_Profile' //findTestObject('Object Repository/Page_Name/Element_Name')
	
	// Convert the TestObject to a WebElement
	//element = WebUiCommonHelper.findWebElement(findTestObject(myTestObject), 1)
	
	WebElement Table = driver.findElement(By.xpath(tableXpath))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size()
	
	println(row_count)
	
	selections = [:]
	
	if (row_count > 0) {
		for (row = 3; row < row_count; row++) {
			println('row is ' + row)
				
			List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
			
			colCount = Columns.size()
			
			println(colCount)
			
			if(colCount == 3) {
				println(Columns.get(0).getText() + ',' +  Columns.get(1).getText() + ',' + Columns.get(2).getText())
				
				name = Columns.get(1).getText()
				
				String value = Columns.get(2).getText()
				
				selections.put(name, value)
				
				println(name + ':' + value)
				
		 
			}
			
		}
	}
	println('\n-----------------------------\n')
	
	profile = [:]
	
	WebUI.delay(2)
	for(selection in selections) {
		 myValues = []
		 myValue = selection.value.replace('\n',',')
		 values = myValue.split(',')
		 for(value in values){ 
			 myValues.add(value.toString())
		 }
		 println(selection.key + ':' + myValues)
		 profile.put(selection.key, myValues)
	}
	
	
	return profile
}

return null

