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
import org.openqa.selenium.interactions.Actions as Actions
import org.sikuli.script.*
import java.io.File as File
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

if(GlobalVariable.fastPath) {
	return
}

// Define the tooltip images path
tooltipImagePath = varTooltipImagePath

// Define the names of the tooltip fields and the unique part of the related test object
// (header is a dummy because Sikulix does not do an image compare correctly on the first element tested)
tooltips = varTooltips

// Define the expected tooltip texts
tooltipText = varTooltipText

// Define the tooltip test object folder for the calling page
testObjectFolder = varTestObjectFolder

// Define the the actual tooltip text on the page
tooltipTextMap = varTooltipTextMap

//Check to see if we're writing printed output to a file
writeFile = false

if (GlobalVariable.outFile != '') {
    String myFile = GlobalVariable.outFile

    println(myFile)

    outFile = new File(myFile)

    writeFile = true
}

WebDriver driver = DriverFactory.getWebDriver()

Actions action = new Actions(driver)

Screen s = new Screen()

outText = 'Verifying the tooltips can be displayed.\n'

outFile.append(outText)

// Use Sikulix to verify the tooltip messages are displayed. % match numbers are sent to output file
//	tooltips.each({
first = true

for (def it : tooltips) {
    myKey = it.key

    myValue = it.value

    println('myKey is ' + myKey)

    println('myValue is ' + myValue)
	
	if(myKey != 'dummy') {	//A dummy first element is necessary because Sikulix is not correctly matching on the first element tested
	
	    tObj = (testObjectFolder + myValue)
	
	    click(tObj)
	    
	    WebUI.delay(1)
		
	    myImage = ((tooltipImagePath + myKey) + '.png')
		
	} else {
		myImage = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/dummy.png'
	}
	
	f = new File(myImage)

    if (f.exists()) {
        println('Looking for ' + myImage)

        Pattern icn = new Pattern(myImage).similar(0.10)

        found = s.exists(icn)

        println(found)

        if (found != null) {
            foundStr = found.toString()

            matchP = foundStr.indexOf('%')
			
			if(matchP >= 0) {

	            println(matchP)
	
	            pct = foundStr.substring(matchP + 1, matchP + 6)

				pctF = pct.toFloat()
				
			} else {
				matchP = foundStr.indexOf('S:')
				
				pct = foundStr.substring(matchP + 2, matchP + 6)
				
				println(pct)
				
				pctF = (Float.parseFloat(pct) * 100).round(2)
				
				println(pctF)
			}
			
           outText = (((('+++ Tooltip match value for ' + myKey) + ' is ') + pctF) + '%')
			
			min = 80
			
			if(pctF.toFloat() < min.toFloat()) {
				outText = '>>>>> ' + outText + ' <<<<<'
			}

            if (myKey != 'dummy') {
                //The new sikulix gets the wrong match results for the first element, so this is a dummy match
                println(outText)

                outFile.append(outText + '\n')
            }
        } else {
			if(myKey != 'dummy') {
	            outText = ('--- Unable to find tooltip text for ' + myKey)
	
	            println(outText)
	
	            outFile.append(outText + '\n')
			}
        }
    } else {
        outText = ('--- Unable to find image file ' + myImage)

        outFile.append(outText + '\n')

        println(outText)

        KeywordUtil.markError('\n' + outText)
    }
}

// Verify the tooltip text found in the call to Get Screenshot and Tooltip Text against what we expected in tooltipText[]
outText = 'Verifying the tooltip text on the page is what was expected.\n'

println(tooltipTextMap)

outFile.append(outText)

for (def it : tooltipText) {
	println(it)
	
	myKey = it.key

	myText = it.value.replace('"','')
	
	myText = myText.trim()

	println(myKey)
	
	println(myText)
	
	println(tooltipTextMap.get(myKey))
	
	actualText = tooltipTextMap.get(myKey).replace('"','')
	
	actualText = actualText.trim()
	

	println((myKey + ':') + actualText)

	if (actualText != myText) {
		outText = (((((('####### ERROR: The tooltip text for ' + myKey) + ' should be:\n') + myText) + '\n but instead is:\n') +
		actualText))

		println(outText)

		outFile.append(outText + '\n')
		
/* FOR DEBUGGING
		println('Expected length is ' + myText.length())
		println('Found length is ' + actualText.length())
		str = ''
		for(chr in myText) {
			str = str + (int)chr + ' '
		}
		println(str)
		str = ''
		for(chr in actualText) {
			str = str + (int)chr + ' '
		}
		println(str)
*/
	}
}

def scrollToObject(def object) {
    println(('Converting ' + object) + ' to web element')

    element = WebUiCommonHelper.findWebElement(findTestObject(object), 1)

    loc = element.getLocation()
	
	x = loc.getX()
	
	if(x >= 560) {
		outText = '####### ERROR: The tooltip for ' + myKey + ' is not positioned near its label.'
	
		println(outText)

		outFile.append(outText + '\n')
	}

    y = loc.getY()

    println('Y location is ' + y)

    top = WebUI.getViewportTopPosition()

    println('Viewport top is ' + top)

    bottom = (top + 600)

    if (((y - top) < 150) || ((bottom - y) < 10)) {
        WebUI.scrollToPosition(0, y - 170)  //// THIS NUMBER (170) IS FOR TOOLTIP SCROLLING ONLY. OTHERS USE 150.

        WebUI.delay(1)
    }
}

def click(def object) {
    scrollToObject(object)

    WebUI.click(findTestObject(object))
}

