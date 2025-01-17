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

WebUI.openBrowser('https://education.missionnext.org/signup/candidate')

WebDriver driver = DriverFactory.getWebDriver()

// Find all of the tooltips on the page
errorMsgs = driver.findElements(By.className('col-sm-offset-3 col-sm-9 text-danger'))

println(errorMsgs.size())

/*
// Get the tooltip locations
for(it in tooltips) {
	if (it.isDisplayed()) {
		ttText = it.getAttribute('data-original-title')
		
		ttLocation = it.getLocation()

		yLocation = ttLocation.getY()

		xLocation = ttLocation.getX()

		fieldTooltips.put(ttText, yLocation)
	}
}

// If tooltips were found, get all of the field labels that are left of the tooltip locations
if (fieldTooltips.size() > 0) {
	
	rightBorder = xLocation

	fieldLabels = [:]

	labels = driver.findElements(By.xpath('//label'))

	labels.each({
			label = it.getText().replace('*','')	//Strip off the required field indicator

			labelLocation = it.getLocation()

			xLocation = labelLocation.getX()

			if (xLocation < rightBorder) {
				yLocation = labelLocation.getY()

				fieldLabels.put(label, yLocation)
			}
		})

//	Match up the tooltips with their associated field labels based on the field and toolip's y (vertical) location	
	for (def label : fieldLabels) {
		
		y = label.value

		for (def tooltip : fieldTooltips) {
			
			if (((tooltip.value == y) || (tooltip.value == (y + 1))) || (tooltip.value == (y - 1))) {
				
				tooltipMap.put(label.key, tooltip.key)

				break
			}
		}
	}
}

return tooltipMap
*/