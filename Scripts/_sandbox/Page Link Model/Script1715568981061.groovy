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

A = "A"
B = "B"
C = "C"
D = "D"
E = "E"
F = "F"
G = "G"
H = "H"
J = "J"
K = "K"
L = "L"
M = "M"

dMap =[:]

sMap = [A:[B,C,D,E], B:[A,C,F,G], C:[A,F,H,J], D:[A,B,E,F,J,K], E:[A,B,C,F,K,L,M], F:[A,M], G:[A,C,E], H:[A,F,G,D], J:[A,C,E], K:[B,F,J], L:[B,D], M:[B,E,A,D,G]]

v = sMap.get(B)
println(v)

keyStack = []

doneList = []

myKey = A

myList = sMap.get(myKey)

println(myList)

done = false

while(!done) {
	
	if(myList.size() > 0) {
		
		println("The key is " + myKey + " and list is " + myList)
		
		dMap.put(myKey,myList)
		
		println('***MAP***')
		
		println(dMap)
	
		myKey = iterateLinkList(myKey)
		
		if(myKey != null) {
		
			println("Key value is " + myKey)
			
			doneList.add(myKey)
			
			myList = sMap.get(myKey)
			
			println("myList is " + myList)
		} else {
			if(!keyStack.isEmpty()) {
				myKey = keyStack.pop()
				myList = sMap.get(myKey)
				
				println("Popped myList is " + myList)
			} else {
				done = true
			}
			

		}
		
	}
	
}

println(doneList)
	
def iterateLinkList(key) {
	println("Getting links for " + key)
	list = sMap.get(key)
	
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

