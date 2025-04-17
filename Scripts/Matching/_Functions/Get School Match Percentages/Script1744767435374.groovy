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
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.io.File as File
import org.apache.commons.io.FileUtils as FileUtils
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration


debug = true
/*
myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
	'/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

outFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

site = 'Education'

radioType = 'Org'

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : radioType], 
    FailureHandling.STOP_ON_FAILURE)

outText = '\n matchValues\n'

println(outText)

if (debug) {
    outFile.append(outText + '\n')
}

matchValues.each({ 
        outText = ((it.key + ':') + it.value)

        println(outText)

        if (debug) {
            outFile.append(outText + '\n')
        }
    })
System.exit(0)

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Education Partner 06'], FailureHandling.STOP_ON_FAILURE)
*/

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))

// get partner profile here
WebUI.delay(3)

Screen s = new Screen()

// Path for Sikulix script images
imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/Matching/'

// File used for capturing region images that will have the text extracted from. (Can't get tesseract to work within this app)
myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'

regMatch = s.find(imagePath + 'What Matched Edu Table.png')

regMatch.highlight(1)

s.click(imagePath + 'What Matched Edu Table.png')

regMatch.setH(500)

regMatch.highlight(1)

icons = regMatch.findAll(imagePath + 'Spy Glass.png')

//sorted_icons = sorted(icons, key=by_y)
regLastName = s.find(imagePath + 'Last Name Header.png')

regLastName.setH(regMatch.getH())

regLastName.setW(30)

regPct = s.find(imagePath + 'Match Percent Header.png')

regLastName.highlight(1)

for (def rg : icons) {
    print(rg.y)

    rg.highlight(1)

    regPct.setY(rg.getY())

    regPct.setH(rg.getH())

    regPct.highlight(1)

    capturedFile = s.capture(regPct).getFile()

    tablePct = getRegionText(capturedFile)

    print('Table percent match is ' + tablePct)

    rg.click()

    WebUI.delay(2)

    what = s.find(imagePath + 'What Matched Popup Text.png')

    pct = s.find(imagePath + 'Popup Percent Text.png')

    pct.setX(what.getX() - 10)

    pct.setW(50)

    pct.highlight(1)

    capturedFile = s.capture(pct).getFile()

    popupPct = getRegionText(capturedFile)

    print('Popup percent match is ' + popupPct)

    s.click(imagePath + 'Popup Close Window Button.png')

    WebUI.delay(1)
	

    name = new Location(regLastName.getX() + 5, rg.getY() + 3)

    s.click(name)

    s.wait(imagePath + 'Candidate Profile Close Button.png', 10)

    WebUI.delay(2)

    myButton = new Pattern(imagePath + 'Print-Forward Button.png').targetOffset(-13, -38)

    s.click(myButton)

    WebUI.delay(3)

    s.click(imagePath + 'Print Forward Close Button.png', 5)

    s.click(imagePath + 'Candidate Profile Close Button.png', 5)
} //	println(sout)
//	println(serr)

def getRegionText(def imageFile) {
    FileUtils.copyFile(new File(imageFile), new File(myImage))

    sout = new StringBuffer()

    serr = new StringBuffer()

    command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()

    command.consumeProcessOutput(sout, serr)

    command.waitForOrKill(1000)

    return sout
}

