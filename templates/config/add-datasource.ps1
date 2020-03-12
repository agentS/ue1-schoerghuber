$bin = Join-Path $([Environment]::GetEnvironmentVariable("JBOSS_HOME", "User")) "bin"

$cmd='data-source add --name=FhBayDS --driver-name="h2" --connection-url="jdbc:h2:~/h2/FhBayDb;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1" --jndi-name="java:jboss/datasources/FhBayDS" --user-name="sa" --password="sa" --enabled=true --use-java-context=true'
& $bin\jboss-cli.ps1 --connect --command="$cmd"

#use option --controller=127.0.0.1:<port> if jboss management runs under a different port.