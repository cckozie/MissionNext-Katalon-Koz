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

/*
GET PROFILE FROM API
#	Candidate Field	Organization Field	Match Weight	Match Value	Contribution %
Single	Couple
- ESL/TESOL=[Preferred Position(s), 5, -, -]
- COMMUNITY DEVELOPMENT=[Preferred Position(s), 5, -, -]
- ENGINEERING=[Preferred Position(s), 5, -, -]
- HEALTH CARE=[Preferred Position(s), 5, -, -]
- EVANGELISM SUPPORT=[Preferred Position(s), 5, -, -]
- RESOURCE MANAGEMENT=[Preferred Position(s), 5, -, -]
- SUPPORT HELPS=[Preferred Position(s), 5, -, -]
- RELIEF AND DEVELOPMENT=[Preferred Position(s), 5, -, -]
- BUSINESS AS MISSION=[Preferred Position(s), 5, -, -]
- SUPPORT PROFESSIONAL=[Preferred Position(s), 5, -, -]
- CHURCH DEVELOPMENT=[Preferred Position(s), 5, -, -]
- DISCIPLESHIP=[Preferred Position(s), 5, -, -]
- JUSTICE/ADVOCACY=[Preferred Position(s), 5, -, -]
- COMMUNICATIONS=[Preferred Position(s), 5, -, -]
- INFORMATION TECHNOLOGY=[Preferred Position(s), 5, -, -]
- CONSTRUCT/MAINTAIN=[Preferred Position(s), 5, -, -]
- EDUCATION=[Preferred Position(s), 5, -, -]
- EVANGELISM=[Preferred Position(s), 5, -, -]
- DISCIPLE YOUTH=[Preferred Position(s), 5, -, -]
Journey Job Category=[Job Categories, 5, -, -]
Ministry Preferences=[Spouse Preferred Position(s), 4, -, 16.7]
Paid & Volunteer Positions=[Paid & Volunteer Positions, 4, 16.7, 16.7]
Time Commitment=[Time Commitment(s), 3, 12.5, 12.5]
Relocation Question=[Relocation Option(s), 3, 12.5, 12.5]
World Region=[Preferred Region(s), 3, 12.5, 12.5]
Cross-Cultural Experience=[Cross-Cultural Experience, 2, 8.3, 8.3]
Languages=[Language(s), 2, 8.3, 8.3]
Start_Requested=[I/We can be Available, 2, 8.3, 8.3]
Travel Support=[Travel Options, 1, 4.2, 4.2]
*/
/*
Name=[SUPPORT PROFESSIONAL, ]
Agency=[MissionNext, , Job Category]
Journey Job Category=[SUPPORT PROFESSIONAL, Group]
- SUPPORT PROFESSIONAL=[Fund Development, Grant Writing, Job Classification]
Alternate Job Title=[Grant Writer]
World Region=[North America]
Country=[United States]
Preferred Experience=[Knowledge of the grant-seeking process and good track record of successful grant applications., Assignment Detail]
Start_Requested=[Not sure]
Time Commitment=[One year, One year to two years]
Cross-Cultural Experience=[Not served in a culture other than my own]
Languages=[English is a native language, English is a second language, Job Description]
Job Comment=[The Grant Writer will research and apply for external grants to fund special projects and operating costs to ensure financial stability and continued growth of MissionNext. Key Duties: The responsibilities of the Grant Writer: 1. Prepares proposals by determining concepts, gathering and formatting information, writing drafts, and obtaining approvals. 2. Determines proposal concept by identifying and clarifying opportunities and needs, studying requests for proposals, and attending strategy meetings. 3. Meets proposal deadline by establishing priorities and target dates for information gathering, writing, review, approval, and transmittal. 4. Enters and monitors tracking data. 5. Coordinates requirements with contributors and contributes proposal status information to review meetings. 6. Develops proposal by assembling information including project nature, objectives/outcomes/deliverables, implementation, methods, timetable, staffing, budget, standards of performance, and evaluation. 7. Writes, revises, and edits drafts including executive summaries, conclusions, and organization credentials. 8. Prepares presentation by evaluating text, graphics, and binding and coordinating printing. 9. Maintains quality results by using templates; following proposal-writing standards including readability, consistency, and tone; maintaining proposal support databases. Improves proposal-writing results by evaluating and re-designing processes, approach, coordination, and boilerplate. 10. Updates job knowledge by participating in educational opportunities; maintaining personal networks.]
Web Reference=[https://missionnext.org/welcome-to-missionnext/about-mn/positions-with-missionnext/]
Listing Expiration=[2025-06-05, Logistics]
Paid & Volunteer Positions=[Volunteer/self-supported position]
Travel Support=[No travel funding provided, Travel funding negotiated]
Relocation Question=[Not a match criteria]
Housing=[Not included.]
Educating Children=[Not included., Contact Details]
Contact Name=[Vicky Warren]
Contact Email=[Vicky.Warren@missionnext.org]
 */
debug = true

site = 'Journey' 

matchType = 'Job'	// Org or Job

highlight = true

updateWildcards = true

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

pages = 1

lastEmailAddress = ''

apiTab = null

if (!(updateWildcards)) {
	
	candidateWildcards = evaluate(new File(filePath + site + ' Candidate Wildcards.txt'))
	
} else {
	wildcards = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Wildcard Selections'), [('varSite') : 'Job'], FailureHandling.STOP_ON_FAILURE)

	candidateWildcards = (wildcards[0])
}

//Write the wildcard maps to the output file
outFile.append('Candidate Wildcards\n')

outFile.append(candidateWildcards.toString() + '\n')

tab = '\t'

outText = 'Matching Rules'

outFile.append(('\n ' + outText) + '\n')

candidateMatchFields = []

jobMatchFields = []


matchValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get Matching Rules'), [('varSite') : site, ('varMatchType') : matchType],
	FailureHandling.STOP_ON_FAILURE)

matchValues.each {  
	println(it)
}

matchValues.each({
	outText = ((((((((it.key + tab) + (it.value[0])) + tab) + (it.value[1])) + tab) + (it.value[2])) + tab) + (it.value[3]))

	//println(outText)
	outFile.append(outText + '\n')

	candidateMatchFields.add(it.key)

	jobMatchFields.add(it.value[0])
})

WebUI.delay(2)

WebUI.callTestCase(findTestCase('_Functions/Log In to API (varUsername Optional)'), [('varSearchKey') : null], FailureHandling.STOP_ON_FAILURE)

apiTab = WebUI.getWindowIndex()

WebUI.executeJavaScript('window.open();', [])

jobTab = WebUI.getWindowIndex() + 1
//WebUI.delay(5)
outFile.append('New jobTab is ' + jobTab + ' and we are on tab ' + WebUI.getWindowIndex() + '\n')

WebUI.switchToWindowIndex(jobTab)
//WebUI.delay(5)
outFile.append('We switched to tab ' + WebUI.getWindowIndex() + ' before logging in as Office \n')

Screen s = new Screen()

imagePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Matching/'

//myImage = '/Users/cckozie/git/MissionNext-Katalon-Koz/images/Matching/myFile.png'
myImage = '/Users/cckozie/Documents/Sikuli/Missionnext/Education Candidate Matches/myFile.png'

filePath = '/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/'

jobFile = (filePath + 'jobprofile.txt')

WebUI.callTestCase(findTestCase('Admin/Switch to Office'), [:], FailureHandling.STOP_ON_FAILURE)

orgTab = WebUI.getWindowIndex()
outFile.append('orgTab is ' + orgTab + '\n')

WebUI.click(findTestObject('Object Repository/Journey Partner Profile/Dashboard/a_Jobs List Beta'))
//WebUI.delay(5)
outFile.append('Jobs list betq is on tab ' + WebUI.getWindowIndex() + '\n')

s.wait(imagePath + 'Add Jobs Button.png',10)

regButtons = s.find(imagePath + 'Add Jobs Button.png')

regButtons.setH(120)

if(highlight) {
	regButtons.highlight(1)
}
// //*[@id="main"]/div/div/div[2]/div[2]


img = (imagePath + 'Delete X.png')

s.wait(img, 10)

s.click(img)

WebUI.delay(1)

jobsReg = s.find(imagePath + 'Matching Category.png')

job = new Location(jobsReg.getX() + 10, jobsReg.getY())

println(job.getY())

button = regButtons.find(imagePath + 'Matches Button.png')

if(highlight) {
	button.highlight(1)
}

myY = button.getY()

println('myY is ' + myY)

job.setY(myY + 2)

s.click(job)

s.wait(imagePath + 'Job Close Button',10)
//WebUI.delay(5)
outFile.append('Job profile is on tab ' + WebUI.getWindowIndex() + '\n')

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

//tempFile = profileFile //'/Users/cckozie/git/MissionNext-Katalon-Koz/Data Files/my test page tmp.csv'

jobProfileFile = new File(jobFile)

Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard()

String data = c.getData(java.awt.datatransfer.DataFlavor.stringFlavor).toString()

println(data)

jobProfileFile.write(data)

jobSelections = formatProfile(jobProfileFile, matchValues)

jobSelections.each {
	println(it)
}

s.click(imagePath + 'Job Close Button.png')
//WebUI.delay(5)
outFile.append('Jobs list is on tab ' + WebUI.getWindowIndex() + '\n')


s.click(button)
//WebUI.delay(5)
outFile.append('Match table is on tab ' + WebUI.getWindowIndex() + '\n')

s.wait(imagePath + 'What Matched.png',10)

regLastName = s.find(imagePath + 'Last Name.png')

//regLastName.setH(regMatch.getH())

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
		pct.setX(what.getX())

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
		//WebUI.delay(5)
		outFile.append('Candidate profile is on tab ' + WebUI.getWindowIndex() + '\n')
		
		
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

//		YourCountryofCitizenship = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ','')
		YourCountryofCitizenship = getRegionText(capturedFile)
		
		if (YourCountryofCitizenship.contains('UnitedStates')) {
			YourCountryofCitizenship = 'United States'
		}
			
		print(YourCountryofCitizenship)
		outFile.append('CoC is ' + YourCountryofCitizenship + '\n')

		marital_status = imagePath + 'Marital status.png'
		
		your_email = imagePath + 'Your Email.png'
			
		big_close_button = imagePath + 'Candidate Profile Big Close Button.png'
			
		regMaritalStatus = s.find(marital_status)
		
		regMaritalStatus.setX((regMaritalStatus.getX() + regMaritalStatus.getW()) + 210)

		regMaritalStatus.setW(220)

		regMaritalStatus.setH(30)

		if (highlight) {
			regMaritalStatus.highlight(1)
		}
		
		capturedFile = s.capture(regMaritalStatus).getFile()

//		maritalStatus = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ', '')
		String maritalStatus = getRegionText(capturedFile)

		maritalStatus = maritalStatus.trim()
		outFile.append('Marital status is ' + maritalStatus + '\n')
		
		regEmail = s.find(your_email)
		
		regEmail.setX((regEmail.getX() + regEmail.getW()) + 240)

		regEmail.setW(450)

		regEmail.setH(30)

		if (highlight) {
			regEmail.highlight(1)
		}
		
		capturedFile = s.capture(regEmail).getFile()

//		emailAddress = getRegionText(capturedFile).replaceAll('\\p{C}', '').replace(':', '').replace(' ', '')
		emailAddress = getRegionText(capturedFile)
		
//		emailAddress = emailAddress.replace(':', '')

//		emailAddress = emailAddress.replace(' ', '')

//		emailAddress = emailAddress.replace('_', '')

		outFile.append('email is ' + emailAddress + '\n')
		
		s.click(big_close_button, 5)
		
		if (emailAddress == lastEmailAddress) {
			
			continue
		}
		
		lastEmailAddress = emailAddress
		
		WebUI.switchToWindowIndex(apiTab)
		//WebUI.delay(5)
		outFile.append('We switched to tab ' + WebUI.getWindowIndex() + ' to get candidate profile\n')

		candidateSelections = [:]
		
		candidateFieldValues = WebUI.callTestCase(findTestCase('Matching/_Functions/Get User Full Profile'), [('varType') : 'email',
			('varEmail') : emailAddress, ('varSite') : site], FailureHandling.STOP_ON_FAILURE)

		if(candidateFieldValues != null) {
		
			candidateFieldValues.each {
				println(it)
			}

//			System.exit(0)
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
			
			doMatching(candidateSelections, jobSelections) //println(outText)
			
		} else {
			outText = ('Failed to find user with email of ' + emailAddress)
			
			outFile.append(('\n' + outText) + '\n')
			
			resultsFile.append(('\n' + outText) + '\n')
			
		}
						
		WebUI.switchToWindowIndex(tableTab)
				

		
		
		
		
		
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
	
	while(line != null) {
	
		while (!isField && line != null) { // Read to the first field
			
			line = reader.readLine()
			
			if(line == null) {
				break
			}
			
			if(debug) {
				println('1>' + line)
			}
			
			if(line != null && line.contains(':')) {
			
				colon = line.indexOf(':')
				
				println(colon)
	
				isField = true
				
				while(isField) {
				
					values = []
					
					field = line.substring(0, colon).trim()
					
					if(debug) {
						println('field is ' + field)
					}
					
					if(line.length() - colon > 2) {
						
						value = line.substring(colon + 2).trim()
						
						values.add(value)
					}
					
					line = reader.readLine()
					
					if(debug) {
						println('2>' + line)
					}
					
					while(!line.contains(':') && line != null) {
						
						values.add(line)
						
						line = reader.readLine()
						
						if(line == null) {
							break
						}
						
						if(debug) {					
							println('3>' + line)
						}
					}
					
					keyFound = matchFields.containsKey(field)
					
					if(debug) {
						println('field is ' + field + ' and keyFound is ' + keyFound)
					}
					
					if(keyFound) {
					
						selections.putAt(field, values)
						
						if(debug) {
							println(field + ':' + values)
						}
					}
					
					if(line == null) {
						break
					}
					
					if(!line.contains(':')) {
						
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
		match = false

		if (!(excluded)) {
			match = false
			
			candidateValues = candidateSelections.get(it.key)

			values = matchValues.get(it.key)

			jobSelectionKey = (values[0])

			matchValue = (values[1])

			println(matchValue)

			matchValue = (values[1]).toInteger()

			pointsSingle = (values[2])

			pointsMarried = (values[3])

			organizationValues = jobSelections.get(orgSelectionKey)

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

	popPct = popupPct.toString().replace('\n', '')

	popPct = popPct.replace('%', '')

	if (((addedPct.toInteger() - tablePct.toInteger()) > 1) || ((tablePct.toInteger() - popPct.toInteger()) > 1)) {
		error = true
	}
	
	outText = (('\nCalculated match percentage adding is ' + addedPct) + '%.')

	outFile.append(outText + '\n')

	tblPct = tablePct.toString()

	tblPct = tblPct.replace('\n', '')

	outText = (('Table match percentage is ' + tblPct) + '%.')

	outFile.append(outText + '\n')

	outText = ('Popup match percentage is ' + popupPct)

	outFile.append(outText + '\n')

	popupPct = popupPct.toString().replace('\n', '')

	if (error) {
		outText = '##### ERRORS FOUND #####'

		outFile.append(outText + '\n')
		
	}
	
	outFile.append('\n\n')

	outText = (((((('Match percentages: Calculated = ' + addedPct) + ' %, Table = ') + tblPct) + '%, Popup = ') + popupPct) +
	'.\n\n')

	if (error) {
		outText = ('##### ERROR: ' + outText)
	}
	
	resultsFile.append(outText)

	errorFile.append(outText)
	
}
