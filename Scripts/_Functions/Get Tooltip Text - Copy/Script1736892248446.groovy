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
//import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
//import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

format = varFormat
if(format == 'CSV') {
	CSV = true
} else {
	CSV = false
	tooltipMap = [:]
}

fieldTooltips = [:]

WebDriver driver = DriverFactory.getWebDriver()

tooltips = driver.findElements(By.className('field-tooltip'))

tooltips.each({
		if (it.isDisplayed()) {
			ttText = '"' + it.getAttribute('data-original-title') + '"'
			if(CSV) {
				ttText = ttText.replace('\n','-') //Because some tooltips have lots of carriage returns 
			}
			
			ttLocation = it.getLocation()

			yLocation = ttLocation.getY()

			xLocation = ttLocation.getX()

			fieldTooltips.put(ttText, yLocation)
		}
	})

println(fieldTooltips)

if (fieldTooltips.size() > 0) {
	tooltipList = ''
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

println(fieldLabels)

	for (def label : fieldLabels) {
		name = '"' + label.key + '"'

		y = label.value

		for (def tooltip : fieldTooltips) {
			if (((tooltip.value == y) || (tooltip.value == (y + 1))) || (tooltip.value == (y - 1))) {
				tooltipList = tooltipList + name + ',' + tooltip.key + '\n'
				tooltipMap.put(name, tooltip.key)
				break
			}
		}
	}
	
	println(tooltipMap)
	
	if(CSV) {
		if (tooltipList.length() > 1) {
			outText = ',"NOTE: LINE FEEDS IN THE TOOLTIP TEXT HAVE BEEN REPLACED WITH HYPHENS FOR READABILITY"\n\n'
	
			outText = outText + '"FIELD LABEL"' + ',' + '"TOOLTIP TEXT"' + '\n' + tooltipList
	
			outFile = new File(file)
	
			outFile.write(outText)
		}
	} else {
		return tooltipMap
	}
}
