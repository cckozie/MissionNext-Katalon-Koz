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
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.apache.commons.io.FileUtils as FileUtils

import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

def by_y(match) {
	return match.y
}



//#### Add wildcard maps
/*
 * Log into AD (function - exists) - Window 0
 * Get match rules (function - exists) - W0
 * Get organization profile (function - new) - W0
 * Switch to new tab - W1
 * Log in as organization
 * Show match table
 * For candidates in table:
 * 		Get table match percent
 * 		Get popup match percent
 * 		Switch to AD tab - W0
 * 		Get candidate profile (function - reuse new)
 * 		Calculate match percent logging matches
 * 		Report match values
 * 		Switch back to table tab
 * 
 */
orgUsername = 'cktest06ep'

orgPassword = '54sGs6IdgS9Or2VrKr+d9g=='

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

// Path for Sikulix script images
imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/education partner/Matching/'

// File used for capturing region images that will have the text extracted from. (Can't get tesseract to work within this app)
myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'

profileFile = (filePath + 'userprofile.txt')


// Entries in AD candidate profile page that need to be ignored
categoriesOrganization = ['Contact Info', 'School Info', 'Vision Trip Description', 'Security', 'Positions Needed', 'Service Options'
    , 'Readiness', 'Match Filters', 'Admin Info', 'Recruiting Countries', 'Ministry Prefs', 'IT Positions']

// Entries in AD organization profile page that need to be ignored
categoriesCandidate = ['Name & Preferences', 'Contact Info', 'Ministry Positions', 'Enter other Ethnicity', 'Enter Family Status'
    , 'Experience', 'Education', 'Additional Language ProficiencyGroup', 'Situation', 'Church', 'Availability', 'Preferences'
    , 'Options/Comment']

//Education Organization Wildcards
orgWildcardsEO = [('Affiliated with a Church?') : 'No', ('Available Start Options') : 'No Preference', ('Time Commitments') : 'Open - Will negotiate'
    , ('Process Stage') : 'I am just beginning to look at missions', ('Relocation Option(s)') : 'Not a Match criterion', ('Experience Preferred') : 'No'
    , ('Formal Education Degree') : 'No', ('Classroom Experience') : 'No', ('Paid & Volunteer Positions') : 'No Preference'
    , ('Bible Training') : 'Not Applicable', ('Cross-cultural Experience') : 'Not served in a culture other than my own']

//Education Candidate Wildcards
candWildcardsEO = [('I/We can be Available') : 'Not xsure', ('Time Commitment(s)') : 'Open - Will negotiate', ('Relocation Option(s)') : 'Not sure'
    , ('Paid & Volunteer Positions') : 'Open']

debug = true

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

outFile.write(('Running ' + myTestCase) + '\n\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

site = 'Education'

radioType = 'Org'
/*
WebUI.openBrowser('')

WebUI.maximizeWindow()

WebUI.callTestCase(findTestCase('_Functions/Log In to AD'), [('varUsername') : ''], FailureHandling.STOP_ON_FAILURE)
*/
matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : radioType], 
    FailureHandling.STOP_ON_FAILURE)
// One tab at this point on ad menu page

Screen s = new Screen()

//Settings.MoveMouseDelay = 0

tab = '\t'

outText = 'Matching Rules'

//println(outText)

outFile.append(('\n ' + outText) + '\n')

candidateMatchFields = []

organizationMatchFields = []

matchValues.each({ 
        outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[
        3]))

        //println(outText)

        outFile.append(outText + '\n')

        candidateMatchFields.add(it.key)

        organizationMatchFields.add(it.value[0])
    })

BufferedReader reader

adTab = WebUI.getWindowIndex()

//println('On return from get match rules on AD window index is ' + adTab)

WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varUsername') : orgUsername, ('varFile') : profileFile
	, ('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

//println('On return from get profile on AD window index is ' + WebUI.getWindowIndex())

returnValues = formatProfile(profileFile, categoriesOrganization, organizationMatchFields)

organizationSelections = returnValues[0]

outText = '\n\n Organization Selections'

//println(outText)

outFile.append(outText + '\n')

organizationSelections.each({ 
        outText = ((it.key + ':') + it.value)

        //println(outText)

        outFile.append(outText + '\n')
    })

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(adTab)

WebUI.delay(2)

WebUI.executeJavaScript('window.open();', [])

orgTab = 1

WebUI.switchToWindowIndex(orgTab)

//println('After opening and switching to a new tab, the tab index is ' + orgTab)

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : orgUsername,
	('varPassword') : orgPassword, ('varSite') : 'Education'], FailureHandling.STOP_ON_FAILURE)

//println('index is now ' + WebUI.getWindowIndex())

//println(WebUI.getWindowTitle())

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))

//println('After opening the table tab, the tab index is ' + WebUI.getWindowTitle())

tableTab = orgTab + 1

// Now at the match table page
//WebUI.waitForPageLoad(10)

img = imagePath + 'What Matched Edu Table.png'

s.wait(img,10)

regMatch = s.find(img)

//regMatch.highlight(1)

s.click(img)

s.wheel(Mouse.WHEEL_UP, 11)

regMatch.setY(128)
regMatch.setH(795)
regMatch.highlight(1)



//regMatch.setH(150)
//regMatch.highlight(1)

//icons = regMatch.findAll(imagePath + 'Spy Glass.png')
icons = regMatch.findAll(new Pattern(imagePath + 'Spy Glass.png').similar(0.50))

//sorted_icons = sorted(icons, key=by_y)
//sorted_icons = sorted(icons, key=lambda y)

//add 14
iconLocs = [:]
for(rg in icons) {
	//println(rg.getX() + ':' + rg.getY())
	iconLocs.put(rg.getY() + 12, rg.getX() + 14)
}

iconLocs.sort()

regLastName = s.find(imagePath + 'Last Name Header.png')

regLastName.setH(regMatch.getH())

regLastName.setW(30)

regPct = s.find(imagePath + 'Match Percent Header.png')

//regLastName.highlight(1)

//for (def rg : icons) {
//	print(rg.y)
for(it in iconLocs) {
	
	rg = new Location(it.value,it.key)
	
//	rg.highlight(1)

	regPct.setY(rg.getY()-12)
	
	regPct.setX(rg.getX()-14)
	
	regPct.setW(35)

	regPct.setH(25)

//	regPct.highlight(1)

	capturedFile = s.capture(regPct).getFile()

	tablePct = getRegionText(capturedFile)

	print('Table percent match is ' + tablePct)

	rg.click()

//	WebUI.delay(2)

	s.wait(imagePath + 'What Matched Popup Text.png',5)
	
	what = s.find(imagePath + 'What Matched Popup Text.png')

	scrolled = false

	if(!s.exists(imagePath + 'Popup Percent Text.png')) {
		
		s.hover(imagePath + 'What Matched Popup Text.png')
		
		s.wheel(Mouse.WHEEL_UP, 2)
		
		scrolled = true
		
		WebUI.delay(1)
	} 
//	Pattern("1744859284953.png").similar(.59)
//	pct = s.find(new Pattern(imagePath + 'Popup Percent Text.png').similar(0.60))
	
	Pattern icn = new Pattern(imagePath + 'Popup Percent Text.png').similar(0.50)
	
	pct = s.exists(icn)

	//println(pct)
	

	pct.setX(what.getX() - 10)

	pct.setW(50)

	pct.highlight(1)

	capturedFile = s.capture(pct).getFile()

	popupPct = getRegionText(capturedFile)

	print('Popup percent match is ' + popupPct)
	
	if(scrolled) {
		s.wheel(Mouse.WHEEL_DOWN, 5)
	}

	s.click(imagePath + 'Popup Close Window Button.png')

	WebUI.delay(1)
	
	name = new Location(regLastName.getX() + 5, rg.getY() + 3)

	s.click(name)

	s.wait(imagePath + 'Candidate Profile Close Button.png', 10)
	
	Robot robot = new Robot()
	
	for (int i = 0; i < 4; i++) { // how many times you want CRTL+'-' pressed
		robot.keyPress(KeyEvent.VK_META)
		robot.keyPress(KeyEvent.VK_ADD)
		robot.keyRelease(KeyEvent.VK_ADD)
		robot.keyRelease(KeyEvent.VK_META)
	}

	regEmail = s.find(imagePath + "Email.png")
	
	regEmail.setX(regEmail.getX() + regEmail.getW())
	
	regEmail.setW(350)


	/*
	regEmail = s.find(imagePath + "Your Email.png")
	
	regEmail.setX(regEmail.getX() + 216)
	
	regEmail.setW(220)
	*/
	regEmail.highlight(1)
	
	capturedFile = s.capture(regEmail).getFile()
	
	emailAddress = getRegionText(capturedFile).replaceAll("\\p{C}", "").replace(":","").replace(" ","")

	emailAddress = emailAddress.replace(":","")
	
	emailAddress = emailAddress.replace(" ","")
	
	emailAddress = emailAddress.replace("_","")
	
	emailAddress = emailAddress.replace(":","")
	
//	emailAddress.replace("‘","")
	
	//println(emailAddress)
	
	for (int i = 0; i < 4; i++) { // how many times you want CRTL+'-' pressed
		robot.keyPress(KeyEvent.VK_META)
		robot.keyPress(KeyEvent.VK_SUBTRACT)
		robot.keyRelease(KeyEvent.VK_SUBTRACT)
		robot.keyRelease(KeyEvent.VK_META)
	}

    s.click(imagePath + 'Candidate Profile Close Button.png', 5)
	
	WebUI.delay(2)
	
	WebUI.switchToWindowIndex(adTab)	
	
	WebUI.click(findTestObject('Object Repository/Admin/Ad Main/u_Admin Section Home'))
	
	userFound = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varEmail') : emailAddress, ('varFile') : profileFile
		, ('varSearchType') : 'email'], FailureHandling.STOP_ON_FAILURE)
	
	if(userFound) {
		returnValues = formatProfile(profileFile, categoriesCandidate, candidateMatchFields)
		
		WebUI.closeWindowIndex(3)
		
		WebUI.switchToWindowIndex(adTab)
		
		WebUI.delay(1)
		
		candidateSelections = returnValues[0]
		
		married = returnValues[1]
		
		spouseServing = returnValues[2]
		
		outText = '\n\n Candidate Selections for ' + firstName + ' ' + lastName
		
		//println(outText)
		
		outFile.append(outText + '\n')
		
		if(!married) {
			outText = 'Candidate is not married.'
		} else {
			outText = 'Candidate is married and spouse is '
			
			if(!spouseServing) {
				outText += ' not '
			}
			
			outText += 'serving.'
		}
			
		//println(outText)
		outFile.append(outText + '\n')
		
		candidateSelections.each{
			outText = ((it.key + ':') + it.value)
	
			//println(outText)
	
			outFile.append(outText + '\n')
		}
		
	//	WebUI.closeWindowIndex(1)
		
		WebUI.switchToWindowIndex(tableTab)
		
		WebUI.delay(1)
		
		doMatching(candidateSelections, organizationSelections)
		
	} else {
		outText = 'Failed to find user with email of ' + emailAddress
		//println(outText)
		outFile.append('\n' + outText + '\n')
	}
	
	WebUI.switchToWindowIndex(tableTab)
		
}
	

def doMatching(candidateSelections, organizationSelections) {
	if (married && !(spouseServing)) {
	    married = false
	}
	
	if (married) {
	    pointValue = 3
	} else {
	    pointValue = 2
	}
	
	excluded = false
	
	points = 0.0
	
	matchValues.each({ 
	        match = false
	
	        if (!(excluded)) {
	            candidateValues = candidateSelections.get(it.key)
	
	            values = matchValues.get(it.key)
	
	            orgSelectionKey = (values[0])
	
	            matchValue = (values[1]).toInteger()
	
	            pointsSingle = (values[2])
	
	            pointsMarried = (values[3])
	
	            organizationValues = organizationSelections.get(orgSelectionKey)
	
	            outText = ('\nFor ' + it.key)
	
	            //println(outText)
	
	            outFile.append(outText + '\n')
	
	            outText = ('Organization selections = ' + organizationValues)
	
	            //println(outText)
	
	            outFile.append(outText + '\n')
	
	            outText = ('Candidate selections = ' + candidateValues)
	
	            //println(outText)
	
	            outFile.append(outText + '\n')
	
	            if (matchValue != 5) {
	                newPoints = (values[pointValue]).toFloat().round(1)
	            } else {
	                newPoints = 0
	            }
	            
	            cWC = candWildcardsEO.get(it.key)
	
	            if ((cWC != null) && (cWC in candidateValues)) {
	                outText = (((('Candidate wildcard match on ' + cWC) + '. Adding ') + newPoints) + ' points.')
	
	                //println(outText)
	
	                outFile.append(outText + '\n')
	
	                points = (points + newPoints)
	
	                match = true
	            } else if (!(match)) {
	                oWC = orgWildcardsEO.get(orgSelectionKey)
	
	                if ((oWC != null) && (oWC in organizationValues)) {
	                    outText = (((('Organization wildcard match on ' + oWC) + '. Adding ') + newPoints) + ' points.')
	
	                    //println(outText)
	
	                    outFile.append(outText + '\n')
	
	                    points = (points + newPoints)
	
	                    match = true
	                } else if (!(match) && candidateValues != null && organizationValues != null) {
	                    matches = candidateValues.intersect(organizationValues)
	
	                    if (matches.size() > 0) {
	                        outText = (((('Found match on ' + candidateValues.intersect(organizationValues)) + '. Adding ') + 
	                        newPoints) + ' points.')
	
	                        //println(outText)
	
	                        outFile.append(outText + '\n')
	
	                        points = (points + newPoints)
	
	                        match = true
	                    }
	                }
	            }
	        }
	        
	        if (match) {
	            outText = (('Match is now ' + points.round(1)) + '%.')
	
	            //println(outText)
	
	            outFile.append(outText + '\n') //	add point calcs
	        } else {
	            if (matchValue == 5) {
	                excluded = true
	            }
	            
	            outText = 'No match found.'
	
	            //println(outText)
	
	            outFile.append(outText + '\n')
	        }
	    })
	
	if (excluded) {
	    outText = 'This is a must match field. Candidate is excluded.'
	
	    //println(outText)
	
	    outFile.append(outText + '\n')
	}
	
	outText = (('Calculated match percentage is ' + points.round(1)) + '%.')
	
	//println(outText)
	
	outFile.append(outText + '\n')
	
	outText = 'Table match percentage is ' + tablePct
	
	//println(outText)
	
	outFile.append(outText + '\n')
	
	outText = 'Popup match percentage is ' + popupPct
	
	//println(outText)
	
	outFile.append(outText + '\n\n')
	

}

 
def getNextLine(reader,categories) {
	line = reader.readLine()
//	//println(line + ':' + line.length())
	while(line != null && line.length() <= 2 || line in categories) {
//		//println(line + ':' + line.length())
		line = reader.readLine()
//		//println(line)
	}
//	//println(line + ':' + line.length())
	if(line != null && !line.contains('Logout')) {
		return line
	} else {
		return 'end'
	}
}

 
def formatProfile(def file, def categories, def matchFields) {
    reader = new BufferedReader(new FileReader(file))

    selections = [:]

    fieldNumbers = []

    values = []

    married = false

    spouseServing = false

    line = getNextLine(reader, categories)

    while (line != 'end') {
        line = getNextLine(reader, categories)

        //println(line)

        while (line != 'end') {
            if (line.substring(1, 4).contains('\t')) {
                values = []

                tab = line.indexOf('\t')

                number = line.substring(0, tab)

                if (!(fieldNumbers.contains(number))) {
                    fieldNumbers.add(number)

                    //println(number)

                    line = line.substring(tab + 1)

                    //println(line)

                    tab = line.indexOf('\t')

                    if (tab >= 0) {
                        field = line.substring(0, tab).trim()

                        //println('Field is ' + field)

                        value = line.substring(tab + 1).trim()

                        //println('Value is ' + value)

                        if (value.length() >= 2) {
                            values.add(value)
                        }
                        
                        line = getNextLine(reader, categories)

                        while (!(line.contains('\t'))) {
                            line = line.trim()

                            values.add(line)

                            line = getNextLine(reader, categories)

                            if (line == 'end') {
                                break
                            }
                        }
                        
                        if (field in matchFields) {
                            selections.put(field, values)
                        }
                        
                        if (field == 'Marital status') {
                            if ('Married' in values) {
                                married = true
                            }
                        }
						
						if(field == 'First Name') {
							firstName = value
						}
                        
						if(field == 'Last Name') {
							lastName = value
						}
                        
                        if (field == 'Spouse Serving with You?') {
                            if ('Yes' in values) {
                                spouseServing = true
                            }
                        }
                    }
                } else {
                    line = getNextLine(reader, categories)

                    if (line == 'end') {
                        break
                    }
                }
            } else {
                line = getNextLine(reader, categories)

                if (line == 'end') {
                    break
                }
            }
        }
        
        line = getNextLine(reader, categories)

        if (line == 'end') {
            break
        }
    }
    
    reader.close()

    return [selections, married, spouseServing]
}

def getRegionText(def imageFile) {
	FileUtils.copyFile(new File(imageFile), new File(myImage))

	sout = new StringBuffer()

	serr = new StringBuffer()

	command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()

	command.consumeProcessOutput(sout, serr)

	command.waitForOrKill(1000)

	return sout
}

