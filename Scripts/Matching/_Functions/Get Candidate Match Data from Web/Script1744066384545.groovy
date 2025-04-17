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
//import java.io.BufferedReader as BufferedReader
import java.io.FileReader as FileReader
import java.io.IOException as IOException
import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard

bypass = false

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [('varUsername') : ''], FailureHandling.STOP_ON_FAILURE)

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/'

if (!(bypass)) {
    WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : 'cktest06ep', ('varSite') : 'Education'], 
        FailureHandling.STOP_ON_FAILURE)

    WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))
}

Screen s = new Screen()

matchReg = s.find(imagePath + 'What Matched Organization.png')

matchReg.highlight(1)

WebUI.delay(3)

System.exit(0)

regPct = find('1744450194782.png')

regPct.highlight(1)

regLast = find('1744450417568.png')

click('1744450417568.png')

regLast.highlight(1)

print(regLast.getX())

regPct.setX(regLast.getX() + 1)

regPct.setW(100)

regPct.highlight(1)

name = regPct.text()

print(name)

loc = Location(495, 792)

click(loc)

Robot robot = new Robot()

s.click('/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/What Matched Jobs.png')

//Select All
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_A)

robot.keyRelease(KeyEvent.VK_A)

robot.keyRelease(KeyEvent.VK_META)

WebUI.delay(1)

//Copy
robot.keyPress(KeyEvent.VK_META)

robot.keyPress(KeyEvent.VK_C)

robot.keyRelease(KeyEvent.VK_C)

robot.keyRelease(KeyEvent.VK_META)

tempFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/my test page tmp.csv'

outFile = new File(tempFile)

Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()

String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()

println(data)

outFile.write(data)

WebUI.delay(2)

BufferedReader reader

reader = new BufferedReader(new FileReader(tempFile))

//dataFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Candidates for Match Testing.csv'
dataFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Candidates for Job Match Testing.csv'

outFile = new File(dataFile)

outFile.write('')

String line = reader.readLine()

include = false

values = []

myValues = []

matchValues = [:]

count = 0

save = false

line = reader.readLine()

while ((line != null) && (count <= 10)) {
    println(line)

    values = line.split('\t')

    if (save || ((values[0]) == '#')) {
        save = true

        println(line.indexOf('New Listing'))

        println(line.indexOf('New_Listing'))

        println(line.indexOf('Age Order'))

        if ((((values[0]) != 'New Listing') && ((values[0]) != 'New_Listing')) && (line.indexOf('Age Order') < 0)) {
            if (values.size() < 5) {
                break
            }
            
            outFile.append(line.replace('\t', ',') + '\n')

            myKey = (values[0])

            values = values.drop(1)

            myValues.add(values[0])

            myValues.add(values[1])

            myValues.add(values[7])

            matchValues.put(myKey, myValues)

            println((myKey + ':') + myValues)

            myValues = []

            count++
        }
    }
    
    line = reader.readLine()
}

reader.close()

matchValues.each({ 
        println((it.key + ':') + it.value)
    })

WebUI.click(findTestObject('Object Repository/Manager/btn_Close'))

System.exit(0)

return matchValues

