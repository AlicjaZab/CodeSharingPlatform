**Code Sharing Platform**
Simple Code Sharing Platform, made under the supervision of JetBrains Academy (https://hyperskill.org/projects/130?track=1).

Key Words : JPA, H2 database, REST API, Spring framework (Boot, Security, Web, MVC), Gradle, JSON, HTML, CSS


The Code Sharing Platform supports following operations:
* Adding new code snippets
* Showing up to 10 latest posts
* Showing specifc code snippet

Additional informations:
* All of the actions are available through the API and the web interface.
* Displaying specific code snippet is availeble only through its UUID, which is displayed in a pop-up message after creating a code snippet (or returned in JSON through API).
* A code snippet may have a limited number of views allowed, or time during witch it can be viewed. 
  * If any of those restrictions is reached, a code snippet is deleted from the database.
  * Restrictions can be added while creating a code snippet. Zero or a negative number in the input is considered as no limitation.
  * If any restriction is imposed on the element, it will not be displayed in the *latest* view (it's only available through UUID).

**Working through web interface**


**Latest posts**
Got to http://localhost:8889/code/latest:
![alt text](https://github.com/AlicjaZab/CodeSharingPlatform/blob/main/images/latest.PNG?raw=true)



**Working through API**
**Creating new quizzes**
Send POST request to /api/quizzes and JSON with title, text
```
{
  "title": "The apple",
  "text": "What color can an apple be?",
  "options": ["Green","Blue","Red","Orange"],
  "answer": [0,2]
}
```
