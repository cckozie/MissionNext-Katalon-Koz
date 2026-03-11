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
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor

// Use Switch-To to login as a journey partner and capture and persist their job definitions to csv files
// ---- Add contact name and contact email to output

switchTo = true // If false, uses user set in Profile dropdown

username = 'office' //'joshua.r@jesusfilm.org'

bypass = false

debug = true

site = 'Journey'

matchType = 'Job'

myTestCase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf(
        '/') + 1)

myTestCase = myTestCase.substring(0, myTestCase.length() - 3)

outFile = new File('/Users/cckozie/Documents/MissionNext/Test Reports/' + myTestCase + '.txt')

println(outFile)

GlobalVariable.outFile = outFile

outFile.write(('Running ' + myTestCase) + '\n\n')

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
/*
		if (it.key.substring(0, 2) == '- ') {
			jobMatchFields.add('Ministry Preferences')
		}
*/		
		//        jobMatchFields.add(it.value[0])
		candidateMatchFields.add(it.value[0])
	})

WebUI.delay(2)



if(!switchTo) {
	WebUI.callTestCase(findTestCase('_Functions/Journey Partner Login'), [:])
	username = GlobalVariable.username
} else {
	WebUI.callTestCase(findTestCase('Admin/Switch-To Username'), [('varUsername') : username , ('varSite') : site], FailureHandling.STOP_ON_FAILURE)
}

jobFilesPath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/Jobs/' + site + '/' + username + '/'

screenshotFolder = jobFilesPath + 'screenshots/'

folder = new File(jobFilesPath)

if (!folder.exists()) {
	
	folder.mkdirs()
	
	outText = 'Folder ' + jobFilesPath + ' was created.'
}

jobFile = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/jobprofile.txt'

WebUI.waitForPageLoad(60)

orgTab = WebUI.getWindowIndex()

WebUI.click(findTestObject('Object Repository/' + site + ' Partner Profile/Dashboard/a_Job Matches'))

WebUI.switchToWindowIndex(1)

WebUI.waitForPageLoad(30)

WebUI.takeFullPageScreenshot(screenshotFolder + 'Jobs List.png')

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
		
			WebElement link = Rows.get(row).findElement(By.linkText(jobCategory))
			
			JavascriptExecutor js = (JavascriptExecutor) driver;
			
			js.executeScript("arguments[0].scrollIntoView(true);", link)
			
			link.click()
	
			WebUI.switchToWindowIndex(2)
			
			WebUI.waitForPageLoad(30)
			
			WebUI.takeFullPageScreenshot(screenshotFolder + jobCategory + '_' + jobTitle + '-screenshot.png')
			
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
			
			prefs = []
			
			object = 'Object Repository/Journey Partner Profile/New Jobs/button_Close'
			WebUI.callTestCase(findTestCase('_Functions/Perform Action'), [('varAction'): 'click', ('varObject') : object], FailureHandling.STOP_ON_FAILURE)
			
			jobSelections.put('Category', jobCategory)
			
			jobSelections.put('Title', jobTitle)
			
			outText = '\n\n Job Selections'
			
			println(outText)
			
			outFile.append(outText + '\n')
			
			myJobTitle = jobTitle.replace('/', '-')
			
			myJobCategory = jobCategory.replace('/', '-')
			
			myJobFilePath = jobFilesPath + myJobCategory + '_' + myJobTitle + '.txt'
			println(myJobFilePath)
			
			// Create a new JsonBuilder with the map
			def builder = new JsonBuilder(jobSelections)
			
			println(builder.toPrettyString())
			
//			myJobFilePath = '/Users/cckozie/Desktop/test.txt'
			// Write the JSON string to the file
			new File(myJobFilePath).text = builder.toPrettyString()
			
			
			WebUI.switchToWindowIndex(1)
		}
	}
}

WebUI.closeWindowIndex(1)

WebUI.switchToWindowIndex(0)

if(switchTo) {
// Log out of office
	WebUI.callTestCase(findTestCase('Admin/Switch-To Log Out 2'), [('varSite') : site], FailureHandling.STOP_ON_FAILURE)
}

WebUI.closeBrowser()

def formatProfile(def file, def matchFields) {
	println('matchFields in formatProfile:')

	println(matchFields)
	
	newValues = ['IT Job Category', '5','' ,'' ]
	
	matchFields.put('IT Job Category', newValues) // Need to add to prevent it being deleted
	
	newValues = ['Need to See Specific IT Jobs?', '1','' ,'' ]
	
	matchFields.putAt('Need to See Specific IT Jobs?', newValues)

	ignoreRows = ['Job Category', 'IT Job Category', 'Group', 'Job Classification', 'Assignment Detail', 'Job Description', 'Logistics', 'Contact Details']

	reader = new BufferedReader(new FileReader(file))

	selections = [:]

	fieldNumbers = []

	values = []

	line = ' '

	line = reader.readLine()

	isField = false
	
	myName = ''

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
					
					if(!keyFound && field.substring(0,2) == '- ') {
						keyFound = true
					}

					if (debug) {
						println((('field is ' + field) + ' and keyFound is ') + keyFound)

						println('values is ' + values)
					}
					
					if(field == 'Name') {
/*						println(values + ', size is ' + values.size())
						v = 0
						for(value in values) {
							println(v + ':' + value)
							v++
						}
						v0 = values[0]
						println(v0)
						println(v0.getClass())
*/
						myName = '- ' + values[0]
						println(myName)
					}
					
					if (keyFound || field == 'Contact Name' || field == 'Contact Email' || field == 'Alternate Job Title' || field == 'IT Job Category' || field == 'Need to See Specific IT Jobs?') {
						lastValue = values[values.size() - 1]
						println('----- field is ' + field + ' and myName is ' + myName)
						if (lastValue != 'Group' || field == myName || field.contains('IT Job Category') || field.contains('Need to See Specific IT Jobs?')) {
							for (def value : values) {
								if (ignoreRows.contains(value)) { //&& value != 'IT Job Category') {
									println(('removing ' + value) + ' from values')

									values -= value
								}
							}
							
							if(field == 'Contact Email') {
								values = values[0]
							}
							
							selections.putAt(field, values)

							if (debug) {
								println('>>>>> Adding selection ' + field + ':' + values)
							}
/*							
							if (field.substring(0, 2) == '- ') {
								selections.putAt('Ministry Preferences', values)
							} */
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

