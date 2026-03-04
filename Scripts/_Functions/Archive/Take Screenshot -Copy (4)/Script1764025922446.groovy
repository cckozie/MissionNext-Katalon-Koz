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
import com.kms.katalon.core.webui.keyword.internal.WebUIAbstractKeyword as WebUIAbstractKeyword
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.By as By
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import java.io.File as File

//Maximize the length of the viewport to include everything down to and including the page footer
//Build the screenshot file name 
//Execute the screenshot and save to local machine
/*
WebUI.openBrowser('https://education.missionnext.org/signup/candidate')
WebUI.maximizeWindow()
*/
WebDriver driver = DriverFactory.getWebDriver()

if(WebUI.verifyTextPresent('Partner Level', false, FailureHandling.OPTIONAL)) {
	user = 'Partner'
} else {
	user = 'Candidate'
}


path = '/Users/cckozie/Documents/MissionNext/Screenshots/'

footer = WebUiCommonHelper.findWebElement(findTestObject('Object Repository/Global/div_Footer'), 1)

// Set the calling parameter values or defaults
if (binding.hasVariable('varExtension')) {
    extension = ('_' + varExtension)
	println('Extension is ' + extension)
} else {
    extension = ''
}

WebUI.waitForPageLoad(30)

screen_size = driver.manage().window().getSize()

width = screen_size.getWidth()

height = screen_size.getHeight()

yLocation = footer.getLocation()

y = yLocation.getY()

zoomFactor = (height / y)

zoomFactor = (zoomFactor - 0.16) //Zoom fudge factor

WebUI.executeJavaScript('document.body.style.zoom=' + zoomFactor, null)

title = (WebUI.getWindowTitle() + extension)

title = title.replace('/', '_')

fileBase = path + user + '_' + title

file = fileBase + '.png'

//println(file)

WebUI.takeScreenshot(file)

WebUI.executeJavaScript('document.body.style.zoom=1.0', null)

fieldTooltips = [:]

tooltips = driver.findElements(By.className('field-tooltip').displayed)

tooltips.each({ 
        if (it.isDisplayed()) {
            ttText = '"' + it.getAttribute('data-original-title') + '"'
			ttText = ttText.replace('\n','-')

            ttLocation = it.getLocation()

            yLocation = ttLocation.getY()

            xLocation = ttLocation.getX()

            fieldTooltips.put(ttText, yLocation)
        }
    })


if (fieldTooltips.size() > 0) {
	tooltipList = ''
    rightBorder = xLocation

//    fieldTooltips.each({ 
//            println(it)
//        })

    fieldLabels = [:]

    labels = driver.findElements(By.xpath('//label'))

    labels.each({ 
            label = it.getText()

            labelLocation = it.getLocation()

            xLocation = labelLocation.getX()

            if (xLocation < rightBorder) {
                yLocation = labelLocation.getY()

                fieldLabels.put(label, yLocation)
            }
        })

//    fieldLabels.each({ 
//            println(it)
//        })

    file = fileBase + '_Tooltips.csv'

    for (def label : fieldLabels) {
        name = '"' + label.key + '"'

        y = label.value

        for (def tooltip : fieldTooltips) {
            if (((tooltip.value == y) || (tooltip.value == (y + 1))) || (tooltip.value == (y - 1))) {
//                outText = ((((((((outText + '"') + name) + '"') + ',') + '"') + tooltip.key) + '"') + '\n')
				tooltipList = tooltipList + name + ',' + tooltip.key + '\n'
				
//                println((name + ':') + tooltip.key)

                break
            }
        }
    }
    
    if (tooltipList.length() > 1) {
		outText = ',"NOTE: LINE FEEDS IN THE TOOLTIP TEXT HAVE BEEN REPLACED WITH HYPHENS FOR READABILITY"\n\n'

        outText = outText + '"FIELD LABEL"' + ',' + '"TOOLTIP TEXT"' + '\n' + tooltipList

        outFile = new File(file)

        outFile.write(outText)
    }
    
//    println(outText)
}


