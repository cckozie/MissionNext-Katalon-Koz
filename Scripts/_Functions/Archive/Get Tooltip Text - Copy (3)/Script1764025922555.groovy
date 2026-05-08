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

debug = true

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
		
		if(debug) {
			println(ttText + ':' + yLocation)
		}
	}
}

fieldTooltips.sort{ -it.value }

if(debug) {
	fieldTooltips.each {
		println(it.key + ':' + it.value)
	}
}

// If tooltips were found, get all of the field labels that are left of the tooltip locations
if (fieldTooltips.size() > 0) {
	
	rightBorder = xLocation
	
	println('rightBorder is ' + rightBorder)

	fieldLabels = [:]
	
	labelsY = []

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
			
//			println('xLocation of ' + label + ' is ' + xLocation)
			
			if (xLocation < rightBorder) {
				yLocation = labelLocation.getY()
	
				fieldLabels.put(label, yLocation)
				labelsY.add(yLocation)
				myLabel = label
			}
		}
	}
	
	println('fieldLabels count = ' + fieldLabels.size())
	
	if(debug) {
		fieldLabels.each { 
			println(it.key + ':' + it.value)
		}
	}
	
	fieldLabels.sort{ -it.value }
	
//	Match up the tooltips with their associated field labels based on the field and toolip's y (vertical) location	
//	results = [:]
	i = 0
	println('Count is ' + fieldTooltips.size())
//	fieldTooltips.each{
	println(myLabel + ':' + ttText)
	if(fieldTooltips.size() == 1 && fieldLabels.size() == 1) {
		tooltipMap.put(myLabel,ttText)
	} else {
		for(it in fieldTooltips) {
			println('it is ' + it)
			println('labels Y is ' + labelsY)
			kY = it.key
			tY = it.value
			tYi = tY.toInteger() + 4
			if(fieldLabels.size() > 1) {
				println('tYi is ' + tYi)
//				lY = labelsY[i+1]
				lY = labelsY[i]
				lYi = lY.toInteger()
				println('lYi is ' + lYi)
				diff = Math.abs(tYi - lYi)
				println('diff Y is ' + diff)
				if(diff > 5) {
					while(tYi > lYi) {
						i = i + 1
						println('i is ' + i)
						if(i+1 >= fieldLabels.size()) {
							break
						}
//						lY = labelsY[i+1]
						lY = labelsY[i]
						lYi = lY.toInteger()
						println('lYi is ' + lYi)
					}
				}
				lY = labelsY[i]
				println('lY is ' + lY)
				myKey = fieldLabels.find{ it.value == lY }?.key
			} else {
				myKey = label
				lYi = tYi - 4
			}
			if(debug) {	
				println('tooltip ' + kY + ' is at ' + tY + ' and label ' + myKey + ' is at ' + lYi)
			}
			tooltipMap.put(myKey,kY)
		}
	}
}

if(debug) {
	tooltipMap.each {
		println(it.key + ':' + it.value)
	}
}

return tooltipMap
