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

if (binding.hasVariable('varUsername')) {
	username = varUsername
} else {
	username = GlobalVariable.username
}

if (binding.hasVariable('varType')) {
	type = varType	//Education or Journey (for now)
} else {
	type = 'Education'
}

if (binding.hasVariable('varRole')) {
	role = varRole	//Candidate, Organization, or Agency
} else {
	role = 'Organization'
}

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

//WebUI.navigateToUrl('https://ad.explorenext.org')
WebUI.navigateToUrl(url)

WebUI.setText(findTestObject('Admin/Ad Login/input_Username'), 'chriskosieracki')

WebUI.setEncryptedText(findTestObject('Admin/Ad Login/input_Password'), 'fAJOXt1JExHva3VUYg96Og==')

WebUI.click(findTestObject('Admin/Ad Login/btn_Submit'))

WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/a_Subscription Information  Administration'))

WebUI.click(findTestObject('Admin/Ad Subscription Utility/select_Website'))

WebUI.delay(1)

WebUI.click(findTestObject('Admin/Ad Subscription Utility/option_' + type))

WebUI.click(findTestObject('Admin/Ad Subscription Utility/select_Role'))

WebUI.delay(1)

WebUI.click(findTestObject('Admin/Ad Subscription Utility/option_' + role))

WebUI.click(findTestObject('Admin/Ad Subscription Utility/btn_Add Subscription Entry'))

WebUI.delay(2)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//p[4]/table'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

found = false

for (row = 1; row < row_count; row++) {
    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

    user = Columns.get(8).getText()

    if (user.indexOf(username) >= 0) {
		
		found = True
		
        outText = 'Creating subscription for ' + username

		println('=====> '+ outText)
		
		if(writeFile) {
			outFile.append(outText + '\n')
		}
		
        Columns.get(4).click()
		
		WebUI.delay(2)
		
		WebUI.click(findTestObject('Admin/Ad Subscription Utility/a_Add a subscription record'))

		WebUI.delay(2)
		
		WebUI.click(findTestObject('Object Repository/Admin/Ad Subscription Utility/btn_Add Subscription Entry'))
		
		WebUI.delay(2)
		
		created = WebUI.verifyElementVisible(findTestObject('Object Repository/Admin/Ad Subscription Utility/li_Record is successfully added'), FailureHandling.STOP_ON_FAILURE)

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
					
		break
		
    }
	
}

if(!found) {
	
	outText = 'Failed to find user ' + username + ' in the unassigned subscriptions area of the subscriptions page.'
	
	println('=====> '+ outText)
	
	if(writeFile) {
		outFile.append(outText + '\n')
	}

}


WebUI.closeBrowser()