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

outFile = GlobalVariable.outFile

fieldTooltips = [:]

tooltipMap = [:]

WebDriver driver = DriverFactory.getWebDriver()

// Find all of the tooltips on the page
tooltips = driver.findElements(By.className('field-tooltip'))
println(tooltips.size())

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

fieldTooltips.each {
	println(it.key + ':' + it.value)
}
// If tooltips were found, get all of the field labels that are left of the tooltip locations
if (fieldTooltips.size() > 0) {
	
	rightBorder = xLocation

	fieldLabels = [:]

	labels = driver.findElements(By.xpath('//label'))

//	showMore = driver.find_elements_by_class_name('getPhotos')
//	onlyVisible = filter(lambda x: x.is_displayed(), showMore)
	
	println('labels count = ' + labels.size())

	labels.each{
		if(it.isDisplayed()) {	//Eleminate labels on other tabs
	
			label = it.getText().replace('*','')	//Strip off the required field indicator
			
			label = label.trim()
	
			label = label.replace('?','')	//Strip off question marks
	
			labelLocation = it.getLocation()
	
			xLocation = labelLocation.getX()
	
			if (xLocation < rightBorder) {
				yLocation = labelLocation.getY()
	
				fieldLabels.put(label, yLocation)
			}
		}
	}
	
	println('fieldLabels count = ' + fieldLabels.size())
	
	
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
tooltipMap.each {
	println(it.key + ':' + it.value)
}

fieldTooltips.each{
	ttText = it.key
	if(tooltipMap.find{it.value == ttText} == null) {
		outText = ('----- Unable to find a label for the tooltip with text "' + ttText + '".')
		println(outText)
		outFile.append(outText + '\n')
	}
}

return tooltipMap
