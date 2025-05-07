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
import org.openqa.selenium.By as By
import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.sikuli.script.*
import org.sikuli.script.SikulixForJython as SikulixForJython
import org.apache.commons.io.FileUtils as FileUtils
import java.awt.Robot as Robot
import java.awt.event.KeyEvent as KeyEvent
import java.io.File as File
import java.awt.Toolkit as Toolkit
import java.awt.datatransfer.Clipboard as Clipboard
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import java.lang.Math as Math

debug = false

pages = 3

site = 'Journey'

matchType = 'Job' // Org or Job

highlight = false

updateWildcards = false

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

resultsFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-results.txt')

resultsFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

jobFile = (filePath + 'jobprofile.txt')

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

lastEmailAddress = ''

apiTab = null

if (!(updateWildcards)) {
    candidateWildcards = evaluate(new File((filePath + site) + ' Candidate Wildcards.txt'))

    jobWildcards = evaluate(new File(filePath + 'Job Wildcards.txt'))
} else {
    wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : 'Job'], FailureHandling.STOP_ON_FAILURE)

    candidateWildcards = (wildcards[0])

    jobWildcards = (wildcards[1])
}

//Write the wildcard maps to the output file
outFile.append('Candidate Wildcards\n')

outFile.append(candidateWildcards.toString() + '\n')

outFile.append('Job Wildcards\n')

outFile.append(jobWildcards.toString() + '\n\n')

tab = '\t'

outText = 'Matching Rules'

outFile.append(('\n ' + outText) + '\n')

candidateMatchFields = []

jobMatchFields = []

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : matchType], 
    FailureHandling.STOP_ON_FAILURE)

matchValues.each({ 
        println(it)
    })

matchValues.each({ 
        outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[
        3]))

        //println(outText)
        outFile.append(outText + '\n')

        candidateMatchFields.add(it.key)

        jobMatchFields.add(it.value[0])
    })

WebUI.delay(2)

WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varSearchKey') : null], FailureHandling.STOP_ON_FAILURE)

apiTab = WebUI.getWindowIndex()

WebUI.executeJavaScript('window.open();', [])

orgTab = (WebUI.getWindowIndex() + 1)

WebUI.switchToWindowIndex(orgTab)

Screen s = new Screen()

WebDriver driver = DriverFactory.getWebDriver()

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Matching/'

myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

jobFile = (filePath + 'jobprofile.txt')

WebUI.callTestCase(findTestCase('Admin/Switch to Office'), [:], FailureHandling.STOP_ON_FAILURE)

orgTab = WebUI.getWindowIndex()

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Jobs List Beta'))

s.wait(imagePath + 'Add Jobs Button.png', 10)

regButtons = s.find(imagePath + 'Add Jobs Button.png')

regButtons.setH(120)

if (highlight) {
    regButtons.highlight(1)
}

img = (imagePath + 'Delete X.png')

s.wait(img, 10)

s.click(img)

WebUI.delay(1)

jobsReg = s.find(imagePath + 'Matching Category.png')

job = new Location(jobsReg.getX() + 10, jobsReg.getY())

println(job.getY())

button = regButtons.find(imagePath + 'Matches Button.png')

button.setY(button.getY() + 40)

if (highlight) {
    button.highlight(1)
}

myButton = button

myY = button.getY()

println('myY is ' + myY)

job.setY(myY + 2)

s.click(job)

s.wait(imagePath + 'Job Close Button', 10)

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

jobProfileFile = new File(jobFile)

Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()

String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()

println(data)

jobProfileFile.write(data)

jobSelections = formatProfile(jobProfileFile, matchValues)

outText = 'Job Selections'

println(outText)

outFile.append(outText + '\n')

jobSelections.each({ 
        outText = it

        println(outText)

        outFile.append(((it.key + ':') + it.value) + '\n')
    })

s.click(imagePath + 'Job Close Button.png')

if (highlight) {
	myButton.highlight(1)
}

buttonLoc = new Location(myButton.getX() + 10, myButton.getY() + 10)

s.click(buttonLoc)

matchTableWindowTitle = WebUI.getWindowTitle()

matchTableURL = WebUI.getUrl()

s.wait(imagePath + 'What Matched.png', 10)

regLastName = s.find(imagePath + 'Last Name.png')

regLastName.setW(30)

if (highlight) {
    regLastName.highlight(1)
}

regPct = s.find(imagePath + 'Match Percent.png')

if (highlight) {
    regPct.highlight(1)
}

//This is the spy glass icon lane
regMatch = s.find(imagePath + 'What Matched.png')

regMatch.setY(178)

regMatch.setH(795)

wheelCount = 15

wheelCountMax = 18

pageCount = 1

first = true

while (pageCount <= pages) {
    s.hover(regMatch)

    s.wheel(Mouse.WHEEL_UP, wheelCount)

    WebUI.delay(1)

    if (highlight) {
        regMatch.highlight(2)
    }
    
    icons = regMatch.findAll(new Pattern(imagePath + 'Spy Glass.png').similar(0.50))

    iconLocs = [:]

    //Don't know how to sort regions in groovy
    for (def rg : icons) {
        iconLocs.put(rg.getY(), rg.getX())
    }
    
    iconLocs = iconLocs.sort()

    for (def it : iconLocs) {
        rg = new Region(it.value - 5, it.key - 12, 30, 40)

        regPct.setY(it.key)

        regPct.setH(22)

        regPct.setW(regPct.getW())

        if (highlight) {
            regPct.highlight(1)
        }
        
        capturedFile = s.capture(regPct).getFile()

        tablePct = getRegionText(capturedFile)
		
		if(!tablePct.isNumber()) {
	        capturedFile = s.capture(regPct).getFile()
	
	        tablePct = getRegionText(capturedFile)
			
			if(!tablePct.isNumber()) {
				tablePct = '0'
			}
		}
/*
        if (tablePct.length() < 2) {
            tablePct = 0

            capturedFile = s.capture(regPct).getFile()

            tablePct = getRegionText(capturedFile)
        }
        
        if (tablePct.length() < 2) {
            tablePct = 0
        }
*/       
        print('Table percent match is ' + tablePct)

        rg.click()

        //	WebUI.delay(2)
        s.wait(imagePath + 'What Matched Popup Text.png', 5)

        what = s.find(imagePath + 'What Matched Popup Text.png')

        scrolled = false

        scrollCount = 0

        while (!(s.exists(imagePath + 'Popup Percent Text.png'))) {
            s.hover(imagePath + 'What Matched Popup Text.png')

            s.wheel(Mouse.WHEEL_UP, 2)

            scrollCount = (scrollCount + 2)

            scrolled = true

            WebUI.delay(1)
        }
        
        Pattern icn = new Pattern(imagePath + 'Popup Percent Text.png').similar(0.50)

        pct = s.exists(icn)

        pct.setX(what.getX())

        pct.setW(50)

        if (highlight) {
            pct.highlight(1)
        }
        
        capturedFile = s.capture(pct).getFile()

        popupPct = getRegionText(capturedFile)

 		if(!popupPct.isNumber()) {
			
	        capturedFile = s.capture(pct).getFile()
	
	        popupPct = getRegionText(capturedFile)
			
			if(!popupPct.isNumber()) {
				popupPct = '0'
			}
		}
		
        print('Popup percent match is ' + popupPct)

        space = popupPct.indexOf(' ')

        if (space > 0) {
            popupPct = popupPct.substring(0, space)
        }
        
        if (scrolled) {
            s.wheel(Mouse.WHEEL_DOWN, scrollCount)
        }
        
        s.click(imagePath + 'Popup Close Window Button.png')

        name = new Location(regLastName.getX() + 5, regPct.getY() + 3)

        s.click(name)

        if (first) {
            s.wait(imagePath + 'Candidate Profile Close Button.png', 10)

            closeButtonReg = s.find(imagePath + 'Candidate Profile Close Button.png')

            s.hover(closeButtonReg)

            button = 'small'

            s.wheel(Mouse.WHEEL_UP, 5)

            first = false
        } else {
            s.wait(imagePath + 'Contact Info.png', 10)

            s.hover(closeButtonReg)

            button = 'big'

            s.wheel(Mouse.WHEEL_UP, 8)
        }
        
        if (button == 'small') {
            zooms = 4

            for (int i = 0; i < zooms; i++) {
                // how many times you want CRTL+'-' pressed
                robot.keyPress(KeyEvent.VK_META)

                robot.keyPress(KeyEvent.VK_ADD)

                robot.keyRelease(KeyEvent.VK_ADD)

                robot.keyRelease(KeyEvent.VK_META)
            }
        }
        
        WebUI.delay(1)

        regCountry = s.find(imagePath + 'Your Country of Citizenship.png')

        regCountry.setX((regCountry.getX() + regCountry.getW()) + 73)

        regCountry.setW(450)

        regCountry.setH(30)

        if (highlight) {
            regCountry.highlight(1)
        }
        
        capturedFile = s.capture(regCountry).getFile()

        println(capturedFile)

        YourCountryofCitizenship = getRegionText(capturedFile)

        if (YourCountryofCitizenship.contains('UnitedStates')) {
            YourCountryofCitizenship = 'United States'
        }
        
        print(YourCountryofCitizenship)

        marital_status = (imagePath + 'Marital status.png')

        your_email = (imagePath + 'Your Email.png')

        big_close_button = (imagePath + 'Candidate Profile Big Close Button.png')

        regMaritalStatus = s.find(marital_status)

        regMaritalStatus.setX((regMaritalStatus.getX() + regMaritalStatus.getW()) + 210)

        regMaritalStatus.setW(220)

        regMaritalStatus.setH(30)

        if (highlight) {
            regMaritalStatus.highlight(1)
        }
        
        capturedFile = s.capture(regMaritalStatus).getFile()

        String maritalStatus = getRegionText(capturedFile)

        maritalStatus = maritalStatus.trim()

        regEmail = s.find(your_email)

        regEmail.setX((regEmail.getX() + regEmail.getW()) + 240)

        regEmail.setW(450)

        regEmail.setH(30)

        if (highlight) {
            regEmail.highlight(1)
        }
        
        capturedFile = s.capture(regEmail).getFile()

        emailAddress = getRegionText(capturedFile)

        s.click(big_close_button, 5)

        if (emailAddress == lastEmailAddress) {
            continue
        }
        
        lastEmailAddress = emailAddress

        WebUI.switchToWindowIndex(apiTab)

        candidateSelections = [:]

        candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'email'
                , ('varEmail') : emailAddress, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

        if (candidateFieldValues != null) {
            candidateFieldValues.each({ 
                    println(it)
                })

            for (def v : candidateFieldValues) {
                myKey = v.key

                if (matchValues.containsKey(myKey)) {
                    candidateSelections.put(v.key, v.value)
                }
            }
            
            if ('Married' in candidateFieldValues.get('Marital status')) {
                married = true
            } else {
                married = false
            }
            
            if ('Yes' in candidateFieldValues.get('Spouse Serving with You?')) {
                spouseServing = true
            } else {
                spouseServing = false
            }
            
            firstName = (candidateFieldValues.getAt('First Name')[0])

            lastName = (candidateFieldValues.getAt('Last Name')[0])

            outText = ((('\n\n Candidate Selections for ' + firstName) + ' ') + lastName)

            outFile.append(outText + '\n')

            outText = ((('\n Results for candidate ' + firstName) + ' ') + lastName)

            resultsFile.append(outText + '\n')

            if (!(married)) {
                outText = 'Candidate is not married.'
            } else {
                outText = 'Candidate is married and spouse is '

                if (!(spouseServing)) {
                    outText += 'not '
                }
                
                outText += 'serving.'
            }
            
            outFile.append(outText + '\n')

            /*
			candidateSelections.each({
					outText = ((it.key + ':') + it.value)
	
					outFile.append(outText + '\n')
				})
*/
            doMatching(candidateFieldValues, jobSelections //println(outText)
                )
        } else {
            outText = ('Failed to find user with email of ' + emailAddress)

            outFile.append(('\n' + outText) + '\n')

            resultsFile.append(('\n' + outText) + '\n')
        }
        
        Set<String> allWindows = driver.getWindowHandles()
		driver.switchTo().window(allWindows[2])
		
/*
        for (String currentWindow : allWindows) {
            println(currentWindow)
            driver.switchTo().window(currentWindow)
        }
*/
        WebUI.delay(1)
    }
	
	pageCount++
}

//Switch to partner dashboard
allWindows = driver.getWindowHandles()
driver.switchTo().window(allWindows[1])

// Unzoom the browser
for (int i = 0; i < zooms; i++) {
    // how many times you want CRTL+'-' pressed
    robot.keyPress(KeyEvent.VK_META)

    robot.keyPress(KeyEvent.VK_SUBTRACT)

    robot.keyRelease(KeyEvent.VK_SUBTRACT)

    robot.keyRelease(KeyEvent.VK_META)
}

// Log out of office
WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [varSite:site], FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()


////// FUNCTIONS
def ifPrint(def msg) {
    if (debug) {
        println(msg)
    }
}

def getRegionText(def imageFile) {
    FileUtils.copyFile(new File(imageFile), new File(myImage))

    sout = new StringBuffer()

    serr = new StringBuffer()

    command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()

    command.consumeProcessOutput(sout, serr)

    command.waitForOrKill(1000)

    println('sout is ' + sout)

    println('serr is ' + serr)

    String strSout = sout
	
    return strSout
}

def formatProfile(def file, def matchFields) {
    reader = new BufferedReader(new FileReader(file))

    selections = [:]

    fieldNumbers = []

    values = []

    line = ' '

    line = reader.readLine()

    isField = false

    while (line != null) {
        while (!(isField) && (line != null)) {
            line = reader.readLine()

            if (line == null) {
                break
            }
            
            if (debug) {
                println('1>' + line)
            }
            
            if ((line != null) && line.contains(':')) {
                colon = line.indexOf(':')

                println(colon)

                isField = true

                while (isField) {
                    values = []

                    field = line.substring(0, colon).trim()

                    if (debug) {
                        println('field is ' + field)
                    }
                    
                    if ((line.length() - colon) > 2) {
                        value = line.substring(colon + 2).trim()

                        values.add(value)
                    }
                    
                    line = reader.readLine()

                    if (debug) {
                        println('2>' + line)
                    }
                    
                    while (!(line.contains(':')) && (line != null)) {
                        values.add(line)

                        line = reader.readLine()

                        if (line == null) {
                            break
                        }
                        
                        if (debug) {
                            println('3>' + line)
                        }
                    }
                    
                    keyFound = matchFields.containsKey(field)

                    if (debug) {
                        println((('field is ' + field) + ' and keyFound is ') + keyFound)
                    }
                    
                    if (keyFound) {
                        selections.putAt(field, values)

                        if (debug) {
                            println((field + ':') + values)
                        }
                    }
                    
                    if (line == null) {
                        break
                    }
                    
                    if (!(line.contains(':'))) {
                        isField = false
                    } else {
                        colon = line.indexOf(':')
                    }
                }
            }
        }
    }
    
    return selections
}

def doMatching(def candidateSelections, def jobSelections) {
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

    lostPoints = 0.0

    categoryMatch = false

    for (def it : matchValues) {
        ifPrint('Testing match values ' + it)

        match = false

        category = false

        myKey = it.key

        if (myKey.substring(0, 2) == '- ') {
            category = true

            myKey = myKey.substring(2)

            ifPrint((('category is ' + category) + ' and myKey is ') + myKey)
        }
        
        if (!(excluded)) {
            match = false

            candidateKey = (it.value[0])

            ifPrint('candidateSelections key is ' + candidateKey)

            candidateValues = candidateSelections.get(candidateKey)

            ifPrint('candidateValues = ' + candidateValues)

            values = matchValues.get(it.key)

            ifPrint('values = ' + values)

            jobSelectionKey = it.key

            matchValue = (values[1])

            matchValue = (values[1]).toInteger()

            pointsSingle = (values[2])

            pointsMarried = (values[3])

            jobValues = jobSelections.get(jobSelectionKey)

            ifPrint('jobValues = ' + jobValues)

            if (!(category) || (jobValues != null)) {
                outText = ('\nFor ' + myKey)

                outFile.append(outText + '\n')

                outText = ('Job selections = ' + jobValues)

                outFile.append(outText + '\n')

                outText = ('Candidate selections = ' + candidateValues)

                outFile.append(outText + '\n')

                ifPrint('matchValue = ' + matchValue)

                if ((matchValue != 5) && !((values[pointValue]).contains('-'))) {
                    newPoints = (values[pointValue]).toFloat().round(1)
                } else {
                    newPoints = 0
                }
                
                cWC = candidateWildcards.get(candidateKey)

                ifPrint('cWC = ' + cWC)

                if ((cWC != null) && (candidateValues != null)) {
                    if (cWC.intersect(candidateValues).size() > 0) {
                        outText = (((('Candidate wildcard match on ' + cWC.intersect(candidateValues)) + '. Adding ') + 
                        newPoints) + ' points.')

                        outFile.append(outText + '\n')

                        points = (points + newPoints)

                        match = true
                    }
                }
                
                if (!(match)) {
                    jWC = jobWildcards.get(jobSelectionKey)

                    ifPrint('jWC = ' + jWC)

                    if ((jWC != null) && (jobValues != null)) {
                        if (jWC.intersect(jobValues).size() > 0) {
                            outText = (((('Job wildcard match on ' + jWC.intersect(jobValues)) + '. Adding ') + newPoints) + 
                            ' points.')

                            outFile.append(outText + '\n')

                            points = (points + newPoints)

                            match = true
                        }
                    }
                    
                    if ((!(match) && (candidateValues != null)) && (jobValues != null)) {
                        matches = candidateValues.intersect(jobValues)

                        if (matches.size() > 0) {
                            outText = (((('Found match on ' + candidateValues.intersect(jobValues)) + '. Adding ') + newPoints) + 
                            ' points.')

                            outFile.append(outText + '\n')

                            points = (points + newPoints)

                            match = true

                            if (category) {
                                categoryMatch = true
                            }
                        }
                    }
                }
                
                if (match) {
                    outText = (('Add match is now ' + points.round(1)) + '%.')

                    outFile.append(outText + '\n')

                    if (category) {
                        categoryMatch = true
                    }
                } else {
                    if (matchValue == 5) {
                        if (!(category) || (jobValues != null)) {
                            excluded = true

                            outText = 'This is a must match field. Candidate is excluded.'

                            outFile.append(outText + '\n')

                            resultsFile.append(outText)

                            break
                        }
                    }
                    
                    outText = 'No match found.'

                    outFile.append(outText + '\n')
                }
            }
        }
    }
    
    if (!(categoryMatch)) {
        outText = '##### ERROR: No category/job match found. Candidate should be excluded.'

        outFile.append(outText + '\n')

        resultsFile.append(outText)
    } else {
        addedPct = points.round(0).toInteger()

        println('addedPct=' + addedPct)

        println('popupPct=' + popupPct)

        println('tablePct=' + tablePct)

        error = false

        popPct = popupPct.toString().replace('\n', '')

        popPct = popPct.replace('%', '')

        popPct = popPct.toInteger()

        tablePct = tablePct.toInteger()

        if ((Math.abs(addedPct - tablePct) > 1) || (Math.abs(tablePct - popPct) > 1)) {
            error = true
        }
        
        outText = (('\nCalculated match percentage adding is ' + addedPct) + '%.')

        outFile.append(outText + '\n')

        tblPct = tablePct.toString()

        tblPct = tblPct.replace('\n', '')

        outText = (('Table match percentage is ' + tblPct) + '%.')

        outFile.append(outText + '\n')

        outText = (('Popup match percentage is ' + popPct) + '%.')

        outFile.append(outText + '\n')

        if (error) {
            outText = '##### ERRORS FOUND #####'

            outFile.append(outText + '\n')
        }
        
        outFile.append('\n\n')

        outText = (((((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%, Popup = ') + popPct) + 
        '%.\n\n')

        if (error) {
            outText = ('##### ERROR: ' + outText)
        }
    }
    
    resultsFile.append(outText)
}

