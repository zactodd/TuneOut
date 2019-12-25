// 
// Decompiled by Procyon v0.5.36
// 

package Model.command;

import seng302.App;
import Environment.Environment;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ShowVersionCommand extends Command
{
    static Logger log;
    
    public ShowVersionCommand() {
        final Properties properties = new Properties();
        try {
            final InputStream stream = this.getClass().getResourceAsStream("/Model/ProjectProperties.properties");
            properties.load(stream);
            this.returnValue = properties.getProperty("versionNumber");
        }
        catch (Exception e) {
            ShowVersionCommand.log.debug("Properties file could not be loaded");
        }
    }
    
    @Override
    public void execute(final Environment env) {
        env.setResponse(this.returnValue);
    }
    
    static {
        ShowVersionCommand.log = Logger.getLogger(App.class.getName());
    }
}
