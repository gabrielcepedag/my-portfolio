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
@rem  practFinal_Acortador startup script for Windows
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

@rem Add default JVM options here. You can also use JAVA_OPTS and PRACT_FINAL_ACORTADOR_OPTS to pass JVM options to this script.
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

set CLASSPATH=%APP_HOME%\lib\practFinal_Acortador-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javalin-5.4.2.jar;%APP_HOME%\lib\javalin-rendering-5.4.2.jar;%APP_HOME%\lib\slf4j-simple-2.0.6.jar;%APP_HOME%\lib\thymeleaf-3.1.1.RELEASE.jar;%APP_HOME%\lib\h2-2.1.214.jar;%APP_HOME%\lib\jjwt-0.9.1.jar;%APP_HOME%\lib\jackson-annotations-2.14.0.jar;%APP_HOME%\lib\jackson-core-2.14.0.jar;%APP_HOME%\lib\javalin-openapi-plugin-5.3.2.jar;%APP_HOME%\lib\javalin-swagger-plugin-5.3.2.jar;%APP_HOME%\lib\javalin-redoc-plugin-5.3.2.jar;%APP_HOME%\lib\openapi-specification-5.3.2.jar;%APP_HOME%\lib\jackson-databind-2.14.0.jar;%APP_HOME%\lib\hibernate-core-6.2.0.CR2.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\javase-2.0.jar;%APP_HOME%\lib\okhttp-4.9.1.jar;%APP_HOME%\lib\json-20230227.jar;%APP_HOME%\lib\jakarta.servlet-api-4.0.3.jar;%APP_HOME%\lib\jaxws-rt-3.0.2.jar;%APP_HOME%\lib\jakarta.xml.ws-api-4.0.0.jar;%APP_HOME%\lib\saaj-impl-2.0.1.jar;%APP_HOME%\lib\jakarta.xml.soap-api-3.0.0.jar;%APP_HOME%\lib\jaxb-runtime-4.0.1.jar;%APP_HOME%\lib\jaxb-core-4.0.1.jar;%APP_HOME%\lib\jaxb-impl-3.0.2.jar;%APP_HOME%\lib\jaxb-core-3.0.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.0.jar;%APP_HOME%\lib\angus-activation-1.0.0.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.0.jar;%APP_HOME%\lib\jetty-http-spi-11.0.13.jar;%APP_HOME%\lib\httpclient-4.5.14.jar;%APP_HOME%\lib\jaxb-api-2.3.1.jar;%APP_HOME%\lib\grpc-netty-shaded-1.39.0.jar;%APP_HOME%\lib\grpc-protobuf-1.39.0.jar;%APP_HOME%\lib\grpc-stub-1.39.0.jar;%APP_HOME%\lib\proto-google-common-protos-2.0.1.jar;%APP_HOME%\lib\protobuf-java-3.17.3.jar;%APP_HOME%\lib\websocket-jetty-server-11.0.14.jar;%APP_HOME%\lib\jetty-annotations-11.0.14.jar;%APP_HOME%\lib\jetty-plus-11.0.14.jar;%APP_HOME%\lib\jetty-webapp-11.0.14.jar;%APP_HOME%\lib\websocket-servlet-11.0.14.jar;%APP_HOME%\lib\jetty-servlet-11.0.14.jar;%APP_HOME%\lib\jetty-security-11.0.14.jar;%APP_HOME%\lib\websocket-core-server-11.0.14.jar;%APP_HOME%\lib\jetty-server-11.0.14.jar;%APP_HOME%\lib\websocket-jetty-common-11.0.14.jar;%APP_HOME%\lib\websocket-core-common-11.0.14.jar;%APP_HOME%\lib\jetty-http-11.0.14.jar;%APP_HOME%\lib\jetty-io-11.0.14.jar;%APP_HOME%\lib\jetty-xml-11.0.14.jar;%APP_HOME%\lib\jetty-jndi-11.0.14.jar;%APP_HOME%\lib\jetty-util-11.0.14.jar;%APP_HOME%\lib\slf4j-api-2.0.6.jar;%APP_HOME%\lib\websocket-jetty-api-11.0.14.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.7.10.jar;%APP_HOME%\lib\ognl-3.3.4.jar;%APP_HOME%\lib\attoparser-2.0.6.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jandex-3.0.5.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.12.18.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.0.jar;%APP_HOME%\lib\antlr4-runtime-4.10.1.jar;%APP_HOME%\lib\javase-3.1.0.jar;%APP_HOME%\lib\core-2.0.jar;%APP_HOME%\lib\jfreesvg-2.1.jar;%APP_HOME%\lib\okio-jvm-2.8.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.7.10.jar;%APP_HOME%\lib\kotlin-stdlib-1.7.10.jar;%APP_HOME%\lib\streambuffer-2.0.2.jar;%APP_HOME%\lib\stax-ex-2.0.1.jar;%APP_HOME%\lib\jakarta.mail-2.0.1.jar;%APP_HOME%\lib\jakarta.activation-2.0.1.jar;%APP_HOME%\lib\ha-api-3.1.13.jar;%APP_HOME%\lib\gmbal-api-only-4.0.3.jar;%APP_HOME%\lib\management-api-3.2.3.jar;%APP_HOME%\lib\mimepull-1.9.15.jar;%APP_HOME%\lib\FastInfoset-2.0.0.jar;%APP_HOME%\lib\woodstox-core-6.2.6.jar;%APP_HOME%\lib\stax2-api-4.2.1.jar;%APP_HOME%\lib\jakarta.jws-api-3.0.0.jar;%APP_HOME%\lib\jakarta.annotation-api-2.1.1.jar;%APP_HOME%\lib\jakarta.ws.rs-api-3.1.0.jar;%APP_HOME%\lib\swagger-ui-3.52.5.jar;%APP_HOME%\lib\redoc-2.0.0-rc.70.jar;%APP_HOME%\lib\js-tokens-5.0.0.jar;%APP_HOME%\lib\httpcore-4.4.16.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-codec-1.11.jar;%APP_HOME%\lib\javax.activation-api-1.2.0.jar;%APP_HOME%\lib\grpc-core-1.39.0.jar;%APP_HOME%\lib\grpc-protobuf-lite-1.39.0.jar;%APP_HOME%\lib\grpc-api-1.39.0.jar;%APP_HOME%\lib\guava-30.1-android.jar;%APP_HOME%\lib\error_prone_annotations-2.4.0.jar;%APP_HOME%\lib\perfmark-api-0.23.0.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\jetty-jakarta-servlet-api-5.0.2.jar;%APP_HOME%\lib\javassist-3.29.0-GA.jar;%APP_HOME%\lib\core-3.1.0.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.7.10.jar;%APP_HOME%\lib\gson-2.10.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\checker-compat-qual-2.5.5.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\annotations-4.1.1.4.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.19.jar;%APP_HOME%\lib\grpc-context-1.39.0.jar;%APP_HOME%\lib\asm-commons-9.4.jar;%APP_HOME%\lib\asm-tree-9.4.jar;%APP_HOME%\lib\asm-9.4.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\txw2-4.0.1.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.1.jar


@rem Execute practFinal_Acortador
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %PRACT_FINAL_ACORTADOR_OPTS%  -classpath "%CLASSPATH%" main.Main %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable PRACT_FINAL_ACORTADOR_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%PRACT_FINAL_ACORTADOR_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
