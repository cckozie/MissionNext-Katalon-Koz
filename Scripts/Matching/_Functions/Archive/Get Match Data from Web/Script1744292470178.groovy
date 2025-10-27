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

println(varUsername)
if(varUsername != '' && varUsername != null) {
	username = varUsername
	matchType = varMatchType
	site = varSite
} else {
	username = 'office'
	matchType = 'Jobs'
	//site = 'Education'
	site = 'Journey'
}

bypass = false //Clipboard already holds table

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/manager/'

if (matchType == 'Organization') {
    link = 'a_Candidate Matches'
}

if (matchType == 'Jobs') {
    link = 'a_Jobs List Beta'
}

Screen s = new Screen()

//	System.exit(0)
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

tempFile = (filePath + 'my test page tmp.csv')

outFile = new File(tempFile)

Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()

String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()

println(data)

outFile.write(data)

WebUI.delay(2)

BufferedReader reader

reader = new BufferedReader(new FileReader(tempFile))

dataFile = (filePath + 'Candidates for Match Testing - ' + matchType + '.csv')

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

while ((line != null) && (count <= 125)) {
    println(line)

    values = line.split('\t')

    if (save || ((values[0]) == '#')) {
        save = true

        if ((((values[0]) != 'New Listing') && ((values[0]) != 'New_Listing')) && (line.indexOf('Age Order') < 0) 
			&& line.indexOf('Your Folder:') < 0) {

            if (values.size() >= 5) {
	            
	            outFile.append(line.replace('\t', ',') + '\n')
	
	            myKey = (values[0])
	
	            values = values.drop(1)
				
				if(site == 'nothing') {
					
					space = values[0].indexOf(' ')
					
					myValues.add(values[0].substring(0,space))
					
					myValues.add(values[0].substring(space + 1))
					
					myValues.add(values[6])
					
				} else {
	
		            myValues.add(values[0])
		
		            myValues.add(values[1])
		
		            myValues.add(values[7])
				}
		
	            matchValues.put(myKey, myValues)
	
	            println((myKey + ':') + myValues)
	
	            myValues = []
	
	            count++
	        }
        }
    }
    
    line = reader.readLine()
}

reader.close()

matchValues.each({ 
        println((it.key + ':') + it.value)
    })

if(!bypass) {
	WebUI.delay(1)
	
		if(site == 'Journey') {
		
			if (matchType == 'Jobs') {
			    s.click(imagePath + 'Blue Jobs List.png')
			
			    s.wait(imagePath + 'Close.png', 10)
			
			    s.click(imagePath + 'Close.png')
			
			} else {
				
			    s.click(imagePath + 'Blue Close.png')
			}
		}
	
	WebUI.delay(3)
	
	WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [('varSite'):site], FailureHandling.STOP_ON_FAILURE)
	
}	
