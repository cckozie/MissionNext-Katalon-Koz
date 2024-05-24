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
import java.io.File as File
import javax.swing.JOptionPane as JOptionPane
import javax.swing.JFrame as JFrame
import groovy.sql.Sql as Sql
@Grab(value = 'org.apache.commons:commons-dbcp2:2.7.0')
import org.apache.commons.dbcp2.BasicDataSource as BasicDataSource

dMap =[:]

sMap = [:]

keyStack = []

doneList = []

bypassList = ['plugin', 'wp-content', 'wp-json', 'feed', 'xml', 'jquery', 'wpincludes']

JFrame frame = new JFrame('My First JFrame')

frame.requestFocus()

home = "https://missionnext.org/"

startPage = JOptionPane.showInputDialog(frame, 'Enter the website where you would like to begin!')

WebUI.callTestCase(findTestCase('_Functions/Open Chrome Browser - No Analytics'), [('dropDownOption') : 'QuickStart'], FailureHandling.STOP_ON_FAILURE)

if(startPage == "") {
	startPage = home
}

WebUI.navigateToUrl(startPage)

WebUI.delay(1)

linkList = []

linkList = WebUI.getAllLinksOnCurrentPage(false, [])

if(linkList.contains(startPage)) {
	println("Removing url " + startPage)

	linkList.remove(startPage)
}

myKey = startPage

myList = linkList

println(myList)

done = false

while(!done) {
	
	println(myList.getClass())
	
	if(myList != null && !myList.isEmpty()) {
		
		println("The key is " + myKey + " and list is " + myList)
		
		dMap.put(myKey,myList)
		
//		println('***MAP***')
		
//		println(dMap)
	
		myKey = iterateLinkList(myKey)
		
		if(myKey != null) {
		
			println("Key value is " + myKey)
			
			doneList.add(myKey)
			
			myList = getLinkList(myKey)
			
			println("myList is " + myList)
			
		} else {
			if(!keyStack.isEmpty()) {
				myKey = keyStack.pop()
				myList = dMap.get(myKey)
				
				println("Popped myList is " + myList)
			} else {
				done = true
			}
			

		}
		
	} else {
		WebUI.delay(60)
	}
	
}

println(doneList)

def getLinkList(url) {
	
	bypass = checkForBypass(url)
	
	if(!bypass) {
		
		println(">>>>>>>>>>>>>>>>>>> Navigating to " + url)

		WebUI.navigateToUrl(url)
		
		WebUI.delay(1)
		
	    linkList = []
	
	    linkList = WebUI.getAllLinksOnCurrentPage(false, [], FailureHandling.CONTINUE_ON_FAILURE)
	
	    if (linkList == null) {
	        WebUI.refresh()
	
	        WebUI.delay(5)
	
	        linkList = WebUI.getAllLinksOnCurrentPage(false, [])
			
	    }
		
		if(linkList.contains(url)) {
			println("Removing url " + url)
		
			linkList.remove(url)
		}
		
		return linkList
	}
}

def checkForBypass(link) {
	println("++++++++++++++++++ Checking url " + link)
	skip = false
	for (def txt : bypassList) {
		if (link.indexOf(txt) > 0) {
			skip = true
			println('####################################### Bypassing ' + link)
			break
		}
	}
	return skip
}
	
def iterateLinkList(key) {
	println("Getting links for " + key)
	
	list = getLinkList(key)
	
	for(link in list) {
		
		println("Iter link is " + link)
		println("###getting dMap")
		if(!dMap.containsKey(link)) {
			println("returning " + link)
			keyStack.push(key)
			return link
		}
	}
}


