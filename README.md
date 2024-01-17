# Simple Java Send Mail (sjsm)
A simple command line application for sending mails.

[![Java Maven Build](https://github.com/fuinorg/sjsm/actions/workflows/maven.yml/badge.svg)](https://github.com/fuinorg/sjsm/actions/workflows/maven.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/sjsm/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/sjsm/)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 11](https://img.shields.io/badge/JDK-11-green.svg)](https://openjdk.java.net/projects/jdk/11/)

## Versions
- 0.2.x = **Java 11** with new 'jakarta' namespace
- 0.1.0 = **Java 8**

## Why?
Sometimes it's nice to have a way to send an email without installing anything (except Java) on the system.
For example it could be used in an AWS CodeBuild "buildspec.yml" to send a mail on a failing build.
It's like [sSMTP](https://help.ubuntu.com/community/EmailAlerts), but without a configuration file.
Only command line arguments are used to configure mail server, receiver, message and other stuff. 

## Requirements
Make sure you have Java 11 installed on your machine.

## Running the application
You can download the latest JAR file here: https://github.com/fuinorg/sjsm/releases

	java -jar sjsm-0.3.0-SNAPSHOT.jar <arguments>

## Command line arguments

| Argument | Value                                                  | Required | Example                                                                                          |
| -------- |--------------------------------------------------------| -------- |--------------------------------------------------------------------------------------------------|
| -host | SMTPS server name                                      | yes | "smtp.no-where-no-no.com"                                                                        |
| -port | SMTPS port number (SSL/TLS)                            | yes | 465                                                                                              |
| -user | Your mailbox user                                      | yes | "acc12345_from.not.exist" or "from.not.exist@no-where-no-no.com" (depends on your mail provider) |
| -pw | Your mailbox password                                  | yes | "xxxxxxx"                                                                                        |
| -from | Sender's email address                                 | yes | "from.not.exist@no-where-no-no.com"                                                              |
| -to | Receiver's email address (multiple separated with ";") | yes | "to.not.exist@no-where-no-no.com" or "jane.doe@no-where-no-no.com;john.doe@no-where-no-no.com"             |
| -subject | Mail subject                                           | yes | "My subject"                                                                                     |
| -message | Message body (TEXT or HTML)                            | yes | "&lt;html&gt;&lt;body&gt;&lt;h1&gt;This is a test mail&lt;/h1&gt;&lt;/body&gt;&lt;/html&gt;"     |
| -html | -                                                      | no | -                                                                                                |
| -charset | Mail encoding (defaults to "utf-8")                    | no | "utf-8"                                                                                          |
| -important | Send High Priority Email (X-Priority flag)             | no | -                                                                                                |

## TEXT example

	java -jar sjsm-0.3.0-SNAPSHOT.jar \
		-host "smtp.no-where-no-no.com" \
		-port 465 \
		-user "acc12345_from.not.exist" \
		-pw "xxxxxxx" \
		-from "from.not.exist@no-where-no-no.com" \
		-to "to.not.exist@no-where-no-no.com" \
		-subject "My subject" \
		-message "This is a test mail"

## HTML example

	java -jar sjsm-0.3.0-SNAPSHOT.jar \
		-host "smtp.no-where-no-no.com" \
		-port 465 \
		-user "acc12345_from.not.exist" \
		-pw "xxxxxxx" \
		-from "from.not.exist@no-where-no-no.com" \
		-to "to.not.exist@no-where-no-no.com" \
		-subject "My html subject" \
		-message "<html><body><h1>This is a test mail</h1></body></html>" \
		-html \

## CAUTION
<b>Be aware that passing your password via the command line will most probably be visible in your command line history.</b>

* * *

## Snapshots

Snapshots can be found on the [OSS Sonatype Snapshots Repository](http://oss.sonatype.org/content/repositories/snapshots/org/fuin "Snapshot Repository"). 

Add the following to your .m2/settings.xml to enable snapshots in your Maven build:

```xml
<repository>
    <id>sonatype.oss.snapshots</id>
    <name>Sonatype OSS Snapshot Repository</name>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```
