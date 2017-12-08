@echo off

rem new start script of FASM clients
rem 2006-08-24 changed

set FASM_DRIVER=F:
set FASM_HOME=F:\FASMDEV-NEW
set WORKDIR=%FASM_HOME%\run
set JUNGLIBDIR=%FASM_HOME%\lib\jung
set JADELIBDIR=%FASM_HOME%\lib\jade

set classpath=%JADELIBDIR%\jade.jar
set classpath=%CLASSPATH%;%JADELIBDIR%\iiop.jar
set classpath=%CLASSPATH%;%JADELIBDIR%\Base64.jar
set classpath=%CLASSPATH%;%JADELIBDIR%\jadetools.jar
set classpath=%CLASSPATH%;%JUNGLIBDIRDIR%\colt.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\concurrent.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\commons-collections-3.1.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\jung-1.7.2.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\resolver.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xercesimpl.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xml-apis.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\jbcl.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\log4j.jar
set classpath=%CLASSPATH%;%FASM_HOME%\run\etc

set classpath=%CLASSPATH%;%FASM_HOME%\classes

echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !                                                                    !
echo !  This is fasmclient.bat                                            !
echo !  fasmclient.bat is used to start AgentSimulator.                   !
echo !  An AgentSimulator will manage and simulate some agents.           !
echo !  If multi AgentSimulator start parallel, the load of simulation    ! 
echo !  will be distributed to the different simulators. It will only make!
echo !  sense when the AgentSimulators are started on different PCS.      !
echo !  The Simulatorname will be given by the central Register Manager   !
echo !                                                                    !
echo !  The current WORKDIR=%WORKDIR%
echo !                                                                    !
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

echo classpath=%CLASSPATH%
set  JADEBASICCONFIG=%WORKDIR%\etc\jadebasic.cfg

%FASM_DRIVER%
cd %WORKDIR%

java -noverify -Xms128m -Xmx512m -DJADEBASICCONFIG=%JADEBASICCONFIG%  de.marketsim.agent.trader.BootAgentSimulator

