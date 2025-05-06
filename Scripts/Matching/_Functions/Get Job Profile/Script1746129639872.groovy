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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.interactions.Actions as Actions
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.interactions.Actions as Actions


//Check to see if we're writing printed output also to a file
writeFile = false

if (GlobalVariable.outFile != '') {
	String myFile = GlobalVariable.outFile

	println(myFile)

	outFile = new File(myFile)

	writeFile = true
}

WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Object Repository/Admin/API User Profile/btn_Full Profile'))

outText = ('Getting job profile of ' + jobName)

println('=====> ' + outText)

if (writeFile) {
	outFile.append(outText + '\n')
}

WebElement pTable = driver.findElement(By.xpath('/html/body/div/table'))
	
List<WebElement> pRows = pTable.findElements(By.tagName('tr'))
	
int pRow_count = pRows.size()
	
println(pRow_count + ' profile rows found')
	
first = true

values = []

for (r = 0; r < pRow_count; r++) {

	List<WebElement> header = pRows.get(r).findElements(By.tagName('th'))
	
	field = header.get(0).getText()
	
	if(field != '') {
		if(!first) {
			fieldValues.put(myField,values)
		}
		myField = field
		values = []
	}
	
	first = false
	
	List<WebElement> detail = pRows.get(r).findElements(By.tagName('td'))
	
	value = detail.get(0).getText()
	
//	print('row is ' + r + ', header is ' + field + ', value is ' + value)
	pos = value.indexOf("\12")
	if(pos >= 0) {
		myValues = value.split("\12")
		for(val in myValues) {
			values.add(val)
		}
	} else {
		values.add(value)
	}
}

fieldValues.put(myField,values)

found = true

WebUI.delay(2)

WebUI.back()
		
return fieldValues
						
/*
fieldValues.each {
	println(it.key + ' = ' + it.value)
}
*/


