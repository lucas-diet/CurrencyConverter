*** For english version see below ***

###########
Einrichtung
###########

Benötigte Software:
- pgAdmin
- Postgres

Benötigte Datenbank:
- Datenbank mit dem Namen "Currency"

Was macht das Programm?
Es handelt sich um eine grafische Benutzeroberfläche (GUI), die Euro in jede andere Währung umrechnen kann, sofern sie in der Datenbank hinterlegt ist.

##########
Verwendung
##########

Information:
Beim ersten Start der GUI und dem Hinzufügen einer Währung werden automatisch Tabellen für den Wechselkurs ("Exchange Rate") und Währung ("Currency") in die Datenbank eingefügt.

Wie fügt man eine neue Währung in die Datenbank ein?
Um eine neue Währung hinzuzufügen, starten Sie das Programm und geben Sie in der GUI folgende Attribute ein:

- Currency -> Name der Währung (z. B.: US-Dollar)
- ISO -> Abkürzung (z. B.: USD)
- Rate -> Umrechnungskurs für 1 EUR (z. B.: 1.09 USD)

-------------------------------------------------------------------------

###########
Setup
###########

Required Software:
- pgAdmin
- Postgres

Required Database:
- Database named "Currency"

What does the program do?
It is a graphical user interface (GUI) capable of converting Euros to any other currency, provided it is stored in the database.

##########
Usage
##########

Information:
Upon the initial launch of the GUI and the addition of a currency, tables for the exchange rate ("Exchange Rate") and currency ("Currency") are automatically inserted into the database.

How to add a new currency to the database?
To add a new currency, launch the program and enter the following attributes in the GUI:

- Currency -> Name of the currency (e.g., US-Dollar)
- ISO -> Abbreviation (e.g., USD)
- Rate -> Conversion rate for 1 EUR (e.g., 1.09 USD)
