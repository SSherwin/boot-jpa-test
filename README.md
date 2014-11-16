boot-jpa-test
=============

Spring Boot JPA test

The pupose of this is to test spring boot with JPA. The main issue was that others tests had not
been successful in that data on a many to many relationship was getting deleted when using a PATCH http
call to update the data.

However This example now works and returns the data using hypermedia-driven interface so that all the RESTful
endpoints can be discovered using curl (or whatever REST client you are using).

```
$ curl http://localhost:8080/
{
  "_links" : {
    "roles" : {
      "href" : "http://localhost:8080/roles"
    },
    "users" : {
      "href" : "http://localhost:8080/users"
    }
  }
}

$ curl http://localhost:8080/users
{
  "_links" : {
    "search" : {
      "href" : "http://localhost:8080/users/search"
    }
  },
  "_embedded" : {
    "users" : [ {
      "active" : 0,
      "dateJoined" : "2014-01-01",
      "firstName" : "admin",
      "surname" : "SurnameChanged",
      "userName" : "admin",
      "version" : 1,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users/1"
        },
        "roles" : {
          "href" : "http://localhost:8080/users/1/roles"
        }
      }
    }, {
      "active" : 1,
      "dateJoined" : "2014-02-01",
      "firstName" : "Jane",
      "surname" : "Doe",
      "userName" : "jdoe",
      "version" : 0,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users/2"
        },
        "roles" : {
          "href" : "http://localhost:8080/users/2/roles"
        }
      }
    }, {
      "active" : 1,
      "dateJoined" : "2014-03-01",
      "firstName" : "user",
      "surname" : "user",
      "userName" : "user",
      "version" : 0,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users/3"
        },
        "roles" : {
          "href" : "http://localhost:8080/users/3/roles"
        }
      }
    }, {
      "active" : 1,
      "dateJoined" : "2014-04-01",
      "firstName" : "admin2",
      "surname" : "admin2",
      "userName" : "admin2",
      "version" : 0,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users/4"
        },
        "roles" : {
          "href" : "http://localhost:8080/users/4/roles"
        }
      }
    } ]
  }
}


$ curl http://localhost:8080/users/2
{
  "active" : 1,
  "dateJoined" : "2014-02-01",
  "firstName" : "Jane",
  "surname" : "Doe",
  "userName" : "jdoe",
  "version" : 0,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/2"
    },
    "roles" : {
      "href" : "http://localhost:8080/users/2/roles"
    }
  }
}



$ curl http://localhost:8080/users/2/roles
{
  "_embedded" : {
    "roles" : [ {
      "roleDescription" : "Standard User",
      "roleName" : "ROLE_USER",
      "version" : 0,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/roles/2"
        },
        "users" : {
          "href" : "http://localhost:8080/roles/2/users"
        }
      }
    } ]
  }
}

$ curl -X PATCH -H "Content-Type:application/json" -d  '{"surname": "SurnameChanged" }' http://localhost:8080/users/2

$ curl http://localhost:8080/users/2
{
  "active" : 0,
  "dateJoined" : "2014-02-01",
  "firstName" : "Jane",
  "surname" : "SurnameChanged",
  "userName" : "jdoe",
  "version" : 1,
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/users/2"
    },
    "roles" : {
      "href" : "http://localhost:8080/users/2/roles"
    }
  }
}



$ curl http://localhost:8080/users/2/roles
{
  "_embedded" : {
    "roles" : [ {
      "roleDescription" : "Standard User",
      "roleName" : "ROLE_USER",
      "version" : 0,
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/roles/2"
        },
        "users" : {
          "href" : "http://localhost:8080/roles/2/users"
        }
      }
    } ]
  }
}

```

The Actuator shows information about the running application.
 * http://localhost:8080/env
 * http://localhost:8080/health
 * http://localhost:8080/metrics