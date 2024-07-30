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

if (binding.hasVariable('varUsername')) {
	username = varUsername
} else {
	username = 'cktest06ep'
}

println('File is ' + GlobalVariable.outFile)

writeFile = false
if(GlobalVariable.outFile != '') {
	myFile = GlobalVariable.outFile
	outFile	= File(myFile)
	writeFile = true
}

println(outFile)
println(writeFile)

if(writeFile) {
	outFile.append('can you see this?' + '\n')
}


System.exit(0)


WebUI.openBrowser('')

WebUI.navigateToUrl('https://ad.explorenext.org')

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Admin/Ad Main/a_User Information  Administration'))

WebUI.setText(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username'), username)

WebUI.delay(1)

WebUI.click(findTestObject('Admin/Ad User Viewer Utility/input_Find account by Username_beginning'))

WebUI.delay(1)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//table[2]'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

if(row_count > 0) {

	found = false
	
	for (row = 1; row < row_count; row++) {
	    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	    user = Columns.get(2).getText()
	
	    if (user.indexOf(username) >= 0) {
			
			found = true
			
			outText = 'Deleting user ' + username
			
	        println('=====> '+ outText)
			
			if(writeFile) {
				outFile.append(outText + '\n')
			}
	
	        link = Columns.get(0).findElement(By.tagName('a'))
			
			link.click()
	
	        outText = 'User ' + username + ' was deleted.'
			
			println('=====> '+ outText)
			
			if(writeFile) {
				outFile.append(outText + '\n')
			}
	
	        WebUI.delay(2)
	
	        break
	    }
	}
}
	
if(!found) {
	
	outText = 'Unable to find and delete user ' + username

	println('=====> '+ outText)
	
	if(writeFile) {
		outFile.append(outText + '\n')
	}

}


WebUI.closeBrowser()

