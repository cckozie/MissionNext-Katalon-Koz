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
//import org.apache.commons.lang3.StringUtils as StringUtils
import javax.swing.*
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard

matchFields = [
	'Affiliated with a church?',
	'Preferred School Positions',
	'I/We can be Available',
	'Time Commitment(s)',
	'Process Stage',
	'Relocation Option(s)',
	'Previous Experience',
	'Formal Education Degree',
	'Classroom Experience',
	'Paid & Volunteer Positions',
	'Bible Training',
	'Cross-Cultural Experience']
	



bypass = true //Clipboard already holds table
/*
filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

tempFile = filePath + 'education candidate profile.txt'

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/'

Screen s = new Screen()

//	System.exit(0)

 */
if (!(bypass)) {
    WebUI.callTestCase(findTestCase('Admin/Switch-To Username - Sikuli'), [('varUsername'):username, ('varSite'):site], FailureHandling.STOP_ON_FAILURE)

    WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/' + link))

    WebUI.delay(5)

    if (matchType == 'Jobs') {
        job = s.find('/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/BAM Coordinator.png')

        matchButton = s.find('/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/Matches.png')

        matchButton.setY(job.getY())

        s.click(matchButton)

        WebUI.delay(5)

        s.click(imagePath + 'What Matched Jobs.png',5)
		
    } else {
        s.click(imagePath + 'What Matched Organization.png')
    }
    
    Robot robot = new Robot()

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
}
/*
tempFile = (filePath + 'my education test page tmp.csv')

outFile = new File(tempFile)

Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()

String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()

//println(data)

outFile.write(data)

WebUI.delay(2)
*/
//BufferedReader reader

//reader = new BufferedReader(new FileReader(tempFile))

//dataFile = (filePath + 'Education Candidate for Match Testing Print.csv')

//outFile = new File(dataFile)

//outFile.write('')

//String line = reader.readLine()

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

tempFile = filePath + 'education candidate profile.txt'

//include = false

//values = []

//myValues = []

//matchValues = [:]

//count = 0

//save = false

BufferedReader reader
reader = new BufferedReader(new FileReader(tempFile))

fields = [:]
values = []
//start = false
//fieldFound = false
line = getNextLine(reader)
//while (line != null) {
while(!line.contains('Contact Info')) {
//	start = true
	line = getNextLine(reader)
	while(line != null) {
		if(line.substring(0,3) == ' \t ') { // It's potentially a field
			if(line.length() > 6) {
//				println(line)
				values = []
//				fieldFound = true
				tab = line.indexOf('\t',4)
				if(tab >= 0) {
					field = line.substring(3,tab)
					value = line.substring(tab).replace('\t','')
					values.add(value)
//					println(field + ' : ' + value)
					line = getNextLine(reader)
					while (line.substring(0,3) != ' \t ' && line.substring(0,2) != ' \t') { // not field and not category
//						println(value)
						values.add(line)
						line = getNextLine(reader)
					}
//					println(field + ' : ' + values)
					if(field in matchFields) {
						fields.put(field,values)
					}
				}
//				println(line)
//				System.exit(0)
			} else {
				line = getNextLine(reader)
			}
		} else {
			line = getNextLine(reader)
		}
	}
//	println('not started yet')
	line = getNextLine(reader)
	if(line == null) {
		break
	}
}

line = getNextLine(reader)
//}

reader.close()
fields.each{
	println(it.key + ':' + it.value)
}

def getNextLine(reader) {
//	reader = new BufferedReader(new FileReader(tempFile))
	line = reader.readLine()
	while(line != null && line.length() <= 2) {
		line = reader.readLine()
	}
	if(line != null) {
		firstChar = line.substring(0,1)
		if(firstChar in ['*','@','#','&']) {
			line = line.substring(1)
		}
	}
	return line
}
