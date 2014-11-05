<html>
<body>
	<h1>Summary</h1>
	<div>
		Grails plugin integrating Primefaces into the Grails project.
		<br>
		The minimum Grails version is 2.3.7 with Java JDK 1.7 and the dependencies have versions:
		<ul>
			<li>Core JSF 2.2</li>
			<li>Primefaces 5.1</li>
			<li>Apache MyFaces 2.2.5</li>
		</ul>
		<br>
		Source code can be found at <a href="https://github.com/andreaminnucci77/primefaces">Github</a>
		<h2>Installation</h2>
		Install the Primefaces plugin in any Grails project by setting the plugin-dependency in the <b>BuildConfig.groovy</b> file, e.g.:
		<pre>
			<code>
	grails.project.dependency.resolution = {
	  ...
	  plugins {
		...
		compile ":primefaces:0.1"
		...
	  }
	  ...
	}
			</code>
		</pre>
		<h2>Description</h2>
		<div>
			You can either create a bean class in Java or Groovy, and the you make the primefaces pages into web-app folder.
			Configure, in the <b>Config.groovy</b>, the list of packages which create the bean class.
			<br>Example:
			<pre>
				<code>
	grails.plugins.primefaces.beans.packages = [ "com.company.demo.beans" ]
				</code>
			</pre>
			<h2>Internationalization</h2>
			To access resources grails/i18n project, in the Bean classes you have to instantiate the class <b>grails.plugins.primefaces.MessageSourceBean</b>
			with the annotation <b>javax.faces.bean.ManagedProperty</b>, and in the xhtml page you have to use:
			<pre>
				<code>
	<b><u>Bean Source</u></b>:
		@ManagedProperty(value = "#{message}")
		MessageSourceBean message
		<br>
	<b><u>XTHML Page</u></b>:
		#{message.i18n(code)} or
		#{message.i18n(code, args)} or
		#{message.i18n(code, args, locale)}
				</code>
			</pre>
			<h2>Grails Service</h2>
			To access the domain class of grails, you must create a service, and define within the bean by annotating <b>grails.plugins.primefaces.GrailsService</b>
			<br>Example:
			<pre>
				<code>
	@GrailsService(name = "anagraphicService")
	AnagraphicService anagraphicService
				</code>
			</pre>
			<br>
			All pages created with primefaces framework can be reached under the link:
				<a href="localhost:8080/appname/faces">localhost:8080/appname/faces</a>
			<br>
			<h2>Example</h2>
			Example project can be found at <a href="https://github.com/andreaminnucci77/grails-primefaces-demo">Github</a>
		</div>
	</div>
</body>
</html>