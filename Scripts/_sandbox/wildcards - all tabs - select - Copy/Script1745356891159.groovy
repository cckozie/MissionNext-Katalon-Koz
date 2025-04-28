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

//WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Availability'))

WebUI.waitForPageLoad(10)

WebUI.delay(5)

JavascriptExecutor executor = (JavascriptExecutor) driver;

wildcards = [:]

selects = [:]

values = []

//List<WebElement> inputs = driver.findElements(By.tagName('select'))
List<WebElement> inputs = driver.findElements(By.tagName('option'))

println(inputs.size())

i = 0

for(option in inputs) {
/*	
	name = option.getAttribute('name')
	println(name)
	
	type = option.getAttribute('type')
	println(type)
*/	
	value = option.getAttribute('value')
//	println(value)
	
//	if(type == 'select-one' && value.contains('!')) {
	if(value.contains('!')) {
			
		label = option.getAttribute('innerHTML')
//		println(label)

		values.add(label)
		
		WebElement parent = option.findElement(By.xpath("./.."));
		
		WebElement gParent = parent.findElement(By.xpath("./.."));
		
		elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gParent)
		attrs = elementAttributes.toString()
//		println(attrs)

		WebElement ggParent = gParent.findElement(By.xpath("./.."));
		
		elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	ggParent)
		attrs = elementAttributes.toString()
//		println(attrs)
		
		List<WebElement> children = ggParent.findElements(By.xpath("./child::*"));
		println(children.size())
		
		for(child in children) {
			if(child.getAttribute('class') == 'col-sm-3') {
//				println(child.getAttribute('innerHTML'))
				WebElement myLabel = child.findElement(By.tagName('label'));
				fLabel = myLabel.getAttribute('innerHTML').replace('*','')
				println(fLabel)
			}
		}
		
		
		values = []
		
		if(selects.containsKey(fLabel)) {
			
			values = selects.get(fLabel)
		} 
		values.add(label)
		
		selects.put(fLabel, values)
		
		values = []
	}
}

wildcards.each {
	println(it.key + ':' + it.value)
}

selects.each {
	println(it.key + ':' + it.value)
}
