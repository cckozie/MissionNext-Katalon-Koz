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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import com.kazurayam.ks.globalvariable.ExecutionProfilesLoader


/* Results for line 2, candidate Natalie Lopez, single.
Match percentages: Calculated = 100%, Table = 100%, Popup = 100%.

No match on [Ministry Preferences]
*/

// Had to some diddling because of the new column (for the *) on the jobs page.
// Leaving as is for now because of new issue Paulette has discovered.

maxMatches = 5 //overridden if test suite running

fudgeFactor = 2 // Allowable difference between calculated, popup, and table match percentages assumed to be from rounding errors.

testJobCount = 3

testJobStart = 1

myJobs = testJobStart.toString() + '-' + (testJobStart + testJobCount - 1).toString()

highlight = false

updateWildcards = false

bypass = false

debug = false

pages = GlobalVariable.matchPages

site = 'Journey'

matchType = 'Job' // Org or Job

user = 'cktest17jp' // office or cktest17jp  joshua.r@jesusfilm.org

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

if(GlobalVariable.testSuiteRunning) {
	testCaseName = GlobalVariable.testCaseName.substring(GlobalVariable.testCaseName.lastIndexOf('/') + 1)
	
	myTestCase = myTestCase.substring(0,myTestCase.length() - 3) + ' - ' + testCaseName
	
	maxMatches = 100
	
} else {

	myTestCase = myTestCase.substring(0, myTestCase.length() - 3)
}

myTestCase += '-' + GlobalVariable.host

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

outFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase + ' Partner-' + user + '-Job#' + myJobs) + '.txt')

resultsFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + ' Partner-' + user + '-Job#' + myJobs) + '_results.txt')

resultsFile.write(('Running ' + myTestCase) + '\n')

errorFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase + ' Partner-' + user + '-Job#' + myJobs) + '_ERRORS.txt')

errorFile.write('Running ' + myTestCase + ' - Listing Errors Only\n')

if(debug) {
	debugFile = new File(('/Users/cckozie/Documents/MissionNext/Test Reports/Matching Details/' + myTestCase + ' Partner-' + user + '-Job#' + myJobs) + '_DEBUG.txt')
	
	debugFile.write('Running ' + myTestCase + ' - Debug Data\n')
}
GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

jobFile = (filePath + 'jobprofile.txt')

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

lastEmailAddress = ''

apiTab = null

if (!(updateWildcards)) {
    candidateWildcards = evaluate(new File((filePath + site) + ' Candidate Wildcards.txt'))

    jobWildcards = evaluate(new File(filePath + 'Journey Job Wildcards.txt'))
	
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

if(user == 'cktest17jp') {
	myProfile = 'Journey Partner 17'
	new ExecutionProfilesLoader().loadProfile(myProfile)
	WebUI.callTestCase(findTestCase('_Functions/Profile Login'), [('varProfile') : myProfile])
	switchTo = false
} else {
	WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : user , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
	switchTo = true
}

WebUI.waitForPageLoad(60)

WebUI.delay(2)

orgTab = WebUI.getWindowIndex()

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Job Matches'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(60)

WebUI.delay(1)

for(testJob = testJobStart; testJob < testJobStart + testJobCount; testJob++) {

	println('Object Repository/Journey Partner Profile/Matching/a_Matching Category ' + testJob)
	
	if(testJob != '') {
//		WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/a_Matching Category ' + testJob))
		WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/a_Matching Category Parm', [('testJob') : testJob + 2]))
	}
	//System.exit(0)
	WebUI.switchToWindowIndex(2)
	
	//WebUI.switchToWindowIndex(1)
	
	WebUI.waitForPageLoad(60)
	
	WebUI.delay(1)
	
	
	
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
	
	WebUI.closeWindowIndex(2)
	
	WebUI.switchToWindowIndex(1)
	
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/button_Matches Parm', [('testJob') : testJob + 2]))
	
	WebUI.delay(1)
	
	WebUI.waitForPageLoad(60)
	
	//WebUI.delay(20)
	
	myTable = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody'
	
	WebDriver driver = DriverFactory.getWebDriver()
	
	WebElement Table = driver.findElement(By.xpath(myTable))
	
	List<WebElement> Rows = Table.findElements(By.tagName('tr'))
	
	int row_count = Rows.size() - 6
	
	if(maxMatches < row_count) {
		row_count = maxMatches
	}
	
	found = false
		
	noErrors = true
	
	if(row_count > 0) {
		
		for (row = 3; row < row_count + 3; row++) {
			
			println('row is ' + row)
			
			List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))
		
			line = Columns.get(0).getText()
			
			println('line is ' + line)
			
			firstName = Columns.get(2).getText()
			
			println('firstName is ' + firstName)
			
			lastName = Columns.get(1).getText()
			
			println('lastName is ' + lastName)
			
	//		if(lastName != 'Knolle') {
	//			continue
	//		}
			
			pct = Columns.get(8).getText()
			
			println('Table pct is ' + pct)
			
			tablePct = pct.toInteger()
			
			println('Table percent is ' + tablePct)
			
			spyGlass = Columns.get(9)
			
			myRow = row + 1
			
	//				   /html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody/tr[4]/td[10]/a/img
			element = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody/tr[' + myRow + ']/td[10]/a/img'
			
			driver.findElement(By.xpath(element)).click()
			
			WebUI.switchToWindowIndex(2)
			
			popupWindowTitle = WebUI.getWindowTitle()
			
			noFatalError = WebUI.verifyTextNotPresent('Fatal error', false, FailureHandling.OPTIONAL)
			
			println('>>>>>>> noFatalError is ' + noFatalError)
			
			if(noFatalError) {
			
				popupPercent = WebUI.getText(findTestObject('Object Repository/Education Partner Profile/Matching/text_Match Percent'))
				
				popupPercent = popupPercent.replace('%', '')
				
				popupPct = popupPercent.replace(' ', '').toInteger()
				
				println('PopupPct percent is ' + popupPct)
				
			} else {
				popupPct = '0'
				println('PopupPct percent is not displayed because of a fatal error on the popup.')
			}
				
	//		WebUI.closeWindowTitle(popupWindowTitle)
			
			WebUI.closeWindowIndex(2)
			
			WebUI.switchToWindowIndex(1)
			
	////		WebUI.delay(1)
	
			Table = driver.findElement(By.xpath(myTable))
	
			Rows = Table.findElements(By.tagName('tr'))
			
			myRow = Rows.get(row)
			
			Columns = Rows.get(row).findElements(By.tagName('td'))
			
			myRow = row + 1
			
	//				   //tr[4]/td[2]/a
			element = '//tr[' + myRow + ']/td[2]/a'
			
			println(element)
			
			theRow = Rows.get(row)
	
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
	//		js.executeScript("arguments[0].scrollIntoView(true);", myRow);
			js.executeScript("arguments[0].scrollIntoView(true);", theRow);
	//		driver.execute_script("arguments[0].scrollIntoView();", element)
			
			driver.findElement(By.xpath(element)).click()
			
			goodProfile = false
			
			testCount = 0
				
			WebUI.switchToWindowIndex(2)
			
			while(!goodProfile) {
			
				WebUI.waitForPageLoad(30)
				
	////			WebUI.delay(1)
				
				notMatched = []
		
				candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Journey Profile from Web - Job Matches'),
					[('varMatchFields') : candidateMatchFields], FailureHandling.STOP_ON_FAILURE)
				
				if(candidateFieldValues != 'error') {
					goodProfile = true
				} else {
					testCount++
					WebUI.refresh()
				}
			}
	
			println(candidateFieldValues)
			
			if(testCount > 0) {
	//			System.exit(0)
			}
			
			if(debug) {
				debugFile.append('\nCandidate Field Values:\n')
				candidateFieldValues.each {
					println(it)
					debugFile.append(it)
					debugFile.append('\n')
				}
			}
	
			maritalStatus = (candidateFieldValues.get('Marital status')[0])
	
			spouseServing = (candidateFieldValues.get('Spouse Serving with You?')[0])
	
			outText = (((((' \n\nCandidate profile for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)
	
			outFile.append(outText + '\n')
	
			println(outText)
			
			if(!noFatalError) {
				outText = '----- Fatal error message on popup.'
				println(outText)
				outFile.append(outText + '\n')
			}
	
			candidateFieldValues.each({
					outText = ((it.key + ':') + it.value)
	
					outFile.append(outText + '\n')
	
					println(outText)
				})
	
			candidateSelections = [:]
			
	        if (candidateFieldValues != null) {
	            candidateFieldValues.each({ 
	                    println(it)
	                })
	
	            for (def v : candidateFieldValues) {
	                myKey = v.key
	
	                if (candidateMatchFields.contains(myKey)) {
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
	
				outText = ((((('\n Results for line ' + line) + ', candidate ') + firstName) + ' ') + lastName)
				
				if (!(married)) {
					outText += ', single.'
				} else {
					outText += ', married and spouse is '
					
					if (!(spouseServing)) {
						outText += 'not '
					}
					
					outText += 'serving.'
				}
	
	            outFile.append(outText + '\n')
				
				resultsFile.append(outText + '\n')
				
				lineText = outText
				
				maritalStatusText = outText
	
	            doMatching(candidateFieldValues, jobSelections)
				
	        }
			
			WebUI.closeWindowIndex(2)
			
			WebUI.switchToWindowIndex(1)
			
	////        WebUI.delay(1)
	    }
	}
	WebUI.back()
}

//Switch to partner dashboard
WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

// Log out of office
if(switchTo) {
	WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out 2'), [varSite:site], FailureHandling.STOP_ON_FAILURE)
} else {
	WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Logout'))
}

if(noErrors) {
	errorFile.delete()
}

WebUI.closeBrowser()


////// FUNCTIONS
def ifPrint(def msg) {
    if (debug) {
        println(msg)
    }
}

def formatProfile(def file, def matchFields) {
	println('matchFields in formatProfile:')
	println(matchFields)

	ignoreRows = [
		'Job Category',
		'IT Job Category',
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
	println('Candidate Selections:')
	candidateSelections.each {
		println(it)
	}
	
	println('Job Selections')
	jobSelections.each{
		println(it)
	}
	
	println('Match Values:')
	matchValues.each {
		println(it)
	}

	// NEED TO FIND MATCH ON CATEGORY AND JOB WITHIN CATEGORY
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
		error = false
		
		mvKey = it.key
		println('key is ' + mvKey)
		
		testValues = jobSelections.get(it.key)
		
		println('testValues:' + testValues)
		
		if(testValues != null && !mvKey.contains('Journey Job Category')) {
		
	        ifPrint('Testing match values ' + it)
	
	        match = false
	
	        category = false
	
	        myKey = it.key
	
	        if (myKey.substring(0, 2) == '- ') {
	            category = true
	
	            ifPrint((('category is ' + category) + ' and myKey is ') + myKey)
	        }
	        
	        if (!(excluded)) {
	            match = false
				
				prefix = myKey.substring(0,2)
				
				ifPrint('myKey prefix is ' + prefix)
				
				if(prefix != '- ') {
	
					candidateKey = (it.value[0])
					
				} else {
					
					candidateKey = myKey
				}
	
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
							println('>>>>>>>>>>>> ' + candidateValues + ' : ' + jobValues)
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
	
	                    if (category) {
							
	                        categoryMatch = true
							
							matchedCategory = myKey
							
							if(categoryJobs.size() == 0) {
							
								matchedCategory = myKey
								
								categoryJobs.put(myKey,jobValues)
								
								outFile.append('*** ' + myKey + ':' + jobValues + '\n')
							}
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
						
						notMatched.add(myKey)
	                }
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
		
        if ((Math.abs(addedPct - tablePct) > fudgeFactor) || (Math.abs(tablePct - popPct) > fudgeFactor)) {
            error = true
			
			noErrors = false
			
			if(Math.abs(addedPct - tablePct) > fudgeFactor) {	
				code = '12 '
			}
			if(Math.abs(addedPct - popPct) > fudgeFactor) {
				code = code + '13 '
			}
			if(Math.abs(tablePct - popPct) > fudgeFactor) {
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
			errorFile.append(lineText + '\n')
			
            outText = '##### ERRORS: '

            outFile.append(outText + '\n')
        }
        
        outFile.append('\n\n')
		
        outText = (((((('Match percentages: Calculated = ' + addedPct) + '%, Table = ') + tblPct) + '%, Popup = ') + popPct) + 
        '%.\n')
		
		resultsText = outText

        if (error) {
            outText = ('##### ERRORS: ' + outText)
        }		
    }
    
    resultsFile.append(outText)
	
	if(!noFatalError) {
		outText = '----- Popup match percent unknown because of fatal error on popup.\n'
		resultsFile.append(outText)
	}
	
	if(notMatched.size() > 0) {
		outText = 'No match on ' + notMatched
	} else {
		outText = 'No non-matches displayed.'
	}

	resultsFile.append(outText + '\n')
	
	if(error) {
		errorFile.append(outText + '\n')
		errorFile.append(resultsText + '\n')
	}
	
//	System.exit(0)

}
