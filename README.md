Plugin-Documentation
====================

A project to document plugins and easily add it into in-game books

In-Game Usage
=============

##How it works

After using any of the commands (with the exception of /dump), the plugin will then read from various files in this repository. When using /plugin-list or /contributors, it'll generate a list from the respective PLUGINS.md and CONTRIBUTORS.md. When using /plugin-help, it'll look for the given plugin's index.json which gives the plugin 'directions' in order to find all the nesscesary info.
This plugin also has a feature which allows for caching information from this repo internally. This will speed up the usage of commands (with the exception of the first time it is executed). However this is more taxing on RAM usage.

##Commands

* /contributors (perm node: PluginDocumentation.contributors): Prints a list of contributors to the project.
* /dump (perm node: PluginDocumentation.dump): Dumps all currently cached data, lowering RAM usage (albeit temporarily)
* /plugin-list (perm node: PluginDocumentation.plugin-list): Prints a list of plugins supported.
* /plugin-help (perm node: PluginDocumentation.plugin-help): Attempts to fetch the documentation for the given plugin
* /book-converter (perm node: PluginDocumentation.book-converter): Attempts to paste the contents of the book in your hand to pastebin-useful for transferring in-game documentation to this project. **WARNING:** May be removed in the future.

##Config options

* InternalCaching (deafault: true): Determines whether the plugin should actively cache data.
* ShowLinks (default: true): Determines whether the plugin will display links if any given plugin documentation book has links.
* Debug (deafult: false): Determines whether the plugin will print the stack trace if a command encounters an error.

Formatting
==========

##Plugin Sections

To create a new section for a plugin, you must create a new directory in the root directory with the exact name of the plugin (as seen when using /plugins) in all caps.

##The index.json file

Every plugin section needs an index.json file, this defines all important variables with the plugin's documentation

###Formatting the index.json file

If you want to learn about .json files, visit [here](http://www.w3schools.com/json/json_syntax.asp). Every index.json file **must** include the following variables: 
* Sectioned (true/false) --Whether the documentation contains sections
* Version (String) --Must be changed after every revision

Based upon the aforementioned variables, some variables must be included, if Sectioned is true it must include:
* Sections (String Array) --A list of the names of the sections

If Sectioned is false, it must include:
* HasLinks (true/false) --Whether the plugin documentation has useful links
* Chapters (integer) --The number of chapters

If HasLinks is true, it must include:
* Links (JSON Array) --An Array of links and their titles

###Example index.json file for Towny

```JSON
{
Sectioned: "true",
Version: "1.0.0",
Sections: ["Towns", "Nations"]
}
```

###Example index.json file for a generic plugin

```JSON
{
Sectioned: "false",
Version: "1.0.0-pre",
HasLinks: "true",
Chapters: "3",
Links: [{Title: "Screenshots",URL: "coolpix.com"},{Title: "See more info here",URL: "wiki.website.com"}]
}
```

##Formatting documentation
Each chapter is written in a file with a title in the following format: Chapter[Insert Chapter Number Here].txt
When writing a chapter, you can specify a title by adding $$TITLE$$="Insert title in these quotes" at the top of the page.
You could then write the entire chapter in this text file without any special formatting! This plugin supports color codes (seen [here](http://ess.khhq.net/mc/)).

##Formatting documentation with sections

Each section must have its own directory, named after the section title as defined with the Sections variable **THEY MUST MATCH EXACTLY**, each directory must have its own index.json file. This index.json file is formatted just like the original, except it must be done as if Sectioned is false (the Sectioned and Version variables are also unneeded). Each section can then be treated as documentation for individual plugins.

###Example index.json

```JSON
{
HasLinks: "true",
Chapters: "3",
Links: [{Title: "Screenshots",URL: "coolpix.com"},{Title: "See more info here",URL: "wiki.website.com"}]
}
```
