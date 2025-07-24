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

updateWildcards = false	

debug = false

site = 'Education' // Journey or Education

radioType = 'Org'	// Org or Job

pages = 1 //How many match table pages to test

orgJourneyUsername = 'cktest07jp'

orgJourneyPassword = 'e+4GBczmpX6OSkyCMXXwIg=='

orgJourneyEmail = 'cktest07@missionnext.org'

orgEducationUsername = 'cktest06ep'

orgEducationPassword = '54sGs6IdgS9Or2VrKr+d9g=='

orgEducationEmail = 'cktest06@missionnext.org' //Not used

firstName = ''

lastName = ''

if (site == 'Journey') {
    orgUsername = orgJourneyUsername

    orgPassword = orgJourneyPassword

    orgEmail = orgJourneyEmail
} else {
    orgUsername = orgEducationUsername

    orgPassword = orgEducationPassword

    orgEmail = orgEducationEmail
}

//####	Mods to make
/*	
 * Multiple scrolls in popup until match % is found - Edwin Clavel #149
 * Repeat table percent read if zero - break and display highlight
 * Need to add languages to org and candidate - journey
 * For Journey, get organization and candidate profiles from API instead of AD
 * 
 */
//#### Delta for Journey
/*	org username and password X
 * 	match rules X
 *  dashboard link
 *  org and candidate wildcard lists X
 *  org and candidate category lists X
 *  get country of citizenship from profile with email address
 *  
 *  I think the rest should work as is
 *  Consider a front-end script to set the above
 *  May be able to use mostly intact for jobs, too
 */
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

// Entries in AD organization profile page that need to be ignored
categoriesOrganization = ['Contact Info', 'School Info', 'Vision Trip Description', 'Security', 'Positions Needed', 'Service Options'
    , 'Readiness', 'Match Filters', 'Admin Info', 'Recruiting Countries', 'Ministry Prefs', 'IT Positions']

// Entries in AD candidate profile page that need to be ignored
categoriesCandidate = ['Name & Preferences', 'Contact Info', 'Ministry Positions', 'Enter other Ethnicity', 'Enter Family Status'
    , 'Experience', 'Education', 'Additional Language ProficiencyGroup', 'Situation', 'Church', 'Availability', 'Preferences'
    , 'Options/Comment']

if (!(updateWildcards)) {
	
	candidateWildcards = evaluate(new File(filePath + site + ' Candidate Wildcards.txt'))
	
	partnerWildcards = evaluate(new File(filePath + site + ' Partner Wildcards.txt'))
	
} else {
    wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : site], FailureHandling.STOP_ON_FAILURE)

    candidateWildcards = (wildcards[0])

    partnerWildcards = (wildcards[1])
}

//Write the wildcard maps to the output file
outFile.append('Candidate Wildcards\n')

outFile.append(candidateWildcards.toString() + '\n')

outFile.append('Partner Wildcards\n')

outFile.append(partnerWildcards.toString() + '\n\n')

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

resultsFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-results.txt')

resultsFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase) + '-ERRORS.txt')

errorFile.write(('Running ' + myTestCase) + '\n')

GlobalVariable.outFile = outFile

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
        outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[3]))

        //println(outText)
        outFile.append(outText + '\n')

        candidateMatchFields.add(it.key)

        organizationMatchFields.add(it.value[0])
    })

BufferedReader reader

if (site == 'Journey') {	
    organizationSelections = [:]

	orgFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from API'), [('varEmail') : orgEmail, ('varSite') : site], 
	    FailureHandling.STOP_ON_FAILURE)
	orgFieldValues.each {
		println(it)
	}

	for(it in orgFieldValues){ 
        if (it.key in organizationMatchFields) {
            organizationSelections.put(it.key, it.value)
        }
    }
	
	apiTab = WebUI.getWindowIndex()
	
} else { //Education
	
	adTab = WebUI.getWindowIndex()
	
    WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varUsername') : orgUsername, ('varFile') : profileFile
            , ('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

    returnValues = formatProfile(profileFile, categoriesOrganization, organizationMatchFields)

    organizationSelections = (returnValues[0])
	
	organizationSelections.each {
		println(it)
	}
	
	WebUI.closeWindowIndex(1)
	
	WebUI.switchToWindowIndex(adTab)
}

outText = '\n\n Organization Selections'

outFile.append(outText + '\n')

organizationSelections.each({ 
        outText = ((it.key + ':') + it.value)

        outFile.append(outText + '\n')
    })

WebUI.delay(2)

//WebUI.newTab('')

WebUI.executeJavaScript('window.open();', [])

orgTab = WebUI.getWindowIndex() + 1

WebUI.switchToWindowIndex(orgTab)

WebUI.maximizeWindow()

WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : '', ('varUsername') : orgUsername, ('varPassword') : orgPassword
        , ('varSite') : site], FailureHandling.STOP_ON_FAILURE //***
    )

//WebUI.click(findTestObject('Object Repository/Education Partner Profile/Dashboard/a_Educator Matches'))	//***
WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Candidate Matches'))

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
regMatch.setY(178)

regMatch.setH(795)

if(site == 'Journey') {

	wheelCount = 13
	
	wheelCountMax = 18
	
} else {
	
	wheelCount = 11
	
	wheelCountMax = 18
}

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

        if (tablePct.length() < 2) {
			
            tablePct = 0
			
			capturedFile = s.capture(regPct).getFile()
			
			tablePct = getRegionText(capturedFile)
        }
		
		if (tablePct.length() < 2) {
			
			tablePct = 0
		}
		
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
            s.wheel(Mouse.WHEEL_DOWN, scrollCount)
        }
        
        s.click(imagePath + 'Popup Close Window Button.png')

//        WebUI.delay(1)

        name = new Location(regLastName.getX() + 5, regPct.getY() + 3)

        s.click(name)
		
		WebUI.delay(1)
/*
		WebUI.waitForElementVisible(findTestObject('Object Repository/Education Partner Profile/Matching/button_Close'), 5)
		
		email = WebUI.getText(findTestObject('Object Repository/Education Partner Profile/Matching/div_Email Address'))
		
		countryOfCitizenship = WebUI.getText(findTestObject('Object Repository/Education Partner Profile/Matching/div_Country of Citizenship'))
		
		maritalStatus = WebUI.getText(findTestObject('Object Repository/Education Partner Profile/Matching/div_Maritial Status'))
		
		println(email)
		println(countryOfCitizenship)
		println(maritalStatus)
*/		
		
        if (first) {
			
			s.wait(imagePath + 'Candidate Profile Close Button.png',10)
			
			closeButtonReg = s.find(imagePath + 'Candidate Profile Close Button.png')
			
			s.hover(closeButtonReg)

            button = 'small'

            s.wheel(Mouse.WHEEL_UP, 5)
			
			first = false
			
        } else {
			
			s.wait(imagePath + 'Contact Info.png',10)
			
			s.hover(closeButtonReg)
			
            button = 'big'

            s.wheel(Mouse.WHEEL_UP, 8)
        }
       
        Robot robot = new Robot()

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

        if (site == 'Journey') {
			regCountry = s.find(imagePath + 'Your Country of Citizenship.png')
        } else { 
			regCountry = s.find(imagePath + 'Your Country of Citizenship EDU.png')
		}

        regCountry.setX((regCountry.getX() + regCountry.getW()) + 73)

        regCountry.setW(450)

        regCountry.setH(30)

        if (highlight) {
            regCountry.highlight(1)
        }
        
        capturedFile = s.capture(regCountry).getFile()

        YourCountryofCitizenship = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ', 
            '')

        if (YourCountryofCitizenship.contains('UnitedStates')) {
            YourCountryofCitizenship = 'United States'
        }
            
        println(YourCountryofCitizenship)


		if(site == 'Journey') {
			marital_status = imagePath + 'Marital status JNY.png'
			
			your_email = imagePath + 'Your Email JNY.png'
			
			big_close_button = imagePath + 'Candidate Profile Big Close Button.png'
			
		} else {
			marital_status = imagePath + 'Marital status EDU.png'
			
			your_email = imagePath + 'Your Email EDU.png'
			
			big_close_button = imagePath + 'Candidate Profile Big Close Button EDU'
		}
        
		regMaritalStatus = s.find(marital_status)
		
        regMaritalStatus.setX((regMaritalStatus.getX() + regMaritalStatus.getW()) + 210)

        regMaritalStatus.setW(220)

        regMaritalStatus.setH(30)

        if (highlight) {
            regMaritalStatus.highlight(1)
        }
        
        capturedFile = s.capture(regMaritalStatus).getFile()

        maritalStatus = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ', '')

		regEmail = s.find(your_email)
		
        regEmail.setX((regEmail.getX() + regEmail.getW()) + 240)

        regEmail.setW(450)

        regEmail.setH(30)

        if (highlight) {
            regEmail.highlight(1)
        }
        
        capturedFile = s.capture(regEmail).getFile()

        emailAddress = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ', '')

        emailAddress = emailAddress.replace(':', '')

        emailAddress = emailAddress.replace(' ', '')

        emailAddress = emailAddress.replace('_', '')

        emailAddress = emailAddress.replace(':', '')

		s.click(big_close_button, 5)
		
        if (emailAddress == lastEmailAddress) {
			
            continue
        }
        
        lastEmailAddress = emailAddress
		
		if(site == 'Journey') {
			
			println('switching to apiTab ' + WebUI.getWindowIndex())
			
			WebUI.switchToWindowIndex(apiTab)
			
			candidateSelections = [:]
			
			candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'email',
				('varEmail') : emailAddress, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
		   		   
//			candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from API'), [('varEmail') : emailAddress, ('varSite') : site],
//				FailureHandling.STOP_ON_FAILURE)
				if(candidateFieldValues != null) {
				
				candidateFieldValues.each {
					println(it)
				}
				
				for(v in candidateFieldValues) { 
					myKey = v.key
					if (matchValues.containsKey(myKey)) {
						candidateSelections.put(v.key, v.value)
					}
				}
				
				if('Married' in candidateFieldValues.get('Marital status')) {
					married = true
				} else {
					married = false
				}
				
				if('Yes' in candidateFieldValues.get('Spouse Serving with You?')) {
					spouseServing = true
				} else {
					spouseServing = false
				}
	
				firstName = candidateFieldValues.getAt("First Name")[0]
				
				lastName = candidateFieldValues.getAt("Last Name")[0]
				
				outText = ('\n\n Candidate Selections for ' + firstName + ' ' + lastName)
				
				//println(outText)
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
				
				//println(outText)
				outFile.append(outText + '\n')
	
				candidateSelections.each({
						outText = ((it.key + ':') + it.value)
	
						//println(outText)
						outFile.append(outText + '\n')
					})
				
				doMatching(candidateSelections, organizationSelections) //println(outText)
				
			} else {
				outText = ('Failed to find user with email of ' + emailAddress)
				
				outFile.append(('\n' + outText) + '\n')
				
				resultsFile.append(('\n' + outText) + '\n')
				
			}
							
			WebUI.switchToWindowIndex(tableTab)
				
		} else {
		
	        WebUI.switchToWindowIndex(adTab)
	
	        WebUI.click(findTestObject('Object Repository/Admin/Ad Main/u_Admin Section Home'))
	
	        userFound = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varEmail') : emailAddress
	                , ('varFile') : profileFile, ('varSearchType') : 'email'], FailureHandling.STOP_ON_FAILURE)
	
	        println(userFound)
	
	        if (userFound != null) {
	            returnValues = formatProfile(profileFile, categoriesCandidate, candidateMatchFields)
	
	            WebUI.closeWindowIndex(3)
	
	            WebUI.switchToWindowIndex(adTab)
	
	            WebUI.delay(1)
	
	            candidateSelections = (returnValues[0])
				
				ycoc = []
				
				ycoc.add(YourCountryofCitizenship)
				
				candidateSelections.put("Your Country of Citizenship", ycoc)
	
	            candidateSelections.each({ 
	                    println(it)
	                })

	            //            married = (returnValues[1])
	            if (maritalStatus == 'Married') {
	                //We now get this from the profile along with email address
	                married = true
	            }
	            
	            println(married)
	
	            spouseServing = (returnValues[2])
	
	            println(spouseServing)
				
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
	
	            doMatching(candidateSelections, organizationSelections //println(outText)
	                )
	        } else {
	            outText = ('Failed to find user with email of ' + emailAddress)
	
	            outFile.append(('\n' + outText) + '\n')
	        }
	        
	        WebUI.switchToWindowIndex(tableTab)
	    }
	    
   }
   
   pageCount += 1
   
   wheelCount = wheelCountMax

}

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

    lostPoints = 0.0

    for (def it : matchValues) {
        match = false

        if (!(excluded)) {
			match = false
            candidateValues = candidateSelections.get(it.key)

            values = matchValues.get(it.key)

            orgSelectionKey = (values[0])

            matchValue = (values[1])

            println(matchValue)

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

            println(matchValue)

            if ((matchValue != 5) && !((values[pointValue]).contains('-'))) {
                newPoints = (values[pointValue]).toFloat().round(1)
            } else {
                newPoints = 0
            }
            
            cWC = candidateWildcards.get(it.key)

            println(cWC)

            println(candidateValues)
/*			
			outFile.append('For match field ' + it + '\n')
			outFile.append('candidateValues : ' + candidateValues + '\n')
			outFile.append('organiztionValues : ' + organizationValues + '\n')
			outFile.append('Intersect at : ' + candidateValues.intersect(organizationValues) + '\n')
*/			

            if ((cWC != null) && (candidateValues != null)) {
                if (cWC.intersect(candidateValues).size() > 0) {
                    outText = (((('Candidate wildcard match on ' + cWC.intersect(candidateValues)) + '. Adding ') + newPoints) + 
                    ' points.')

                    outFile.append(outText + '\n')

                    points = (points + newPoints)

                    match = true
                }
            
            }
			if (!(match)) {
                oWC = partnerWildcards.get(orgSelectionKey)

                println('partner wildcards = ' + oWC)

                println('partner selections =' + organizationValues)

                if ((oWC != null) && (organizationValues != null)) {
					
                    if (oWC.intersect(organizationValues).size() > 0) {
						
                        outText = (((('Organization wildcard match on ' + oWC.intersect(organizationValues)) + '. Adding ') + 
                        newPoints) + ' points.')

                        outFile.append(outText + '\n')

                        points = (points + newPoints)

                        match = true
                    }
                }
				if ((!(match) && (candidateValues != null)) && (organizationValues != null)) {
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
            outText = (('Add match is now ' + points.round(1)) + '%.')

            outFile.append(outText + '\n')
        } else {
            if (matchValue == 5) {
                excluded = true

                outText = 'This is a must match field. Candidate is excluded.'

                outFile.append(outText + '\n')

                resultsFile.append(outText)

                break
            }
            
            outText = 'No match found.'

            outFile.append(outText + '\n')
        }
    }
    
    addedPct = points.round(0).toInteger()

    println('addedPct=' + addedPct)

    println('popupPct=' + popupPct)

    println('tablePct=' + tablePct)

    error = false

//    popPct = popupPct.toString().replace('\n', '')

//    popPct = popPct.replace('%', '')
	
	popPct = popupPct.toString().replace('\n', '')
	
	popPct = popPct.replace('%', '')

	popPct = popPct.toInteger()

	tablePct = tablePct.toInteger()

	code = ''
	
    if (((addedPct - tablePct) > 1) || ((tablePct - popPct) > 1)) {
        error = true
		
		if(Math.abs(addedPct - tablePct) > 1) {	
			code = '12 '
		}
		if(Math.abs(addedPct - popPct) > 1) {
			code = code + '13 '
		}
		if(Math.abs(tablePct - popPct) > 1) {
			code = code + '23'
		}
    }
    
    outText = (('\nCalculated match percentage adding is ' + addedPct) + '%.')

    outFile.append(outText + '\n')

    tblPct = tablePct.toString()

    tblPct = tblPct.replace('\n', '')

    outText = (('Table match percentage is ' + tblPct) + '%.')

    outFile.append(outText + '\n')

//    outText = ('Popup match percentage is ' + popupPct)
	outText = (('Popup match percentage is ' + popPct) + '%.')
	
    outFile.append(outText + '\n')

//    popupPct = popupPct.toString().replace('\n', '')

    if (error) {
//        outText = '##### ERRORS FOUND #####'
		outText = '##### ERRORS ' + code + ' FOUND #####'
		
        outFile.append(outText + '\n')
		
    }
    
    outFile.append('\n\n')

//    outText = (((((('Match percentages: Calculated = ' + addedPct) + ' %, Table = ') + tblPct) + '%, Popup = ') + popupPct) + 
//    '.\n\n')
	outText = (((((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%, Popup = ') + popPct) +
		'%.\n\n')

    if (error) {
//        outText = ('##### ERROR: ' + outText)
        outText = ('##### ERRORS ' + code + ': ' + outText)
    }
    
    resultsFile.append(outText)

	errorFile.append(outText)
	
}

def getNextLine(def reader, def categories) {
    line = reader.readLine()

    while (((line != null) && (line.length() <= 2)) || (line in categories)) {
        line = reader.readLine()
    }
    
    if (((line != null) && !(line.contains('Logout'))) && !(line.contains('Note: A candidate user'))) {
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
//            if (line.substring(1, 4).contains('\t')) {
            if (line.length() > 3 && line.substring(1, 4).contains('\t')) {
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

                        while (!(line.contains('\t') && !(line.contains('end')))) {
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

//    return [selections, married, spouseServing, firstName, lastName]
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

