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
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

import groovy.io.FileType
import org.apache.commons.io.FileUtils

import javax.swing.*

folders = ['Scripts']
list = []
dirList = []

for (folder in folders) {

//path = GlobalVariable.projectPath+ '/Scripts/'
//	path = GlobalVariable.projectPath + '/' + folder + '/'
	path = GlobalVariable.projectPath + '/' + folder
	println(path)
	
	dirLen = path.length()
	dir = new File(path)
	dir.eachFileRecurse (FileType.DIRECTORIES) { file ->
		list << file
	  }
	 
	list.each {
		p = it.path
		dir = p.substring(dirLen)
//		if (!dir.contains('/') && !dir.contains('archive')) {
		if (!dir.contains('Copy') && !dir.contains('Archive') && !dir.contains('_')  && !dir.contains('Functions')) {
			dirList.add(p)
		}
	}
}

//newPath = GlobalVariable.projectPath + '/Test Listeners'
//dirList.add(newPath)
dirList.sort()
dirList.each{
//	println(it)
	println(it.substring(path.length()+1))
}
return dirList