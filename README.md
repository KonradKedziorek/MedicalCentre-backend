# MedicalCentre-backend
###Installation and running
You must have Maven installed on your computer.
In terminal you must navigate to directory in which project is stored
and run command "mvn install".
Then navigate to directory "target" and there run commend "java -jar MedicalCentreApplication-0.0.1-SNAPSHOT.jar"

###Database details
Database is set on docker, so you must have run docker deamon.
Then run command from docker directory in workspace (MedicalCentreApplication/docker/docker-local-db).

###Connection to local database
Database: PostgreSQL

Username: dev

Password: dev

Database name: medicalCentre

Port: 5432

##Testing application in Postman
In application were added by migration three users, roles and one example address.
###Login to application as admin (access to admin resources)

#### - - - - - - - - - - - - - - - - - - - - - - - -Login- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/login
```

BODY(RAW, JSON) example:

```json
{
    "email": "admin@email.com",
    "password": "admin"
}
```
#### - - - - - - - - - - - - - - - - - - - - - - - -Saving user- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/user/save
```

BODY(RAW, JSON) example:

```json
{
    "uuid": "",
    "name": "Test",
    "surname": "Test",
    "email": "test@email.com",
    "pesel": "98718976561",
    "phoneNumber": "796736261",
    "city": "Białystok",
    "postcode": "15-400",
    "street": "Ulica",
    "localNumber": "1",
    "houseNumber": "1",
    "roles": ["DOCTOR"]
}
```
While creating new user, an application is sending an email to email address given during registration. In content user can find password and username (which is email address).
#### - - - - - - - - - - - - - - - - - - - - - - - -Edit user- - - - - - - - - - - - - - - - - - - - - - - -
```http
  PUT localhost:8080/api/user/edit
```
Uuid value should be uuid of existing user (for example created in json above).
BODY(RAW, JSON) example:
```json
{
  "uuid": "898e35d4-90bb-43eb-a5b8-33977511ae6f",
  "name": "TestChanged",
  "surname": "Test",
  "email": "testChanged@email.com",
  "pesel": "98718976561",
  "phoneNumber": "596732861",
  "city": "Łomża",
  "postcode": "15-400",
  "street": "Ulica Inna",
  "localNumber": "1",
  "houseNumber": "1",
  "roles": ["DOCTOR"]
}
```
#### - - - - - - - - - - - - - - - - - - - - - - - -Delete user- - - - - - - - - - - - - - - - - - - - - - - -
```http
  PUT localhost:8080/api/user/uuid={uuid}/delete
```
Uuid value as pathVariable should be uuid of existing user.
BODY(none), just uuid value of user to delete.

#### - - - - - - - - - - - - - - - - - - - - - - - -Activate user account- - - - - - - - - - - - - - - - - - - - - - - -
```http
  PUT localhost:8080/api/user/activateAccount/pesel={pesel}
```
Pesel value of an user.

#### - - - - - - - - - - - - - - - - - - - - - - - -Saving role- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/role/save
```

BODY(RAW, JSON) example:

```json
{
  "roleName": "ROLE_EXAMPLE"
}
```

#### - - - - - - - - - - - - - - - - - - - - - - - -Change user roles- - - - - - - - - - - - - - - - - - - - - - - -
```http
  PUT localhost:8080/api/user/uuid={uuid}/role/changeUserRoles
```

BODY(RAW, JSON) example:

```json
{
  "roles": ["PATIENT"]
}
```

###Login to application as doctor (access to doctor resources)

#### - - - - - - - - - - - - - - - - - - - - - - - -Login- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/login
```

BODY(RAW, JSON) example:

```json
{
    "email": "doctor@email.com",
    "password": "doctor"
}
```
#### - - - - - - - - - - - - - - - - - - - - Saving or updating research project - - - - - - - - - - - - - - - - - - - -
This endpoint is to create or update existing research project. If a value of an uuid field will be empty we will create new research project. Otherwise we can update existing research project and send request with uuid of our project we want to change. In doctors field we have to send id and surname of existing user with doctor role who will be added do research project list of doctors.
```http
  POST localhost:8080/api/researchProject/saveOrUpdate
```

BODY(RAW, JSON) example:

```json
{
  "uuid": "",
  "name": "TestNameOfProject",
  "description": "Description Test",
  "doctors": [
    {
      "id": 4,
      "surname": "Kędziorek"
    }]
}
```
#### - - - - - - - - - - - - - - - - - - - - - - - -Delete research project- - - - - - - - - - - - - - - - - - - - - - - -
```http
  PUT localhost:8080/api/researchProject/uuid={uuid}/delete
```
Uuid value as pathVariable should be uuid of existing research project.
BODY(none), just uuid value of research project to delete.

#### - - - - - - - - - - - - - - - - - - - - - - - -Saving permission- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/permission/save
```
It is needed to set file value as a file and send there pdf with permission. In addiction to that we have to send in second key json value of request.
BODY(FORM-DATA) example:

| KEY | VALUE     | CONTENT TYPE |
| :-------- | :------- | :------- | 
| `file` | `file.pdf` |  `Auto`
| `permissionRequest` | `{"userUuid":"uuid","researchProjectUuid":"uuid"}`| `application/json`

#### - - - - - - - - - - - - - - - - - - - - - - - -Delete permission- - - - - - - - - - - - - - - - - - - - - - - -
```http
  DELETE localhost:8080/api/permission/uuid={uuid}/delete
```
Uuid value as pathVariable should be uuid of existing permission.
BODY(none), just uuid value of permission to delete.

#### - - - - - - - - - - - - - - - - - - - - - - - -Saving commission- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/commission/save
```

BODY(RAW, JSON) example:

```json
{
  "uuid": "",
  "typeOfResearch": "Test",
  "description": "Test description",
  "userPesel": "{userPesel}",
  "researchProjectUuid": "{researchProjectUuid}",
  "dateOfResearch": "2023-04-25",
  "timeOfResearch": "12:00"
}
```
#### - - - - - - - - - - - - - - - - - - - - - - - -Delete commission- - - - - - - - - - - - - - - - - - - - - - - -
```http
  DELETE localhost:8080/api/commission/uuid={uuid}/delete
```
Uuid value as pathVariable should be uuid of existing commission.
BODY(none), just uuid value of commission to delete.

#### - - - - - - - - - - - - - - - - - - - - Saving or updating result - - - - - - - - - - - - - - - - - - - -
This endpoint is to create or update existing result (as in research project above).
```http
  POST localhost:8080/api/result/saveOrUpdate
```

BODY(RAW, JSON) example:

```json
{
  "uuid": "{uuid}",
  "resultDescription": "Change test description of result",
  "commissionUuid": "{commissionUuid}"
}
```
#### - - - - - - - - - - - - - - - - - - - - - - - -Delete result- - - - - - - - - - - - - - - - - - - - - - - -
```http
  DELETE localhost:8080/api/result/uuid={uuid}/delete
```
Uuid value as pathVariable should be uuid of existing result.
BODY(none), just uuid value of result to delete.

###Login to application as patient (access to patient resources)

#### - - - - - - - - - - - - - - - - - - - - - - - -Login- - - - - - - - - - - - - - - - - - - - - - - -
```http
  POST localhost:8080/api/login
```

BODY(RAW, JSON) example:

```json
{
    "email": "patient@email.com",
    "password": "patient"
}
```
#### - - - - - - - - - - - - - - - - - - - - - - - -Get result- - - - - - - - - - - - - - - - - - - - - - - -
```http
  GET localhost:8080/api/result/uuid={uuid}/get
```
Uuid value as pathVariable should be uuid of existing result.
BODY(none), just uuid value of result to get.

#### - - - - - - - - - - - - - - - - - - - - - - - -Get commission- - - - - - - - - - - - - - - - - - - - - - - -
```http
  GET localhost:8080/api/commission/uuid={uuid}/get
```
Uuid value as pathVariable should be uuid of existing commission.
BODY(none), just uuid value of commission to get.
