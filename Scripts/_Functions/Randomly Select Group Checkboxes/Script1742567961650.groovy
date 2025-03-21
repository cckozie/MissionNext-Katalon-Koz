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
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import javax.swing.*;

// Click on randomly selected elements within a group
outFile = GlobalVariable.outFile

debug = false	//Print debug info

WebDriver driver = DriverFactory.getWebDriver()


//Unselect all selected checkboxes
result = [:]

elements = driver.findElements(By.xpath(varXpath))

println(elements.size())

elements.each {
	if(it.isSelected()) {
		locY = it.getLocation().getY()
		result.put(it, locY)
	}
}

results = [:]

results = result.sort { a, b -> a.value <=> b.value }

results.each {
	scrollAndClick(it.key, it.value)
}

elements = driver.findElements(By.xpath(varXpath))

element_count = elements.size()

Collections.shuffle elements // or with Groovy 3: nameList.shuffle()

result = [:]

for(i = 0; i < varSize; i++) {
	element = elements[i]
	locY = element.getLocation().getY()
	result.put(element, locY)
}

results = [:]

results = result.sort { a, b -> a.value <=> b.value }

results.each {
	scrollAndClick(it.key, it.value)
}

	
def scrollAndClick(elem, y) {
	top = WebUI.getViewportTopPosition()

	bottom = (top + 600)

	if (((y - top) < 150) || ((bottom - y) < 10)) {
		WebUI.scrollToPosition(0, y - 150)

		WebUI.delay(1)
	}
	
	try {
		elem.click();
	} catch (Exception e) {
		printError("Exception Occured - " + e)
	}
}

