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
import org.openqa.selenium.WebDriver as Driver
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
import groovy.json.JsonBuilder

// Use Switch-To to login as a journey partner and capture and persist their job definitions to csv files
// ---- Add contact name and contact email to output

username = 'billtreat'

bypass = false

debug = false

site = 'Education'

matchType = 'Job'

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

println(outFile)

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

//filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

jobFilesPath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Jobs/' + site + '/' + username + '/'

folder = new File(jobFilesPath)

if (!folder.exists()) {
	
	folder.mkdirs()
	
	outText = 'Folder ' + jobFilesPath + ' was created.'
}

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

candidateMatchFields = []

jobMatchFields = []

tab = '\t'

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

		if (it.key.substring(0, 2) == '- ') {
			jobMatchFields.add('Ministry Preferences')
		}
		
		//        jobMatchFields.add(it.value[0])
		candidateMatchFields.add(it.value[0])
	})

WebUI.delay(2)



WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : username, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

WebUI.waitForPageLoad(60)

orgTab = WebUI.getWindowIndex()

//WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Job Matches'))
WebUI.click(findTestObject('Object Repository/' + site + ' Partner Profile/Dashboard/a_Job Matches'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(30)

jobTableXpath = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[1]/tbody'

Driver driver = DriverFactory.getWebDriver()

WebElement Table = driver.findElement(By.xpath(jobTableXpath))

List<WebElement> Rows = Table.findElements(By.tagName('tr'))

int row_count = Rows.size()

println(row_count - 2 + 'jobs found.')

jobFound = false

if (row_count > 2) {
	for (row = 2; row < row_count; row++) {
		println('row is ' + row)

		List<WebElement> Columns = Rows.get(row).findElements(By.tagName('td'))

		jobID = Columns.get(0).getText()

		println('jobID is ' + jobID)
		
		categoryElement = Columns.get(1)

		jobCategory = categoryElement.getText()

		println('jobCategory is ' + jobCategory)

		jobTitle = Columns.get(2).getText()

		println('jobTitle is ' + jobTitle)
		
		matchButton = Columns.get(7)
		
		List<WebElement> childElements = matchButton.findElements(By.xpath("./*"));
		
		children = childElements.size()
		
		println('>>>>> The matchButton element has ' + children + ' children.') 
		
		if(children > 0) {
		
			driver.findElement(By.linkText(jobCategory)).click();
				
			WebUI.switchToWindowIndex(2)
			
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
			
			jobSelections.put('Category', jobCategory)
			
			jobSelections.put('Title', jobTitle)
			
	//		jobSelections.put('Contact Name', )
			
			outText = '\n\n Job Selections'
			
			println(outText)
			
			outFile.append(outText + '\n')
			
			myJobTitle = jobTitle.replace('/', '-')
			
//			myJobFile = new File(jobFilesPath + jobCategory + '_' + jobTitle + '.txt')
//			myJobFilePath = jobFilesPath + jobCategory + '_' + jobTitle + '.txt'
			myJobFile = new File(jobFilesPath + jobCategory + '_' + myJobTitle + '.txt')
			myJobFilePath = jobFilesPath + jobCategory + '_' + myJobTitle + '.txt'
			println(myJobFilePath)
			
			// Create a new JsonBuilder with the map
			def builder = new JsonBuilder(jobSelections)
			
			// Define the file path
			//def filePath = "data.json"
			
			// Write the JSON string to the file
			new File(myJobFilePath).text = builder.toPrettyString()
			
	//		println "Map saved to ${filePath}"
			
	//		jobSelectionsString = jobSelections.toMapString()
			
	//		myJobFile.write(jobSelectionsString)
			
	/*
			myJobFile.withWriter { writer ->
			    jobSelections.each { key, value ->
			        writer.println "${key}=${value}"
			    }
			}		
	
			jobSelections.each({ 
			        outText = it
			
			        println(outText)
			
			        outFile.append(it.key + ':' + it.value + '\n')
					
					myJobFile.append(it.key + ':' + it.value + '\n')
			    })
			
	*/		
			WebUI.closeWindowIndex(2)
			
			WebUI.switchToWindowIndex(1)
		}
	}
}
//System.exit(0)
/*
//WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Matching/button_Matches 2'))
matchButton.click()

WebUI.waitForPageLoad(30)

myTable = '/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody'

Table = driver.findElement(By.xpath(myTable))

Rows = Table.findElements(By.tagName('tr'))

row_count = Rows.size() - 6

if (maxMatches < row_count) {
    row_count = maxMatches
}

found = false

if (row_count > 0) {
    for (row = 3; row < (row_count + 3); row++) {
        println('row is ' + row)

        Columns = Rows.get(row).findElements(By.tagName('td'))

        line = Columns.get(0).getText()

        println('line is ' + line)

        firstName = Columns.get(2).getText()

        println('firstName is ' + firstName)

        lastName = Columns.get(1).getText()

        println('lastName is ' + lastName)

        pct = Columns.get(8).getText()

        println('Table pct is ' + pct)

        tablePct = pct.toInteger()

        println('Table percent is ' + tablePct)

        spyGlass = Columns.get(9)

        myRow = (row + 1)

        //				   /html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody/tr[4]/td[10]/a/img
        element = (('/html/body/center/table/tbody/tr[4]/td/table/tbody/tr/td[3]/span/form/table[2]/tbody/tr[' + myRow) + 
        ']/td[10]/a/img')

        driver.findElement(By.xpath(element)).click()

        WebUI.switchToWindowIndex(2)

        poputWindowTitle = WebUI.getWindowTitle()

        popupPercent = WebUI.getText(findTestObject('Object Repository/Education Partner Profile/Matching/text_Match Percent'))

        popupPercent = popupPercent.replace('%', '')

        popupPct = popupPercent.replace(' ', '').toInteger()

        println('PopupPct percent is ' + popupPct)

        WebUI.closeWindowTitle(poputWindowTitle)

        WebUI.switchToWindowIndex(1)

        WebUI.delay(1)

        Table = driver.findElement(By.xpath(myTable))

        Rows = Table.findElements(By.tagName('tr'))

        Columns = Rows.get(row).findElements(By.tagName('td'))

        myRow = (row + 1)

        //				   //tr[4]/td[2]/a
        element = (('//tr[' + myRow) + ']/td[2]/a')

        println(element)

        driver.findElement(By.xpath(element)).click()

        WebUI.switchToWindowIndex(2)

        WebUI.waitForPageLoad(30)

        notMatched = []

        candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Journey Profile from Web'), [('varMatchFields') : candidateMatchFields], 
            FailureHandling.STOP_ON_FAILURE)

        println(candidateFieldValues)

        maritalStatus = (candidateFieldValues.get('Marital status')[0])

        spouseServing = (candidateFieldValues.get('Spouse Serving with You?')[0])

        outText = (((((' \n\nCandidate profile for ' + firstName) + ' ') + lastName) + ', ') + maritalStatus)

        outFile.append(outText + '\n')

        println(outText)

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

            outText = ((('\n\n Candidate Selection Matches for ' + firstName) + ' ') + lastName)

            outFile.append(outText + '\n')

            outText = ((((('\n Results for line ' + line) + ', candidate ') + firstName) + ' ') + lastName)

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

            doMatching(candidateFieldValues, jobSelections)
        }
        
        WebUI.closeWindowIndex(2)

        WebUI.switchToWindowIndex(1)

        WebUI.delay(1)
    }
}
*/
WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

// Log out of office
WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out'), [('varSite') : site], FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

def formatProfile(def file, def matchFields) {
	println('matchFields in formatProfile:')

	println(matchFields)

	ignoreRows = ['Job Category', 'Group', 'Job Classification', 'Assignment Detail', 'Job Description', 'Logistics', 'Contact Details']

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

						println('values is ' + values)
					}
					
//					if (keyFound) {
					if (keyFound || field == 'Contact Name' || field == 'Contact Email' || field == 'Alternate Job Title') {
						if ((values[(values.size() - 1)]) != 'Group') {
							for (def value : values) {
								if (ignoreRows.contains(value)) {
									println(('removing ' + value) + ' from values')

									values -= value
								}
							}
							
							if(field == 'Contact Email') {
								values = values[0]
							}
							
							selections.putAt(field, values)

							if (debug) {
								println((field + ':') + values)
							}
							
							if (field.substring(0, 2) == '- ') {
								selections.putAt('Ministry Preferences', values)
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
	
	selections.each({
			println(it)
		})

	return selections
}
