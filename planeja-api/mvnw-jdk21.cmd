@echo off
REM Executa o Maven Wrapper deste projeto forcando o JDK 21, sem alterar
REM o JAVA_HOME global do Windows (nao afeta outras aplicacoes, ex.: WebSphere).
REM Uso: mvnw-jdk21.cmd compile
REM      mvnw-jdk21.cmd clean install

setlocal
set "JAVA_HOME=C:\SDK\Java\jdk-21.0.12"
set "PATH=%JAVA_HOME%\bin;%PATH%"

call "%~dp0mvnw.cmd" %*
endlocal
