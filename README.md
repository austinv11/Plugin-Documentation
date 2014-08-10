Plugin-Documentation
====================

A project to document plugins and easily add it into in-game books

Formatting
==========

##Plugin Sections

To create a new section for a plugin, you must create a new directory in the root directory with the exact name of the plugin (as seen when using /plugins) in all caps.

##The index.json file

Every plugin section needs an index.json file, this defines all important variables with the plugin's documentation

###Formatting the index.json file

If you want to learn about .json files, visit [here](http://www.w3schools.com/json/json_syntax.asp). Every index.json file **must** include the following variables: 
* Sectioned (true/false) --Whether the documentation contains sections

Based upon the aforementioned variables, some variables must be included, if Sectioned is true it must include:
* Sections (String Array) --A list of the names of the sections

If Sectioned is false, it must include:
* HasLinks (true/false) --Whether the plugin documentation has useful links
* Chapters (integer) --The number of chapters

If HasLinks is true, it must include:
* Links (JSON Array)

###Example index.json file for Towny

```JSON
{
"Sectioned": "true",
"Sections": "[Towns, Nations]"
}
```

###Example index.json file for a generic plugin

```JSON
{
"Sectioned": "false",
"HasLinks": "true",
"Chatpters": "3",
"Links": [{"Title": "Screenshots","URL": "coolpix.com"},{"Title": "See more info here:","URL": "wiki.website.com"}]
}
```

##Formatting documentation
Each chapter is written in a file with a title in the following format: Chapter[Insert Chapter Number Here].txt
When writing a chapter, you can specify a title by adding $$TITLE$$="Insert title in these quotes"
You could then write the entire chapter in this text file without any special formatting! This plugin supports color codes (seen [here](http://ess.khhq.net/mc/)).

##Formatting documentation with sections

Each section must have its own directory, named after the section title as defined with the Sections variable **THEY MUST MATCH EXACTLY**, each directory must have its own index.json file. This index.json file is formatted just like the original, except it must be done as if Sectioned is false (the section variable is also unneeded). Each section can then be treated as documentation for individual plugins.

###Example index.json

```JSON
{
"HasLinks": "true",
"Chatpters": "3",
"Links": [{"Title": "Screenshots","URL": "coolpix.com"},{"Title": "See more info here:","URL": "wiki.website.com"}]
}
```
