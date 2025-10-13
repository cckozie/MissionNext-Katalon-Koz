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
import java.time.Instant as Instant

/*	TO DO:
 * Need to save list of recruiting countries and verify all listed candidates have Country of Residence matching the list
 * 	(Maybe force add 'Your Country of Citizenship   Recruit From Countries	5' to the matching scheme) Why isn't it there?
 * Add education jobs to the education profiles and use my education profile(s) for match testing instead of scmnschool
*/
bypass = false

debug = true

highlight = false

updateWildcards = false

pages = GlobalVariable.matchPages

site = 'Education'

userMN = false

if(userMN) {
	user = 'scmnschool'
	
} else {
	user = 'cktest16ep'
	
	userProfile = 'Education Partner 16'
}

matchType = 'Job' // Org or Job

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
} else {
	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '.txt')

resultsFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-results.txt')

resultsFile.write(('Running ' + myTestCase) + '\n\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

jobFile = (filePath + 'jobprofile.txt')

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

lastEmailAddress = ''

apiTab = null

if (!(updateWildcards)) {
    candidateWildcards = evaluate(new File((filePath + site) + ' Candidate Wildcards.txt'))

    jobWildcards = evaluate(new File((filePath + site) + ' Job Wildcards.txt'))
} else {
    wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : site, ('varType') : matchType], 
        FailureHandling.STOP_ON_FAILURE)

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

// Need to add Recruiting Countries to the match scheme
matchValues = [:]

//Your Country of Citizenship=[Recruit From Countries, 5, , ]
//matchValues.put('Your Country of Citizenship', ['Recruit From Countries', '5', '', ''])
matchValues.put('Recruit From Countries', ['Your Country of Citizenship', '5', '', ''])

matchValuesA = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : matchType], 
    FailureHandling.STOP_ON_FAILURE)

matchValuesA.each({ 
        matchValues.put(it.key, it.value)
    })

matchValues.each({ 
        print(it)

        println('\n')
    })

//System.exit(0)
matchValues.each({ 
        outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[
        3]))

        //println(outText)
        outFile.append(outText + '\n')

        //        candidateMatchFields.add(it.key)
        jobMatchFields.add(it.key)

        //        jobMatchFields.add(it.value[0])
        candidateMatchFields.add(it.value[0])
    })

WebUI.delay(2)

WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varSearchKey') : null], FailureHandling.STOP_ON_FAILURE)

WebDriver driver = DriverFactory.getWebDriver()

apiTab = WebUI.getWindowIndex()

println('1:' + driver.getWindowHandles().size())

WebUI.executeJavaScript('window.open();', [])

println('2:' + driver.getWindowHandles().size())

orgTab = (WebUI.getWindowIndex() + 1)

WebUI.switchToWindowIndex(orgTab)

println('3:' + driver.getWindowHandles().size())

println('4:' + driver.getWindowHandles().size())

//WebDriver driver = DriverFactory.getWebDriver()
println('5:' + driver.getWindowHandles().size())

Screen s = new Screen()

println(apiTab)

println(orgTab)

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Matching/'

myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

jobFile = (filePath + 'jobprofile.txt')

if (userMN) {
    WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : user, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
} else {
	WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : userProfile], FailureHandling.STOP_ON_FAILURE)
}

driver = DriverFactory.getWebDriver()

println('6:' + driver.getWindowHandles().size())

// Click (My Profile)
WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_My Profile'))

// Click (Recruiting Countries)
WebUI.click(findTestObject('Object Repository/Education Partner Profile/Tabs/a_Recruiting Countries'))

// Call (Get Status of Group Elements)
//xpath of the Recruiting Countries group
recruiting_countries = "//input[@id='profile_group-1744301635.194_recruit_countries']"

element_list = [] //All elements

checked_only = true

countries = WebUI.callTestCase(findTestCase('_Functions/Get Status of Group Elements'), [varXpath:recruiting_countries, varList: element_list,
	varCheckedOnly: checked_only], FailureHandling.STOP_ON_FAILURE)

myCountries = []

countries.each{
	if(debug) {
		println(it.value)
	}
	
	myCountries.add(it.value[0])
}

println(myCountries)

// Add recruiting countries to selections[:]
selections = ['Recruit From Countries' : myCountries]

WebUI.click(findTestObject(('Object Repository/' + site) + ' Partner Profile/Dashboard/a_Job Matches'))

s.wait(imagePath + 'Add Jobs Button.png', 30)

regButtons = s.find(imagePath + 'Add Jobs Button.png')

regButtons.setH(120)

regButtons.setX(regButtons.getX() - 15)

if (highlight) {
    regButtons.highlight(2)
}

img = (imagePath + 'Delete X.png')

s.wait(img, 30)

s.click(img)

WebUI.delay(1)

jobsReg = s.find(imagePath + 'Matching Category.png')

job = new Location(jobsReg.getX() + 10, jobsReg.getY())

println(job.getY())

button = regButtons.find(imagePath + 'Matches Button.png')

//button.setY(button.getY() + 40)
button.setY(button.getY() // First "Matches" button
    )

if (highlight) {
    button.highlight(1)
}

myButton = button

myY = button.getY()

println('myY is ' + myY)

job.setY(myY + 2)

s.click(job)

s.wait(imagePath + 'Job Close Button', 30)

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

outText = '\n\n Job Selections'

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

s.wait(imagePath + 'What Matched.png', 30)

regLastName = s.find(imagePath + 'Last Name.png')

regLastName.setW(30)

if (highlight) {
    regLastName.highlight(1)
}

regPct = s.find(imagePath + 'Match Percent.png')

regPct.setX(regPct.getX() + 6)

regPct.setH(16)

regPct.setW(regPct.getW() - 8)

if (highlight) {
    regPct.highlight(1)
}

//This is the spy glass icon lane
regMatch = s.find(imagePath + 'What Matched.png')

regMatch.setY(160)

regMatch.setH(810)

wheelCount = 14

wheelCountMax = 18

pageCount = 1

first = true

while (pageCount <= pages) {
    s.hover(regMatch)

    s.wheel(Mouse.WHEEL_UP, wheelCount)

    WebUI.delay(1)

    wheelCount = wheelCountMax

    WebUI.delay(1)

    if (!(bypass)) {
        if (highlight) {
            regMatch.highlight(1)
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

            regPct.setY(it.key + 3)

            if (highlight) {
                regPct.highlight(3)
            }
            
            tablePct = getRegionText(regPct)

            print('Table percent match is ' + tablePct)

            rg.click()

            s.wait(imagePath + 'What Matched Popup Text.png', 30)

            what = s.find(imagePath + 'What Matched Popup Text.png')

            scrolled = false

            scrollCount = 0

            while (!(s.exists(imagePath + 'Popup Percent Text.png', 1))) {
                s.hover(imagePath + 'What Matched Popup Text.png')

                s.wheel(Mouse.WHEEL_UP, 2)

                scrollCount = (scrollCount + 2)

                scrolled = true
            }
            
            Pattern icn = new Pattern(imagePath + 'Popup Percent Text.png').similar(0.50)

            pct = s.exists(icn)

            pct.setX(what.getX() + 7)

            pct.setW(50)

            if (highlight) {
                pct.highlight(1)
            }
            
            popupPct = getRegionText(pct)

            print('Popup percent match is ' + popupPct)

            space = popupPct.indexOf(' ')

            if (space > 0) {
                popupPct = popupPct.substring(0, space)
            }
            
            print('Popup percent match is ' + popupPct)

            if (scrolled) {
                s.wheel(Mouse.WHEEL_DOWN, scrollCount)
            }
            
            s.click(imagePath + 'Popup Close Window Button.png')

            name = new Location(regLastName.getX() + 5, regPct.getY() + 3)

            s.click(name)

            s.wait(imagePath + 'Candidate Profile Close Button.png', 30)

            candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Education Profile from Web'), 
                [('varMatchFields') : candidateMatchFields], FailureHandling.STOP_ON_FAILURE)

            println(candidateFieldValues)

            firstName = (candidateFieldValues.get('First Name')[0])

            lastName = (candidateFieldValues.get('Last Name')[0])

            maritalStatus = (candidateFieldValues.get('Marital status')[0])

            outText = (((((' \n\nCandidate selections for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)

            outFile.append(outText + '\n')

            println(outText)

            candidateFieldValues.each({ 
                    outText = ((it.key + ':') + it.value)

                    outFile.append(outText + '\n')

                    println(outText)
                })

            if (maritalStatus == 'Married') {
                married = true
            } else {
                married = false
            }
            
            spouseServing = false

            doMatching(candidateFieldValues, jobSelections)

            s.click(imagePath + 'Candidate Profile Close Button.png')
        }
    }
    
    pageCount++
}

//Switch to partner dashboard
allWindows = driver.getWindowHandles()

driver.switchTo().window(allWindows[1])

if(user != 'cktest16ep') {
	// Log out of office
	WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [('varSite') : site], FailureHandling.STOP_ON_FAILURE)
}

WebUI.closeBrowser()

WebUI.delay(5) //In case running as test suite

def ifPrint(def msg) {
    if (debug) {
        println(msg)
    }
}

def getRegionText(def region) {
    Screen s = new Screen()

    myPct = ''

    loops = 5

    loop = 0

    while ((myPct == '') && (loop < loops)) {
        capturedFile = s.capture(region).getFile()

        FileUtils.copyFile(new File(capturedFile), new File(myImage))

        sout = new StringBuffer()

        serr = new StringBuffer()

        command = ['/Users/cckozie/Documents/Scripts/MissionNext/ocr.sh'].execute()

        command.consumeProcessOutput(sout, serr)

        command.waitForOrKill(2000)

        println('sout is ' + sout)

        println('serr is ' + serr)

        String strSout = sout

        strSout = strSout.replace('%', '')

        if (strSout.isNumber() && (strSout.length() > 0)) {
            pctMatch = strSout

            break
        } else {
            pctMatch = 999
        }
        
        loop++

        WebUI.delay(1)

        myInstant = Instant.now()

        errFile = (('/Users/cckozie/Documents/MissionNext/Fail Screenshots/' + myInstant) + '.png')

        FileUtils.copyFile(new File(capturedFile), new File(errFile))
    }
    
    return pctMatch
}

def formatProfile(def file, def matchFields) {
    reader = new BufferedReader(new FileReader(file))

//    selections = [:]

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

                    if (field == 'Job Title') {
                        field = ('Education ' + field)
                    }
                    
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

    for (def it : matchValues) {
        ifPrint('Testing match values ' + it)

        match = false

        myKey = it.key

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

            if (jobValues != null) {
                outText = ('\nFor ' + myKey)

                outFile.append(outText + '\n')

                outText = ('Job selections = ' + jobValues)

                outFile.append(outText + '\n')

                outText = ('Candidate selections = ' + candidateValues)

                outFile.append(outText + '\n')

                ifPrint('matchValue = ' + matchValue)

                if (((matchValue != 5) && !((values[pointValue]).contains('-'))) && ((values[pointValue]).length() > 0)) {
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
                        }
                    }
                }
                
                if (match) {
                    outText = (('Add match is now ' + points.round(1)) + '%.')

                    outFile.append(outText + '\n')
                } else {
                    if (matchValue == 5) {
                        if (jobValues != null) {
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
    
    if (1 == 2) {
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

        code = ''

        if ((Math.abs(addedPct - tablePct) > 1) || (Math.abs(tablePct - popPct) > 1)) {
            error = true

            if (Math.abs(addedPct - tablePct) > 1) {
                code = '12 '
            }
            
            if (Math.abs(addedPct - popPct) > 1) {
                code = (code + '13 ')
            }
            
            if (Math.abs(tablePct - popPct) > 1) {
                code = (code + '23')
            }
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
            outText = (('##### ERRORS ' + code) + ' FOUND #####')

            outFile.append(outText + '\n')
        }
        
        outFile.append('\n\n')

        outText = (((((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%, Popup = ') + popPct) + 
        '%.\n\n')

        if (error) {
            outText = ((('##### ERRORS ' + code) + ': ') + outText)
        }
    }
    
	resultsFile.append(' Candidate Selection Matches for ' + firstName + ' ' + lastName + '\n')
	
    resultsFile.append(outText)
}

