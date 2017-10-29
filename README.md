# sjsm
A simple java send mail command line application.

## Why?
Sometimes it's nice to have a way to send an email without installing anything on the system.
For example it could be used in an AWS CodeBuild "buildspec.yml" to send a mail on a failing build.

## Requirements
Make sure you have Java 8 installed on your machine.

## Running the app

	java -jar sjsm-0.1.0.jar <arguments>

## Command line arguments

| Argument | Value | Required | Example |
| -------- | ----- | -------- | ------- |
| -host | SMTPS server name | yes | "smtp.no-where-no-no.com" |
| -port | SMTPS port number (SSL/TLS) | yes | 465 |
| -user | Your mailbox user | yes | "acc12345_from.not.exist" or "from.not.exist@no-where-no-no.com" (depends on your mail provider) |
| -pw | Your mailbox password | yes | "xxxxxxx" |
| -from | Sender's email address | yes | "from.not.exist@no-where-no-no.com" |
| -to | Receiver's email address | yes | "to.not.exist@no-where-no-no.com" |
| -subject | Mail subject | yes | "My subject" |
| -message | Message body (TEXT or HTML) | yes | "<html><body><h1>This is a test mail</h1></body></html>" |
| -html | - | no | - |
| -charset | Mail encoding (defaults to "utf-8") | no | "utf-8"  |

## TEXT example

	java -jar sjsm-0.1.0.jar \
		-host "smtp.no-where-no-no.com" \
		-port 465 \
		-user "acc12345_from.not.exist" \
		-pw "xxxxxxx" \
		-from "from.not.exist@no-where-no-no.com" \
		-to "to.not.exist@no-where-no-no.com" \
		-subject "My subject" \
		-message "This is a test mail"

## HTML example

	java -jar sjsm-0.1.0.jar \
		-host "smtp.no-where-no-no.com" \
		-port 465 \
		-user "acc12345_from.not.exist" \
		-pw "xxxxxxx" \
		-from "from.not.exist@no-where-no-no.com" \
		-to "to.not.exist@no-where-no-no.com" \
		-subject "My subject" \
		-message "<html><body><h1>This is a test mail</h1></body></html>" \
		-html \

## CAUTION
Be aware that passing your password via the command line will most probably be visible in your command line history.

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
