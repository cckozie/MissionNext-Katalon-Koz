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

if(varSite == 'Journey') {
	siteValue = '3'
} else {
	siteValue = '6'
}

//Check to see if we're writing printed output also to a file
writeFile = false

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(10)

if (GlobalVariable.outFile != '') {
    String myFile = GlobalVariable.outFile

    println(myFile)

    outFile = new File(myFile)

    writeFile = true
}

object = 'Object Repository/Admin/AD Matching/a_Matching Schemes'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Admin/AD Matching/select_Website'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'selectOptionByValue', ('varObject') : object, ('varParm1') : siteValue], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Admin/AD Matching/radio_' + varMatchType + '-2-Candidate'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

object = 'Object Repository/Admin/AD Matching/btn_Go'
WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath('(.//*[normalize-space(text()) and normalize-space(.)="Start Again"])[1]/following::table[1]'))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

println(row_count + ' rows found')

matchValues = [:]

orgFields = []

cndFields = []

for (row = 2; row < row_count; row++) {
    List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
    orgField = Columns.get(1).getText().trim()

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

/*
matchValues.each{ 
        println(it.key + ' = ' + it.value)
}
*/
WebUI.click(findTestObject('Object Repository/Admin/Ad Main/u_Admin Section Home'))

WebUI.closeBrowser()

return matchValues

WebUI.closeBrowser()
