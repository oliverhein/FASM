@echo off

rem new start script of FASM Developer Environment
rem 2007-02-10 changed

set FASM_DRIVER=C:
set FASM_HOME=C:\Users\olive\git\FASM
set WORKDIR=%FASM_HOME%\run
set JUNGLIBDIR=%FASM_HOME%\lib\jung
set JADELIBDIR=%FASM_HOME%\lib\jade


set classpath=%JADELIBDIR%\jade.jar
set classpath=%CLASSPATH%;%JADELIBDIR%\iiop.jar
set classpath=%CLASSPATH%;%JADELIBDIR%\Base64.jar
set classpath=%CLASSPATH%;%JADELIBDIR%\jadetools.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\colt.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\concurrent.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\commons-collections-3.1.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\jung-1.7.2.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\resolver.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xercesimpl.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xml-apis.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\jbcl.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\log4j.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\jfreechart\jcommon-1.0.12.jar
set classpath=%CLASSPATH%;%FASM_HOME%\lib\jfreechart\jfreechart-1.0.9.jar
set classpath=%CLASSPATH%;%FASM_HOME%\run\etc
set classpath=%CLASSPATH%;%FASM_HOME%\lib\fasm.jar
rem set classpath=%CLASSPATH%;%FASM_HOME%\classes

echo %CLASSPATH%

pause

echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo !                                                                    !
echo ! Your current WORKDIR=%WORKDIR%                                      !
echo !                                                                    !
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

rem  Attention: Parameter is seperated by space not comma
set  JADEBASICCONFIG=%WORKDIR%\etc\jadebasic.cfg
set  AGENTCONFIGPATH=%WORKDIR%\run\config

%FASM_DRIVER%
cd %WORKDIR%

java -noverify -Xms128m -Xmx512m -DTRACEORDER=false -DAGENTCONFIGPATH=%AGENTCONFIGPATH% -DJADEBASICCONFIG=%JADEBASICCONFIG% -DSIMULATIONCONFIG=config/demo1.xml de.marketsim.gui.StartFrame
