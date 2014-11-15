import groovy.text.SimpleTemplateEngine

includeTargets << grailsScript('_GrailsBootstrap')

overwriteAll = false
templateAttributes = [:]
templateDir = "$primefacesPluginDir/src/templates"
appDir = "$basedir/grails-app"
templateEngine = new SimpleTemplateEngine()

packageToDir = { String packageName ->
	String dir = ''
	if (packageName) {
		dir = packageName.replaceAll('\\.', '/') + '/'
	}
	return dir
}

okToWrite = { String dest ->
	File file = new File(dest)
	if (overwriteAll || !file.exists()) {
		return true
	}

	String propertyName = "file.overwrite.$file.name"
	ant.input(addProperty: propertyName, message: "$dest exists, ok to overwrite?",
	          validargs: 'y,n,a', defaultvalue: 'y')

	if (ant.antProject.properties."$propertyName" == 'n') {
		return false
	}

	if (ant.antProject.properties."$propertyName" == 'a') {
		overwriteAll = true
	}

	true
}

generateFile = { String templatePath, String outputPath ->
	if (!okToWrite(outputPath)) {
		return
	}

	File templateFile = new File(templatePath)
	if (!templateFile.exists()) {
		errorMessage "\nERROR: $templatePath doesn't exist"
		return
	}

	File outFile = new File(outputPath)

	// in case it's in a package, create dirs
	ant.mkdir dir: outFile.parentFile

	outFile.withWriter { writer ->
		templateEngine.createTemplate(templateFile.text).make(templateAttributes).writeTo(writer)
	}

	printMessage "generated $outFile.absolutePath"
}

splitClassName = { String fullName ->

	int index = fullName.lastIndexOf('.')
	String packageName = ''
	String className = ''
	if (index > -1) {
		packageName = fullName[0..index-1]
		className = fullName[index+1..-1]
	}
	else {
		packageName = ''
		className = fullName
	}

	[packageName, className]
}

copyFile = { String from, String to ->
	if (!okToWrite(to)) {
		return
	}

	ant.copy file: from, tofile: to, overwrite: true
}

printMessage = { String message -> event('StatusUpdate', [message]) }
errorMessage = { String message -> event('StatusError', [message]) }