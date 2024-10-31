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
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By

// Ensure that we are using the correct execution profile
username = GlobalVariable.username

if (username != 'cktest07jp') {
	println('The Execution Profile must be set to "Journey Partner"')

	System.exit(0)
}

ministriesList = []

if (binding.hasVariable('varMinistries')) {
	ministriesList = varMinistries
}

if(ministriesList.size()  == 0) {
	println('EXITING BECAUSE NO MINISTRIES CHECKBOXES ARE TO BE SET')
	System.exit(9)
}

WebUI.callTestCase(findTestCase('_Functions/Profile Log In to Journey'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Ministry Prefs Tab/a_Ministry Prefs'))

WebDriver driver = DriverFactory.getWebDriver()

allCheckboxes = driver.findElements(By.xpath('//input[@id=\'profile_group-1446522088.64_ministry_preferences\']'))

element_count = allCheckboxes.size()

println(element_count)

count = 0

element_count = allCheckboxes.size()

println('count is ' + element_count)

selected = []

//Build list of selected ministries
for(element in allCheckboxes) {
	
	myValue = element.getAttribute("value")
	
	if(element.isSelected()) {
		
		selected.add(myValue)	
		
	}
}

errorCount = 0

//println(selected)
//println(ministriesList)
ministriesList.each({
	
	if(!(it in selected)) {
		println(it + ' was in the ministries list but was not a selected.')
		errorCount ++
	}
})

selected.each({
	
	if(!(it in ministriesList)) {
		println(it + ' was selected but not in the ministries list')
		errorCount ++
	}
})

println('There were ' + errorCount + ' selection error(s) found.')
