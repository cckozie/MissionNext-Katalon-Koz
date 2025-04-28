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

highlight = false

updateWildcards = true

debug = false

pages = 5 //How many match table pages to test

//####	Mods to make
/*	Call Get Wildcard Selections to get the wildcard maps
 * 	Comment out the existing maps
 * 	Change wildcard map names
 * 	Write wildcard maps to outFile
 * 	Modify wildcard tests to allow for multiple wildcard values per field
 */
//#### Delta for Journey
/*	org username and password
 * 	match rules
 *  dashboard link
 *  org and candidate wildcard lists
 *  org and candidate category lists
 *  I think the rest should work as is
 *  Consider a front-end script to set the above
 *  May be able to use mostly intact for jobs, too
 */

orgUsername = 'cktest06ep'

orgPassword = '54sGs6IdgS9Or2VrKr+d9g=='

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
		'/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)



filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '.txt')

outFile.write(('Running ' + myTestCase) + '\n\n')

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

if (!(updateWildcards)) {
    //Use these hardcoded maps (or copy new ones from the wildcard data files from last update run)
    //Education Partner Wildcards
    partnerWildcards = [('Affiliated with a Church?') : 'No', ('Available Start Options') : 'No Preference', ('Time Commitments') : 'Open - Will negotiate'
        , ('Process Stage') : 'I am just beginning to look at missions', ('Relocation Option(s)') : 'Not a Match criterion'
        , ('Experience Preferred') : 'No', ('Formal Education Degree') : 'No', ('Classroom Experience') : 'No', ('Paid & Volunteer Positions') : 'No Preference'
        , ('Bible Training') : 'Not Applicable', ('Cross-cultural Experience') : 'Not served in a culture other than my own']

    //Education Candidate Wildcards
    candidateWildcards = [('I/We can be Available') : 'Not sure', ('Time Commitment(s)') : 'Open - Will negotiate', ('Relocation Option(s)') : 'Not sure'
        , ('Paid & Volunteer Positions') : 'Open'] //Create new wildcard maps 
	
} else {	//Load new wildcard maps for candidates and partners
	
	wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : 'Education'], 
		FailureHandling.STOP_ON_FAILURE)

    candidateWildcards = (wildcards[0])

    partnerWildcards = (wildcards[1])
}

//Write the wildcard maps to the output file
outFile.append('Candidate Wildcards\n')
outFile.append(candidateWildcards.toString() + '\n')

outFile.append('Partner Wildcards\n')
outFile.append(partnerWildcards.toString() + '\n\n')

/*
Education Candidate
>time_commitment:Open - Will negotiate
world_region_preferences:No Preference
financial_support:Open
travel_support:No travel funding provided

Education Partner
>time_commitments:Open - Will negotiate
>available_start_options:No Preference
school_term_available:Open
candidate_process_stages:I am just beginning to look at missions
cross-cultural_experience:Not served in a culture other than my own
>bible_school_training:Not Applicable
attended_perspectives?:I have not taken the Perspectives Course
relocation_question:Not a Match criterion
school_formal_education_degree:No
school_classroom_experience:No
has_teaching_credentials:No
english_skills:Not Applicable
financial_support:No Preference
*/
debug = false

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

resultsFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-results.txt')

resultsFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

site = 'Education'

radioType = 'Org'

lastEmailAddress = ''

matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : radioType], 
    FailureHandling.STOP_ON_FAILURE)

// One tab at this point on ad menu page
Screen s = new Screen()

//Settings.MoveMouseDelay = 0
tab = '\t'

outText = 'Matching Rules'

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

WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varUsername') : orgUsername, ('varFile') : profileFile
        , ('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

returnValues = formatProfile(profileFile, categoriesOrganization, organizationMatchFields)

organizationSelections = (returnValues[0])

outText = '\n\n Organization Selections'

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

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : orgUsername, ('varPassword') : orgPassword
        , ('varSite') : 'Education'], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))

tableTab = (orgTab + 1)

// Now at the match table page
//WebUI.waitForPageLoad(10)
img = (imagePath + 'What Matched Edu Table.png')

s.wait(img, 10)

WebUI.delay(1)

regMatch = s.find(img)

if (highlight) {
    regMatch.highlight(1)
}

s.click(img)

regLastName = s.find(imagePath + 'Last Name Header.png')

regLastName.setH(regMatch.getH())

regLastName.setW(30)

regPct = s.find(imagePath + 'Match Percent Header.png')

if (highlight) {
    regPct.highlight(1)
}

//This is the spy glass icon lane
regMatch.setY(128)

regMatch.setH(795)

wheelCount = 11

pageCount = 1

while (pageCount <= pages) {
    s.wheel(Mouse.WHEEL_UP, wheelCount)

    WebUI.delay(1)

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

        regPct.setY(it.key)

        regPct.setH(20)

        regPct.setW(regPct.getW())

        if (highlight) {
            regPct.highlight(1)
        }
        
        capturedFile = s.capture(regPct).getFile()

        tablePct = getRegionText(capturedFile)

        print('Table percent match is ' + tablePct)

        rg.click()

        //	WebUI.delay(2)
        s.wait(imagePath + 'What Matched Popup Text.png', 5)

        what = s.find(imagePath + 'What Matched Popup Text.png')

        scrolled = false

        if (!(s.exists(imagePath + 'Popup Percent Text.png'))) {
            s.hover(imagePath + 'What Matched Popup Text.png')

            s.wheel(Mouse.WHEEL_UP, 2)

            scrolled = true

            WebUI.delay(1)
        }
        
        Pattern icn = new Pattern(imagePath + 'Popup Percent Text.png').similar(0.50)

        pct = s.exists(icn)

        //println(pct)
        pct.setX(what.getX() - 10)

        pct.setW(50)

        if (highlight) {
            pct.highlight(1)
        }
        
        capturedFile = s.capture(pct).getFile()

        popupPct = getRegionText(capturedFile)

        print('Popup percent match is ' + popupPct)

        if (scrolled) {
            s.wheel(Mouse.WHEEL_DOWN, 5)
        }
        
        s.click(imagePath + 'Popup Close Window Button.png')

        WebUI.delay(1)

        name = new Location(regLastName.getX() + 5, regPct.getY() + 3)

        s.click(name)

        s.wait(imagePath + 'Candidate Profile Close Button.png', 10)

        Robot robot = new Robot()

        for (int i = 0; i < 4; i++) {
            // how many times you want CRTL+'-' pressed
            robot.keyPress(KeyEvent.VK_META)

            robot.keyPress(KeyEvent.VK_ADD)

            robot.keyRelease(KeyEvent.VK_ADD)

            robot.keyRelease(KeyEvent.VK_META)
        }
        
        regEmail = s.find(imagePath + 'Email.png')

        regEmail.setX(regEmail.getX() + regEmail.getW())

        regEmail.setW(350)

        if (highlight) {
            regEmail.highlight(1)
        }
        
        capturedFile = s.capture(regEmail).getFile()

        emailAddress = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ', '')

        emailAddress = emailAddress.replace(':', '')

        emailAddress = emailAddress.replace(' ', '')

        emailAddress = emailAddress.replace('_', '')

        emailAddress = emailAddress.replace(':', '')

        //println(emailAddress)
        for (int i = 0; i < 4; i++) {
            // how many times you want CRTL+'-' pressed
            robot.keyPress(KeyEvent.VK_META)

            robot.keyPress(KeyEvent.VK_SUBTRACT)

            robot.keyRelease(KeyEvent.VK_SUBTRACT)

            robot.keyRelease(KeyEvent.VK_META)
        }
        
        s.click(imagePath + 'Candidate Profile Close Button.png', 5)

        //		WebUI.delay(2)
        if (emailAddress == lastEmailAddress) {
            continue
        }
        
        lastEmailAddress = emailAddress

        WebUI.switchToWindowIndex(adTab)

        WebUI.click(findTestObject('Object Repository/Admin/Ad Main/u_Admin Section Home'))

        userFound = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varEmail') : emailAddress
                , ('varFile') : profileFile, ('varSearchType') : 'email'], FailureHandling.STOP_ON_FAILURE)

        if (userFound != null) {
            returnValues = formatProfile(profileFile, categoriesCandidate, candidateMatchFields)

            WebUI.closeWindowIndex(3)

            WebUI.switchToWindowIndex(adTab)

            WebUI.delay(1)

            candidateSelections = (returnValues[0])

            married = (returnValues[1])

            spouseServing = (returnValues[2])

            outText = ((('\n\n Candidate Selections for ' + firstName) + ' ') + lastName)

            //println(outText)
            outFile.append(outText + '\n')

            outText = ((('\n Results for candidate ' + firstName) + ' ') + lastName)

            resultsFile.append(outText + '\n')

            if (!(married)) {
                outText = 'Candidate is not married.'
            } else {
                outText = 'Candidate is married and spouse is '

                if (!(spouseServing)) {
                    outText += ' not '
                }
                
                outText += 'serving.'
            }
            
            //println(outText)
            outFile.append(outText + '\n')

            candidateSelections.each({ 
                    outText = ((it.key + ':') + it.value)

                    //println(outText)
                    outFile.append(outText + '\n')
                })

            //	WebUI.closeWindowIndex(1)
            WebUI.switchToWindowIndex(tableTab)

            WebUI.delay(1)

            doMatching(candidateSelections, organizationSelections) //println(outText)
        } else {
            outText = ('Failed to find user with email of ' + emailAddress)

            outFile.append(('\n' + outText) + '\n')
        }
        
        WebUI.switchToWindowIndex(tableTab)
    }
    
    pageCount += 1

    wheelCount = 18
} //println(outText)
//println(outText)
//println(outText)
//println(outText)
//println(outText)
//println(outText)
//println(outText)
//	add point calcs
//println(outText)
//println(outText)
//println(outText)
//println(outText)
//println(outText)
//println(line)
//println(number)
//println(line)
//println('Field is ' + field)
//println('Value is ' + value)

def doMatching(def candidateSelections, def organizationSelections) {
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

                outFile.append(outText + '\n')

                outText = ('Organization selections = ' + organizationValues)

                outFile.append(outText + '\n')

                outText = ('Candidate selections = ' + candidateValues)

                outFile.append(outText + '\n')

                if (matchValue != 5) {
                    newPoints = (values[pointValue]).toFloat().round(1)
                } else {
                    newPoints = 0
                }
                
                cWC = candidateWildcards.get(it.key)	//Need to allow for cWC being a list

                if ((cWC != null) && (cWC in candidateValues)) {
                    outText = (((('Candidate wildcard match on ' + cWC) + '. Adding ') + newPoints) + ' points.')

                    outFile.append(outText + '\n')

                    points = (points + newPoints)

                    match = true
                } else if (!(match)) {
                    oWC = partnerWildcards.get(orgSelectionKey)

                    if ((oWC != null) && (oWC in organizationValues)) {
                        outText = (((('Organization wildcard match on ' + oWC) + '. Adding ') + newPoints) + ' points.')

                        outFile.append(outText + '\n')

                        points = (points + newPoints)

                        match = true
                    } else if ((!(match) && (candidateValues != null)) && (organizationValues != null)) {
                        matches = candidateValues.intersect(organizationValues)

                        if (matches.size() > 0) {
                            outText = (((('Found match on ' + candidateValues.intersect(organizationValues)) + '. Adding ') + 
                            newPoints) + ' points.')

                            outFile.append(outText + '\n')

                            points = (points + newPoints)

                            match = true
                        }
                    }
                }
            }
            
            if (match) {
                outText = (('Match is now ' + points.round(1)) + '%.')

                outFile.append(outText + '\n')
            } else {
                if (matchValue == 5) {
                    excluded = true
                }
                
                outText = 'No match found.'

                outFile.append(outText + '\n')
            }
        })

    if (excluded) {
        outText = 'This is a must match field. Candidate is excluded.'

        outFile.append(outText + '\n')
    }
    
    outText = (('Calculated match percentage is ' + points.round(0).toInteger()) + ' %.')

    outFile.append(outText + '\n')

    tblPct = tablePct.toString()

    tblPct = tblPct.replace('\n', '')

    outText = (('Table match percentage is ' + tblPct) + ' %.')

    outFile.append(outText + '\n')

    outText = ('Popup match percentage is ' + popupPct)

    popupPct = popupPct.toString().replace('\n', '')

    outFile.append(outText + '\n\n')

    resultsFile.append(((((('Match percentages: Calculated = ' + points.round(0).toInteger()) + ' %, Table = ') + tblPct) + 
        ' %, Popup = ') + popupPct) + '.\n')
}

def getNextLine(def reader, def categories) {
    line = reader.readLine()

    while (((line != null) && (line.length() <= 2)) || (line in categories)) {
        line = reader.readLine()
    }
    
    if ((line != null) && !(line.contains('Logout'))) {
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

        while (line != 'end') {
            if (line.substring(1, 4).contains('\t')) {
                values = []

                tab = line.indexOf('\t')

                number = line.substring(0, tab)

                if (!(fieldNumbers.contains(number))) {
                    fieldNumbers.add(number)

                    line = line.substring(tab + 1)

                    tab = line.indexOf('\t')

                    if (tab >= 0) {
                        field = line.substring(0, tab).trim()

                        value = line.substring(tab + 1).trim()

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
                        
                        if (field == 'First Name') {
                            firstName = value
                        }
                        
                        if (field == 'Last Name') {
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

