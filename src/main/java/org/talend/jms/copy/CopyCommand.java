package org.talend.jms.copy;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=", commandDescription = "Record changes to the repository")
public class CopyCommand {

    @Parameter(names="-surl", description = "URL of source ActiveMQ. Example: tcp://localhost:61616", required=true)
    String surl;
    
    @Parameter(names="-durl", description = "URL of destination ActiveMQ", required=true)
    String durl;
    
    @Parameter(names="-user", description = "Username")
    String user;

    @Parameter(names="-password", description = "Password")
    String password;

    @Parameter(names="-filter", description = "Filter for queues to copy like *.DLQ")
    String filter = "*";
}
