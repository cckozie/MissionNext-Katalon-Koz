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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Journey Candidate 05'], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Availability'))

JavascriptExecutor executor = (JavascriptExecutor) driver;

wildcards = [:]

checkboxes = [:]

values = []

List<WebElement> inputs = driver.findElements(By.tagName('input'))

println(inputs.size())

i = 0

for(option in inputs) {
	
	name = option.getAttribute('name')
	
	type = option.getAttribute('type')
	
	value = option.getAttribute('value')
	
	if(type == 'checkbox' && value.contains('!')) {
		
		WebElement optionLabel = option.findElement(By.xpath("following-sibling::*"))
		
		label = optionLabel.getAttribute('innerHTML')

		values.add(label)
		
		WebElement parent = option.findElement(By.xpath("./.."));
		
		WebElement gParent = parent.findElement(By.xpath("./.."));
		
		WebElement ggParent = gParent.findElement(By.xpath("./.."));
		
		WebElement gggParent = ggParent.findElement(By.xpath("./.."));
		
		WebElement fieldLabel = gggParent.findElement(By.tagName('label'))
		
		fLabel = fieldLabel.getAttribute('innerHTML').replace('*','')
		
		if(fLabel.contains('&')) {
			
			segments = fLabel.split(' ')
			
			fLabel = segments[0] + ' & ' + segments[2] + ' ' + segments[3]
		}
		
		values = []
		
		if(checkboxes.containsKey(fLabel)) {
			
			values = checkboxes.get(fLabel)
		} 
		values.add(label)
		
		checkboxes.put(fLabel, values)
		
		values = []
	}
}

wildcards.each {
	println(it.key + ':' + it.value)
}

checkboxes.each {
	println(it.key + ':' + it.value)
}
