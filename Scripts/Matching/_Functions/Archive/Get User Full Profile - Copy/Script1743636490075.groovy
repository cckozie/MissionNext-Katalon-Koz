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


username = varUsername

//Check to see if we're writing printed output also to a file
writeFile = false

WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varUsername') : username], FailureHandling.STOP_ON_FAILURE)

WebUI.delay(5)

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}

domain = GlobalVariable.domain

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('//*[@id="default-rezult"]/table'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

println(row_count + ' rows found')

fieldValues = [:]

for (row = 1; row < row_count; row++) {
	List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
	rowLoc = Rows.get(row).location
	println(rowLoc)
	
	println(Columns.size() + ' columns')
	for(col = 1; col < Columns.size(); col++) {
		colLoc = Columns.get(col).location
		println(colLoc)
	}
	user = Columns.get(1).getText()
	
	println(user)

	if (user == username) {
		println(Columns.get(1))
		location = Columns.get(1).location
		println(location)
		Columns.get(1).click()
		
		WebUI.delay(5)

//		WebUI.waitForPageLoad(10)

		outText = ('Opening full profile of ' + username)

		println('=====> ' + outText)

		if (writeFile) {
			outFile.append(outText + '\n')
		}
		
		WebUI.click(findTestObject('Object Repository/Admin/API User Profile/btn_Full Profile'))
		
		WebUI.delay(5)
		
		WebElement pTable = driver.findElement(By.xpath('/html/body/div/table'))
		 
		List<WebElement> pRows = pTable.findElements(By.tagName('tr'))
		 
		int pRow_count = pRows.size()
		 
		println(pRow_count + ' profile rows found')
		 
		for (r = 0; r < pRow_count; r++) {

			List<WebElement> header = pRows.get(r).findElements(By.tagName('th'))
			
			field = header.get(0).getText()
			
			List<WebElement> detail = pRows.get(r).findElements(By.tagName('td'))
			
			value = detail.get(0).getText()
			
//			println(field + ' = ' + value)
			
			fieldValues.put(field,value)
		}

		break
	}
}

fieldValues.each {
	println(it.key + ' = ' + it.value)
}

return fieldValues

WebUI.closeBrowser()

