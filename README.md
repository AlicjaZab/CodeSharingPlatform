**Code Sharing Platform**

Simple Code Sharing Platform, made under the supervision of JetBrains Academy (https://hyperskill.org/projects/130?track=1).

Key Words : JPA, H2 database, REST API, Spring Boot, Gradle, JSON, HTML, CSS


The Code Sharing Platform supports following operations:
* Adding new code snippets
* Showing up to 10 latest posts
* Showing specifc code snippet

Additional informations:
* All of the actions are available through the API and the web interface.
* Displaying specific code snippet is availeble only through its UUID (ID), which is displayed in a pop-up message after creating a code snippet (or returned in JSON through API).
* For each post (code snippet) there is saved date and time of posting it.
* A code snippet may have a limited number of views allowed, or time during witch it can be viewed. 
  * If any of those restrictions is reached, a code snippet is deleted from the database.
  * Restrictions can be added while creating a code snippet. Zero or a negative number in the input is considered as no limitation.
  * If any restriction is imposed on the element, it will not be displayed in the *latest* view (it's only available through UUID).

_________________________________________________

**Working through web interface**


*Latest posts*

Go to http://localhost:8889/code/latest:
![alt text](https://github.com/AlicjaZab/CodeSharingPlatform/blob/main/images/latest.PNG?raw=true)
and simply you will see 10 latest posts (or less, if there is less of them in the database).


*Specified code snippet*

Go to http://localhost:8889/code/[UUID]:
![alt text](https://github.com/AlicjaZab/CodeSharingPlatform/blob/main/images/uuid.PNG?raw=true)
and there is post with given UUID (if it exists, of course). As you can see, if there are any restrictions applied, they are displayed above the piece of code.


*New snippet*

Go to http://localhost:8889/code/new:
![alt text](https://github.com/AlicjaZab/CodeSharingPlatform/blob/main/images/new.PNG?raw=true)
and there you can write your code and add restrictions if you want. After pressing the "Submit" button, there will be displayed a pop-us message with UUID of your post (wchich means the code snippet was succcesfully saved).


_________________________________________________

**Working through API**


*Latest posts*

Send GET request to /api/code/latest, and you will receive a response with JSON containing a list of 10 latest snippets (or less and only the ones without restrictions) and some informations about each of them - code (content of post), date of posting it, time restriction (in seconds) and views restriction ('0' means there is no restriction).
```
{
  {
      "code": "Some piece of code...",
      "date": "2020/05/05 12:01:45",
      "time": 0,
      "views": 0
  },
  {
      "code": "Some other piece of code...",
      "date": "2020/05/05 10:20:32",
      "time": 0,
      "views": 0
  },
  ...
}
```


*Specified code snippet*

Send GET request to /api/code/[UUID], and you will receive a response with JSON containing snippets with given UUID and some informations about it -code (content of post), date of posting it, time restriction (in seconds) and views restriction ('0' means there is no restriction).
```

{
    "code": "Some secret piece of code...",
    "date": "2020/05/05 12:01:45",
    "time": 3089,
    "views": 5
}
```


*New snippet*

Send POST request to /api/code/new with a JSON containg code snippet, time restriction and views restriction:
```
{
    "code": "class Code { ...",
    "time": 0,
    "views": 0
}
```
then you will receive a response containing JSON with generated UUID for your post:
```
{ "id" : "7dc53df5-703e-49b3-8670-b1c468f47f1f" }
```
