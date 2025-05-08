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

if (binding.hasVariable('varUsername')) {
	username = varUsername
} else {
	username = GlobalVariable.username
}


//Check to see if we're writing printed output also to a file
writeFile = false
println('outFile is ' + GlobalVariable.outFile)
if(GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile
	println(myFile)
	outFile = new java.io.File(myFile)
	writeFile = true
} else {
	println('outFile not defined')
}

if(!GlobalVariable.allow_delete) {
	outText = 'Profile for user indicates to not allow delete.'
	println(outText)
	if(writeFile) {
		outFile.append(outText + '\n')
	}
	KeywordUtil.markFailedAndStop('Profile for user indicates to not allow delete.')
	
}

domain = GlobalVariable.domain

url = 'https://ad.' + domain

WebUI.openBrowser('')

//WebUI.navigateToUrl('https://ad.explorenext.org')
WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)

WebUI.delay(1)

WebUI.click(findTestObject('Admin/Ad User Viewer Utility/button_Find account by Username'))

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//table[2]'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

found = false
	
if(row_count > 0) {

	for (row = 1; row < row_count; row++) {
	    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	    user = Columns.get(2).getText()
	
	    if (user.indexOf(username) >= 0) {
			
			found = true
			
	        outText = 'User ' + username + ' was found. Attempting to delete.'
			
	        println('=====> '+ outText)
			
			if(writeFile) {
				outFile.append(outText + '\n')
			}
	
	        link = Columns.get(0).findElement(By.tagName('a'))
			
			link.click()
		
	        WebUI.delay(2)
	
	        break
			
		}
	}
}

if(found) {
	
	WebUI.delay(1)
	
	WebUI.waitForPageLoad(10)
	
	deletedUser = WebUI.getText(findTestObject('Object Repository/Admin/Ad User Viewer Utility/p_The missionnext Candidate account username'))	
	
	if(deletedUser.indexOf(username) >= 0) {
		
		outText = ('User ' + username + ' was successfully deleted.')
		
		println(outText)
	
		outFile.append(outText + '\n')
	
	} else {
		
		outText = ('Failed to verify that user ' + username + ' was deleted.')
		
		println(outText)
	
		outFile.append(outText + '\n')
	
	}
	
} else {
	
	outText = username + ' was not found to be an active user.'
	
	println('=====> '+ outText)
	
//	if(writeFile) {
		outFile.append(outText + '\n')
//	}

}

WebUI.closeBrowser()

