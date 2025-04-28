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
//import java.io.File;
import org.apache.commons.io.FileUtils

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile'):'Education Partner 06'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))

WebUI.delay(3)

Screen s = new Screen()

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/Matching/'

myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/1744586430108.png'
/*
textFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/pct.txt'
imageFile = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/1744586430108.png'
//command = ["/opt/homebrew/Cellar/tesseract/5.5.0_1/bin/tesseract +imageFile+ stdout"].execute()
sout = new StringBuffer() 
serr = new StringBuffer()
command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()
command.consumeProcessOutput(sout, serr)
command.waitForOrKill(1000)
println(sout)
println(serr)

myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'
*/

regMatch = s.find(imagePath + "What Matched Edu Table.png")
regMatch.highlight(1)
capturedFile = (s.capture(regMatch).getFile())

println(getRegionText(capturedFile))

System.exit(2)

capturedFile = (s.capture(regMatch).getFile())


FileUtils.copyFile(new File(capturedFile), new File(myImage))

sout = new StringBuffer()
serr = new StringBuffer()
command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()
command.consumeProcessOutput(sout, serr)
command.waitForOrKill(1000)
println(sout)
println(serr)

System.exit(0)

matchText = regMatch.getText()
println(matchText)

s.click(imagePath + "What Matched Edu Table.png")
regMatch.setH(500)
regMatch.highlight(1)
icons = regMatch.findAll(imagePath + "Spy Glass.png")
//sorted_icons = sorted(icons, key=by_y)

regLastName = s.find(imagePath + "Last Name Header.png")
regLastName.setH(regMatch.getH())
regLastName.setW(30)

regPct = s.find(imagePath + "Match Percent Header.png")


regLastName.highlight(1)

for(rg in icons) {
	print(rg.y)
    rg.highlight(1)
	regPct.setY(rg.getY())
	regPct.setH(rg.getH())
    regPct.highlight(1)
	tablePct = myImage.text()
//	print(tablePct)
	rg.click()
	WebUI.delay(2)
	what = s.find(imagePath + "What Matched Popup Text.png")
	pct = s.find(imagePath + "Popup Percent Text.png")
	pct.setX(what.getX()-10)
	pct.setW(50)
	pct.highlight(1)
	popupPct = myImage.text()
//	print(popupPct)
	s.click(imagePath + "Popup Close Window Button.png")
	WebUI.delay(1)
	name = new Location(regLastName.getX() + 5, rg.getY() + 3)
	s.click(name)
	s.wait(imagePath + "Candidate Profile Close Button.png",10)
	
	WebUI.delay(2)
	myButton = new Pattern(imagePath + "Print-Forward Button.png").targetOffset(-13,-38)
	s.click(myButton)
//	s.click(Pattern(imagePath + "Print-Forward Button.png").targetOffset(-13,-38),5)
//	s.click(imagePath + "Print-Forward Button.png").targetOffset(-13,-38)
//	wait(imagePath + "Print Forward Close Button.png",5)
	WebUI.delay(3)
	s.click(imagePath + "Print Forward Close Button.png",5)
	s.click(imagePath + "Candidate Profile Close Button.png",5)
}

def getRegionText(imageFile) {
	FileUtils.copyFile(new File(imageFile), new File(myImage))
	sout = new StringBuffer()
	serr = new StringBuffer()
	command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()
	command.consumeProcessOutput(sout, serr)
	command.waitForOrKill(1000)
//	println(sout)
//	println(serr)
	return sout
}
