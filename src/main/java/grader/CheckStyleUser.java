package grader;

import java.io.File;
import java.util.List;

//import org.apache.commons.cli.CommandLine;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class CheckStyleUser {
//	public static  final String CONFIG_FILE = "D:/dewan_backup/Java/lib/style_checks.xml";
	public static  final String CONFIG_FILE = "test_style_checks.xml";

	public static final String JAVA_FILE = "D:/dewan_backup/Java/Grader/src/grader/StringSplitter.java";

	public static void main(String[] args) {
		final AuditListener listener = createListener();
		final Configuration config = loadConfig();
		final List<File> files = getFilesToProcess();
		final Checker c = createChecker(config, listener);
		final int numErrs = c.process(files);
		c.destroy();
		System.exit(numErrs);

	}

	private static Checker createChecker(Configuration aConfig,
			AuditListener aNosy) {
		Checker c = null;
		try {
			c = new Checker();

			final ClassLoader moduleClassLoader = Checker.class
					.getClassLoader();
			c.setModuleClassLoader(moduleClassLoader);
			c.configure(aConfig);
			c.addListener(aNosy);
		} catch (final Exception e) {
			System.out.println("Unable to create Checker: " + e.getMessage());
			e.printStackTrace(System.out);
			System.exit(1);
		}
		return c;
	}

	private static Configuration loadConfig() {
		try {
			return ConfigurationLoader.loadConfiguration(CONFIG_FILE,
					new PropertiesExpander(System.getProperties()));
		} catch (final CheckstyleException e) {
			System.out.println("Error loading configuration file");
			e.printStackTrace(System.out);
			System.exit(1);
			return null; // can never get here
		}
	}

	private static List<File> getFilesToProcess() {
		final List<File> files = Lists.newLinkedList();
		files.add(new File(JAVA_FILE));
		return files;
	}
	
	private static AuditListener createListener() {
		return new DefaultLogger(System.out, false);
	}

}
