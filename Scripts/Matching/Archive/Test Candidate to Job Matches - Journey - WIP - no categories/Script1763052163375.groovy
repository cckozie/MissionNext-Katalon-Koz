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
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader as ExecutionProfilesLoader

///////////////////////////////////// Mod Log \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

// Try ignoring categories (items starting with '- ') and just match Ministry Preferences to both Preferred positions and
//	Spouse Preferred Position(s)


maxMatches = 100

bypass = false

debug = true

pages = GlobalVariable.matchPages

site = 'Journey'

matchType = 'Job' // Org or Job

user = 'Journey Candidate 15'

highlight = false

updateWildcards = false

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

resultsFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase) + '-ERRORS.txt')

errorFile.write('Running ' + myTestCase + ' - Listing Errors Only\n')

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

jobFile = (filePath + 'jobprofile.txt')

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

lastEmailAddress = ''

new ExecutionProfilesLoader().loadProfile(user)

candidateUsername = GlobalVariable.username

candidatePassword = GlobalVariable.password

candidateEmail = GlobalVariable.email

apiTab = null

if (!(updateWildcards)) {
    candidateWildcards = evaluate(new File((filePath + site) + ' Candidate Wildcards.txt'))

    jobWildcards = evaluate(new File(filePath + 'Job Wildcards.txt'))
} else {
    wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : site, 
		('varType') : matchType], FailureHandling.STOP_ON_FAILURE)

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

//        candidateMatchFields.add(it.key)
		jobMatchFields.add(it.key)
		if (it.key.substring(0, 2) == '- ')
			jobMatchFields.add('Ministry Preferences')
		
//        jobMatchFields.add(it.value[0])
        candidateMatchFields.add(it.value[0])
    })

WebUI.delay(2)

//Screen s = new Screen()	

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

jobFile = (filePath + 'jobprofile.txt')

if (site == 'Journey') {
    candidateSelections = [:]

    candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from API'), [('varEmail') : candidateEmail
            , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

    candidateFieldValues.each({ 
            println(it)
        })
// Need to post process candidate Field Values to create category entries for the matching process
	myCategories = []
	myJobs = []
	candidateFieldValues.each { 
		if(it.key == 'Job Categories') {
			values = it.value
			for(value in values) {
				myCategories.add('- ' + value)
			}
		} else if(it.key == 'Preferred Position(s)') {
			values = it.value
			for(value in values) {
				myJobs.add(value)
			}
		}
	}
	
	myCategories.each{ 
		candidateFieldValues.put(it,myJobs)
	}

	candidateFieldValues.each{
		println(it)
	}
	
    for (def it : candidateFieldValues) {
        if (it.key in candidateMatchFields || it.key.substring(0,2) == '- ') {
            candidateSelections.put(it.key, it.value)

            println(it)
        }
    }
    
    apiTab = WebUI.getWindowIndex() //Education
    
} else {

    adTab = WebUI.getWindowIndex()

    WebUI.callTestCase(findTestCase('Matching/_Functions/Get Profile from AD'), [('varUsername') : candidateUsername, ('varFile') : profileFile
            , ('varSearchType') : 'username'], FailureHandling.STOP_ON_FAILURE)

    returnValues = formatProfile(profileFile, categoriesCandidate, candidateMatchFields)

    candidateSelections = (returnValues[0])

    candidateSelections.put('Your Country of Citizenship', myFieldValues.get('Your Country of Citizenship'))

    candidateSelections.each({ 
            println(it)
        })

    WebUI.closeWindowIndex(1)

    WebUI.switchToWindowIndex(adTab)
}

if (site == 'Journey') {
    maritalStatus = (candidateFieldValues.get('Marital status')[0])

    spouseServing = (candidateFieldValues.get('Spouse Serving with You?')[0])

    firstName = (candidateFieldValues.get('First Name')[0])

    lastName = (candidateFieldValues.get('Last Name')[0])

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
} else {
    married = (returnValues[1])

    spouseServing = (returnValues[2])

    if (married) {
        maritalStatus = 'Married'
    } else {
        maritalStatus = 'Single'
    }
}

outText = ((((('\n\n Candidate Selections for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)

if (married) {
    if (spouseServing) {
        outText += ', spouse is serving.'
    } else {
        outText += ', spouse is not serving.'
    }
}

outFile.append(outText + '\n')

candidateSelections.each({ 
        outText = ((it.key + ':') + it.value)

        outFile.append(outText + '\n')
    })

WebUI.delay(2)

//WebUI.callTestCase(findTestCase('Admin/Switch to Office'), [:], FailureHandling.STOP_ON_FAILURE)
WebUI.callTestCase(findTestCase('_Functions/Generic Login'), [('varProfile') : 'Journey Candidate 15', ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(60)

userTab = WebUI.getWindowIndex()

WebUI.click(findTestObject('Object Repository/Journey Candidate Profile/Dashboard/a_View Job Matches'))

WebUI.waitForPageLoad(30)

jobTableXpath = '//*[@id="main"]/div/div/div[2]/div[3]/div/div[1]/table/tbody'

WebDriver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath(jobTableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size() - 1

if (maxMatches < row_count) {
    row_count = maxMatches
}

jobFound = false

if (row_count > 0) {
	for (row = 1; row < row_count; row++) {
		println('row is ' + row)

		rowClass = Rows.get(row).getAttribute("class")
		
		if(rowClass.contains('item')) {
				
			List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
	
			line = Columns.get(0).getText()
	
			println('line is ' + line)
			
			categoryElement = Columns.get(1)
	
			jobCategory = categoryElement.getText()
	
			println('jobCategory is ' + jobCategory)
			
			agency = Columns.get(2).getText()
			
			println('agency is ' + agency)
				
			jobTitle = Columns.get(3).getText()
	
			println('jobTitle is ' + jobTitle)
			
			tablePct = Columns.get(5).getText().toInteger()
			
			link = Columns.get(1)
			
			link.findElement(By.xpath("./a")).click();
			
			jobFound = true
			
			WebUI.switchToWindowIndex(1)
			
			WebUI.waitForPageLoad(30)
			
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
			
			WebUI.closeWindowIndex(1)
			
			WebUI.switchToWindowIndex(0)
		
            
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

            outText = ((('\n\n Candidate Selection Matches for ' + firstName) + ' ') + lastName)

            outFile.append(outText + '\n')

            outText = ((('\n Results for line ' + line + ', candidate ' + firstName) + ' ') + lastName)

            resultsFile.append(outText + '\n')
			
			candidateText = outText

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
			
			maritalStatusText = outText

//            doMatching(candidateFieldValues, jobSelections)
//			doMatching(candidateSelections, jobSelections)
			doMatching(jobSelections, candidateSelections)
			
        }
		
    }
}


//Switch to partner dashboard
WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

// Log out of office
WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [varSite:site], FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

WebUI.delay(5) //In case running as test suite


////// FUNCTIONS
def ifPrint(def msg) {
    if (debug) {
        println(msg)
    }
}
/*
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
*/
def formatProfile(def file, def matchFields) {
	println('matchFields in formatProfile:')
	println(matchFields)

	ignoreRows = [
		'Job Category',
		'Group',
		'Job Classification',
		'Assignment Detail',
		'Job Description',
		'Logistics',
		'Contact Details']
	
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
					
//					field = field.replace('- ', '')

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
						println('values is ' + values)
                    }
                    
                    if (keyFound) {
//						field = field.replace('- ', '')
						
						if(values[values.size() - 1 ] != 'Group') {
							
							for(value in values) {
								if(ignoreRows.contains(value)) {
									println('removing ' + value + ' from values')
									values -= value
								}
							}
						
	                        selections.putAt(field, values)
	
	                        if (debug) {
	                            println((field + ':') + values)
	                        }
							
							if(field.substring(0,2) == '- ') {
								
								selections.putAt('Ministry Preferences', values) //Matches with spouse's ministry prefs
							}
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
	selections.each{
		println(it)
	}
    return selections
}

def doMatching(def candidateSelections, def jobSelections) {
	
//  The candidateSelections and jobSelections map are swapped (borrowed code and too many changes to make, prints are fixed)
		
//	Need to compensate for candidate's profile not containing category/job relationships
//	Match candidate's categories to parnter's category and 
//	Match Preferred Position(s) and Spouse Preferred Position(s) to Ministry Preferences for partner (priority 5)
	
	error = false
	
	println('Job Selections:')
	candidateSelections.each {
		println(it)
	}
	
	println('Candidate Selections')
	jobSelections.each{
		println(it)
	}
	
	println('Match Values:')
	matchValues.each {
		println(it)
	}

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
	
	categoryJobs = [:]

    for (def it : matchValues) {
        myKey = it.key

		testValues = jobSelections.get(myKey)
		
		if(myKey.substring(0,2) == '- ' || testValues == null) {
			continue
		}
		
        ifPrint('Testing match values ' + it)

        match = false

        if (!(excluded)) {
			
			candidateKey = (it.value[0])

            ifPrint('jobSelections key is ' + candidateKey)

            candidateValues = candidateSelections.get(candidateKey)

            ifPrint('jobValues = ' + candidateValues)

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
				
				if(myKey == 'Ministry Preferences' && categoryMatch) {
					
					jobValues = categoryJobs.get(matchedCategory)
					
				}

                outText = ('Job selections = ' + jobValues)

                outFile.append(outText + '\n')

                outText = ('Candidate selections = ' + candidateValues)

                outFile.append(outText + '\n')

                ifPrint('matchValue = ' + matchValue)

                if (matchValue != 5 && !values[pointValue].contains('-') && values[pointValue].length() > 0) {
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
								
								if(categoryJobs.size() == 0) {
								
									matchedCategory = myKey
									
									categoryJobs.put(myKey,jobValues)
									
									outFile.append('*** ' + myKey + ':' + jobValues + '\n')
								
	                            }
                            }
                        }
                    }
                }
                
                if (match) {
                    outText = (('Add match is now ' + points.round(1)) + '%.')

                    outFile.append(outText + '\n')
/*
                    if (category) {
						
                        categoryMatch = true
						
						matchedCategory = myKey
						
						if(categoryJobs.size() == 0) {
						
							matchedCategory = myKey
							
							categoryJobs.put(myKey,jobValues)
							
							outFile.append('*** ' + myKey + ':' + jobValues + '\n')
						}
                    }
*/
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

		code = ''
		
        if ((Math.abs(addedPct - tablePct) > 1) || (Math.abs(tablePct - popPct) > 1)) {
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

        outText = (('Popup match percentage is ' + popPct) + '%.')

        outFile.append(outText + '\n')

        if (error) {
            outText = '##### ERRORS ' + code + ' FOUND #####'

            outFile.append(outText + '\n')
        }
        
        outFile.append('\n\n')
		
		outText = 'No match on ' + notMatched
		
//		outFile.append(outText + '\n')

        outText = (((((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%, Popup = ') + popPct) + 
        '%.\n')
		
		resultsText = outText

        if (error) {
            outText = ('##### ERRORS ' + code + ': ' + outText)
        }
    }
    
    resultsFile.append(outText)
	
	if(error) {
		errorFile.append(candidateText + '. ' + maritalStatusText + '\n')
		if(notMatched.size() > 0) {
			outText = 'No match on ' + notMatched	
		} else {
			outText = 'No non-matches displayed.'
		}
		errorFile.append(outText + '\n')
		errorFile.append(resultsText + '\n')
	}

}

