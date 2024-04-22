@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  carritoCompra_2 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and CARRITO_COMPRA_2_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\carritoCompra_2-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javalin-5.3.2.jar;%APP_HOME%\lib\slf4j-simple-2.0.6.jar;%APP_HOME%\lib\jackson-annotations-2.14.0.jar;%APP_HOME%\lib\jackson-core-2.14.0.jar;%APP_HOME%\lib\jackson-databind-2.14.0.jar;%APP_HOME%\lib\thymeleaf-3.1.1.RELEASE.jar;%APP_HOME%\lib\javalin-rendering-5.3.2.jar;%APP_HOME%\lib\jasypt-1.9.3.jar;%APP_HOME%\lib\hibernate-core-6.2.0.CR2.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\h2-2.1.214.jar;%APP_HOME%\lib\postgresql-42.5.4.jar;%APP_HOME%\lib\java-dotenv-5.2.2.jar;%APP_HOME%\lib\j2html-1.0.0.jar;%APP_HOME%\lib\websocket-jetty-server-11.0.13.jar;%APP_HOME%\lib\jetty-annotations-11.0.13.jar;%APP_HOME%\lib\jetty-plus-11.0.13.jar;%APP_HOME%\lib\jetty-webapp-11.0.13.jar;%APP_HOME%\lib\websocket-servlet-11.0.13.jar;%APP_HOME%\lib\jetty-servlet-11.0.13.jar;%APP_HOME%\lib\jetty-security-11.0.13.jar;%APP_HOME%\lib\websocket-core-server-11.0.13.jar;%APP_HOME%\lib\jetty-server-11.0.13.jar;%APP_HOME%\lib\websocket-jetty-common-11.0.13.jar;%APP_HOME%\lib\websocket-core-common-11.0.13.jar;%APP_HOME%\lib\jetty-http-11.0.13.jar;%APP_HOME%\lib\jetty-io-11.0.13.jar;%APP_HOME%\lib\jetty-xml-11.0.13.jar;%APP_HOME%\lib\jetty-jndi-11.0.13.jar;%APP_HOME%\lib\jetty-util-11.0.13.jar;%APP_HOME%\lib\slf4j-api-2.0.6.jar;%APP_HOME%\lib\websocket-jetty-api-11.0.13.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.7.10.jar;%APP_HOME%\lib\ognl-3.3.4.jar;%APP_HOME%\lib\attoparser-2.0.6.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.7.10.jar;%APP_HOME%\lib\kotlin-stdlib-1.7.10.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jandex-3.0.5.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.12.18.jar;%APP_HOME%\lib\jaxb-runtime-4.0.1.jar;%APP_HOME%\lib\jaxb-core-4.0.1.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.0.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.0.jar;%APP_HOME%\lib\antlr4-runtime-4.10.1.jar;%APP_HOME%\lib\checker-qual-3.5.0.jar;%APP_HOME%\lib\jetty-jakarta-servlet-api-5.0.2.jar;%APP_HOME%\lib\javassist-3.29.0-GA.jar;%APP_HOME%\lib\angus-activation-1.0.0.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.0.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\asm-commons-9.4.jar;%APP_HOME%\lib\asm-tree-9.4.jar;%APP_HOME%\lib\asm-9.4.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.7.10.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\txw2-4.0.1.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.1.jar


@rem Execute carritoCompra_2
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %CARRITO_COMPRA_2_OPTS%  -classpath "%CLASSPATH%" org.example.Main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable CARRITO_COMPRA_2_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%CARRITO_COMPRA_2_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
