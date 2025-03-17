---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# NUSMeet User Guide
**NUSMeet** is a **Command Line Interface (CLI)** application designed for **NUS students** to **efficiently manage and organize** their contacts. It focuses on helping users **find coursemates** for study discussions, with **tagging** and **filtering** features tailored to student life.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure that Java `17` or above installed in your Computer.<br>
   * **Windows users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html).
   * **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
   * **Linux users** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationLinux.html).
2. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103T-T13-3/tp/releases).

3. Copy the downloaded file to a folder that you want to use as the _home folder_ for your NUSMeet application.

4. Open a command terminal, navigate to the folder where you placed the JAR file using `cd /path/to/your/folder`, and use the `java -jar nusmeet.jar` command to run the application.<br>
   A Graphical User Interface (GUI) similar to the one shown below should appear in a few seconds.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 c/CS2103T c/CS2101 t/friends t/owesMoney f/CLOSE_FRIEND` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**[Add](#adding-a-friend-add)**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**[Clear](#clearing-all-entries--clear)**  | `clear`
**[Delete](#deleting-a-person--delete)** | `delete INDEX`<br> e.g., `delete 3`
**[Edit](#editing-a-person--edit)**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**[Find](#locating-persons-by-name-find)**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**[List](#listing-all-persons--list)**   | `list`
**[Help](#viewing-help--help)**   | `help`



<box type="info" seamless>

**Notes about the command format:**<br>

#### 1. Upper Case

 * Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

#### 2. Square Brackets ([ ])
* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

#### 3. Triple Dots (…​)
* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

#### 4. Ordering
* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

#### 5. Parameters
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

#### 6. PDF
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>


## Features

### 1. Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### 2. Adding a friend: `add`

Adds a friend to the contact book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [c/COURSE]…​ [t/TAG]…​ [f/FRIENDSHIP_LEVEL]`

<box type="tip" seamless>

**Tip:** A friend can have any number of tags & course (including 0)
</box>

**Note:** The Friend Tag can only be one of the following `CLOSE_FRIEND`, `FRIEND`, `ACQUAINTANCES`

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 c/CS2103T c/CS2101 t/friends t/owesMoney f/CLOSE_FRIEND`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 f/FRIEND`

### 3. Listing all persons : `list`

Shows a list of all friends in the contact book.

Format: `list`

### 4. Editing a person : `edit`

Edits an existing friend in the contact book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [c/COURSE]…​ [f/FRIENDSHIP] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### 5. Locating persons by name: `find`

Finds friends whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### 6. Deleting a person : `delete`

Deletes the specified friend from the contact book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### 7. Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### 8. Exiting the program : `exit`

Exits the program.

Format: `exit`

### 9. Saving the data

NUSMeet data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### 10. Editing the data file

NUSMeet data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, NUSMeet will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the NUSMeet to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### 11. Automatic Courses Archival

Once the semester ends, all entries in the contact book will be updated. A friend's current courses will be cleared, and their current courses will be automatically moved to the previous courses section.
This enhances efficiency, as users no longer need to manually update all their contacts.

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous NUSMeet home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------


## Glossary

Word    | Meaning
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**CLI**    | A text-based interface where users interact with an application
**Command Terminal**   | A console application where users enter commands.
**Java**    | A programming language required to run an application.
**JDK**   | A software package needed to run Java applications.
**JSON**   |  JavaScript Object Notation (JSON) is a lightweight data format used to store and exchange data
**GUI**   |  A visual interface that allows users to interact with an application.
