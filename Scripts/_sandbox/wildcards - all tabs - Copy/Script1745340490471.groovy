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

//WebElement Table = driver.findElement(By.xpath('(.//*[normalize-space(text()) and normalize-space(.)="Start Again"])[1]/following::table[1]'))
//WebElements fields = driver.find_elements(By.XPATH, f'//input[@name="{text}"]'

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_My Profile'))

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Tabs/a_Availability'))



JavascriptExecutor executor = (JavascriptExecutor) driver;
/*
List<WebElement> tabs = driver.findElements(By.xpath("//*[@role='tabpanel']"))
println(tabs.size())

for(tab in tabs) {
	hidden = tab.getAttribute('aria-hidden')
	if(hidden == 'false') {
		List<WebElement> fields = tab.findElements(By.tagName('select'))
		println(fields.size())
		for(field in fields) {
			println(field.getAttribute('id'))
		}
	}
}
System.exit(0)
*/
//List<WebElement> groups = driver.findElements(By.xpath("//*[@class='form-group']"))
//List<WebElement> wildcards = driver.findElements(By.xpath("//*[contains(text(),'!')]"))
//println(groups.size())	 

//System.exit(0)
wildcards = [:]
checkboxes = [:]
values = []
List<WebElement> inputs = driver.findElements(By.tagName('input'))
println(inputs.size())
i = 0
for(label in inputs) {
	name = label.getAttribute('name')
	type = label.getAttribute('type')
	value = label.getAttribute('value')
	if(type == 'checkbox' && value.contains('!')) {
		values.add(name)
		values.add(value)

		WebElement groupLabel = label.findElement(By.xpath("following-sibling::*"))
		values.add(groupLabel.getAttribute('innerHTML'))
		wildcards.put(i,values)
		i++
		values = []
	}

	
}
wildcards.each {
	println(it.key + ':' + it.value)
}
/*
		for (def inputField : inputFields) {
			println(inputField.getAttribute('id'))
			
			myName = inputField.getAttribute("name")
			myValue = inputField.getAttribute('value')
			myType = inputField.getAttribute('type')
//			println(myType)
			println('myValue is ' + myValue + ' and myType is ' + inputField.getAttribute('type'))
			if(myValue.contains('!')) {
				if(myType == 'checkbox' || myType == 'radio') {
					WebElement label = inputField.findElement(By.xpath("following-sibling::*"))
					myLabel = label.getAttribute('innerHTML')
				} else {
					myType = 'select'
					List<WebElement> options = inputField.findElements(By.tagName('option'))
					println('there are ' + options.size() + 'options')
					for(option in options) {
						myValue = option.getAttribute('value')
						if(myValue.contains('!')) {
							myLabel = option.getAttribute('innerHTML')
						}
					}
//					println('value is ' + myValue + ', label is ' + myLabel)
//					System.exit(0)
//					myLabel = inputField.getAttribute('innerHTML')
				}

					
//				println('type is ' + myType + ', label is ' + myLabel + ', wildcard value is ' + myValue + '\n\n')
				WebElement parent = inputField.findElement(By.xpath("./.."));
				elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	parent)
				attrs = elementAttributes.toString()
//				println('Parent:' + attrs)
				
				WebElement gParent = parent.findElement(By.xpath("./.."));
				elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gParent)
				attrs = elementAttributes.toString()
//				println('gParent:' + attrs)
				
				WebElement ggParent = gParent.findElement(By.xpath("./.."));
				elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	ggParent)
				attrs = elementAttributes.toString()
//				println('ggParent:' + attrs)
				
				WebElement gggParent = ggParent.findElement(By.xpath("./.."));
				elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	gggParent)
				attrs = elementAttributes.toString()
//				println('gggParent:' + attrs + '\n\n')	
				
				if(myType == 'select') {
					WebElement fieldLabel = gParent.findElement(By.tagName('label'))
					fLabel = fieldLabel.getAttribute('innerHTML')
					println('fLabel:' + fLabel)
					

				} else {				
					WebElement fieldLabel = gggParent.findElement(By.tagName('label'))
					elementAttributes = executor.executeScript('var items = {}; for (index = 0; index < arguments[0].attributes.length; ++index) { items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; return items;',	fieldLabel)
					attrs = elementAttributes.toString()
	//				println(attrs)
	//				println('field label is ' + fieldLabel.getAttribute('innerHTML'))
					fLabel = fieldLabel.getAttribute('innerHTML')
				}
				
				println('field label is ' + fLabel + ', type is ' + myType + ', label is ' + myLabel + ', wildcard value is ' + myValue + '\n')
				

//				break
			}
			
		}

	}
	
}

	//<input id="profile_group-1623986423.799_time_commitment" type="checkbox" name="profile[group-1623986423.799][time_commitment][]" checked="checked" value="Long Term">

*/