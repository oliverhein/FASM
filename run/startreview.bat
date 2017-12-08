@echo off


set FASM_HOME=C:\FASMdev-new
set WORKDIR=%FASM_HOME%\run
set JUNGLIBDIR=%FASM_HOME%\lib\jung

set classpath=%FASM_HOME%\classes
set classpath=%CLASSPATH%;%FASM_HOME%\lib\fasm.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\colt.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\concurrent.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\commons-collections-3.1.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\jung-1.7.2.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\resolver.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xercesimpl.jar
set classpath=%CLASSPATH%;%JUNGLIBDIR%\xml-apis.jar


echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
echo ! Script for review  of the graph state of the simulation            !
echo ! Version 2006-08-25                                                 !
echo ! The current WORKDIR=%WORKDIR%                                      
echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

echo classpath=%CLASSPATH%

rem  Attention: Parameter is seperated by space not comma
set  JADEBASICCONFIG=C:\diss\programme\eigene\kapsim\storeprotest\run\etc\jadebasic.cfg
set  AGENTCONFIGPATH=C:\diss\programme\eigene\kapsim\storeprotest\run\


cd %WORKDIR%

rem java -noverify -Xms64m -Xmx512m -DAGENTCONFIGPATH=%AGENTCONFIGPATH% -DJADEBASICCONFIG=%JADEBASICCONFIG% -DSIMULATIONCONFIG=simulation1.cfg de.marketsim.gui.StartFrame

java -Xms200m -Xmx300m de.marketsim.gui.graph.RunReviewer
